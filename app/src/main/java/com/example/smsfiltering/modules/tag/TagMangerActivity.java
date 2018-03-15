package com.example.smsfiltering.modules.tag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smsfiltering.R;
import com.heaven7.android.dragflowlayout.ClickToDeleteItemListenerImpl;
import com.heaven7.android.dragflowlayout.DragAdapter;
import com.heaven7.android.dragflowlayout.DragFlowLayout;
import com.heaven7.android.dragflowlayout.IDraggable;
import com.heaven7.android.dragflowlayout.IViewObserver;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TagMangerActivity extends AppCompatActivity {


    @BindView(R.id.drag_flowLayout)
    DragFlowLayout mDragflowLayout;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_manger);
        ButterKnife.bind(this);
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
        // mDragflowLayout.setLayoutTransition(TransitionProvider.createTransition(this));
        //长按item拖拽，如果要处理点击事件请调用。
        mDragflowLayout.setOnItemClickListener(new ClickToDeleteItemListenerImpl(R.id.iv_close) {
            @Override
            protected void onDeleteSuccess(DragFlowLayout dfl, View child, Object data) {
                //删除成功后的处理。
            }
        });
        mDragflowLayout.setDragAdapter(new DragAdapter<TestBean>() {
            @Override
            public int getItemLayoutId() {
                return R.layout.item_drag_flow;
            }

            @Override
            public void onBindData(View itemView, int dragState, TestBean data) {
                itemView.setTag(data);

                TextView tv = (TextView) itemView.findViewById(R.id.tv_text);
                tv.setText(data.text);
                //iv_close是关闭按钮。只有再非拖拽空闲的情况吓才显示
                itemView.findViewById(R.id.iv_close).setVisibility(
                        dragState != DragFlowLayout.DRAG_STATE_IDLE
                                && data.draggable ? View.VISIBLE : View.INVISIBLE);
            }

            @NonNull
            @Override
            public TestBean getData(View itemView) {
                return (TestBean) itemView.getTag();
            }
        });
        //预存指定个数的Item. 这些Item view会反复使用，避免重复创建, 默认10个
        mDragflowLayout.prepareItemsByCount(10);
        //设置拖拽状态监听器
        mDragflowLayout.setOnDragStateChangeListener(new DragFlowLayout.OnDragStateChangeListener() {
            @Override
            public void onDragStateChange(DragFlowLayout dfl, int dragState) {
                //  System.out.println("on drag state change : dragState = " + dragState);
            }
        });
        //添加view观察者
        mDragflowLayout.addViewObserver(new IViewObserver() {
            @Override
            public void onAddView(View child, int index) {
                // Logger.i(TAG, "onAddView", "index = " + index);
            }

            @Override
            public void onRemoveView(View child, int index) {
                // Logger.i(TAG, "onRemoveView", "index = " + index);
            }
        });
    }

    @OnClick(R.id.btn_add)
    public void onViewClicked() {

    }

//    @OnClick(R.id.bt_begin_drag)
//    public void onClickBeginDrag(View v) {
//        mDragflowLayout.beginDrag();
//    }
//
//    @OnClick(R.id.bt_done)
//    public void onClickDone(View v) {
//        //标记拖拽结束, 内部会自动将拖拽状态改为idle
//        mDragflowLayout.finishDrag();
//    }
//
//    @OnClick(R.id.bt_add)
//    public void onClickAdd(View v) {
//        final TestBean bean = new TestBean("test_" + (mIndex++) * caculateShift(mIndex) + "");
//        int index;
//        if (mDragflowLayout.getChildCount() == 0) {
//            //为了测试，设置第一个条目不准拖拽
//            bean.draggable = false;
//            index = 0;
//        } else {
//            index = 1;
//        }
//        mDragflowLayout.getDragItemManager().addItem(index, bean);
//    }

//    private int caculateShift(int src) {
//        return (int) Math.pow(10, src % 3 + 1);
//    }

//    @OnClick(R.id.bt_remove_center)
//    public void onClickRemoveCenter(View v) {
//        final DragFlowLayout.DragItemManager itemManager = mDragflowLayout.getDragItemManager();
//        final int count = itemManager.getItemCount();
//        if (count == 0) {
//            return;
//        }
//        if (count <= 2) {
//            itemManager.removeItem(count - 1);
//        } else {
//            itemManager.removeItem(count - 2);
//        }
//    }

    /**
     * 如果想禁止某些Item拖拽请实现 {@link IDraggable} 接口
     */
    private static class TestBean implements IDraggable {
        String text;
        boolean draggable = true;

        public TestBean(String text) {
            this.text = text;
        }

        @Override
        public boolean isDraggable() {
            return draggable;
        }

    }
}
