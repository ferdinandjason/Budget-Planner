package com.ferdinand.budgetplanner;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link tab3.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link tab3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class tab3 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public tab3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment tab3.
     */
    // TODO: Rename and change types and number of parameters
    public static tab3 newInstance(String param1, String param2) {
        tab3 fragment = new tab3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    protected Realm realm;
    private PieChart chart;
    protected Spinner spinner;
    protected String bulanPilih;
    protected RadioGroup rad;
    protected RadioButton in;

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
        View view = inflater.inflate(R.layout.fragment_tab3, container, false);
        // rad=(RadioGroup) view.findViewById(R.id.radio);
        // in=(RadioButton) view.findViewById(R.id.income);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // in.setChecked(true);
                startActivity(new Intent(getActivity(),add_data.class));
            }
        });
        chart= (PieChart) view.findViewById(R.id.PieChart2);
        spinner = (Spinner) view.findViewById(R.id.spinner2);
        final String[] dataBulan = {"Januari","Februari","Maret","April","Mei","Juni","Juli","Agustus","September","Oktober","November","Desember"};

        ArrayAdapter spinnerArray = new ArrayAdapter(getActivity().getApplicationContext(),android.R.layout.simple_dropdown_item_1line,dataBulan);
        spinner.setAdapter(spinnerArray);

        bulanPilih = dataBulan[Calendar.getInstance().get(Calendar.MONTH)];
        ArrayAdapter newAdap = (ArrayAdapter)spinner.getAdapter();
        int defaultPosition = newAdap.getPosition(bulanPilih);
        spinner.setSelection(defaultPosition);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView selectedText = (TextView) adapterView.getChildAt(0);
                selectedText.setTextColor(Color.BLACK);
                bulanPilih = spinner.getSelectedItem().toString();
                setData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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

    public void setData(){
        HashMap<String,Integer> translate = new HashMap<String,Integer>();
        translate.put("Januari",1);
        translate.put("Februari",2);
        translate.put("Maret",3);
        translate.put("April",4);
        translate.put("Mei",5);
        translate.put("Juni",6);
        translate.put("Juli",7);
        translate.put("Agustus",8);
        translate.put("September",9);
        translate.put("Oktober",10);
        translate.put("November",11);
        translate.put("Desember",12);

        realm=Realm.getDefaultInstance();
        realm.beginTransaction();
        ArrayList<Integer> balanceBaseCategory = new ArrayList<Integer>();
        ArrayList<String> category = new ArrayList<String>();
        RealmResults<dataInput> categoryDistinct = realm.where(dataInput.class).distinct("category");

        for(dataInput varc:categoryDistinct){
            category.add(varc.getCategory());
        }
        for(String vara:category){
            RealmResults<dataInput> temporary = realm.where(dataInput.class).equalTo("category",vara).equalTo("bulan",translate.get(bulanPilih)).findAll();
            int totalBalance=0;
            for(dataInput varb:temporary){
                totalBalance+=varb.getBalance();
            }
            balanceBaseCategory.add(totalBalance);
        }
        List<PieEntry> entries = new ArrayList<>();
        for(int i=0;i<category.size();i++){
            entries.add(new PieEntry(balanceBaseCategory.get(i),category.get(i)));
        }

        PieDataSet set = new PieDataSet(entries,"");
        ArrayList<Integer> Colorss = new ArrayList<Integer>();
        for(int col:ColorTemplate.PASTEL_COLORS){
            Colorss.add(col);
        }
        for(int col:ColorTemplate.JOYFUL_COLORS){
            Colorss.add(col);
        }
        for(int col:ColorTemplate.MATERIAL_COLORS){
            Colorss.add(col);
        }
        for(int col:ColorTemplate.COLORFUL_COLORS){
            Colorss.add(col);
        }
        set.setColors(Colorss);
        set.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(12f);
        PieData data = new PieData(set);
        chart.setEntryLabelTextSize(12f);
        chart.setEntryLabelColor(Color.BLACK);
        chart.animateXY(500,500);
        chart.setCenterText("Pemasukkan");
        chart.getDescription().setText("");
        chart.notifyDataSetChanged();
        chart.setData(data);
        chart.invalidate();
        realm.commitTransaction();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        realm.close();
    }

}
