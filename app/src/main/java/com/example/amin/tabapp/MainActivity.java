package com.example.amin.tabapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.amin.tabapp.model.Employer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    String getInfoURL = "http://m4saad.fekrait.com/test-api/";
    List<Employer> employerInfo;
    TableLayout tableLayout;
    String workTotalhours, restTotalhours , name,month;
    Long monthlyWorkHours, monthlyRestHours, monthlyWorkTime = Long.valueOf(0), monthlyRestTime;
    TextView eName,eDate;
    int workHours;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tableLayout = findViewById(R.id.main_table);

        eName = findViewById(R.id.name);
        eDate = findViewById(R.id.date);

        if (isOnline()) {
            getAPIInfo();
            addHeader();

        } else {
            Toast.makeText(this,"You are disconnected with the internet",Toast.LENGTH_LONG).show();
        }

    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    public void addHeader() {

        TableRow tableRow = new TableRow(this);
        tableRow.setBackgroundColor(Color.GRAY);
        tableRow.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        TextView dateTitle = new TextView(this);
        dateTitle.setText("Date");
        dateTitle.setGravity(Gravity.CENTER);
        dateTitle.setTextColor(Color.BLACK);
        dateTitle.setPadding(8, 8, 8, 8);
        tableRow.addView(dateTitle);

        TextView attendTitle = new TextView(this);
        attendTitle.setText("Attend");
        attendTitle.setGravity(Gravity.CENTER);
        attendTitle.setTextColor(Color.BLACK);
        dateTitle.setPadding(8, 8, 8, 8);
        tableRow.addView(attendTitle);

        TextView LeaveTitle = new TextView(this);
        LeaveTitle.setText("Leave");
        LeaveTitle.setGravity(Gravity.CENTER);
        LeaveTitle.setTextColor(Color.BLACK);
        dateTitle.setPadding(8, 8, 8, 8);
        tableRow.addView(LeaveTitle);

        TextView WorkHrTitle = new TextView(this);
        WorkHrTitle.setText("Work Hours");
        WorkHrTitle.setGravity(Gravity.CENTER);
        WorkHrTitle.setTextColor(Color.BLACK);
        dateTitle.setPadding(8, 8, 8, 8);
        tableRow.addView(WorkHrTitle);

        TextView restHrTitle = new TextView(this);
        restHrTitle.setText("Rest Hours");
        restHrTitle.setGravity(Gravity.CENTER);
        restHrTitle.setTextColor(Color.BLACK);
        dateTitle.setPadding(8, 8, 8, 8);
        tableRow.addView(restHrTitle);


        tableLayout.addView(tableRow, new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

    }

    public void addData() {
        for (Employer employer : employerInfo) {

            TableRow tableRow = new TableRow(this);
            tableRow.setBackgroundColor(Color.WHITE);
            tableRow.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));

            TextView date = new TextView(this);
            date.setText(employer.getAttend_date());
            date.setTextColor(Color.BLACK);
            date.setGravity(Gravity.CENTER);
            date.setPadding(8, 8, 8, 8);
            tableRow.addView(date);

            TextView attend = new TextView(this);
            attend.setText(employer.getAttend_time());
            attend.setTextColor(Color.BLACK);
            attend.setGravity(Gravity.CENTER);
            attend.setPadding(8, 8, 8, 8);
            tableRow.addView(attend);

            TextView leave = new TextView(this);
            leave.setText(employer.getLeave_time());
            leave.setTextColor(Color.BLACK);
            leave.setGravity(Gravity.CENTER);
            leave.setPadding(8, 8, 8, 8);
            tableRow.addView(leave);

            TextView workHours = new TextView(this);
            workHours.setText(employer.getWorkHours());
            workHours.setTextColor(Color.BLACK);
            workHours.setGravity(Gravity.CENTER);
            workHours.setPadding(8, 8, 8, 8);
            tableRow.addView(workHours);

            TextView restHours = new TextView(this);
            restHours.setText(employer.getRestHours());
            restHours.setTextColor(Color.BLACK);
            restHours.setGravity(Gravity.CENTER);
            restHours.setPadding(8, 8, 8, 8);
            tableRow.addView(restHours);

            tableLayout.addView(tableRow, new TableLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
        }

        addFooter();
    }

    public void addFooter() {

        Long minutes = monthlyWorkTime/(60 * 1000) % 60;
        if (minutes >30){
        monthlyWorkHours = monthlyWorkTime / (60 * 60 * 1000) +1;
        }else {
            monthlyWorkHours = monthlyWorkTime / (60 * 60 * 1000);
        }

        Long restMinutes = monthlyRestTime/(60 * 1000) % 60;

        if (restMinutes >30){
            monthlyRestHours = monthlyRestTime / (60 * 60 * 1000)+1;
        }else {
            monthlyRestHours = monthlyRestTime / (60 * 60 * 1000);
        }

        TableRow tableRow = new TableRow(this);
        tableRow.setBackgroundColor(Color.WHITE);
        tableRow.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        TextView date = new TextView(this);
        date.setText("Total Hours");
        date.setTextColor(Color.BLUE);
        date.setGravity(Gravity.CENTER);
        date.setPadding(8, 8, 8, 8);
        tableRow.addView(date);

        TextView attend = new TextView(this);
        attend.setText(String.valueOf("~ "+monthlyWorkHours)+"/"+String.valueOf(workHours));
        attend.setTextColor(Color.BLUE);
        attend.setGravity(Gravity.CENTER);
        attend.setPadding(8, 8, 8, 8);
        tableRow.addView(attend);

        TextView leave = new TextView(this);
        leave.setText("Rest Hours");
        leave.setTextColor(Color.BLUE);
        leave.setGravity(Gravity.CENTER);
        leave.setPadding(8, 8, 8, 8);
        tableRow.addView(leave);

        TextView workHours = new TextView(this);
        workHours.setText(String.valueOf("~ "+monthlyRestHours)+" Hrs");
        workHours.setTextColor(Color.BLUE);
        workHours.setGravity(Gravity.CENTER);
        workHours.setPadding(8, 8, 8, 8);
        tableRow.addView(workHours);

        TextView restHours = new TextView(this);
        restHours.setText("THANKS");
        restHours.setTextColor(Color.RED);
        restHours.setGravity(Gravity.CENTER);
        restHours.setPadding(8, 8, 8, 8);
        tableRow.addView(restHours);

        tableLayout.addView(tableRow, new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

    }

    public void getAPIInfo() {
        String url = getInfoURL;

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pd.dismiss();
                        // Do something with response
                        String sResponse = response.toString();
                        Log.d("response", sResponse);

                        try {
                            JSONObject jsonResponse = new JSONObject(sResponse);
                            name = jsonResponse.getString("name");
                            workHours = jsonResponse.getInt("basic_work_hours");
                            month = jsonResponse.getString("month");


                            JSONArray ar = jsonResponse.getJSONArray("data");
                            employerInfo = new ArrayList<>();
                            for (int i = 0; i < ar.length(); i++) {
                                JSONObject jsonObject = ar.getJSONObject(i);

                                int numberOfDays = ar.length();
                                Long totalhours = Long.valueOf(workHours * 60 * 60 * 1000);

                                Employer employer = new Employer();

                                employer.setAttend_date(jsonObject.getString("attend_date"));
                                employer.setAttend_time(jsonObject.getString("attend_time"));
                                employer.setLeave_date(jsonObject.getString("leave_date"));
                                employer.setLeave_time(jsonObject.getString("leave_time"));

                                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH);

                                Date d1 = null;
                                Date d2 = null;

                                try {
                                    d1 = format.parse(jsonObject.getString("attend_date") + " "
                                            + jsonObject.getString("attend_time"));
                                    d2 = format.parse(jsonObject.getString("leave_date") + " "
                                            + jsonObject.getString("leave_time"));

                                    //in milliseconds
                                    long diff = d2.getTime() - d1.getTime();
                                    monthlyWorkTime = monthlyWorkTime + diff;
                                    monthlyRestTime = totalhours - monthlyWorkTime;

                                    long diffSeconds = diff / 1000 % 60;
                                    long diffMinutes = diff / (60 * 1000) % 60;
                                    long diffHours = diff / (60 * 60 * 1000) % 24;

                                    Long rest = Math.max(0, 60 * 60 * 8 * 1000 - diff);
                                    long restSeconds = rest / 1000 % 60;
                                    long restMinutes = rest / (60 * 1000) % 60;
                                    long restHours = rest / (60 * 60 * 1000) % 24;


                                    workTotalhours = String.format(Locale.getDefault(),
                                            "%d:%02d:%02d", diffHours, diffMinutes, diffSeconds);

                                    restTotalhours = String.format(Locale.getDefault(),
                                            "%d:%02d:%02d", restHours, restMinutes, restSeconds);


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                employer.setWorkHours(workTotalhours);
                                employer.setRestHours(restTotalhours);

                                employerInfo.add(employer);
                            }
                            eName.setText(name);
                            eDate.setText("Date: " + month);
                            addData();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred

                    }
                }
        );

        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonObjectRequest);

        pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("Loading The data....");
        pd.show();
    }

}
