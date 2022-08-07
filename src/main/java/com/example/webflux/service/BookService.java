package com.example.webflux.service;

import com.example.webflux.model.Book;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 普通的book接口类
 *
 * @author zetu
 * @date 2022/7/14
 */
public interface BookService {
    /**
     * 获取所有
     *
     * @return {@link Flux<Book>} 所有数据
     */
    Flux<Book> findAll();

    /**
     * 保存新对象
     *
     * @param book 要保存数据
     * @return {@link Mono<Book>} 已保存的数据
     */
    Mono<Book> save(Book book);

    /**
     * 根据ID获取数据
     *
     * @param id 数据ID
     * @return {@link Mono<Book>} 获取到的数据
     */
    Mono<Book> findById(String id);

    /**
     * 更新数据
     *
     * @param id   数据ID
     * @param book 要更新的数据
     * @return {@link Mono<Book>} 更新后的数据
     */
    Mono<Book> update(String id, Book book);

    /**
     * 删除数据
     *
     * @param id 数据ID
     * @return {@link Mono<Void>} 无结果返回
     */
    Mono<Void> deleteById(String id);
}
