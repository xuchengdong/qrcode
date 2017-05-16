package com.struggle.timer;

import com.struggle.domain.LoginQrCodeBuilder;
import com.struggle.qiniu.QiNiuClient;
import com.struggle.redis.RedisQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xuchengdongxcd@126.com on 2016/12/2.
 */
public class QRCodeTask {

    private static final Logger LOGGER = LogManager.getLogger(QRCodeTask.class);

    private static final int CORE_POOL_SIZE = 5;
    private static final int MAXIMUM_POOL_SIZE = 10;
    private static final long KEEP_ALIVE_TIME = 60L;
    private static final int QUEUE_SIZE = 10;

    @Autowired
    private RedisQueue<Object> redisQueue;

    @Autowired
    private QiNiuClient qiNiuClient;

    @Autowired
    private LoginQrCodeBuilder loginQrCodeBuilder;

    @Value("${qb.login.check}")
    private String lgCheck;

    private ThreadPoolExecutor es;

    void init() {
        es = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(QUEUE_SIZE), new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public void exec() {
        if (redisQueue.isFull()) {
            return;
        }
        es.execute(new QRCodeThread(redisQueue, qiNiuClient, loginQrCodeBuilder, lgCheck));

        LOGGER.info("QRCodeTask PoolSize:{}, ActiveCount:{}, Queue:{}", es.getPoolSize(), es.getActiveCount(), es.getQueue().size());
    }

}
