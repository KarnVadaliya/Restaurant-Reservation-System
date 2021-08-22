/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.me.finalproject.dao;

import static com.me.finalproject.dao.DAO.getSession;
import com.me.finalproject.pojo.Booking;
import com.me.finalproject.pojo.Cuisine;
import com.me.finalproject.pojo.Customer;
import com.me.finalproject.pojo.Restaurant;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

/**
 *
 * @author karnvadaliya
 */
@Repository
public class RestaurantDAO extends DAO {
    
    
    public List<Cuisine> getAllCuisines(){
        String hqlQuery="FROM Cuisine";
        try{
            Query query=getSession().createQuery(hqlQuery);
            return query.list();
        }catch (HibernateException e) {
            
        }
        return null;
    }
    
    public boolean addRestaurant(HttpServletRequest request, Restaurant restaurantDetails, String[] cuisines)
    {
        String hqlQuery="FROM Restaurant WHERE uemail=:e";
        try{
            
             Query query=getSession().createQuery(hqlQuery);
             query.setParameter("e", restaurantDetails.getUemail());
             if(query.uniqueResult()!=null)
             {
                 return true;
             }
             else
             {
                 begin();
                 Set<Cuisine> c = restaurantDetails.getCuisines();
                 if(cuisines!=null)
                 {
                     for(int i=0;i<cuisines.length;i++)
                     {
                         c.add(findCuisine(cuisines[i]));
                     }
                 }
                 restaurantDetails.setStatus("pending");
                 getSession().save(restaurantDetails);
                 commit();
                 return false;
             }
             
        }catch (HibernateException e) {
            getSession().getTransaction().rollback();
            return true;
        } finally {
            getSession().close();
        } 
    }
    
    public Cuisine findCuisine(String s)
    {
        try{
         String hqlQuery="FROM Cuisine WHERE cuisineName=:e";
         Query query=getSession().createQuery(hqlQuery);
         query.setParameter("e", s);
         return (Cuisine)query.uniqueResult();
        }catch (HibernateException e) {
        }
         return null;
    }
    
    
    
    
    public List searchRestaurants(String searchfield, String searchby, int bt) throws ParseException
    {
        try {
           if(searchby.equals("cuisine"))
                {
                    
                    Criteria crit = getSession().createCriteria(Restaurant.class, "res")
                                .createAlias("res.cuisines", "cuisine")
                                .add(Restrictions.ilike("cuisine.cuisineName", searchfield, MatchMode.START))
                                .add(Restrictions.and(Restrictions.le("res.openingtime", bt), Restrictions.gt("res.closingtime", bt)))
                                .add(Restrictions.eq("res.status", "active"));
                    
                    List results = crit.list();
                    return results;
                }
                else
                {
                    String s="res."+searchby;
                    Criteria crit = getSession().createCriteria(Restaurant.class, "res")
                                .add(Restrictions.ilike(s, searchfield, MatchMode.START))
                                .add(Restrictions.and(Restrictions.le("res.openingtime", bt), Restrictions.gt("res.closingtime", bt)))
                                .add(Restrictions.eq("res.status", "active"));
                                
                    List results = crit.list();
                    return results;
                }
                
               
			
		} catch(HibernateException e) {
			return null;
		} 
    }
    
    public Restaurant getRestaurantById(long rid)
    {
        Criteria crit = getSession().createCriteria(Restaurant.class);
		crit.add(Restrictions.eq("resId", rid));
		Restaurant r = (Restaurant) crit.uniqueResult();
		return r;
    }
    
    public boolean deleteRestaurant(Restaurant r)
    {		
        try {
            begin();
            getSession().delete(r);
            commit();
            return true;

        } catch(HibernateException e) {
            e.printStackTrace();
            rollback();
            return false;
        } finally {
            close();
        }
			
    }
    
    public boolean updateRestaurant(Restaurant r)
    {
        try {
            begin();
            getSession().update(r);
            commit();
            return true;

        } catch(HibernateException e) {
            e.printStackTrace();
            rollback();
            return false;
        } finally {
            close();
        }
    }
    
    public List getPendingRestaurants()
    {
        Criteria crit = getSession().createCriteria(Restaurant.class);
		crit.add(Restrictions.eq("status", "pending"));
        List results = crit.list();
        return results;     
    }
}
