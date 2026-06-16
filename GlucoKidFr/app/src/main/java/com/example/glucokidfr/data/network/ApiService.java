package com.example.glucokidfr.data.network;

import com.example.glucokidfr.data.dto.ChildDTO;
import com.example.glucokidfr.data.dto.ParentDTO;
import com.example.glucokidfr.data.dto.SugarLevelDTO;
import com.example.glucokidfr.domain.entities.Child;

import retrofit2.Call;
import retrofit2.http.*;
import java.util.List;

public interface ApiService {

    @GET("parent/{id}")
    Call<ParentDTO> getParent(@Path("id") long parentId);
    @GET("parent")
    Call<List<ParentDTO>> getAllParents();
    @GET("parent/{id}/children")
    Call<List<ChildDTO>> getChildren(@Path("id") long parentId);
    @GET("sugar/child/{id}")
    Call<List<SugarLevelDTO>> getSugarHistory(@Path("id") long childId);
    @POST("sugar/add")
    Call<SugarLevelDTO> addSugar(@Body SugarLevelDTO sugar);
    @POST("parent/register")
    Call<ParentDTO> registerParent(@Body ParentDTO parent);
    @POST("child/add")
    Call<ChildDTO> addChild(@Body ChildDTO child);
    @POST("child/register")
    Call<ChildDTO> registerChild(@Body ChildDTO child);
    @POST("parent/login")
    Call<ParentDTO> loginParent(@Body ParentDTO parent);
    @POST("child/login")
    Call<ChildDTO> loginChild(@Body ChildDTO child);
    @POST("child/{id}/generate-code")
    Call<String> generateConnectionCode(@Path("id") Long childId);
    @POST("parent/{parentId}/connect")
    Call<ChildDTO> linkChildByCode(@Path("parentId") long parentId, @Query("code") String code);
    @PUT("/api/child/{id}")
    Call<ChildDTO> updateChild(@Path("id") Long id, @Body ChildDTO dto);
    @GET("/api/parent/{parentId}/children")
    Call<List<Child>> getMyChildren(@Path("parentId") Long parentId);
    @PUT("/api/parent/{id}")
    Call<ParentDTO> updateParent(@Path("id") Long id, @Body ParentDTO dto);
    @GET("child/{id}")
    Call<ChildDTO> getChild(@Path("id") long childId);
    @DELETE("/api/parent/{parentId}/disconnect/{childId}")
    Call<Void> disconnectChild(@Path("parentId") Long parentId, @Path("childId") Long childId);
}