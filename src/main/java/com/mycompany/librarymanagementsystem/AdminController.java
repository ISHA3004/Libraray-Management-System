/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.librarymanagementsystem;

import DTOs.BookDTO;
import Services.AdminService;
import Constants.AdminConstants;
import Constants.BookConstatants;

import Services.BookService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author ISHA MISTRY
 */
@Path(AdminConstants.ADMIN)
public class AdminController {
    
    @POST
    @Path(BookConstatants.BOOKS)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBooks(@HeaderParam (AdminConstants.TOKEN) String token,JSONObject userdetails)
    {
        BookService bookservice = new BookService();
        
        if(!bookservice.isValidToken(token,((BigDecimal)userdetails.get(AdminConstants.USERID)).intValue()))
        {
            return Response.status(AdminConstants.STATUS_CODE).entity(AdminConstants.SESSION_TIME_OUT).build();
        }
        
        if(!(userdetails.get(AdminConstants.USER_TYPE)).equals(AdminConstants.ADMIN))
        {
            return Response.status(AdminConstants.STATUS_CODE).entity(AdminConstants.NO_ADMIN_ACCESS).build();
        }
        
        List<BookDTO> books = bookservice.getAllBooks();
        System.out.println("contoller "+books.size());
        JSONArray arr = new JSONArray();
        if(!books.isEmpty())
        {
            for(BookDTO book : books)
            {
                JSONObject jobj = new JSONObject();
                jobj.put(BookConstatants.BOOKID,book.getBookid());
                jobj.put(BookConstatants.BOOKNAME,book.getBookname());
                jobj.put(BookConstatants.BOOKAUTHOR,book.getBookauthor());
                jobj.put(BookConstatants.ISBN,book.getISBN());
                jobj.put(BookConstatants.COUNT,book.getCount());
                
                arr.add(jobj);
            }
            JSONObject res = new JSONObject();
            res.put(BookConstatants.BOOKS,arr);
            res.put(BookConstatants.STATUS,BookConstatants.SUCCESS);
            
            return Response.status(BookConstatants.STATUS_CODE).entity(res.toString()).build();
        }
        else
        {
            JSONObject message = new JSONObject();
            message.put(BookConstatants.STATUS,BookConstatants.FAILED);
            return Response.status(BookConstatants.STATUS_CODE).entity(message.toString()).build();
        }
    }
    
    
    @POST
    @Path(BookConstatants.BOOK_SEARCH)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookByQuery(@HeaderParam (BookConstatants.TOKEN)String token,JSONObject details)
    {
        BookService bookservice = new BookService();
        if(!bookservice.isValidToken(token,((BigDecimal)details.get(AdminConstants.USERID)).intValue()))
        {
            return Response.status(AdminConstants.STATUS_CODE).entity(AdminConstants.SESSION_TIME_OUT).build();
        }
        
        if(!(details.get(AdminConstants.USER_TYPE)).equals(AdminConstants.ADMIN))
        {
            return Response.status(AdminConstants.STATUS_CODE).entity(AdminConstants.NO_ADMIN_ACCESS).build();
        }
        
        List<BookDTO> books = bookservice.getBookByQuery(details);
        JSONArray arr = new JSONArray();
        if(!books.isEmpty())
        {
            for(BookDTO book : books)
            {
                JSONObject jobj = new JSONObject();
                jobj.put(BookConstatants.BOOKID,book.getBookid());
                jobj.put(BookConstatants.BOOKNAME,book.getBookname());
                jobj.put(BookConstatants.BOOKAUTHOR,book.getBookauthor());
                jobj.put(BookConstatants.ISBN,book.getISBN());
                jobj.put(BookConstatants.COUNT,book.getCount());
                
                arr.add(jobj);
            }
            JSONObject res = new JSONObject();
            res.put(BookConstatants.BOOKS,arr);
            res.put(BookConstatants.STATUS,BookConstatants.SUCCESS);
            
            return Response.status(BookConstatants.STATUS_CODE).entity(res.toString()).build();
        }
        else
        {
            JSONObject message = new JSONObject();
            message.put(BookConstatants.STATUS,BookConstatants.FAILED);
            return Response.status(BookConstatants.STATUS_CODE).entity(message.toString()).build();
        }
    }
    
