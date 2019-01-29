package zhou.com.vpn.presenter;

import android.util.Log;

import okhttp3.OkHttpClient;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import zhou.com.vpn.activity.LoginActivity;
import zhou.com.vpn.activity.WebActivity;
import zhou.com.vpn.api.VpnApi;
import zhou.com.vpn.base.RxPresenter;
import zhou.com.vpn.bean.LoginBean;
import zhou.com.vpn.bean.NumBean;
import zhou.com.vpn.contract.WebContract;
import zhou.com.vpn.contract.loginContract;

/**
 * Created by zhou
 * on 2018/12/4.
 */

public class WebPresenter extends RxPresenter<WebContract.View> implements WebContract.Presenter<WebContract.View> {

    VpnApi vpnApi;
    WebActivity webActivity;

    public WebPresenter(WebActivity webActivity){
        this.webActivity = webActivity;
        this.vpnApi = new VpnApi(new OkHttpClient());
    }

    @Override
    public void GetPhoneAppNum() {
        Subscription rxSubscription = vpnApi.GetPhoneAppNum("GetPhoneAppNum",getUserId(),getSignature(),getTimestamp())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NumBean>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                        Log.d("", "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError();
                        Log.d("", "onError: "+e.getMessage());
                    }

                    @Override
                    public void onNext(NumBean numBean) {
                        Log.d("", "onNext: "+numBean.toString());
                        mView.GetPhoneAppNumSuccess(numBean);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public String getUserId() {
        return mView.setUserId();
    }

    @Override
    public String getSignature() {
        return mView.setSignature();
    }

    @Override
    public String getTimestamp() {
        return mView.setTimestamp();
    }
}
