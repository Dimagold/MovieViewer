package com.movieviewer.bll.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import android.os.Environment;

import com.movieviewer.bll.network.responce.GetMovieDetailsResponce;
import com.movieviewer.bll.network.responce.GetPopularMoviesResponce;

/***
 * Wrapper class for binary files I/O. This class implementing internal storage
 * of application for offline usage.
 * 
 * @author Dima Goldenberg
 *
 */
public class InternalDataStorage {
	
	private static InternalDataStorage instance = null;
	private static final String POPULAR_MOVIES_STORAGE_FILE = "pop_mov.bin";
	private static final String MOVIES_DETAILS_STORAGE_FILE = "mov_details.bin";
	private static final String BASE_STORAGE_DIRECTORY = 
			Environment.getExternalStorageDirectory().getPath();
	
	private InternalDataStorage() {}
	
	public static synchronized InternalDataStorage getInstance() {
		if(instance == null) {
			instance = new InternalDataStorage();
		}
		return instance;
	}
	
	
	/**
	 * Must be implemented with one of the following threading approaches:
	 * AsynchTask, Runnable, Callable, etc.
	 * 
	 * @param rtHolder
	 */
	public void loadInternalData(RuntimeDataHolder rtHolder) {
		HashMap<Integer, GetPopularMoviesResponce> popularMovies = read(POPULAR_MOVIES_STORAGE_FILE);
		HashMap<Integer, GetMovieDetailsResponce> moviesDetails = read(MOVIES_DETAILS_STORAGE_FILE);
		
		rtHolder.setPopularMovies(popularMovies);
		rtHolder.setMoviesDetails(moviesDetails);
		rtHolder.setCurrentMoviesPage(0);
	}
	
	/**
	 * Must be implemented with one of the following threading approaches:
	 * AsynchTask, Runnable, Callable, etc.
	 * 
	 * @param rtHolder
	 */
	public void saveInternalData(RuntimeDataHolder rtHolder) {
		write(rtHolder.getPopularMovies(), POPULAR_MOVIES_STORAGE_FILE);
		write(rtHolder.getMoviesDetails(), MOVIES_DETAILS_STORAGE_FILE);
	}
	
	private <K,V> HashMap<K, V> read(String fileToRead) {
		HashMap<K, V> map = null;
		try{
	        File toRead = new File(BASE_STORAGE_DIRECTORY + "/" + fileToRead);
	        FileInputStream fis = new FileInputStream(toRead);
	        ObjectInputStream ois = new ObjectInputStream(fis);

	        HashMap<K, V> mapInFile = (HashMap<K, V>)ois.readObject();

	        map = mapInFile;
	        ois.close();
    
	        
	    } catch(Exception e){}
		
		return map;
	}
	
	private <K, V> void write(HashMap<K, V> data, String fileToWrite) {
		
		try{
		    File fileOne = new File(BASE_STORAGE_DIRECTORY + "/" + fileToWrite);
		    FileOutputStream fos = new FileOutputStream(fileOne);
		    ObjectOutputStream oos = new ObjectOutputStream(fos);

		    oos.writeObject(data);
		    oos.flush();
		    oos.close();
		    fos.close();
		} catch(Exception e){}
	}
	
	
}
