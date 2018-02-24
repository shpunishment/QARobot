package com.shpun.qarobot;

/**
 * Created by shpun on 2018/2/24.
 */

public class Msg {

    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SEND = 1;

    private String content;
    private int type;
    private String time;
    private long timeMills;

    public Msg(String content,int type,String time,long timeMills){
        this.content=content;
        this.type=type;
        this.time=time;
        this.timeMills=timeMills;
    }


    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }

    public String getTime() {
        return time;
    }

    public long getTimeMills() {
        return timeMills;
    }
}
