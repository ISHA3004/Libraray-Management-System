/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;
import java.time.LocalDate;
import DTOs.TransactionDTO;
import DAOs.TransactionDAO;
import java.sql.Date;
/**
 *
 * @author ISHA MISTRY
 */
public class TransactionService {
    
    public boolean isValidToken(String token,int userid)
    {
        TransactionDAO transdao = new TransactionDAO();
        System.out.println("func token "+token);
        System.out.println("db token "+transdao.getToken(userid));
        return token.equals(transdao.getToken(userid));
    }
    
    public boolean borrowBook(TransactionDTO transdto)
    {
        LocalDate borrowdate = LocalDate.now();
        LocalDate duedate = borrowdate.plusDays(7);
        
        transdto.setBorrowdate(borrowdate);
        transdto.setDuedate(duedate);
        
        TransactionDAO transdao = new TransactionDAO();
        if(transdao.borrowBook(transdto))
        {
            //update books table
           transdao.updateBookCount(transdto);
            return true;
        }
        return false;
    }
    
    public int renewBook(TransactionDTO transdto)
    {
        TransactionDAO transdao = new TransactionDAO();
        LocalDate duedate = (transdao.getDueDate(transdto)).toLocalDate();
        LocalDate renewdate = LocalDate.now();
        if(duedate != null)
        {
            if(renewdate.isBefore(duedate) || renewdate.equals(duedate))
            {
                transdto.setBorrowdate(renewdate);
                transdto.setDuedate(renewdate.plusDays(7));
                if(transdao.renewBook(transdto))
                    return 1;
            }
            else
            {
                int totalDays = (int)java.time.temporal.ChronoUnit.DAYS.between(duedate, renewdate);
                return totalDays*10;
            }
        }
        return 0;
    }
    
    public int returnBook(TransactionDTO transdto)
    {
        TransactionDAO transdao = new TransactionDAO();
        LocalDate duedate = (transdao.getDueDate(transdto)).toLocalDate();
        System.out.println("service wala "+duedate);
        LocalDate returndate = LocalDate.now();
        if(duedate != null)
        {
            if(returndate.isBefore(duedate) || returndate.equals(duedate))
            {
                transdto.setReturndate(returndate); //update only return date
                transdao.returnBook(transdto);
                return 1;
            }
            else
            {
                transdto.setReturndate(returndate); //update only return date
                transdao.returnBook(transdto);
                int totalDays = (int)java.time.temporal.ChronoUnit.DAYS.between(duedate, returndate);
                return totalDays*10;
            }
        }
        return 0;
    }
    
}
