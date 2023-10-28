package vn.edu.fpt.calotracker;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

public class FoodAdapter extends BaseAdapter {
    private MyDatabaseHelper myDatabaseHelper;
    private Context context;
    private int layout;

    private List<Food> foodList;

    public FoodAdapter(Context context, int layout, List<Food> foodList) {
        this.context = context;
        this.layout = layout;
        this.foodList = foodList;
        myDatabaseHelper = new MyDatabaseHelper(context, "Foods.sqlite", null, 1);
    }
    public interface OnDeleteClickListener {
        void onDeleteClick();
    }
    @Override
    public int getCount() {
        return foodList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView textView;
        TextView itemCalo;
        ImageView itemImage;
        Button btnDele;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.textView);
            viewHolder.itemCalo = (TextView) convertView.findViewById(R.id.itemCalo);
            viewHolder.itemImage = (ImageView) convertView.findViewById(R.id.itemImage);
            viewHolder.btnDele = (Button) convertView.findViewById(R.id.btnDele);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Food food = foodList.get(position);
        if(!food.getName().startsWith("add_")){

            viewHolder.textView.setText(food.getName());
            String imageNameFromSQLite = food.getName();
            Resources resources = context.getResources();
            final int resourceId = resources.getIdentifier(imageNameFromSQLite, "drawable",
                    context.getPackageName());
            viewHolder.itemImage.setImageResource(resourceId);
        }else{
            viewHolder.textView.setText(food.getName().substring(4));
//            File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/"+food.getName()+".jpg");
//
//            if (file.exists()) {
//                // Tệp ảnh tồn tại
             viewHolder.btnDele.setVisibility(View.VISIBLE);
            viewHolder.btnDele.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDatabaseHelper.QueryData("DELETE FROM Food WHERE name = '"+food.getName()+"' AND calo = "+food.getCalo());
                    notifyDataSetChanged(); // Cập nhật ListView
                    Toast.makeText(context, "Delete successfully!", Toast.LENGTH_LONG).show();
                }
            });
                viewHolder.itemImage.setImageResource(R.drawable.baseline_food_bank_24);
//            } else {
//
//            }
        }
        viewHolder.itemCalo.setText(food.getCalo()+" kcal");

        return convertView;
    }
}
