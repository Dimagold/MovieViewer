package com.movieviewer.bll.network.responce.dto;

import java.io.Serializable;

public class ProductionCompany implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8749692763790798251L;
	private String name = "";
	private int id = 0;
	
	public ProductionCompany() {}
	
	public ProductionCompany(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
