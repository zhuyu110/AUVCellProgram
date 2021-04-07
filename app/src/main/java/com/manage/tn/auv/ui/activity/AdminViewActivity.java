package com.manage.tn.auv.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.danmo.commonapi.AppDatabase;
import com.danmo.commonapi.db.entity.AUVBoardCellDevice;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.XPopup;
import com.manage.tn.auv.MyApplication;
import com.manage.tn.auv.R;
import com.manage.tn.auv.base.BaseActivity;
import com.manage.tn.auv.base.BaseViewPagerAdapter;
import com.manage.tn.auv.databinding.ActivityAdminViewBinding;
import com.manage.tn.auv.ui.fragment.AUVCellGrildFragment;
import com.manage.tn.auv.util.AUVCellController;
import com.manage.tn.auv.util.Config;
import com.manage.tn.auv.util.LockControlBoardUtils;
import com.manage.tn.auv.util.ScanGunKeyEventUtils;
import com.manage.tn.auv.widget.dialog.CustomSetingCentryDialog;
import com.manage.tn.auv.widget.dialog.CustomTimeSetCentryDialog;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdminViewActivity extends BaseActivity<ActivityAdminViewBinding> {
    SegmentTabLayout tabhost;
    public ViewPager mBaseViewPager;//用于显示不同nav下的内容
    public BaseViewPagerAdapter mAdapter;
    int uitype=0;//1：51一组  2：51+24 3：21一组 4：51+54+8
    List<AUVBoardCellDevice> deviceList;
    List<AUVBoardCellDevice>  mDateList;
    List<AUVBoardCellDevice>  mDateList2;
    List<AUVBoardCellDevice>  mDateList3;
    CustomTimeSetCentryDialog dialog;
    List<AUVBoardCellDevice> selDevices;
    CustomSetingCentryDialog setingCentryDialog;
    int indexTab=100;
    @Override
    public void initData(@Nullable Bundle bundle) {
        initDevice();
    }

    public  void initDevice(){
        deviceList = AppDatabase.getInstance(getApplication()).getAllDevice();
        if(deviceList==null){
            return;
        }
        if( deviceList.size()==51){
            uitype=1;
        }else  if(deviceList.size()==75){
            uitype=2;
        }
        else  if(deviceList.size()==21){
            uitype=3;
        }else  if(deviceList.size()==113){
            uitype=4;
        }
        mDateList=new ArrayList<>();
        if(uitype==1){
            for (int i=1;i<52;i++){
                mDateList.add(deviceList.get(i-1));
            }
        }else if(uitype==2){
            mDateList2=new ArrayList<>();
            for (int i=1;i<52;i++){
                mDateList.add(deviceList.get(i-1));
            }
            for(int i=52;i<76;i++){
                mDateList2.add(deviceList.get(i-1));
            }
        }else if(uitype==3){
            for (int i=1;i<22;i++){
                mDateList.add(deviceList.get(i-1));
            }
        }else  if(uitype==4){
            mDateList2=new ArrayList<>();
            mDateList3=new ArrayList<>();
            for (int i=1;i<52;i++){
                mDateList.add(deviceList.get(i-1));
            }
            for (int i=52;i<106;i++){
                mDateList2.add(deviceList.get(i-1));
            }
            for (int i=106;i<114;i++){
                mDateList3.add(deviceList.get(i-1));
            }
        }
    }
    public static void start(Context context) {
        Intent intent = new Intent(context, AdminViewActivity.class);
        context.startActivity(intent);
    }
    @Override
    public void initView(@Nullable Bundle savedInstanceState, ActivityAdminViewBinding childDb, @Nullable ViewDataBinding rootDb) {
        tabhost=childDb.tabhost;
        mBaseViewPager=childDb.viewPager;
        ViewGroup.LayoutParams layoutParams = tabhost.getLayoutParams();
        if(uitype==1||uitype==3){
            tabhost.setTabData(Config.tab1);
            layoutParams.width= SizeUtils.dp2px(231);
        }else if(uitype==2){
            tabhost.setTabData(Config.tab2);
            layoutParams.width= SizeUtils.dp2px(462);
        }else if(uitype==4){
            tabhost.setTabData(Config.tab3);
            layoutParams.width= SizeUtils.dp2px(693);
        }
        tabhost.setLayoutParams(layoutParams);
        mAdapter=new BaseViewPagerAdapter(mActivity,getSupportFragmentManager(),getPages());
        mBaseViewPager.setAdapter(mAdapter);
        mBaseViewPager.setCurrentItem(0,true);
        tabhost.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mBaseViewPager.setCurrentItem(position,true);
                if(indexTab==position){
                    childDb.selAllBtn.setChecked(true);
                }else {
                    childDb.selAllBtn.setChecked(false);
                }

            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mBaseViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabhost.setCurrentTab(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        childDb.icTimeSet.setOnClickListener(v->{
            if (dialog == null) {
                dialog = (CustomTimeSetCentryDialog) new XPopup.Builder(this)
                        .asCustom(new CustomTimeSetCentryDialog(this))
                        .show();
            } else if (!dialog.isShow()) {
                dialog.show();
            }


        });
        childDb.backBtn.setOnClickListener(v->finish());
        childDb.selAllBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    indexTab=tabhost.getCurrentTab();
                }else {
                    if(indexTab==tabhost.getCurrentTab()){
                        indexTab=100;
                        selDevices=new ArrayList<>();
                    }
                }
                    switch (tabhost.getCurrentTab()){
                        case 0:
                            if(b){
                                selDevices=mDateList;
                            }
                            for (AUVBoardCellDevice one:mDateList){
                                one.setSel(b);
                            }

                            break;
                        case 1:
                            if(b){
                                selDevices=mDateList2;
                            }
                            for (AUVBoardCellDevice one:mDateList2){
                                one.setSel(b);
                            }
                            break;
                        case 2:
                            if(b){
                                selDevices=mDateList3;
                            }
                            for (AUVBoardCellDevice one:mDateList3){
                                one.setSel(b);
                            }
                            break;
                }
                LiveEventBus.get("RefreshUI",String.class).post("RefreshUI");

            }
        });
        childDb.openBtn.setOnClickListener(v->{

            if(selDevices!=null&&selDevices.size()>0){

                ThreadUtils.getFixedPool(3).execute(new Runnable() {
                    @Override
                    public void run() {
                        MyApplication.statue=0;
                        for (AUVBoardCellDevice d:selDevices){
                            if(MyApplication.isUSB){
                                AUVCellController.getInstance(mActivity).openDoor(d.getCellNo());
                            }else {
                                LockControlBoardUtils.getInstances().openDoor(d.getCellNo());
                            }
                        }
                    }
                });
                //TODO 可控制



            }else {
                ToastUtils.showShort("NO Item Select,Please select First!");
            }
        });

        LiveEventBus.get("SelDevice",AUVBoardCellDevice.class).observe(this,result->{
            if(selDevices==null){
                selDevices=new ArrayList<>();
            }
            if(selDevices.indexOf(result)<0){
                if(result.isSel()){
                    selDevices.add(result);
                }

            }else {
                if(result.isSel()){
                    selDevices.add(result);
                }else {
                    selDevices.remove(result);
                }
            }
        });
        childDb.setBtn.setOnClickListener(v->{
            if (setingCentryDialog == null) {
                setingCentryDialog = (CustomSetingCentryDialog) new XPopup.Builder(this)
                        .asCustom(new CustomSetingCentryDialog(this))
                        .show();
            } else if (!setingCentryDialog.isShow()) {
                setingCentryDialog.show();
            }
        });
        LiveEventBus.get("refreshData",String.class).observe(this,result->{
            showLoading();
            deviceList=new ArrayList<>();
            for(int i=0;i<MyApplication.cell_count;i++){
                AUVBoardCellDevice one=new AUVBoardCellDevice();
                one.setCellNo(i+1);
                one.setIsGoodOrFail(true);
                if(i==52){
                    one.setCellGoodsExist(true);
                }
                deviceList.add(one);
            }
            ThreadUtils.getFixedPool(3).execute(new Runnable() {
                @Override
                public void run() {
                    AppDatabase.getInstance(getApplication()).deleteAllDevice();
                    for (AUVBoardCellDevice device:deviceList){
                        AppDatabase.getInstance(getApplication()).inser(device);
                    }
                    if(MyApplication.isUSB){
                        AUVCellController.getInstance(mActivity).getAllDoorStatus(deviceList);
                    }else {
                        LockControlBoardUtils.getInstances().getAllDoorStatus(deviceList);//获取设备状态
                    }
                    dismissLoading();
                    finish();

                }
            });


        });


    }

    private List<BaseViewPagerAdapter.PagerInfo> getPages(){
        List<BaseViewPagerAdapter.PagerInfo> listPages=new ArrayList<>();
        Bundle bundle1=new Bundle();
        Bundle bundle2=new Bundle();
        Bundle bundle3=new Bundle();
        List<String> list=new ArrayList<>();
        if(uitype==1||uitype==3){
            list = Arrays.asList(Config.tab1);
            bundle1.putSerializable("devices", (Serializable) mDateList);
            bundle1.putString("type","#A");
        }else if(uitype==2){
            bundle1.putSerializable("devices", (Serializable) mDateList);
            bundle1.putString("type","#A");
            bundle2.putSerializable("devices", (Serializable) mDateList2);
            bundle2.putString("type","#B");
            list = Arrays.asList(Config.tab2);
        }else if(uitype==4){
            bundle1.putSerializable("devices", (Serializable) mDateList);
            bundle1.putString("type","#A");
            bundle2.putSerializable("devices", (Serializable) mDateList2);
            bundle2.putString("type","#B");
            bundle3.putSerializable("devices", (Serializable) mDateList3);
            bundle3.putString("type","#C");
            list = Arrays.asList(Config.tab3);
        }
        for(int i=0;i<list.size();i++) {
            switch (list.get(i)) {
                case "Cabinet #A":
                    listPages.add(new BaseViewPagerAdapter.PagerInfo(list.get(i),AUVCellGrildFragment.class,bundle1));
                    break;
                case "Cabinet #B":
                    listPages.add(new BaseViewPagerAdapter.PagerInfo(list.get(i),AUVCellGrildFragment.class,bundle2));
                    break;
                case "Cabinet #C":
                    listPages.add(new BaseViewPagerAdapter.PagerInfo(list.get(i),AUVCellGrildFragment.class,bundle3));
                    break;
                default:
                    break;
            }
        }
        return listPages;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(MyApplication.isUSB){
            AUVCellController.getInstance(mActivity).onPause();
        }else {
            LockControlBoardUtils.getInstances().closeSerialPort();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(MyApplication.isUSB){
            AUVCellController.getInstance(mActivity).initUsbControl();
            AUVCellController.getInstance(mActivity).onDeviceStateChange();
        }else {
            if(!StringUtils.isEmpty(MyApplication.PORT_NAME)){
                LockControlBoardUtils.getInstances().onDeviceStateChange();
            }
            LockControlBoardUtils.getInstances().onDeviceStateChange();
        }


    }

    @Override
    public int bindLayout() {
        return R.layout.activity_admin_view;
    }
}
