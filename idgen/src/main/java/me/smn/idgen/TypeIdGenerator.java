package me.smn.idgen;

/**
 * 根据对象的类型生成自增的对象id
 *
 * 可以使用不同的ICounterService 来使用不同的计数器实现。
 */
public class TypeIdGenerator {
    // 0{bits for counter}{bits for data center}{bits for type}
    private final static long sequenceBits = 50L;
    private final static long dcBits = 4L;
    private final static long typeBits = 9L;

    private final static long sequenceShift = dcBits + typeBits;
    private final static long dcShift = typeBits;

    private final long maxTypeId = ~(-1L << typeBits);
    private final long maxDatacenterId = ~(-1L << dcBits);
    private final static long sequenceMask = ~(-1L << sequenceBits);

    private ICounterService counterService;
    private IGeneratorConfig config;

    public TypeIdGenerator(ICounterService counterService, IGeneratorConfig config) {
        this.counterService = counterService;
        this.config = config;
    }


    public ICounterService getCounterService(){
        return counterService;
    }

    /**
     * 生成对象id
     *
     * @param type type number of entity
     * @param dc   data center number
     * @return
     * @throws Exception on failure
     */
    public long generateId(long type, long dc) throws IllegalArgumentException, IllegalStateException {

        if (type > maxTypeId || type < 0) {
            throw new IllegalArgumentException(String.format("type can't be greater than %d or less than 0", maxTypeId));
        }

        if (dc > maxDatacenterId || dc < 0) {
            throw new IllegalArgumentException(String.format("datacenter can't be greater than %d or less than 0", maxDatacenterId));
        }

        long sequence = counterService.addAndGet(type, dc);
        sequence = sequence & sequenceMask;

        return (sequence << sequenceShift)
                | (dc << dcShift)
                | type;

    }

    public long generateId() throws IllegalArgumentException, IllegalStateException {
        return this.generateId(this.config.getObjType(), this.config.getDc());
    }

    public static long add(long id, int delta, long dc, long type){
        long sequence = id >> sequenceShift;
        sequence += delta;

        sequence = sequence & sequenceMask;

        return (sequence << sequenceShift)
                | (dc << dcShift)
                | type;
    }
    public static long minus(long id, int delta, long dc, long type){
        long sequence = id >> sequenceShift;
        sequence -= delta;
        if(sequence<=0){
            sequence =0;

        }
        sequence = sequence & sequenceMask;
        return (sequence << sequenceShift)
                | (dc << dcShift)
                | type;

    }

    public static boolean checkType(long type, long id) {
        return getType(id) == type;
    }

    public static boolean checkDataCenter(long dc, long id) {
        return getDataCenter(id) == dc;
    }

    public static long getType(long id) {
        return (id) & ~(-1L << typeBits);
    }

    public static long getDataCenter(long id){
        return ((id >> dcShift) & ~(-1L << dcBits));
    }

    public static long change(long id, long dc, long type){
        long sequence = id >> sequenceShift;
        sequence = sequence & sequenceMask;

        return (sequence << sequenceShift)
                | (dc << dcShift)
                | type;

    }

    /**
     * 获取id高位数值
     */
    public static long getSequence(long id){
        return  id >> sequenceShift;
    }

}

