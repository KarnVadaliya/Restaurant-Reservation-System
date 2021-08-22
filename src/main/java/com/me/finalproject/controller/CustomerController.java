/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.me.finalproject.controller;

import com.me.finalproject.dao.BookingDAO;
import com.me.finalproject.dao.CustomerDAO;
import com.me.finalproject.dao.RestaurantDAO;
import com.me.finalproject.pojo.Booking;
import com.me.finalproject.pojo.Customer;
import com.me.finalproject.pojo.Login;
import com.me.finalproject.pojo.Restaurant;
import com.me.finalproject.validator.CustomerValidator;
import com.me.finalproject.view.Confirmation;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

/**
 *
 * @author karnvadaliya
 */
@Controller
public class CustomerController {
    
    @Autowired
    CustomerValidator customerValidator;
    
    
    @Value("${email.username}")
    private String eusername;
	
    
    @Value("${email.password}")
    private String epassword;
    
    @PostMapping("/customer-register.htm")
    public ModelAndView registerCustomer(@ModelAttribute("customerDetails") @Validated Customer customerDetails, BindingResult result, ModelMap model, HttpServletRequest request, CustomerDAO customerdao)
    {
        customerValidator.validate(customerDetails, result);
        if(result.hasErrors())
                return new ModelAndView("customerRegister");
        else
        {
            if(customerdao.addCustomer(request,customerDetails))
            {
                request.setAttribute("error", "User already Exist ! Please Login");
                return new ModelAndView("customerRegister");
            }
            else
            {
                request.setAttribute("msg", "Account Created");
                model.addAttribute("loginDetails", new Login());
                return new ModelAndView("login");
            }
        }
    }
    
    @GetMapping(value="/customer/search-restaurant.htm")
    public ModelAndView search(HttpServletRequest request)
    {
       return new ModelAndView("search");
    }
    
    
    @PostMapping(value = "/customer/search-restaurant.htm")
    public ModelAndView searchRestaurant(RestaurantDAO restaurantdao, HttpServletRequest request, ModelMap mp) throws ParseException 
    {
        
        String searchfield=(String) request.getParameter("searchfield");
        String searchby=(String) request.getParameter("searchby");
        String bookDate=(String) request.getParameter("bookDate");
        String bookTime=(String) request.getParameter("bookTime");
        String timeOfDay=(String) request.getParameter("timeOfDay");
        String peopleNumber=(String) request.getParameter("peopleNumber");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        
        int tod=0;
        int bt;
        try{
            bt=Integer.parseInt(bookTime);
            bt=bt%12;
            if(timeOfDay.equals("PM"))
            {
                bt=bt+12;
                tod=1;
            }
            
     
        }catch(NumberFormatException e)
        {
            return new ModelAndView("error");
        }
        
        if(!validDateAndTime(bookDate,bt))
            return new ModelAndView("error");
         
        List<Restaurant> finalRestaurants = new ArrayList<Restaurant>();
        List<Restaurant> lr = restaurantdao.searchRestaurants(searchfield, searchby, bt);
        for(Restaurant r : lr)
        {
           int count=0;
           for(Booking b:r.getBookings())
           {
               Calendar cal = Calendar.getInstance();
               cal.setTime(formatter.parse(formatter.format(b.getBookingDate())));
               cal.add(Calendar.DATE, 1);
               String dt=formatter.format(cal.getTime());
               
               if(dt.equals(bookDate) && b.getTimeSlot()==bt)
                   count++;
           }
           if(count<r.getTotaltable())
               finalRestaurants.add(r);
        }
        System.out.println(lr);
        
        int sb=0;
        if(searchby.equals("cuisine"))
            sb=1;
        else if(searchby.equals("name"))
            sb=2;
        
        
        mp.addAttribute("myBookDate", bookDate);
        mp.addAttribute("myBookTime", bt);
        mp.addAttribute("myPeople", peopleNumber);
        mp.addAttribute("s",finalRestaurants.size());
        request.setAttribute("bd", bookDate);
        request.setAttribute("bt", Integer.parseInt(bookTime)-1);
        request.setAttribute("peopl", Integer.parseInt(peopleNumber)-1);
        request.setAttribute("sb", sb);
        request.setAttribute("sf", searchfield);
        request.setAttribute("tod", tod);
        mp.addAttribute("rs",lr.size());

       
        return new ModelAndView("search","restaurants", finalRestaurants);
    }
    
