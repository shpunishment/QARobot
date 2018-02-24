package com.shpun.qarobot.Util;

import com.google.gson.Gson;
import com.shpun.qarobot.gson.RobotText;
import com.shpun.qarobot.log.LogUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.MediaType;

/**
 * Created by shpun on 2018/2/24.
 */

public class Utility {

    private static final String TAG = "Utility";

    /**
     * {
     “key”: “APIKEY”,
     “info”: “今天天气怎么样”
     “loc”：“北京市中关村”，
     “userid”：“123456”
     }
     */

    public static String createRequestJSON(String info,String userid){
        try{
            JSONObject requestJSON=new JSONObject();
            requestJSON.put("key","a6c6f854abbe41deb95ff9d1ebf56475");
            requestJSON.put("info",info);
            requestJSON.put("userid",userid);

            //LogUtil.d(TAG,"createRequestJSON requestJson : "+requestJSON.toString());

            return requestJSON.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * {
     "code":100000,
     "text":"你也好 嘻嘻"
     }
     */

    public static RobotText handleResponseJSON(String response){
        try{
            /*
            JSONObject jsonObject=new JSONObject(response);

            String code=jsonObject.getString("code");
            String text=jsonObject.getString("text");
            //LogUtil.d(TAG,"handleResponseJSON code : "+code);
            //LogUtil.d(TAG,"handleResponseJSON text : "+text);
            */

            return new Gson().fromJson(response,RobotText.class);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
