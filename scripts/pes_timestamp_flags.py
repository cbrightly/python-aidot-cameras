"""Count video PES with PTS_DTS_flags == 0 in an MPEG-TS file.

This is the predicate that identified where the timestamp discontinuity comes
from, after nine other mechanisms had been eliminated.

This is the predicate that matters. An earlier capture on this same leg checked
for NEGATIVE DTS and found none - but a PES carrying no timestamps at all has no
DTS to be negative, so that check could never have seen it. go2rtc renders such a
PES as RTP timestamp 0, and Home Assistant then computes dts = -base_timestamp.
"""
import collections
import sys

def scan(path):
    pids = collections.Counter()
    flags = collections.Counter()
    zero_examples = []
    pes_total = 0
    with open(path, 'rb') as fh:
        data = fh.read()
    # find sync
    off = 0
    while off < len(data) and data[off] != 0x47:
        off += 1
    n = 0
    while off + 188 <= len(data):
        pkt = data[off:off+188]
        off += 188
        if pkt[0] != 0x47:
            # resync
            k = pkt.find(b'\x47')
            if k < 0:
                continue
            off = off - 188 + k
            continue
        n += 1
        pusi = pkt[1] & 0x40
        pid = ((pkt[1] & 0x1F) << 8) | pkt[2]
        afc = (pkt[3] >> 4) & 0x3
        if not pusi or afc in (0, 2):
            continue
        i = 4
        if afc == 3:
            i += 1 + pkt[4]
        if i + 9 > 188:
            continue
        if pkt[i:i+3] != b'\x00\x00\x01':
            continue
        sid = pkt[i+3]
        if not (0xE0 <= sid <= 0xEF):      # video stream ids only
            continue
        pes_total += 1
        pids[pid] += 1
        fl = (pkt[i+7] >> 6) & 0x3          # PTS_DTS_flags
        flags[fl] += 1
        if fl == 0 and len(zero_examples) < 5:
            zero_examples.append((n, pid, pkt[i+4] << 8 | pkt[i+5]))
    return pes_total, pids, flags, zero_examples, n

for p in sys.argv[1:]:
    try:
        tot, pids, flags, zeros, npkt = scan(p)
    except Exception as e:
        print(f"{p}: parse error {e}")
        continue
    if tot == 0:
        continue
    z = flags.get(0, 0)
    print(f"{p.split('/')[-1]}")
    print(f"   TS packets {npkt}   video PES {tot}   PIDs {dict(pids)}")
    print(f"   PTS_DTS_flags histogram: {dict(sorted(flags.items()))}")
    print(f"   >>> flags==0 (NO timestamps): {z}")
    if zeros:
        print(f"       examples (ts_pkt_index, pid, pes_len): {zeros}")
