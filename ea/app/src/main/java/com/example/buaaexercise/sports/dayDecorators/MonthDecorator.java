package com.example.buaaexercise.sports.dayDecorators;

import android.graphics.drawable.Drawable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.ArrayList;

public class MonthDecorator implements DayViewDecorator {
    private ArrayList<CalendarDay> dates;
    private Drawable drawable;

    public MonthDecorator(ArrayList<CalendarDay> dates, Drawable drawable) {
        this.dates = dates;
        this.drawable = drawable;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(drawable);
    }
}