package com.dk.basepack.bseaapplication.weight;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.dk.basepack.bseaapplication.R;
import com.dk.basepack.bseaapplication.mode.FragmentBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Description : 底部导航栏组件
 * Created by Administrator on 2017/9/14.
 */

public class CustomizedBottomNavigationBar extends LinearLayout implements RadioGroup.OnCheckedChangeListener {
    private final  String  tag="CustomizedBottomNavigationBar";
    private Context context;
    private static List<FragmentBean>  fragments;
    private FragmentManager fm;
    private FragmentTransaction transaction;
    private int replace_layout;
    private RadioGroup radioGroup;
    private   boolean  add_Tag=false;

    //第一次可见fragment  标记
    private List<String>  firstVisibles=new ArrayList<>();

    //指定fragment 切换模式  替换模式
    public    final  static  int   CHANGE_FRAGMENT_REPLACE_MODE=0;

    //低耗模式  show 或者 hidden 模式
    public    final  static  int   CHANGE_FRAGMENT_REPLACE_SHOW_HIDDN=1;
    private int mode=-1;
    private boolean isShowFade=true;
    private DragBubbleView bubbleView_tab2;
    public CustomizedBottomNavigationBar(Context context) {
        this(context,null);
    }

    public CustomizedBottomNavigationBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomizedBottomNavigationBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context= context;

        //这样写就是把布局贴给了CustomizedBottomNavigationBar
        View view = View.inflate(context, R.layout.customized_bottom_navigation_bar, this);

