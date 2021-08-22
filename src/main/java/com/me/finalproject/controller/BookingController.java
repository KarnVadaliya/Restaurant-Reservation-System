/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.me.finalproject.controller;

import com.me.finalproject.dao.BookingDAO;
import com.me.finalproject.pojo.Booking;
import com.me.finalproject.pojo.Customer;
import com.me.finalproject.pojo.Restaurant;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author karnvadaliya
 */
@Controller
public class BookingController {
    
    @GetMapping("/customer/mybookings.htm")
    public ModelAndView getBookings()
    {
        return new ModelAndView("customerBookings");
    }
    
    
    @GetMapping("/customer/mybookings/{bookingValue}.htm")
    public ModelAndView getCurrentBookings(@PathVariable(value="bookingValue") String bookingValue, BookingDAO bookingdao, HttpServletRequest request, ModelMap mp) throws ParseException
    {
        
        if(!bookingValue.equals("current") && !bookingValue.equals("past"))
        {
            return new ModelAndView("error");
        }
        
        
        TimeZone.setDefault(TimeZone.getTimeZone("EST"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        HttpSession session = request.getSession();
        Customer c = (Customer) session.getAttribute("Customer");
        Date currDate = new Date();
        List<Booking> bookings = bookingdao.getBookings(c.getCustomerId());
        int currHours=currDate.getHours();
        List<Booking> results = new ArrayList<>();
        List<Booking> results2 = new ArrayList<>();
        
        for(Booking b:bookings)
        {
            Calendar cal = Calendar.getInstance();
            cal.setTime(formatter.parse(formatter.format(b.getBookingDate())));
            cal.add(Calendar.DATE, 1);
            currDate=formatter.parse(formatter.format(currDate));
            b.setBookingDate(formatter.parse(formatter.format(cal.getTime())));
            if(cal.getTime().compareTo(currDate)==0)
            {
                if(b.getTimeSlot()>currHours)
                    results.add(b);
                else
                    results2.add(b);
            }
            else if(cal.getTime().after(currDate))
            {
                results.add(b);
            }
            else
            {
                results2.add(b);
            }
        }
        
        
        if(bookingValue.equals("current"))
            mp.put("mybookings", results);
        else
            mp.put("mybookings", results2);
       
        request.setAttribute("typeofbooking",bookingValue);
        return new ModelAndView("customerBookings");
    }
    
    @PostMapping("customer/mybookings/cancel.htm")
    public ModelAndView cancelBooking(BookingDAO bookingdao, HttpServletRequest request, ModelMap mp)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM d, yyyy");
        long bookingID=Long.parseLong(request.getParameter("bookingid"));
        HttpSession session = request.getSession();
        Customer c = (Customer) session.getAttribute("Customer");
        if(!bookingdao.checkValidCustomerBooking(c.getCustomerId(),bookingID))
            return new ModelAndView("error");
        
        Booking b = bookingdao.getBookingById(bookingID);
        
        if(!bookingdao.removeBooking(bookingID))
            return new ModelAndView("error");
        
        int t=b.getTimeSlot();
        String tod="AM";

            if(t>12)
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
                email.setSubject("OpenRestaurant : Reservation Cancellation");
                email.setMsg("<h1>Reservation Details<h1/>" +
                                          "<h4>Booking Id: " +b.getBookId()+ "</h4>" + 
                                          "<h4>Booking Date: "+sdf.format(b.getBookingDate())+ "</h4>" +
                                          "<h4>Booking Time: " + t+" "+tod + "</h4>" +
                                          "<h4>Customer Name: " +c.getName()+ "</h4>" +
                                          "<h4>Customer Email: "+ c.getUemail()+ "</h4>"+
                                          "<h4>Customer Number: "+ c.getPhone()+ "</h4>"+
                                          "<h4>Restaurant Name: "+ b.getRestaurants().getName()+ "</h4>");
                
                email.addTo("karn.vadaliya@gmail.com");
                email.send();                        
        } catch (EmailException e) {
                e.printStackTrace();
        }
        
        
        
        
        return new ModelAndView("cancelConfirmation");
        
    }
            
}
