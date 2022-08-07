package com.example.webflux.service;

import com.example.webflux.model.Post;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;

/**
 * @author zetu
 * @date 2022/7/14
 */
public interface PostRepository extends ReactiveElasticsearchRepository<Post, String> {
}
