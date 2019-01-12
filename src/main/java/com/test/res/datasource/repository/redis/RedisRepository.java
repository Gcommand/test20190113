package com.test.res.datasource.repository.redis;

import java.util.Map;

public interface RedisRepository {

    /**
     * Return all movies
     */
    Map<Object, Object> findAll();

    /**
     * Add key-value pair to Redis.
     */
    void add(String key, Object value);

    /**
     * Delete a key-value pair in Redis.
     */
    void delete(String id);

    /**
     * find a obj
     */
    Object find(String id);

}
