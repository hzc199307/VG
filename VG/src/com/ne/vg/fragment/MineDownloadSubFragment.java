package com.ne.vg.fragment;

import java.util.ArrayList;
import java.util.List;

import com.ne.vg.R;
import com.ne.vg.activity.BigSceneDetailActivity;
import com.ne.vg.activity.SceneActivity;
import com.ne.vg.adapter.CommonAdapter;
import com.ne.vg.adapter.ViewHolder;
import com.ne.vg.bean.BigScene;
import com.ne.vg.bean.City;
import com.ne.vg.dao.VGDao;
import com.ne.vg.fragment.MineDownloadFragment.ListAppTask;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MineDownloadSubFragment extends Fragment {

	private static final String TAG = "MineDownloadSubFragment";

	private int cityID;
	private ListView listView ;
	private List<BigScene> bigSceneList;
	private CommonAdapter adapter;
	private ProgressDialog progressDialog;

	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_mine_download_sub,container, false);
		listView = (ListView)rootView.findViewById(R.id.mine_download_sub_lv);
		if(bigSceneList==null)
			bigSceneList = new ArrayList<BigScene>();
		adapter = new CommonAdapter<BigScene>(getActivity(), bigSceneList, R.layout.mine_download_sub_item_bigscene) {

			@Override
			public void convert(ViewHolder helper, BigScene item, int position) {
				helper.setText(R.id.mine_download_sub_item_bigscene_position, (position+1)+".");
				helper.setText(R.id.mine_download_sub_item_bigscene_name, item.getBigSceneName());
			}

		};
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(getActivity(),BigSceneDetailActivity.class); 
				myIntent.putExtra("bigSceneID", bigSceneList.get(position).getBigSceneID());
				startActivity(myIntent);
			}
		});

		new ListAppTask().execute();

		progressDialog = new ProgressDialog(getActivity());
		progressDialog.setMessage(getString(R.string.loading));
		progressDialog.setCancelable(false);
		progressDialog.show();

		return rootView;
	}

	/**
	 * 后台线程get数据
	 */
	public class ListAppTask extends AsyncTask<Void, Void, List<BigScene>> {

		protected List<BigScene> doInBackground(Void... args) {
			cityID = getActivity().getIntent().getExtras().getInt("cityID");
			List<BigScene> data = VGDao.getInstance(getActivity()).getBigSceneList(cityID);
			return data;
		}

		protected void onPostExecute(List<BigScene> result) {
			Log.v(TAG, "ListAppTask onPostExecute");
			bigSceneList.clear();
			bigSceneList.addAll(result);
			adapter.notifyDataSetChanged();
			if (progressDialog != null) {
				progressDialog.dismiss();
			}
		}
	}
}
