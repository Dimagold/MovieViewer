package com.movieviewer;

import java.net.InetAddress;
import java.util.List;

import com.movieviewer.bll.data.db.DBStorageUtil;
import com.movieviewer.bll.network.RESTLoader.HTTPVerb;
import com.movieviewer.bll.network.RESTLoader.RESTResponce;
import com.movieviewer.bll.network.request.GetPopularMoviesRequest;
import com.movieviewer.bll.network.request.RequestLanguage;
import com.movieviewer.bll.network.responce.GetPopularMoviesResponce;
import com.movieviewer.bll.network.responce.dto.MovieMetaData;

import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;
import android.app.Activity;

/**
 * Based on generated template of Master Detail view
 */
public class MovieMainActivity extends BaseActivity implements
		MovieMainFragment.OnMovieItemFragmentListener {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;
	private MovieMainFragment movieMainFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movieitem_list);

		movieMainFragment = ((MovieMainFragment) getFragmentManager()
				.findFragmentById(R.id.movieitem_grid_fragment));
		
		if (findViewById(R.id.movieitem_detail_container) != null) {
			mTwoPane = true;
			movieMainFragment.setActivateOnItemClick(true);
		} else {
			mTwoPane = false;
		}
		
		//MovieViewer.isOnline = isOnline();
		//if(!MovieViewer.isOnline) {
		//	MovieViewer.loadInternalDataToRuntimeDataHolder();
		//}
		
		MovieViewer.runtimeDataHolder.reset();
		loadPopularMoviesData();
	}
	
	private boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		return cm.getActiveNetworkInfo() != null;
	}
	
	private void loadPopularMoviesData() {
		Toast.makeText(getApplicationContext(), "Loading", Toast.LENGTH_LONG).show();
		int currentPage = MovieViewer.runtimeDataHolder.getCurrentMoviesPage();
		int nextPage = currentPage + 1;
		
		if(MovieViewer.isOnline) {	
			getLoaderManager().initLoader(LOADER_GET_POPULAR_MOVIES,
					bundleForLoader(new GetPopularMoviesRequest(RequestLanguage.ENG, nextPage), true, HTTPVerb.GET), this);
		} else {	
			List<MovieMetaData> page = DBStorageUtil.retrivePage(this, nextPage);
			if(page != null) movieMainFragment.addPageData(page);
		}
	}

	/**
	 * Callback method from {@link MovieMainFragment.OnMovieItemFragmentListener} indicating
	 * that the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(String id) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(MovieItemDetailFragment.ARG_ITEM_ID, id);
			MovieItemDetailFragment fragment = new MovieItemDetailFragment();
			fragment.setArguments(arguments);
			getFragmentManager().beginTransaction()
					.replace(R.id.movieitem_detail_container, fragment)
					.commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this,
					MovieItemDetailActivity.class);
			detailIntent.putExtra(MovieItemDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		MovieViewer.isOnline = isOnline();
	}
	
	@Override
	protected void onPause() {
		super.onStop();
		//MovieViewer.saveRantimeDataToInternalStorage();
	}

	@Override
	public void onNewJsonResponse(Loader<RESTResponce> loader, String jsonStr) {
		if(jsonStr != null) { 
			switch(loader.getId()) {
			case LOADER_GET_POPULAR_MOVIES:
				GetPopularMoviesResponce responce = new GetPopularMoviesResponce(jsonStr);
				MovieViewer.runtimeDataHolder.getPopularMovies().put(responce.getPage(), responce);
				MovieViewer.runtimeDataHolder.setCurrentMoviesPage(responce.getPage());
				movieMainFragment.addPageData(responce.getResults());
				movieMainFragment.setRefreshingGrid(false);
				
				getLoaderManager().destroyLoader(loader.getId());
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void onNewPageRequested() {
		loadPopularMoviesData();
	}

	@Override
	public void onRefreshAllData() {
		loadPopularMoviesData();
		MovieViewer.runtimeDataHolder.reset();
	}
}
