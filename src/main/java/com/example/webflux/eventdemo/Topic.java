package com.example.webflux.eventdemo;

import lombok.Data;

/**
 * @author zetu
 * @date 2022/8/7
 */
@Data
public class Topic {
    /**
     * 主题名称
     */
    private String topic;

    /**
     * 时间戳
     */
    private long timestamp;
}