    @PostMapping("/customer/book/confirmation.htm")
    public ModelAndView bookRestaurant(ModelMap mp, HttpServletRequest request, RestaurantDAO restaurantdao, BookingDAO bookingdao)
    {
        try{
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
           
            
            long rid = Long.parseLong(request.getParameter("restaurant"));
            Restaurant r = restaurantdao.getRestaurantById(rid);
            HttpSession session = request.getSession();
            Customer c = (Customer) session.getAttribute("Customer");
            
            String bookDate=request.getParameter("mydate");
            int bt=Integer.parseInt(request.getParameter("mytime"));
            int people=Integer.parseInt(request.getParameter("mypeople"));
            Date d1=formatter.parse(bookDate);
            
            if(!validDateAndTime(bookDate,bt))
                return new ModelAndView("error");
            
           int count=0;
           for(Booking b:r.getBookings())
           {
               Calendar cal = Calendar.getInstance();
               cal.setTime(formatter.parse(formatter.format(b.getBookingDate())));
               cal.add(Calendar.DATE, 1);
               String dt=formatter.format(cal.getTime());
               
               if(dt.equals(bookDate) && b.getTimeSlot()==bt)
                    count++;                                     
           }
           if(r.getTotaltable()<count)
              return new ModelAndView("error");
                     
        Booking booking = bookingdao.addBooking(c, r, d1, bt, people);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM d, yyyy");
        int t=booking.getTimeSlot();
        String tod="AM";

            if(t>=12)
            {
                tod="PM";
                if(t>12)
                    t=t-12;
            }
            if(t==0)
                t=12;
            
                Email email = new HtmlEmail();
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator("webtools6250@gmail.com", ""));
		email.setSSLOnConnect(true);
		try {
			
			email.setFrom("webtools6250@gmail.com");
			email.setSubject("OpenRestaurant : Reservation Confirmation");
			email.setMsg("<h1>Reservation Details<h1/>" +
						  "<h4>Booking Id: " +booking.getBookId()+ "</h4>" + 
						  "<h4>Booking Date: "+sdf.format(booking.getBookingDate())+ "</h4>" +
						  "<h4>Booking Time: " + t+" "+tod + "</h4>" +
						  "<h4>Customer Name: " +c.getName()+ "</h4>" +
						  "<h4>Customer Email: "+ c.getUemail()+ "</h4>" +
						  "<h4>Restaurant Name: " + r.getName() +"</h4>"+
						  "<h4>Restaurant Number: "+ r.getNumber()+ "</h4>" +
						  "<h4>Restaurant Address: "+"\n"+ r.getStreet()+ "\n" + r.getCity()+"\n"+r.getZipcode()+ "</h4>");
			email.addTo("karn.vadaliya@gmail.com");
			email.send();                        
		} catch (EmailException e) {
			e.printStackTrace();
		}
                Email email2 = new HtmlEmail();
		email2.setHostName("smtp.gmail.com");
		email2.setSmtpPort(465);
		email2.setAuthenticator(new DefaultAuthenticator("webtools6250@gmail.com", ""));
		email2.setSSLOnConnect(true);
                try{
                        email2.setFrom("webtools6250@gmail.com");
			email2.setSubject("OpenRestaurant : New Reservation");
			email2.setMsg("<h1>Reservation Details<h1/>" +
						  "<h4>Booking Id: " +booking.getBookId()+ "</h4>" + 
						  "<h4>Booking Date: "+sdf.format(booking.getBookingDate())+ "</h4>" +
						  "<h4>Booking Time: " + t+" "+tod + "</h4>" +
						  "<h4>Customer Name: " +c.getName()+ "</h4>" +
						  "<h4>Customer Email: "+ c.getUemail()+ "</h4>" +
						  "<h4>Customer Number: "+ c.getPhone()+ "</h4>"+
                                                  "<h4>Total People: "+ booking.getPeople()+ "</h4>");
			email2.addTo("vadaliya.k@northeastern.edu");
			email2.send();
                }catch (EmailException e) {
			e.printStackTrace();
		}
           
           View v = new Confirmation();
           return new ModelAndView(v,"booking",booking);
            
        }catch(NumberFormatException | ParseException e)
        {
            return new ModelAndView("error");
        }
     
        
    }

    private boolean validDateAndTime(String bookDate, int bt) throws ParseException {
        TimeZone.setDefault(TimeZone.getTimeZone("EST"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        
        Date tempDate=new Date();
        
        int hours=tempDate.getHours();
        
        tempDate=formatter.parse(formatter.format(tempDate));
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(tempDate);
        cal2.add(Calendar.DATE, 30);
        
        System.out.println(formatter.parse(bookDate));
        System.out.println(tempDate);
        System.out.println(hours);
        System.out.println(formatter.format(tempDate));
        System.out.println(cal2.getTime());
        
        if(formatter.parse(bookDate).before(tempDate) || formatter.parse(bookDate).after(cal2.getTime()))
        {
            return false;
        }
        
        if(bookDate.equals(formatter.format(tempDate)) && bt<=hours)
        {
            return false;
        }
        
        return true;
    }
    
        
    
    
    
    
   
    
    
}
