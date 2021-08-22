/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.me.finalproject.validator;

import com.me.finalproject.pojo.Login;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author karnvadaliya
 */
@Component
public class LoginValidator implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validate(Object o, Errors errors) {
        Login loginDetails = (Login) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.email", "Email is Empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pass", "error.pass", "Password is Empty");
        
//        if(xssCheck(loginDetails.getEmail()))
//        {
//            errors.rejectValue("email","error.email"," Malicious Script Detected");
//        }
//        
//        if(xssCheck(loginDetails.getPass()))
//        {
//            errors.rejectValue("pass","error.pass"," Malicious Script Detected");
//        }
        
    }
    
//    private boolean xssCheck(String value) {
//        if (value != null) {
//            return (value.matches("<script>(.*?)</script>") || value.matches("\"<script(.*?)>\""));
//        }
//        return false;
//    }
    
}
