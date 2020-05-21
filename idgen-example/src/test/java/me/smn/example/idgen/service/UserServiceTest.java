package me.smn.example.idgen.service;

import me.smn.idgen.TypeIdGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    TypeIdGenerator typeIdGenerator;


    @Test
    public void testCreateUser(){
        Long id1 = userService.createUser("zhangsan");

        assertThat(1).isEqualTo(TypeIdGenerator.getSequence(id1));
        System.out.println(String.format("id1 type:%s, dc:%s, counter: %s", TypeIdGenerator.getType(id1),
                TypeIdGenerator.getDataCenter(id1),TypeIdGenerator.getSequence(id1)));

        Long id2 = userService.createUser("lisi");
        assertThat(2).isEqualTo(TypeIdGenerator.getSequence(id2));

        Long id3 = userService.createUser("wangwu");
        assertThat(3).isEqualTo(TypeIdGenerator.getSequence(id3));

        Long id4 = userService.createUser("zhaoliu");
        assertThat(4).isEqualTo(TypeIdGenerator.getSequence(id4));

        System.out.println(String.format("id1: %s, id2: %s, id3: %s, id4: %s",id1, id2, id3, id4));

    }


    private long parseId(long type, long dc, long value){
        long sequenceBits=50;
        long dcBits = 4;
        int typeBits = 9;

        long dcShift = typeBits;
        long sequenceShift = dcBits+typeBits;

        final long sequenceMask = ~(-1L << sequenceBits);

        long sequence = sequenceMask & value;

        return (sequence << sequenceShift)
                | (dc << dcShift)
                | type;

    }


}
