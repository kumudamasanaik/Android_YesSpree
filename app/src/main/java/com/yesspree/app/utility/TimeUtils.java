package com.yesspree.app.utility;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class TimeUtils {


    public static String getLocalFormattedDate(String dateTime) {
        return getFormattedDateTime(convertGmtToLocal(dateTime));
    }

    public static String getMonthName(int monthId) {
        switch (monthId) {
            case 1:
                return "Jan";
            case 2:
                return "Feb";
            case 3:
                return "Mar";
            case 4:
                return "Apr";
            case 5:
                return "May";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Aug";
            case 9:
                return "Sep";
            case 10:
                return "Oct";
            case 11:
                return "Nov";
            case 12:
                return "Dec";
            default:
                return "MM";
        }
    }

    public static String getMonthDay(int day) {
        return day >= 10 ? "" + day : "0" + day;
    }

    /**
     * Get Current Date Time In String
     *
     * @return
     */
    public static String getCurrentDateTimeInString() {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            return df.format(new Date());
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Time Methods
     */
    public static String getDateFromMilliSec(long milliSeconds) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliSeconds);
            return formatter.format(calendar.getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    /**
     * Get String Frome Date
     *
     * @param date
     * @return
     */
    public static String getStringFromDate(Date date) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            return df.format(date);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Get Date From String Date
     *
     * @param date
     * @return
     */
    public static Date getDateFromStringDate(String date) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            return df.parse(date);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Get Date From String Date Time
     *
     * @param dateTime
     * @return
     */
    public static Date getDateFromStringDateTime(String dateTime) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            return df.parse(dateTime);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Convert GMT To Local Time Zone
     *
     * @param dateTime
     * @return
     */
    public static String convertGmtToLocal(String dateTime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));  // set this if your timezone is different
            Date date1 = sdf.parse(dateTime); // now time is Sun Jan 25 00:00:00 GMT 2015
            sdf.setTimeZone(TimeZone.getDefault());
            Log.d("date", sdf.format(date1)); // now time is Sun Jan 25 05:30:00 IST 2015
            return sdf.format(date1);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Get Current Day Of Week
     *
     * @return WeekDay name
     */
    public static String getCurrentDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
    }

    /**
     * Get Formatted Date
     *
     * @param date
     * @return
     */
    public static String getFormattedDate(String date) {
        try {
            return getMonthDay(Integer.valueOf(date.split("-")[2])) + " " + getMonthName(Integer.valueOf(date.split("-")[1])) + ", " + Integer.valueOf(date.split("-")[0]);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "-";
        }
    }

    /**
     * Get Formatted Date And Time
     *
     * @param date
     * @return
     */
    public static String getFormattedDateTime(String date) {
        try {
            Log.d("ok", "DateTime> " + date + "  " + date.split(" ").length);
            String time = date.split(" ")[1];
            date = date.split(" ")[0];
            return getMonthDay(Integer.valueOf(date.split("-")[2])) + " " + getMonthName(Integer.valueOf(date.split("-")[1])) + ", " + Integer.valueOf(date.split("-")[0]) + " " + time;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Get Day Count From Date Time
     *
     * @param dateTime
     * @return
     */
    public static String getDayCountFromDateTime(String dateTime) {
        try {
            Calendar smsTime = Calendar.getInstance();
            smsTime.setTime(getDateFromStringDateTime(dateTime));
            Calendar now = Calendar.getInstance();
            if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE))
                return "Today";
            else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1)
                return "Yesterday";
            else
                return null;
        } catch (Exception ex) {
            return null;
        }
    }

    public static int getTimeGoneInHours(long oldTime) {
        return (int) (System.currentTimeMillis() - oldTime) / (1000 * 60 * 60);
    }

    public static String getFromatedSlot(String slot) {
        if (slot != null && slot.length() > 0) {
            try {
                String slotStartTime, slotEndTime;

                String date = slot.split(" ")[0];
                String newSlotTime = slot.split(" ")[1];

                date = getMonthDay(Integer.valueOf(date.split("-")[2])) + " " + getMonthName(Integer.valueOf(date.split("-")[1])) + ", " + Integer.valueOf(date.split("-")[0]);

                slotStartTime = newSlotTime.split("-")[0];
                slotEndTime = newSlotTime.split("-")[1];

                slotStartTime = convertTimeTo12Hr(slotStartTime);
                slotEndTime = convertTimeTo12Hr(slotEndTime);

                return date + " " + slotStartTime + "-" + slotEndTime;
            } catch (Exception exp) {
                exp.printStackTrace();
                return " ";
            }

        } else return " ";
    }


    public static String convertTimeTo12Hr(String time) {
        if (time != null && time.length() > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            try {
                Date date3 = sdf.parse(time);
                SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm aa");
                return sdf2.format(date3);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        } else return " ";
    }






    public static String getMyAccountDateFormate(Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(calendar.getTimeZone());
        System.out.println("DATE : " + dateFormat.format(calendar.getTime()));
        //dateFormat.format(calendar.getTime());
        return dateFormat.format(calendar.getTime());
    }




    public static String getValidDateFormate(String strCurrentDate)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        try {
            newDate = format.parse(strCurrentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        format = new SimpleDateFormat("dd-MM-yyyy");
        String date = format.format(newDate);
        return  date;

    }

    public static String splitDate(String date) {

        try {
            Log.d("ok", "DateTime> " + date + "  " + date.split(" ").length);
            date = date.split(" ")[0];
            return getMonthDay(Integer.valueOf(date.split("-")[2])) + "-" + Integer.valueOf(date.split("-")[1]) + "-" + Integer.valueOf(date.split("-")[0]) ;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    public static String getdate(String etStartDate) {
        DateFormat srcDf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = srcDf.parse(etStartDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat destDf = new SimpleDateFormat("dd-MM-yyyy");

        String dateStr = destDf.format(date);
        return dateStr;
    }


    public static String getdateapi(String etStartDate) {
        DateFormat srcDf = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = srcDf.parse(etStartDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DateFormat destDf = new SimpleDateFormat("yyyy-MM-dd");

        String dateStr = destDf.format(date);
        return dateStr;
    }
}
