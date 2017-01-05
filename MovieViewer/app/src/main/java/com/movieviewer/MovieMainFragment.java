package com.movieviewer;

import java.util.ArrayList;
import java.util.List;

import com.movieviewer.bll.network.responce.dto.MovieMetaData;
import com.movieviewer.ui.adapters.MovieGridViewAdapter;
import com.movieviewer.ui.adapters.MovieGridViewAdapter.OnMovieGridViewAdapterListener;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;


/**
 * A list fragment representing a list of Items. This fragment also supports
 * tablet devices by allowing list items to be given an 'activated' state upon
 * selection. This helps indicate which item is currently being viewed in a
 * {@link MovieItemDetailFragment}.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnMovieItemFragmentListener}
 * interface.
 */
public class MovieMainFragment extends Fragment {

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * activated item position. Only used on tablets.
	 */
	private static final String STATE_ACTIVATED_POSITION = "activated_position";

	/**
	 * The fragment's current callback object, which is notified of list item
	 * clicks.
	 */
	private OnMovieItemFragmentListener onMovieItemFragmentListener = sDummyCallbacks;

	/**
	 * The current activated item position. Only used on tablets.
	 */
	private int mActivatedPosition = GridView.INVALID_POSITION;

	/**
	 * A callback interface that all activities containing this fragment must
	 * implement. This mechanism allows activities to be notified of item
	 * selections.
	 */
	public interface OnMovieItemFragmentListener {
		/**
		 * Callback for when an item has been selected.
		 */
		public void onItemSelected(String id);
		
		public void onNewPageRequested();
		
		public void onRefreshAllData();
	}

	/**
	 * A dummy implementation of the {@link OnMovieItemFragmentListener} interface that does
	 * nothing. Used only when this fragment is not attached to an activity.
	 */
	private static OnMovieItemFragmentListener sDummyCallbacks = new OnMovieItemFragmentListener() {
		@Override
		public void onItemSelected(String id) {
		}

		@Override
		public void onNewPageRequested() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onRefreshAllData() {
			// TODO Auto-generated method stub
			
		}
		
	};

	private int preLastGridItem;
	private GridView moviesGridView;
	private MovieGridViewAdapter moviesGridViewAdapter;
	private SwipeRefreshLayout refreshLayout;
	private ArrayList<MovieMetaData> moviesList = new ArrayList<MovieMetaData>();
	
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public MovieMainFragment() {}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	public void addPageData(List<MovieMetaData> moviesList) {
		this.moviesList.addAll(moviesList);
		if(moviesGridViewAdapter != null) {
			moviesGridViewAdapter.notifyDataSetChanged();
		}
		
	}
	
	public void setRefreshingGrid(boolean flag) {
		if(refreshLayout != null) {
			refreshLayout.setRefreshing(flag);
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_grid, container, false);
		moviesGridView = (GridView) rootView.findViewById(R.id.gridViewMovies);
		refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
		moviesGridViewAdapter = new MovieGridViewAdapter(getActivity(), moviesList);
		
		moviesGridView.setAdapter(moviesGridViewAdapter);
		moviesGridViewAdapter.setOnMovieGridViewAdapterListener(new OnMovieGridViewAdapterListener() {
			
			@Override
			public void onMoviewItemClicked(int position, MovieMetaData movieMetaData) {
				if(onMovieItemFragmentListener != null) {
					onMovieItemFragmentListener.onItemSelected(movieMetaData.getId() + "");
				}
			}
		});
		
		refreshLayout.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				if(onMovieItemFragmentListener != null) {
            		onMovieItemFragmentListener.onRefreshAllData();
            	}
			}
		});
		
		moviesGridView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				
				final int lastItem = firstVisibleItem + visibleItemCount;

	            if(lastItem == totalItemCount)
	            {
	                if(preLastGridItem != lastItem)
	                {
	                    //to avoid multiple calls for last item
	                	preLastGridItem = lastItem;
	                	
	                	if(onMovieItemFragmentListener != null) {
	                		onMovieItemFragmentListener.onNewPageRequested();
	                	}
	                }
	            }
			}
		});
		
		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Restore the previously serialized activated item position.
		if (savedInstanceState != null && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
			setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Activities containing this fragment must implement its callbacks.
		if (!(activity instanceof OnMovieItemFragmentListener)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}

		setOnMovieItemListFragmentListener((OnMovieItemFragmentListener) activity);
	}

	@Override
	public void onDetach() {
		super.onDetach();

		// Reset the active callbacks interface to the dummy implementation.
		setOnMovieItemListFragmentListener(sDummyCallbacks);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mActivatedPosition != ListView.INVALID_POSITION) {
			// Serialize and persist the activated item position.
			outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
		}
	}

	/**
	 * Turns on activate-on-click mode. When this mode is on, list items will be
	 * given the 'activated' state when touched.
	 */
	public void setActivateOnItemClick(boolean activateOnItemClick) {
		moviesGridView.setChoiceMode(
				activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
						: ListView.CHOICE_MODE_NONE);
	}

	private void setActivatedPosition(int position) {
		if (position == ListView.INVALID_POSITION) {
			moviesGridView.setItemChecked(mActivatedPosition, false);
		} else {
			moviesGridView.smoothScrollToPosition(mActivatedPosition);
		}

		mActivatedPosition = position;
	}

	public OnMovieItemFragmentListener getOnMovieItemListFragmentListener() {
		return onMovieItemFragmentListener;
	}

	public void setOnMovieItemListFragmentListener(
			OnMovieItemFragmentListener onMovieItemListFragmentListener) {
		this.onMovieItemFragmentListener = onMovieItemListFragmentListener;
	}
}
