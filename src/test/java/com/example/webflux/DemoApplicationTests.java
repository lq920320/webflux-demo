package com.example.webflux;

import com.example.webflux.model.Book;
import com.example.webflux.model.Post;
import com.example.webflux.service.BookService;
import com.example.webflux.service.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private BookService bookService;

    @Autowired
    private PostRepository posts;

    @Test
    public void bookFindById() {
        String id = "100";
        Mono<Book> notFound = bookService.findById("100")
                .switchIfEmpty(Mono.error(new RuntimeException("Not Found by Id:" + id)));
        notFound.subscribe(System.out::println);
    }

    @Test
    public void testAll() {
        Flux<Post> allPost = all();
        List<Post> postList = allPost.toStream().collect(Collectors.toList());
        System.out.println(postList);
    }

    @Test
    public void createdTest() {
        Post post = Post.builder().id("1").title("titleAbc").content("This is content.").build();
        Mono<Post> postCreated = this.create(post);
        postCreated.block(Duration.ofMillis(10000));
    }

    @Test
    public void getTest() {
        Mono<Post> postInfo = get("1");
        System.out.println(postInfo);
    }

    public Flux<Post> all() {
        return this.posts.findAll();
    }

    public Mono<Post> create(Post post) {
        return this.posts.save(post);
    }

    public Mono<Post> get(String id) {
        return this.posts.findById(id);
    }

    public Mono<Post> update(String id, Post post) {
        return this.posts.findById(id)
                .map(p -> {
                    p.setTitle(post.getTitle());
                    p.setContent(post.getContent());

                    return p;
                })
                .flatMap(this.posts::save);
    }

    public Mono<Void> delete(String id) {
        return this.posts.deleteById(id);
    }

}
