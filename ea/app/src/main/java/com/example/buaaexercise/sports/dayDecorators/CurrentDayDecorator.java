package com.example.buaaexercise.sports.dayDecorators;

import android.graphics.drawable.Drawable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

public class CurrentDayDecorator implements DayViewDecorator {

    private CalendarDay date;
    private Drawable drawable;

    public CurrentDayDecorator(CalendarDay date, Drawable drawable) {
        this.date = date;
        this.drawable = drawable;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return day.equals(date);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(drawable);
    }
}