package com.example.webflux.model.common;

import lombok.Data;

/**
 * 请求参数
 *
 * @author liuqian
 * created of 2024/8/3 10:34 for com.example.webflux.model.common
 */
@Data
public class ApiParam<T> {
    private T data;
}
