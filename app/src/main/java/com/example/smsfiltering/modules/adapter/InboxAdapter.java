package com.example.smsfiltering.modules.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smsfiltering.R;
import com.example.smsfiltering.table.SMS;
import java.util.List;

/**
 * Created by yinzhiyu on 2017/12/28.
 */

public class InboxAdapter extends RecyclerView.Adapter {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private IndoxHolder IndoxHolder;
    private List<SMS> list;

    public InboxAdapter(Context context, List<SMS> list) {
        this.list = list;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void notifityData(List<SMS> list) {
        if (list != null && list.size() > 0) {
            this.list.addAll(list);
            notifyDataSetChanged();
        } else {
            Toast.makeText(mContext, "没有更多数据...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public IndoxHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        IndoxHolder holder;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_indox, parent, false);
        holder = new IndoxHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        // 给ViewHolder设置元素
        final SMS sms = list.get(position);
        if (holder instanceof IndoxHolder) {
            IndoxHolder = (IndoxHolder) holder;
            IndoxHolder.tv_sender.setText(sms.getSender());
            IndoxHolder.tv_content.setText(sms.getContent());
            IndoxHolder.tv_time.setText(sms.getTime());
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    static class IndoxHolder extends RecyclerView.ViewHolder {
        TextView tv_sender;
        TextView tv_content;
        TextView tv_time;

        IndoxHolder(View view) {
            super(view);
            tv_sender = (TextView) view.findViewById(R.id.tv_sender);
            tv_content = (TextView) view.findViewById(R.id.tv_content);
            tv_time = (TextView) view.findViewById(R.id.tv_time);

        }
    }

}