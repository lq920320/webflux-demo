package com.example.webflux.eventdemo;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author zetu
 * @date 2022/8/7
 */
@Slf4j
public class MySubscriber extends BaseSubscriber<Integer> {
    private long PROCESS_DURATION = 30L;
    private CountDownLatch latch;

    public MySubscriber(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    protected void hookOnSubscribe(Subscription subscription) {
        System.out.println("subscribing....");
        request(1);
    }

    @Override
    protected void hookOnNext(Integer value) {
        System.out.println("     receive <<<< " + value);

        try {
            TimeUnit.MILLISECONDS.sleep(PROCESS_DURATION);
        } catch (InterruptedException e) {
            log.error("");
        }
        request(1);
    }
}
