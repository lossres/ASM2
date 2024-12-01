package com.example.lab1;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;


public interface APIService {

//    String DOMAIN = "http://192.168.23.30:3000/"; // localhost
    String DOMAIN = "http://10.24.49.99:3000/"; // localhost

    @GET("/api/list")
    Call<List<CarModel>> getCars();


    @POST("/api/add_xe")
    Call<List<CarModel>> addCar(@Body CarModel xe);

    @PUT("/api/update_xe")
    Call<List<CarModel>> updateCar(@Body CarModel xe);

    @DELETE("/api/xoa")
    Call<List<CarModel>> deleteCar(String id);
}