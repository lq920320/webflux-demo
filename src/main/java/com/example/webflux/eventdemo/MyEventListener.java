package com.example.webflux.eventdemo;


/**
 * @author zetu
 * @date 2022/8/7
 */
public abstract class MyEventListener {

    public abstract void onNewEvent(Integer event);

    public abstract void onEventFinished();
}
