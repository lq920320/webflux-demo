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
//        Flux.fromStream();
        return Flux.create(fluxSink -> {
            for (int i = 0; i < 10; i++) {
                String id = String.valueOf(i + 1);
                Book book = Book.builder()
                        .id(id)
                        .name("book-" + id)
                        .author("author-" + id)
                        .build();
                fluxSink.next(book);
            }
            fluxSink.complete();
        });
    }

    @Override
    public Mono<Book> save(Book book) {
        return Mono.just(book);
    }

    @Override
    public Mono<Book> findById(String id) {
        return Mono.create(callback -> {
            Book book = Book.builder()
                    .id(id)
                    .name("book-" + id)
                    .author("author-" + id)
                    .build();
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
