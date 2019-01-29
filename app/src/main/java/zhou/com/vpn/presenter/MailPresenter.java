package zhou.com.vpn.presenter;

import okhttp3.OkHttpClient;
import zhou.com.vpn.activity.LoginActivity;
import zhou.com.vpn.activity.MailActivity;
import zhou.com.vpn.api.VpnApi;
import zhou.com.vpn.base.RxPresenter;
import zhou.com.vpn.contract.MailContract;
import zhou.com.vpn.contract.loginContract;

/**
 * Created by zhou
 * on 2018/8/2.
 */

public class MailPresenter extends RxPresenter<MailContract.View> implements MailContract.Presenter<MailContract.View>{

    VpnApi vpnApi;
    MailActivity mailActivity;

    public MailPresenter(MailActivity mailActivity){
        this.mailActivity = mailActivity;
        this.vpnApi = new VpnApi(new OkHttpClient());
    }

    @Override
    public void getMailList(String param) {

    }
}
