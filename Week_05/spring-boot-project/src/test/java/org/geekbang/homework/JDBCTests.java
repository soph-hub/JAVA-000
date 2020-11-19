package org.geekbang.homework;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.geekbang.homework.utils.JdbcUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JDBCTests {

    /**
     * 测试添加数据
     */
    @Test
    public void insertTest() throws SQLException {
        /**
         * 步骤
         * 1.加载驱动
         * 2.获取连接
         * 3.编写新增数据的sql
         * 4.创建执行sql的对象
         * 6.开启事务
         * 7.执行sql
         * 8.提交事务/回滚事务
         * 9.释放资源
         */
        try (Connection connection = JdbcUtils.getConnection()){
            // 编写sql
            String sql = "insert into student (`name`) values ( ? )";
            // 创建执行sql的对象
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "haha");
            //开启事务
            JdbcUtils.beginTransaction();
            // 执行sql，并打印结果
            System.out.println(preparedStatement.execute() ? "修改数据成功!" : "修改数据失败!" );
            JdbcUtils.commitTransaction();
        } catch (SQLException e) {
            e.printStackTrace();
            JdbcUtils.rollbackTransaction();
        }
    }

    /**
     * 修改第四条数据名称为 haha
     */
    @Test
    public void updateTest() throws SQLException {
        /**
         * 步骤
         * 1.加载驱动
         * 2.获取连接
         * 3.编写修改数据的sql
         * 4.创建执行sql的对象
         * 6.开启事务
         * 7.执行sql
         * 8.提交事务/回滚事务
         * 9.释放资源
         */
        try (Connection connection = JdbcUtils.getConnection()){
            // 编写sql
            String sql = "update student set `name` = ? where id = ?";
            // 创建执行sql的对象
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "haha");
            preparedStatement.setObject(2, 4);
            //开启事务
            JdbcUtils.beginTransaction();
            // 执行sql，并打印结果
            System.out.println(preparedStatement.execute() ? "修改数据成功!" : "修改数据失败!" );
            JdbcUtils.commitTransaction();
        } catch (SQLException e) {
            e.printStackTrace();
            JdbcUtils.rollbackTransaction();
        }
    }

    /**
     * 删除数据测试
     * 删除第四条数据
     */
    @Test
    public void deleteTest() throws SQLException {
        /**
         * 步骤
         * 1.加载驱动
         * 2.获取连接
         * 3.编写删除数据的sql
         * 4.创建执行sql的对象
         * 6.开启事务
         * 7.执行sql
         * 8.提交事务/回滚事务
         * 9.释放资源
         */
        try (Connection connection = JdbcUtils.getConnection()){
            // 编写sql
            String sql = "delete from student where id = ?";
            // 创建执行sql的对象
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 4);
            //开启事务
            JdbcUtils.beginTransaction();
            // 执行sql，并打印结果
            System.out.println(preparedStatement.execute() ? "删除数据成功!" : "删除数据失败!" );
            JdbcUtils.commitTransaction();
        } catch (SQLException e) {
            e.printStackTrace();
            JdbcUtils.rollbackTransaction();
        }

    }

    /**
     * 测试查询数据
     * 使用jdbc工具类
     */
    @Test
    public void selectTest() {
        /**
         * 步骤
         * 1.加载驱动
         * 2.获取连接
         * 3.编写sql
         * 4.创建执行sql的对象
         * 5.执行sql
         * 6.遍历结果
         * 7.释放资源
         */
        try (Connection connection = JdbcUtils.getConnection()){
            // 编写sql
            String sql = "select * from student";
            // 创建执行sql的对象
            Statement statement = connection.createStatement();
            // 执行sql
            ResultSet resultSet = statement.executeQuery(sql);
            // 遍历结果
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                System.out.println(id+"-"+name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
