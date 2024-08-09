/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DTOs.LoginDTO;
/**
 *
 * @author ISHA MISTRY
 */
public class LoginDAO extends DatabaseConnection{
   
    public LoginDAO(){
        super();
        System.out.println("login dao");
    }
    
    public void createUser(LoginDTO logindto)
    {
        String sql = "insert into user_login(username,password,usertype,token) values(?,?,?,?)";
        try{
            int pos=1;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(pos++,logindto.getUsername());
            ps.setString(pos++,logindto.getPassword());
            ps.setString(pos++,logindto.getUsertype());
            ps.setString(pos++,logindto.getToken());
            ps.executeUpdate();
            
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    public LoginDTO userLogin(LoginDTO logindto)
    {
        String sql = "select userid,password,usertype,token from user_login where username=?";
        try{
            int ind=1;
            String dbPassword=null,dbUsertype=null,dbToken=null;
            int dbUserid=0;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(ind++,logindto.getUsername());
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                ind=1;
                dbUserid = rs.getInt(ind++);
                dbPassword = rs.getString(ind++);
                dbUsertype = rs.getString(ind++);
                dbToken = rs.getString(ind++);
                
                if((logindto.getPassword()).equals(dbPassword))
                {
                    logindto.setUserid(dbUserid);
                    logindto.setUsertype(dbUsertype);
                    logindto.setToken(dbToken);
                    System.out.println("Dao mei "+logindto.toString());
                    return logindto;
                }
            }
            else
            {
                logindto.setUsertype("customer");
                createUser(logindto);
                
                String isql = "SELECT LAST_INSERT_ID();";
                try{
                    PreparedStatement ips = conn.prepareStatement(isql);
                    ResultSet irs = ips.executeQuery();
                    if(irs.next())
                    {
                        logindto.setUserid(irs.getInt(1));
                    }
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
                return logindto;
            }
            
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return null;
    }
}
