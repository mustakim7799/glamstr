package glamster.mustakim.com.glamster;

import com.google.gson.annotations.SerializedName;

public class UserModelCLass {

    @SerializedName("response")
    private String Response;

    @SerializedName("name")
    private String Name;

    public String getResponse() {
        return Response;
    }

    public String getName() {
        return Name;
    }
}
