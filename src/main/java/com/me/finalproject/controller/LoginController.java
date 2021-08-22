package com.me.finalproject.controller;

import com.me.finalproject.dao.CustomerDAO;
import com.me.finalproject.dao.LoginDAO;
import com.me.finalproject.dao.RestaurantDAO;
import com.me.finalproject.pojo.Admin;
import com.me.finalproject.pojo.Cuisine;
import com.me.finalproject.pojo.Customer;
import com.me.finalproject.pojo.Login;
import com.me.finalproject.pojo.Restaurant;
import com.me.finalproject.validator.LoginValidator;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	
        @Autowired
        LoginValidator loginValidator;
        
	@GetMapping("/login.htm")
	public String doGet(@RequestParam("type") String loginType,HttpServletRequest request,CustomerDAO customerdao,ModelMap model)
	{
                
		if(loginType!=null && (loginType.equals("Admin") || loginType.equals("Customer") || loginType.equals("Restaurant")))
		{
                        HttpSession session = request.getSession();
			session.setAttribute("type",loginType);
                        model.addAttribute("loginDetails", new Login());
			return "login";
		}	
		else
			return "error";
	}
        
        @GetMapping("/admin/abc.htm")
        public String ach()
        {
            return "error";
        }
        
        @PostMapping("/login.htm")
        public ModelAndView authenticate(@ModelAttribute("loginDetails") @Validated Login loginDetails, BindingResult result, Model model, HttpServletRequest request,HttpServletResponse response, LoginDAO logindao) throws ServletException, IOException
        {
            loginValidator.validate(loginDetails, result);
            if(result.hasErrors())
                return new ModelAndView("login");
            else
            {
                Object o=logindao.checkLogin(loginDetails, request);
                if(o==null)
                    result.rejectValue("email","error.email"," Invalid Email/Password");
                else
                {
                    
                    HttpSession session = request.getSession();
                    String lType=(String) session.getAttribute("type");
                    if(lType.equals("Restaurant"))
                    {
                        Restaurant r = (Restaurant) o;
                        if(r.getStatus().equals("pending"))
                        {
                             request.setAttribute("msg", "Pending Verification");
                             return new ModelAndView("login");
                        }
                    }
                    
                    session.setAttribute(lType ,o);
                    if(lType.equals("Customer"))
                    {
                        return new ModelAndView("redirect:customer/search-restaurant.htm"); 
                    }
                    else if(lType.equals("Restaurant"))
                    {
                        return new ModelAndView("redirect:restaurant/main.htm"); 
                    }
                    else
                    {
                        return new ModelAndView("redirect:admin/main.htm"); 
                    }
                }
            }
            return new ModelAndView("login");
        }
        
        @GetMapping("/register.htm")
        public String register(HttpServletRequest request, ModelMap model)
        {
            HttpSession session = request.getSession();
            String loginType=(String) session.getAttribute("type");
            if(loginType!=null && (loginType.equals("Admin") || loginType.equals("Customer") || loginType.equals("Restaurant")))
            {
               if(loginType.equals("Customer"))
               {
                   model.addAttribute("customerDetails",new Customer());
                   return "customerRegister";
               }  
               else if(loginType.equals("Restaurant"))
               {
                   model.addAttribute("restaurantDetails",new Restaurant());
                   return "restaurantRegister";
               }
               else
               {
                   model.addAttribute("adminDetails",new Admin());
                   return "adminRegister";
               }
                
            }	
            else
                return "error";
        }
        
        @ModelAttribute("cuisineList")
        public List<Cuisine> getCuisineList(RestaurantDAO rd){
        List<Cuisine> cuisineList = rd.getAllCuisines();
        return cuisineList;
        }
        
        @GetMapping("/logout.htm")
        public ModelAndView logoutUser(HttpServletRequest request)
        {
            HttpSession session = request.getSession();
            session.invalidate();
            return new ModelAndView("redirect:/");
        }
        
       
}