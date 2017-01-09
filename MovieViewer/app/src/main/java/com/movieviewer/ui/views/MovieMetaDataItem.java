package com.movieviewer.ui.views;

import com.movieviewer.R;
import com.movieviewer.bll.data.db.DBStorageUtil;
import com.movieviewer.bll.network.request.BaseRequest;
import com.movieviewer.bll.network.responce.dto.MovieMetaData;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Picasso.LoadedFrom;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
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
	
	public void setDisplayData(final MovieMetaData displayData) {
		this.displayData = displayData;
		this.title.setText(displayData.getTitle());
		Picasso.with(getContext())
			.load(BaseRequest.MOVIE_POSTER_BASE_URL + displayData.getPoster_path())
			.placeholder(R.drawable.loading_animation)
			.noFade()
			.into(picassoTarget);
	}
	
	private Target picassoTarget = new Target() {
		
		@Override
		public void onPrepareLoad(Drawable arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onBitmapLoaded(final Bitmap arg0, LoadedFrom arg1) {
			moviePoster.setImageDrawable(new BitmapDrawable(getResources(), arg0));
			displayData.setOfflineBitmap(arg0);
			DBStorageUtil.insertMovieMetaDataItem(getContext(), displayData);
		}
		
		@Override
		public void onBitmapFailed(Drawable arg0) {
			// TODO Auto-generated method stub
			
		}
	};

}
