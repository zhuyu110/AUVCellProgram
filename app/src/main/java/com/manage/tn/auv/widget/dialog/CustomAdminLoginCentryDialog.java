package com.manage.tn.auv.widget.dialog;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.core.CenterPopupView;
import com.manage.tn.auv.R;
import com.manage.tn.auv.ui.activity.AdminViewActivity;
import com.manage.tn.auv.ui.adapter.KeyBoardNumberAdapter;
import com.manage.tn.auv.widget.edittext.AutoCheckEditText;

import java.util.Arrays;

public class CustomAdminLoginCentryDialog extends CenterPopupView {
    Integer[] date=new Integer[]{0,1,2,3,4,5,6,7,8,9,10,11};
    RecyclerView recyclerView;
    KeyBoardNumberAdapter adapter;
    AutoCheckEditText login_password;
    public CustomAdminLoginCentryDialog(@NonNull Context context) {
        super(context);
    }


    @Override
    protected int getImplLayoutId() {
        return R.layout.admin_login_view;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ImageView close_btn=findViewById(R.id.close_btn);
         recyclerView=findViewById(R.id.number_rcy);
         login_password =findViewById(R.id.login_password);

        GridLayoutManager manager = new GridLayoutManager(getContext(), 3);
        adapter=new KeyBoardNumberAdapter(getContext());
        adapter.setDatas(Arrays.asList(date));
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        close_btn.setOnClickListener(view -> {
            dismiss();
        });
        LiveEventBus.get("Number",Integer.class).observe((LifecycleOwner) getContext(),(response->{
            String text=login_password.getText().toString();
            if(response==9){
                if(TextUtils.isEmpty(text)){
                    return;
                }else {
                    text=text.substring(0,text.length()-1);
                }
            }else if(response==10){
                text+="0";
            }else  if(response==11){
                if(text.equals("123")){
                    dismiss();
                    AdminViewActivity.start(getContext());
                }else {
                    ToastUtils.showShort("Password error！");
                }
            }else {
                text+=(response+1)+"" ;
            }
            login_password.setText(text);
            login_password.setSelection(text.length());
        }));


    }

    @Override
    protected void onShow() {
        super.onShow();
        login_password.setText("");
    }


    @Override
    protected void onDismiss() {
        super.onDismiss();

    }

}
