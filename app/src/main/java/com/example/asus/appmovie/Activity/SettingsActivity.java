package com.example.asus.appmovie.Activity;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.example.asus.appmovie.Preference.AppPreference;
import com.example.asus.appmovie.R;

public class SettingsActivity extends AppCompatActivity {

    Switch dailySwitch;
    Switch releaseSwitch;
    private DailyReminder dailyReminder;
    private ReleaseReminder releaseReminder;
    private AppPreference appPreference;
    LinearLayout linearLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        dailySwitch = findViewById(R.id.switch_daily);
        releaseSwitch = findViewById(R.id.switch_release);
        linearLanguage = findViewById(R.id.linear_language);

        dailyReminder = new DailyReminder();
        releaseReminder = new ReleaseReminder();
        appPreference = new AppPreference(this);
        setNotifyReminder();

        linearLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setting = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(setting);
            }
        });

        dailySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean isDaily = dailySwitch.isChecked();
                if (isDaily){
                    dailySwitch.setEnabled(true);
                    appPreference.setDaily(true);
                    dailyReminder.setRepeatingAlarm(SettingsActivity.this, DailyReminder.TYPE_REPEATING, "07:00", getString(R.string.message_notif_daily));
                } else {
                    dailySwitch.setChecked(false);
                    appPreference.setDaily(false);
                    dailyReminder.cancelAlarm(SettingsActivity.this, DailyReminder.TYPE_REPEATING);
                }
            }
        });

        releaseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean isRelease = releaseSwitch.isChecked();
                if (isRelease){
                    releaseSwitch.setEnabled(true);
                    appPreference.setRelease(true);
                    releaseReminder.setRepeatingAlarm(SettingsActivity.this, ReleaseReminder.TYPE_REPEATING, "08:00", getString(R.string.message_notif_release));
                } else {
                    releaseSwitch.setChecked(false);
                    appPreference.setRelease(false);
                    releaseReminder.cancelAlarm(SettingsActivity.this, ReleaseReminder.TYPE_REPEATING);
                }
            }
        });
    }

    private void setNotifyReminder() {
        if (appPreference.isDaily()){
            dailySwitch.setChecked(true);
        } else {
            dailySwitch.setChecked(false);
        }

        if (appPreference.isRelease()){
            releaseSwitch.setChecked(true);
        } else {
            releaseSwitch.setChecked(false);
        }
    }
}
