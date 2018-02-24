package com.shpun.qarobot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shpun.qarobot.Util.HttpUtil;
import com.shpun.qarobot.Util.Utility;
import com.shpun.qarobot.gson.RobotText;
import com.shpun.qarobot.log.LogUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private List<Msg> msgList=new ArrayList<>();
    private EditText inputText;
    private Button send;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=(Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        inputText=(EditText)findViewById(R.id.edit_text);
        send=(Button)findViewById(R.id.send_text);
        msgRecyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(manager);
        adapter=new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content=inputText.getText().toString();
                if(!"".equals(content)){
                    Msg msg=new Msg(content,Msg.TYPE_SEND,getTime(),null,System.currentTimeMillis());
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size()-1);
                    msgRecyclerView.scrollToPosition(msgList.size()-1);
                    inputText.setText("");

                    requestRobot(content);
                }
            }
        });

        inputText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msgRecyclerView.scrollToPosition(msgList.size()-1);
            }
        });

    }

    /*
    {“key”: “APIKEY”,
    “info”: “今天天气怎么样”，
    “loc”：“北京市中关村”，
     “userid”：“123456”               }
     */

    private void requestRobot(String content){
        String url="http://www.tuling123.com/openapi/api";
        // 创建 json request文件
        RequestBody body=RequestBody.create(JSON, Utility.createRequestJSON(content,"1001"));

        HttpUtil.sendOkHttpRequest(url, body, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"request onFailure",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText=response.body().string();
                final RobotText robotText=Utility.handleResponseJSON(responseText);

                LogUtil.d(TAG,"url : "+robotText.url);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(robotText!=null){
                            Msg msg=new Msg(robotText.text,Msg.TYPE_RECEIVED,getTime(), robotText.url,System.currentTimeMillis());
                            msgList.add(msg);
                            adapter.notifyItemInserted(msgList.size()-1);
                            msgRecyclerView.scrollToPosition(msgList.size()-1);
                        }
                    }
                });

            }
        });

    }

    private String getTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
        String dateString = simpleDateFormat.format(new Date());
        LogUtil.d(TAG,"time: "+dateString);
        return dateString;
    }

}
