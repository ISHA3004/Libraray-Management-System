/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;
import DTOs.BookDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ISHA MISTRY
 */
public class BookDAO extends DatabaseConnection{
    
    public BookDAO()
    {
        super();
    }
    
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
    public List<BookDTO> getBooks()
    {
        List<BookDTO> books = new ArrayList<>();
        String sql = "select bookid,bookname,bookauthor,isbn,count from books where count>0";
        
        try{
            int pos=1;
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                pos=1;
                BookDTO bookdto = new BookDTO();
                bookdto.setBookid(rs.getInt(pos++));
                bookdto.setBookname(rs.getString(pos++));
                bookdto.setBookauthor(rs.getString(pos++));
                bookdto.setISBN(rs.getLong(pos++));
                bookdto.setCount(rs.getInt(pos++));
                
                books.add(bookdto);
            }
            System.out.println("book length "+books.size());
            return books;
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
                
        return books;
    }
    
    public List<BookDTO> getBooksByAuthorname(String authorname)
    {
        String sql = "select bookid,bookname,bookauthor,isbn,count from books where count>0 and bookauthor=?";
        List<BookDTO> books = new ArrayList<>();
        try{
            int pos=1;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(pos++,authorname);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                pos=1;
                BookDTO bookdto = new BookDTO();
                bookdto.setBookid(rs.getInt(pos++));
                bookdto.setBookname(rs.getString(pos++));
                bookdto.setBookauthor(rs.getString(pos++));
                bookdto.setISBN(rs.getLong(pos++));
                bookdto.setCount(rs.getInt(pos++));
                
                books.add(bookdto);
            }
            System.out.println("book length "+books.size());
            return books;
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return books;
    }
    
    public List<BookDTO> getBooksByName(String bookname)
    {
        String sql = "select bookid,bookname,bookauthor,isbn,count from books where count>0 and bookname=?";
        List<BookDTO> books = new ArrayList<>();
        try{
            System.out.println("Dao mei "+bookname);
            int pos=1;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(pos++,bookname);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                pos=1;
                BookDTO bookdto = new BookDTO();
                bookdto.setBookid(rs.getInt(pos++));
                bookdto.setBookname(rs.getString(pos++));
                bookdto.setBookauthor(rs.getString(pos++));
                bookdto.setISBN(rs.getLong(pos++));
                bookdto.setCount(rs.getInt(pos++));
                
                books.add(bookdto);
            }
            System.out.println("book length "+books.size());
            return books;
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return books;
    }
    
    public List<BookDTO> getBooksById(int id)
    {
        String sql = "select bookid,bookname,bookauthor,isbn,count from books where bookid=?";
        List<BookDTO> books = new ArrayList<>();
        try
        {
            int pos=1;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(pos++,id);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                pos=1;
                BookDTO bookdto = new BookDTO();
                bookdto.setBookid(rs.getInt(pos++));
                bookdto.setBookname(rs.getString(pos++));
                bookdto.setBookauthor(rs.getString(pos++));
                bookdto.setISBN(rs.getLong(pos++));
                bookdto.setCount(rs.getInt(pos++));
                
                books.add(bookdto);
            }
            
            return books;
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return books;
    }
    
    public List<BookDTO> getBooksByQuery(String columnname,int intVal,String strVal)
    {
        List<BookDTO> books = new ArrayList<>();
        String sql = "select bookid,bookname,bookauthor,isbn,count from books where "+columnname+" = ?";
        if(intVal != -1)
        {
            try
            {
                int pos=1;
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(pos++,intVal);
                ResultSet rs = ps.executeQuery();
                
                while(rs.next())
                {
                    pos=1;
                    BookDTO bookdto = new BookDTO();
                    bookdto.setBookid(rs.getInt(pos++));
                    bookdto.setBookname(rs.getString(pos++));
                    bookdto.setBookauthor(rs.getString(pos++));
                    bookdto.setISBN(rs.getLong(pos++));
                    bookdto.setCount(rs.getInt(pos++));

                    books.add(bookdto);
                }

                return books;
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
        else
        {
            try
            {
                int pos=1;
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(pos++,strVal);
                ResultSet rs = ps.executeQuery();
                
                while(rs.next())
                {
                    pos=1;
                    BookDTO bookdto = new BookDTO();
                    bookdto.setBookid(rs.getInt(pos++));
                    bookdto.setBookname(rs.getString(pos++));
                    bookdto.setBookauthor(rs.getString(pos++));
                    bookdto.setISBN(rs.getLong(pos++));
                    bookdto.setCount(rs.getInt(pos++));

                    books.add(bookdto);
                }

                return books;
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
        return books;
    }
}
