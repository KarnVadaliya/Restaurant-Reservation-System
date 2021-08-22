/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.me.finalproject.interceptor;

import com.me.finalproject.pojo.Customer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 *
 * @author karnvadaliya
 */
public class FinalInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        HttpSession session = request.getSession();
        if(session.getAttribute("type")!=null)
        {
            String lType=(String) session.getAttribute("type");
            if(request.getRequestURI().contains(lType.toLowerCase()) && session.getAttribute(lType)!=null)
            {
                return true;
            }
        }
      
    
  
     response.sendRedirect("/FinalProject/");
     return false;

    }
}
