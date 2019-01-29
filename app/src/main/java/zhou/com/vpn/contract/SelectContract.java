package zhou.com.vpn.contract;

import java.util.List;

import zhou.com.vpn.base.BaseContract;
import zhou.com.vpn.bean.AppInfoBean;
import zhou.com.vpn.bean.LoginBean;
import zhou.com.vpn.bean.SNIDList;
import zhou.com.vpn.bean.SelectBean;

/**
 * Created by zhou
 * on 2018/12/12.
 */

public interface SelectContract {
    interface View extends BaseContract.BaseView {
        void getSnidListSuccess(List<SNIDList> snidList);
        String setKeywork();
        void getAppinfoSuccess(AppInfoBean appInfoBean);
        String setSNID();
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getSnidList();
        String getKeywork();
        void getAppInfo();
        String getSNID();
    }
}
