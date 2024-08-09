/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;
import DAOs.LoginDAO;
import DTOs.LoginDTO;
import java.util.Random;
/**
 *
 * @author ISHA MISTRY
 */
public class LoginService {
    
    public LoginDTO isValidUsernamePassword(LoginDTO logindto)
    {
        String password = logindto.getPassword();
        
        if(password.length() == 8)
        {
            boolean digit = false,alphabet=false,specialChar=false;
            char[] pass = password.toCharArray();
            for(int i=0;i<pass.length;i++)
            {
                if(Character.isDigit(pass[i]))
                    digit = true;
                else if(Character.isLetter(pass[i]))
                    alphabet = true;
                else
                    specialChar = true;
            }
            if(digit && alphabet && specialChar)
            {
                LoginDAO logindao = new LoginDAO();
                System.out.println("Token value "+logindto.getToken());
                if(logindto.getToken() == null)
                {
                    String tokengen = generateToken();
                    logindto.setToken(tokengen);
                }
                
                logindto = logindao.userLogin(logindto);
                System.out.println("Service mei "+logindto.toString());
             
                return logindto;
            }
        }
        return null;
    }
    
    private String generateToken()
    {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz01234567891@#$%&";
        int strlen = str.length();
        Random random = new Random();
        StringBuilder token = new StringBuilder(16);
        for(int i=0;i<16;i++)
        {
            token.append(str.charAt(random.nextInt(strlen)));
        }
        return token.toString();
    }
}
