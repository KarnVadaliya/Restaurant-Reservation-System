/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.me.finalproject.dao;

import com.me.finalproject.pojo.Login;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author karnvadaliya
 */
@Repository
public class LoginDAO extends DAO{
    
    public Object checkLogin(Login loginDetails, HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        String lType=(String) session.getAttribute("type");
        String hqlQuery="FROM "+lType+" WHERE uemail=:ue AND upassword=:up";
        try{
            begin();
            Query query=getSession().createQuery(hqlQuery);
            query.setParameter("ue", loginDetails.getEmail());
            query.setParameter("up", loginDetails.getPass());
            commit();
            return query.uniqueResult();
        }catch (HibernateException e) {
        }
        return null;   
    }
}
