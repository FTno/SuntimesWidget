/**
    Copyright (C) 2017-2018 Forrest Guice
    This file is part of SuntimesWidget.

    SuntimesWidget is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    SuntimesWidget is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with SuntimesWidget.  If not, see <http://www.gnu.org/licenses/>.
*/
package com.forrestguice.suntimeswidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Typeface;

import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;

import android.view.View;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.forrestguice.suntimeswidget.calculator.SuntimesEquinoxSolsticeData;
import com.forrestguice.suntimeswidget.calculator.SuntimesEquinoxSolsticeDataset;
import com.forrestguice.suntimeswidget.settings.AppSettings;
import com.forrestguice.suntimeswidget.settings.WidgetSettings;

import java.util.Calendar;

@SuppressWarnings("Convert2Diamond")
public class EquinoxView extends LinearLayout
{
    private SuntimesUtils utils = new SuntimesUtils();
    private boolean isRtl = false;
    private boolean centered = false;
    private WidgetSettings.TrackingMode trackingMode = WidgetSettings.TrackingMode.SOONEST;

    private View layout;
    private TextView labelView, timeView, noteView;
    protected Calendar time, now;

    public EquinoxView(Context context)
    {
        super(context);
        init(context, null);
    }

    public EquinoxView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs)
    {
        initLocale(context);
        initColors(context);
        LayoutInflater.from(context).inflate(R.layout.layout_view_equinox, this, true);

        if (attrs != null)
        {
            LinearLayout.LayoutParams lp = generateLayoutParams(attrs);
            centered = ((lp.gravity == Gravity.CENTER) || (lp.gravity == Gravity.CENTER_HORIZONTAL));
        }

        layout = findViewById(R.id.info_equinoxsolstice_layout);
        labelView = (TextView)findViewById(R.id.text_date_equinox_label);
        timeView = (TextView)findViewById(R.id.text_date_equinox);
        noteView = (TextView)findViewById(R.id.text_date_equinox_note);

        if (isInEditMode())
        {
            updateViews(context, null);
        }
    }

    private int noteColor; //, springColor, summerColor, fallColor, winterColor;

    private void initColors(Context context)
    {
        int[] colorAttrs = { android.R.attr.textColorPrimary }; //, R.attr.springColor, R.attr.summerColor, R.attr.fallColor, R.attr.winterColor };
        TypedArray typedArray = context.obtainStyledAttributes(colorAttrs);
        int def = R.color.transparent;
        noteColor = ContextCompat.getColor(context, typedArray.getResourceId(0, def));
        //springColor = ContextCompat.getColor(context, typedArray.getResourceId(1, def));
        //summerColor = ContextCompat.getColor(context, typedArray.getResourceId(2, def));
        //fallColor = ContextCompat.getColor(context, typedArray.getResourceId(3, def));
        //winterColor = ContextCompat.getColor(context, typedArray.getResourceId(4, def));
        typedArray.recycle();
    }

    public void initLocale(Context context)
    {
        isRtl = AppSettings.isLocaleRtl(context);
    }

    public void setTrackingMode(WidgetSettings.TrackingMode mode)
    {
        trackingMode = mode;
    }
    public WidgetSettings.TrackingMode getTrackingMode()
    {
        return trackingMode;
    }

    /**private EquinoxNote findSoonestNote(Calendar now)
    {
        return findClosestNote(now, true);
    }
    private EquinoxNote findClosestNote(Calendar now)
    {
        return findClosestNote(now, false);
    }
    private EquinoxNote findClosestNote(Calendar now, boolean upcoming)
    {
        if (notes == null || now == null)
        {
            return null;
        }

        EquinoxNote closest = null;
        long timeDeltaMin = Long.MAX_VALUE;
        for (EquinoxNote note : notes)
        {
            Calendar noteTime = note.getTime();
            if (noteTime != null)
            {
                if (upcoming && !noteTime.after(now))
                    continue;

                long timeDelta = Math.abs(noteTime.getTimeInMillis() - now.getTimeInMillis());
                if (timeDelta < timeDeltaMin)
                {
                    timeDeltaMin = timeDelta;
                    closest = note;
                }
            }
        }
        return closest;
    }*/

    public void updateTime( Context context, Calendar time )
    {
        updateTime(context, time, false);
    }
    public void updateTime( Context context, Calendar time, boolean showSeconds )
    {
        this.time = time;
        if (timeView != null)
        {
            SuntimesUtils.TimeDisplayText timeText = utils.calendarDateTimeDisplayString(context, time, showSeconds);
            timeView.setText(timeText.toString());
        }
    }

    public void updateNote( Context context, Calendar now, int noteColor )
    {
        this.now = now;
        if (noteView != null)
        {
            if (now != null && time != null)
            {
                String noteText = utils.timeDeltaDisplayString(now.getTime(), time.getTime()).toString();

                if (time.before(Calendar.getInstance()))
                {
                    String noteString = context.getString(R.string.ago, noteText);
                    SpannableString noteSpan = (noteView.isEnabled() ? SuntimesUtils.createBoldColorSpan(noteString, noteText, noteColor)
                            : SuntimesUtils.createBoldSpan(noteString, noteText));
                    noteView.setText(noteSpan);

                } else {
                    String noteString = context.getString(R.string.hence, noteText);
                    SpannableString noteSpan = (noteView.isEnabled() ? SuntimesUtils.createBoldColorSpan(noteString, noteText, noteColor)
                            : SuntimesUtils.createBoldSpan(noteString, noteText));
                    noteView.setText(noteSpan);
                }
            } else {
                noteView.setText("");
            }
        }
    }

    protected void updateViews( Context context, SuntimesEquinoxSolsticeDataset data )
    {
        if (isInEditMode())
        {
            return;
        }

        if (data == null)
        {
            updateTime(context, null);
            updateNote(context, null, noteColor);
            return;
        }

        if (data.isCalculated() && data.isImplemented())
        {
            SuntimesEquinoxSolsticeData eventData = (trackingMode == WidgetSettings.TrackingMode.SOONEST ? data.findSoonest(data.now())
                                                                                                         : data.findClosest(data.now()));
            Calendar eventCalendar = eventData.eventCalendarUpcoming(data.now());

            boolean showSeconds = WidgetSettings.loadShowSecondsPref(context, 0);
            updateTime(context, eventCalendar, showSeconds);
            updateNote(context, data.now(), noteColor);
        }
    }

    public void setOnClickListener( View.OnClickListener listener )
    {
        layout.setOnClickListener(listener);
    }

    public void setOnLongClickListener( View.OnLongClickListener listener)
    {
        layout.setOnLongClickListener(listener);
    }

}
