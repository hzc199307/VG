package com.ne.vg.fragment;

import java.util.ArrayList;
import java.util.List;

import com.fortysevendeg.android.swipelistview.SwipeListView;
import com.ne.vg.R;
import com.ne.vg.adapter.MineDownloadCityAdapter;
import com.ne.vg.bean.City;
import com.ne.vg.util.PhoneUtil;
import com.ne.vg.util.UnitUtil;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @ClassName: MineDownloadFragment 
 * @author ºØÖÇ³¬
 * @Description: TODO 
 * @date 2014-10-9 ÏÂÎç2:23:07
 */
public class MineDownloadFragment extends Fragment{

	private static final String TAG = "MineDownloadFragment";

	private SwipeListView swipeListView;
	private MineDownloadCityAdapter adapter;
	private List<City> listData;

	private ProgressDialog progressDialog;

	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState)
	{
		Log.v(TAG, "onCreateView");
		View rootView = inflater.inflate(R.layout.fragment_mine_download,container, false);
		swipeListView = (SwipeListView) rootView.findViewById(R.id.mine_download_swipelv);
		listData = new ArrayList<City>();
		adapter = new MineDownloadCityAdapter(getActivity(), listData);
		swipeListView.setOffsetLeft(PhoneUtil.getPhoneWidthPx(getActivity())
				-UnitUtil.convertDpToPixel(getActivity(), 67));
		swipeListView.setAdapter(adapter);

		new ListAppTask().execute();
		
		progressDialog = new ProgressDialog(getActivity());
		progressDialog.setMessage(getString(R.string.loading));
		progressDialog.setCancelable(false);
		progressDialog.show();

		return rootView;
	}

	public class ListAppTask extends AsyncTask<Void, Void, List<City>> {

		protected List<City> doInBackground(Void... args) {
			List<City> data = new ArrayList<City>();

			for(int i=0;i<10;i++)
				data.add(new City());

			return data;
		}

		protected void onPostExecute(List<City> result) {
			Log.v(TAG, "ListAppTask onPostExecute");
			listData.clear();
			listData.addAll(result);
			adapter.notifyDataSetChanged();
			if (progressDialog != null) {
				progressDialog.dismiss();
			}
		}
	}
}
