package com.example.webflux.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author zetu
 * @date 2022/7/14
 */
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
