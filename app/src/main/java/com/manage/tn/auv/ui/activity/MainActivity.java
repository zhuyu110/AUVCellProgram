package com.manage.tn.auv.ui.activity;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.ViewDataBinding;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.android_serialport_api.DataUtils;
import com.blankj.utilcode.constant.TimeConstants;
import com.blankj.utilcode.util.*;
import com.danmo.commonapi.AppDatabase;
import com.danmo.commonapi.base.Constant;
import com.danmo.commonapi.db.entity.AUVBoardCellDevice;
import com.danmo.commonapi.db.entity.OperationRecord;
import com.danmo.commonapi.db.entity.User;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lxj.xpopup.XPopup;
import com.manage.tn.auv.MyApplication;
import com.manage.tn.auv.R;
import com.manage.tn.auv.base.BaseActivity;
import com.manage.tn.auv.base.ViewModelFactory;
import com.manage.tn.auv.databinding.ActivityHomeBinding;
import com.manage.tn.auv.ui.adapter.AUVBoardCellDevAdapter;
import com.manage.tn.auv.ui.adapter.AUVBoardCellFallsDevAdapter;
import com.manage.tn.auv.ui.broadcast.UsbBroadcastReceiver;
import com.manage.tn.auv.ui.service.LongRunningService;
import com.manage.tn.auv.util.AUVCellController;
import com.manage.tn.auv.util.Bcc16Util;
import com.manage.tn.auv.util.LockControlBoardUtils;
import com.manage.tn.auv.util.ScanGunKeyEventUtils;
import com.manage.tn.auv.viewModel.UpdateToServerViewModel;
import com.manage.tn.auv.viewModel.UserLoginViewModel;
import com.manage.tn.auv.widget.dialog.CustomAdminLoginCentryDialog;
import com.manage.tn.auv.widget.dialog.CustomAlarmLeaderCentryDialog;
import com.manage.tn.auv.widget.dialog.CustomBorrowCentryDialog;
import com.manage.tn.auv.widget.dialog.CustomGoodOrFailCentryDialog;
import com.manage.tn.auv.widget.dialog.CustomHaveReturnAlarmDialog;
import com.manage.tn.auv.widget.dialog.CustomLeaderReturnMainDialog;
import com.manage.tn.auv.widget.dialog.CustomReturnAlarmCentryDialog;
import com.manage.tn.auv.widget.dialog.CustomShowMessageCentryDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.manage.tn.auv.util.Config.getUserList;
import static com.manage.tn.auv.util.Config.login;
import static com.manage.tn.auv.util.Config.uploadBins;
import static com.manage.tn.auv.util.Config.uploadLockers;
import static com.manage.tn.auv.util.Config.uploadOperators;


