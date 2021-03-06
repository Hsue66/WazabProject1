package com.ourincheon.wazap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ourincheon.wazap.Retrofit.regUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Hsue.
 */
public class showMypageActivity extends AppCompatActivity {

    ImageView profileImg;
    String thumbnail;
    regUser reguser;
    private TextView sName, sMajor, sUniv, sLoc, sKakao, sIntro, sExp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_mypage);

     //   RetrofitService retroService = new RetrofitService();
     //   retroService.loadPage();

        sName = (TextView) findViewById(R.id.sName);
        sMajor = (TextView)  findViewById(R.id.sMajor);
        sUniv = (TextView)  findViewById(R.id.sUniv);
        sLoc = (TextView)  findViewById(R.id.sLoc);
        sKakao = (TextView)  findViewById(R.id.sKakao);
        sIntro = (TextView) findViewById(R.id.sIntro);
        sExp = (TextView) findViewById(R.id.sExp);

        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        profileImg = (ImageView)findViewById(R.id.sPro);
        thumbnail = pref.getString("profile_img","");
        ThumbnailImage thumb = new ThumbnailImage(thumbnail, profileImg);
        thumb.execute();

        loadPage();
        Intent intent = getIntent();

    }


    void loadPage()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://come.n.get.us.to/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WazapService service = retrofit.create(WazapService.class);

        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        String user_id = pref.getString("user_id", "");
        Log.d("SUCCESS", user_id );

        Call<regUser> call = service.getUserInfo(user_id);
        call.enqueue(new Callback<regUser>() {
            @Override
            public void onResponse( Response<regUser> response) {
                if (response.isSuccess() && response.body() != null) {

                    Log.d("SUCCESS", response.message());
                    reguser = response.body();

                    //user = response.body();
                    //Log.d("SUCCESS", reguser.getMsg());

                    String result = new Gson().toJson(reguser);
                    Log.d("SUCESS-----",result);

                    JSONObject jsonRes;
                    try{
                        jsonRes = new JSONObject(result);
                        JSONArray jsonArr = jsonRes.getJSONArray("data");
                        Log.d("username",jsonArr.getJSONObject(0).getString("username"));
                        sName.setText(jsonArr.getJSONObject(0).getString("username"));
                        sMajor.setText(jsonArr.getJSONObject(0).getString("major"));
                        sUniv.setText(jsonArr.getJSONObject(0).getString("school"));
                        sLoc.setText(jsonArr.getJSONObject(0).getString("locate"));
                        sKakao.setText(jsonArr.getJSONObject(0).getString("kakao_id"));
                        sIntro.setText(jsonArr.getJSONObject(0).getString("introduce"));
                        sExp.setText(jsonArr.getJSONObject(0).getString("exp"));
                    }catch (JSONException e)
                    {};

                } else if (response.isSuccess()) {
                    Log.d("Response Body isNull", response.message());
                } else {
                    Log.d("Response Error Body", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure( Throwable t) {
                t.printStackTrace();
                Log.e("Errorglg''';kl", t.getMessage());
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_mypage, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {
            Intent i = new Intent(showMypageActivity.this, MypageActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
