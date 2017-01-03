package com.movieviewer.bll.network.responce.dto;

import java.io.Serializable;

public class ProductionCountries implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 553373419769850411L;
	private String iso_3166_1 = "";
	private String name = "";
	
	public ProductionCountries() {}
	
	public ProductionCountries(String iso_3166_1, String name) {
		this.iso_3166_1 = iso_3166_1;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getIso_3166_1() {
		return iso_3166_1;
	}

	public void setIso_3166_1(String iso_3166_1) {
		this.iso_3166_1 = iso_3166_1;
	}
}
