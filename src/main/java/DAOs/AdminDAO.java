/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import DTOs.BookDTO;
/**
 *
 * @author ISHA MISTRY
 */
public class AdminDAO extends DatabaseConnection{
    public String getToken(int userid)
    {
        String sql = "select token from user_login where userid = ?";
        try{
            int pos=1;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(pos++,userid);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next())
            {
                return rs.getString(1);
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return null;
    }
    
    public boolean updateBookCount(BookDTO bookdto)
    {
        String sql = "update books set count=? where bookid=?";
        try{
            int pos=1;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(pos++,bookdto.getCount());
            ps.setInt(pos++,bookdto.getBookid());
            ps.executeUpdate();
            
            return true;
        }
        catch(Exception e)
        {
            System.out.println(e);
            return false;
        }
    }
    
    public boolean addBook(BookDTO bookdto)
    {
        String sql = "insert into books(bookname,bookauthor,isbn,count) values(?,?,?,?)";
        try{
            int pos=1;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(pos++,bookdto.getBookname());
            ps.setString(pos++,bookdto.getBookauthor());
            ps.setLong(pos++, bookdto.getISBN());
            ps.setInt(pos++,bookdto.getCount());
            ps.executeUpdate();
            
            return true;
        }
        catch(Exception e)
        {
            System.out.println(e);
            return false;
        }
    }
    
    public boolean removeBook(BookDTO bookdto)
    {
        String sql = "delete from books where bookid=?";
        try{
            int pos=1;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(pos++,bookdto.getBookid());
            ps.executeUpdate();
            
            return true;
        }
        catch(Exception e)
        {
            System.out.println(e);
            return false;
        }
    }
}
