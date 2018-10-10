package tapptic.com.numberslight;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;

public class DetailActivity extends AppCompatActivity {
    private OkHttpClient okHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        String title = this.getIntent().getStringExtra("title");

        try {
            JSONObject obj = new JSONObject(getLight(title));

            if (obj.getString(LightsAdapter.FIELD_NAME) != null) {
                ((TextView) findViewById(R.id.detailTxtTitle)).setText(obj.getString(LightsAdapter.FIELD_NAME));
            }
            if (obj.getString(LightsAdapter.FIELD_TEXT) != null) {
                ((TextView) findViewById(R.id.detailTxtText)).setText(obj.getString(LightsAdapter.FIELD_TEXT));
            }

        } catch (JSONException e) {
            Logger.debug(e.getMessage());
        }
    }

    public String getLight(String name) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Request request = new Request.Builder()
                .url(ProjectUrl.LIGHTDETAIL + name)
                .build();

        Logger.debug(ProjectUrl.LIGHTDETAIL + name);

        try {
            ResponseBody body = okHttpClient.newCall(request).execute().body();
            return (body != null) ? body.string() : "";
        } catch (IOException e) {
            Logger.debug(e.getMessage());
            return "";
        }
    }

}
