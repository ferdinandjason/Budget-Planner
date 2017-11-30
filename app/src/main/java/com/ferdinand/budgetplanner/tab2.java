package com.ferdinand.budgetplanner;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link tab2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link tab2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class tab2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public tab2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment tab2.
     */
    // TODO: Rename and change types and number of parameters
    public static tab2 newInstance(String param1, String param2) {
        tab2 fragment = new tab2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    protected Realm realm;
    private BarChart chart;
    protected TextView saldo;
    protected TextView income;

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
        // Inflate the layout for this fragment
        realm=Realm.getDefaultInstance();
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);
        chart = (BarChart) view.findViewById(R.id.BarChart);
        saldo = (TextView) view.findViewById(R.id.textView5);
        setData();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    protected void setData(){


        realm=Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<dataInput> input = realm.where(dataInput.class).equalTo("bulan",Calendar.getInstance().get(Calendar.MONTH)+1).findAll();
        RealmResults<dataOutput> output = realm.where(dataOutput.class).equalTo("bulan",Calendar.getInstance().get(Calendar.MONTH)+1).findAll();
        int jumlahInput=0,jumlahOutput=0;
        for(dataInput temp:input){
            jumlahInput+=temp.getBalance();
        }
        for(dataOutput temp:output){
            jumlahOutput+=temp.getBalance();
        }
        int saldoSekarang = jumlahInput-jumlahOutput;
        saldo.setText("Saldo  : "+Integer.toString(saldoSekarang));

        ArrayList<BarEntry> entry = new ArrayList<>();
        entry.add(new BarEntry((float) 0.0,(float)jumlahInput));
        entry.add(new BarEntry((float) 0.0,(-1)*(float)jumlahOutput));
        BarDataSet set = new BarDataSet(entry,"In - Out");
        set.setColors(ColorTemplate.JOYFUL_COLORS);
        BarData data = new BarData(set);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisLeft().setDrawLabels(false);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getAxisRight().setDrawLabels(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        chart.getDescription().setText("");
        chart.getXAxis().setDrawLabels(false);
        chart.setPinchZoom(false);
        chart.setScaleEnabled(false);
        chart.setDrawGridBackground(false);
        chart.animateXY(500,500);
        chart.notifyDataSetChanged();
        chart.setData(data);
        chart.invalidate();


//        RealmResults<dataOutput> tempOut=realm.where(dataOutput.class).equalTo("bulan",Calendar.getInstance().get(Calendar.MONTH)+1).distinct("category");
//        ArrayList<String> output = new ArrayList<String>();
//        for(dataOutput vara:tempOut){
//            vara
//        }

        realm.commitTransaction();

    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        realm.close();
    }

}
