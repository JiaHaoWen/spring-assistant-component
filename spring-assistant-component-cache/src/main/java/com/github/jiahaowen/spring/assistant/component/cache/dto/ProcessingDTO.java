package com.github.jiahaowen.spring.assistant.component.cache.dto;

/** @author jiahaowen */
public class ProcessingDTO {

    private volatile long startTime;

    private volatile CacheWrapper<Object> cache;

    private volatile boolean firstFinished = false;

    private volatile Throwable error;

    public ProcessingDTO() {
        startTime = System.currentTimeMillis();
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public CacheWrapper<Object> getCache() {
        return cache;
    }

    public void setCache(CacheWrapper<Object> cache) {
        this.cache = cache;
    }

    public boolean isFirstFinished() {
        return firstFinished;
    }

    public void setFirstFinished(boolean firstFinished) {
        this.firstFinished = firstFinished;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
