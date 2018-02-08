package org.hvdw.sofiaservernokill;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {
    public static final String ACTION_PREF_NO_KILL_CHANGED = "org.hvdw.sofiaservernokill.action.ACTION_PREF_NO_KILL_CHANGED";
    public static final String EXTRA_PREF_NO_KILL_ENABLED = "org.hvdw.sofiaservernokill.extra.PREF_NO_KILL_ENABLED";
    public static final String PREF_NO_KILL = "pref_no_kill";






    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);



        getPreferenceManager().setSharedPreferencesMode(Context.MODE_WORLD_READABLE);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Intent intent = new Intent();

        switch (key) {
            case PREF_NO_KILL:
                intent.setAction(ACTION_PREF_NO_KILL_CHANGED);
                intent.putExtra(EXTRA_PREF_NO_KILL_ENABLED, sharedPreferences.getBoolean(key, false));
                break;
        }

        if (intent.getAction() != null) {
            getActivity().sendBroadcast(intent);
        }
    }
}
