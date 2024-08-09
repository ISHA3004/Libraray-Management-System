/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ISHA MISTRY
 */
public class DatabaseConnection {
    Connection conn = null;
    public DatabaseConnection(){
        String url = "jdbc:mysql://localhost:3306/library";
        String username = "root";
        String password = "";
        try{
             Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url,username,password);
            if(conn != null)
                System.out.println("Successfull Connection");
            else
                System.out.println("Connection failed");
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
         catch (ClassNotFoundException e) {
            System.out.println("Class not found "+e);
        }
    }
}
