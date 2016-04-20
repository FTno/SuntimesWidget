/**
    Copyright (C) 2014 Forrest Guice
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

package com.forrestguice.suntimeswidget.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import com.forrestguice.suntimeswidget.R;
import com.forrestguice.suntimeswidget.calculator.SuntimesCalculatorDescriptor;
import com.forrestguice.suntimeswidget.layouts.SuntimesLayout;
import com.forrestguice.suntimeswidget.layouts.SuntimesLayout_1x1_0;
import com.forrestguice.suntimeswidget.layouts.SuntimesLayout_1x1_1;
import com.forrestguice.suntimeswidget.layouts.SuntimesLayout_1x1_2;
import com.forrestguice.suntimeswidget.themes.DarkTheme;
import com.forrestguice.suntimeswidget.themes.SuntimesTheme;

public class WidgetSettings
{
    public static final String PREFS_WIDGET = "com.forrestguice.suntimeswidget";

    public static final String PREF_PREFIX_KEY = "appwidget_";
    public static final String PREF_PREFIX_KEY_APPEARANCE = "_appearance_";
    public static final String PREF_PREFIX_KEY_GENERAL = "_general_";
    public static final String PREF_PREFIX_KEY_LOCATION = "_location_";
    public static final String PREF_PREFIX_KEY_TIMEZONE = "_timezone_";
    public static final String PREF_PREFIX_KEY_ACTION = "_action_";

    public static final String PREF_KEY_GENERAL_CALCULATOR = "calculator";
    public static final String PREF_DEF_GENERAL_CALCULATOR = "any";

    public static final String PREF_KEY_APPEARANCE_THEME = "theme";
    public static final String PREF_DEF_APPEARANCE_THEME = DarkTheme.THEMEDEF_NAME;

    public static final String PREF_KEY_APPEARANCE_SHOWTITLE = "showtitle";
    public static final boolean PREF_DEF_APPEARANCE_SHOWTITLE = false;

    private static final String PREF_KEY_APPEARANCE_TITLETEXT = "titletext";
    private static final String PREF_DEF_APPEARANCE_TITLETEXT = "";

    private static final String PREF_KEY_APPEARANCE_WIDGETMODE_1x1 = "widgetmode_1x1";
    private static final WidgetMode1x1 PREF_DEF_APPEARANCE_WIDGETMODE_1x1 = WidgetMode1x1.WIDGETMODE1x1_BOTH_1;

    public static final String PREF_KEY_GENERAL_TIMEMODE = "timemode";
    public static final TimeMode PREF_DEF_GENERAL_TIMEMODE = TimeMode.OFFICIAL;

    public static final String PREF_KEY_GENERAL_TIMENOTE_RISE = "timenoterise";
    public static final TimeMode PREF_DEF_GENERAL_TIMENOTE_RISE = TimeMode.ASTRONOMICAL;

    public static final String PREF_KEY_GENERAL_TIMENOTE_SET = "timenoteset";
    public static final TimeMode PREF_DEF_GENERAL_TIMENOTE_SET = TimeMode.OFFICIAL;

    public static final String PREF_KEY_GENERAL_COMPAREMODE = "comparemode";
    public static final CompareMode PREF_DEF_GENERAL_COMPAREMODE = CompareMode.TOMORROW;

    public static final String PREF_KEY_ACTION_MODE = "action";
    public static final ActionMode PREF_DEF_ACTION_MODE = ActionMode.ONTAP_LAUNCH_CONFIG;

    public static final String PREF_KEY_ACTION_LAUNCH = "launch";
    public static final String PREF_DEF_ACTION_LAUNCH = "com.forrestguice.suntimeswidget.SuntimesActivity";

    public static final String PREF_KEY_LOCATION_MODE = "locationMode";
    public static final LocationMode PREF_DEF_LOCATION_MODE = LocationMode.CUSTOM_LOCATION;

    public static final String PREF_KEY_LOCATION_LONGITUDE = "longitude";
    public static final String PREF_DEF_LOCATION_LONGITUDE = "-112.4677778";

    public static final String PREF_KEY_LOCATION_LATITUDE = "latitude";
    public static final String PREF_DEF_LOCATION_LATITUDE = "34.54";

    public static final String PREF_KEY_LOCATION_LABEL = "label";
    public static final String PREF_DEF_LOCATION_LABEL = "";

    public static final String PREF_KEY_TIMEZONE_MODE = "timezoneMode";
    public static final TimezoneMode PREF_DEF_TIMEZONE_MODE = TimezoneMode.CURRENT_TIMEZONE;

    public static final String PREF_KEY_TIMEZONE_CUSTOM = "timezone";
    public static final String PREF_DEF_TIMEZONE_CUSTOM = "US/Arizona";


    /**
     * WidgetOnTap
     */
    public static enum ActionMode
    {
        ONTAP_DONOTHING("Ignore"),
        ONTAP_LAUNCH_CONFIG("Reconfigure Widget"),
        ONTAP_LAUNCH_ACTIVITY("Launch Activity"),
        ONTAP_FLIPTO_NEXTITEM("Flip Views");

        private String displayString;

        private ActionMode(String displayString)
        {
            this.displayString = displayString;
        }

        public String toString()
        {
            return displayString;
        }

        public String getDisplayString()
        {
            return displayString;
        }

        public void setDisplayString( String displayString )
        {
            this.displayString = displayString;
        }

        public static void initDisplayStrings( Context context )
        {
            ONTAP_DONOTHING.setDisplayString(context.getString(R.string.actionMode_doNothing));
            ONTAP_LAUNCH_CONFIG.setDisplayString(context.getString(R.string.actionMode_config));
            ONTAP_LAUNCH_ACTIVITY.setDisplayString(context.getString(R.string.actionMode_launchActivity));
            ONTAP_FLIPTO_NEXTITEM.setDisplayString(context.getString(R.string.actionMode_flipToNextItem));
        }

        public int ordinal( ActionMode[] array )
        {
            for (int i=0; i<array.length; i++)
            {
                if (array[i].name().equals(this.name()))
                {
                    return i;
                }
            }
            return -1;
        }
    }

    /**
     * WidgetMode1x1
     */
    public static enum WidgetMode1x1
    {
        WIDGETMODE1x1_SUNRISE("Sunrise only", R.layout.layout_widget_1x1_1),
        WIDGETMODE1x1_SUNSET("Sunset only", R.layout.layout_widget_1x1_2),
        WIDGETMODE1x1_BOTH_1("Sunrise & Sunset (1)", R.layout.layout_widget_1x1_0),
        WIDGETMODE1x1_BOTH_2("Sunrise & Sunset (2)", R.layout.layout_widget_1x1_3);

        private int layoutID;
        private String displayString;

        private WidgetMode1x1(String displayString, int layoutID)
        {
            this.displayString = displayString;
            this.layoutID = layoutID;
        }

        public int getLayoutID()
        {
            return layoutID;
        }

        public String toString()
        {
            return displayString;
        }

        public String getDisplayString()
        {
            return displayString;
        }

        public void setDisplayString( String displayString )
        {
            this.displayString = displayString;
        }

        public static void initDisplayStrings( Context context )
        {
            WIDGETMODE1x1_SUNRISE.setDisplayString(context.getString(R.string.widgetMode1x1_sunrise));
            WIDGETMODE1x1_SUNSET.setDisplayString(context.getString(R.string.widgetMode1x1_sunset));
            WIDGETMODE1x1_BOTH_1.setDisplayString(context.getString(R.string.widgetMode1x1_both_1));
            WIDGETMODE1x1_BOTH_2.setDisplayString(context.getString(R.string.widgetMode1x1_both_2));
        }
    }

    /**
     * TimezoneMode
     */
    public static enum TimezoneMode
    {
        CURRENT_TIMEZONE("Current Timezone"),
        CUSTOM_TIMEZONE("Custom Timezone");

        private String displayString;

        private TimezoneMode(String displayString)
        {
            this.displayString = displayString;
        }

        public String toString()
        {
            return displayString;
        }

        public String getDisplayString()
        {
            return displayString;
        }

        public void setDisplayString( String displayString )
        {
            this.displayString = displayString;
        }

        public static void initDisplayStrings( Context context )
        {
            CURRENT_TIMEZONE.setDisplayString(context.getString(R.string.timezoneMode_current));
            CUSTOM_TIMEZONE.setDisplayString(context.getString(R.string.timezoneMode_custom));
        }
    }

    /**
     * LocationMode
     */
    public static enum LocationMode
    {
        //CURRENT_LOCATION("Current Location"),
        CUSTOM_LOCATION("Custom Location");

        private String displayString;

        private LocationMode(String displayString)
        {
            this.displayString = displayString;
        }

        public String toString()
        {
            return displayString;
        }

        public String getDisplayString()
        {
            return displayString;
        }

        public void setDisplayString( String displayString )
        {
            this.displayString = displayString;
        }

        public static void initDisplayStrings( Context context )
        {
            //CURRENT_LOCATION.setDisplayString(context.getString(R.string.locationMode_current));
            CUSTOM_LOCATION.setDisplayString(context.getString(R.string.locationMode_custom));
        }
    }

    public static class Location
    {
        String label = "";
        String latitude;
        String longitude;

        public Location( String latitude, String longitude )
        {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public Location( String label, String latitude, String longitude )
        {
            this.label = label;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public String getLabel()
        {
            return label;
        }

        public String getLatitude()
        {
            return latitude;
        }

        public String getLongitude()
        {
            return longitude;
        }

        public Uri getUri()
        {
            return Uri.parse("geo:" + latitude + "," + longitude);
        }

        public String toString()
        {
            return latitude + ", " + longitude;
        }
    }

    public static enum CompareMode
    {
        YESTERDAY("Yesterday"),
        TOMORROW("Tomorrow");

        private String displayString;

        private CompareMode( String displayString )
        {
            this.displayString = displayString;
        }

        public String getDisplayString()
        {
            return displayString;
        }

        public void setDisplayString( String displayString )
        {
            this.displayString = displayString;
        }

        public String toString()
        {
            return displayString;
        }

        public static void initDisplayStrings( Context context )
        {
            YESTERDAY.setDisplayString( context.getString(R.string.compareMode_yesterday) );
            TOMORROW.setDisplayString( context.getString(R.string.compareMode_tomorrow) );
        }
    }

    /**
     * TimeMode
     */
    public static enum TimeMode
    {
        OFFICIAL("Actual", "Actual Time", 3, 0),
        CIVIL("Civil", "Civil Twilight", 2, 1),
        NAUTICAL("Nautical", "Nautical Twilight", 1, 2),
        ASTRONOMICAL("Astronomical", "Astronomical Twilight", 0, 3),
        NOON("Noon", "Solar Noon", -1, -1);

        public static boolean shortDisplayStrings = false;
        private String longDisplayString;
        private String shortDisplayString;
        private int riseOrder, setOrder;

        private TimeMode(String shortDisplayString, String longDisplayString, int riseOrder, int setOrder)
        {
            this.shortDisplayString = shortDisplayString;
            this.longDisplayString = longDisplayString;
            this.riseOrder = riseOrder;
            this.setOrder = setOrder;
        }

        public String toString()
        {
            if (shortDisplayStrings)
            {
                return shortDisplayString;

            } else {
                return longDisplayString;
            }
        }

        public static TimeMode getModeForRiseOrder( int riseOrder )
        {
            for (TimeMode mode : TimeMode.values())
            {
                if (mode.getRiseOrder() == riseOrder)
                {
                    return mode;
                }
            }
            return OFFICIAL;
        }

        public int getRiseOrder()
        {
            return riseOrder;
        }

        public int getSetOrder()
        {
            return setOrder;
        }

        public static TimeMode getModeForSetOrder( int setOrder )
        {
            for (TimeMode mode : TimeMode.values())
            {
                if (mode.getSetOrder() == setOrder)
                {
                    return mode;
                }
            }
            return ASTRONOMICAL;
        }

        public String getShortDisplayString()
        {
            return shortDisplayString;
        }

        public String getLongDisplayString()
        {
            return longDisplayString;
        }

        public void setDisplayStrings(String shortDisplayString, String longDisplayString)
        {
            this.shortDisplayString = shortDisplayString;
            this.longDisplayString = longDisplayString;
        }

        public static void initDisplayStrings( Context context )
        {
            OFFICIAL.setDisplayStrings( context.getString(R.string.timeMode_official_short),
                    context.getString(R.string.timeMode_official) );

            NAUTICAL.setDisplayStrings( context.getString(R.string.timeMode_nautical_short),
                    context.getString(R.string.timeMode_nautical));

            CIVIL.setDisplayStrings( context.getString(R.string.timeMode_civil_short),
                    context.getString(R.string.timeMode_civil) );

            ASTRONOMICAL.setDisplayStrings( context.getString(R.string.timeMode_astronomical_short),
                    context.getString(R.string.timeMode_astronomical) );

            NOON.setDisplayStrings( context.getString(R.string.timeMode_noon_short),
                    context.getString(R.string.timeMode_noon) );
        }
    }



    public static void save1x1ModePref(Context context, int appWidgetId, WidgetSettings.WidgetMode1x1 mode)
    {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_WIDGET, 0).edit();
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_APPEARANCE;
        prefs.putString(prefs_prefix + PREF_KEY_APPEARANCE_WIDGETMODE_1x1, mode.name());
        prefs.commit();
    }
    public static WidgetSettings.WidgetMode1x1 load1x1ModePref(Context context, int appWidgetId)
    {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_WIDGET, 0);
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_APPEARANCE;
        String modeString = prefs.getString(prefs_prefix + PREF_KEY_APPEARANCE_WIDGETMODE_1x1, PREF_DEF_APPEARANCE_WIDGETMODE_1x1.name());

        WidgetMode1x1 widgetMode;
        try
        {
            widgetMode = WidgetSettings.WidgetMode1x1.valueOf(modeString);

        } catch (IllegalArgumentException e) {
            widgetMode = PREF_DEF_APPEARANCE_WIDGETMODE_1x1;
            Log.w("load1x1ModePref", "Failed to load value '" + modeString + "'; using default '" + PREF_DEF_APPEARANCE_WIDGETMODE_1x1.name() + "'.");
        }
        return widgetMode;
    }
    public static SuntimesLayout load1x1ModePref_asLayout(Context context, int appWidgetId)
    {
        SuntimesLayout layout;
        WidgetSettings.WidgetMode1x1 mode = load1x1ModePref(context, appWidgetId);
        switch (mode.getLayoutID())
        {
            case R.layout.layout_widget_1x1_1:
                layout = new SuntimesLayout_1x1_1();
                break;

            case R.layout.layout_widget_1x1_2:
                layout = new SuntimesLayout_1x1_2();
                break;

            case R.layout.layout_widget_1x1_0:
            default:
                layout = new SuntimesLayout_1x1_0(mode.getLayoutID());
                break;
        }
        return layout;
    }
    public static void delete1x1ModePref(Context context, int appWidgetId)
    {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_WIDGET, 0).edit();
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_APPEARANCE;
        prefs.remove(prefs_prefix + PREF_KEY_APPEARANCE_WIDGETMODE_1x1);
        prefs.commit();
    }




    public static void saveThemePref(Context context, int appWidgetId, String themeName)
    {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_WIDGET, 0).edit();
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_APPEARANCE;
        prefs.putString(prefs_prefix + PREF_KEY_APPEARANCE_THEME, themeName);
        prefs.commit();
    }
    public static SuntimesTheme loadThemePref(Context context, int appWidgetId)
    {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_WIDGET, 0);
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_APPEARANCE;
        String themeName = prefs.getString(prefs_prefix + PREF_KEY_APPEARANCE_THEME, PREF_DEF_APPEARANCE_THEME);

        SuntimesTheme theme = WidgetThemes.loadTheme(context, themeName);
        Log.d("loadThemePref", "theme is " + theme.themeName());
        return theme;
    }
    public static void deleteThemePref(Context context, int appWidgetId)
    {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_WIDGET, 0).edit();
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_APPEARANCE;
        prefs.remove(prefs_prefix + PREF_KEY_APPEARANCE_THEME);
        prefs.commit();
    }


    public static void saveCalculatorModePref(Context context, int appWidgetId, SuntimesCalculatorDescriptor mode)
    {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_WIDGET, 0).edit();
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_GENERAL;
        prefs.putString(prefs_prefix + PREF_KEY_GENERAL_CALCULATOR, mode.name());
        prefs.commit();
    }
    public static SuntimesCalculatorDescriptor loadCalculatorModePref(Context context, int appWidgetId)
    {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_WIDGET, 0);
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_GENERAL;
        String modeString = prefs.getString(prefs_prefix + PREF_KEY_GENERAL_CALCULATOR, PREF_DEF_GENERAL_CALCULATOR);

        SuntimesCalculatorDescriptor calculatorMode = null;
        try
        {
            calculatorMode = SuntimesCalculatorDescriptor.valueOf(modeString);

        } catch (IllegalArgumentException e) {
            Log.e("loadCalculatorModePref", e.toString() + " ... It looks like " + modeString + " isn't in our list of calculators.");
            // TODO: handle this better. right now it allows this function to return a null, which triggers NullPointerExceptions later
            // ... what is the right course of action? either instantiate a default (that couples us to that third party code) or ...? our widget doesn't currently have an error display state
        }
        return calculatorMode;
    }
    public static void deleteCalculatorModePref(Context context, int appWidgetId)
    {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_WIDGET, 0).edit();
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_GENERAL;
        prefs.remove(prefs_prefix + PREF_KEY_GENERAL_CALCULATOR);
        prefs.commit();
    }


    public static void saveShowTitlePref(Context context, int appWidgetId, boolean showTitle)
    {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_WIDGET, 0).edit();
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_APPEARANCE;
        prefs.putBoolean(prefs_prefix + PREF_KEY_APPEARANCE_SHOWTITLE, showTitle);
        prefs.commit();
    }
    public static boolean loadShowTitlePref(Context context, int appWidgetId)
    {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_WIDGET, 0);
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_APPEARANCE;
        return prefs.getBoolean(prefs_prefix + PREF_KEY_APPEARANCE_SHOWTITLE, PREF_DEF_APPEARANCE_SHOWTITLE);
    }
    public static void deleteShowTitlePref(Context context, int appWidgetId)
    {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_WIDGET, 0).edit();
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_APPEARANCE;
        prefs.remove(prefs_prefix + PREF_KEY_APPEARANCE_SHOWTITLE);
        prefs.commit();
    }


    public static void saveTitleTextPref(Context context, int appWidgetId, String titleText)
    {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_WIDGET, 0).edit();
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_APPEARANCE;
        prefs.putString(prefs_prefix + PREF_KEY_APPEARANCE_TITLETEXT, titleText);
        prefs.commit();
    }
   public static String loadTitleTextPref(Context context, int appWidgetId)
    {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_WIDGET, 0);
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_APPEARANCE;
        return prefs.getString(prefs_prefix + PREF_KEY_APPEARANCE_TITLETEXT, PREF_DEF_APPEARANCE_TITLETEXT);
    }
    public static void deleteTitleTextPref(Context context, int appWidgetId)
    {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_WIDGET, 0).edit();
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_APPEARANCE;
        prefs.remove(prefs_prefix + PREF_KEY_APPEARANCE_TITLETEXT);
        prefs.commit();
    }


    public static void saveTimeModePref(Context context, int appWidgetId, WidgetSettings.TimeMode mode)
    {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_WIDGET, 0).edit();
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_GENERAL;
        prefs.putString(prefs_prefix + PREF_KEY_GENERAL_TIMEMODE, mode.name());
        prefs.commit();
    }
    public static WidgetSettings.TimeMode loadTimeModePref(Context context, int appWidgetId)
    {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_WIDGET, 0);
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_GENERAL;
        String modeString = prefs.getString(prefs_prefix + PREF_KEY_GENERAL_TIMEMODE, PREF_DEF_GENERAL_TIMEMODE.name());

        TimeMode timeMode;
        try
        {
            timeMode = WidgetSettings.TimeMode.valueOf(modeString);

        } catch (IllegalArgumentException e) {
            timeMode = PREF_DEF_GENERAL_TIMEMODE;
        }
        return timeMode;
    }
    public static void deleteTimeModePref(Context context, int appWidgetId)
    {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_WIDGET, 0).edit();
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_GENERAL;
        prefs.remove(prefs_prefix + PREF_KEY_GENERAL_TIMEMODE);
        prefs.commit();
    }


    public static void saveActionModePref(Context context, int appWidgetId, WidgetSettings.ActionMode mode)
    {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_WIDGET, 0).edit();
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_ACTION;
        prefs.putString(prefs_prefix + PREF_KEY_ACTION_MODE, mode.name());
        prefs.commit();
    }
    public static WidgetSettings.ActionMode loadActionModePref(Context context, int appWidgetId)
    {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_WIDGET, 0);
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_ACTION;
        String modeString = prefs.getString(prefs_prefix + PREF_KEY_ACTION_MODE, PREF_DEF_ACTION_MODE.name());

        ActionMode actionMode;
        try
        {
            actionMode = WidgetSettings.ActionMode.valueOf(modeString);

        } catch (IllegalArgumentException e) {
            actionMode = PREF_DEF_ACTION_MODE;
        }
        return actionMode;
    }
    public static void deleteActionModePref(Context context, int appWidgetId)
    {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_WIDGET, 0).edit();
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_ACTION;
        prefs.remove(prefs_prefix + PREF_KEY_ACTION_MODE);
        prefs.commit();
    }


    public static void saveActionLaunchPref(Context context, int appWidgetId, String launchString)
    {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_WIDGET, 0).edit();
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_ACTION;
        prefs.putString(prefs_prefix + PREF_KEY_ACTION_LAUNCH, launchString);
        prefs.commit();
    }
    public static String loadActionLaunchPref(Context context, int appWidgetId)
    {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_WIDGET, 0);
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_ACTION;
        String launchString = prefs.getString(prefs_prefix + PREF_KEY_ACTION_LAUNCH, PREF_DEF_ACTION_LAUNCH);
        return launchString;

    }
    public static void deleteActionLaunchPref(Context context, int appWidgetId)
    {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_WIDGET, 0).edit();
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_ACTION;
        prefs.remove(prefs_prefix + PREF_KEY_ACTION_LAUNCH);
        prefs.commit();
    }




    public static void saveLocationModePref(Context context, int appWidgetId, WidgetSettings.LocationMode mode)
    {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_WIDGET, 0).edit();
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_LOCATION;
        prefs.putString(prefs_prefix + PREF_KEY_LOCATION_MODE, mode.name());
        prefs.commit();
    }
    public static WidgetSettings.LocationMode loadLocationModePref(Context context, int appWidgetId)
    {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_WIDGET, 0);
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_LOCATION;
        String modeString = prefs.getString(prefs_prefix + PREF_KEY_LOCATION_MODE, PREF_DEF_LOCATION_MODE.name());

        LocationMode locationMode;
        try
        {
            locationMode = WidgetSettings.LocationMode.valueOf(modeString);

        } catch (IllegalArgumentException e) {
            locationMode = PREF_DEF_LOCATION_MODE;
        }
        return locationMode;
    }
    public static void deleteLocationModePref(Context context, int appWidgetId)
    {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_WIDGET, 0).edit();
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_LOCATION;
        prefs.remove(prefs_prefix + PREF_KEY_LOCATION_MODE);
        prefs.commit();
    }


    public static void saveTimezoneModePref(Context context, int appWidgetId, WidgetSettings.TimezoneMode mode)
    {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_WIDGET, 0).edit();
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_TIMEZONE;
        prefs.putString(prefs_prefix + PREF_KEY_TIMEZONE_MODE, mode.name());
        prefs.commit();
    }
    public static WidgetSettings.TimezoneMode loadTimezoneModePref(Context context, int appWidgetId)
    {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_WIDGET, 0);
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_TIMEZONE;
        String modeString = prefs.getString(prefs_prefix + PREF_KEY_TIMEZONE_MODE, PREF_DEF_TIMEZONE_MODE.name());

        TimezoneMode timezoneMode;
        try
        {
            timezoneMode = WidgetSettings.TimezoneMode.valueOf(modeString);

        } catch (IllegalArgumentException e) {
            timezoneMode = PREF_DEF_TIMEZONE_MODE;
        }
        return timezoneMode;
    }
    public static void deleteTimezoneModePref(Context context, int appWidgetId)
    {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_WIDGET, 0).edit();
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_TIMEZONE;
        prefs.remove(prefs_prefix + PREF_KEY_TIMEZONE_MODE);
        prefs.commit();
    }


    public static void saveLocationPref(Context context, int appWidgetId, Location location)
    {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_WIDGET, 0).edit();
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_LOCATION;
        prefs.putString(prefs_prefix + PREF_KEY_LOCATION_LONGITUDE, location.getLongitude());
        prefs.putString(prefs_prefix + PREF_KEY_LOCATION_LATITUDE, location.getLatitude());
        prefs.putString(prefs_prefix + PREF_KEY_LOCATION_LABEL, location.getLabel());
        prefs.commit();
    }
    public static Location loadLocationPref(Context context, int appWidgetId)
    {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_WIDGET, 0);
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_LOCATION;
        String lonString = prefs.getString(prefs_prefix + PREF_KEY_LOCATION_LONGITUDE, PREF_DEF_LOCATION_LONGITUDE);
        String latString = prefs.getString(prefs_prefix + PREF_KEY_LOCATION_LATITUDE, PREF_DEF_LOCATION_LATITUDE);
        String nameString = prefs.getString(prefs_prefix + PREF_KEY_LOCATION_LABEL, PREF_DEF_LOCATION_LABEL);
        return new Location(nameString, latString, lonString);

    }
    public static void deleteLocationPref(Context context, int appWidgetId)
    {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_WIDGET, 0).edit();
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_LOCATION;
        prefs.remove(prefs_prefix + PREF_KEY_LOCATION_LONGITUDE);
        prefs.remove(prefs_prefix + PREF_KEY_LOCATION_LATITUDE);
        prefs.remove(prefs_prefix + PREF_KEY_LOCATION_LABEL);
        prefs.commit();
    }


    public static void saveTimezonePref(Context context, int appWidgetId, String timezone)
    {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_WIDGET, 0).edit();
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_TIMEZONE;
        prefs.putString(prefs_prefix + PREF_KEY_TIMEZONE_CUSTOM, timezone);
        prefs.commit();
    }
    public static String loadTimezonePref(Context context, int appWidgetId)
    {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_WIDGET, 0);
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_TIMEZONE;
        return prefs.getString(prefs_prefix + PREF_KEY_TIMEZONE_CUSTOM, PREF_DEF_TIMEZONE_CUSTOM);
    }
    public static void deleteTimezonePref(Context context, int appWidgetId)
    {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_WIDGET, 0).edit();
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_TIMEZONE;
        prefs.remove(prefs_prefix + PREF_KEY_TIMEZONE_CUSTOM);
        prefs.commit();
    }


    public static void saveCompareModePref(Context context, int appWidgetId, WidgetSettings.CompareMode mode)
    {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_WIDGET, 0).edit();
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_GENERAL;
        prefs.putString(prefs_prefix + PREF_KEY_GENERAL_COMPAREMODE, mode.name());
        prefs.commit();
    }
    public static WidgetSettings.CompareMode loadCompareModePref(Context context, int appWidgetId)
    {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_WIDGET, 0);
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_GENERAL;
        String modeString = prefs.getString(prefs_prefix + PREF_KEY_GENERAL_COMPAREMODE, PREF_DEF_GENERAL_COMPAREMODE.name());

        CompareMode compareMode;
        try
        {
            compareMode = WidgetSettings.CompareMode.valueOf(modeString);

        } catch (IllegalArgumentException e) {
            compareMode = PREF_DEF_GENERAL_COMPAREMODE;
        }
        return compareMode;
    }
    public static void deleteCompareModePref(Context context, int appWidgetId)
    {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_WIDGET, 0).edit();
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_GENERAL;
        prefs.remove(prefs_prefix + PREF_KEY_GENERAL_COMPAREMODE);
        prefs.commit();
    }



    public static void saveTimeNoteRisePref(Context context, int appWidgetId, TimeMode riseChoice)
    {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_WIDGET, 0).edit();
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_GENERAL;
        prefs.putString(prefs_prefix + PREF_KEY_GENERAL_TIMENOTE_RISE, riseChoice.name());
        prefs.commit();
    }
    public static TimeMode loadTimeNoteRisePref(Context context, int appWidgetId)
    {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_WIDGET, 0);
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_GENERAL;
        String modeString = prefs.getString(prefs_prefix + PREF_KEY_GENERAL_TIMENOTE_RISE, PREF_DEF_GENERAL_TIMENOTE_RISE.name());

        TimeMode riseMode;
        try {
            riseMode = WidgetSettings.TimeMode.valueOf(modeString);

        } catch (IllegalArgumentException e) {
            riseMode = PREF_DEF_GENERAL_TIMENOTE_RISE;
        }
        return riseMode;
    }
    public static void deleteTimeNoteRisePref(Context context, int appWidgetId)
    {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_WIDGET, 0).edit();
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_GENERAL;
        prefs.remove(prefs_prefix + PREF_KEY_GENERAL_TIMENOTE_RISE);
        prefs.commit();
    }



    public static void saveTimeNoteSetPref(Context context, int appWidgetId, TimeMode setChoice)
    {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_WIDGET, 0).edit();
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_GENERAL;
        prefs.putString(prefs_prefix + PREF_KEY_GENERAL_TIMENOTE_SET, setChoice.name());
        prefs.commit();
    }
    public static TimeMode loadTimeNoteSetPref(Context context, int appWidgetId)
    {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_WIDGET, 0);
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_GENERAL;
        String modeString = prefs.getString(prefs_prefix + PREF_KEY_GENERAL_TIMENOTE_SET, PREF_DEF_GENERAL_TIMENOTE_SET.name());

        TimeMode setMode;
        try {
            setMode = WidgetSettings.TimeMode.valueOf(modeString);

        } catch (IllegalArgumentException e) {
            setMode = PREF_DEF_GENERAL_TIMENOTE_SET;
        }
        return setMode;
    }
    public static void deleteTimeNoteSetPref(Context context, int appWidgetId)
    {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_WIDGET, 0).edit();
        String prefs_prefix = PREF_PREFIX_KEY + appWidgetId + PREF_PREFIX_KEY_GENERAL;
        prefs.remove(prefs_prefix + PREF_KEY_GENERAL_TIMENOTE_SET);
        prefs.commit();
    }




    public static void deletePrefs(Context context, int appWidgetId)
    {
        deleteActionModePref(context, appWidgetId);
        deleteActionLaunchPref(context, appWidgetId);

        delete1x1ModePref(context, appWidgetId);

        deleteThemePref(context, appWidgetId);
        deleteShowTitlePref(context, appWidgetId);
        deleteTitleTextPref(context, appWidgetId);

        deleteCalculatorModePref(context, appWidgetId);
        deleteTimeModePref(context, appWidgetId);
        deleteCompareModePref(context, appWidgetId);

        deleteLocationModePref(context, appWidgetId);
        deleteLocationPref(context, appWidgetId);

        deleteTimezoneModePref(context, appWidgetId);
        deleteTimezonePref(context, appWidgetId);

        deleteTimeNoteRisePref(context, appWidgetId);
        deleteTimeNoteSetPref(context, appWidgetId);
    }


    public static void initDisplayStrings( Context context )
    {
        ActionMode.initDisplayStrings(context);
        WidgetMode1x1.initDisplayStrings(context);
        CompareMode.initDisplayStrings(context);
        TimeMode.initDisplayStrings(context);
        LocationMode.initDisplayStrings(context);
        TimezoneMode.initDisplayStrings(context);
    }
}