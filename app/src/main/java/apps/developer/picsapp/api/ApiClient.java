package apps.developer.picsapp.api;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiClient {

   @GET("list")
   Call<String> getDataList();
}
