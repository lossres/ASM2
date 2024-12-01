package com.example.lab1;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class After extends AppCompatActivity {

    ListView lvMain;
    List<CarModel> listCarModel;

    CarAdapter carAdapter;
    private Retrofit retrofit;
    private  APIService apiService;
    private FloatingActionButton btn_add;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_after);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lvMain = findViewById(R.id.listviewMain);
        btn_add = findViewById(R.id.btn_add);

        retrofit = new Retrofit.Builder()
                .baseUrl(APIService.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        loadData();




        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_Data();
            }
        });

    }

    private void add_Data() {

        AlertDialog.Builder builder = new AlertDialog.Builder(After.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_submit, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText edt_name_new = view.findViewById(R.id.edt_name_new);
        EditText edt_date_new = view.findViewById(R.id.edt_date_new);
        EditText edt_brand_new = view.findViewById(R.id.edt_brand_new);
        EditText edt_price_new = view.findViewById(R.id.edt_price_new);
        Button btn_submit_dialog = view.findViewById(R.id.btn_submit_dialog);
//        Button btn_Update_Car = view.findViewById(bt)



        btn_submit_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = edt_name_new.getText().toString().trim();
                String date = edt_date_new.getText().toString().trim();
                String brand = edt_brand_new.getText().toString().trim();
                String price = edt_price_new.getText().toString().trim();

                CarModel carModel = new CarModel();
                carModel.set_id(null);
                carModel.setTen(name);
                carModel.setNamSX(Integer.parseInt(date));
                carModel.setHang(brand);
                carModel.setGia(Double.parseDouble(price));

                Call<List<CarModel>> callAddXe = apiService.addCar(carModel);

                callAddXe.enqueue(new Callback<List<CarModel>>() {
                    @Override
                    public void onResponse(Call<List<CarModel>> call, Response<List<CarModel>> response) {
                        if (response.isSuccessful()) {

                            listCarModel.clear();
                            listCarModel.addAll(response.body());
                            loadData();
                            carAdapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<CarModel>> call, Throwable t) {
                        Log.e("Main", t.getMessage());
                    }
                });
            }
        });



    }

    private void loadData() {
        apiService = retrofit.create(APIService.class);

        Call<List<CarModel>> call = apiService.getCars();

        call.enqueue(new Callback<List<CarModel>>() {
            @Override
            public void onResponse(Call<List<CarModel>> call, Response<List<CarModel>> response) {
                if (response.isSuccessful()) {
                    listCarModel = response.body();

                    carAdapter = new CarAdapter(After.this, listCarModel);

                    lvMain.setAdapter(carAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<CarModel>> call, Throwable t) {
                Log.e("Main", t.getMessage());
            }
        });
    }
}