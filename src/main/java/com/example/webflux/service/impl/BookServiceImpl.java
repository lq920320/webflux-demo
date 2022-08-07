package com.example.webflux.service.impl;

import com.example.webflux.model.Book;
import com.example.webflux.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 普通的book接口实现类
 *
 * @author zetu
 * @date 2022/7/14
 */
@Service
@Slf4j
public class BookServiceImpl implements BookService {


    @Override
    public Flux<Book> findAll() {
        return Flux.create(fluxSink -> {
            // 比如这里我们创建了 10 个对象，然后添加到 fluxSink 里
            for (int i = 0; i < 10; i++) {
                String id = String.valueOf(i + 1);
                Book book = Book.builder()
                        .id(id)
                        .name("book-" + id)
                        .author("author-" + id)
                        .build();
                // 加入到 sink
                fluxSink.next(book);
            }
            // 结束之后调用完结方法
            fluxSink.complete();
        });
    }

    @Override
    public Mono<Book> save(Book book) {
        // 直接返回对象，使用 just() 方法
        return Mono.just(book);
    }

    @Override
    public Mono<Book> findById(String id) {
        // 直接返回对象，也可以使用 create() 方法
        return Mono.create(callback -> {
            Book book = Book.builder()
                    .id(id)
                    .name("book-" + id)
                    .author("author-" + id)
                    .build();
            // 成功的时候返回的结果，success() 方法有一个带参数，一个不带参数
            // 另外还有 error() 方法，在异常的情况下返回的结果
            callback.success(book);
        });
    }

    @Override
    public Mono<Book> update(String id, Book book) {
        return null;
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return null;
    }
}
