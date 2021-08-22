package com.me.finalproject.pojo;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
public class Booking {
	
	@Id
	@Column(name="BookingID", unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long bookId;
	
	@Basic
	@Column(name="TotalPeople")
	private int people;
	
	
	@Basic
	@Column(name="Booking_Date")
	@Temporal(TemporalType.DATE)
	private Date bookingDate;
	
	@Basic
	@Column(name="TimeSlot")
	private int timeSlot;
	
	@ManyToOne
	@JoinColumn(name = "CustomerID")
	private Customer customers;
	
	@ManyToOne
	@JoinColumn(name = "RestaurantID")
	private Restaurant restaurants;
	
	public Booking() {
		
	}

	public long getBookId() {
		return bookId;
	}

	public void setBookId(long bookId) {
		this.bookId = bookId;
	}

	public int getPeople() {
		return people;
	}

	public void setPeople(int people) {
		this.people = people;
	}

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

        public int getTimeSlot() {
            return timeSlot;
        }

        public void setTimeSlot(int timeSlot) {
            this.timeSlot = timeSlot;
        }


        public Customer getCustomers() {
            return customers;
        }

        public void setCustomers(Customer customers) {
            this.customers = customers;
        }

	
	public Restaurant getRestaurants() {
		return restaurants;
	}

	public void setRestaurants(Restaurant restaurants) {
		this.restaurants = restaurants;
	}
	
	
	
	
}
