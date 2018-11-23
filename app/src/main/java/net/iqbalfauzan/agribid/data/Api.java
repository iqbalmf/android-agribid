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

    @FormUrlEncoded
    @POST("/user/signUp")
    Call<ResponseBody> signUp(@Header("Content-Type") String content_type,
                              @Field("email") String email,
                              @Field("nama") String nama,
                              @Field("noTelp") String noTelp);

    @FormUrlEncoded
    @POST("/user/signIn")
    Call<ResponseBody> signIn(@Header("Content-Type") String content_type,
                              @Field("email") String email,
                              @Field("password") String password);
}
