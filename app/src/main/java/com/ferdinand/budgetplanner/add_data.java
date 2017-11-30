package com.ferdinand.budgetplanner;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieRadarChartBase;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.realm.implementation.RealmPieDataSet;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.w3c.dom.Text;

import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

import static com.ferdinand.budgetplanner.R.styleable.View;
import static com.ferdinand.budgetplanner.tab0.customFormat;
import static java.security.AccessController.getContext;

public class add_data extends AppCompatActivity {

    protected EditText openDatePicker;
    protected Calendar myCalendar;
    protected Realm realm;
    protected RadioButton boolIncome;
    protected RadioButton boolOutcome;
    protected TextView tanggal;
    protected EditText balance;
    protected EditText notes;
    protected AutoCompleteTextView category;
    protected TextView coba;
    protected Date date;

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        realm=Realm.getDefaultInstance();

        openDatePicker = (EditText) findViewById(R.id.editTanggal);

        ButterKnife.bind(this);
        myCalendar=Calendar.getInstance();

        openDatePicker.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new DatePickerDialog(add_data.this, new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view,int year,int month,int datOfMonth){
                        myCalendar.set(Calendar.YEAR,year);
                        myCalendar.set(Calendar.MONTH,month);
                        myCalendar.set(Calendar.DAY_OF_MONTH,datOfMonth);
                        String formatTanggal = "dd/MM/yyyy";
                        SimpleDateFormat sdf = new SimpleDateFormat(formatTanggal);
                        openDatePicker.setText(sdf.format(myCalendar.getTime()));
                    }
                },
                    myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        Button cancel = (Button) findViewById(R.id.button2);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        final PieChart chart = (PieChart) findViewById(R.id.PieChart);
        final PieChart chart2= (PieChart) findViewById(R.id.PieChart2);
        final BarChart chart3= (BarChart) findViewById(R.id.BarChart);


        Button submit = (Button) findViewById(R.id.button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
                chart.invalidate();
                chart.notifyDataSetChanged();
                chart2.invalidate();
                chart2.notifyDataSetChanged();
                chart3.invalidate();
                chart3.notifyDataSetChanged();
                startActivity(new Intent(add_data.this,UtamaActivity.class));
                finish();
            }
        });
        boolIncome = (RadioButton) findViewById(R.id.income);
        boolOutcome = (RadioButton) findViewById(R.id.outcome);
        category = (AutoCompleteTextView) findViewById(R.id.editCategory);
        String[] data;
        ArrayList<String> dataList = new ArrayList<String>();
        //dataList.add("Add");

        realm.beginTransaction();
        dataList.clear();
        RealmResults<dataInput> temporary = realm.where(dataInput.class).distinct("category");
        for(dataInput hehe: temporary){
            dataList.add(hehe.getCategory());
        }
        RealmResults<dataOutput> temporary2 = realm.where(dataOutput.class).distinct("category");
        for(dataOutput hehe: temporary2){
            dataList.add(hehe.getCategory());
        }
        data =dataList.toArray(new String[dataList.size()]);

        realm.commitTransaction();
        ArrayAdapter arrayAdapter = new ArrayAdapter(add_data.this,android.R.layout.simple_list_item_1,data);
        category.setAdapter(arrayAdapter);
        category.setThreshold(1);

        category.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v,boolean hasFocus){
                if(hasFocus) category.showDropDown();
            }
        });
        balance = (EditText) findViewById(R.id.editBalance);

    }

    protected void submit(){
        boolIncome = (RadioButton) findViewById(R.id.income);
        boolOutcome = (RadioButton) findViewById(R.id.outcome);
        tanggal = (TextView) findViewById(R.id.editTanggal);
        balance = (EditText) findViewById(R.id.editBalance);
        category = (AutoCompleteTextView) findViewById(R.id.editCategory);
        notes = (EditText) findViewById(R.id.editNotes);

        String passingTanggal = tanggal.getText().toString();
        String passingBalance = balance.getText().toString();
        String passingCategory = category.getText().toString();
        String passingInput = "";
        if(boolIncome.isChecked()) passingInput="IN";
        if(boolOutcome.isChecked()) passingInput="OUT";

        String[] temp = passingTanggal.split("/");
        int bulanObj=Integer.parseInt(temp[1]);
        int balanceObj=Integer.parseInt(passingBalance);
        int nextID=0;

        if(passingInput=="OUT"){
            realm.beginTransaction();

            if(realm.isEmpty()) nextID=0;
            else nextID = (int) UUID.randomUUID().getMostSignificantBits();

            dataOutput newData = new dataOutput(nextID,bulanObj,balanceObj,passingCategory,tanggal.getText().toString(),notes.getText().toString());
            realm.copyToRealm(newData);

            realm.commitTransaction();
        }
        else{
            realm.beginTransaction();

            if(realm.isEmpty()) nextID=0;
            else nextID = (int) UUID.randomUUID().getMostSignificantBits();

            dataInput newData = new dataInput(nextID,bulanObj,balanceObj,passingCategory,tanggal.getText().toString(),notes.getText().toString());
            realm.copyToRealm(newData);

            realm.commitTransaction();
        }
    }

    protected void onDestroy(){
        super.onDestroy();
        realm.close();    }


}

