package com.manage.tn.auv.util;
/*消息传递类
* */
public class EventBusMsg {
    private int radiotype;//0,all; 1,nofinish  代表 类型 2:代表修改时间 3:修改访问网点数量
    private  String time;
    private int websitesize;

    public int getWebsitesize() {
        return websitesize;
    }

    public void setWebsitesize(int websitesize) {
        this.websitesize = websitesize;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getRadiotype() {
        return radiotype;
    }

    public void setRadiotype(int radiotype) {
        this.radiotype = radiotype;
    }
}
