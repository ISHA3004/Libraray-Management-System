/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.librarymanagementsystem;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.core.Response;
import org.json.simple.JSONObject;

import Constants.LoginConstants;
import DTOs.LoginDTO;
import Services.LoginService;
/**
 *
 * @author ISHA MISTRY
 */
@Path(LoginConstants.USER)
public class LoginController {
    
    private LoginService loginservice;
    
    @POST
    @Path(LoginConstants.LOGIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response userlogin(@HeaderParam (LoginConstants.TOKEN) String token,JSONObject loginDetails)
    {
        System.out.println("hello");
        String username = (String)loginDetails.get(LoginConstants.USERNAME);
        String password = (String)loginDetails.get(LoginConstants.PASSWORD);
        
        LoginDTO logindto = new LoginDTO();
        logindto.setUsername(username);
        logindto.setPassword(password);
        logindto.setToken(token);
        
        loginservice = new LoginService();
        logindto = loginservice.isValidUsernamePassword(logindto);
        JSONObject res = new JSONObject();
        if(logindto != null)
        {
            res.put(LoginConstants.USERID,logindto.getUserid());
            res.put(LoginConstants.USERTYPE,logindto.getUsertype());
            res.put(LoginConstants.TOKEN,logindto.getToken());
            res.put(LoginConstants.STATUS,LoginConstants.SUCCESS);
           
            return Response.status(LoginConstants.STATUS_CODE).entity(res.toString()).build();
        }
        else
        {
            res.put(LoginConstants.STATUS,LoginConstants.FAILED);
            return Response.status(LoginConstants.STATUS_CODE).entity(res.toString()).build();
        }
    }
}
