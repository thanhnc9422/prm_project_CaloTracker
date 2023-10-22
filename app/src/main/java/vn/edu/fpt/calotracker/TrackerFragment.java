package vn.edu.fpt.calotracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class TrackerFragment extends Fragment {
    private Button btnUpdateCl;
    public TrackerFragment() {
        // Constructor mặc định, bạn có thể để trống hoặc thêm các tham số tùy ý.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_home, container, false);
             btnUpdateCl = view.findViewById(R.id.buttonUpdateCalories);
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