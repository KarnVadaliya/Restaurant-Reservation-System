/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.me.finalproject.controller;

import com.me.finalproject.dao.RestaurantDAO;
import com.me.finalproject.pojo.Cuisine;
import com.me.finalproject.pojo.Login;
import com.me.finalproject.pojo.Restaurant;
import com.me.finalproject.validator.RestaurantValidator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author karnvadaliya
 */
@Controller
public class RestaurantController {
    @Autowired
    RestaurantValidator restaurantValidator;
    
    
    
    @PostMapping("/restaurant-register.htm")
    public ModelAndView registerCustomer(@ModelAttribute("restaurantDetails") @Validated Restaurant restaurantDetails, BindingResult result, ModelMap model, HttpServletRequest request, RestaurantDAO restaurantdao)
    {
        restaurantValidator.validate(restaurantDetails, result);
        String[] c=request.getParameterValues("rcuisines");
        if(result.hasErrors())
                return new ModelAndView("restaurantRegister");
        else
        {
            if(restaurantdao.addRestaurant(request,restaurantDetails,c))
            {
                request.setAttribute("error", "Restaurant already Exist ! Please Login");
                return new ModelAndView("restaurantRegister");
            }
            else
            {
                request.setAttribute("msg", "Account Created");
                model.addAttribute("loginDetails", new Login());
                return new ModelAndView("login");
            }
        }
    }
    
    
    
    @ModelAttribute("cuisineList")
    public List<Cuisine> getCuisineList(RestaurantDAO rd){
    List<Cuisine> cuisineList = rd.getAllCuisines();
    return cuisineList;
  }
    
  
    
}
