# webflux-demo

WebFlux 是 Spring Framework5.0 中引入的一种新的反应式 Web 框架。通过Reactor 项目实现 Reactive Streams
规范，是一个完全异步和非阻塞的框架。其本身不会加快程序执行速度，但在高并发情况下借助异步IO能够以少量而稳定的线程处理更高的吞吐，规避文件IO/网络IO阻塞带来的线程堆积。

Spring WebFlux 是一个完全非阻塞、基于注解的 Web 框架，构建在 Project Reactor 之上，可以在 HTTP 层上构建响应式应用程序。 WebFlux 使用新的路由功能特性将函数式编程应用于 Web
层并绕过声明性控制器和 RequestMapping。 WebFlux 要求需要将 Reactor 作为核心依赖项导入。

在 Spring 5 中添加了 WebFlux 作为 Spring MVC 的响应式替代方案，并增加了对以下方面的支持：

- **非阻塞线程**：完成指定任务而不等待先前任务完成的并发线程。
- **Reactive Stream API**：一种标准化工具，包括用于具有非阻塞背压的异步流处理选项。
- **异步数据处理**：当数据在后台处理，用户可以继续使用正常的应用程序功能而不会中断。

最终，WebFlux 取消了 SpringMVC 的 `thread-per-request` 模型，而是使用多 EventLoop 非阻塞模型来启用反应式、可扩展的应用程序。 随着对 Netty、Undertow 和 Servlet
3.1+ 容器等流行服务器的支持，WebFlux 已成为响应式堆栈的关键部分。

#### 1 使用注解构建一个 web 应用

我们使用 `spring-boot` 来构建应用，首先需要引入依赖：

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
```

定义启动类：

```java

@SpringBootApplication
@EnableWebFlux
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
```

定义 Controller 类，跟正常的 Spring MVC 接口定义一样，唯一的区别就是返回类型不同，只能是 `Mono` 或者 `Flux` 的：

```java

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
        return this.bookService.findById(id);
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

```

而 Service 接口里的实现则是直接创建返回结果，这里我们只展示部分实现，例如：

```java

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
    // ...some other code
}
```

然后启动项目，接口调用和 Spring MVC 一致

这里我们还可以使用支持响应式的 `spring-data` 的 `Elasticsearch` 来共同构建，方便数据操作。这样我们就只需要引入依赖：

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-elasticsearch</artifactId>
</dependency>
```

定义好 ES 文档的对象：

```java

@Document(indexName = "posts")
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    private String id;

    @Field(store = true, type = FieldType.Text, fielddata = true)
    private String title;

    @Field(store = true, type = FieldType.Text, fielddata = true)
    private String content;
}
```

然后声明一个数据操作接口类：

```java
public interface PostRepository extends ReactiveElasticsearchRepository<Post, String> {
}
```

这样 Controller 就可以直接使用：

```java

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
```

#### 2 使用 Router 构建一个 web 应用

首先，我们来创建一个路由，通过 URL `http://localhost:8080/hello` 可以访问我们的接口内容。这定义了用户如何请求我们将在处理程序中定义的数据。

```java

@Configuration
public class GreetingRouter {

    @Bean
    public RouterFunction<ServerResponse> route(GreetingHandler greetingHandler) {
        return RouterFunctions.route(RequestPredicates.GET("/hello")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
                greetingHandler::hello);
    }
}
```

现在我们将添加一个处理程序来监听所有请求 `/hello` 路由的用户。 一旦路由识别出请求的路径匹配，它就会将用户发送给处理程序。 我们的处理程序接收消息并将用户带到带有我们问候语的页面。

```java

@Component
public class GreetingHandler {

    public Mono<ServerResponse> hello(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromValue("Hello, Spring WebFlux!"));
    }
}
```

最后运行项目，我们访问 `http://localhost:8080/hello` 便会看到响应结果：

```
Hello, Spring WebFlux!
```


