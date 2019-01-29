package zhou.com.vpn.contract;

import zhou.com.vpn.base.BaseContract;
import zhou.com.vpn.bean.LoginBean;

/**
 * Created by zhou
 * on 2018/8/1.
 */

public interface loginContract {

    interface View extends BaseContract.BaseView {

        void loginSuccess(LoginBean loginBean);

        void vpnLoginSuccess(LoginBean loginBean);

        String setUser();
        String setPsd();
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void login();

        void vpnLogin();

        String getUser();
        String getPsd();
    }
}
