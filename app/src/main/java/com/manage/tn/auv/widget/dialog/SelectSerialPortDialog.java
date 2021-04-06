package com.manage.tn.auv.widget.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android_serialport_api.DeviceUtils;

import com.manage.tn.auv.R;

import java.util.List;


public class SelectSerialPortDialog extends BaseDialog {
    private View emptySerialPortView;
    private LinearLayout serialPortContainerView;
    private List<String> serialPortList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.select_serial_port_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emptySerialPortView = view.findViewById(R.id.empty_serial_port_label);
        serialPortContainerView = view.findViewById(R.id.serial_port_container_view);

        serialPortList = DeviceUtils.getAllDevices();

        if (serialPortList.size() > 0) {
            serialPortContainerView.removeAllViews();
            for (String serialPort : serialPortList) {

                if(DeviceUtils.isNotEmpty(serialPort)){
                    int  index = serialPort.indexOf("(");
                    if(index>0){
                        serialPort = serialPort.substring(0,index);
                        serialPort =  serialPort.replace(" ","");
                    }
                }
                Button button = (Button) LayoutInflater.from(getActivity()).inflate(R.layout.serial_port_button, null);

                button.setText(serialPort);

                String finalSerialPort = serialPort;
                button.setOnClickListener(view1 -> {
                    if (onSerialPortSelectedListener != null) {
                        onSerialPortSelectedListener.onSerialPortSelected(finalSerialPort);
                    }

                    dismiss();
                });
                serialPortContainerView.addView(button);
            }
        } else {
            emptySerialPortView.setVisibility(View.VISIBLE);
            serialPortContainerView.setVisibility(View.GONE);
        }
    }

    private OnSerialPortSelectedListener onSerialPortSelectedListener;

    public void setOnSerialPortSelectedListener(OnSerialPortSelectedListener onSerialPortSelectedListener) {
        this.onSerialPortSelectedListener = onSerialPortSelectedListener;
    }

    public interface OnSerialPortSelectedListener {
        void onSerialPortSelected(String serialPort);
    }
}
