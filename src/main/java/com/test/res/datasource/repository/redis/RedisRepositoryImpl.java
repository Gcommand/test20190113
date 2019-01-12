package com.test.res.datasource.repository.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;

@Repository
public class RedisRepositoryImpl implements RedisRepository {
    private static final String KEY = "RES";

    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations hashOperations;

    @Autowired
    public RedisRepositoryImpl(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init(){
        hashOperations = redisTemplate.opsForHash();
    }

    public void add(String key, Object value) {
        hashOperations.put(KEY, key, value);
    }

    public void delete(final String id) {
        hashOperations.delete(KEY, id);
    }

    public Object find(final String id){
        return hashOperations.get(KEY, id);
    }

    public Map<Object, Object> findAll(){
        return hashOperations.entries(KEY);
    }


}

