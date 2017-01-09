package com.movieviewer;

import android.os.Bundle;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.movieviewer.bll.data.db.DBStorageUtil;
import com.movieviewer.bll.network.request.BaseRequest;
import com.movieviewer.bll.network.responce.GetMovieDetailsResponce;
import com.movieviewer.bll.network.responce.dto.Gener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Picasso.LoadedFrom;

/**
 * A fragment representing a single MovieItem detail screen. This fragment is
 * either contained in a {@link MovieMainActivity} in two-pane mode (on
 * tablets) or a {@link MovieItemDetailActivity} on handsets.
 */
public class MovieItemDetailFragment extends Fragment {

	public static final String ARG_ITEM_ID = "item_id";
	private GetMovieDetailsResponce displayItem;
	
	private ImageView moviePoster;
	private TextView movieTitle;
	private TextView movieDate;
	private TextView movieDuration;
	private TextView movieRating;
	private TextView movieDescription;
	private TextView movieGeners;
	

	public MovieItemDetailFragment() {}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_movieitem_detail,
				container, false);
		
		this.moviePoster = (ImageView) rootView.findViewById(R.id.imageViewPoster);
		this.movieDescription = (TextView) rootView.findViewById(R.id.textViewDescription);
		this.movieTitle = (TextView) rootView.findViewById(R.id.textViewMovieName);
		this.movieDate = (TextView) rootView.findViewById(R.id.textViewDate);
		this.movieDuration = (TextView) rootView.findViewById(R.id.textViewDuration);
		this.movieRating = (TextView) rootView.findViewById(R.id.textViewRating);
		this.movieGeners = (TextView) rootView.findViewById(R.id.TextViewGeners);
		
		return rootView;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setDisplayData(displayItem);
	}
	
	public void setDisplayData(final GetMovieDetailsResponce data) {
		if(data != null) {
			if(movieDate != null) movieDate.setText(data.getRelease_date());
			if(movieDescription != null) movieDescription.setText(data.getOverview());
			if(movieTitle != null) movieTitle.setText(data.getOriginal_title());
			if(movieDuration != null) movieDuration.setText(data.getRuntime() + "");
			if(movieGeners != null) movieGeners.setText(Gener.toString(data.getGenres()));
			if(movieRating != null) movieRating.setText(data.getVote_average() + "/10");
			if(moviePoster != null) {
				Picasso.with(getActivity())
					.load(BaseRequest.MOVIE_POSTER_BASE_URL + data.getPoster_path())
					.placeholder(R.drawable.loading_animation)
					.into(new Target() {
						
						@Override
						public void onPrepareLoad(Drawable arg0) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onBitmapLoaded(Bitmap arg0, LoadedFrom arg1) {
							moviePoster.setBackground(new BitmapDrawable(getResources(), arg0));
							data.setOfflinePoster(arg0);
							DBStorageUtil.insertMovieDetail(getActivity(), data);
						}
						
						@Override
						public void onBitmapFailed(Drawable arg0) {
							// TODO Auto-generated method stub
							
						}
					});
			}
		}
	}
	
	
}
