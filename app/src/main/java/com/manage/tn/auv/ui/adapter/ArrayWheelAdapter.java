package com.manage.tn.auv.ui.adapter;

import com.contrarywind.adapter.WheelAdapter;

import java.util.List;

public class ArrayWheelAdapter implements WheelAdapter {
    List<String> datas;
    public ArrayWheelAdapter(List<String> datas) {
        this.datas=datas;
    }

    @Override
    public int getItemsCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int index) {
        return datas.get(index);
    }

    @Override
    public int indexOf(Object o) {
        return datas.indexOf(o);
    }
}
