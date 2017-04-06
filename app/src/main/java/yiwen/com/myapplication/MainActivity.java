package yiwen.com.myapplication;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //收到通知
    private Button mBtn_receive_notice;
    //发送通知
    private Button mBtn_send_notice;
    //下划线
    private View mFollow_task_indicate_line;
    //填充数据
    private ViewPager mVp_notice;
    /*碎片集合*/
    private List<Fragment> fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化组件
        initView();
        //加载碎片
        initFragment();
        //点击事件监听
        initListener();

    }


    /**
     * 初始化
     */
    private void initView() {
        mBtn_receive_notice = (Button) findViewById(R.id.btn_receive_notice);
        mBtn_send_notice = (Button) findViewById(R.id.btn_send_notice);
        mFollow_task_indicate_line = (View) findViewById(R.id.follow_task_indicate_line);
        mVp_notice = (ViewPager) findViewById(R.id.vp_notice);


    }

    /**
     * 点击事件
     */
    private void initListener() {
        mBtn_receive_notice.setOnClickListener(this);
        mBtn_send_notice.setOnClickListener(this);
        //设置滑动监听
        mVp_notice.setOnPageChangeListener(new OnMainPageChangeListener());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_receive_notice:
                mVp_notice.setCurrentItem(0);
                break;
            case R.id.btn_send_notice:
                mVp_notice.setCurrentItem(1);
                break;
        }
    }
    /**
     * 记载碎片数据
     */
    protected void initFragment() {
        /*详情界面的两个功能  填充效果*/
        fragments = new ArrayList<Fragment>();
        fragments.add(new Fragment1());
        fragments.add(new Fragment2());
        MyPageAdapter myPageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
        mVp_notice.setAdapter(myPageAdapter);
        // 高亮第一个标签
        updateTabs(0);
        // 初始化指示器宽度
        int screenW = getWindowManager().getDefaultDisplay().getWidth();
        mFollow_task_indicate_line.getLayoutParams().width = screenW / 2;
        mFollow_task_indicate_line.requestLayout(); // 重新计算大小，并刷新控件
    }

    /**
     * 高亮position选中页面对应的标签，并将其他的变暗
     */
    private void updateTabs(int position) {
        updateTab(position, 0, mBtn_receive_notice);
        updateTab(position, 1, mBtn_send_notice);
    }

    /**
     * 判断当前要处理的 tabPosition 是否是选中的 position，并修改tab的高亮状态
     */
    private void updateTab(int position, int tabPosition, TextView tab) {
        int blue = getResources().getColor(R.color.blue);
        int halfWhite = getResources().getColor(R.color.font3);
        if (position == tabPosition) {
            // tab对应的页面被选中
            tab.setTextColor(blue);
        } else {
            // tab对应的页面没有被选中
            tab.setTextColor(halfWhite);
        }
    }
    public class MyPageAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments;

        public MyPageAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    private class OnMainPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        /** 当touch事件发生时回调此方法 */
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // 偏移位置 = 手指划过屏幕的百分比 * 指示器宽度
            int offsetX = (int) (positionOffset * mFollow_task_indicate_line.getWidth());
            // 起始位置 = position * 指示器宽度
            int startX = position * mFollow_task_indicate_line.getWidth();
            // 指示器移动的位置 = 起始位置 + 偏移位置
            int translationX = startX + offsetX;
            ViewHelper.setTranslationX(mFollow_task_indicate_line, translationX);
        }

        @Override
        /** 当见面选中状态发生变更时会回调此方法 */
        public void onPageSelected(int position) {
            // 高亮选中页面对应的标签，并将其他的变暗
            updateTabs(position);
        }

        @Override
        /** 当页面的滑动状态发生变更会回调此方法 */
        public void onPageScrollStateChanged(int state) {

        }
    }
}
