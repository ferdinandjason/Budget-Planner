package com.ferdinand.budgetplanner;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class dataInput extends RealmObject {
    @PrimaryKey
    private int id=0;
    private String date;
    private int bulan;
    private int balance;
    private String category;
    private String coment;

    public dataInput(){}
    public dataInput(int id,int bulan,int balance,String category,String date,String comentt){
        setId(id);
        setBulan(bulan);
        setBalance(balance);
        setCategory(category);
        setDate(date);
        setComent(comentt);
    }

    public void setBulan(int bulan){
        this.bulan=bulan;
    }
    public void setBalance(int balance){
        this.balance=balance;
    }
    public void setCategory(String category){
        this.category=category;
    }
    public void setId(int id){
        this.id=id;
    }
    public void setDate(String date){
        this.date=date;
    }
    public void setComent(String comentt){
        this.coment=comentt;
    }

    public int getBulan(){
        return this.bulan;
    }
    public int getBalance(){
        return this.balance;
    }
    public String getCategory(){
        return this.category;
    }
    public int getId(){
        return this.id;
    }
    public String getDate(){
        return this.date;
    }
    public String getComent() {
        return coment;
    }
}
