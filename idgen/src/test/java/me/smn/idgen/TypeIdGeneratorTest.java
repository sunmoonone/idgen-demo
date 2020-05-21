package me.smn.idgen;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static me.smn.idgen.TypeIdGenerator.checkDataCenter;
import static me.smn.idgen.TypeIdGenerator.checkType;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class TypeIdGeneratorTest {

    private ICache cache;
    private IGeneratorConfig config;
    CacheCounter cc;
    TypeIdGenerator generator;

    @Before
    public void initCache() {
        String keyPre = "0:testGenId:";
        long type = 10;
        long dc = 0;

        config = new GeneratorConfigImpl(keyPre, type, dc);

        cache = new CacheImpl();
        cc = new CacheCounter(keyPre, cache);
        generator = new TypeIdGenerator(cc, config);

    }

    private static class GeneratorConfigImpl implements IGeneratorConfig{
        private String pre;
        private long type;
        private long dc;

        GeneratorConfigImpl(String keyPre, long type, long dc){
            this.pre = keyPre;
            this.type = type;
            this.dc = dc;

        }

        @Override
        public String getKeyPrefix() {
            return pre;
        }

        @Override
        public Long getObjType() {
            return type;
        }

        @Override
        public Long getDc() {
            return dc;
        }
    }

    @Test
    public void testGenId() {

        try {
            generator.generateId(-1, 1);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), ("type can't be greater than 511 or less than 0"));
        }

        try {
            generator.generateId(513, 1);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), ("type can't be greater than 511 or less than 0"));
        }

        try {
            generator.generateId(10, -1);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), ("datacenter can't be greater than 15 or less than 0"));
        }
        try {
            generator.generateId(10, 17);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), ("datacenter can't be greater than 15 or less than 0"));
        }


        long type = config.getObjType();
        long dc = config.getDc();

        cache.del("0:testGenId:10:0");
        try {
            generator.generateId(type, dc);

        } catch (IllegalStateException e) {
            assertTrue(e.getMessage().equals("key 0:testGenId:10:0 has not been initialized"));
        }

        cc.initCounter(type, dc, 0);
        long id1 = generator.generateId(type, dc);
        long id2 = generator.generateId(type, dc);

        assertThat(id1).isEqualTo(parseId(type, dc, 1L));
        assertThat(id2).isEqualTo(parseId(type, dc, 2L));

        assertThat(id1).isLessThan(id2);

        print(id1);
        print(id2);

        cc.initCounter(type, dc, 0);
        long id3 = generator.generateId(type, dc);
        assertThat(id3).isEqualTo(parseId(type, dc, 3L));

        assertThat(id3).isGreaterThan(id2);
        print(id3);

        cache.del("0:testGenId:10:0");
        cc.initCounter(type, dc, 100L);
        id1 = generator.generateId(type, dc);
        id2 = generator.generateId(type, dc);

        assertThat(id1).isEqualTo(parseId(type, dc, 101L));
        assertThat(id2).isEqualTo(parseId(type, dc, 102L));
        assertThat(id1).isLessThan(id2);

        print(id1);
        print(id2);


        long id4 = parseId(type, dc, 999999999999L);
        print(id4);

    }

    @Test
    public void testCheckDataCenter() {

        assertTrue(checkDataCenter(0, parseId(0, 0, 300)));
        assertTrue(checkDataCenter(10, parseId(100, 10, 300)));
        assertTrue(checkDataCenter(12, parseId(246, 12, 300)));
        assertTrue(checkDataCenter(13, parseId(511, 13, 4500)));
    }

    @Test
    public void testCheckType() {

        assertTrue(checkType(0, parseId(0, 0, 300)));
        assertTrue(checkType(100, parseId(100, 10, 300)));
        assertTrue(checkType(246, parseId(246, 12, 300)));
        assertTrue(checkType(511, parseId(511, 13, 4500)));
    }

    private void print(long id) {
        System.out.println(id);
    }

    private long parseId(long type, long dc, long value) {
        long sequenceBits = 50;
        long dcBits = 4;
        int typeBits = 9;

        long dcShift = typeBits;
        long sequenceShift = dcBits + typeBits;

        final long sequenceMask = ~(-1L << sequenceBits);

        long sequence = sequenceMask & value;

        return (sequence << sequenceShift)
                | (dc << dcShift)
                | type;

    }
}

