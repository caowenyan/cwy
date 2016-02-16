package cn.cindy.jdbc.batch;

import cn.cindy.jdbc.BaseUtil;

import java.sql.*;
import java.util.Arrays;

/**
 *
 */
public class StatementTest {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName(BaseUtil.JDBC_DRIVER);

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(BaseUtil.DB_URL, BaseUtil.USER, BaseUtil.PASS);
            // Set auto-commit to false
            conn.setAutoCommit(false);

            stmt = conn.createStatement();

            // Create SQL statement
            String SQL = "INSERT INTO Employees (id, first, last, age, bir) " +
                    "VALUES(200,'Zia', 'Ali', 30, '08-08-8')";
            // Add above SQL statement in the batch.
            stmt.addBatch(SQL);

            // Create one more SQL statement
            //{d 'yyyy-mm-dd'},{t 'hh:mm:ss'}
            SQL = "INSERT INTO Employees (id, first, last, age, bir) " +
                    "VALUES(201,'Raj', 'Kumar', 35, {d '14-01-2'})";
            // Add above SQL statement in the batch.
            stmt.addBatch(SQL);

            //不知道为什吗此句sql无法执行
           /* Date bir = new Date(new java.util.Date().getTime());
            SQL = "INSERT INTO Employees (id, first, last, age, bir) " +
                    "VALUES(202,'Zia', 'Ali', 30, "+ bir +")";
            System.out.println(SQL);
            // Add above SQL statement in the batch.
            stmt.addBatch(SQL);*/

            // Create one more SQL statement
            SQL = "UPDATE Employees SET age = 35 " +
                    "WHERE id = 101";
            // Add above SQL statement in the batch.
            stmt.addBatch(SQL);

            // Create an int[] to hold returned values
            int[] count = stmt.executeBatch();
            System.out.println(Arrays.toString(count));

            //Explicitly commit statements to apply changes
            conn.commit();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }
}
