package zhou.com.vpn.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.WindowManager;

import org.ksoap2.serialization.SoapObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.wps.moffice.client.ViewType;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import zhou.com.vpn.R;
import zhou.com.vpn.api.VpnApi;
import zhou.com.vpn.api.httpUtil;
import zhou.com.vpn.base.Constant;
import zhou.com.vpn.bean.AppInfoBean;
import zhou.com.vpn.bean.LoginBean;
import zhou.com.vpn.bean.SNIDList;
import zhou.com.vpn.bean.SelectBean;
import zhou.com.vpn.bean.VpnSelectBean;
import zhou.com.vpn.contract.SelectContract;
import zhou.com.vpn.presenter.SelectPresenter;
import zhou.com.vpn.service.MyServpce;
import zhou.com.vpn.utils.AppManager;
import zhou.com.vpn.utils.SpUtil;
import zhou.com.vpn.utils.ToastUtil;
import zhou.com.vpn.utils.WebServiceUtil;

public class SplashActivity extends AppCompatActivity implements SelectContract.View{

    private static final String TAG = "SplashActivity";
    private SelectPresenter presenter = new SelectPresenter(this);
    private String SNID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        presenter.attachView(this);
        presenter.getAppInfo();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        AppManager.getAppManager().addActivity(SplashActivity.this);

        final boolean aBoolean = SpUtil.getBoolean(getApplicationContext(), Constant.VPN_AUTO, false);//是否自动登录
        VpnSelectBean vpnSelectBean = (VpnSelectBean) SpUtil.getObject(getApplicationContext(), Constant.vpnAccount, VpnSelectBean.class);//vpn 的账号密码
        SelectBean selectBean = (SelectBean) SpUtil.getObject(getApplicationContext(), Constant.Account, SelectBean.class);//账号密码

        Log.d(TAG, "onCreate: 123");
        if (aBoolean) {
            Log.d(TAG, "onCreate: 1234");
            //vpn自动登录
            if (vpnSelectBean != null && selectBean != null) {
                //点击过vpn登录
                if (vpnSelectBean.isSelect()) {
                    startService(new Intent(getApplicationContext(), MyServpce.class));
                } else {
                    //没有点击过vpn登录
                    login(selectBean.getUser(), selectBean.getPsd());
                }
            } else {
                if (selectBean != null){
                    //没有vpn 的自动登录
                    login(selectBean.getUser(), selectBean.getPsd());
                }else {
                    Log.d(TAG, "onCreate: 123456");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                            finish();
                        }
                    }, 2000);
                }

            }
        } else {
            Log.d(TAG, "onCreate: 123456");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
            }, 2000);
        }
    }

    /**
     * 没有使用vpn的自动登录
     *
     * @param user
     * @param psd
     */
    private void login(final String user, final String psd) {
        new VpnApi(new OkHttpClient()).vpnLogin("Login", "{UserID:'" + user + "',UserPsw:'" + psd + "'}")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(getApplicationContext(),"请检查网络");
                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        if (loginBean.isResult()){
                            Intent intent = new Intent(getApplicationContext(),WebActivity.class);
                            intent.putExtra(Constant.ACCOUNT_USER,user);
                            intent.putExtra(Constant.ACCOUNT_PSD,psd);
                            startActivity(intent);
                            finish();
                        }else {
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                            ToastUtil.show(getApplicationContext(),loginBean.getMessage());
                        }
                    }
                });
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }

    @Override
    public void getSnidListSuccess(List<SNIDList> snidList) {

    }

    @Override
    public String setKeywork() {
        return null;
    }

    @Override
    public void getAppinfoSuccess(AppInfoBean appInfoBean) {
        if (appInfoBean==null)return;
        Log.d(TAG, "getAppinfoSuccess: "+appInfoBean.toString());
        SpUtil.putObject(getApplicationContext(),Constant.AppInfo,appInfoBean);
    }

    @Override
    public String setSNID() {
        return null;
    }
}
