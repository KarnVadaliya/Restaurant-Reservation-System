/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.me.finalproject.validator;

import com.me.finalproject.pojo.Admin;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author karnvadaliya
 */
@Component
public class AdminValidator implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validate(Object o, Errors errors) {
        Admin adminDetails = (Admin) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "uemail", "error.uemail", "Email is Empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "upassword", "error.upassword", "Password is Empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cpassword", "error.cpassword", "Confirm Password is Empty");
        
        Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE);
       if(!adminDetails.getUemail().equals(""))
       {
            if(!emailPattern.matcher(adminDetails.getUemail()).matches())
            {
                errors.rejectValue("uemail","error.uemail","Invalid Email");
            }
       }
       
        Pattern passwordPattern = Pattern.compile("(?=^.{8,}$)(?=.*\\d)(?=.*[!@#$%^&*]+)(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$",
            Pattern.CASE_INSENSITIVE);
        if(!adminDetails.getUpassword().equals(""))
        {
             if(!passwordPattern.matcher(adminDetails.getUpassword()).matches())
             {
                 errors.rejectValue("upassword","error.upassword","Password must contain minimum 8 characters with 1 Uppercase, 1 Lowercase, 1 Digit and 1 Special Character");
             }
        }
        
       if(!adminDetails.getUpassword().equals(adminDetails.getCpassword()))
       {
           errors.rejectValue("cpassword","error.cpassword", "Passwords do not match ");
       }
    }
    
}
