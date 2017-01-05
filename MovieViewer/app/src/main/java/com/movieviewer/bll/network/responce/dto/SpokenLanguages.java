package com.movieviewer.bll.network.responce.dto;

import java.io.Serializable;

public class SpokenLanguages implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3970213573002963717L;
	private String iso_639_1 = "";
	private String name = "";
	
	public SpokenLanguages() {}
	
	public SpokenLanguages(String iso_639_1, String name) {
		this.setIso_639_1(iso_639_1);
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getIso_639_1() {
		return iso_639_1;
	}

	public void setIso_639_1(String iso_639_1) {
		this.iso_639_1 = iso_639_1;
	}
}
