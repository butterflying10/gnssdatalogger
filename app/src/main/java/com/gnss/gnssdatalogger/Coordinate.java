package com.gnss.gnssdatalogger;

/**
 * 主要实现在wgs84椭球下  大地坐标系转为WGS84空间直角坐标系
 * 白腾飞
 * 202.3.26
 */
public class Coordinate {

    private double Lat;   //rad
    private double Long;  //rad
    private double Alt;//WGS84椭球面  m

    //度转为弧度表示
    private double PI = Math.PI;
    private static double API = Math.PI / 180.0;

    public static double[] WGS84LLAtoXYZ(double Lat, double Long, double Alt) {
        final double a = 6378137.0;
        final double f = 1 / 298.257223563;
        final double b = a * (1 - f);
        double e1 = Math.pow(a * a - b * b, 0.5) / a;
        double e2 = Math.pow(a * a - b * b, 0.5) / b;

        double Lat_rad=Lat*API;

        //子午线弧长计算步骤
        double m0=a*(1-Math.pow(e1,2.0));
        double m2 = 1.5 * Math.pow(e1, 2.0) * m0;
        double m4 = 1.25 * Math.pow(e1, 2.0) * m2;
        double m6 = 7.0 / 6.0 * Math.pow(e1, 2.0) * m4;
        double m8 = 9.0 / 8.0 * Math.pow(e1, 2.0) * m6;

        double a0 = m0 + m2 / 2.0 + 3.0 / 8.0 * m4 + 5.0 / 16.0 * m6 + 35.0 / 128.0 * m8;
        double a2 = m2 / 2.0 + m4 / 2.0 + 15.0 / 32.0 * m6 + 7.0 / 16.0 * m8;
        double a4 = m4 / 8.0 + 3.0 / 16.0 * m6 + 7.0 / 32.0 * m8;
        double a6 = m6 / 32.0 + m8 / 16.0;
        double a8 = m8 / 128.0;
        double X = a0 * Lat_rad - a2 / 2.0 * Math.sin(2.0 * Lat_rad) + a4 / 4.0 * Math.sin(4.0 * Lat_rad) - a6 / 6.0 * Math.sin(6.0 * Lat_rad) + a8 / 8.0 * Math.sin(8.0 * Lat_rad);


        //获取三度带的中心子午线
        int n=(int)Math.round(Long/3);
        double Long0=3*n;

        double l=(Long-Long0)*API;


        double t = Math.tan(Lat_rad);
        double η = e2 * Math.cos(Lat_rad);
        double N = a / Math.sqrt(1 - e1 *e1* Math.sin(Lat_rad) * Math.sin(Lat_rad));//卯酉圈半径长度
//        double x=X+N/2*Math.sin(Lat_rad)*Math.cos(Lat_rad)*Math.pow(l,2)+N/24*Math.sin(Lat_rad)*Math.pow(Math.cos(Lat_rad),3)*(5-Math.pow(t,2)+Math.pow(η,2)*9+4*Math.pow(η,4))*Math.pow(l,4)+N/720*Math.sin(Lat_rad)*Math.pow(Math.cos(Lat_rad),5)*(61-58*Math.pow(t,2)+Math.pow(t,4)+270*Math.pow(η,2)-330*Math.pow(η,2)*Math.pow(t,2))*Math.pow(l,6);
//
//        double y = N * l * Math.cos(Lat_rad) + N / 6 * Math.pow(Math.cos(Lat_rad), 3) * (1 - Math.pow(t, 2) + Math.pow(η, 2)) * Math.pow(l, 3) + N / 120 * Math.pow(Math.cos(Lat_rad), 5) * (5 - 18 * Math.pow(t, 2) + Math.pow(t, 4) + 14 * Math.pow(η, 2) - 58 * Math.pow(η, 2) * Math.pow(t, 2)) * Math.pow(l, 5);

        double x=(N+Alt)*Math.cos(Lat_rad)*Math.cos(Long*API);
        double y=(N+Alt)*Math.cos(Lat_rad)*Math.sin(Long*API);
        double z=(N*(1-e1*e1)+Alt)*Math.sin(Lat_rad);
        double[] XYZ=new double[]{x,y,z};
        return XYZ;
    }


}
