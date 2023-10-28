package vn.edu.fpt.calotracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST_CODE = 22;
    private static final int WRITE_EXTERNAL_STORAGE_CODE = 1;
    private MyDatabaseHelper myDatabaseHelper;
    private ListView listViewFoods;
    private ArrayList<Food> arrayList;
    private FoodAdapter foodAdapter;
    private SharedPreferences sharedPreferences;
    private TextView editTextFoodName;
    private TextView editTextCalories;
    private Button buttonAddImg;
    private Button buttonAddFood;
    private ImageView showImg;
    private Bitmap imageBitmap;
    private OutputStream outputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewFoods = findViewById(R.id.listViewFoods);
        editTextFoodName = findViewById(R.id.editTextFoodName);
        editTextCalories = findViewById(R.id.editTextCalories);
        showImg = findViewById(R.id.showImg);
        buttonAddImg = findViewById(R.id.buttonAddImg);
        buttonAddFood = findViewById(R.id.buttonAddFood);
        arrayList = new ArrayList<>();
        foodAdapter = new FoodAdapter(this, R.layout.item_listview, arrayList);
        listViewFoods.setAdapter(foodAdapter);


        FoodAdapter.OnDeleteClickListener onDeleteClickListener = new FoodAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick() {
                // Thực hiện các công việc cần thiết, sau đó làm mới hoạt động
                recreate();
            }
        };
//        buttonAddImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent, CAMERA_REQUEST_CODE);
//            }
//        });

        buttonAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDatabaseHelper.QueryData("INSERT INTO Food VALUES (NULL , 'add_"+editTextCalories.getText()+"', "+Float.parseFloat(editTextCalories.getText().toString())+")");
                recreate();
//                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
//                    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
//                        String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
//                        requestPermissions(permission,WRITE_EXTERNAL_STORAGE_CODE);
//                    }
//                    else{
//                        saveImg();
//                    }
//                }
            }
        });

        listViewFoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();


                TextView v = (TextView) view.findViewById(R.id.textView);
                TextView itemCalo = (TextView) view.findViewById(R.id.itemCalo);

                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.popup_layout); // Sử dụng layout bạn đã tạo
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_popup_bg);

                EditText edtGram = dialog.findViewById(R.id.edtGram);
                Button btnCancel = dialog.findViewById(R.id.btnCancel);
                Button btnOk = dialog.findViewById(R.id.btnOK);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnOk.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        float valueCalo = sharedPreferences.getFloat("currentCalo", 0);
                        int eatGram = Integer.parseInt(edtGram.getText().toString());
                        float i = Float.parseFloat(itemCalo.getText().toString().substring(0, itemCalo.getText().toString().length() - 5));
                        float caloCal = (eatGram * i) / 100;
                        Log.i("demo", valueCalo + " " + eatGram + " " + i + " " + caloCal);
                        editor.putFloat("currentCalo", caloCal + valueCalo);
                        editor.apply();
                        Toast.makeText(getApplicationContext(), "Successful calorie update", Toast.LENGTH_LONG).show();
//
//                        FragmentManager fragmentManager = getSupportFragmentManager();
//                        fragmentManager.beginTransaction().replace(R.id.fragment_container, new TrackerFragment()).commit();
                    }

                });
                dialog.show();
            }
        });
        //tao database
        myDatabaseHelper = new MyDatabaseHelper(this, "Foods.sqlite", null, 1);

        //tao bang
//        myDatabaseHelper.QueryData("DROP TABLE Food");

//        myDatabaseHelper.QueryData("CREATE TABLE IF NOT EXISTS Food(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(200), calo DECIMAL)");
//        myDatabaseHelper.QueryData("INSERT INTO Food VALUES (NULL , 'meat', 242.1)");
//        myDatabaseHelper.QueryData("INSERT INTO Food VALUES (NULL , 'shrimp', 99.2)");
//        myDatabaseHelper.QueryData("INSERT INTO Food VALUES (NULL , 'egg', 155.1)");
//        myDatabaseHelper.QueryData("INSERT INTO Food VALUES (NULL , 'vegetable', 65.2)");
//        myDatabaseHelper.QueryData("INSERT INTO Food VALUES (NULL , 'apple', 50)");
//        myDatabaseHelper.QueryData("INSERT INTO Food VALUES (NULL , 'banana', 88.7)");
//        myDatabaseHelper.QueryData("INSERT INTO Food VALUES (NULL , 'chicken', 239)");
        Cursor dataFood = myDatabaseHelper.GetData("select * from Food");
        while (dataFood.moveToNext()) {
            int id = dataFood.getInt(0);
            String name = dataFood.getString(1);
            float calo = Float.parseFloat(dataFood.getString(2));

            arrayList.add(new Food(id, name, calo));
        }
        foodAdapter.notifyDataSetChanged();



    }

//    private void saveImg() {
//        imageBitmap =((BitmapDrawable) showImg.getDrawable()).getBitmap();
//        File path = Environment.getExternalStorageDirectory();
//        File dir = new File(path+"/DCIM");
//        dir.mkdirs();
//        String imgName = "add_"+editTextFoodName.getText()+"png";
//        File file = new File(dir,imgName);
//        OutputStream out;
//
//        try{
//            out = new FileOutputStream(file);
//            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//            Toast.makeText(getApplicationContext(), "add img to dcim", Toast.LENGTH_LONG).show();
//
//        }catch (Exception e){
//            Toast.makeText(getApplicationContext(), "fail add img to dcim", Toast.LENGTH_LONG).show();
//
//        }
//
//        myDatabaseHelper.QueryData("INSERT INTO Food VALUES (NULL , 'add_"+editTextFoodName.getText()+"', "+editTextCalories.getText()+")");
//
//
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            imageBitmap = (Bitmap) extras.get("data");
//            showImg.setImageBitmap(imageBitmap);
//
//
//        }
//
//    }
}