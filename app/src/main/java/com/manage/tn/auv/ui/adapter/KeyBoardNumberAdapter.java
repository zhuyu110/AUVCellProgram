package com.manage.tn.auv.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.danmo.commonutil.recyclerview.adapter.base.RecyclerViewHolder;
import com.danmo.commonutil.recyclerview.adapter.singletype.SingleTypeAdapter;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.manage.tn.auv.R;


public class KeyBoardNumberAdapter extends SingleTypeAdapter<Integer> {
    public KeyBoardNumberAdapter(@NonNull Context context) {
        super(context, R.layout.number_view);
    }



    @Override
    public void convert(int position, RecyclerViewHolder holder, Integer bean) {
        TextView view = holder.get(R.id.item_number);
        if(bean==9){
            view.setText("Delete");
        }else if(bean==10){
            view.setText("0");
        }else  if(bean==11){
            view.setText("Confirm");
            view.setTextColor(Color.WHITE);
            ((LinearLayout)holder.get(R.id.roView)).setBackgroundColor(mContext.getResources().getColor(R.color.blue));
        }else {
            view.setText((bean+1)+"");
        }

        holder.getRootView().setOnClickListener(v->{
            LiveEventBus.get("Number",int.class).post(bean);
        });

    }

}
