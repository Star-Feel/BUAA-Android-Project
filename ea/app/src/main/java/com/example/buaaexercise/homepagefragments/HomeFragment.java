package com.example.buaaexercise.homepagefragments;

import static com.example.buaaexercise.backend.dbFunction.DBFunction.getFrequencyInDate;
import static com.example.buaaexercise.backend.dbFunction.DBFunction.getSportTimeInDate;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.buaaexercise.MainActivity;
import com.example.buaaexercise.sports.dayDecorators.CurrentDayDecorator;
import com.example.buaaexercise.sports.dayDecorators.MonthDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import com.example.buaaexercise.R;
import com.example.buaaexercise.sports.SportkindActivity;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.IntStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static HomeFragment newInstance(String param1) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        // args.putString(ARG_PARAM2, param2);
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setSports();
        setButtonStart(view);
        setTvHint(view);
        initImageMap();
        mTvWeatherName = view.findViewById(R.id.tv_weather_name);
        mIvWeather = view.findViewById(R.id.iv_weather);
        mTvTemperature = view.findViewById(R.id.tv_temperature);
        try {
            setOnlineWeather(view);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        setCalendar(view);
    }

    private Button mBtnStart;

    void setButtonStart(View view) {
        mBtnStart = view.findViewById(R.id.button_startsport);
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SportkindActivity.class);
                startActivity(intent);
            }
        });
    }

    private TextView mTvHint;

    void setTvHint(View view) {
        mTvHint = view.findViewById(R.id.tv_home_hint);
        mTvHint.setSelected(true);
    }

    private TextView mTvWeatherName;
    private ImageView mIvWeather;

    private TextView mTvTemperature;
    private HashMap<Integer, Integer> weatherImageMap = new HashMap<>();

    private static ArrayList<String> sportName = new ArrayList<>();

    private void setSports() {
        sportName.add("run");         //跑步
        sportName.add("walk");        //徒步
        sportName.add("fit");         //健身
        sportName.add("td");          //Td
        sportName.add("swim");        //游泳
        sportName.add("basketball");  //篮球
        sportName.add("tennis");      //网球
        sportName.add("tabletennis"); //乒乓球
        sportName.add("billiard");    //桌球
        sportName.add("badminton");   //羽毛球
        sportName.add("volleyball");  //排球
        sportName.add("soccer");      //足球
        sportName.add("frisbee");
    }

    public static ArrayList<String> getSportName() { return sportName; }

    private ArrayList<String> weathers = new ArrayList<>();

    private static String weatherStr;
    private String weatherCode;

    public static String getWeather() {
        return weatherStr;
    }
    private String temperature;

    private String urlApi = "https://api.seniverse.com/v3/weather/now.json?key=S6QoUoSRpmRDld0Mu&location=beijing&language=zh-Hans&unit=c";

    void initImageMap() {
        weatherImageMap.put(0, R.mipmap.weather_sunny);
        weatherImageMap.put(1, R.drawable.weather_1);
        weatherImageMap.put(2, R.drawable.weather_2);
        weatherImageMap.put(3, R.drawable.weather_3);
        weatherImageMap.put(4, R.drawable.weather_4);
        weatherImageMap.put(5, R.drawable.weather_5);
        weatherImageMap.put(6, R.drawable.weather_6);
        weatherImageMap.put(7, R.drawable.weather_7);
        weatherImageMap.put(8, R.drawable.weather_8);
        weatherImageMap.put(9, R.drawable.weather_9);
        weatherImageMap.put(10, R.drawable.weather_10);
        weatherImageMap.put(11, R.drawable.weather_11);
        weatherImageMap.put(12, R.drawable.weather_12);
        weatherImageMap.put(13, R.drawable.weather_13);
        weatherImageMap.put(14, R.drawable.weather_14);
        weatherImageMap.put(15, R.drawable.weather_15);
        weatherImageMap.put(16, R.drawable.weather_16);
        weatherImageMap.put(17, R.drawable.weather_17);
        weatherImageMap.put(18, R.drawable.weather_18);
        weatherImageMap.put(19, R.drawable.weather_19);
        weatherImageMap.put(20, R.drawable.weather_20);
        weatherImageMap.put(21, R.drawable.weather_21);
        weatherImageMap.put(22, R.drawable.weather_22);
        weatherImageMap.put(23, R.drawable.weather_23);
        weatherImageMap.put(24, R.drawable.weather_24);
        weatherImageMap.put(25, R.drawable.weather_25);
        weatherImageMap.put(26, R.drawable.weather_26);
        weatherImageMap.put(27, R.drawable.weather_27);
        weatherImageMap.put(28, R.drawable.weather_28);
        weatherImageMap.put(29, R.drawable.weather_29);
        weatherImageMap.put(30, R.drawable.weather_30);
        weatherImageMap.put(31, R.drawable.weather_31);
        weatherImageMap.put(32, R.drawable.weather_32);
        weatherImageMap.put(33, R.drawable.weather_33);
        weatherImageMap.put(34, R.drawable.weather_34);
        weatherImageMap.put(35, R.drawable.weather_35);
        weatherImageMap.put(36, R.drawable.weather_36);
        weatherImageMap.put(37, R.drawable.weather_37);
        weatherImageMap.put(38, R.drawable.weather_38);
        weatherImageMap.put(99, R.drawable.weather_99);
    }

    void setOnlineWeather(View view) throws IOException, JSONException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();//创建一个OkHttp实例
                    Request request = new Request.Builder().url(urlApi).build();//创建Request对象发起请求,记得替换成你自己的key
                    Log.d("Request", "-------------------------sending---------------------------");
                    Response response = client.newCall(request).execute();//创建call对象并调用execute获取返回的数据
                    String responseData = response.body().string();
                    String jsonData = responseData;
                    try {
                        JSONObject jsonObject = new JSONObject(jsonData);
                        JSONArray results = jsonObject.getJSONArray("results");   //得到键为results的JSONArray
                        JSONObject now = results.getJSONObject(0).getJSONObject("now");//得到键值为"now"的JSONObject
                        Log.d("json", "-------------" + now.toString() + "--------------");
                        weatherStr = now.getString("text");//得到"now"键值的JSONObject下的"text"属性,即天气信息
                        weatherCode = now.getString("code");
                        temperature = now.getString("temperature"); //获取温度
                        Log.d("json", "-------------" + weatherStr + weatherCode + temperature + "--------------");
                        setWeather(view);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    void setWeather(View view) {
        getActivity().runOnUiThread(new Runnable() {//切换到主线程,ui界面的更改不能出现在子线程,否则app会崩溃
            @Override
            public void run() {
                mTvWeatherName.setText("今日天气：" + weatherStr);
                mTvTemperature.setText("今日气温：" + temperature + "℃");
                Log.d("json", "-------------" + weatherCode + " " + weatherImageMap.containsKey(Integer.valueOf(weatherCode)) + "--------------");
                mIvWeather.setImageResource(weatherImageMap.get(Integer.valueOf(weatherCode)));
            }
        });
    }

    private MaterialCalendarView mCvCalendar;

    private MonthDecorator monthDecorator;

    private int[] days = new int[] {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

    private TextView mTvDateSumTime;
    private TextView mTvDateFrequency;
    private CalendarDay calendarDay;

    private LinearLayout LL_LL;
    private LinearLayout LL_LL_empty;
    private TextView mTvEmpty;

    void setCalendar(View view) {

        LL_LL = view.findViewById(R.id.LL_LL);
        LL_LL_empty = view.findViewById(R.id.LL_LL_empty);
        mTvEmpty = view.findViewById(R.id.tv_today_line);

        mCvCalendar = view.findViewById(R.id.calendarView);

        mTvDateSumTime = view.findViewById(R.id.tv_dateSumTime);
        mTvDateFrequency = view.findViewById(R.id.tv_dateFrequency);
        Date date = new Date();
        mCvCalendar.setDateSelected(date, true);
        calendarDay = new CalendarDay(date);

        Log.d("calendarDay", "--------------------" + calendarDay.getYear() + "." + calendarDay.getMonth()%12 + 1 + "." + calendarDay.getDay());
        Log.d("calendarDay", "--------------------" + String.valueOf(getFrequencyInDate(MainActivity.getCurrentUsername(), calendarDay.getYear(), calendarDay.getMonth()%12 + 1, calendarDay.getDay())));
        Log.d("calendarDay", "--------------------" + String.valueOf(getSportTimeInDate(MainActivity.getCurrentUsername(), calendarDay.getYear(), calendarDay.getMonth()%12 + 1, calendarDay.getDay())));



        Drawable drawable = getResources().getDrawable(R.drawable.bg_current_day);
        CurrentDayDecorator currentDayDecorator = new CurrentDayDecorator(calendarDay, drawable);
        setThisMonthDecorator(calendarDay.getYear(), calendarDay.getMonth()%12 + 1);
        mCvCalendar.addDecorator(currentDayDecorator);

        int frequency = getFrequencyInDate(MainActivity.getCurrentUsername(), calendarDay.getYear(), calendarDay.getMonth()%12 + 1, calendarDay.getDay());
        mTvDateFrequency.setText(String.valueOf(frequency));

        String str = "%d h %d m %d s";
        int sumTime = getSportTimeInDate(MainActivity.getCurrentUsername(), calendarDay.getYear(), calendarDay.getMonth()%12 + 1, calendarDay.getDay());
        str = String.format(str, sumTime/3600, sumTime%3600/60, sumTime%60);

        mTvDateSumTime.setText(str);

        if (frequency == 0) {
            LL_LL.setVisibility(View.INVISIBLE);
            mTvEmpty.setText("今天还没有运动哦！健康生活，从现在开始！");
            LL_LL_empty.setVisibility(View.VISIBLE);
        } else {
            LL_LL.setVisibility(View.VISIBLE);
            LL_LL_empty.setVisibility(View.INVISIBLE);
        }


        mCvCalendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int year = date.getYear();
                int month = date.getMonth()%12 + 1;
                int day = date.getDay();
                Log.d("getDateSelected", "------------------" + year + "." + month + "." + day + "------------------");

                int frequency = getFrequencyInDate(MainActivity.getCurrentUsername(), year, month, day);
                mTvDateFrequency.setText(String.valueOf(frequency));
                String str = "%d h %d m %d s";
                int sumTime = getSportTimeInDate(MainActivity.getCurrentUsername(), year, month, day);
                str = String.format(str, sumTime/3600, sumTime%3600/60, sumTime%60);

                mTvDateSumTime.setText(str);

                if (frequency == 0) {
                    LL_LL.setVisibility(View.INVISIBLE);
                    mTvEmpty.setText("今天还没有运动哦！健康生活，从现在开始！");
                    if (calendarDay.getYear() != date.getYear() || calendarDay.getMonth() != date.getMonth() || calendarDay.getDay() != date.getDay()) {
                        mTvEmpty.setText("这一天没有运动哦！坚持才是胜利！");
                    }
                    LL_LL_empty.setVisibility(View.VISIBLE);
                } else {
                    LL_LL.setVisibility(View.VISIBLE);
                    LL_LL_empty.setVisibility(View.INVISIBLE);
                }


            }
        });

        mCvCalendar.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                int newYear = date.getYear();
                int newMonth = date.getMonth() % 12 + 1;
                setThisMonthDecorator(newYear, newMonth);
                Log.d("MonthChanged", "------------------" + newYear + "." + newMonth + "--------------");
            }
        });
    }



    void setThisMonthDecorator(int year, int month) {
        if (year % 400 == 0 || (year % 100 != 0 && year % 4 == 0)) {
            days[2] = 29;
        } else {
            days[2] = 28;
        }
        if (monthDecorator != null) mCvCalendar.removeDecorator(monthDecorator);
        ArrayList<CalendarDay> dates = new ArrayList<>();
        for (int i = 1; i <= days[month]; i++) {
            if (getFrequencyInDate(MainActivity.getCurrentUsername(), year, month, i) != 0 && (year != calendarDay.getYear() || month != calendarDay.getMonth()%12 + 1 || i != calendarDay.getDay())) {
                dates.add(CalendarDay.from(year, month, i));
            }
            // if ((i == 1 || i == 3 || i == 5 )&& (year != calendarDay.getYear() || month != calendarDay.getMonth()%12 + 1 || i != calendarDay.getDay())) {
            //     dates.add(CalendarDay.from(year, month - 1, i));
            // }
        }
        monthDecorator = new MonthDecorator(dates, getResources().getDrawable(R.drawable.bg_this_month));
        mCvCalendar.addDecorator(monthDecorator);
    }

}