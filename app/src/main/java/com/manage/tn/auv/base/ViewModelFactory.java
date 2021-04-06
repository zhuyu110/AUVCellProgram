/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.manage.tn.auv.base;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.Utils;
import com.danmo.commonapi.DataRepository;
import com.manage.tn.auv.MyApplication;

import java.lang.reflect.InvocationTargetException;

/**
 *  ViewModels构造工厂
 */
public class ViewModelFactory<G extends DataRepository> implements ViewModelProvider.Factory {

    private  DataRepository mRepository;

    public ViewModelFactory() {
        if(mRepository==null){
            mRepository= ((MyApplication)Utils.getApp()).getRepository();
        }
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {

        try {
            return (T) modelClass.getDeclaredConstructor(mRepository.getClass()).newInstance(mRepository);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        throw new IllegalArgumentException("Unknown ViewModel class");

    }
}
