package zhou.com.vpn.contract;

import java.util.List;

import zhou.com.vpn.base.BaseContract;
import zhou.com.vpn.bean.LoginBean;
import zhou.com.vpn.bean.MailBean;

/**
 * Created by zhou
 * on 2018/8/2.
 */

public interface MailContract {

    interface View extends BaseContract.BaseView {

        void onSuccess(List<MailBean> list);

        void onFailure(String errorMsg);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {

        public abstract void getMailList(String param);
    }

}
