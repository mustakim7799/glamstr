package glamster.mustakim.com.glamster;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {


    @FormUrlEncoded
    @POST("user_registration.php")
    Call<UserModelCLass> performRegistration(@Query("name") String Name, @Query("username") String UserName,
                                             @Query("email") String Email, @Query("password") String Password,
                                             @Query("uEligibility") String Eligibility, @Query("uAge") String Age,
                                             @Query("uGender") String Gender, @Query("uCity") String City,
                                             @Query("uHeight") String Height, @Query("uEducation") String Education,
                                             @Query("uSkin") String Skin, @Query("uEyes") String Eyes,
                                             @Query("uMaritialStatus") String Maritial_status,
                                             @Query("uLanguages") String Languages, @Query("uTraining") String Training,
                                             @Query("uPassport") String Passport, @Query("uFigure") String Figure,
                                             @Query("uBiography") String Biography,@Field("uPhoto") String Profile_Image);

    @GET("user_login.php")
    Call<UserModelCLass> performLogin(@Query("username") String Name, @Query("password") String Password);


    @GET("user_details.php")
    Call<UserModelCLass> getUserInformation(@Query("username") String Username);

    //@Query("uPhoto") String Profile_Image
}
