package me.smn.idgen;

public interface ICounterService {

    /**
     * 将counter的值加1并返回新的值
     * @param type 类型id
     * @param dc 数据中心 id
     * @return
     * @throws Exception
     */
    long addAndGet(long type, long dc) throws IllegalStateException;
}

