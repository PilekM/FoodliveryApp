package com.example.foodliveryapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.foodliveryapp.log.DailyStatsHandler;


public class DailyStatsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DailyStatsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DailyStatsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DailyStatsFragment newInstance(String param1, String param2) {
        DailyStatsFragment fragment = new DailyStatsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View rootView = inflater.inflate(R.layout.fragment_daily_stats, container, false);

        DailyStatsHandler dailyStatsHandler = new DailyStatsHandler(getActivity(),rootView);

        dailyStatsHandler.getDailyStats(response -> {
            if(response.getInt("success") == 1){
                dailyStatsHandler.setStats(response.getJSONArray("body").getJSONObject(0));

            }else{
                System.out.println("Jest mi smunto.");
            }
        });

        dailyStatsHandler.getDailyTopRest(response -> {
            if(response.getInt("success") == 1){
                dailyStatsHandler.setRestaurants(response.getJSONArray("body"));
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }
}
