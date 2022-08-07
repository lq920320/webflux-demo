package com.example.webflux.eventdemo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.concurrent.TimeUnit;

/**
 * @author zetu
 * @date 2022/8/7
 */
public class MyPublisher {

    private MyEventSource eventSource = new MyEventSource();

    public Flux<Integer> createFlux(FluxSink.OverflowStrategy strategy) {
        return Flux.create(fluxSink -> {
            eventSource.register(new MyEventListener() {
                @Override
                public void onNewEvent(Integer event) {
                    System.out.println("(" + Thread.currentThread().getName() + " ) publish >>> " + event);
                    fluxSink.next(event);
                }

                @Override
                public void onEventFinished() {
                    System.out.println("(" + Thread.currentThread().getName() + " ) event finished! ");
                    fluxSink.complete();
                }
            });
        }, strategy);
    }

    public void generateEvent(int times, int millis) {
        for (int i = 0; i < times; i++) {
            try {
                TimeUnit.MILLISECONDS.sleep(millis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            eventSource.publishNewEvent(i);
        }
    }
}