public class MainActivity extends BaseActivity<ActivityHomeBinding> {
    public static final String TAG=MainActivity.class.getSimpleName();
    RecyclerView cellNo1;
    RecyclerView cellNo2;
    RecyclerView cellNo3;
    List<AUVBoardCellDevice> deviceList;
    List<AUVBoardCellDevice>  mDateList;
    List<AUVBoardCellDevice>  mDateList2;
    List<AUVBoardCellDevice>  mDateList3;
    List<AUVBoardCellDevice> userdevices;
    List<OperationRecord> operators;
    AUVBoardCellDevAdapter devAdapter;
    AUVBoardCellDevAdapter devAdapter2;
    AUVBoardCellFallsDevAdapter devAdapter3;//瀑布流adpter
    List<User> userList;
    CustomAdminLoginCentryDialog adminLoginCentryDialog;//管理员登录
    CustomReturnAlarmCentryDialog returnDialog;// 归还相关弹框
    int uitype=0;//1：51一组  2：51+24 3：21一组 4：51+54+8
    String beginTime;//服务时间
    String endTime;//结束时间
    int lockerHour=0;
    CustomBorrowCentryDialog  borrowCentryDialog;
    CustomAlarmLeaderCentryDialog leaderCentryDialog;
    CustomLeaderReturnMainDialog  returnMainDialog;
    private UsbBroadcastReceiver mUsbReceiver;
    CustomShowMessageCentryDialog  showMessageCentryDialog;
    CustomHaveReturnAlarmDialog customHaveReturnAlarmDialog;
    CustomGoodOrFailCentryDialog  customGoodOrFailCentryDialog;
    public ScanGunKeyEventUtils scanGunKeyEventUtils;
    private UserLoginViewModel userLoginViewModel;
    private UpdateToServerViewModel updateToServerViewModel;
    @Override
    public boolean isSwipeBack() {
        return false;
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return scanGunKeyEventUtils.analysisKeyEvent(event);
    }
    @Override
    public void initData(@Nullable Bundle bundle) {
        super.initData(bundle);
        if(scanGunKeyEventUtils==null){
            scanGunKeyEventUtils=new ScanGunKeyEventUtils();
        }

        //initUser(null);

    }
    /*
        初始化设备
     */
    private void initDevice() {
        deviceList=AppDatabase.getInstance(getApplication()).getAllDevice();
        MyApplication.cell_count=SPUtils.getInstance().getInt("cellCount", 0);
        MyApplication.getStrSecondaryCount=SPUtils.getInstance().getString("StrSecondaryCount","0");
        mDateList=new ArrayList<>();
        if( MyApplication.cell_count==51){
            uitype=1;
        }else  if(MyApplication.cell_count==75){
            uitype=2;
        }
        else  if(MyApplication.cell_count==21){
            uitype=3;
        }else  if(MyApplication.cell_count==113){
            uitype=4;
        }else {
            showMessageDialog("NO Item Plaease to setting ");
            return;
        }
        if(!MyApplication.isUSB){
            if(StringUtils.isEmpty(MyApplication.PORT_NAME)){
                showMessageDialog("NO Item Plaease to setting ");
                return;
            }
        }


        if(uitype==1){
            for (int i=1;i<52;i++){
                mDateList.add(deviceList.get(i-1));
            }
            mDateList.add(new AUVBoardCellDevice());
        }else if(uitype==2){
            mDateList2=new ArrayList<>();
            for (int i=1;i<52;i++){
                mDateList.add(deviceList.get(i-1));
            }
            mDateList.add(new AUVBoardCellDevice());
            for(int i=52;i<76;i++){
                mDateList2.add(deviceList.get(i-1));
            }
        }else if(uitype==3){
            for (int i=1;i<22;i++){
                mDateList.add(deviceList.get(i-1));
            }
            mDateList.add(new AUVBoardCellDevice());
        }else  if(uitype==4){
            mDateList2=new ArrayList<>();
            mDateList3=new ArrayList<>();
            for (int i=1;i<52;i++){
                mDateList.add(deviceList.get(i-1));
            }
            mDateList.add(new AUVBoardCellDevice());
            for (int i=52;i<106;i++){
                mDateList2.add(deviceList.get(i-1));
            }
            for (int i=106;i<114;i++){
                mDateList3.add(deviceList.get(i-1));
            }
        }


    }


