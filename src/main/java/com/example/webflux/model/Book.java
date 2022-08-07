package com.example.webflux.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zetu
 * @date 2022/7/14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    /**
     * ID
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 作者
     */
    private String author;
}
