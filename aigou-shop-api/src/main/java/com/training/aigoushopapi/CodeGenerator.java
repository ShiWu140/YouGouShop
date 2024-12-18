package com.training.aigoushopapi;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.sql.Types;
import java.util.Collections;

/**
 * @author 十五
 */
public class CodeGenerator {
    public static void main(String[] args) {
        String url = "jdbc:mysql://127.0.0.1:3306/aigou?serverTimezone=GMT&useUnicode=true&characterEncoding=UTF-8";
        String username = "root";
        String password = "";
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("shiwu") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
                            .outputDir("aigou-shop-api\\src\\main\\java"); // 指定输出目录
                })
                .dataSourceConfig(builder ->
                        builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                            if (typeCode == Types.SMALLINT) {
                                // 自定义类型转换
                                return DbColumnType.INTEGER;
                            }
                            return typeRegistry.getColumnType(metaInfo);
                        })
                )
                .packageConfig(builder ->
                        // 设置父包名
                        builder.parent("com.training.aigoushopapi")
                                // 设置父包模块名
//                                .moduleName("book")
                                .mapper("mapper")
                                .service("service")
                                .serviceImpl("service.impl")
                                .xml("mappers")
                                // 设置mapperXml生成路径
                                .pathInfo(Collections.singletonMap(OutputFile.xml, "aigou-shop-api/src/main/resources/mapper"))
                                .build()
                )
                .strategyConfig(builder ->
                        // 设置需要生成的表名
                        builder.addInclude("s_brand","s_carousel_figure","s_order","s_order_product",
                                        "s_product","s_product_type","s_receiving_address","s_sales",
                                        "s_search_history","s_shop_cart","s_shop_cart_product","s_user")
                                .addTablePrefix("s_").entityBuilder()
                                // 启用 Lombok
                                .enableLombok()
                                // 启用字段注解
                                .enableTableFieldAnnotation()
                                .controllerBuilder()
                                // 启用 REST 风格
                                .enableRestStyle()
                                .entityBuilder().enableFileOverride()
                                .controllerBuilder().enableFileOverride()
                                .mapperBuilder().enableFileOverride()
                                .serviceBuilder().enableFileOverride()
                                .build()

                )
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