    /**
     * 初始化用户
     */
    public void initUser(List<User> users){
        userList=new ArrayList<>();
       /* userList.add(new User("04903934","admin","MASTER"));
        userList.add(new User("1024465099","张三","USER"));
        userList.add(new User("1024266784","李四","USER"));
        userList.add(new User("505423226","王五","USER"));
        userList.add(new User("1031355646","king","MASTER"));
        userList.add(new User("1031317966","queen","MASTER"));
        userList.add(new User("1031338043","admin","MASTER"));*/
       userList=users;
        ThreadUtils.getFixedPool(3).execute(new Runnable() {
            @Override
            public void run() {
                for (User user:userList){
                    AppDatabase.getInstance(getApplication()).insertOrReplace(user);
                }
            }
        });
    }
    @Override
    public void initView(@Nullable Bundle savedInstanceState, ActivityHomeBinding childDb, @Nullable ViewDataBinding rootDb) {
        cellNo1=childDb.cellNo1;
        cellNo2=childDb.cellNo2;
        cellNo3=childDb.cellNo3;
        userLoginViewModel = new ViewModelProvider(this, new ViewModelFactory()).get(UserLoginViewModel.class);
        updateToServerViewModel=new ViewModelProvider(this, new ViewModelFactory()).get(UpdateToServerViewModel.class);
        childDb.logoImg.setOnLongClickListener(view -> {
            showAdmain();
            return false;
        });
        LiveEventBus.get("RefreshUI",String.class).observe(this,result->{
            initDevice();
            refreshView();
        });
        LiveEventBus.get("ShowBorrow",String.class).observe(this,result->{
            showBorrow();
        });
        LiveEventBus.get("LeaderReturn",List.class).observe(this,result->{
              showLeaderReturn(result);
        });

        LiveEventBus.get("ReturnGoodOrFail",AUVBoardCellDevice.class).observe(this,result->{
            showCustomGoodOrFailCentryDialog(result);
        });
        LiveEventBus.get("Return",AUVBoardCellDevice.class).observe(this,result->{
            showReturn(result);
        });
        LiveEventBus.get("Update",int.class).observe(this,result->{
            ThreadUtils.getFixedPool(3).execute(new Runnable() {
                @Override
                public void run() {
                    operators = AppDatabase.getInstance(getApplication()).getAllOperation();
                    LiveEventBus.get("RefreshUI",String.class).post("RefreshUI");
                    if(!MyApplication.isFirst){
                        userLoginViewModel.login("2_sprc","97RgHj2dPCENqCwj");
                    }else {
                        updateToServerViewModel.uploadLockers(deviceList);
                        updateToServerViewModel.uploadOperators(operators);
                    }

                }
            });


        });
        LiveEventBus.get("newData",byte[].class).observe(this,result->{
            String s = Bcc16Util.byteTo16String(result).toUpperCase();
            Log.d("MainActivity","收到 16进制数据"+s);
            deviceList=AppDatabase.getInstance(getApplication()).getAllDevice();
            String[] split = s.split(" ");
            if(split.length==5){
                String boardId= split[1];
                String lockId= split[2];
                String statue=split[3];
                int cellNo=0;
                if(!lockId.equals("00")) {
                    if (DataUtils.HexToInt(boardId) == 1) {
                        cellNo = DataUtils.HexToInt(lockId);
                    } else if (DataUtils.HexToInt(boardId) > 1) {
                        cellNo = (DataUtils.HexToInt(boardId) - 2) * Integer.parseInt(MyApplication.strSecondaryCellTotal) + Integer.parseInt(MyApplication.strMainCellTotal) + DataUtils.HexToInt(lockId);
                    }
                }
               switch (split[0]){
                   case "8A"://开锁反馈和锁关反馈
                       if(cellNo!=0){
                           for (AUVBoardCellDevice device:deviceList){
                               if(device.getCellNo()==cellNo){
                                   if(statue.equals("11")){
                                       if(MyApplication.statue==2||MyApplication.statue==1){
                                           LiveEventBus.get("OpenResult",AUVBoardCellDevice.class).post(device);
                                       }
                                       if(MyApplication.statue==1||MyApplication.statue==4){
                                           device.setPerNo(null);
                                           device.setCellGoodsExist(true);
                                           if(MyApplication.statue==1){
                                               device.setBorrowTime("");
                                               device.setReturnTime(TimeUtils.getString(TimeUtils.getNowString(),lockerHour, TimeConstants.HOUR));
                                               ThreadUtils.getFixedPool(3).execute(new Runnable() {
                                                   @Override
                                                   public void run() {
                                                       String userNo = SPUtils.getInstance().getString("userNo", "");
                                                       String userName = SPUtils.getInstance().getString("userName", "");
                                                       AppDatabase.getInstance(getApplication()).insertOperation(new OperationRecord(device.getCellNo(),device.getName(),device.getCellNo(),device.getName(),device.getCellNo(),device.getName(),device.getPartNo(),device.getDeviceNo(),userName,userNo,1,1,1,1,TimeUtils.getNowString()));
                                                   }
                                               });
                                           }
                                       }else if(MyApplication.statue==2){
                                           device.setBorrowTime(TimeUtils.getNowString());
                                           device.setPerNo( SPUtils.getInstance().getString("userNo",null));
                                           device.setCellGoodsExist(false);
                                           ThreadUtils.getFixedPool(3).execute(new Runnable() {
                                               @Override
                                               public void run() {
                                                   String userNo = SPUtils.getInstance().getString("userNo", "");
                                                   String userName = SPUtils.getInstance().getString("userName", "");
                                                   AppDatabase.getInstance(getApplication()).insertOperation(new OperationRecord(device.getCellNo(),device.getName(),device.getCellNo(),device.getName(),device.getCellNo(),device.getName(),device.getPartNo(),device.getDeviceNo(),userName,userNo,-1,0,1,0,TimeUtils.getNowString()));

                                               }
                                           });
                                       }
                                       device.setCellLockOpen(true);
                                       device.setCellLightOpen(true);
                                   }else {
                                       device.setCellLockOpen(false);
                                       device.setCellLightOpen(false);
                                   }

                                   ThreadUtils.getFixedPool(3).execute(new Runnable() {
                                       @Override
                                       public void run() {
                                           AppDatabase.getInstance(getApplication()).inser(device);
                                           userdevices = AppDatabase.getInstance(getApplication()).queryDevice(SPUtils.getInstance().getString("userNo",null));
                                           LiveEventBus.get("RefreshUI",String.class).post("RefreshUI");
                                           LiveEventBus.get("refresh",List.class).post(userdevices);
                                           LiveEventBus.get("RefreshDevice",AUVBoardCellDevice.class).post(device);

                                       }
                                   });

                               }
                           }
                       }
                       break;
                   case "80":
                       if(cellNo!=0){

                           for (AUVBoardCellDevice device:deviceList){
                               if(device.getCellNo()==cellNo){
                                   if(statue.equals("11")){
                                       device.setCellLockOpen(true);
                                       device.setCellLightOpen(true);
                                   }else {
                                       device.setCellLockOpen(false);
                                       device.setCellLightOpen(false);
                                   }

                                   ThreadUtils.getFixedPool(3).execute(new Runnable() {
                                       @Override
                                       public void run() {
                                           AppDatabase.getInstance(getApplication()).inser(device);
                                       }
                                   });
                                   break;
                               }
                           }
                       }
                       break;

               }
            }


        });

        LiveEventBus.get("DeviceType",String.class).observe(this,result->{
            boolean isexit=false;
            for (AUVBoardCellDevice d:deviceList){
                if(!StringUtils.isEmpty(d.getDeviceNo())&&!StringUtils.isEmpty(d.getPartNo())){
                    if(d.getCellGoodsExist()&&d.getPartNo().equals(result)&&d.getIsGoodOrFail()){
                        //判断是否过期
                        if (!TextUtils.isEmpty(d.getEndTime())) {
                            long timeSpanByNow = TimeUtils.getTimeSpanByNow(d.getEndTime() + " 00:00:00", TimeConstants.SEC);
                            if (timeSpanByNow < 0) {
                                isexit=false;
                                continue;
                            }
                        }
                        //判断是否被锁定
                        if(!StringUtils.isEmpty(d.getReturnTime())){
                            long timeSpanByNow = TimeUtils.getTimeSpanByNow(d.getReturnTime() ,TimeConstants.SEC);
                            if(timeSpanByNow<0){
                                MyApplication.statue=2;
                                if (MyApplication.isUSB){
                                    AUVCellController.getInstance(mActivity).openDoor(d.getCellNo());
                                }else {
                                    LockControlBoardUtils.getInstances().openDoor(d.getCellNo());
                                }
                                isexit=true;
                                break;
                            }
                        }else {
                            MyApplication.statue=2;
                            if (MyApplication.isUSB){
                                AUVCellController.getInstance(mActivity).openDoor(d.getCellNo());
                            }else {
                                LockControlBoardUtils.getInstances().openDoor(d.getCellNo());
                            }
                            isexit=true;
                            break;
                        }


                    }
                }

            }

            if(!isexit){
                showMessageDialog("No Item Available \n ไม่มีรายการต้องคืน");
            }

        });
        LiveEventBus.get("OpenResult",AUVBoardCellDevice.class).observe(this,result->{
            showOpenDialog(result);
        });

    }

