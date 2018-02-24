package com.shpun.qarobot;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;


/**
 * Created by shpun on 2018/2/24.
 */

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {

    private static final String TAG = "MsgAdapter";

    private List<Msg> mMsgList;

    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout leftLayout;
        RelativeLayout rightLayout;
        TextView leftText;
        TextView rightText;
        TextView time;

        public ViewHolder(View view){
            super(view);

            leftLayout=(RelativeLayout)view.findViewById(R.id.left_layout);
            rightLayout=(RelativeLayout)view.findViewById(R.id.right_layout);
            leftText=(TextView)view.findViewById(R.id.left_text);
            rightText=(TextView)view.findViewById(R.id.right_text);
            time=(TextView)view.findViewById(R.id.time);
        }
    }

    public MsgAdapter(List<Msg> msgList){
        mMsgList=msgList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Msg msg=mMsgList.get(position);
        if(msg.getType()==Msg.TYPE_RECEIVED){
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftText.setText(msg.getContent());

            holder.time.setVisibility(View.GONE);
        }else if(msg.getType()==Msg.TYPE_SEND){
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.rightText.setText(msg.getContent());

            holder.time.setText(msg.getTime());
            holder.time.setVisibility(View.VISIBLE);
        }

        if(msg.getImageUrl()!=null){
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftText.setText(msg.getImageUrl());

            holder.leftText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(mContext,WebActivity.class);
                    intent.putExtra("url",msg.getImageUrl());
                    mContext.startActivity(intent);
                }
            });



        }

    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }

}
