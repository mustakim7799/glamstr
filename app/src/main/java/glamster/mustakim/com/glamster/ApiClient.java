package glamster.mustakim.com.glamster;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient  {

    public static final String BASE_URL = "http://192.168.0.100/glamstr/";
    public static Retrofit retrofit = null;

    public static Retrofit getApiCLient(){

        if(retrofit == null)
        {
            return retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        }

        return retrofit;
    }

}
