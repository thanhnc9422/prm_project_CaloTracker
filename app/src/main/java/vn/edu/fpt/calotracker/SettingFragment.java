package vn.edu.fpt.calotracker;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import java.util.Locale;

public class SettingFragment extends Fragment {

    private Switch nightModeSwitch;
//    private Switch nightModeSwitch;
    private boolean nightMode;
    SharedPreferences sharedPreferences;
    public SettingFragment() {
        // Constructor mặc định, bạn có thể để trống hoặc thêm các tham số tùy ý.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        nightModeSwitch = view.findViewById(R.id.nightModeSwitch);
        nightModeSwitch = view.findViewById(R.id.nightModeSwitch);
        sharedPreferences = getActivity().getSharedPreferences("MODE", Context.MODE_PRIVATE);
         nightMode = sharedPreferences.getBoolean("night", false);
        if(nightMode){
            nightModeSwitch.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        nightModeSwitch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                 if(nightMode){
                     AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                     editor.putBoolean("night",false);
                 }else{
                     AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                     editor.putBoolean("night",true);

                 }
                editor.apply();
            }
        });
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putFloat("targetCalo",caloPerOption("loss", "0.25"));
//        editor.putFloat("targetKg",-0.25f);
//        editor.apply();
        return view;
    }

//    public void setLocal(Activity activity, String langCode){
//      Locale locale = new Locale(langCode);
//      locale.setDefault(locale);
//        Resources resources = activity.getResources();
//        Configuration config = resources.getConfiguration();
//        config.setLocales(locale);
//        resources.updateConfiguration(config,resources.getDisplayMetrics());

//    }
}