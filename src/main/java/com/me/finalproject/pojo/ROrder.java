package com.me.finalproject.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class ROrder {
	
	@Id
	@Column(name="ROrderID", unique = true, nullable = false)
	private long orderId;
	
	@Basic
	@Column(name="Tip")
	private int tip;
	
	@Basic
	@Column(name="Payment_Type")
	private String paymentType;
	
	@ManyToMany(mappedBy="orders")
	private Set<Item> items = new HashSet<Item>();
	
	public ROrder() {
		
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public int getTip() {
		return tip;
	}

	public void setTip(int tip) {
		this.tip = tip;
	}

	public Set<Item> getItems() {
		return items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}
	
	
}
