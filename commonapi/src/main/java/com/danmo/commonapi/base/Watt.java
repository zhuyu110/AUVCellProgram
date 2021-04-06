package com.danmo.commonapi.base;

import java.io.Serializable;
//电表
public class Watt implements Serializable {
    double voltage1;
    double voltage2;
    double voltage3;
    double current1;
    double current2;
    double current3;
    double electricityEnergy;

    public Watt() {
    }

    public Watt(double voltage1, double voltage2, double voltage3, double current1, double current2, double current3, double electricityEnergy) {
        this.voltage1 = voltage1;
        this.voltage2 = voltage2;
        this.voltage3 = voltage3;
        this.current1 = current1;
        this.current2 = current2;
        this.current3 = current3;
        this.electricityEnergy = electricityEnergy;
    }

    public double getVoltage1() {
        return voltage1;
    }

    public void setVoltage1(double voltage1) {
        this.voltage1 = voltage1;
    }

    public double getVoltage2() {
        return voltage2;
    }

    public void setVoltage2(double voltage2) {
        this.voltage2 = voltage2;
    }

    public double getVoltage3() {
        return voltage3;
    }

    public void setVoltage3(double voltage3) {
        this.voltage3 = voltage3;
    }

    public double getCurrent1() {
        return current1;
    }

    public void setCurrent1(double current1) {
        this.current1 = current1;
    }

    public double getCurrent2() {
        return current2;
    }

    public void setCurrent2(double current2) {
        this.current2 = current2;
    }

    public double getCurrent3() {
        return current3;
    }

    public void setCurrent3(double current3) {
        this.current3 = current3;
    }

    public double getElectricityEnergy() {
        return electricityEnergy;
    }

    public void setElectricityEnergy(double electricityEnergy) {
        this.electricityEnergy = electricityEnergy;
    }
}
