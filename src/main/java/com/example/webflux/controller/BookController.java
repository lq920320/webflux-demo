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

    /**
     * 获取所有图书
     *
     * @return 图书列表
     */
    @GetMapping("")
    public Flux<Book> all() {
        return this.bookService.findAll();
    }

    /**
     * 新增/创建图书数据
     *
     * @param book 要新增的图书
     * @return 刚刚新增的图书
     */
    @PostMapping("")
    public Mono<Book> create(@RequestBody Book book) {
        return this.bookService.save(book);
    }

    /**
     * 根据ID获取图书
     *
     * @param id 图书ID
     * @return 获取到的图书信息
     */
    @GetMapping("/{id}")
    public Mono<Book> get(@PathVariable("id") String id) {
        return this.bookService.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("通过ID：" + id + "未找到图书")));
    }

    /**
     * 更新图书信息
     *
     * @param id   图书ID
     * @param book 要更新的图书信息
     * @return 更新图书
     */
    @PutMapping("/{id}")
    public Mono<Book> update(@PathVariable("id") String id, @RequestBody Book book) {
        return this.bookService.update(id, book);
    }

    /**
     * 删除图书
     *
     * @param id 图书ID
     * @return 没有响应结果
     */
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") String id) {
        return this.bookService.deleteById(id);
    }

}
