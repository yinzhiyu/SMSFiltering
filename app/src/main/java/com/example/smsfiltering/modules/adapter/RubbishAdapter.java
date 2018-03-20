package com.example.smsfiltering.modules.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.smsfiltering.R;
import com.example.smsfiltering.base.BaseApplication;
import com.example.smsfiltering.greendao.SMSDao;
import com.example.smsfiltering.modules.fragment.RubbishBoxFragment;
import com.example.smsfiltering.table.SMS;

import java.util.List;


public class RubbishAdapter extends RecyclerView.Adapter {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private IndoxHolder IndoxHolder;
    private List<SMS> list;
    private OnListener mOnListener;
    public RubbishAdapter(Context context, List<SMS> list) {
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
    //    给接口变量赋值
    public void setCallback(OnListener callBack) {
        this.mOnListener = callBack;
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
            IndoxHolder.tv_sender.setText("发件人：" + sms.getSender());
            IndoxHolder.tv_content.setText(sms.getContent());
            IndoxHolder.tv_time.setText("时间：" + sms.getTime());
            IndoxHolder.trash_can.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new MaterialDialog.Builder(mContext)
                            .content("确认删除？")
                            .positiveText("再看看")
                            .negativeText("确定")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                }
                            })
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    SMSDao smsDao = BaseApplication.getInstance().getDaoSession().getSMSDao();
                                    smsDao.delete(sms);
                                    mOnListener.onRListener();

                                }
                            })
                            .show();
                }
            });
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
        ImageView trash_can;

        IndoxHolder(View view) {
            super(view);
            tv_sender = (TextView) view.findViewById(R.id.tv_sender);
            tv_content = (TextView) view.findViewById(R.id.tv_content);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            trash_can = (ImageView) view.findViewById(R.id.trash_can);

        }
    }
}