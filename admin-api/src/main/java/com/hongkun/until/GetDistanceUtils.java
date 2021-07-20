package com.hongkun.until;

/**
 * @ClassName MapUtils
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/16 17:48
 */
public class GetDistanceUtils {

    private static double rad(double d) {
        return d * Math.PI / 180.00; // 角度转换成弧度
    }

    public static void main(String[] args) {
        String startLonLat = "116.413731,39.979324";
        String endLonLat = "116.417537,39.97722";
        String key = "34933c35ef4cc86a937f428eb3933657";
        double longitude1 = 116.413732;
        double latitude1 = 39.97722;
        double longitude2 = 116.413731;
        double latitude2 = 39.97722;
        double dis = algorithm(longitude1, latitude1, longitude2, latitude2);
        System.out.println(dis);
    }


    /*
     * 根据经纬度计算两点之间的距离（单位米）
     */
    public static double algorithm(double longitude1, double latitude1, double longitude2, double latitude2) {

        double Lat1 = rad(latitude1); // 纬度

        double Lat2 = rad(latitude2);

        double a = Lat1 - Lat2;// 两点纬度之差

        double b = rad(longitude1) - rad(longitude2); // 经度之差

        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(Lat1) * Math.cos(Lat2) * Math.pow(Math.sin(b / 2), 2)));// 计算两点距离的公式

        s = s * 6378137.0;// 弧长乘地球半径（半径为米）

        s = Math.round(s * 10000d) / 10000d;// 精确距离的数值
        // 四舍五入 保留一位小数
        //DecimalFormat df = new DecimalFormat("#.0");

        return s;

    }


    private double[] gaoDeToBaidu(double gd_lon, double gd_lat) {
        double[] bd_lat_lon = new double[2];
        double PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = gd_lon, y = gd_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * PI);
        bd_lat_lon[0] = z * Math.cos(theta) + 0.0065;
        bd_lat_lon[1] = z * Math.sin(theta) + 0.006;
        return bd_lat_lon;
    }

    private double[] bdToGaoDe(double bd_lat, double bd_lon) {
        double[] gd_lat_lon = new double[2];
        double PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * PI);
        gd_lat_lon[0] = z * Math.cos(theta);
        gd_lat_lon[1] = z * Math.sin(theta);
        return gd_lat_lon;
    }


}
