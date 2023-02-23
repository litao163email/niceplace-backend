package com.taoli.niceplace.service;

import com.taoli.niceplace.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;

@SpringBootTest
public class RedisTest {

    @Resource
    private RedisTemplate redisTemplate;

    @Test
    void test() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        // 增
        valueOperations.set("taoliString", "dog");
        valueOperations.set("taoliInt", 1);
        valueOperations.set("taoliDouble", 2.0);
        User user = new User();
        user.setId(1L);
        user.setUsername("taoli");
        valueOperations.set("taoliUser", user);
        // 查
        Object taoli = valueOperations.get("taoliString");
        Assertions.assertTrue("dog".equals((String) taoli));
        taoli = valueOperations.get("taoliInt");
        Assertions.assertTrue(1 == (Integer) taoli);
        taoli = valueOperations.get("taoliDouble");
        Assertions.assertTrue(2.0 == (Double) taoli);
        System.out.println(valueOperations.get("taoliUser"));
        valueOperations.set("taoliString", "dog");
        redisTemplate.delete("taoliString");
    }
}
