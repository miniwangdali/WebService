package org.pentagram;

import java.sql.*;

/**
 * Created by wang_ on 2016/9/28.
 */
public class DBHelper {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/msband?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    private static final String name = "com.mysql.jdbc.Driver";
    private static final String user = "root";
    private static final String password = "password";

    private Connection conn = null;
    private PreparedStatement preparedStatement = null;

    public DBHelper(){
        try{
            Class.forName(name);
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ResultSet query(String sql){
        ResultSet result = null;
        try{
            preparedStatement = conn.prepareStatement(sql);
            if(preparedStatement.execute()){
                result = preparedStatement.getResultSet();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void close(){
        try{
            this.conn.close();
            this.preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
