package com.danmo.commonapi.db.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.danmo.commonapi.db.entity.AUVBoardCellDevice;
import com.danmo.commonapi.db.entity.OperationRecord;
import com.danmo.commonapi.db.entity.User;

import com.danmo.commonapi.db.greendao.AUVBoardCellDeviceDao;
import com.danmo.commonapi.db.greendao.OperationRecordDao;
import com.danmo.commonapi.db.greendao.UserDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig aUVBoardCellDeviceDaoConfig;
    private final DaoConfig operationRecordDaoConfig;
    private final DaoConfig userDaoConfig;

    private final AUVBoardCellDeviceDao aUVBoardCellDeviceDao;
    private final OperationRecordDao operationRecordDao;
    private final UserDao userDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        aUVBoardCellDeviceDaoConfig = daoConfigMap.get(AUVBoardCellDeviceDao.class).clone();
        aUVBoardCellDeviceDaoConfig.initIdentityScope(type);

        operationRecordDaoConfig = daoConfigMap.get(OperationRecordDao.class).clone();
        operationRecordDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        aUVBoardCellDeviceDao = new AUVBoardCellDeviceDao(aUVBoardCellDeviceDaoConfig, this);
        operationRecordDao = new OperationRecordDao(operationRecordDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);

        registerDao(AUVBoardCellDevice.class, aUVBoardCellDeviceDao);
        registerDao(OperationRecord.class, operationRecordDao);
        registerDao(User.class, userDao);
    }
    
    public void clear() {
        aUVBoardCellDeviceDaoConfig.clearIdentityScope();
        operationRecordDaoConfig.clearIdentityScope();
        userDaoConfig.clearIdentityScope();
    }

    public AUVBoardCellDeviceDao getAUVBoardCellDeviceDao() {
        return aUVBoardCellDeviceDao;
    }

    public OperationRecordDao getOperationRecordDao() {
        return operationRecordDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

}
