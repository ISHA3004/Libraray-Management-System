/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import DAOs.AdminDAO;
import DTOs.BookDTO;

/**
 *
 * @author ISHA MISTRY
 */
public class AdminService {
    
    public boolean isValidToken(String token,int userid)
    {
        AdminDAO admindao = new AdminDAO();
        System.out.println("func token "+token);
        System.out.println("db token "+admindao.getToken(userid));
        return token.equals(admindao.getToken(userid));
    }
    
    public boolean updateBookCount(BookDTO bookdto)
    {
        AdminDAO admindao = new AdminDAO();
        return admindao.updateBookCount(bookdto);
    }
    
    public boolean addBook(BookDTO bookdto)
    {
        AdminDAO admindao = new AdminDAO();
        return admindao.addBook(bookdto);
    }
    
    public boolean removeBook(BookDTO bookdto)
    {
        AdminDAO admindao = new AdminDAO();
        return admindao.removeBook(bookdto);
    }
}
