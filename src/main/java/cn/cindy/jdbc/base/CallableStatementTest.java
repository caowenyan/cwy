package cn.cindy.jdbc.base;

import cn.cindy.jdbc.BaseUtil;

import java.sql.*;

/**
 * Created by Caowenyan on 2016/2/15.
 */
public class CallableStatementTest {

    public static void main(String[] args) {
        Connection conn = null;
        CallableStatement stmt = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName(BaseUtil.JDBC_DRIVER);

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(BaseUtil.DB_URL, BaseUtil.USER, BaseUtil.PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            String sql = "{call getEmpName (?, ?)}";
            stmt = conn.prepareCall(sql);

            //Bind IN parameter first, then bind OUT parameter
            int empID = 102;
            stmt.setInt(1, empID); // This would set ID as 102
            // Because second parameter is OUT so register it
            stmt.registerOutParameter(2, java.sql.Types.VARCHAR);

            //Use execute method to run stored procedure.
            System.out.println("Executing stored procedure..." );
            stmt.execute();

            //Retrieve employee name with getXXX method
            String empName = stmt.getString(2);
            System.out.println("Emp Name with ID:" +
                    empID + " is " + empName);
            stmt.close();
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
                if(stmt!=null)
                    stmt.close();
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
