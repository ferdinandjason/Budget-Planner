package com.ferdinand.budgetplanner;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.calendar.CalendarView;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.models.*;
import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link tab0.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link tab0#newInstance} factory method to
 * create an instance of this fragment.
 */
public class tab0 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public tab0() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment tab0.
     */
    // TODO: Rename and change types and number of parameters
    public static tab0 newInstance(String param1, String param2) {
        tab0 fragment = new tab0();
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

    protected Realm realm;

    public static Calendar toCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_tab0, container, false);
        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        minDate.add(Calendar.MONTH,-1);
        minDate.add(Calendar.DAY_OF_MONTH,1);
        maxDate.add(Calendar.YEAR,1);
        List<CalendarEvent> eventList = new ArrayList<>();
        AgendaCalendarView agendaCalendar=view.findViewById(R.id.calendar_view_agenda);

        realm=Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<dataInput> input = realm.where(dataInput.class).equalTo("bulan",Calendar.getInstance().get(Calendar.MONTH)+1).findAll();
        RealmResults<dataOutput> output = realm.where(dataOutput.class).equalTo("bulan",Calendar.getInstance().get(Calendar.MONTH)+1).findAll();
        Log.d(Integer.toString(input.size()),"hehe");
        for(dataInput temp:input){
            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();
            String[] tempo=temp.getDate().split("/");
            start.set(Integer.parseInt(tempo[2]),Integer.parseInt(tempo[1])-1,Integer.parseInt(tempo[0]));
            end.set(Integer.parseInt(tempo[2]),Integer.parseInt(tempo[1])-1,Integer.parseInt(tempo[0]));
            Log.d("ferdinand",tempo[2]+" "+tempo[1]+" "+tempo[0]);
            eventList.add(new BaseCalendarEvent("+ "+customFormat("Rp ###,###,##0.00",temp.getBalance())+" - "+temp.getComent(),"","", Color.BLUE,start,end,false));
        }
        for(dataOutput temp:output){
            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();
            String[] tempo=temp.getDate().split("/");
            start.set(Integer.parseInt(tempo[2]),Integer.parseInt(tempo[1])-1,Integer.parseInt(tempo[0]));
            end.set(Integer.parseInt(tempo[2]),Integer.parseInt(tempo[1])-1,Integer.parseInt(tempo[0]));
            Log.d("ferdinand",tempo[2]+" "+tempo[1]+" "+tempo[0]);
            eventList.add(new BaseCalendarEvent("- "+customFormat("Rp ###,###,##0.00",temp.getBalance())+" - "+temp.getComent(),"","", Color.RED,start,end,false));
        }



        realm.commitTransaction();
        agendaCalendar.init(eventList, minDate, maxDate, Locale.getDefault(), new CalendarPickerController() {
            @Override
            public void onDaySelected(DayItem dayItem) {

            }

            @Override
            public void onEventSelected(CalendarEvent event) {

            }

            @Override
            public void onScrollToDate(Calendar calendar) {

            }
        });



        return view;
    }

    static public String customFormat(String pattern, int s ) {
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        String stringFormatOutput = myFormatter.format(s);
        return stringFormatOutput;
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
        } else throw new RuntimeException(context.toString()
                + " must implement OnFragmentInteractionListener");
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

    /**
     * Initializes the event
     *
     * @param id          The id of the event.
     * @param color       The color of the event.
     * @param title       The title of the event.
     * @param description The description of the event.
     * @param location    The location of the event.
     * @param dateStart   The start date of the event.
     * @param dateEnd     The end date of the event.
     * @param allDay      Int that can be equal to 0 or 1.
     * @param duration    The duration of the event in RFC2445 format.
     */

}
