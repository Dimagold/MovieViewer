package com.movieviewer;

import com.movieviewer.bll.data.db.DBStorageUtil;
import com.movieviewer.bll.network.RESTLoader.HTTPVerb;
import com.movieviewer.bll.network.RESTLoader.RESTResponce;
import com.movieviewer.bll.network.request.GetMovieDetailsRequest;
import com.movieviewer.bll.network.request.GetPopularMoviesRequest;
import com.movieviewer.bll.network.request.RequestLanguage;
import com.movieviewer.bll.network.responce.GetMovieDetailsResponce;
import com.movieviewer.bll.network.responce.GetPopularMoviesResponce;

import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.app.Activity;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * An activity representing a single MovieItem detail screen. This activity is
 * only used on handset devices. On tablet-size devices, item details are
 * presented side-by-side with a list of items in a
 * {@link MovieMainActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link MovieItemDetailFragment}.
 */
public class MovieItemDetailActivity extends BaseActivity {

	private MovieItemDetailFragment movieItemDetailFragment;
	private int movieId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movieitem_detail);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		int movieId = Integer.parseInt(getIntent().getStringExtra(
						MovieItemDetailFragment.ARG_ITEM_ID));
				
		loadMovieDetails(movieId);
		
		movieItemDetailFragment = new MovieItemDetailFragment();
		getFragmentManager().beginTransaction()
				.add(R.id.movieitem_detail_container, movieItemDetailFragment).commit();
	}

	private void loadMovieDetails(int movieId) {
		Toast.makeText(getApplicationContext(), "Loading", Toast.LENGTH_LONG).show();
		
		if(MovieViewer.isOnline) {
			getLoaderManager().initLoader(LOADER_GET_MOVIE_DETAIL,
				bundleForLoader(new GetMovieDetailsRequest(RequestLanguage.ENG, movieId), true, HTTPVerb.GET), this);
		} else {
			movieItemDetailFragment.setDisplayData(DBStorageUtil.retriveMovieDetails(this, movieId));
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			navigateUpTo(new Intent(this, MovieMainActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onPause() {
		super.onStop();
		//getIntent().putExtra(MovieItemDetailFragment.ARG_ITEM_ID, movieId);
		//MovieViewer.saveRantimeDataToInternalStorage();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
//		if(MovieViewer.isOnline) {
//			loadMovieDetails(movieId);
//		}
	}

	@Override
	public void onNewJsonResponse(Loader<RESTResponce> loader, String jsonStr) {
		if(jsonStr != null) { 
			switch(loader.getId()) {
			case LOADER_GET_MOVIE_DETAIL:
				GetMovieDetailsResponce responce = new GetMovieDetailsResponce(jsonStr);
				MovieViewer.runtimeDataHolder.getMoviesDetails().put(responce.getId(), responce);
				movieItemDetailFragment.setDisplayData(responce);
				getLoaderManager().destroyLoader(loader.getId());
				break;
			default:
				break;
			}
		}
	}
}
