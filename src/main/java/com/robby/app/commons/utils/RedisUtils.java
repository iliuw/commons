package com.robby.app.commons.utils;

import com.google.common.collect.Lists;
import com.robby.app.commons.exceptions.BaseException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created @ 2020/5/14
 *
 * @author liuwei
 */
public class RedisUtils {
    final RedisTemplate<String, Object> tpl;

    public RedisUtils(RedisTemplate<String, Object> tpl) {
        this.tpl = tpl;
    }

    /**
     * 指定缓存life time
     * @param key cache key
     * @param ttl life time (seconds)
     * @return
     */
    public boolean expire(String key, long ttl) {
        if(ttl > 0) {
            tpl.expire(key, ttl, TimeUnit.SECONDS);
        }
        return true;
    }

    /**
     * 查询缓存的life time
     * @param key cache key
     * @return life time
     */
    public long ttl(String key) {
        return tpl.getExpire(key);
    }

    /**
     * 检查缓存是否存在
     * @param key cache key
     * @return
     */
    public boolean exists(String key) {
        return tpl.hasKey(key);
    }

    /**
     * 检查hash键是否存在
     * @param key cache key
     * @param field hash field
     * @return
     */
    public boolean hexists(String key, String field) {
        return tpl.opsForHash().hasKey(key, field);
    }

    /**
     * 删除缓存
     * @param key cache keys
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if(Optional.ofNullable(key).isPresent() && key.length > 0) {
            if(key.length == 1) {
                tpl.delete(key[0]);
            } else {
                tpl.delete(Lists.newArrayList(key));
            }
        }
    }

    /**
     * 原子递增
     * @param param [0]=key; [1]=delta or default 1
     */
    public long incr(Object... param) throws BaseException {
        if(!Optional.ofNullable(param).isPresent() || param.length <= 0) {
            throw new BaseException(1000, "缺少必要的参数：cache-key");
        }
        String key = param[0].toString();
        long delta = param.length < 2 ? 1 : ((Long)param[1]).longValue();
        if(delta <= 0) {
            throw new BaseException(1000, "递增因子必须大于0");
        }
        return tpl.opsForValue().increment(key, delta);
    }

    /**
     * 原子递减
     * @param param
     * @return
     * @throws BaseException
     */
    public long decr(Object... param) throws BaseException {
        if(!Optional.ofNullable(param).isPresent() || param.length <= 0) {
            throw new BaseException(1000, "缺少必要的参数：cache-key");
        }
        String key = param[0].toString();
        long delta = param.length < 2 ? 1 : ((Long)param[1]).longValue();
        if(delta <= 0) {
            throw new BaseException(1000, "递减因子必须大于0");
        }
        return tpl.opsForValue().increment(key, -delta);
    }

    /**
     * Get cache
     * @param key
     * @param <T>
     * @return
     */
    public <T> T get(String key) {
        return Optional.ofNullable(key).isPresent() ? (T)tpl.opsForValue().get(key) : null;
    }

    /**
     * Set cache
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, Object value) {
        tpl.opsForValue().set(key, value);
        return true;
    }

    /**
     * Set cache with ttl`s life
     * @param key
     * @param ttl
     * @param value
     * @return
     * @throws Exception
     */
    public boolean setEx(String key, long ttl, Object value) {
        tpl.opsForValue().set(key, value, ttl, TimeUnit.SECONDS);
        return true;
    }

    /**
     * Get hash item
     * @param key
     * @param field
     * @return
     */
    public <T> T hget(String key, String field) {
        return (T) tpl.opsForHash().get(key, field);
    }

    /**
     * Get hash`s all item
     * @param key
     * @return
     */
    public Map<Object, Object> hmget(String key) {
        return tpl.opsForHash().entries(key);
    }

    /**
     * Set hash
     * @param key
     * @param values
     * @return
     * @throws Exception
     */
    public boolean hmset(String key, Map<String, Object> values) {
        tpl.opsForHash().putAll(key, values);
        return true;
    }

    /**
     * Set hash and set ttl
     * @param key
     * @param values
     * @param ttl
     * @return
     * @throws Exception
     */
    public boolean hmset(String key, Map<String, Object> values, long ttl) {
        tpl.opsForHash().putAll(key, values);
        return expire(key, ttl);
    }

    /**
     * Put hash
     * @param key
     * @param field
     * @param value
     * @return
     * @throws Exception
     */
    public boolean hset(String key, String field, Object value) {
        tpl.opsForHash().put(key, field, value);
        return true;
    }

    /**
     * Multi delete hash`s item
     * @param key
     * @param field
     */
    @SuppressWarnings("unchecked")
    public void hdel(String key, String... field) {
        tpl.opsForHash().delete(key, field);
    }

    /**
     * hash incr
     * @param key
     * @param field
     * @param delta
     * @return
     */
    public double hincr(String key, String field, double delta) {
        return tpl.opsForHash().increment(key, field, delta);
    }

    /**
     * hash decr
     * @param key
     * @param field
     * @param delta
     * @return
     */
    public double hdecr(String key, String field, double delta) {
        return tpl.opsForHash().increment(key, field, -delta);
    }

    public Set<Object> sget(String key) {
        return tpl.opsForSet().members(key);
    }

    public boolean sexists(String key, Object value) {
        try {
            return tpl.opsForSet().isMember(key, value);
        } catch(Exception e) {
            return false;
        }
    }

    public long sadd(String key, Object... values) {
        return tpl.opsForSet().add(key, values);
    }

    public long saddEx(String key, long ttl, Object... values) {
        long count = tpl.opsForSet().add(key, values);
        expire(key, ttl);
        return count;
    }

    public long slen(String key) {
        return tpl.opsForSet().size(key);
    }

    public long sdel(String key, Object... values) {
        return tpl.opsForSet().remove(key, values);
    }

    public List<Object> lrange(String key, long... targets) {
        long begin = Optional.ofNullable(targets[0]).orElse(Long.parseLong("0"));
        long end = Optional.ofNullable(targets[1]).orElse(Long.parseLong("-1"));
        return tpl.opsForList().range(key, begin, end);
    }

    public long llen(String key) {
        return tpl.opsForList().size(key);
    }

    public <T> T lget(String key, long index) {
        return (T)tpl.opsForList().index(key, index);
    }

    public boolean lset(String key, Object value) {
        tpl.opsForList().rightPush(key, value);
        return true;
    }

    public boolean lsetEx(String key, Object value, long ttl) {
        tpl.opsForList().rightPush(key, value);
        expire(key, ttl);
        return true;
    }

    public boolean lpushAll(String key, List<Object> values) {
        tpl.opsForList().rightPushAll(key, values);
        return true;
    }

    public boolean lsetEx(String key, List<Object> values, long ttl) {
        tpl.opsForList().rightPushAll(key, values);
        expire(key, ttl);
        return true;
    }

    public boolean lupdate(String key, long index, Object value) {
        tpl.opsForList().set(key, index, value);
        return true;
    }

    public long ldel(String key, long index, Object value) {
        return tpl.opsForList().remove(key, index, value);
    }

    public <T> T lpop(String key) {
        return (T)tpl.opsForList().leftPop(key);
    }

}
