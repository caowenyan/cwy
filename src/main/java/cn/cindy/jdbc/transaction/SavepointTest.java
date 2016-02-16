package cn.cindy.jdbc.transaction;

import cn.cindy.jdbc.BaseUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Savepoint;
import java.sql.Statement;

/**
 * 当设置一个保存点在事务中定义一个逻辑回滚点。
 * 如果发生错误，过去一个保存点，则可以使用rollback方法来撤消要么所有的改变或仅保存点之后所做的更改。
 *
 * 不过不知道怎么进行操作的????,一直是全部回归或是成功,没有在保存点之前保存,保存点之后回滚(调整savepoint的位置)
 */
public class SavepointTest {
    public static void main(String[] args) {
        Connection conn = null;
        Savepoint savepoint = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName(BaseUtil.JDBC_DRIVER);

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(BaseUtil.DB_URL, BaseUtil.USER, BaseUtil.PASS);
            //Assume a valid connection object conn
            conn.setAutoCommit(false);
            savepoint = conn.setSavepoint();

            //STEP 4: Execute a query to create statment with
            // required arguments for RS example.
            System.out.println("Creating statement...");
            //注意参数是update
            Statement stmt = conn.createStatement();
            //STEP 5: Execute a sql
            String sql = "INSERT INTO Employees  " +
                    "VALUES (116, 20, 'Rita', 'Tez')";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO Employees  " +
                    "VALUES (117, 22, 'Sita', 'Singh')";
            stmt.executeUpdate(sql);
            // If there is no error.
            conn.commit();
        }catch (Exception e){
            try {
                e.printStackTrace();
                if(conn!=null && savepoint!=null)
                    conn.rollback(savepoint);
            }catch (Exception e1){
                e1.printStackTrace();
            }
        }
    }
}
