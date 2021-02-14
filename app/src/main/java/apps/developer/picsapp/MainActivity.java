package apps.developer.picsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import apps.developer.picsapp.adapter.PicsAdapter;
import apps.developer.picsapp.api.ApiClient;
import apps.developer.picsapp.api.RetrofitClient;
import apps.developer.picsapp.model.PicsDataList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recycler_view;
    private ApiClient apiClient;
    ArrayList<PicsDataList> picsDataLists =new ArrayList<>();
    private PicsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler_view=(RecyclerView)findViewById(R.id.recycler_view);
        apiClient= RetrofitClient.getClient().create(ApiClient.class);
        getPicsList();

    }

    private void getPicsList() {

        apiClient.getDataList().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());
                        try {
                            JSONArray jsonArray =new JSONArray(response.body().toString());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                PicsDataList data = new PicsDataList(
                                        object.getString("id"),
                                        object.getString("author"));
                                picsDataLists.add(data);
                            }
                            bindData(picsDataLists);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this, ""+t.toString(), Toast.LENGTH_SHORT).show();
                Log.d("TAG","Response = "+t.toString());
            }
        });

    }

    private void bindData(ArrayList<PicsDataList> picsDataLists) {
        recycler_view.setLayoutManager(new GridLayoutManager(this, 2));
        adapter=new PicsAdapter(MainActivity.this, picsDataLists);
        recycler_view.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}