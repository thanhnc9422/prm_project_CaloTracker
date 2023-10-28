package vn.edu.fpt.calotracker;

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