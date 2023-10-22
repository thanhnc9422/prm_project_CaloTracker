package vn.edu.fpt.calotracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private MyDatabaseHelper myDatabaseHelper;
    private ListView listViewFoods;
    private ArrayList<Food> arrayList;
    private FoodAdapter foodAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewFoods = findViewById(R.id.listViewFoods);
        arrayList = new ArrayList<>();
        foodAdapter = new FoodAdapter(this, R.layout.item_listview, arrayList);
        listViewFoods.setAdapter(foodAdapter);
       //tao database
        myDatabaseHelper = new MyDatabaseHelper(this,"Foods.sqlite",null,1);

        //tao bang
//        myDatabaseHelper.QueryData("DROP TABLE Food");

        myDatabaseHelper.QueryData("CREATE TABLE IF NOT EXISTS Food(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(200), calo DECIMAL)");
//        myDatabaseHelper.QueryData("INSERT INTO Food VALUES (NULL , 'meat', 242.1)");
//        myDatabaseHelper.QueryData("INSERT INTO Food VALUES (NULL , 'shrimp', 99.2)");
//        myDatabaseHelper.QueryData("INSERT INTO Food VALUES (NULL , 'egg', 155.1)");
//        myDatabaseHelper.QueryData("INSERT INTO Food VALUES (NULL , 'vegetable', 65.2)");
//        myDatabaseHelper.QueryData("INSERT INTO Food VALUES (NULL , 'apple', 50)");
//        myDatabaseHelper.QueryData("INSERT INTO Food VALUES (NULL , 'banana', 88.7)");
//        myDatabaseHelper.QueryData("INSERT INTO Food VALUES (NULL , 'chicken', 239)");
        Cursor dataFood = myDatabaseHelper.GetData("select * from Food");
        while (dataFood.moveToNext()){
            int id =dataFood.getInt(0);
            String name = dataFood.getString(1);
            float calo = Float.parseFloat(dataFood.getString(2));

            arrayList.add(new Food(id, name, calo));
        }
        foodAdapter.notifyDataSetChanged();
    }
}