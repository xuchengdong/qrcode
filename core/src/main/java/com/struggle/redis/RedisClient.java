package com.struggle.redis;

import com.struggle.constant.EscapeChar;
import org.springframework.data.redis.core.RedisTemplate;

import javax.validation.constraints.NotNull;
import java.util.concurrent.TimeUnit;

/**
 * @author xuchengdongxcd@126.com on 2016/10/20.
 */
public class RedisClient<E> {
    @NotNull
    private final String nameSpace;

    @NotNull
    private final RedisTemplate<String, E> client;

    public RedisClient(final String nameSpace, final RedisTemplate<String, E> client) {
        this.nameSpace = nameSpace;
        this.client = client;
    }

    public void set(String key, E obj, String... businessSpace) {
        client.boundValueOps(getKey(key, businessSpace)).set(obj);
    }

    public void set(String key, E obj, int second, String... businessSpace) {
        client.boundValueOps(getKey(key, businessSpace)).set(obj, second, TimeUnit.SECONDS);
    }

    public E get(String key, String... businessSpace) {
        return client.boundValueOps(getKey(key, businessSpace)).get();
    }

    public void del(String key, String... businessSpace) {
        client.delete(getKey(key, businessSpace));
    }

    public long llen(String key, String... businessSpace) {
        return client.boundListOps(getKey(key, businessSpace)).size();
    }

    public E leftPop(String key, String... businessSpace) {
        return client.boundListOps(getKey(key, businessSpace)).leftPop();
    }

    public void rightPush(String key, E obj, String... businessSpace) {
        client.boundListOps(getKey(key, businessSpace)).rightPush(obj);
    }

    private String getKey(String key, String... businessSpace) {
        StringBuilder bisSpace = null;
        if (businessSpace != null && businessSpace.length > 0) {
            bisSpace = new StringBuilder(businessSpace.length * 10);
            for (String space : businessSpace) {
                bisSpace.append(space).append(EscapeChar.COLON);
            }
        }
        return nameSpace + EscapeChar.COLON + (bisSpace != null ? bisSpace.toString() : "") + key;
    }
}
