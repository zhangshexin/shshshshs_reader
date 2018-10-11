package com.redread.net;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class ConfigParam<T> {

    public static final String AppKey = "14424917";
    public static final String AppSecret = "88c8dffd9dbd49338f06668aedbf5ac6";
    public static final String baseUrl = "http://47.95.111.63:9090";
    private static final int CODE = 9999;
    private static ConfigParam configParam;

    private static Context context;

    public synchronized static ConfigParam getInstance(Context context) {
        if (configParam == null) {
            configParam = new ConfigParam();
            ConfigParam.context = context;
        }
        return configParam;
    }

    public String getFormat() {
        return "json";
    }

    public String getLocale() {
        return "zh_CN";
    }

    // 与服务器时间偏移值
    public static long timeOffset = 0;

    /**
     * 获取时间戳
     *

    public long getTimeStamp() {
        String url = baseUrl + "?";
        String catStr = "";
        if (timeOffset > 0) {
            // 返回现在时间偏移后的时间戳
            long timeStamp = new Date().getTime() + timeOffset;
            return timeStamp;
        } else {
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("appKey", AppKey);
            params.put("method", "api.system.time.get");
            params.put("v", "1.0");
            params.put("format", "json");
            params.put("timeStamp", String.valueOf(System.currentTimeMillis()));
            params.put("locale", getLocale());
            params.put("sign", buildSign(params));
            Iterator<Map.Entry<String, String>> iterator2 = params.entrySet().iterator();
            try {
                while (iterator2.hasNext()) {
                    Map.Entry ent = (Map.Entry) iterator2.next();
                    catStr += ent.getKey().toString() + "=" + URLEncoder.encode(ent.getValue().toString(), "UTF-8") + "&";
                }
            } catch (Exception e) {
            }
            catStr = catStr.substring(0, catStr.length() - 1);
            url += catStr;

            StringRequest request1 = new StringRequest(url);
            CallServer.getInstance().add(context, CODE, request1, new HttpListner<String>() {
                @Override
                public void onSucceed(int what, Response<String> response) {
                    if (what == CODE) {
                        Timestamp ts = new Gson().fromJson(response.get(), TimeStamp.class);
                        long svrTimestamp = 0;
                        if (!TextUtils.isEmpty(ts.getSystemTime())) {
                            svrTimestamp = Long.parseLong(ts.getSystemTime());
                        }
                        long curTimestamp = new Date().getTime();
                        timeOffset = svrTimestamp - curTimestamp;
                    }
                }

                @Override
                public void onFailed(int what, String message) {

                }
            }, true, false);
            long timeStamp = new Date().getTime() + timeOffset;
            return timeStamp;
        }


    }
     * @return
     */


    /**
     * 加密签名
     *
     * @param params
     * @return sing
     */
    public String buildSign(HashMap<String, String> params) {
        //对HashMap的key进行排序
        TreeMap treemap = new TreeMap(params);
        String str = "";
        Iterator titer = treemap.entrySet().iterator();
        while (titer.hasNext()) {
            Map.Entry ent = (Map.Entry) titer.next();
            String keyt = ent.getKey().toString();
            String valuet = ent.getValue().toString();
            str += (keyt + valuet);
        }
        str = AppSecret + str + AppSecret;
        try {
            str = sha1(str);
        } catch (Exception e) {
        }

        return str.toUpperCase();
    }

    /**
     * SHA1加密
     *
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     */
    public String sha1(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        md.update(data.getBytes());
        StringBuffer buf = new StringBuffer();
        byte[] bits = md.digest();
        for (int i = 0; i < bits.length; i++) {
            int a = bits[i];
            if (a < 0) a += 256;
            if (a < 16) buf.append("0");
            buf.append(Integer.toHexString(a));
        }
        return buf.toString();
    }

    /**
     * 获取版本号
     *
     * @return
     */
    public String getCurrentCode() {
        //获取当前版本号
        String now_version = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            now_version = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return now_version;
    }

    /**
     * 获取渠道
     *
     * @return
     */
    public String getChannel() {
        String channel = "";
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            channel = appInfo.metaData.getString("UMENG_CHANNEL");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (channel.isEmpty()) {
            channel = "anne";
        }
        return channel;
    }

    /**
     * get请求
     *
     * @param data 签名集合字段参数
     * @return
     */
    public String commonGetApi(HashMap<String, String> data) {
        String url = baseUrl + "?";
        String catStr = "";
        HashMap<String, String> params = new HashMap<String, String>();
        try {
            // 系统级别输入参数
            params.put("appKey", AppKey);
            params.put("method", data.get("method"));
            params.put("v", "1.0");
            params.put("format", "json");
            params.put("locale", getLocale());
            params.put("timeStamp", String.valueOf(new Date().getTime()));
            Iterator<Map.Entry<String, String>> iterator = data.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry ent = (Map.Entry) iterator.next();
                params.put(ent.getKey().toString(), ent.getValue().toString());
            }
            params.put("sign", buildSign(params));
            Log.e("PArams", params.toString());
            Iterator<Map.Entry<String, String>> iterator2 = params.entrySet().iterator();
            while (iterator2.hasNext()) {
                Map.Entry ent = (Map.Entry) iterator2.next();
//                params.put(ent.getKey().toString(), URLEncoder.encode(ent.getValue().toString(), "UTF-8"));
                catStr += ent.getKey().toString() + "=" + URLEncoder.encode(ent.getValue().toString(), "UTF-8") + "&";
            }
        } catch (Exception e) {
            e.printStackTrace();
//            Log.e("commonGetApi===>", e.getMessage());
        }
        catStr = catStr.substring(0, catStr.length());
        url += catStr;
        Log.i("commonGetApi===>", url);
        return url;
    }

    /**
     * post请求
     *
     * @param data       签名集合字段参数
     * @param ignoreData 需要忽略的签名集合字段参数
     * @return
     */
    public HashMap<String, String> commonPostApi(HashMap<String, String> data, HashMap<String, String> ignoreData) {
        Log.i("commonPostApi Url===>", baseUrl);
        HashMap<String, String> params = new HashMap<String, String>();
        try {
            // 系统级别输入参数
            params.put("appKey", AppKey);
            params.put("method", data.get("method"));
            params.put("v", "1.0");
            params.put("format", "json");
            params.put("locale", getLocale());
            params.put("timeStamp", String.valueOf(new Date().getTime()));
            Iterator<Map.Entry<String, String>> iterator = data.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry ent = (Map.Entry) iterator.next();
                params.put(ent.getKey().toString(), ent.getValue().toString());
            }
            params.put("sign", buildSign(params));
            Log.e("SIGN", params.toString());
            if (ignoreData != null) {
                Iterator<Map.Entry<String, String>> iterator1 = ignoreData.entrySet().iterator();
                while (iterator1.hasNext()) {
                    Map.Entry ent = (Map.Entry) iterator1.next();
                    params.put(ent.getKey().toString(), URLEncoder.encode(ent.getValue().toString(), "UTF-8"));
                }
            }
            Iterator<Map.Entry<String, String>> iterator2 = params.entrySet().iterator();

            while (iterator2.hasNext()) {
                Map.Entry ent = (Map.Entry) iterator2.next();
                params.put(ent.getKey().toString(), URLEncoder.encode(ent.getValue().toString(), "UTF-8"));
                Log.i("commonPostApi Body===>", ent.getKey().toString() + "=" + ent.getValue().toString());
            }

        } catch (Exception e) {
            Log.e("commonPostApi error===>", e.getMessage());
        }

        return params;
    }


    public String paramToString(String method,HashMap<String, String> data){
        String url=baseUrl+"/"+method+"?";
        Iterator<Map.Entry<String, String>> iterator = data.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry ent = (Map.Entry) iterator.next();
            url=url+ent.getKey().toString()+"="+ ent.getValue().toString()+"&";
        }
        return url;
    }
}