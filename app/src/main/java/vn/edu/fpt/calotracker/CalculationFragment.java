package vn.edu.fpt.calotracker;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.RadioGroup;
import androidx.fragment.app.Fragment;

import java.time.ZoneId;

public class CalculationFragment extends Fragment {
    private Spinner spinnerExerciseFrequency;
    private Spinner spinnerMany;
    private Spinner spinnerGainOrLoss;
    private EditText edtAge;
    private RadioGroup radioGroupGender;
    private EditText edtHeight;
    private EditText edtWeight;
    private Button btnCalculate;
    private TextView tvBMIResult;
    private Button saveButton;
    private SharedPreferences sharedPreferences;
    private String genderSelected;
    private View vWeightGain;
    private View vWeightLoss;
    private TextView clUp050;
    private TextView clUp025;
    private TextView clUp100;
    private TextView clLoss050;
    private TextView clLoss025;
    private TextView clLoss100;
    private TextView clBlLoss;
    private TextView clBl;
    private TableLayout tbUp;
    private TableLayout tbLoss;
    private TableLayout yourChoice;
    private float caloriesNeeded;
    public CalculationFragment() {
        // Constructor mặc định, bạn có thể để trống hoặc thêm các tham số tùy ý.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calculation, container, false);
        radioGroupGender = view.findViewById(R.id.radioGroupGender);
        saveButton = view.findViewById(R.id.saveButton);
        vWeightLoss = view.findViewById(R.id.vWeightLoss);
        vWeightGain = view.findViewById(R.id.vWeightGain);
        tvBMIResult = view.findViewById(R.id.tvBMIResult);

        clUp050 = view.findViewById(R.id.clUp050);
        clUp025 = view.findViewById(R.id.clUp025);
        clUp100 = view.findViewById(R.id.clUp100);

        clLoss050 = view.findViewById(R.id.clLoss050);
        clLoss025 = view.findViewById(R.id.clLoss025);
        clLoss100 = view.findViewById(R.id.clLoss100);

        clBl =  view.findViewById(R.id.clBl);
        clBlLoss =  view.findViewById(R.id.clBlLoss);

        tbUp = view.findViewById(R.id.tbUp);
        tbLoss = view.findViewById(R.id.tbLoss);
        yourChoice = view.findViewById(R.id.yourChoice);
        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Kiểm tra xem RadioButton nào được chọn
                if (checkedId == R.id.radioMale) {
                    genderSelected = "male";
                } else if (checkedId == R.id.radioFemale) {
                    genderSelected = "female";
                }
            }
        });


        edtAge = view.findViewById(R.id.edtAge);
        edtHeight = view.findViewById(R.id.edtHeight);
        edtWeight = view.findViewById(R.id.edtWeight);
        btnCalculate = view.findViewById(R.id.btnCalculate);
        spinnerExerciseFrequency = view.findViewById(R.id.spinnerExerciseFrequency);
        spinnerGainOrLoss = view.findViewById(R.id.spinnerGainOrLoss);
        spinnerMany = view.findViewById(R.id.spinnerMany);
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBMIAndCalories();
                vWeightGain.setVisibility(View.VISIBLE);
                vWeightLoss.setVisibility(View.VISIBLE);
                saveButton.setVisibility(View.VISIBLE);
                tbUp.setVisibility(View.VISIBLE);
                tbLoss.setVisibility(View.VISIBLE);
                yourChoice.setVisibility(View.VISIBLE);

            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if(spinnerGainOrLoss.equals("Weight loss")) {
                    if(spinnerMany.getSelectedItem().toString().equals("0.25 (kg)")){
                      editor.putFloat("targetCaloLoss",caloPerOption("Loss", "0.25"));
                        editor.apply();
                    }
                    if(spinnerMany.getSelectedItem().toString().equals("0.05 (kg)")){
                        editor.putFloat("targetCaloLoss",caloPerOption("Loss", "0.50"));
                        editor.apply();
                    }
                    if(spinnerMany.getSelectedItem().toString().equals("1 (kg)")){
                        editor.putFloat("targetCaloLoss",caloPerOption("Loss", "1.00"));
                        editor.apply();
                    }
                }else if(spinnerGainOrLoss.equals("Weight gain")){
                    if(spinnerMany.getSelectedItem().toString().equals("0.25 (kg)")){
                        editor.putFloat("targetCaloGain",caloPerOption("Gain", "0.25"));
                        editor.apply();
                    }
                    if(spinnerMany.getSelectedItem().toString().equals("0.50 (kg)")){
                        editor.putFloat("targetCaloGain",caloPerOption("Gain", "0.50"));
                        editor.apply();
                    }
                    if(spinnerMany.getSelectedItem().toString().equals("1.00 (kg)")){
                        editor.putFloat("targetCaloGain",caloPerOption("Gain", "1.00"));
                        editor.apply();
                    }
                }
            }
        });
        // Tạo mảng các tùy chọn cho Spinner
        String[] exerciseFrequencyOptions = {"ít hoặc không tập thể dục", "1-3 lần một tuần", "4-5 lần một tuần", "6-7 lần một tuần"};
        String[] GainOrLossOptions = {"Weight loss", "Weight gain"};
        String[] ManyOptions = {"0.25 (kg)", "0.5 (kg)", "1.00 (kg)"};

        // Tạo ArrayAdapter để hiển thị danh sách tùy chọn
        ArrayAdapter<String> adapterspinnerExerciseFrequency = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, exerciseFrequencyOptions);
        adapterspinnerExerciseFrequency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> adapterspinnerGainOrLoss = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, GainOrLossOptions);
        adapterspinnerGainOrLoss.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> adapterspinnerMany = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, ManyOptions);
        adapterspinnerMany.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Đặt ArrayAdapter cho Spinner
        spinnerExerciseFrequency.setAdapter(adapterspinnerExerciseFrequency);
        spinnerGainOrLoss.setAdapter(adapterspinnerGainOrLoss);
        spinnerMany.setAdapter(adapterspinnerMany);

        return view;
    }
    private void calculateBMIAndCalories() {
        // Lấy giá trị từ các trường nhập liệu
        int age = Integer.parseInt(edtAge.getText().toString());
        String gender = genderSelected;
        float height = Float.parseFloat(edtHeight.getText().toString());
        float weight = Float.parseFloat(edtWeight.getText().toString());
        String exerciseFrequency = spinnerExerciseFrequency.getSelectedItem().toString();

        // Tính toán BMI (Chỉ số khối cơ thể)
        float bmi = calculateBMI(height, weight);
        String weightStatus = determineWeightStatus(bmi);

        // Hiển thị kết quả BMI
        tvBMIResult.setText("BMI: " + String.format("%.2f", bmi) + " (" + weightStatus + ")");

        // Tính toán Calo cần nạp mỗi ngày tương ứng với tần suất tập thể dục
        caloriesNeeded = calculateCaloriesNeeded(age, weight, height, gender, exerciseFrequency);

        // Hiển thị kết quả Calo cần nạp
//        tvCaloriesResult.setText("Calories Needed: " + String.format("%.2f", caloriesNeeded) + " kcal");
//         tvCaloriesGain.setText("Calories Needed to weight gain (0.25kg/week): " + String.format("%.2f", caloriesNeeded*1.09) + " kcal\n"+
//                                "Calories Needed to weight gain (0.5kg/week):" + String.format("%.2f", caloriesNeeded*1.18) + " kcal\n"+
//                                "Calories Needed to weight gain (1kg/week): " + String.format("%.2f", caloriesNeeded*1.27) + " kcal"
//                 );

        clBlLoss.setText(String.format("%.2f", caloriesNeeded)+ " kcal");
        clBl.setText(String.format("%.2f", caloriesNeeded)+ " kcal");


         clUp025.setText(String.format("%.2f", caloriesNeeded*1.09)+ " kcal");
         clUp050.setText(String.format("%.2f", caloriesNeeded*1.18)+ " kcal");
         clUp100.setText(String.format("%.2f", caloriesNeeded*1.27)+ " kcal");
        clLoss025.setText(String.format("%.2f", caloriesNeeded*0.91)+ " kcal");
        clLoss050.setText(String.format("%.2f", caloriesNeeded*0.82)+ " kcal");
        clLoss100.setText(String.format("%.2f", caloriesNeeded*0.73)+ " kcal");

//        tvCaloriesLoss.setText("Calories Needed to weight loss: " + String.format("%.2f", caloriesNeeded-250) + " kcal");
    }

    private float calculateBMI(float height, float weight) {
        // Tính BMI = (Cân nặng (kg)) / (Chiều cao (m))^2
        return weight / ((height / 100) * (height / 100));
    }
    private float calculateBMR(int age, float weight, float height,String genderSelected) {
        float BMR = 0.0f;
        if(genderSelected.equals("male")){
            BMR = 10*weight + 6.25f*height -5*age +5;
        }else{
            BMR = 10*weight + 6.25f*height -5*age -161;
        }
        return BMR;
    }

    private float calculateCaloriesNeeded(int age, float weight, float height,String gender, String exerciseFrequency) {
        // Thực hiện tính toán Calo cần nạp dựa trên thông tin age, weight, và exerciseFrequency
        // Các thuật toán tính toán Calo thường có sẵn, tùy thuộc vào mục tiêu (giảm cân, tăng cân, duy trì cân nặng).
        // Ở đây, bạn có thể thay thế bằng logic thích hợp cho từng mục tiêu.
        float caloriesNeeded = 0.0f;

        if (exerciseFrequency.equals("ít hoặc không tập thể dục")) {
            caloriesNeeded = calculateBMR(age,weight,height,genderSelected)*1.2f;
        } else if (exerciseFrequency.equals("1-3 lần một tuần")) {
            caloriesNeeded = calculateBMR(age,weight,height,genderSelected)*1.375f;
        } else if (exerciseFrequency.equals("4-5 lần một tuần")) {
            caloriesNeeded = calculateBMR(age,weight,height,genderSelected)*1.55f;
        } else if (exerciseFrequency.equals("6-7 lần một tuần")) {
            caloriesNeeded = calculateBMR(age,weight,height,genderSelected)*1.725f;
        }

        return caloriesNeeded;
    }
    private String determineWeightStatus(float bmi) {
        // Dựa vào giá trị BMI, xác định tình trạng cân nặng
        if (bmi < 18.5) {
            return "Thiếu cân";
        } else if (bmi < 24.9) {
            return "Cân đối";
        } else {
            return "Thừa cân";
        }
    }
    private float caloPerOption(String option, String expect){
        if(option.equals("loss")){
            if(expect.equals("0.25")){
               return (float) ((float) caloriesNeeded*0.91);
            }
            if(expect.equals("0.50")){
                return (float) ((float) caloriesNeeded*0.82);
            }
            if(expect.equals("1.00")){
                return (float) ((float) caloriesNeeded*0.73);
            }
        }else{
            if(expect.equals("0.25")){
                return (float) ((float) caloriesNeeded*1.09);
            }
            if(expect.equals("0.50")){
                return (float) ((float) caloriesNeeded*1.18);
            }
            if(expect.equals("1.00")){
                return (float) ((float) caloriesNeeded*1.27);
            }
        }
        return 0;
    }
}
