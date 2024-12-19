package com.training.aigouapi.mapper;

import com.training.aigouapi.entity.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 商品表数据操作接口
 *
 * @author ethan
 */
@Mapper
public interface ProductMapper {

/*    @Select("select * from s_product")*/
    List<Product> list(String words);

    @Select("select * from s_product where id = #{id}")
    Product getId(String id);

    void update(Product product);

    void delete(List<String> ids);


    @Insert("insert into s_product(id,product_name, product_image, price, product_type, product_desc, create_time, product_brand)" +
            " values (#{id},#{productName}, #{productImage}, #{price}, #{productType}, #{productDesc}, #{createTime}, #{productBrand} )")
    void save(Product product);

    @Select("select * from s_product")
    List<Product> selectAll();
}
