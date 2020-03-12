package com.example.foodliveryapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.foodliveryapp.log.MonthlyStatsHandler;


public class MonthlyStatsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public MonthlyStatsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MonthlyStatsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MonthlyStatsFragment newInstance(String param1, String param2) {
        MonthlyStatsFragment fragment = new MonthlyStatsFragment();
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

        View rootView = inflater.inflate(R.layout.fragment_monthly_stats, container, false);

        MonthlyStatsHandler monthlyStatsHandler = new MonthlyStatsHandler(getActivity(),rootView);

        monthlyStatsHandler.getMonthlyStats(response -> {
            if(response.getInt("success") == 1){
                monthlyStatsHandler.setStats(response.getJSONArray("body").getJSONObject(0));

            }else{
                Toast.makeText(getActivity(), "Błąd przy pobieraniu statystyk. Spróbuj ponownie.", Toast.LENGTH_SHORT).show();
            }
        });

        monthlyStatsHandler.getMonthlyTopRest(response -> {
            if(response.getInt("success") == 1){
                monthlyStatsHandler.setRestaurants(response.getJSONArray("body"));
            }else{
                Toast.makeText(getActivity(), "Błąd przy pobieraniu statystyk. Spróbuj ponownie.", Toast.LENGTH_SHORT).show();
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }
}
