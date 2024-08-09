/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;
import DAOs.BookDAO;
import DTOs.BookDTO;
import java.math.BigDecimal;
import java.util.List;
import org.json.simple.JSONObject;
/**
 *
 * @author ISHA MISTRY
 */
public class BookService {
    private BookDAO bookdao;
    
    public BookService()
    {
        bookdao = new BookDAO();
    }
    
    public boolean isValidToken(String token,int userid)
    {
        bookdao = new BookDAO();
        System.out.println("func token "+token);
        System.out.println("db token "+bookdao.getToken(userid));
        return token.equals(bookdao.getToken(userid));
    }
    
    public List<BookDTO> getAllBooks(){
        List<BookDTO> books = bookdao.getBooks();
        return books;
    }
    
    public List<BookDTO> getBookByQuery(JSONObject details)
    {
        String columnname = (String)details.get("columnname");
       
        if(columnname.equals("bookid"))
        {
            int value = ((BigDecimal)details.get("value")).intValue();
            return bookdao.getBooksByQuery(columnname,value,null);
        }
        else
        {
            String value = (String)details.get("value");
            return bookdao.getBooksByQuery(columnname,-1,value);
        }
    }
}
