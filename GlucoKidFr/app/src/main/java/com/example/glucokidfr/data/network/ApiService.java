package com.example.glucokidfr.data.network;

import com.example.glucokidfr.data.dto.ChildDTO;
import com.example.glucokidfr.data.dto.ParentDTO;
import com.example.glucokidfr.data.dto.SugarLevelDTO;

import retrofit2.Call;
import retrofit2.http.*;
import java.util.List;

public interface ApiService {

    @GET("parent/{id}")
    Call<ParentDTO> getParent(@Path("id") long parentId);
    @GET("parents")
    Call<List<ParentDTO>> getAllParents();
    @GET("parent/{id}/children")
    Call<List<ChildDTO>> getChildren(@Path("id") long parentId);
    @GET("child/{id}/sugar")
    Call<List<SugarLevelDTO>> getSugarHistory(@Path("id") long childId);
    @POST("sugar/add")
    Call<Void> addSugar(@Body SugarLevelDTO sugar);
    @POST("parent/register")
    Call<ParentDTO> registerParent(@Body ParentDTO parent);
    @POST("child/add")
    Call<ChildDTO> addChild(@Body ChildDTO child);
}
//HTTP