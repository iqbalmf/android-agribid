package net.iqbalfauzan.agribid.data;

public class AgriBidClientBuilder {
    public static final String BASE_URL_API = "https://agribid.herokuapp.com/";

    // Mendeklarasikan Interface BaseApiService
    public static Api getAPIService(){
        return AgriBidClient.getClient(BASE_URL_API).create(Api.class);
    }
}
