package me.smn.idgen;

public class CacheCounter implements ICounterService{

    private ICache cache;
    String keyPre;

    public CacheCounter(String keyPrefix, ICache cache){
        this.cache = cache;
        keyPre = keyPrefix;
    }


    /**
     * initCounter should be called on application startup if counter key does not exist in cache store
     * @param type
     * @param dc
     */
    public synchronized void initCounter(long type, long dc, long initValue){
        cache.setForIncrNx(String.format("%s%s:%s",keyPre , type, dc), initValue);
    }


    @Override
    public long addAndGet(long type, long dc) throws IllegalStateException {
        //throw error if key has not been initialized
        String k = String.format("%s%s:%s",keyPre , type, dc);
        if(!cache.exists(k)){
            throw new IllegalStateException("key "+k+" has not been initialized");
        }
        return cache.incr(k);
    }
}

