package com.me.finalproject.pojo;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import org.springframework.format.annotation.NumberFormat;

@Entity
public class Customer {
	@Id
	@Column(name="CustomerID", unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long customerId;
	
	@Basic
	@Column(name="Email")
	private String uemail;
	
	@Basic
	@Column(name="Password")
	private String upassword;
	
	@Basic
	@Column(name="Name")
	private String name;
	
	@Basic
	@Column(name="Phone")
	private long phone;
        
        @Transient
        private String cpassword;
        
	@OneToMany(mappedBy="customers")
	private List<Booking> bookings=new ArrayList<Booking>();
	
	public Customer() {
		
	}

        public long getCustomerId() {
            return customerId;
        }

        public void setCustomerId(long customerId) {
            this.customerId = customerId;
        }

	public String getUemail() {
		return uemail;
	}

	public void setUemail(String uemail) {
		this.uemail = uemail;
	}

	public String getUpassword() {
		return upassword;
	}

	public void setUpassword(String upassword) {
		this.upassword = upassword;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

        public long getPhone() {
            return phone;
        }

        public void setPhone(long phone) {
            this.phone = phone;
        }

	public String getCpassword() {
            return cpassword;
        }

        public void setCpassword(String cpassword) {
            this.cpassword = cpassword;
        }
	
	
}