        findViewById();
    }

    /**
     * 初始化控件
     *
     */
    private void findViewById() {
        radioGroup = (RadioGroup) findViewById(R.id.bottomnavigationbar_radiogroup);
        radioGroup.setOnCheckedChangeListener(this);

        bubbleView_tab2 = (DragBubbleView) findViewById(R.id.dragBubbleView_tab2);
        bubbleView_tab2.setOnBubbleStateListener(bubbleStateListener_tab2);
    }




    DragBubbleView.OnBubbleStateListener  bubbleStateListener_tab2= new DragBubbleView.OnBubbleStateListener() {
        @Override
        public void onDrag() {
//            Log.e("---> ", "拖拽气泡");
        }

        @Override
        public void onMove() {
//            Log.e("---> ", "移动气泡");
        }

        @Override
        public void onRestore() {
//            Log.e("---> ", "气泡恢复原来位置");
        }

        @Override
        public void onDismiss() {
//            Log.e("---> ", "气泡消失");
            bubbleView_tab2.postDelayed(new Runnable() {
                @Override
                public void run() {
                    bubbleView_tab2.reCreate();
                }
            },700);

        }
    };

    /**
     *
     * @param fragments  要跳转的fragment集合
     * @param manager       FragmentManager
     * @param replace_layout         替换布局
     */
    public   void  setChangedFragmentLists(List<FragmentBean> fragments, FragmentManager manager, int replace_layout)
    {
        //开启事物为替换做准备
        this.fm=manager;
        this.replace_layout=replace_layout;

        this.fragments=fragments;

        //设置默认选择
        seChecked(0);
    }

    /**
     *
     * @param fragments  要跳转的fragment集合
     * @param manager       FragmentManager
     */
    public   void  setChangedFragmentLists(List<FragmentBean> fragments, FragmentManager manager)
    {
        setChangedFragmentLists(fragments,manager,0);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        bubbleView_tab2.setIsMoveFalg(true);
        if (mode==-1)
        {
            throw  new NullPointerException("onCheckedChanged"+":"+"切换 模式的缺省值 mode  等于  ---->"+mode+"无效");
        }
        if (fragments==null) return;

        RadioButton radioButton= (RadioButton) group.findViewById(checkedId);
        FragmentBean fragment=getFragmentBean((String) radioButton.getTag());
        Fragment fg =  fragment.getFragment();
        if ( fragment.getFragment()==null)
        {
            throw   new NullPointerException(fragment.getFragmentTag()+":"+"切换 fragment  等于  null ---->"+tag);
        }


        switch (mode)
        {
            case  CHANGE_FRAGMENT_REPLACE_MODE:
                changedReplaceFragmentMode(fg,fragment);
                break;
            case CHANGE_FRAGMENT_REPLACE_SHOW_HIDDN:
                changedFragmentHiddeORShowMode(fg,fragment);
                break;
            default:
                break;
        }
    }


    /**
     * 第一次显示fragment 的时候  显示淡入淡出效果
     * @param fragment
     */
    private void    firstVisibleFragment(FragmentBean fragment)
    {
        if (!isShowFade) return;

        if (!firstVisibles.contains(fragment.getFragmentTag()))
        {
            transaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
            firstVisibles.add(fragment.getFragmentTag());
        }
    }


    /**
     * 获取当前将要切换fragment信息
     * @param tag
     * @return
     */
    private FragmentBean getFragmentBean(String  tag){
        for (FragmentBean fragmentBean : fragments) {
            if (tag.equals(fragmentBean.getFragmentTag()))
            {
                return fragmentBean;
            }
        }
        return null;
    }


    /**
     * 是否要关闭掉第一次显示fragment的时候的 淡入淡出效果
     * @param isShowFade   默认开启状态
     */
    public  void  firstFragmentShowFade(boolean  isShowFade)
    {
        this.isShowFade=isShowFade;
    }


    /**
     * 选中那个
     * @param index
     */
    public  void  seChecked(int  index)
    {
        ((RadioButton)radioGroup.getChildAt(index)).setChecked(true);
    }


    /**
     * fragment 的切换模式，目前有两种
     * 替换模式  or    show  hidden 模式
     *
     * 必须在  setChangedFragmentLists（）  之前调用
     * @param mode
     */
    public   void  setFragmentChangeMode(int  mode)
    {
        this.mode=mode;
    }


    /**________________________________________________________________________________________*/

    /**
     * 切换fragment  替换模式 replace方法替换
     * @param fg
     * @param fragment
     */
    private void   changedReplaceFragmentMode(Fragment fg, FragmentBean fragment)
    {
        transaction = fm.beginTransaction();

        firstVisibleFragment(fragment);

        transaction.replace(replace_layout,fg);
        transaction.commit();

    }

    /**________________________________________________________________________________________*/





    /*************************************************************************************************/

    /**
     *   show   或者 hidden  模式
     * @param fg
     * @param fragment
     */
    private void changedFragmentHiddeORShowMode(Fragment fg, FragmentBean fragment) {

        //每次要创建一个新的事务
        transaction = fm.beginTransaction();

        //将每一个fragment添加到  事务中
        if (!add_Tag)  addFragments();

        //隐藏没有隐藏的fragment
        hideFragments(transaction);

        firstVisibleFragment(fragment);
        //show   fragment
        transaction.show(fg);

        //提交事务
        transaction.commitAllowingStateLoss();
    }


    /**
     * 循环隐藏所有的fragment
     * @param transaction
     */
    private void hideFragments(FragmentTransaction transaction) {
        for (FragmentBean fragmentBean : fragments) {
            if (fragmentBean!=null&&fragmentBean.getFragment()!=null)
            {
                boolean ishidden= fragmentBean.getFragment().isHidden();

                if (!ishidden) transaction.hide(fragmentBean.getFragment());
            }
        }
    }


    /**
     * 添加所有的fragment
     */
    private void addFragments() {
        for (FragmentBean fragmentBean : fragments) {
            if (fragmentBean!=null&&fragmentBean.getFragment()!=null)
            {
                //setRetainInstance(true),这样旋转时 Fragment 不需要重新创建,还是之前的fragment
                fragmentBean.getFragment().setRetainInstance(true);
                transaction.add(replace_layout, fragmentBean.getFragment());
            }
        }

        add_Tag=true;
    }
    /*************************************************************************************************/
}
