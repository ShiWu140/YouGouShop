package com.training.aigoushopapi.entity.request;

import com.training.aigoushopapi.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


/**
 * @author ethan
 * @version 1.0
 * @since 2024/12/26 01:14
 */
@Data
@AllArgsConstructor
public class CategoryProductRequest {
    private String id;
    private String typeName;
    private List<Product> product;
}
