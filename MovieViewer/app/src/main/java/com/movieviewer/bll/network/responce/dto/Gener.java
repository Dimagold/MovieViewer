package com.movieviewer.bll.network.responce.dto;

import java.io.Serializable;
import java.util.List;

public class Gener implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7121258782537309678L;
	private int id;
	private String name;
	
	public Gener() {
		this.id = 0;
		this.name = "";
	}
	
	public Gener(int id, String name) {
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
	
	public static final String toString(List<Gener> geners) {
		StringBuilder strBuilder = new StringBuilder();
		for(Gener g : geners) {
			strBuilder.append(g.getName()).append(", ");
		}
		return strBuilder.toString();
	}
}
