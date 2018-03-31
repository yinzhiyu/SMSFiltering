package com.example.smsfiltering.modules.tag;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smsfiltering.R;
import com.example.smsfiltering.base.BaseApplication;
import com.example.smsfiltering.greendao.KeyWordDao;
import com.example.smsfiltering.greendao.PhoneDao;
import com.example.smsfiltering.table.KeyWord;
import com.example.smsfiltering.utils.SharePreferenceUtil;
import com.example.smsfiltering.utils.SnackbarUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 关键字管理
 */
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
    TagFlowLayout search_page_flowlayout;
    //数据库相关
    MyAdapter myAdapter;
    @BindView(R.id.parent)
    LinearLayout mParent;

    private List<KeyWord> smsList;
    private  KeyWordDao keyWordDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_manger);
        ButterKnife.bind(this);
        initToolBar();
        getData();
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

    protected void initView(final List<KeyWord> mVals) {
        final LayoutInflater mInflater = LayoutInflater.from(TagMangerActivity.this);
        search_page_flowlayout.setAdapter(new TagAdapter(mVals) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                TextView tv = (TextView) mInflater.inflate(R.layout.search_page_flowlayout_tv,
                        search_page_flowlayout, false);
                tv.setText(mVals.get(position).getKeyword());
                return tv;
            }
        });
        search_page_flowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Toast.makeText(TagMangerActivity.this, "fff"+mVals.get(position).getKeyword(), Toast.LENGTH_SHORT).show();
                //view.setVisibility(View.GONE);
                KeyWord keyWord = smsList.get(position);
                keyWordDao.delete(keyWord);
                return true;
            }
        });
        search_page_flowlayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                TagMangerActivity.this.setTitle("choose:" + selectPosSet.toString());
            }
        });
    }

    private void getData() {
        keyWordDao = BaseApplication.getInstance().getDaoSession().getKeyWordDao();
        smsList = keyWordDao.loadAll();
        initView(smsList);
//        myAdapter = new MyAdapter(TagMangerActivity.this, smsList);
    }

    @OnClick(R.id.btn_add)
    public void onViewClicked() {
        Long id = SharePreferenceUtil.getInfoLong(BaseApplication.getContext(), SharePreferenceUtil.ID);
        KeyWordDao keyWordDao = BaseApplication.getInstance().getDaoSession().getKeyWordDao();
        String keyword = mEtKeywords.getText().toString().trim();
        if (!TextUtils.isEmpty(keyword)) {
            KeyWord insertData = new KeyWord(id, keyword);
            keyWordDao.insert(insertData);
//            myAdapter = new MyAdapter(TagMangerActivity.this, smsList);
            getData();
        } else {
            SnackbarUtil.showLongSnackbar(mParent, "请填写关键字...", SnackbarUtil.WHITE, SnackbarUtil.ORANGE);
        }
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
