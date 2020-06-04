package com.ceph.rados;

public class PoolStat {
    long numBytes;
    long numKB;
    long numObjects;
    long numObjectClones;
    long numObjectCopies;
    long numObectsMissingOnPrimary;
    long numObjectsUnfound;
    long numObjectsDegraded;
    long numRd;
    long numRdKB;
    long numWr;
    long numWrKB;
    long numUserBytes;
    long compressedBytesOrig;
    long compressedBytes;
    long compressedBytesAlloc;

    public long getNumBytes() {
        return numBytes;
    }

    public long getNumKB() {
        return numKB;
    }

    public long getNumObjects() {
        return numObjects;
    }

    public long getNumObjectClones() {
        return numObjectClones;
    }

    public long getNumObjectCopies() {
        return numObjectCopies;
    }

    public long getNumObectsMissingOnPrimary() {
        return numObectsMissingOnPrimary;
    }

    public long getNumObjectsUnfound() {
        return numObjectsUnfound;
    }

    public long getNumObjectsDegraded() {
        return numObjectsDegraded;
    }

    public long getNumRd() {
        return numRd;
    }

    public long getNumRdKB() {
        return numRdKB;
    }

    public long getNumWr() {
        return numWr;
    }

    public long getNumWrKB() {
        return numWrKB;
    }

    public long getNumUserBytes() {
        return numUserBytes;
    }

    public long getCompressedBytesOrig() {
        return compressedBytesOrig;
    }

    public long getCompressedBytes() {
        return compressedBytes;
    }

    public long getCompressedBytesAlloc() {
        return compressedBytesAlloc;
    }
}
