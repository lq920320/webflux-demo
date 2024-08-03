package com.example.webflux.controller;

import com.example.webflux.model.Post;
import com.example.webflux.model.common.ApiParam;
import com.example.webflux.service.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author zetu
 */
@RestController
@RequestMapping(value = "/posts")
public class PostController {

    @Autowired
    private PostRepository posts;

    /**
     * 获取所有数据
     *
     * @return 数据列表
     */
    @GetMapping("")
    public Flux<Post> all() {
        return this.posts.findAll();
    }

    /**
     * 创建一条数据
     *
     * @param post 要创建的数据
     * @return 已经创建的数据
     */
    @PostMapping("")
    public Mono<Post> create(@RequestBody Post post) {
        return this.posts.save(post);
    }

    /**
     * 创建多条数据
     *
     * @param param 要创建的数据
     */
    @PostMapping("batchSave")
    public Flux<Post> batchSave(@RequestBody ApiParam<List<Post>> param) {
        return this.posts.saveAll(param.getData());
    }

    /**
     * 根据ID获取
     *
     * @param id 报告ID
     * @return 对应ID的数据
     */
    @GetMapping("/{id}")
    public Mono<Post> get(@PathVariable("id") String id) {
        return this.posts.findById(id);
    }

    /**
     * 更新一条数据
     *
     * @param id   报告ID
     * @param post 具体更新的内容
     * @return 已更新的数据
     */
    @PutMapping("/{id}")
    public Mono<Post> update(@PathVariable("id") String id, @RequestBody Post post) {
        return this.posts.findById(id)
                .map(p -> {
                    p.setTitle(post.getTitle());
                    p.setContent(post.getContent());
                    return p;
                })
                .flatMap(this.posts::save);
    }

    /**
     * 删除一条数据
     *
     * @param id 报告ID
     * @return 没有返回内容
     */
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") String id) {
        return this.posts.deleteById(id);
    }

}
