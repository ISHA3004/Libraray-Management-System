/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.librarymanagementsystem;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;
import org.json.simple.JSONObject;
import java.util.List;

import DTOs.BookDTO;
import Services.*;
import Constants.BookConstatants;

import jakarta.ws.rs.HeaderParam;
import org.json.simple.JSONArray;
/**
 *
 * @author ISHA MISTRY
 */
@Path(BookConstatants.CUST)
public class BookController {
    
    @POST
    @Path(BookConstatants.BOOKS)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBooks(@HeaderParam (BookConstatants.TOKEN) String token,JSONObject userdetails)
    {
        BookService bookservice = new BookService();
        
        if(!bookservice.isValidToken(token,((BigDecimal)userdetails.get(BookConstatants.USERID)).intValue()))
        {
            return Response.status(BookConstatants.STATUS_CODE).entity(BookConstatants.SESSION_TIME_OUT).build();
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
            return Response.status(400).entity(message.toString()).build();
        }
    }
    
    
    @POST
    @Path(BookConstatants.BOOK_SEARCH)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookByQuery(@HeaderParam (BookConstatants.TOKEN)String token,JSONObject details)
    {
        BookService bookservice = new BookService();
        if(!bookservice.isValidToken(token,((BigDecimal)details.get(BookConstatants.USERID)).intValue()))
        {
            return Response.status(BookConstatants.STATUS_CODE).entity(BookConstatants.SESSION_TIME_OUT).build();
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
}
