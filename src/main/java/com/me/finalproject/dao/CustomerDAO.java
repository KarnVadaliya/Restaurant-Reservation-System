package com.me.finalproject.dao;

import static com.me.finalproject.dao.DAO.getSession;
import com.me.finalproject.pojo.Customer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDAO extends DAO{
	
        
        public boolean addCustomer(HttpServletRequest request, Customer customerDetails){
        String hqlQuery="FROM Customer WHERE uemail=:e";
        try{
            
             Query query=getSession().createQuery(hqlQuery);
             query.setParameter("e", customerDetails.getUemail());
             if(query.uniqueResult()!=null)
             {
                 return true;
             }
             else
             {
                 begin();
                 getSession().save(customerDetails);
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
