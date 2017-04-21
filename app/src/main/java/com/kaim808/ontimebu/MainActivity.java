package com.kaim808.ontimebu;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.kaim808.ontimebu.model.ArrivalEstimate;
import com.kaim808.ontimebu.model.Result;
import com.kaim808.ontimebu.model.Root;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    // public static final String BASE_URL = "https://www.bu.edu/bumobile/rpc/bus/livebus.json.php/";
    public static final String BASE_URL = "https://www.bu.edu/bumobile/rpc/bus/";

    @BindView(R.id.timesTextView) TextView mTimesTextView;
    @BindView(R.id.swipeContainer) SwipeRefreshLayout mSwipeLayout;
    @BindView(R.id.lastUpdatedTextView) TextView mLastUpdatedTextView;
    @BindView(R.id.countDownTextView) TextView mCountDownTextView;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final ShuttleEndpointInterface apiService = retrofit.create(ShuttleEndpointInterface.class);

        apiCall(apiService);

        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                apiCall(apiService);
            }
        });

    }

    private void apiCall(final ShuttleEndpointInterface apiService) {
        Call<Root> call = apiService.getRoot("php");
        mSwipeLayout.setRefreshing(true);

        call.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(final Call<Root> call, Response<Root> response) {
                int statusCode = response.code();
                Log.e("kaikai", String.valueOf(statusCode));
                Root root = response.body();

                // for now, we're considering only Myles; 	ID: 4160726
                String stopID = "4160738";

                List<Result> results = root.getResultSet().getResult();

                List<String> arrivalTimes = new ArrayList<>();

                // populate arrivalTimes with arrivalTimes for Myles
                for (Result result : results) {

                    List<ArrivalEstimate> arrivalEstimates = result.getArrivalEstimates();
                    if (arrivalEstimates == null) continue;

                    for (ArrivalEstimate arrivalEstimate : arrivalEstimates) {
                        if (arrivalEstimate.getStopId().equals(stopID)) {
                            arrivalTimes.add(arrivalEstimate.getArrivalAt());
                            break;
                        }
                    }
                }

                // sort arrivalTimes since buses are not necessarily in order
                Collections.sort(arrivalTimes);

                // here we format the arrivalTime strings
                String arrivalTimesString = "";
                for (String arrivalTime : arrivalTimes) {
                    String hour = arrivalTime.substring(11, 13);
                    String minute = arrivalTime.substring(14, 16);
                    // String second = arrivalTime.substring(17, 19);

                    if (hour.compareTo("12") > 0) {
                        hour = String.valueOf(Integer.valueOf(hour) - 12);
                    }
                    if (hour.compareTo("00") == 0) {
                        hour = "12";
                    }

                    arrivalTimesString += hour + ":" + minute + "\n";
                }

                mTimesTextView.setText(arrivalTimesString);
                mSwipeLayout.setRefreshing(false);



                Calendar calendar = Calendar.getInstance();
                int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                if (currentHour > 12) currentHour -= 12;
                if (currentHour == 0) currentHour = 12;

                int currentMinute = calendar.get(Calendar.MINUTE);
                String strCurrentMinute = currentMinute < 10 ? "0" + currentMinute : String.valueOf(currentMinute);

                mLastUpdatedTextView.setText("Last updated: " + currentHour + ":" + strCurrentMinute);


                if (arrivalTimes.isEmpty()) {
                    mTimesTextView.setText(R.string.noBusOnline);
                    mCountDownTextView.setText(R.string.zeroTimeString);
                    return;
                }

                // assuming that the first time stamp is the soonest bus arrival, as in we sorted arrivalTimes
                String arrivalTime = arrivalTimes.get(0);
                int arrivalHour = Integer.valueOf(arrivalTime.substring(11,13));
                if (arrivalHour > 12) arrivalHour -= 12;
                if (arrivalHour == 0) arrivalHour = 12;
                int arrivalMinute = Integer.valueOf(arrivalTime.substring(14, 16));
                int arrivalSeconds = Integer.valueOf(arrivalTime.substring(17, 19));


                long currentTimeEpoch = calendar.getTimeInMillis();

                calendar.set(Calendar.HOUR, arrivalHour);
                calendar.set(Calendar.MINUTE, arrivalMinute);
                calendar.set(Calendar.SECOND, arrivalSeconds);
                long arrivalTimeEpoch = calendar.getTimeInMillis();

                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
                countDownTimer = new CountDownTimer(arrivalTimeEpoch - currentTimeEpoch, 1000) {
                    @Override
                    public void onTick(long l) {
                        l = l/1000;
                        String minutes = String.valueOf((int) l / 60);

                        /* Note, countdown is disabled around 12 o'clock as a workaround for a bug.
                        Won't be using the app around 12 so a fix is not imperative.
                        */
                        if (Integer.valueOf(minutes) > 100) {
                            countDownTimer.cancel();
                            return;
                        }

                        String seconds = String.valueOf(l % 60);
                        if (Integer.valueOf(seconds) < 10) seconds = "0" + seconds;
                        mCountDownTextView.setText(minutes + ":" + seconds);
                    }

                    @Override
                    public void onFinish() {
                        apiCall(apiService);
                    }
                };

                countDownTimer.start();

            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Connection failure, try again.", Toast.LENGTH_SHORT).show();
                mSwipeLayout.setRefreshing(false);
            }
        });

    }
}


