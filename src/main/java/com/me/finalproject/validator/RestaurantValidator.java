/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.me.finalproject.validator;

import com.me.finalproject.pojo.Restaurant;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author karnvadaliya
 */
@Component
public class RestaurantValidator implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validate(Object o, Errors errors) {
        Restaurant restaurantDetails = (Restaurant) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "uemail", "error.uemail", "Email is Empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "upassword", "error.upassword", "Password is Empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cpassword", "error.cpassword", "Confirm Password is Empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "number", "error.number", "Phone Number is Empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.name", "Name is Empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "street", "error.street", "Street is Empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "error.city", "City is Empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "zipcode", "error.zipcode", "Zip code is Empty");
        
        
//        if(xssCheck(restaurantDetails.getUemail()))
//        {
//            errors.rejectValue("uemail","error.uemail"," Malicious Script Detected");
//        }
//        
//        if(xssCheck(restaurantDetails.getUpassword()))
//        {
//            errors.rejectValue("upassword","error.upassword"," Malicious Script Detected");
//        }
//        
//        if(xssCheck(restaurantDetails.getCpassword()))
//        {
//            errors.rejectValue("cpassword","error.cpassword"," Malicious Script Detected");
//        }
//        
//        if(xssCheck(restaurantDetails.getName()))
//        {
//            errors.rejectValue("name","error.name"," Malicious Script Detected");
//        }
//        
//        
//        if(xssCheck(restaurantDetails.getCity()))
//        {
//            errors.rejectValue("city","error.city"," Malicious Script Detected");
//        }
//        
//        
//        if(xssCheck(restaurantDetails.getStreet()))
//        {
//            errors.rejectValue("street","error.street"," Malicious Script Detected");
//        }
        
        
        
        
        Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE);
       if(!restaurantDetails.getUemail().equals(""))
       {
            if(!emailPattern.matcher(restaurantDetails.getUemail()).matches())
            {
                errors.rejectValue("uemail","error.uemail","Invalid Email");
            }
       }
       
        Pattern passwordPattern = Pattern.compile("(?=^.{8,}$)(?=.*\\d)(?=.*[!@#$%^&*]+)(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$",
            Pattern.CASE_INSENSITIVE);
        if(!restaurantDetails.getUpassword().equals(""))
        {
             if(!passwordPattern.matcher(restaurantDetails.getUpassword()).matches())
             {
                 errors.rejectValue("upassword","error.upassword","Password must contain minimum 8 characters with 1 Uppercase, 1 Lowercase, 1 Digit and 1 Special Character");
             }
        }
        
        
        if(restaurantDetails.getTotaltable()<=0)
        {
            errors.rejectValue("totaltable","error.totaltable", "Total Tables should be greater than 0");
        }
        
        if(!String.valueOf(restaurantDetails.getNumber()).matches("^\\d{10}$"))
        {
            errors.rejectValue("number","error.number", "Number should be 10 digits");
        }
        
        if(restaurantDetails.getOpeningtime()>=restaurantDetails.getClosingtime())
        {
            errors.rejectValue("closingtime","error.closingtime", "Closing time cannot be before opening time");
        }
       
       if(!restaurantDetails.getUpassword().equals(restaurantDetails.getCpassword()))
       {
           errors.rejectValue("cpassword","error.cpassword", "Passwords do not match ");
       }
        
       if(!String.valueOf(restaurantDetails.getZipcode()).matches("^\\d{5}$"))
       {
           errors.rejectValue("zipcode","error.zipcode", "Zipcode should be a 5 digit number");
       }
      
        
    }
    
//    private boolean xssCheck(String value) {
//        if (value != null) {
//            return (value.matches("<script>(.*?)</script>") || value.matches("\"<script(.*?)>\""));
//        }
//        return false;
//    }
    
}
