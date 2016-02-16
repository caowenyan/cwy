package cn.cindy.jdbc.transaction;

import cn.cindy.jdbc.BaseUtil;

import java.sql.*;

/**
 * JDBC驱动程序默认使用auto-commit模式手动事务支持，使用Connection对象的的setAutoCommit()方法。
 * 如果传递一个布尔值false到setAutoCommit()，关闭自动提交。可以传递一个布尔值true将其重新打开。
 *
 * 一旦已经完成了变化，要提交更改，然后调用commit（在连接对象）方法:conn.commit( );
 * 回滚更新对数据库所做的使用命名连接conn:conn.rollback( );
 */
public class SimpleTest {
    public static void main(String[] args) {
        Connection conn = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName(BaseUtil.JDBC_DRIVER);

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(BaseUtil.DB_URL, BaseUtil.USER, BaseUtil.PASS);
            //Assume a valid connection object conn
            conn.setAutoCommit(false);

            //STEP 4: Execute a query to create statment with
            // required arguments for RS example.
            System.out.println("Creating statement...");
            //注意参数是update
            Statement stmt = conn.createStatement();
            //STEP 5: Execute a sql
            String sql = "INSERT INTO Employees  " +
                    "VALUES (106, 20, 'Rita', 'Tez')";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO Employees  " +
                    "VALUES (107, 22, 'Sita', 'Singh')";
            stmt.executeUpdate(sql);
            // If there is no error.
            conn.commit();

            stmt.close();
            conn.close();
        }catch (Exception e){
            try {
                e.printStackTrace();
                if(conn!=null)
                   conn.rollback();
            }catch (Exception e1){
                e1.printStackTrace();
            }
        }finally{
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }
}
