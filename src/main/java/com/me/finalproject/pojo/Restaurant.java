package com.me.finalproject.pojo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class Restaurant {
	
	
	@Id
	@Column(name="RestaurantID", unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long resId;
	
	@Basic
	@Column(name="Name")
	private String name;
	
	@Basic
	@Column(name="Number")
	private long number;
	
	@Basic
	@Column(name="TotalTables")
	private int totaltable;
	
	@Basic
	@Column(name="Email",unique = true)
	private String uemail;
	
	@Basic
	@Column(name="Password")
	private String upassword;
        
        @Transient
        private String cpassword;
	
	@Basic
	@Column(name="Street")
	private String street;
	
	@Basic
	@Column(name="City")
	private String city;
	
	@Basic
	@Column(name="Zipcode")
	private int zipcode;
        
        @Basic
	@Column(name="Opening_Time")
	private int openingtime;
        
        @Basic
	@Column(name="Closing_Time")
	private int closingtime;
        
        @Basic
        @Column(name="Status")
	private String status;

        @ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name = "Restaurant_Cuisine",
	joinColumns = { @JoinColumn(name = "RestaurantID") },
	inverseJoinColumns = { @JoinColumn(name = "CuisineID") })
	private Set<Cuisine> cuisines = new HashSet<Cuisine>();
	
        @Fetch(FetchMode.SELECT)
	@OneToMany(fetch = FetchType.EAGER, mappedBy="restaurants")
	private List<Booking> bookings=new ArrayList<Booking>();
	
	
	public Restaurant() {
		
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public long getResId() {
		return resId;
	}

	public void setResId(long resId) {
		this.resId = resId;
	}

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getNumber() {
		return number;
	}


	public void setNumber(long number) {
		this.number = number;
	}
        
        public int getOpeningtime() {
                return openingtime;
        }

        public void setOpeningtime(int openingtime) {
            this.openingtime = openingtime;
        }

        public int getClosingtime() {
            return closingtime;
        }

        public void setClosingtime(int closingtime) {
            this.closingtime = closingtime;
        }
        
	public int getTotaltable() {
		return totaltable;
	}

	public void setTotaltable(int totaltable) {
		this.totaltable = totaltable;
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
        
        public String getCpassword() {
        return cpassword;
        }

        public void setCpassword(String cpassword) {
            this.cpassword = cpassword;
        }
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getZipcode() {
		return zipcode;
	}

	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}

	public Set<Cuisine> getCuisines() {
		return cuisines;
	}

	public void setCuisines(Set<Cuisine> cuisines) {
		this.cuisines = cuisines;
	}
	
	
	
	
}
