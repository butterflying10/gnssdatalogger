package com.gnss.gnssdatalogger.Ntrip;

public interface RTCM3ClientListener {


    void onDataReceived(byte[] data);


}
