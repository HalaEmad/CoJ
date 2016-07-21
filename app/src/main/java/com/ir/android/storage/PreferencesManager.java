/**
 *
 */
package com.ir.android.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * @author bahamada
 */
public class PreferencesManager {
    private Context mContext;

    private static PreferencesManager sSelf;

    private SharedPreferences mSettings;

    private SharedPreferences.Editor mEditor;

    private static final String FIRST_RUN_KEY = "key.first.run";

    private PreferencesManager(Context context) {
        mContext = context;
        mSettings = PreferenceManager.getDefaultSharedPreferences(mContext);
        mEditor = mSettings.edit();
    }

    /**
     * get settings manager instance
     *
     * @param context
     * @return
     */
    public static PreferencesManager getInstance(Context context) {
        if (sSelf == null) {
            sSelf = new PreferencesManager(context);
        }
        return sSelf;
    }

    public void setFirstRun(boolean firstRun) {
        mEditor.putBoolean(FIRST_RUN_KEY, firstRun);
        mEditor.commit();
    }

    public boolean isFirstRun() {
        return mSettings.getBoolean(FIRST_RUN_KEY, true);
    }
}
