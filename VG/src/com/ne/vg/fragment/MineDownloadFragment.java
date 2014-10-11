package com.ne.vg.fragment;

import java.util.ArrayList;
import java.util.List;

import com.fortysevendeg.android.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.android.swipelistview.SwipeListView;
import com.fortysevendeg.android.swipelistview.SwipeListViewListener;
import com.ne.vg.R;
import com.ne.vg.activity.MineDownloadSubActivity;
import com.ne.vg.activity.MyLoveActivity;
import com.ne.vg.adapter.CommonAdapter;
import com.ne.vg.adapter.MineDownloadCityAdapter;
import com.ne.vg.adapter.ViewHolder;
import com.ne.vg.bean.BigScene;
import com.ne.vg.bean.City;
import com.ne.vg.dao.VGDao;
import com.ne.vg.util.PhoneUtil;
import com.ne.vg.util.UnitUtil;

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
import android.widget.Toast;

/**
 * 
 * @ClassName: MineDownloadFragment 
 * @author 贺智超
 * @Description: TODO 
 * @date 2014-10-9 下午2:23:07
 */
public class MineDownloadFragment extends Fragment{

	private static final String TAG = "MineDownloadFragment";

	private SwipeListView swipeListView;
	private CommonAdapter adapter;
	private List<City> cityList;

	private ProgressDialog progressDialog;

	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState)
	{
		Log.v(TAG, "onCreateView");
		View rootView = inflater.inflate(R.layout.fragment_mine_download,container, false);
		swipeListView = (SwipeListView) rootView.findViewById(R.id.mine_download_swipelv);
		if(cityList==null)cityList = new ArrayList<City>();
		//设置滑动lv的滑动左边剩余宽度px
		swipeListView.setOffsetLeft(PhoneUtil.getPhoneWidthPx(getActivity())
				-getResources().getDimension(R.dimen.mine_download_swipelv_btn_width));

		adapter = new CommonAdapter<City>(getActivity(), cityList, R.layout.mine_download_item_city) {

			@Override
			public void convert(ViewHolder helper, City item, int position) {
				// TODO Auto-generated method stub
				helper.setText(R.id.mine_download_item_city_name, item.getCityName());
				helper.setText(R.id.mine_download_item_city_pinyin, item.getCityPinyin());
			}
		};
		swipeListView.setAdapter(adapter);
		///setOnItemClickListener失效 所以改用此函数代替
		swipeListView.setSwipeListViewListener(new BaseSwipeListViewListener(){
			@Override
			public void onClickFrontView(int position) {
				// TODO Auto-generated method stub
				super.onClickFrontView(position);
				Intent intent = new Intent(getActivity(),MineDownloadSubActivity.class);
				City city = cityList.get(position);
				intent.putExtra("cityID", city.getCityID());
				intent.putExtra("cityName", city.getCityName());
				startActivity(intent);
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
	public class ListAppTask extends AsyncTask<Void, Void, List<City>> {

		protected List<City> doInBackground(Void... args) {
			List<City> data = VGDao.getInstance(getActivity()).getCityList();
			return data;
		}

		protected void onPostExecute(List<City> result) {
			Log.v(TAG, "ListAppTask onPostExecute");
			cityList.clear();
			cityList.addAll(result);
			adapter.notifyDataSetChanged();
			if (progressDialog != null) {
				progressDialog.dismiss();
			}
		}
	}
}
