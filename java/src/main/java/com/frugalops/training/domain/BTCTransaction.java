package com.frugalops.training.domain;

import java.time.LocalDate;

public class BTCTransaction {
    private LocalDate date;
    private Integer txCount;
    private Integer generatedCoins;
    private Integer blockSize;
    private Integer blockCount;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getTxCount() {
        return txCount;
    }

    public void setTxCount(Integer txCount) {
        this.txCount = txCount;
    }

    public Integer getGeneratedCoins() {
        return generatedCoins;
    }

    public void setGeneratedCoins(Integer generatedCoins) {
        this.generatedCoins = generatedCoins;
    }

    public Integer getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(Integer blockSize) {
        this.blockSize = blockSize;
    }

    public Integer getBlockCount() {
        return blockCount;
    }

    public void setBlockCount(Integer blockCount) {
        this.blockCount = blockCount;
    }
}
