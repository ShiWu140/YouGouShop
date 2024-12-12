package com.training.aigouapi.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * JDBC工具类
 *
 * @author ethan
 */
public class JDBCUtils {
    // 日志记录器
    private static final Logger logger = LoggerFactory.getLogger(JDBCUtils.class);
    // 数据源
    private static DataSource dataSource = null;

    // 初始化连接池数据源
    static {
        Properties properties = new Properties();
        try {
            properties.load(JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties"));
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            logger.error("Failed to initialize data source", e);
        }
    }

    /**
     * 执行增删改
     *
     * @param sql    SQL语句
     * @param params 可变参数，SQL语句参数的值
     * @return 行数
     */
    public static int executeUpdate(String sql, Object... params) {
        // 创建连接，自动关闭
        try (Connection connection = dataSource.getConnection()) {
            // 创建命令对象
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // 设置占位符参数
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            // 执行命令
            return preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Failed to execute update", ex);
            // 向上抛出异常
            throw new RuntimeException(ex);
        }
    }

/**
 * 将给定的小驼峰命名法字符串转换为下划线命名法
 * 该方法主要用于将Java字段名转换为数据库列名
 * Java字段名通常使用驼峰命名法，而数据库列名则使用下划线来分隔单词
 *
 * @param camelCaseName Java字段名，例如：userName
 * @return 转换后的下划线命名法字符串，例如：user_name
 */
private static String toSnakeCase(String camelCaseName) {
    // 创建一个StringBuilder对象来构建最终的下划线命名字符串
    StringBuilder snakeCase = new StringBuilder();

    // 遍历输入字符串的每个字符
    for (char c : camelCaseName.toCharArray()) {
        // 如果当前字符是大写字母，则在其前面添加一个下划线，并将该字符转换为小写
        if (Character.isUpperCase(c)) {
            snakeCase.append('_').append(Character.toLowerCase(c));
        } else {
            // 否则，直接将当前字符添加到结果字符串中
            snakeCase.append(c);
        }
    }
    // 返回构建好的下划线命名法字符串
    return snakeCase.toString();
}


    /**
     * 执行查询操作
     *
     * @param clazz  要查询的实体类类型 如： Student.class
     * @param sql    SQL语句
     * @param params 占位符参数
     * @param <T>    泛型类型
     * @return 泛型集合
     */
    public static <T> List<T> executeQuery(Class<T> clazz, String sql, Object... params) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            // 执行查询获得ResultSet
            ResultSet rs = preparedStatement.executeQuery();
            // 创建List集合
            List<T> list = new ArrayList<>();
            // 遍历结果集
            while (rs.next()) {
                // 使用反射机制创建对象 new Student();
                T object = clazz.getDeclaredConstructor().newInstance();
                // 给对象的所有属性赋值
                // 使用反射获得类中所有属性 Student id name age gender address
                Field[] fields = clazz.getDeclaredFields();
                // 遍历所有属性
                for (Field field : fields) {
                    // 通过属性名，将结果集中的列的值读出来
                    String columnName = toSnakeCase(field.getName());
                    Object value = rs.getObject(columnName);
                    // 设置属性可以访问
                    field.setAccessible(true);
                    // 给属性赋值 对象.属性 = 值
                    field.set(object, value);
                }
                // 添加对象到List集合
                list.add(object);
            }
            return list;
        } catch (Exception ex) {
            logger.error("Failed to execute query", ex);
            throw new RuntimeException(ex);
        }
    }

    /**
     * 查询行数的方法
     *
     * @param sql    要执行的SQL语句
     * @param params 参数列表
     * @return 查询到的行数
     */
    public static Integer executeCount(String sql, Object... params) {
        // 获取数据库连接，try-with-resources语句确保在方法结束时关闭连接
        try (Connection connection = dataSource.getConnection()) {
            // 准备SQL语句
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // 设置SQL语句的参数
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            // 执行查询
            ResultSet resultSet = preparedStatement.executeQuery();
            // 处理查询结果
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            // 如果结果集为空，返回默认值0
            return 0;
        } catch (Exception e) {
            // 记录并处理异常
            logger.error("Failed to execute count", e);
            throw new RuntimeException(e);
        }
    }
}
