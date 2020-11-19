package org.geekbang.homework.utils;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public class JdbcUtils {

    /**
     * 它为null表示没有事务 它不为null表示有事务 当开启事务时，需要给它赋值 当结束事务时，需要给它赋值为null 并且在开启事务时，让dao的多个方法共享这个Connection
     */
    private static ThreadLocal<Connection> tl = new ThreadLocal<>();

    private static DataSource dataSource = new HikariDataSource();

    public static DataSource getDataSource() {
        return dataSource;
    }
    /**
     * dao使用本方法来获取连接
     *
     * @return connection connection
     */
//    public static Connection getConnection() throws SQLException {
//        Connection connection = tl.get();//获取当前线程的事务连接
//        if (connection != null) {
//            return connection;
//        }
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            System.out.println("Can't find mysql jdbc driver");
//            e.printStackTrace();
//        }
//        try {
//            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "root", "root");
//            if (connection != null) {
//                tl.set(connection);
//                System.out.println("Connection successful!");
//            } else {
//                System.out.println("Connection failed!");
//            }
//        } catch (SQLException e) {
//            System.out.println("Connection failed!");
//            e.printStackTrace();
//        }
//        return connection;
//    }

    public static Connection getConnection() throws SQLException {
        /*
         * 如果有事务，返回当前事务的con
         * 如果没有事务，通过连接池返回新的con
         */
        Connection con = tl.get();//获取当前线程的事务连接
        if (con != null) {
            return con;
        }
        return getDataSource().getConnection();
    }

    /**
     * 开启事务
     * @throws SQLException SQLException
     */
    public static void beginTransaction() throws SQLException {
        Connection connection = tl.get();//获取当前线程的事务连接
        if (connection != null) {
            throw new SQLException("已经开启了事务，不能重复开启！");
        }
        connection = getConnection();//给con赋值，表示开启了事务
        connection.setAutoCommit(false);//设置为手动提交
        tl.set(connection);//把当前事务连接放到tl中
    }

    /**
     * 提交事务
     * @throws SQLException SQLException
     */
    public static void commitTransaction() throws SQLException {
        Connection connection = tl.get();//获取当前线程的事务连接
        if (connection == null) {
            throw new SQLException("没有事务不能提交！");
        }
        connection.commit();//提交事务
        if (!connection.isClosed()) {//如果参数连接没有关闭，关闭之！
            connection.close();
        }
        tl.remove();
    }

    /**
     * 回滚事务
     * @throws SQLException SQLException
     */
    public static void rollbackTransaction() throws SQLException {
        Connection connection = tl.get();//获取当前线程的事务连接
        if (connection == null) {
            throw new SQLException("没有事务不能回滚！");
        }
        connection.rollback();
        if (!connection.isClosed()) {//如果参数连接没有关闭，关闭之！
            connection.close();
        }
        tl.remove();
    }

}