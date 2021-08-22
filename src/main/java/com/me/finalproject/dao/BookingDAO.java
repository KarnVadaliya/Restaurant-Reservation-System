/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.me.finalproject.dao;

import static com.me.finalproject.dao.DAO.getSession;
import com.me.finalproject.pojo.Booking;
import com.me.finalproject.pojo.Customer;
import com.me.finalproject.pojo.Restaurant;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author karnvadaliya
 */
@Repository
public class BookingDAO extends DAO {
    
    public Booking addBooking(Customer c ,Restaurant r, Date bookDate, int bt, int people) {
			Booking b = new Booking();
			try {
				b.setBookingDate(bookDate);
                                b.setPeople(people);
                                b.setTimeSlot(bt);
                                b.setCustomers(c);
                                b.setRestaurants(r);
                               
				 
				Session session = getSession();
				Transaction t = session.beginTransaction();	
				session.save(b);
				t.commit();
				
				return b;
			} catch(HibernateException e) {
				getSession().getTransaction().rollback();
				return null;
			} finally {
				close();
			}
			
	}
    
    public List<Booking> getBookings(long cid)
    {
        try{
             Criteria crit = getSession().createCriteria(Booking.class,"b")
                                .createAlias("b.customers", "c")
                                .add(Restrictions.eq("c.customerId", cid));
                    
                    List<Booking> results = crit.list();
                    return results;
            
        }catch(HibernateException e) {
			return null;
		} 
    }
    
    public boolean checkValidCustomerBooking(long cid, long bid)
    {
        try{
            Criteria crit = getSession().createCriteria(Booking.class,"b")
                                .createAlias("b.customers", "c")
                                .add(Restrictions.and(Restrictions.eq("c.customerId", cid),Restrictions.eq("b.bookId", bid)));
            List<Booking> results = crit.list();
            return (results.size()==1)?true:false;
        }catch(HibernateException e) {
                        return false;
		}
    }
    
    
    public Booking getBookingById(long bid)
    {
        String hqlQuery="From Booking WHERE bookId=:e";
        try{
            Query query=getSession().createQuery(hqlQuery);
            query.setParameter("e", bid);
            Booking b = (Booking) query.uniqueResult();
            return b;
        }catch(HibernateException e) {
			getSession().getTransaction().rollback();
		} finally {
                    close();
                }
        return null;
    }
    
    
    public boolean removeBooking(long bid)
    {
        String hqlQuery="DELETE Booking WHERE bookId=:e";
        try{
            Query query=getSession().createQuery(hqlQuery);
            query.setParameter("e", bid);
            begin();
            int result = query.executeUpdate();
            commit();
            return (result==1)?true:false;
        }catch(HibernateException e) {
			getSession().getTransaction().rollback();
                        return false;
		} finally {
                    close();
                }
    }
    
    
}
