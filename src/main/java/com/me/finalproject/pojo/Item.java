package com.me.finalproject.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Item {
	@Id
	@Column(name="ItemID", unique = true, nullable = false)
	private long itemId;
	
	@Basic
	@Column(name="Name")
	private String itemName;
	
	@Basic
	@Column(name="Price")
	private int price;
	
	@ManyToMany
	@JoinTable(name = "Item_Order",
	joinColumns = { @JoinColumn(name = "ItemID") },
	inverseJoinColumns = { @JoinColumn(name = "OrderID") })
	private Set<ROrder> orders = new HashSet<ROrder>();
	
	public Item() {
		
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Set<ROrder> getOrders() {
		return orders;
	}

	public void setOrders(Set<ROrder> orders) {
		this.orders = orders;
	}

	
	
	
	
	
}
