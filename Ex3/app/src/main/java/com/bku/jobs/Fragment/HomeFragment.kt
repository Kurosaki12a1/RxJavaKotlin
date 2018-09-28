package com.bku.jobs.Fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v4.app.FragmentManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

import com.bku.jobs.API.APIService
import com.bku.jobs.Activity.JobDetailActivity
import com.bku.jobs.Adapter.JobsAdapter
import com.bku.jobs.Adapter.UserDataAdapter
import com.bku.jobs.DataZip
import com.bku.jobs.ModelData.JobData
import com.bku.jobs.ModelData.UserData.ResultsItem
import com.bku.jobs.ModelData.UserData.UserData
import com.bku.jobs.R
import com.bku.jobs.Util.UtilityJob

import java.util.ArrayList
import java.util.concurrent.TimeUnit

import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import rx.Observer
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.functions.Func1
import rx.functions.Func2
import rx.schedulers.Schedulers


/**
 * Created by Welcome on 5/21/2018.
 */

class HomeFragment : Fragment(), AdapterView.OnItemClickListener {

    private val TAG = HomeFragment::class.java.simpleName

    private lateinit  var listView: ListView

    //  ArrayList<JobInfo> jobsList;

    private lateinit var mProgressBar: ProgressBar
    private lateinit var jobsAdapter: JobsAdapter
 //   private var fragmentManager: FragmentManager? = null

    private var _isAccess: Boolean ?=false

    private var mParam2: String? = ""

    private var mListener: OnFragmentInteractionListener? = null

    private lateinit var jobDataLst: ArrayList<JobData>

    private lateinit var retrofit: Retrofit

    private lateinit var retrofit1: Retrofit

    private lateinit var lstResultsItem: ArrayList<ResultsItem>

    private lateinit var observable: Observable<List<JobData>>

    private lateinit var observableUserData: Observable<UserData>

    private lateinit var subscription: Subscription

    private lateinit var apiService: APIService

    private lateinit var apiService1: APIService //for UserData

    private lateinit var  jobData: ArrayList<JobData>

    private lateinit var subscription1: Subscription

    private lateinit var txtError: TextView

    private lateinit var btnRetry: Button

    private lateinit var recyclerView: RecyclerView

    private var subscription2: Subscription? = null //this for something more bigger

