package com.movieviewer.ui.views;

import com.movieviewer.R;
import com.movieviewer.bll.network.request.BaseRequest;
import com.movieviewer.bll.network.responce.dto.MovieMetaData;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MovieMetaDataItem extends RelativeLayout {

	private static final int DISPLAY_LAYOUT = R.layout.movie_metadata;
	
	private MovieMetaData displayData;
	private ImageView moviePoster;
	private TextView title;
	
	public MovieMetaDataItem(Context context) {
		super(context);
		initiate();
	}
	
	private void initiate() {
		LayoutInflater inflater = LayoutInflater.from(getContext());
		inflater.inflate(DISPLAY_LAYOUT, this, true);
		
		displayData = new MovieMetaData();
		moviePoster = (ImageView) findViewById(R.id.imageViewPoster);
		title = (TextView) findViewById(R.id.textViewTitle);
	}
	
	public void setDisplayData(MovieMetaData displayData) {
		this.displayData = displayData;
		this.title.setText(displayData.getTitle());
		Picasso.with(getContext())
			.load(BaseRequest.MOVIE_POSTER_BASE_URL + displayData.getPoster_path())
			.placeholder(R.drawable.loading_animation)
			.into(moviePoster);
	}

}
