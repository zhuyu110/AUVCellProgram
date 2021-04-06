package com.manage.tn.auv.viewModel;

import com.danmo.commonapi.DataRepository;
import com.danmo.commonapi.base.Watt;
import com.danmo.commonapi.db.entity.AUVBoardCellDevice;
import com.danmo.commonapi.db.entity.OperationRecord;
import com.manage.tn.auv.base.BaseViewModel;
import com.manage.tn.auv.util.Config;

import java.util.List;

public class UpdateToServerViewModel extends BaseViewModel {
    public UpdateToServerViewModel(DataRepository dataRepository) {
        super(dataRepository);
    }

    public void uploadLockers(List<AUVBoardCellDevice> devices){
        mDataSource.commonApi.deviceImpl.uploadLockers(devices);
    }


    public void uploadBins(List<AUVBoardCellDevice> devices){
        mDataSource.commonApi.deviceImpl.uploadBins(devices);
    }
    public void uploadItems(List<AUVBoardCellDevice> devices){
        mDataSource.commonApi.deviceImpl.uploadItems(devices);
    }
    public void uploadOperators(List<OperationRecord> records){
        mDataSource.commonApi.deviceImpl.uploadOperators(records);
    }
}
