# Budget-Planner

## Daftar Isi
- [1. Prerequisite]
- [1.1 Realm.io]
- [1.2 MPAndroidChart]
- [1.3 AgendaCalendarView]
- [2 Overview]

## Prerequisite
Software/Database/Project usage in this project
### Realm.io
![alt text](https://github.com/realm/realm-java/blob/master/logo.png)

Realm is a mobile database that runs directly inside phones, tablets or wearables. 
This repository holds the source code for the usage Java version of Realm, which currently runs only on Android.

Realm.io runs on :
    - Android Studio version 1.5.1 or higher
    - JDK version 7.0 or higher
    - A recent version of the Android SDK
    - Android API Level 9 or higher (Android 2.3 and above)

Features :
1. Mobile-first: Realm is the first database built from the ground up to run directly inside phones, tablets and wearables.
2. Simple: Data is directly exposed as objects and queryable by code, removing the need for ORM's riddled with performance & maintenance issues. Plus, we've worked hard to keep our API down to very few classes: most of our users pick it up intuitively, getting simple apps up & running in minutes.
3. Modern: Realm supports easy thread-safety, relationships & encryption.
4. Fast: Realm is faster than even raw SQLite on common operations, while maintaining an extremely rich feature set.


You must provide an Android context. A good place to initialize Realm is in onCreate on an application subclass:
```java
public class MyApplication extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    Realm.init(this);
  }
}
```

Want's more info about Realm.io click here [link](https://github.com/realm/realm-java)

### MPAndroidCharts-Realm
MPAndroidChart :zap: is a powerful & easy to use chart library for Android. It runs on API level 8 and upwards.
As an additional feature, this library allows cross-platform development between Android and iOS as an iOS version of this
library is also available.

Pie Chart (usage for this project):
![alt text](https://camo.githubusercontent.com/7e8a4a3c938c21d032d44d999edd781b6e146f2a/68747470733a2f2f7261772e6769746875622e636f6d2f5068696c4a61792f4d50416e64726f696443686172742f6d61737465722f73637265656e73686f74732f73696d706c6564657369676e5f7069656368617274312e706e67)

Usage MPAndroidCharts-Realm.io is easyy
```java
// get realm instance
Realm realm = Realm.getDefaultInstance();

// load your data from Realm.io database
RealmResults<YourData> results = realm.where(YourData.class).findAll();

// create a DataSet and specify fields, MPAndroidChart-Realm does the rest
RealmBarDataSet<YourData> dataSet = new RealmBarDataSet<YourData>(results, "xValue", "yValue");

// create a data object with the dataset 
BarData data = new BarData(dataSet);
chart.setData(data);
chart.invalidate(); // refresh
```
MPAndroidChart-Realm allows to directly plot / draw data from Realm.io mobile database.

Features:
1. 8 different chart types
2. Scaling on both axes (with touch-gesture, axes separately or pinch-zoom)
3. Dragging / Panning (with touch-gesture)
4. Combined-Charts (line-, bar-, scatter-, candle-data)
5. Dual (separate) Axes
6. Customizable Axes (both x- and y-axis)
7. Highlighting values (with customizable popup-views)
8. Save chart to SD-Card (as image, or as .txt file)
9. Predefined color templates
10. Legends (generated automatically, customizable)
11. Animations (build up animations, on both xPx- and yPx-axis)
12. Limit lines (providing additional information, maximums, ...)
13. Fully customizable (paints, typefaces, legends, colors, background, gestures, dashed lines, ...)
14. Smooth zooming and scrolling for up to 10.000 data points in Line- and BarChart
15. Gradle support
16. Plotting data directly from Realm.io mobile database: MPAndroidChart-Realm :zap:

Want's more info about MPAndroidCharts click here [link](https://github.com/PhilJay/MPAndroidChart)

### Agenda Calendar View
This library replicates the basic features of the Calendar and Agenda views from the Sunrise Calendar (now Outlook) app, coupled with some small design touch from the Google Calendar app.

![alt text](https://raw.githubusercontent.com/Tibolte/AgendaCalendarView/master/demo.gif)

Features:
1. Parallax items like in Google Calendar
2. Easier way to provide your own list of events (i.e when receiving objects from a web API)

Want's more info about Agenda Calendar View click here [link](https://github.com/Tibolte/AgendaCalendarView)

## Overview

Alasan dikembangkannya aplikasi ini karena saya melihat banyak orang atau yang lebih khususnya mahasiswa, sering menghabiskan uang
mereka di awal bulan, kemudian diakhir bulan seperti menderita. Oleh karena itu, diperlukan sesuatu pencatatan, dimana mencatat 
pengeluaran dan pemasukan, agar mahasiwa bisa mempekirakan dalam menggunakan uangnya yang tersisa untuk kehidupan yang tidak menderita
Pencatatan membuat mahasiswa bosan, oleh karena itu perlu applikasi yang membantu, serta tidak membuat bosan.

Dengan aplikasi ini diharapkan :
1. Mahasiswa lebih pandai dalam mengatur uang bulanan mereka.
2. Pada akhir bulan tidak menerapkan pola makanan yang tidak sehat.
3. Bisa menabung uang.
4. Mahasiswa tidak bosan dalam menginputkan datanya

![alt text](https://user-images.githubusercontent.com/32606656/33444619-605822c4-d62d-11e7-9232-eb091c820f57.png)
![alt text](https://user-images.githubusercontent.com/32606656/33444630-66e2941c-d62d-11e7-99d6-7e0ee3c60775.png)

Aplikasi pengatur keuangan yang user friendly, sehingga pengguna tidak bosan dalam penginputan record data.
Dengan harapan kedepannya aplikasi ini dapat dikembangkan seperti :
2. memiliki fitur foto dalam sebuah record.
3. memiliki fitur pencatatan hutang.
4. dengan memfoto nota, data dapat terisi dengan sempurna

Thanks to: ALPRO too :)
