package vn.edu.fpt.calotracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class TrackerFragment extends Fragment {
    private Button btnUpdateCl;
    private SharedPreferences sharedPreferences;
    private TextView TexViewTargetCalo;
    private TextView textViewCaloriesConsumed;
    private TextView textViewCaloriesCurrent;
    public TrackerFragment() {
        // Constructor mặc định, bạn có thể để trống hoặc thêm các tham số tùy ý.
    }
    public void updateTextView(String newText) {
        textViewCaloriesCurrent.setText(newText);
    }
    public void updateTrackerFragment() {
        sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        float valueCalo = sharedPreferences.getFloat("targetCalo", 0);
        float valueKg = sharedPreferences.getFloat("targetKg", 0);
        float valueCurrentCalo = sharedPreferences.getFloat("currentCalo", 0);

        TexViewTargetCalo.setText("target "+valueKg+" kg/weeks");
        textViewCaloriesConsumed.setText(String.format("%.2f",valueCalo));
        textViewCaloriesCurrent.setText(String.format("%.2f", valueCurrentCalo));
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//=========================dat lich sau 0h00 set lại data=====================================
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), ResetXReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, PendingIntent.FLAG_IMMUTABLE);

// Lên lịch công việc vào 00:00 hàng ngày
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 0); // Giờ 0
        calendar.set(Calendar.MINUTE, 0); // Phút 0

        long triggerTime = calendar.getTimeInMillis();

// Nếu thời gian đã trôi qua 00:00 hôm nay, hãy lên lịch vào 00:00 hôm sau
        if (System.currentTimeMillis() > triggerTime) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            triggerTime = calendar.getTimeInMillis();
        }

// Sử dụng AlarmManager để lên lịch công việc
        alarmManager.setRepeating(AlarmManager.RTC, triggerTime, AlarmManager.INTERVAL_DAY, pendingIntent);
//==============================================================


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tracker, container, false);
             btnUpdateCl = view.findViewById(R.id.buttonUpdateCalories);
        TexViewTargetCalo = view.findViewById(R.id.TexViewTargetCalo);
        textViewCaloriesCurrent = view.findViewById(R.id.textViewCaloriesCurrent);
        textViewCaloriesConsumed = view.findViewById(R.id.textViewCaloriesConsumed);
        updateTrackerFragment();
        btnUpdateCl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}