package tapptic.com.numberslight;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SimpleAdapter;
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

public class MainActivity extends AppCompatActivity  implements LightsAdapter.ItemClickListener
{
    private LightsAdapter mAdapter;

    private OkHttpClient okHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.main_recycler);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        ArrayList<HashMap<String, Object>> lights = new ArrayList<HashMap<String, Object>>();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        try {
            JSONArray obj = new JSONArray(getLights());

            String strMessage = "";
            for (int i=0; i<obj.length(); i++) {
                JSONObject message = obj.getJSONObject(i);
                strMessage += "-" + message.getString("name") + "/" + message.getString("image");
                Logger.debug(strMessage);

                HashMap<String, Object> mapping = new HashMap<String, Object>();
                mapping.put("title" , "pie");
                mapping.put("image", "image");
                lights.add(mapping);
            }


        } catch (JSONException e) {

        }

        mAdapter = new LightsAdapter(this, lights);
        mAdapter.setClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onItemClick(View view, int position) {
        Logger.debug(mAdapter.getItem(position));
    }

    public String getLights()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Request request = new Request.Builder()
                .url(ProjectUrl.LIGHTSLIST)
                .build();

        try {
            ResponseBody body = okHttpClient.newCall(request).execute().body();
            return (body != null) ? body.toString() : "";
        } catch (IOException e) {
            return "";
        }
    }
}
