package net.sqlcipher.database;

public class SQLiteQueryStats {
    long largestIndividualRowSize = 0;
    long totalQueryResultSize = 0;

    public SQLiteQueryStats(long totalQueryResultSize2, long largestIndividualRowSize2) {
        this.totalQueryResultSize = totalQueryResultSize2;
        this.largestIndividualRowSize = largestIndividualRowSize2;
    }

    public long getTotalQueryResultSize() {
        return this.totalQueryResultSize;
    }

    public long getLargestIndividualRowSize() {
        return this.largestIndividualRowSize;
    }
}
