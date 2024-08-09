/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;
import DTOs.TransactionDTO;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.sql.ResultSet;
/**
 *
 * @author ISHA MISTRY
 */
public class TransactionDAO extends DatabaseConnection{
    
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
    
    public boolean borrowBook(TransactionDTO transdto)
    {
        String sql = "insert into book_transaction(userid,bookid,borrowdate,duedate,status) values(?,?,?,?,?)";
        try{
            int pos=1;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(pos++,transdto.getUserid());
            ps.setInt(pos++,transdto.getBookid());
            ps.setDate(pos++,Date.valueOf(transdto.getBorrowdate()));
            ps.setDate(pos++,Date.valueOf(transdto.getDuedate()));
            ps.setString(pos++,"issued");
            ps.executeUpdate();
            
            return true;
        }
        catch(Exception e)
        {
            System.out.println(e);
            return false;
        } 
    }
    
    
    public boolean updateBookCount(TransactionDTO transdto)
    {
        String sql = "update books set count=count-1 where count>0 and bookid=?";
        try{
            int pos=1;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(pos++,transdto.getBookid());
            ps.executeUpdate();
            
            return true;
        }
        catch(Exception e)
        {
            System.out.println(e);
            return false;
        } 
    }
    
    public Date getDueDate(TransactionDTO transdto)
    {
        String sql = "select duedate from book_transaction where userid=? and bookid=?";
        try{
            int pos=1;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(pos++,transdto.getUserid());
            ps.setInt(pos++,transdto.getBookid());
            ResultSet rs = ps.executeQuery();
            
            if(rs.next())
            {
                pos=1;
                Date duedate = rs.getDate(pos);
                System.out.println("dao wala "+duedate);
                return duedate;
            }
            return null;
        }
        catch(Exception e)
        {
            System.out.println(e);
            return null;
        }
    }
    
    public boolean renewBook(TransactionDTO transdto)
    {
        String sql = "update book_transaction set borrowdate=?,duedate=?,status=? where userid=? and bookid=?";
        try{
            int pos=1;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(pos++,Date.valueOf(transdto.getBorrowdate()));
            ps.setDate(pos++,Date.valueOf(transdto.getDuedate()));
            ps.setString(pos++,"renewed");
            ps.setInt(pos++,transdto.getUserid());
            ps.setInt(pos++,transdto.getBookid());
            ps.executeUpdate();
            
            return true;
        }
        catch(Exception e)
        {
            System.out.println(e);
            return false;
        }
    }
    
    public boolean returnBook(TransactionDTO transdto)
    {
        String sql = "update book_transaction set returndate=?,status=? where userid=? and bookid=?";
        try{
            int pos=1;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(pos++,Date.valueOf(transdto.getReturndate()));
            ps.setString(pos++,"returned");
            ps.setInt(pos++,transdto.getUserid());
            ps.setInt(pos++,transdto.getBookid());
            ps.executeUpdate();
            System.out.println("returned");
            return true;
        }
        catch(Exception e)
        {
            System.out.println(e);
            return false;
        }
    }
}