    /**
     * 刷新页面UI
     */
    public  void refreshView(){
        beginTime = SPUtils.getInstance().getString("beginTime", "");
        endTime = SPUtils.getInstance().getString("endTime", "");
        lockerHour=SPUtils.getInstance().getInt("lockerHour", 0);
        if(uitype==1){
            cellNo1.setVisibility(View.VISIBLE);
            cellNo2.setVisibility(View.GONE);
            cellNo3.setVisibility(View.GONE);
            GridLayoutManager manager = new GridLayoutManager(this, 9);
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position==9) {
                        return 3;
                    }else{
                        return 1;
                    }
                }
            });
            if(devAdapter==null){
                devAdapter=new AUVBoardCellDevAdapter(this);
            }
            devAdapter.setDatas(mDateList);
            devAdapter.setTypeStr("A");
            devAdapter.setSrceenIndex(10);
            devAdapter.notifyDataSetChanged();
            cellNo1.setLayoutManager(manager);
            cellNo1.setItemAnimator(new DefaultItemAnimator());
            cellNo1.setAdapter(devAdapter);
        }else if(uitype==2){
            cellNo1.setVisibility(View.VISIBLE);
            cellNo2.setVisibility(View.GONE);
            cellNo3.setVisibility(View.VISIBLE);
            GridLayoutManager manager = new GridLayoutManager(this, 9);
            GridLayoutManager manager2 = new GridLayoutManager(this, 4);
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position==9) {
                        return 3;
                    }else{
                        return 1;
                    }
                }
            });
            if(devAdapter==null){
                devAdapter=new AUVBoardCellDevAdapter(this);
            }
            if(devAdapter2==null){
                devAdapter2=new AUVBoardCellDevAdapter(this);
            }
            devAdapter.setDatas(mDateList);
            devAdapter.setTypeStr("A");
            devAdapter.notifyDataSetChanged();
            devAdapter2.setDatas(mDateList2);
            devAdapter2.setTypeStr("B");
            devAdapter2.notifyDataSetChanged();
            devAdapter.setSrceenIndex(10);
            cellNo1.setLayoutManager(manager);
            cellNo1.setItemAnimator(new DefaultItemAnimator());
            cellNo1.setAdapter(devAdapter);
            cellNo3.setLayoutManager(manager2);
            cellNo3.setItemAnimator(new DefaultItemAnimator());
            cellNo3.setAdapter(devAdapter2);
        }else if(uitype==3){
            cellNo1.setVisibility(View.GONE);
            cellNo2.setVisibility(View.GONE);
            cellNo3.setVisibility(View.VISIBLE);
            GridLayoutManager manager = new GridLayoutManager(this, 4);
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position==4) {
                        return 3;
                    }else{
                        return 1;
                    }
                }
            });
            if(devAdapter==null){
                devAdapter=new AUVBoardCellDevAdapter(this);
            }
            devAdapter.setDatas(mDateList);
            devAdapter.setTypeStr("A");
            devAdapter.setSrceenIndex(5);
            cellNo3.setLayoutManager(manager);
            cellNo3.setItemAnimator(new DefaultItemAnimator());
            cellNo3.setAdapter(devAdapter);
            devAdapter.notifyDataSetChanged();
        }else if(uitype==4){
            cellNo1.setVisibility(View.VISIBLE);
            cellNo2.setVisibility(View.VISIBLE);
            cellNo3.setVisibility(View.VISIBLE);
            GridLayoutManager manager = new GridLayoutManager(this, 9);
            GridLayoutManager manager2 = new GridLayoutManager(this, 9);
            GridLayoutManager manager3 = new GridLayoutManager(this, 4);
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position==9) {
                        return 3;
                    }else{
                        return 1;
                    }
                }
            });
            manager3.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position>3) {
                        return 2;
                    }else{
                        return 1;
                    }
                }
            });
            if(devAdapter==null){
                devAdapter=new AUVBoardCellDevAdapter(this);
            }
            if(devAdapter2==null){
                devAdapter2=new AUVBoardCellDevAdapter(this);
            }
            if(devAdapter3==null){
                devAdapter3=new AUVBoardCellFallsDevAdapter(this);
            }
            devAdapter.setDatas(mDateList);
            devAdapter.setTypeStr("A");
            devAdapter.setSrceenIndex(10);
            devAdapter.notifyDataSetChanged();
            devAdapter2.setDatas(mDateList2);
            devAdapter2.setTypeStr("B");
            devAdapter2.notifyDataSetChanged();
            devAdapter3.setDatas(mDateList3);
            devAdapter3.setTypeStr("C");
            devAdapter3.setSrceenIndex(5);
            devAdapter3.notifyDataSetChanged();
            cellNo1.setLayoutManager(manager);
            cellNo1.setAdapter(devAdapter);
            cellNo2.setLayoutManager(manager2);
            cellNo2.setAdapter(devAdapter2);
            cellNo3.setLayoutManager(manager3);
            cellNo3.setAdapter(devAdapter3);
        }

    }
    /**
     * 消息显示弹框
     * @param message
     */
    public void showMessageDialog(String message){
        if (showMessageCentryDialog == null) {
            showMessageCentryDialog = (CustomShowMessageCentryDialog) new XPopup.Builder(getApplication())
                    .asCustom(new CustomShowMessageCentryDialog(mActivity,null,message))
                    .show();
        } else if (!showMessageCentryDialog.isShow()) {
            showMessageCentryDialog.setCurrentDevice(null);
            showMessageCentryDialog.setMessage(message);
            showMessageCentryDialog.show();
        }
    }


    /**
     * 开门提示
     * @param device
     */
    public  void  showOpenDialog(AUVBoardCellDevice device){
        if (showMessageCentryDialog == null) {
            showMessageCentryDialog = (CustomShowMessageCentryDialog) new XPopup.Builder(getApplication())
                    .asCustom(new CustomShowMessageCentryDialog(mActivity,device,""))
                    .show();
        } else if (!showMessageCentryDialog.isShow()) {
            showMessageCentryDialog.setCurrentDevice(device);
            showMessageCentryDialog.setMessage("");
            showMessageCentryDialog.show();
        }

    }
    /**
     * 普通用户提示未归还
     * @param device
     */
    public void showHaveNoReturnAlarm(AUVBoardCellDevice device){

        if (customHaveReturnAlarmDialog == null) {
            customHaveReturnAlarmDialog = (CustomHaveReturnAlarmDialog) new XPopup.Builder(getApplication())
                    .asCustom(new CustomHaveReturnAlarmDialog(mActivity,device))
                    .show();
        } else if (!customHaveReturnAlarmDialog.isShow()) {

            customHaveReturnAlarmDialog.setAuvBoardCellDevice(device);
            customHaveReturnAlarmDialog.show();
        }
    }

    /**
     *显示归还弹框
     */

    public void  showReturn(AUVBoardCellDevice device){
        if (returnDialog == null) {
            returnDialog = (CustomReturnAlarmCentryDialog) new XPopup.Builder(getApplication())
                    .asCustom(new CustomReturnAlarmCentryDialog(mActivity,device))
                    .show();
        } else if (!returnDialog.isShow()) {
            returnDialog.setCellDevice(device);
            returnDialog.show();
        }
    }
    /**
     * 显示管理员归还主列表页面
     * @param datas
     */
   public  void showLeaderReturn(List<AUVBoardCellDevice> datas){

       if (returnMainDialog == null) {
           returnMainDialog = (CustomLeaderReturnMainDialog) new XPopup.Builder(getApplication())
                   .asCustom(new CustomLeaderReturnMainDialog(mActivity,datas))
                   .show();
       } else if (!returnMainDialog.isShow()) {
           returnMainDialog.setmDatas(datas);
           returnMainDialog.show();
       }

   }

    /**
     * 显示借弹框
     */
    public  void showBorrow(){
        if (borrowCentryDialog == null) {
            borrowCentryDialog = (CustomBorrowCentryDialog) new XPopup.Builder(getApplication())
                    .asCustom(new CustomBorrowCentryDialog(mActivity))
                    .show();
        } else if (!borrowCentryDialog.isShow()) {
            borrowCentryDialog.show();
        }
    }


    /**
     * 显示管理员登录
     */
    public  void showAdmain(){

        if (adminLoginCentryDialog == null) {
            adminLoginCentryDialog = (CustomAdminLoginCentryDialog) new XPopup.Builder(this)
                    .asCustom(new CustomAdminLoginCentryDialog(this))
                    .show();
        } else if (!adminLoginCentryDialog.isShow()) {
            adminLoginCentryDialog.show();
        }

    }

    /**
     * 显示老板端未归还
     * @param list
     */
    public  void showLeaderCentryDialog(List<AUVBoardCellDevice> list){
        if (leaderCentryDialog == null) {
            leaderCentryDialog = (CustomAlarmLeaderCentryDialog) new XPopup.Builder(getApplication())
                    .asCustom(new CustomAlarmLeaderCentryDialog(mActivity,list))
                    .show();
        } else if (!leaderCentryDialog.isShow()) {
            leaderCentryDialog.setList(list);
            leaderCentryDialog.show();
        }
    }
    public  void  hideDialog(){
        if(showMessageCentryDialog!=null&&showMessageCentryDialog.isShown()){
            showMessageCentryDialog.dismiss();
        }
    }
    /**
     * 显示是否货物是好坏的选择
     * @param device
     */
    public void showCustomGoodOrFailCentryDialog(AUVBoardCellDevice device){
        if (customGoodOrFailCentryDialog == null) {
            customGoodOrFailCentryDialog = (CustomGoodOrFailCentryDialog) new XPopup.Builder(getApplication())
                    .asCustom(new CustomGoodOrFailCentryDialog(mActivity,device))
                    .show();
        } else if (!customGoodOrFailCentryDialog.isShow()) {
            customGoodOrFailCentryDialog.setAuvBoardCellDevice(device);
            customGoodOrFailCentryDialog.show();
        }

    }

    /*
    * 数据耗时操作
    * */
    @Override
    public void doBusiness() {
        userLoginViewModel.getMLDByCode(this,login, String.class,true).observe(this, (success) -> {
            if (success != null) {
                SPUtils.getInstance().put("token", success);
                Constant.Authorization= "Bearer "+SPUtils.getInstance().getString("token","");
                MyApplication.isFirst=true;
                userLoginViewModel.getUserList();
                LogUtils.d("登陆成功！");
            }
        });
        userLoginViewModel.getMLDByCode(this,getUserList, List.class,true).observe(this, (success) -> {
            if (success != null) {
                initUser(success);
                LogUtils.d("更新用户成功！");
            }
        });
        updateToServerViewModel.getMLDByCode(this,uploadLockers, List.class,true).observe(this, (success) -> {
            if (success != null) {
                updateToServerViewModel.uploadBins(deviceList);
                LogUtils.d("上传Locker数据成！");
            }
        });
        updateToServerViewModel.getMLDByCode(this,uploadBins, List.class,true).observe(this, (success) -> {
            if (success != null) {
                updateToServerViewModel.uploadItems(deviceList);
                LogUtils.d("上传BIN和Item数据成！");

            }
        });
        userLoginViewModel.getMLDByCode(this,uploadOperators,List.class,true).observe(this,(success)->{
            if (success != null){
                LogUtils.d("上传操作LOG数据成！");
                ThreadUtils.getFixedPool(3).execute(new Runnable() {
                    @Override
                    public void run() {
                        AppDatabase.getInstance(getApplication()).deleAllOperation();
                    }
                });
            }
        });

        userLoginViewModel.login("2_sprc","97RgHj2dPCENqCwj");
        Intent i = new Intent(this, LongRunningService.class);
        startService(i);
    }

    /*
    * 点击事件回调
    * */
    @Override
    public void onDebouncingClick(@NonNull View view) {

    }



    @Override
    protected void onPause() {
        super.onPause();
        if (MyApplication.isUSB){
            AUVCellController.getInstance(mActivity).onPause();
            unregisterReceiver(mUsbReceiver);
        }else {
            LockControlBoardUtils.getInstances().closeSerialPort();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.PORT_NAME=SPUtils.getInstance().getString("serialPort", "");
        initScanDevice();
        initDevice();
        refreshView();
       if(MyApplication.isUSB){
           AUVCellController.getInstance(mActivity).initUsbControl();
           AUVCellController.getInstance(mActivity).onDeviceStateChange();
           mUsbReceiver=new UsbBroadcastReceiver(AUVCellController.getInstance(mActivity));
           IntentFilter usbFilter = new IntentFilter();
           usbFilter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
           usbFilter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
           registerReceiver(mUsbReceiver, usbFilter);
       }else {
            if(!StringUtils.isEmpty(MyApplication.PORT_NAME)){
                 LockControlBoardUtils.getInstances().onDeviceStateChange();
             }
       }






    }

    @Override
    public int bindLayout() {
        return R.layout.activity_home;
    }

    public void initScanDevice(){
        /*
            监听扫码
         */
        if(scanGunKeyEventUtils!=null){
            scanGunKeyEventUtils.setOnBarCodeCatchListener(new ScanGunKeyEventUtils.OnScanSuccessListener() {
                @Override
                public void onScanSuccess(String barcode) {
                    if(StringUtils.isEmpty(barcode)){
                        return;
                    }
                    hideDialog();
                    ThreadUtils.getFixedPool(3).execute(new Runnable() {
                        @Override
                        public void run() {
                            String nowString = TimeUtils.getNowString(new SimpleDateFormat("yyyy-MM-dd"));
                            String startTimeStr = SPUtils.getInstance().getString("beginTime", "");
                            String endTimeStr = SPUtils.getInstance().getString("endTime", "");
                            if (!TextUtils.isEmpty(endTimeStr) && !TextUtils.isEmpty(startTimeStr)) {
                                long timeSpanByNow = TimeUtils.getTimeSpanByNow(nowString + " " + endTimeStr + ":00", TimeConstants.SEC);
                                long timeSpanByNow2 = TimeUtils.getTimeSpanByNow(nowString + " " + startTimeStr + ":00", TimeConstants.SEC);
                                if (timeSpanByNow < 0 || timeSpanByNow2 > 0) {
                                    showMessageDialog("Service Time  "+startTimeStr+"~ "+endTimeStr);
                                    return;
                                }
                            }

                            List<User> list = AppDatabase.getInstance(getApplication()).queryUser(barcode);
                            if(list!=null&&list.size()==1){
                                    SPUtils.getInstance().put("userNo",list.get(0).get_id());
                                    SPUtils.getInstance().put("userName",list.get(0).getUsername());
                                    userdevices = AppDatabase.getInstance(getApplication()).queryDevice(barcode);
                                    if(list.get(0).getRole().equals("USER")||list.get(0).getRole().equals("OPERATOR")){
                                        if(userdevices!=null&&userdevices.size()>=1){
                                            showHaveNoReturnAlarm(userdevices.get(0));
                                        }else {
                                            showBorrow();
                                        }
                                    }else if(list.get(0).getRole().equals("MASTER")){
                                        if(userdevices!=null&&userdevices.size()>0){
                                            showLeaderCentryDialog(userdevices);
                                        }else {
                                            showBorrow();
                                        }
                                    }
                            }else {
                                //不存在用户
                                showMessageDialog("No the User !");
                                SPUtils.getInstance().put("userNo","");
                            }

                        }
                    });
                }
            });

        }
    }



}
