package com.robby.app.commons.datatype.generators;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * Created @ 2019/12/7
 *
 * @author liuwei
 */
public class RobbyUUIDIdGenerator implements IdentifierGenerator {

    final JedisPool jedisPool;

    @Autowired
    public RobbyUUIDIdGenerator(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     *
     * @param entity redis id池的index
     * @return
     */
    @Override
    public Number nextId(Object entity) {
        Jedis client = null;
        Long id = null;

        try{
            client = jedisPool.getResource();
            id = client.incr(entity.toString());
        } catch(Exception e) {
            id = Long.valueOf(-1);
        } finally{
            if(client != null) {
                client.close();
            }
        }

        return id;
    }

    /**
     *
     * @param entity 用于生成UUID的字符串
     * @return
     */
    @Override
    public String nextUUID(Object entity) {
        String id = null;
        try {
            id = UUID.nameUUIDFromBytes(entity.toString().getBytes("UTF-8")).toString().replaceAll("-", "").toUpperCase();
        } catch (UnsupportedEncodingException e) {
            id = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        }
        return id;
    }
}
