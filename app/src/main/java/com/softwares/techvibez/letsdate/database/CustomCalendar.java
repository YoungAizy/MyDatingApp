package com.softwares.techvibez.letsdate.database;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.CalendarContract;

public class CustomCalendar {

    public CustomCalendar() {
    }

    public static String CreateCalendar(Context context){

        Uri calUri = CalendarContract.Calendars.CONTENT_URI;

        String accountName = "Planit Bookings";
        String accountType =   CalendarContract.ACCOUNT_TYPE_LOCAL;
        String displayName = "Planit Calendar";

        ContentValues cv = new ContentValues();
        cv.put(CalendarContract.Calendars.ACCOUNT_NAME, accountName);
        cv.put(CalendarContract.Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL);
        cv.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, displayName);
        cv.put(CalendarContract.Calendars.OWNER_ACCOUNT, true);

        calUri = asSyncAdapter(calUri, accountName, accountType);

        Uri result = context.getContentResolver().insert(calUri, cv);

        return result.toString();
    }

    private static Uri asSyncAdapter(Uri uri, String account, String accountType) {
        return uri.buildUpon()
                .appendQueryParameter(android.provider.CalendarContract.CALLER_IS_SYNCADAPTER,"true")
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, account)
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, accountType).build();
    }
}
