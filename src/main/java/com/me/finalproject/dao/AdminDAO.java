/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.me.finalproject.dao;

import com.me.finalproject.pojo.Admin;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author karnvadaliya
 */
@Repository
public class AdminDAO extends DAO {
    
    public boolean addAdmin(HttpServletRequest request, Admin adminDetails){
        String hqlQuery="FROM Admin WHERE uemail=:e";
        try{
            
             Query query=getSession().createQuery(hqlQuery);
             query.setParameter("e", adminDetails.getUemail());
             if(query.uniqueResult()!=null)
             {
                 return true;
             }
             else
             {
                 begin();
                 getSession().save(adminDetails);
                 commit();
                 return false;
             }
             
        }catch (HibernateException e) {
            getSession().getTransaction().rollback();
        } finally {
            getSession().close();
        } 
        return false;
      }
}
