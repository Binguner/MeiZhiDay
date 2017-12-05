package com.example.nenguou.meizhiday.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nenguou.meizhiday.Entity.Gank;
import com.example.nenguou.meizhiday.GetDatas.GankOkhttp;
import com.example.nenguou.meizhiday.GetDatas.GetGankDatas;
import com.example.nenguou.meizhiday.MainActivity;
import com.example.nenguou.meizhiday.UI.others.GithubPageActivity;
import com.example.nenguou.meizhiday.R;
import com.example.nenguou.meizhiday.adapter.GankAppAdapter;
import com.example.nenguou.meizhiday.network.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link appRecommendFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link appRecommendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class appRecommendFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    private SwipeRefreshLayout AllSwipeRefreshLayout;
    private RecyclerView AllRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private GetGankDatas getGankAppDatas;
    private CardView app_recommend_cardview;
    private GankAppAdapter gankAppAdapter;
    private ArrayList<Gank> main_ganks = new ArrayList<>();
    private int flags = 0;
    private int count = 1;
    private int lastVisibleItem;
    private GetAppDates getAppDates = null;
    private boolean appReIsLoading = true;

    public appRecommendFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initId();
        initViews();
        if (!Utils.isNetworkAvailable(getContext())) {
            Toast.makeText(getContext(), "请检查网络", Toast.LENGTH_SHORT).show();
        } else {
            //firstLoad();
            FirstLoadDatas();
            setRefreshListener();
        }
        //FirstLoadDatas();
        //setRefreshListener();

    }

    private void FirstLoadDatas() {
        getAppDates =
                new GetAppDates("http://gank.io/api/data/App/6/1", flags);
        getAppDates.execute();
    }


    private void initId() {
        app_recommend_cardview = (CardView) getView().findViewById(R.id.app_recommend_cardview);
        AllSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.all_swipe_refresh_layout);
        AllRecyclerView = (RecyclerView) getView().findViewById(R.id.allrecyclerview);
    }

    private void initViews() {
        linearLayoutManager = new LinearLayoutManager(getContext());
        AllRecyclerView.setLayoutManager(linearLayoutManager);
        AllRecyclerView.setHasFixedSize(true);
        AllSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorYello, R.color.colorAccent, R.color.colorTablayout);
        gankAppAdapter = new GankAppAdapter(getContext(), main_ganks);
        AllRecyclerView.setAdapter(gankAppAdapter);
        gankAppAdapter.setOnItemClickListener(new GankAppAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = AllRecyclerView.getChildAdapterPosition(view);
                Intent intent = new Intent(getContext(), GithubPageActivity.class);
                Bundle bundle = new Bundle();
                //Log.i("ueueue",main_ganks.get(position).url);
                bundle.putString("url", main_ganks.get(position).url);
                bundle.putString("title", main_ganks.get(position).desc);
                intent.putExtras(bundle);
                //Toast.makeText(getContext(),"第"+position+"个",Toast.LENGTH_SHORT).show();
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(view, (int) view.getWidth() / 2, (int) view.getHeight() / 2, 0, 0);
                ActivityCompat.startActivity(getContext(), intent, compat.toBundle());
            }
        });
    }

    private void setRefreshListener() {
        AllSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAppDates =
                        new GetAppDates("http://gank.io/api/data/App/6/1", 1);
                getAppDates.execute();
            }
        });

        AllRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && appReIsLoading
                        && lastVisibleItem + 3 >= linearLayoutManager.getItemCount()) {
                    appReIsLoading = false;
                    getAppDates =
                            new GetAppDates("http://gank.io/api/data/App/6/" + (++count), 0);
                    getAppDates.execute();
                    Log.i("findLastVisiblfeftion", "" + linearLayoutManager.getItemCount());
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                Log.i("findLastVisibleftion", "" + lastVisibleItem);
            }
        });
    }


    // public class GetAppDates extends AsyncTask<String,Void,List<Gank>>{
    public class GetAppDates extends AsyncTask<String, Void, String> {

        private List<Gank> ganks = new ArrayList<>();   //异步里的 gankslists
        private String url;
        private String datas;
        private String JSONdatas;
        private int flag;
        private boolean isLoading = true;

        public GetAppDates(String url, int flag) {
            this.url = url;
            this.flag = flag;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            AllSwipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected String doInBackground(String... strings) {
            /*if(flag == 0){*/
            if (isLoading) {
                try {//这是 String
                    datas = GankOkhttp.getDatas(url);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "当前网络状态不好，请稍后再试。", Toast.LENGTH_SHORT).show();
                    datas = "";
                }

                isLoading = false;
            }
            return datas;
         /*   }else {
                return null;
            }*/

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (datas.toString() == "") {
                AllSwipeRefreshLayout.setRefreshing(false);
                appReIsLoading = true;
            }
            if (flag == 0 && datas.toString() != "") {
                Gson gson = new Gson();
                try {
                    JSONObject jsonObject = new JSONObject(datas);
                    JSONdatas = jsonObject.getString("results");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Type type = new TypeToken<ArrayList<Gank>>() {
                }.getType();
                ganks = gson.fromJson(JSONdatas, type);
                //      Log.i("asdasdasd",ganks.get(0).images[0].toString());

                try {
                    main_ganks.addAll(ganks);
                } catch (Exception e) {

                }
                gankAppAdapter.notifyItemInserted(main_ganks.size());
                AllSwipeRefreshLayout.setRefreshing(false);
                isLoading = true;
                appReIsLoading = true;
            } else {
                Toast.makeText(getContext(), "No more datas", Toast.LENGTH_SHORT).show();
                AllSwipeRefreshLayout.setRefreshing(false);
            }

//            gankAppAdapter.setOnItemClickListener(new GankAppAdapter.onItemClickListener() {
//                @Override
//                public void onItemClick(View view) {
//                    int position = AllRecyclerView.getChildAdapterPosition(view);
//                    Intent intent = new Intent(getContext(), GithubPageActivity.class);
//                    Bundle bundle = new Bundle();
//                    //Log.i("ueueue",main_ganks.get(position).url);
//                    bundle.putString("url", main_ganks.get(position).url);
//                    bundle.putString("title", main_ganks.get(position).desc);
//                    intent.putExtras(bundle);
//                    //Toast.makeText(getContext(),"第"+position+"个",Toast.LENGTH_SHORT).show();
//                    ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(view, (int) view.getWidth() / 2, (int) view.getHeight() / 2, 0, 0);
//                    ActivityCompat.startActivity(getContext(), intent, compat.toBundle());
//                }
//            });
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        getAppDates.cancel(true);
    }

    //初始化控件资源
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public static appRecommendFragment newInstance() {
        appRecommendFragment fragment = new appRecommendFragment();
        return fragment;
    }

    //初始化一些资源文件
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    //绑定布局
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_apprecommend, container, false);
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
