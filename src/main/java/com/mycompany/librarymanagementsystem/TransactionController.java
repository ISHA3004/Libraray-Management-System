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

import DTOs.TransactionDTO;
import Services.TransactionService;
import Constants.TransactionConstants;
import jakarta.ws.rs.HeaderParam;
/**
 *
 * @author ISHA MISTRY
 */
@Path(TransactionConstants.TRANSACTION)
public class TransactionController {
    
    @Path(TransactionConstants.BORROW)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response borrowBook(@HeaderParam (TransactionConstants.TOKEN)String token,JSONObject transdetails)
    {
        TransactionService transservice = new TransactionService();
        if(!transservice.isValidToken(token,((BigDecimal)transdetails.get(TransactionConstants.USERID)).intValue()))
        {
            return Response.status(TransactionConstants.STATUS_CODE).entity(TransactionConstants.SESSION_TIME_OUT).build();
        }
        
        TransactionDTO transdto = new TransactionDTO();
        transdto.setUserid(((BigDecimal)transdetails.get(TransactionConstants.USERID)).intValue());
        transdto.setBookid(((BigDecimal)transdetails.get(TransactionConstants.BOOKID)).intValue());
        boolean result = transservice.borrowBook(transdto);
        
        JSONObject res = new JSONObject();
        if(result)
        {
            res.put(TransactionConstants.STATUS,TransactionConstants.SUCCESS);
            return Response.status(TransactionConstants.STATUS_CODE).entity(res.toString()).build();
        }
        else
        {
            res.put(TransactionConstants.STATUS,TransactionConstants.FAILED);
            return Response.status(TransactionConstants.STATUS_CODE).entity(res.toString()).build();
        }
    }
    
    @Path(TransactionConstants.RENEW)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response renewBook(@HeaderParam (TransactionConstants.TOKEN)String token,JSONObject transdetails)
    {
        TransactionService transservice = new TransactionService();
        if(!transservice.isValidToken(token,((BigDecimal)transdetails.get(TransactionConstants.USERID)).intValue()))
        {
            return Response.status(TransactionConstants.STATUS_CODE).entity(TransactionConstants.SESSION_TIME_OUT).build();
        }
        
        TransactionDTO transdto = new TransactionDTO();
        transdto.setUserid(((BigDecimal)transdetails.get(TransactionConstants.USERID)).intValue());
        transdto.setBookid(((BigDecimal)transdetails.get(TransactionConstants.BOOKID)).intValue());
        
        int result = transservice.renewBook(transdto);
        
        JSONObject res = new JSONObject();
        if(result == 1)
        {
            res.put(TransactionConstants.STATUS,TransactionConstants.SUCCESS);
            return Response.status(TransactionConstants.STATUS_CODE).entity(res.toString()).build();
        }
        else if(result == 0)
        {
            res.put(TransactionConstants.STATUS,TransactionConstants.FAILED);
            return Response.status(TransactionConstants.STATUS_CODE).entity(res.toString()).build();
        }
        else
        {
            res.put(TransactionConstants.FINE,result);
            res.put(TransactionConstants.STATUS,TransactionConstants.SUCCESS);
            return Response.status(TransactionConstants.STATUS_CODE).entity(res.toString()).build();
        }
    }
    
    @Path(TransactionConstants.RETURN)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response returnBook(@HeaderParam (TransactionConstants.TOKEN)String token,JSONObject transdetails)
    {
        TransactionService transservice = new TransactionService();
        if(!transservice.isValidToken(token,((BigDecimal)transdetails.get(TransactionConstants.USERID)).intValue()))
        {
            return Response.status(TransactionConstants.STATUS_CODE).entity(TransactionConstants.SESSION_TIME_OUT).build();
        }
        
        TransactionDTO transdto = new TransactionDTO();
        transdto.setUserid(((BigDecimal)transdetails.get("userid")).intValue());
        transdto.setBookid(((BigDecimal)transdetails.get("bookid")).intValue());
        
        int result = transservice.returnBook(transdto);
        
        JSONObject res = new JSONObject();
        if(result == 1)
        {
            res.put(TransactionConstants.STATUS,TransactionConstants.SUCCESS);
            return Response.status(TransactionConstants.STATUS_CODE).entity(res.toString()).build();
        }
        else if(result == 0)
        {
            res.put(TransactionConstants.STATUS,TransactionConstants.FAILED);
            return Response.status(TransactionConstants.STATUS_CODE).entity(res.toString()).build();
        }
        else
        {
            res.put(TransactionConstants.FINE,result);
            res.put(TransactionConstants.STATUS,TransactionConstants.SUCCESS);
            return Response.status(TransactionConstants.STATUS_CODE).entity(res.toString()).build();
        }
    }
}
