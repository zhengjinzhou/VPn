package zhou.com.vpn.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import zhou.com.vpn.R;
import zhou.com.vpn.base.BaseActivity;
import zhou.com.vpn.bean.FileDataUtil;
import zhou.com.vpn.bean.MailBean;

public class MailActivity extends BaseActivity {

    private static final String TAG = "MailActivity";
    private String mailStr;
    private List<MailBean> mUserListBeen;
    private List<MailBean> mTempUserListBeen;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    HomeAdapter adapter;

    @BindView(R.id.txtTitle) TextView txtTitle;
    public static void newInstance(Context context) {
        Intent intent = new Intent(context, MailActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_mail;
    }

    @Override
    public void init() {
        txtTitle.setText("通讯录");
        //获取联系人列表
        String addressBook = FileDataUtil.loadDataFile(getApplicationContext(), "AddressBook");
        Gson gson = new Gson();

        mUserListBeen = gson.fromJson(addressBook, new TypeToken<List<MailBean>>(){}.getType());
        mTempUserListBeen = gson.fromJson(addressBook, new TypeToken<List<MailBean>>(){}.getType());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HomeAdapter();
        recyclerView.setAdapter(adapter);

    }

    @OnClick({R.id.lyLeftContainer}) void onClick(View view){
        switch (view.getId()){
            case R.id.lyLeftContainer:
                finish();
                break;
        }
    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    MailActivity.this).inflate(R.layout.parent_item, parent,
                    false));
            return holder;
        }

        @OnTextChanged(value = R.id.et_search,callback = OnTextChanged.Callback.TEXT_CHANGED)
        void onTextChange(CharSequence s, int start, int before, int count){
            if (!TextUtils.isEmpty(s)){
                mUserListBeen.clear();
                for (MailBean user : mTempUserListBeen){
                    if (user.getKSName().indexOf(s.toString()) != -1){
                        mUserListBeen.add(user);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }

        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.tv.setText(mUserListBeen.get(position).getKSName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: "+mUserListBeen.get(position));
                    DepartmentActivity.newInstance(MailActivity.this, mUserListBeen.get(position).toString());
                }
            });
        }

        @Override
        public int getItemCount() {
            return mUserListBeen == null ? 0 : mUserListBeen.size();
        }
        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv;
            public MyViewHolder(View view) {
                super(view);
                tv = view.findViewById(R.id.parent_title);
            }
        }
    }
}
