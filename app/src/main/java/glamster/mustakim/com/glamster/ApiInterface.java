package glamster.mustakim.com.glamster;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("user_registration.php")
    Call<UserModelCLass> performRegistration(@Query("name") String Name, @Query("username") String UserName, @Query("email") String Email, @Query("password") String Password);

    @GET("user_login.php")
    Call<UserModelCLass> performLogin(@Query("username") String Name, @Query("password") String Password);
}
