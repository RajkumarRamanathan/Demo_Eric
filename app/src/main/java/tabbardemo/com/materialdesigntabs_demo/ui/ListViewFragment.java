package tabbardemo.com.materialdesigntabs_demo.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import tabbardemo.com.materialdesigntabs_demo.R;
import tabbardemo.com.materialdesigntabs_demo.db.DatabaseHelper;
import tabbardemo.com.materialdesigntabs_demo.pojo.Example;

public class ListViewFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private View view;
    private DatabaseHelper databaseHelper;//DB Object

    private String title;//String for tab title

    private static RecyclerView recyclerView;
    private static FloatingActionButton saveBtn;

    private SwipeRefreshLayout swipeRefreshLayout; //For pulldown Refresh

    private static final String URL_DATA = "https://jsonplaceholder.typicode.com/comments";
    private ProgressDialog progressDialog;


    public ListViewFragment() {
    }

    @SuppressLint("ValidFragment")
    public ListViewFragment(String title) {
        this.title = title;//Setting tab title
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.listviewfragment, container, false);

        setRecyclerView();

        return view;

    }

    //Setting recycler view
    private void setRecyclerView() {

        databaseHelper = new DatabaseHelper(getActivity());

        progressBar = (ProgressBar)view.findViewById(R.id.loading_spinner);

        recyclerView = (RecyclerView) view
                .findViewById(R.id.recyclerView);
        saveBtn = (FloatingActionButton) view
                .findViewById(R.id.save_btn);
        recyclerView.setHasFixedSize(true);
        recyclerView
                .setLayoutManager(new LinearLayoutManager(getActivity()));//Linear Items


      // set adapter on recyclerview
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        /**
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {



                loadRecyclerViewData();

            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DBOperaations().execute("");


            }
        });
    }

    @Override
    public void onRefresh() {
        loadRecyclerViewData();//call wheneever swipe down occurs.

    }

    List<Example> dataList = null;
    private ProgressBar progressBar;

    private void loadRecyclerViewData() {

        if(!swipeRefreshLayout.isRefreshing())
            progressBar.setVisibility(View.VISIBLE);


        OkHttpClient client = new OkHttpClient();

        // GET request
        Request request = new Request.Builder()
                .url(URL_DATA)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("", e.toString());

                new Thread() {
                    public void run() {

                        getActivity().runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                showSnackBar(getActivity(),"Network Error Please try again!!!");
                                swipeRefreshLayout.setRefreshing(false);                // Stopping swipe refresh
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                    }
                }.start();

            }
            @Override
            public void onResponse(Response response) throws IOException {
                Log.i("fullresponse", response.toString());

                try{
                    String jArray =response.body().string().toString();

                    Type listType = new TypeToken<ArrayList<Example>>() {}.getType();
                    dataList = new Gson().fromJson(jArray, listType);

                }
                catch (Exception e){
                    e.printStackTrace();
                }

                new Thread() {
                    public void run() {

                        getActivity().runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), dataList);
                                recyclerView.setAdapter(adapter);
                                swipeRefreshLayout.setRefreshing(false);                // Stopping swipe refresh
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                    }
                }.start();
            }
        });
    }

    public void showSnackBar(Activity activity, String message){
        View rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show();
    }





    private class DBOperaations extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            for (Example note:dataList){
                if(databaseHelper.insertNote(note)<0)//Duplicate check -1 duplicate data/ 1 is unique
                {
                    showSnackBar(getActivity(),"Already Data Saved !!!");
                    break;
                }
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            dismissProgressDialog();

        }

        @Override
        protected void onPreExecute() {
            showProgressDialog();
        }

        @Override
        protected void onProgressUpdate(Void... values) {


        }
    }

    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
        }
        progressDialog.setMessage("Saving...");
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}