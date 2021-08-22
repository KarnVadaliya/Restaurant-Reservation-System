/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.me.finalproject.controller;

import com.me.finalproject.dao.AdminDAO;
import com.me.finalproject.dao.BookingDAO;
import com.me.finalproject.dao.RestaurantDAO;
import com.me.finalproject.pojo.Admin;
import com.me.finalproject.pojo.Booking;
import com.me.finalproject.pojo.Login;
import com.me.finalproject.pojo.Restaurant;
import com.me.finalproject.validator.AdminValidator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author karnvadaliya
 */
@Controller
public class AdminController {
    
    @Autowired
    AdminValidator adminValidator;
    
    
    @Value("${eusername}")
    private String eusername;
    
    @Value("${epassword}")
    private String epassword;
    
    @GetMapping("/admin/main.htm")
    public String getAdminMainPage()
    {
        return "adminMain";
    }
    
    @PostMapping("/admin-register.htm")
    public ModelAndView registerAdmin(@ModelAttribute("adminDetails") @Validated Admin adminDetails, BindingResult result, ModelMap model, HttpServletRequest request, AdminDAO admindao)
    {
        adminValidator.validate(adminDetails, result);
        if(result.hasErrors())
                return new ModelAndView("adminRegister");
        else
        {
            if(admindao.addAdmin(request,adminDetails))
            {
                request.setAttribute("error", "User already Exist ! Please Login");
                return new ModelAndView("adminRegister");
            }
            else
            {
                request.setAttribute("msg", "Account Created");
                model.addAttribute("loginDetails", new Login());
                return new ModelAndView("login");
            }
        }
    }
    
    @GetMapping("/admin/pending-requests.htm")
    public ModelAndView getPendingRequests(ModelMap mp, RestaurantDAO restaurantdao)
    {
        mp.addAttribute("errmsg", "");
        List<Restaurant> restaurants = restaurantdao.getPendingRestaurants();
        if(restaurants.size()==0)
            mp.addAttribute("errmsg", "No Pending Restaurant Registration Requests");
        
        mp.addAttribute("restaurants", restaurants);
        return new ModelAndView("pendingRestaurants","restaurants",restaurants);
    }
    
    @GetMapping("/admin/booking-information.htm")
    public String getBookingInfo(ModelMap mp)
    {
        mp.addAttribute("errmsg", "");
        mp.addAttribute("resultmsg", "");
        return "bookingInfo";
    }
    
    @PostMapping("/admin/booking-information.htm")
    public String findBookingDetails(HttpServletRequest request,ModelMap mp,BookingDAO bookingdao)
    {
        String bid = request.getParameter("bid");
        String errmsg="";
        String resultmsg="";
        long bookid;
        try{
            bookid=Long.parseLong(bid);
            Booking b = bookingdao.getBookingById(bookid);
            if(b==null)
            {
                resultmsg="No results found for given Booking ID";
                mp.addAttribute("resultmsg", resultmsg);
            }
            else
            {
                mp.addAttribute("booking", b);
            }
           
        }catch(NumberFormatException e)
        {
            errmsg="Please enter a numeric value for Booking ID";
        }
        mp.addAttribute("errmsg", errmsg);
        return "bookingInfo";
    }
    
    @GetMapping("admin/pending-requests/restaurantDetails.htm")
    public ModelAndView getRestuarantDetails(HttpServletRequest request, RestaurantDAO restaurantdao, ModelMap model)
    {
        long rid;
        try{
            rid=Long.parseLong(request.getParameter("restaurant"));
        }catch(NumberFormatException e)
        {
            return new ModelAndView("error");
        }
        Restaurant r = restaurantdao.getRestaurantById(rid);
        
        if(r!=null)
            return new ModelAndView("restaurantDetails","r",r);
        else
            return new ModelAndView("error");        
    }
    
    @PostMapping("admin/pending-requests/restaurantDetails/{myaction}.htm")
    public String takeAction(@PathVariable(value="myaction") String myaction, RestaurantDAO restaurantdao, HttpServletRequest request)
    {
        
        long rid;
        try{
            rid=Long.parseLong(request.getParameter("rid"));
        }catch(NumberFormatException e)
        {
            return "error";
        }
        
        Restaurant r = restaurantdao.getRestaurantById(rid);
        if(r!=null)
        {
            String remail=r.getUemail();
            String rname=r.getName();
                
            if(myaction.equals("decline"))
            {
                String reason = request.getParameter("reason");
     
                if(reason.equals(""))
                    reason="No reason provided";
                if(restaurantdao.deleteRestaurant(r))
                {
                    sendEmailToRestaurant("<p>Restaurant Name :"+ rname+"</p>"+ "<p>Your restaurant registration has been cancelled. \nReason : "+reason+"</p>"+"\n<p>For more information contact webtools6250@gmail.com </p>", remail);
                    return "adminConfirmation";
                }
            }
            else if(myaction.equals("accept"))
            {
                    r.setStatus("active");
                    if(restaurantdao.updateRestaurant(r))
                    {
                        sendEmailToRestaurant("<p>Restaurant Name :"+ rname+"</p>"+ "<p>Your restaurant is now active and start taking reservations</p>"+"\n<p>For more information contact webtools6250@gmail.com </p>", remail);
                        return "adminConfirmation";
                    }
            }
        }
 
        return "error";
    }
    
    
    public void sendEmailToRestaurant(String msg, String remail)
    {
        System.out.println(eusername+"here");
        Email email = new HtmlEmail();
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator(eusername, epassword));
        email.setSSLOnConnect(true);
         try {

                email.setFrom(eusername);
                email.setSubject("OpenRestaurant : Registration Update");
                email.setMsg(msg);
                email.addTo("karn.vadaliya@gmail.com");
                email.send();                        
        } catch (EmailException e) {
                e.printStackTrace();
        }
    }
    
    
    
}
