package com.example.smsfiltering.modules.tag;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smsfiltering.R;
import com.example.smsfiltering.base.BaseApplication;
import com.example.smsfiltering.greendao.KeyWordDao;
import com.example.smsfiltering.table.KeyWord;
import com.example.smsfiltering.utils.SharePreferenceUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TagMangerActivity extends AppCompatActivity {


    @BindView(R.id.toolbar_back)
    ImageView mToolbarBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.et_keywords)
    EditText mEtKeywords;
    @BindView(R.id.btn_add)
    Button mBtnAdd;
    @BindView(R.id.search_page_flowlayout)
    com.zhy.view.flowlayout.TagFlowLayout search_page_flowlayout;
    //数据库相关
    MyAdapter myAdapter;
    private KeyWordDao mKeyWordDao;
    private String[] mVals = new String[]
            {"Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView", "TextView", "Helloworld"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_manger);
        ButterKnife.bind(this);
        mKeyWordDao = BaseApplication.getInstance().getDaoSession().getKeyWordDao();
        initToolBar();
        initView();
    }

    /**
     * 设置ToolBar属性
     */
    private void initToolBar() {
        mToolbar.setTitle("");
        mToolbarTitle.setText("添加关键字");
        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mToolbarTitle.setTextColor(getResources().getColor(R.color.white));
//        toolbarTitle.getPaint().setFakeBoldText(true);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    protected void initView() {
        final LayoutInflater mInflater = LayoutInflater.from(TagMangerActivity.this);
        search_page_flowlayout.setAdapter(new TagAdapter<String>(mVals) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.search_page_flowlayout_tv,
                        search_page_flowlayout, false);
                tv.setText(s);
                return tv;
            }
        });
        search_page_flowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Toast.makeText(TagMangerActivity.this, mVals[position], Toast.LENGTH_SHORT).show();
                //view.setVisibility(View.GONE);
                return true;
            }
        });
        search_page_flowlayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                TagMangerActivity.this.setTitle("choose:" + selectPosSet.toString());
            }
        });
        getData();
    }

    private void getData() {
        KeyWordDao keyWordDao = BaseApplication.getInstance().getDaoSession().getKeyWordDao();
        List<KeyWord> smsList = keyWordDao.loadAll();
        myAdapter = new MyAdapter(TagMangerActivity.this, smsList);
    }

    @OnClick(R.id.btn_add)
    public void onViewClicked() {
        Long id = SharePreferenceUtil.getInfoLong(BaseApplication.getContext(), SharePreferenceUtil.ID);
        KeyWordDao keyWordDao = BaseApplication.getInstance().getDaoSession().getKeyWordDao();
        KeyWord insertData = new KeyWord(id, mEtKeywords.getText().toString().trim());
        keyWordDao.insert(insertData);
    }

    class MyAdapter extends BaseAdapter {
        private Context context;
        private List<KeyWord> list;

        public MyAdapter(Context context, List<KeyWord> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            KeyWord tv = (KeyWord) getItem(i);
            ViewHolder viewHolder;
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.search_page_flowlayout_tv, null);
                viewHolder = new ViewHolder();
                viewHolder.flowlayout_tv = (TextView) view.findViewById(R.id.flowlayout_tv);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.flowlayout_tv.setText(tv.getKeyword());

            return view;
        }

        //创建ViewHolder类
        class ViewHolder {
            TextView flowlayout_tv;
        }
    }

}
