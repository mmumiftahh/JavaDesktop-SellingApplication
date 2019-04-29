/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Muhamad Miftah
 */
public class koneksi {
    private static Connection con;
    public static Connection getConnection(){
        if (con == null){
            try{
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                con = DriverManager.getConnection("jdbc:mysql://localhost/db_jualhape","root","");
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        return con;
    }
}
