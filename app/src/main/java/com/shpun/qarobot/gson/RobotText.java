package com.shpun.qarobot.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by shpun on 2018/2/24.
 */

public class RobotText {

    public String code;
    public String text;
    public String url; // 图片

    @SerializedName("list")
    public List<newsList> newsLists;

}