    override fun onItemClick(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
        val intent = Intent(activity, JobDetailActivity::class.java)
        intent.putExtra("jobTitle", jobDataLst[i].title)
        intent.putExtra("company", jobDataLst[i].company)
        intent.putExtra("jobType", jobDataLst[i].type)
        intent.putExtra("location", jobDataLst[i].location)
        intent.putExtra("jobCreated", jobDataLst[i].createdAt)
        intent.putExtra("jobDescription", jobDataLst[i].description)
        intent.putExtra("howToApply", jobDataLst[i].howToApply)
        val utilityJob = UtilityJob.getInstance()
        utilityJob.jobInfo = jobDataLst[i]
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (this.arguments != null) {
            _isAccess = arguments?.getBoolean(ARG_PARAM1,false)
            mParam2 = arguments?.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindView()
        super.onViewCreated(view, savedInstanceState)
        updateData(10)

        //Thời gian update adapter  phải lớn hơn thời gian load xong cái update Data đầu tiên đã
        //Nếu không xảy ra lỗi luồng đè lẫn nhau


        /* subscription = Observable.interval(10, TimeUnit.SECONDS).subscribe(new Subscriber<Long>() {
                @Override
                public void onCompleted() {
                    //Không biết khi nào vô đây nữa.....
                }
                @Override
                public void onError(Throwable e) {

                }
                @Override
                public void onNext(Long aLong) {
                    int time = (int) (long) aLong;
                    switch (time) {
                        case 0:
                            updateData(15);
                            break;
                        case 1:
                            updateData(20);
                            break;
                        case 2:
                            updateData(25);
                            break;
                        case 3:
                            updateData(30);
                            break;
                        case 4:
                            updateData(35);
                            break;
                        case 5:
                            subscription.unsubscribe();
                            break;
                        default:
                            break;

                    }
                }
            });*/


        listView.onItemClickListener = this
        btnRetry.setOnClickListener {
            Toast.makeText(activity, "Try again....", Toast.LENGTH_SHORT).show()
            txtError.visibility = View.GONE
            btnRetry.visibility = View.GONE
            listView.visibility = View.VISIBLE
            callSubscriptsTon()
        }
    }

    private fun callSubscriptsTon() {
        Observable.interval(5, TimeUnit.SECONDS).subscribe(object : Subscriber<Long>() {
            override fun onCompleted() {
                //Không biết khi nào vô đây nữa.....
            }

            override fun onError(e: Throwable) {

            }

            override fun onNext(aLong: Long?) {
                val time = (aLong as Long).toInt()
                when (time) {
                    0 -> updateData(15)
                    1 -> updateData(20)
                    2 -> updateData(25)
                    3 -> updateData(30)
                    4 -> updateData(35)
                    5 -> subscription.unsubscribe()

                    6 -> updateData(40)
                    else -> {
                    }
                }
            }
        })

    }

    private fun updateData(size: Int) {
        retrofit = Retrofit.Builder().baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()

        retrofit1 = Retrofit.Builder().baseUrl(urlData)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()


        apiService = retrofit.create(APIService::class.java)

        apiService1 = retrofit1.create(APIService::class.java)

        observable = apiService.getJobData()

        observableUserData = apiService1.getUserData("10")

        /*
        subscription1=observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<JobData>>() {
            @Override
            public void onCompleted() {
                Toast.makeText(getActivity(),"Update new  " + size + " Job ",Toast.LENGTH_SHORT).show();
                setRecycleViewAdapter(size);

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getActivity(),"Check your connection or maybe issue from our server... ",Toast.LENGTH_SHORT).show();
                txtError.setText("SORRY , THERE IS AN ERROR TO LOAD JOBS....");
                txtError.setVisibility(View.VISIBLE);
                btnRetry.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                subscription.unsubscribe();
            }

            @Override
            public void onNext(List<JobData> jobData2) {
                    jobDataLst = new ArrayList<>();
                    jobDataLst.addAll(jobData2);

            }
        });
*/

        Observable.zip(observable, observableUserData) { jobData, userData -> DataZip(jobData, userData) }.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<DataZip>() {
                    override fun onCompleted() {
                        // Toast.makeText(getActivity(),"Update new  " + size + " Job ",Toast.LENGTH_SHORT).show();
                        jobsAdapter = JobsAdapter(requireActivity(), jobDataLst)
                        listView.adapter = jobsAdapter
                        mProgressBar.visibility = View.GONE
                        val userDataAdapter = UserDataAdapter(requireContext(), lstResultsItem)
                        recyclerView.adapter = userDataAdapter
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onNext(dataZip: DataZip) {
                        jobDataLst = ArrayList()
                        jobDataLst.addAll(dataZip.jobData)
                        lstResultsItem = ArrayList(dataZip.userData.results)
                    }
                })


    }


    /* private Subscription getUserData(){
        return observableUserData.subscribe(new Observer<UserData>() {
            @Override
            public void onCompleted() {
                UserDataAdapter userDataAdapter=new UserDataAdapter(getContext(),lstResultsItem);
                recyclerView.setAdapter(userDataAdapter);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(UserData userData) {
                lstResultsItem=new ArrayList<>(userData.getResults());
            }
        });
    }*/


    //Hàm này chỉ lấy 5 phần tử
    private fun setRecycleViewAdapter(size: Int) {
        jobData = ArrayList()
        //con số limit là ý chỉ lấy số xx phần tử của trang . Có thể lựa chọn trang này nọ <=size
        Observable.from(jobDataLst).limit(size).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<JobData>() {
                    override fun onCompleted() {
                        jobsAdapter = JobsAdapter(requireActivity(), jobData)
                        listView.adapter = jobsAdapter
                        mProgressBar.visibility = View.GONE
                    }

                    override fun onError(e: Throwable) {
                        /* Toast.makeText(getActivity(),"ERROR ON THIS SHIT.... ",Toast.LENGTH_SHORT).show();
                        txtError.setTextColor(getResources().getColor(R.color.colorWhite));*/

                    }

                    override fun onNext(jobData1: JobData) {
                        jobData.add(jobData1)
                    }
                })
    }


    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener?.onFragmentInteraction(uri)
        }
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
        mListener = null

    }


    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

    fun bindView() {
        btnRetry = view?.findViewById<View>(R.id.btnRetry) as Button
        txtError = view?.findViewById<View>(R.id.errorText) as TextView
        listView = view?.findViewById<View>(R.id.jobsList) as ListView
        mProgressBar = view?.findViewById<View>(R.id.progressBar) as ProgressBar
        recyclerView = view?.findViewById<View>(R.id.userList) as RecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
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
        private const val url = "https://jobs.github.com"
        private const val urlData = "https://randomuser.me"
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
        fun newInstance(param1: Boolean, param2: String): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            args.putBoolean(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }


    /*private class GetJobs extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr );
            if (jsonStr != null){
                try {
                    JSONArray jobs = new JSONArray(jsonStr);

                    for (int i=0; i<jobs.length();i++){
                        JSONObject j = jobs.getJSONObject(i);
                        JobInfo job = new JobInfo();
                        job.setJobId(j.getString("id"));
                        job.setJobCreatedAt(j.getString("created_at"));
                        job.setJobTitle(j.getString("title"));
                        job.setLocation(j.getString("location"));
                        job.setType(j.getString("type"));
                        job.setDescription(j.getString("description"));
                        job.setCompany(j.getString("company"));
                        job.setCompanyURL(j.getString("company_url"));
                        job.setCompanyLogo(j.getString("company_logo"));
                        job.setHowToApply(j.getString("how_to_apply"));
                        job.setURL(j.getString("url"));
                        jobsList.add(i, job);
                    }
                }catch (final JSONException e){
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    getActivity().runOnUiThread(new Runnable(){
                        @Override
                        public void run() {
                            Toast.makeText(getActivity().getApplicationContext(),"Json parsing error: " + e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }else {
                Log.e(TAG, "Couldn't get json from server.");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Couldn't get json from server!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            */
    /**
     * Updating parsed JSON data into ListView
     *//*


        }

    }*/

}// Required empty public constructor
