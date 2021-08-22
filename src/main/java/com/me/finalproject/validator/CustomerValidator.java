/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.me.finalproject.validator;

import com.me.finalproject.pojo.Customer;
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
public class CustomerValidator implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validate(Object o, Errors errors) {
        Customer customerDetails = (Customer) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "uemail", "error.uemail", "Email is Empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "upassword", "error.upassword", "Password is Empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cpassword", "error.cpassword", "Confirm Password is Empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "error.phone", "Phone Number is Empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.name", "Name is Empty");
        
//        if(xssCheck(customerDetails.getUemail()))
//        {
//            errors.rejectValue("uemail","error.uemail"," Malicious Script Detected");
//        }
//        
//        if(xssCheck(customerDetails.getUpassword()))
//        {
//            errors.rejectValue("upassword","error.upassword"," Malicious Script Detected");
//        }
//        
//        if(xssCheck(customerDetails.getCpassword()))
//        {
//            errors.rejectValue("cpassword","error.cpassword"," Malicious Script Detected");
//        }
//        
//       
//        
//        if(xssCheck(customerDetails.getName()))
//        {
//            errors.rejectValue("name","error.name"," Malicious Script Detected");
//        }
        
        Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE);
       if(!customerDetails.getUemail().equals(""))
       {
            if(!emailPattern.matcher(customerDetails.getUemail()).matches())
            {
                errors.rejectValue("uemail","error.uemail","Invalid Email");
            }
       }
       
        Pattern passwordPattern = Pattern.compile("(?=^.{8,}$)(?=.*\\d)(?=.*[!@#$%^&*]+)(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$",
            Pattern.CASE_INSENSITIVE);
        if(!customerDetails.getUpassword().equals(""))
        {
             if(!passwordPattern.matcher(customerDetails.getUpassword()).matches())
             {
                 errors.rejectValue("upassword","error.upassword","Password must contain minimum 8 characters with 1 Uppercase, 1 Lowercase, 1 Digit and 1 Special Character");
             }
        }
        
        if(!String.valueOf(customerDetails.getPhone()).matches("^\\d{10}$"))
        {
            errors.rejectValue("phone","error.phone", "Number should be 10 digit only");
        }
        
       
       if(!customerDetails.getUpassword().equals(customerDetails.getCpassword()))
       {
           errors.rejectValue("cpassword","error.cpassword", "Passwords do not match ");
       }
        
      
        
    }
    
//    private boolean xssCheck(String value) {
//        if (value != null) {
//            return (value.matches("<script>(.*?)</script>") || value.matches("\"<script(.*?)>\""));
//        }
//        return false;
//    }
    
}
