package cn.cindy.jdbc.batch;

import cn.cindy.jdbc.BaseUtil;

import java.sql.*;
import java.util.Arrays;

/**
 * Created by Caowenyan on 2016/2/15.
 */
public class PreparedStatementTest {


    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName(BaseUtil.JDBC_DRIVER);

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(BaseUtil.DB_URL, BaseUtil.USER, BaseUtil.PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            // Create SQL statement
            String SQL = "INSERT INTO Employees (id, first, last, age,bir) " +
                    "VALUES(?, ?, ?, ?,?)";

            // Create PrepareStatement object
            pstmt = conn.prepareStatement(SQL);

            //Set auto-commit to false
            conn.setAutoCommit(false);

            // Set the variables
            pstmt.setInt( 1, 400 );
            pstmt.setString( 2, "Pappu" );
            pstmt.setString( 3, "Singh" );
            pstmt.setInt( 4, 33 );
            pstmt.setString(5,"2015-2-2");
            // Add it to the batch
            pstmt.addBatch();

            // Set the variables
            pstmt.setInt( 1, 401 );
            pstmt.setString( 2, "Pawan" );
            pstmt.setString(3, "Singh");
            pstmt.setInt( 4, 31 );
            pstmt.setDate(5,new Date(new java.util.Date().getTime()));
            // Add it to the batch
            pstmt.addBatch();
            //STEP 6: Clean-up environment

            int[] count = pstmt.executeBatch();
            System.out.println(Arrays.toString(count));

            //Explicitly commit statements to apply changes
            conn.commit();

            pstmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(pstmt!=null)
                    pstmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }
}
