package zhou.com.vpn.api;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;

import zhou.com.vpn.base.Constant;
import zhou.com.vpn.bean.SelectBean;
import zhou.com.vpn.utils.AppManager;
import zhou.com.vpn.utils.SpUtil;
import zhou.com.vpn.utils.WebServiceUtil;

/**
 * Created by zhou
 * on 2018/11/27.
 */

public class httpUtil {

    private static final String TAG = "联网";
    private static String url = Constant.BASE_URL+"/DMS_Phone/WebServices/WebServiceForFWNG.asmx";

    /**
     * 上传手机号码，物理地址
     */
    public static void initSaveFWNGRegInfo(Context context) {
        //获取手机号码
        TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint({"HardwareIds", "MissingPermission"}) String te1  = tm.getLine1Number();//获取本机号码*/
        SelectBean selectBean = (SelectBean) SpUtil.getObject(context, Constant.Account, SelectBean.class);

        Log.d(TAG, "initSaveFWNGRegInfo: "+ AppManager.getMacAddress());
        HashMap<String, String> data = new HashMap<>();
        data.put("UserID",selectBean.getUser());
        data.put("MAC",AppManager.getMacAddress());
        data.put("Mobile",te1);
        WebServiceUtil.callWebService(url, "SaveFWNGRegInfo", data, new WebServiceUtil.WebServiceCallBack() {
            @Override
            public void callBack(SoapObject result) {
                if (result!=null)
                Log.d(TAG, "上传手机号码，物理地址: " + result.toString());
            }
        });
    }

    /**
     * 获取序列号
     */
    public static void initGetFwNGKeySN() {

        HashMap<String, String> data = new HashMap<>();
        WebServiceUtil.callWebService(url, "GetFWNGKeySN", data, new WebServiceUtil.WebServiceCallBack() {
            @Override
            public void callBack(SoapObject result) {
                Log.d(TAG, "序列号: " + result.toString());
            }
        });
    }

    /**
     * 解锁文件
     *
     * @param GUID
     */
    @SuppressLint("HandlerLeak")
    public static void UnlockFile(String GUID) {
        HashMap<String, String> data = new HashMap<>();
        data.put("GUID", GUID);

        WebServiceUtil.callWebService(Constant.UPLOAD_URL, "UnlockFile", data, new WebServiceUtil.WebServiceCallBack() {
            @Override
            public void callBack(SoapObject result) {
                if (result!=null)
                    Log.d(TAG, "callBack: " + result.toString());
            }
        });
    }

    /**
     * 小文件上传
     */
    String methodName = "UploadFile"; //webservice方法

    @SuppressLint("HandlerLeak")
    private void uploadWebService(int offset, int total, final String uploadBuffer,String guid) {

        HashMap<String, String> data = new HashMap<>();
        data.put("input", uploadBuffer);
        data.put("offset", offset + "");
        data.put("total", total + "");
        data.put("GUID", guid);
        WebServiceUtil.callWebService(Constant.UPLOAD_URL, methodName, data, new WebServiceUtil.WebServiceCallBack() {
            @Override
            public void callBack(SoapObject result) {
                if (result!=null)
                Log.d(TAG, "callBack: " + result.toString());
            }
        });
    }
}
