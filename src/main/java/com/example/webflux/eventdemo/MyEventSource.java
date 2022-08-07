package com.example.webflux.eventdemo;

/**
 * @author zetu
 * @date 2022/8/7
 */
public class MyEventSource {

    private MyEventListener eventListener;

    public void register(MyEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void publishNewEvent(Integer event) {
        this.eventListener.onNewEvent(event);
    }
}
