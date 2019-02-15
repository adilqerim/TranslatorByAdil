package com.kerimov.adee.transtalorbyadil;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {


    @GET("translate")
    Call<Word> getWord(@Query("lang") String lang,@Query("text") String text, @Query("key") String key);
}
