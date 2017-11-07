package precisioninfomatics.servicefirst.SharedPreferences;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by 4264 on 08/03/2017.
 */

 public class SharedPreferenceManager
{
    private static SharedPreferences mSharedPref;

    public static void init(Context context)
    {
        if(mSharedPref == null)
            mSharedPref = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public static String getString(String key, String defValue) {
        return mSharedPref.getString(key, defValue);
    }
    public static String getServiceString(String key, String defValue,Context context) {
        mSharedPref = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return mSharedPref.getString(key, defValue);
    }
    public static void puString(String key, String value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }

    public static boolean getBooean(String key, boolean defValue) {
        return mSharedPref.getBoolean(key, defValue);
    }
    public static void putBoolean(String key, boolean value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.apply();
    }
    public static Integer getInteger(String key, int defValue) {
        return mSharedPref.getInt(key, defValue);
    }

    public static void putInteger(String key, Integer value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putInt(key, value).apply();
    }
    public static void RemoveValue(String key){
        mSharedPref.edit().remove(key).apply();
    }
    public static Integer getServiceInteger(String key, int  defValue, Context context) {
        mSharedPref = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return mSharedPref.getInt(key, defValue);
    }
    public static boolean getServiceBoolean(String key, boolean  defValue, Context context) {
        mSharedPref = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return mSharedPref.getBoolean(key, defValue);
    }
}