    @POST
    @Path(AdminConstants.UPDATE)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBook(@HeaderParam ("token")String token,JSONObject upddetails)
    {
        // check if user is customer or admin
        AdminService adminservice = new AdminService();
        
        if(!(upddetails.get(AdminConstants.USER_TYPE)).equals(AdminConstants.ADMIN))
        {
            return Response.status(AdminConstants.STATUS_CODE).entity(AdminConstants.NO_ADMIN_ACCESS).build();
        }
        if(!adminservice.isValidToken(token,((BigDecimal)upddetails.get(AdminConstants.USERID)).intValue()))
        {
            return Response.status(AdminConstants.STATUS_CODE).entity(AdminConstants.SESSION_TIME_OUT).build();
        }
        
        BookDTO bookdto = new BookDTO();
        bookdto.setBookid(((BigDecimal)upddetails.get(BookConstatants.BOOKID)).intValue());
        bookdto.setCount(((BigDecimal)upddetails.get(AdminConstants.NEW_COUNT)).intValue());
        boolean result = adminservice.updateBookCount(bookdto);
        
        JSONObject jobj = new JSONObject();
        if(result)
        {
            jobj.put(AdminConstants.STATUS,AdminConstants.SUCCESS);
            return Response.status(AdminConstants.STATUS_CODE).entity(jobj.toString()).build();
        }
        else
        {
            jobj.put(AdminConstants.STATUS,AdminConstants.FAILED);
            return Response.status(AdminConstants.STATUS_CODE).entity(jobj.toString()).build();
        }

    }
    
    @POST
    @Path("addbook")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBook(@HeaderParam ("token")String token,JSONObject bookdetails)
    {
        AdminService adminservice = new AdminService();
        
        if(!adminservice.isValidToken(token,((BigDecimal)bookdetails.get(AdminConstants.USERID)).intValue()))
        {
            return Response.status(AdminConstants.STATUS_CODE).entity(AdminConstants.SESSION_TIME_OUT).build();
        } 
        
        if(!(bookdetails.get(AdminConstants.USER_TYPE)).equals(AdminConstants.ADMIN))
        {
            return Response.status(AdminConstants.STATUS_CODE).entity(AdminConstants.NO_ADMIN_ACCESS).build();
        }
        
        
        BookDTO bookdto = new BookDTO();
        bookdto.setBookname((String)bookdetails.get(BookConstatants.BOOKNAME));
        bookdto.setBookauthor((String)bookdetails.get(BookConstatants.BOOKAUTHOR));
        bookdto.setISBN(((BigDecimal)bookdetails.get(BookConstatants.ISBN)).intValue());
        bookdto.setCount(((BigDecimal)bookdetails.get(BookConstatants.COUNT)).intValue());
        
        boolean result = adminservice.addBook(bookdto);
        JSONObject jobj = new JSONObject();
        if(result)
        {
            jobj.put(AdminConstants.STATUS,AdminConstants.SUCCESS);
            return Response.status(AdminConstants.STATUS_CODE).entity(jobj.toString()).build();
        }
        else
        {
            jobj.put(AdminConstants.STATUS,AdminConstants.FAILED);
            return Response.status(AdminConstants.STATUS_CODE).entity(jobj.toString()).build();
        }
    }
    
    @POST
    @Path("deletebook")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeBook(@HeaderParam ("token")String token,JSONObject bookdetails)
    {
        AdminService adminservice = new AdminService();
        
        if(!adminservice.isValidToken(token,((BigDecimal)bookdetails.get(AdminConstants.USERID)).intValue()))
        {
            return Response.status(AdminConstants.STATUS_CODE).entity(AdminConstants.SESSION_TIME_OUT).build();
        } 
        
        if(!(bookdetails.get(AdminConstants.USER_TYPE)).equals(AdminConstants.ADMIN))
        {
            return Response.status(AdminConstants.STATUS_CODE).entity(AdminConstants.NO_ADMIN_ACCESS).build();
        }
        
        BookDTO bookdto = new BookDTO();
        bookdto.setBookid(((BigDecimal)bookdetails.get(BookConstatants.BOOKID)).intValue());
        
        boolean result = adminservice.removeBook(bookdto);
        JSONObject jobj = new JSONObject();
        if(result)
        {
            jobj.put(AdminConstants.STATUS,AdminConstants.SUCCESS);
            return Response.status(AdminConstants.STATUS_CODE).entity(jobj.toString()).build();
        }
        else
        {
            jobj.put(AdminConstants.STATUS,AdminConstants.FAILED);
            return Response.status(AdminConstants.STATUS_CODE).entity(jobj.toString()).build();
        }
    }
}
