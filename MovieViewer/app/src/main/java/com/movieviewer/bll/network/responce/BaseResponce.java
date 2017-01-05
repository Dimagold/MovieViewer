package com.movieviewer.bll.network.responce;

import java.io.Serializable;

import com.movieviewer.bll.network.JsonParselable;


public abstract class BaseResponce<E> implements JsonParselable<E>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8879582251625013220L;

	public static enum STATUS {
		SUCCESSFUL, ERROR;
	}
	
	public transient STATUS status;
	
	public BaseResponce() {
		
	}

}
