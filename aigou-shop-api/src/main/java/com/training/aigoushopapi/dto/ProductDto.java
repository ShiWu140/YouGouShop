package com.training.aigoushopapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto implements Serializable {
    private Integer page;
    private Integer size;
    private String name;
    private String productType;
    private String[] brands;
    private Double minP;
    private Double maxP;
    //排序字段 1为升序 0为降序
    private Integer status;
}
