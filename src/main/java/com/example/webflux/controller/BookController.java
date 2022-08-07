package com.example.webflux.controller;

import com.example.webflux.model.Book;
import com.example.webflux.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author qianliu
 */
@RestController
@RequestMapping(value = "/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("")
    public Flux<Book> all() {
        return this.bookService.findAll();
    }

    @PostMapping("")
    public Mono<Book> create(@RequestBody Book book) {
        return this.bookService.save(book);
    }

    @GetMapping("/{id}")
    public Mono<Book> get(@PathVariable("id") String id) {
        return this.bookService.findById(id);
    }

    @PutMapping("/{id}")
    public Mono<Book> update(@PathVariable("id") String id, @RequestBody Book book) {
        return this.bookService.update(id, book);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") String id) {
        return this.bookService.deleteById(id);
    }

}