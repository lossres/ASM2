package com.example.lab1;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.List;

public class CarAdapter extends BaseAdapter {
    List<CarModel> carModelList;

    Context context;

    public CarAdapter (Context context, List<CarModel> carModelList) {
        this.context = context;
        this.carModelList = carModelList;
    }

    @Override
    public int getCount() {
        return carModelList.size();
    }

    @Override
    public Object getItem(int i) {
        return carModelList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_car, viewGroup, false);

        TextView tvID = (TextView) rowView.findViewById(R.id.tvId);
        ImageView imgAvatar = (ImageView) rowView.findViewById(R.id.imgAvatatr);
        TextView tvName = (TextView) rowView.findViewById(R.id.tvName);

        TextView tvNamSX = (TextView) rowView.findViewById(R.id.tvNamSX);

        TextView tvHang = (TextView) rowView.findViewById(R.id.tvHang);

        TextView tvGia = (TextView) rowView.findViewById(R.id.tvGia);

//        String imageUrl = mList.get(position).getThumbnailUrl();
//        Picasso.get().load(imageUrl).into(imgAvatar);
////        imgAvatar.setImageResource(imageId[position]);
        tvName.setText(String.valueOf(carModelList.get(position).getTen()));

        tvNamSX.setText(String.valueOf(carModelList.get(position).getNamSX()));

        tvHang.setText(String.valueOf(carModelList.get(position).getHang()));

        tvGia.setText(String.valueOf(carModelList.get(position).getGia()));

        Button btnUpdate_Car = rowView.findViewById(R.id.btnUpdate_Car);
        Button btnDelete_Car = rowView.findViewById(R.id.btnDelete_Car);

        rowView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_submit, null);
                builder.setView(view);
                Dialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });
        btnUpdate_Car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement update functionality here
                CarModel car = carModelList.get(position);

                // Check if car is null
                if (car == null) {
                    Log.e("CarAdapter", "Car is null, cannot update");
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_update, null); // Create `dialog_update.xml` layout for update dialog
                builder.setView(dialogView);

                TextView editTextName = dialogView.findViewById(R.id.editName); // Add inputs for update in `dialog_update.xml`
                TextView editTextNamSX = dialogView.findViewById(R.id.editNamSX); // Add inputs for update in `dialog_update.xml`
                TextView editTextHang = dialogView.findViewById(R.id.editHang); // Add inputs for update in `dialog_update.xml`
                TextView editTextGia = dialogView.findViewById(R.id.editGia); // Add inputs for update in `dialog_update.xml`
                Button btnConfirm = dialogView.findViewById(R.id.btnConfirm);

                // Set current values for editing
                editTextName.setText(car.getTen());
                editTextNamSX.setText(String.valueOf(car.getNamSX())); // Convert int to string
                editTextHang.setText(car.getHang());
                editTextGia.setText(String.valueOf(car.getGia())); // Convert double to string

                Dialog dialog = builder.create();
                dialog.show();

                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Log the values to check before updating
                        String name = editTextName.getText().toString();
                        String namSX = editTextNamSX.getText().toString();
                        String hang = editTextHang.getText().toString();
                        String gia = editTextGia.getText().toString();

                        Log.d("CarAdapter", "Name: " + name + ", NamSX: " + namSX + ", Hang: " + hang + ", Gia: " + gia);

                        // Validate input
                        if (name.isEmpty() || namSX.isEmpty() || hang.isEmpty() || gia.isEmpty()) {
                            Log.e("CarAdapter", "Invalid input values");
                            return; // Prevent update if any field is empty
                        }

                        // Update car details
                        car.setTen(name);

                        // Parse NamSX and Gia, handling invalid input gracefully
                        try {
                            car.setNamSX(Integer.parseInt(namSX)); // Handle integer parsing
                        } catch (NumberFormatException e) {
                            Log.e("CarAdapter", "Invalid NamSX: " + namSX, e);
                            return; // Do not update if NamSX is invalid
                        }

                        car.setHang(hang);

                        try {
                            car.setGia(Double.parseDouble(gia)); // Handle double parsing
                        } catch (NumberFormatException e) {
                            Log.e("CarAdapter", "Invalid Gia: " + gia, e);
                            return; // Do not update if Gia is invalid
                        }

                        // Refresh the list
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
            }
        });

        btnDelete_Car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement delete functionality here
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Bạn có chắc xóa xe này không?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            carModelList.remove(position); // Remove item from list
                            notifyDataSetChanged(); // Refresh the list
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        return rowView;
    }
}
