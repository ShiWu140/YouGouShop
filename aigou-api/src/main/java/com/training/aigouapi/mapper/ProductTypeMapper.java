package com.training.aigouapi.mapper;

import com.training.aigouapi.entity.ProductType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 商品类型表数据操作接口
 *
 * @author ethan
 */
@Mapper
public interface ProductTypeMapper {

    List<ProductType> page(String words);

    @Select("select * from s_product_type where id = #{id}")
    ProductType getId(String id);

    void update(ProductType productType);

    void delete(List<String> ids);

    @Insert("insert into s_product_type(id, product_type_name, product_type_desc, product_type_icon) " +
            "values (#{id},#{productTypeName},#{productTypeDesc},#{productTypeIcon})")
    void save(ProductType productType);

    @Select("select * from s_product_type")
    List<ProductType> selectAll();
}
