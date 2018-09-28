package com.bku.jobs.Fragment

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.icu.util.TimeUnit
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.bku.jobs.API.APIService
import com.bku.jobs.Adapter.SearchJobAdapter
import com.bku.jobs.ModelData.JobData
import com.bku.jobs.R

import java.lang.reflect.Array
import java.util.ArrayList
import java.util.Arrays

import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import rx.Observer
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Func1
import rx.schedulers.Schedulers

import android.content.ContentValues.TAG


/**
 * Created by Welcome on 5/21/2018.
 */

class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private  var mParam1: String?=""
    private  var mParam2: String?=""
    private  lateinit var searchTxt: EditText
    private  lateinit var resultList: RecyclerView
    private lateinit var searchAdapter: SearchJobAdapter
    private lateinit var mListener: OnFragmentInteractionListener
    private lateinit var jobArrayList: ArrayList<JobData>
    private lateinit var splitString: kotlin.Array<String>

    private lateinit var apiService: APIService

    private var strSearch: String = ""
    private var url = "https://jobs.github.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments?.getString(ARG_PARAM1).toString()
            mParam2 = arguments?.getString(ARG_PARAM2).toString()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindView(view)
        super.onViewCreated(view, savedInstanceState)

        val retrofit = Retrofit.Builder().baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()

        apiService = retrofit.create(APIService::class.java)


        setKeyListener()


        /*  searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchTxt.getText().toString().isEmpty()){
                    searchTxt.setError("Keyword is empty");
                    return;
                }
                new GetSearchResult().execute();

            }
        });*/
    }

    private fun setKeyListener() {
        searchTxt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // getListSearch(apiService);
                //  getDataLocation(apiService);
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            override fun afterTextChanged(s: Editable) {

                getDataLocation(apiService)
            }
        })
    }

    private fun getDataLocation(apiService: APIService) {
        //   jobArrayList=new ArrayList<>();
        if (searchTxt.text.toString().contains(";")) {
            splitString = searchTxt.text.toString().split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            strSearch = splitString[0]
        } else
            strSearch = searchTxt.text.toString()
        apiService.getSearchData(strSearch)
                .switchMap { jobData ->
                    jobArrayList = ArrayList()
                    Observable.from(jobData)
                }.filter { jobData -> !searchTxt.text.toString().contains(";") || jobData.location.toLowerCase().contains(splitString[1].toLowerCase())  }
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<JobData>() {

                    override fun onCompleted() {
                        searchAdapter = SearchJobAdapter(requireActivity(), jobArrayList)
                        val layoutManager = LinearLayoutManager(activity)
                        layoutManager.orientation = LinearLayoutManager.VERTICAL
                        resultList.layoutManager = layoutManager
                        resultList.adapter = searchAdapter
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onNext(jobData: JobData) {
                        jobArrayList.add(jobData)
                    }
                })
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private fun getListSearch(apiService: APIService) {
        val observable = apiService.getSearchData(searchTxt.text.toString())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())

        //delay 100ms mỗi lần seach
        observable.debounce(100, java.util.concurrent.TimeUnit.MILLISECONDS)
                .subscribe(object : Observer<List<JobData>> {
                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable) {
                        Toast.makeText(activity, "Opps , there is an error ... ", Toast.LENGTH_SHORT).show()
                    }

                    override fun onNext(jobData2: List<JobData>) {
                        jobArrayList = ArrayList()
                        jobArrayList.addAll(jobData2)
                        searchAdapter = SearchJobAdapter(requireActivity(), jobArrayList)
                        val layoutManager = LinearLayoutManager(activity)
                        layoutManager.orientation = LinearLayoutManager.VERTICAL
                        resultList.layoutManager = layoutManager
                        resultList.adapter = searchAdapter
                    }
                })


    }

    private fun getFilter() {

    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
            mListener.onFragmentInteraction(uri)

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context?.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
      //  mListener = null
    }

    private fun bindView(view :View) {
        searchTxt = view.findViewById(R.id.searchTxt)
        resultList = view.findViewById(R.id.result_list)
        jobArrayList = ArrayList()
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): SearchFragment {
            val fragment = SearchFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }


    /* private class GetSearchResult extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            jobArrayList.clear();
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String url =  "https://jobs.github.com/positions.json?description="+searchTxt.getText().toString();
            String jsonStr = sh.makeServiceCall(url);

            if (jsonStr != null) {
                try {
                    JobInfo tempJob;
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject temp = new JSONObject();
                        temp=jsonArray.getJSONObject(i);
                        tempJob = new JobInfo(temp.getString("id"),temp.getString("created_at"),temp.getString("title"),temp.getString("location"), temp.getString("type"), temp.getString("description"),temp.getString("how_to_apply"),temp.getString("company"),temp.getString("company_url"),temp.getString("company_logo"),temp.getString("url"));
                        jobArrayList.add(tempJob);
                        String id = temp.getString("title");
                        Log.d("1abc","id thư: "+i+" "+id);
                    }
                } catch (final JSONException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            if(jobArrayList.size()==0){
                Toast.makeText(getActivity(),"There are no results that match your search",Toast.LENGTH_SHORT).show();
            }else {
                searchAdapter = new SearchJobAdapter(getActivity(), jobArrayList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                resultList.setLayoutManager(layoutManager);
                resultList.setAdapter(searchAdapter);
            }
        }

    }*/
}// Required empty public constructor
