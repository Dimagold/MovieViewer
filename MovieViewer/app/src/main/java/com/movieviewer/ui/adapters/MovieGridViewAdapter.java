package com.movieviewer.ui.adapters;

import java.util.List;

import com.movieviewer.bll.network.responce.dto.MovieMetaData;
import com.movieviewer.ui.views.MovieMetaDataItem;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MovieGridViewAdapter extends BaseAdapter {

	private static final String TAG = MovieGridViewAdapter.class.getSimpleName();

    protected Context context;
    private List<MovieMetaData> data = null;
    private OnMovieGridViewAdapterListener onMovieGridViewAdapterListener = null;
    
    public MovieGridViewAdapter(Context context, List<MovieMetaData> data) {
    	
    	this.data = data;
    	this.context = context;
    } 

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	final MovieMetaData data = (MovieMetaData) this.getItem(position);
    	
    	if(convertView == null) {
    		convertView = new MovieMetaDataItem(context);
    		final int itemPosition = position;
    		convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(onMovieGridViewAdapterListener != null) {
						onMovieGridViewAdapterListener.onMoviewItemClicked(itemPosition, data);
					}
				}
			});
    	}
    	
    	((MovieMetaDataItem) convertView).setDisplayData(data);
    	
        return convertView;
    }

    public List<MovieMetaData> getMovieList() {
        return data;
    }

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return (data != null) ? data.get(position) : null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public OnMovieGridViewAdapterListener getOnMovieGridViewAdapterListener() {
		return onMovieGridViewAdapterListener;
	}

	public void setOnMovieGridViewAdapterListener(
			OnMovieGridViewAdapterListener onMovieGridViewAdapterListener) {
		this.onMovieGridViewAdapterListener = onMovieGridViewAdapterListener;
	}

	public static interface OnMovieGridViewAdapterListener {
		public void onMoviewItemClicked(int position, MovieMetaData movieMetaData);
	}
}
