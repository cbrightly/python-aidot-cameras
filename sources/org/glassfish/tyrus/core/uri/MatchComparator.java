package org.glassfish.tyrus.core.uri;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.tyrus.core.DebugContext;

public class MatchComparator implements Comparator<Match>, Serializable {
    private final transient Logger LOGGER = Logger.getLogger(MatchComparator.class.getName());
    private final transient DebugContext debugContext;

    MatchComparator(DebugContext debugContext2) {
        this.debugContext = debugContext2;
    }

    public int compare(Match m1, Match m2) {
        DebugContext debugContext2 = this.debugContext;
        Logger logger = this.LOGGER;
        Level level = Level.FINER;
        DebugContext.Type type = DebugContext.Type.MESSAGE_IN;
        debugContext2.appendTraceMessage(logger, level, type, "Choosing better match from ", m1, " and ", m2);
        boolean m1exact = m1.isExact();
        boolean m2exact = m2.isExact();
        if (m1exact) {
            if (m2exact) {
                this.debugContext.appendTraceMessage(this.LOGGER, level, type, "Both ", m1, " and ", m2, " are exact matches");
                return 0;
            }
            this.debugContext.appendTraceMessage(this.LOGGER, level, type, m1, " is an exact match");
            return -1;
        } else if (m2exact) {
            this.debugContext.appendTraceMessage(this.LOGGER, level, type, m2, " is an exact match");
            return 1;
        } else {
            List<Integer> m1Indices = m1.getVariableSegmentIndices();
            List<Integer> m2Indices = m2.getVariableSegmentIndices();
            int i = 0;
            while (i < Math.max(m1Indices.size(), m2Indices.size())) {
                if (i > m2Indices.size() - 1) {
                    this.debugContext.appendTraceMessage(this.LOGGER, Level.FINER, DebugContext.Type.MESSAGE_IN, m2, " is a  better match, because ", m1, " has more variables");
                    return 1;
                } else if (i > m1Indices.size() - 1) {
                    this.debugContext.appendTraceMessage(this.LOGGER, Level.FINER, DebugContext.Type.MESSAGE_IN, m1, " is a  better match, because ", m2, " has more variables");
                    return -1;
                } else {
                    int m1Index = m1Indices.get(i).intValue();
                    int m2Index = m2Indices.get(i).intValue();
                    if (m1Index > m2Index) {
                        this.debugContext.appendTraceMessage(this.LOGGER, Level.FINER, DebugContext.Type.MESSAGE_IN, m1, " is a  better match, because it has longer exact path");
                        return -1;
                    } else if (m2Index > m1Index) {
                        this.debugContext.appendTraceMessage(this.LOGGER, Level.FINER, DebugContext.Type.MESSAGE_IN, m2, " is a  better match, because it has longer exact path");
                        return 1;
                    } else {
                        i++;
                    }
                }
            }
            return 0;
        }
    }
}
