package net.iqbalfauzan.agribid.data;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {

    @GET("/user/getUsers")
    Call<ResponseBody> getUsers(@Header("Authorization") String auth);

    @GET("/user/getUsers/{id_user}")
    Call<ResponseBody> getUserDetail(@Header("Authorization") String auth,
                                     @Path("id_user") String idUser);

    @GET("/lahan")
    Call<ResponseBody> getLahan(@Header("Authorization") String auth);

    @GET("/invest")
    Call<ResponseBody> getInvest(@Header("Authorization") String auth);

    @GET("/invest/lahan/{id_lahan}")
    Call<ResponseBody> getDetailInvest(@Path("id_user") String id_user);

    @GET("/lelang/lahan/{lahan_id}")
    Call<ResponseBody> getDetailLelang(@Path("id_user") String id_user);

    @GET("/lelang")
    Call<ResponseBody> getLelang(@Header("Authorization") String auth);

    @FormUrlEncoded
    @POST("/user/storeUser")
    Call<ResponseBody> storeUser(@Header("Content-Type") String content_type,
                              @Field("email") String email,
                              @Field("nama") String nama,
                              @Field("noTelp") String noTelp);
}
