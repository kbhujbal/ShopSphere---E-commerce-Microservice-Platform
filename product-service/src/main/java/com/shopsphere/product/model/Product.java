package com.shopsphere.product.model;

import com.shopsphere.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@org.springframework.data.mongodb.core.mapping.Document(collection = "products")
@Document(indexName = "products")
public class Product extends BaseEntity {
    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Keyword)
    private String category;

    @Field(type = FieldType.Double)
    private BigDecimal price;

    @Field(type = FieldType.Integer)
    private Integer stockQuantity;

    @Field(type = FieldType.Text)
    private List<String> images;

    @Field(type = FieldType.Keyword)
    private String brand;

    @Field(type = FieldType.Double)
    private Double rating;

    @Field(type = FieldType.Integer)
    private Integer reviewCount;

    @Field(type = FieldType.Boolean)
    private boolean active;

    @Field(type = FieldType.Keyword)
    private String sku;

    @Field(type = FieldType.Text)
    private List<String> tags;

    @Field(type = FieldType.Text)
    private List<String> specifications;
} 