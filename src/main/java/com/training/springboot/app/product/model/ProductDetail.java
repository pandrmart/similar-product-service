package com.training.springboot.app.product.model;

import java.io.Serializable;

/**
 * This pojo is used for binding JSON data obtained via REST API.
 * 
 * @author Pablo
 *
 */
public class ProductDetail implements Serializable {

	private String id;
	private String name;
	private Double price;
	private Boolean availability;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Boolean getAvailability() {
		return availability;
	}

	public void setAvailability(Boolean availability) {
		this.availability = availability;
	}

	private static final long serialVersionUID = 6344733744853430765L;

}
