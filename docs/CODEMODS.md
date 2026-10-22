# Codemods

Some source rewrites are too large to review line by line and too easy to get
subtly wrong by hand. Those are kept as scripts under `scripts/codemods/`, and
the commit they produce is *generated*: it is made again by running the script,
never edited by hand or rebased.

## explicit_phase_state

`scripts/codemods/explicit_phase_state.py` replaces the `locals()` plumbing of
the four split methods (the DTLS and SDES opens and the two serve loops, see
`tests/source_parts.py`) with one explicit state object per method:

- each method gets a `@dataclasses.dataclass(slots=True)` class
  (`_DtlsOpenState`, `_SdesOpenState`, `_SdesKeepaliveState`,
  `_DtlsServeLoopState`) with one field per name that crosses a phase
  boundary. The method's own arguments are constructor arguments; every other
  field starts unset, so reading it too early raises `AttributeError` as the
  unbound local did;
- phases take `(self, ps)` and read and write `ps.<name>` (the name `ps` is
  fixed; the script refuses if a phase already uses it); they return only a
  control signal (`_PhaseReturn`, `_LoopContinue`, `_LoopBreak`) or None;
- the `if "x" in _in: x = _in["x"]` prologues, the read-back blocks after each
  call and the three `_phase_outputs` copies go away.

A name stays a plain local of one phase where making it a field would change
what another phase sees (it is rebound but was never handed back, it is bound
by `except ... as`, or nothing outside the phase ever reads it). Exported names
bound by `import`, `def` or `class` keep their binding and get `ps.x = x` right
after it. The script prints these decisions for every phase.

It refuses, with exit status 2 and a reason, rather than guess: a phase call
inside `try`/`with`, a phase returning a bare value, a closure that would see a
rebinding it did not see before, export tuples that differ between the returns
of one phase, `nonlocal`/walrus/`del` on a field, and similar shapes.

### Running it

Rewriting needs libcst; `--check` and `--awaits` need only the standard library.

```sh
pip install -e ".[codemod]"        # or prefix the commands with: uvx --with libcst
python scripts/codemods/explicit_phase_state.py aidot_cameras/camera
python scripts/codemods/explicit_phase_state.py aidot_cameras/camera --check
```

After rewriting it runs `ruff format` on the files it changed (ruff on `PATH`,
`python -m ruff`, or `uvx ruff`; `--no-format` skips this), since the rewrite
lengthens lines. It is deterministic and idempotent: a method already converted
is left alone, so a second run changes nothing. `--check` exits non-zero unless every method
is converted and no `locals()` call or `_phase_outputs` is left.
`--awaits` prints every `await` in each method and its phases in source order,
with `ps.x` shown as `x`; the list from before the rewrite and after it must be
identical (the order of the awaited calls is unchanged).

### Regenerating on a new base

The generated commit must be **regenerated, not rebased**, whenever the base
moves (soak fixes, the 1.0.0 tag). Rebasing it over a fix to a phase module
either conflicts on most hunks or, worse, applies cleanly and leaves the fix's
new name as a plain local where it should be a field. Regenerating classifies
the fix's names along with everything else.

```sh
git switch -c refactor/explicit-phase-state-1.0.0 v1.0.0
# the codemod and the test helper commits (no generated code in them)
git cherry-pick <refactor(tools): codemod ...> <test: read pinned source ...>
python scripts/codemods/explicit_phase_state.py aidot_cameras/camera
python scripts/codemods/explicit_phase_state.py aidot_cameras/camera --check
# the guard swap: the only hand-written part of the generated commit
git checkout <previous apply commit> -- \
    tests/test_phase_state_is_complete.py \
    tests/test_phase_returns_reach_the_caller.py \
    tests/test_loop_break_ends_the_loop.py
git rm tests/test_phase_exports_are_complete.py
pytest tests -q --ignore=tests/e2e -p no:cacheprovider -n 4
pytest tests/e2e -m e2e -q --timeout=180
git commit -m "refactor: apply explicit_phase_state codemod"
```

Compare `--awaits` on the base and on the result before committing, and
validate the branch head against live cameras before it lands.

If the script refuses on the new base, a fix introduced a shape it does not
convert safely; adjust the fix (or the script) on the base first rather than
editing the generated output.

Source-pinning tests read the split methods through
`tests/source_parts.without_state` (or `full_source`, which applies it), which
drops the `ps.` prefix, so their pins hold in both forms.
