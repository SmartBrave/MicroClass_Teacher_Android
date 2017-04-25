package com.example.smart.microclass;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.smart.microclass.Course.CourseFragment;
import com.example.smart.microclass.FaQ.FaQFragmeng;
import com.example.smart.microclass.HomePage.HomePageFragment;
import com.example.smart.microclass.Mine.MineFragment;
import com.example.smart.microclass.OtherClass.HTTP.HttpUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static com.example.smart.microclass.R.id.viewpager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private HomePageFragment homePageFragment;
    private CourseFragment courseFragment;
    private FaQFragmeng faQFragmeng;
    private MineFragment mineFragment;
    private ViewPager viewPager;
    private LinearLayout homepage;
    private LinearLayout course;
    private LinearLayout faq;
    private LinearLayout mine;
    private ImageButton homepage_img;
    private ImageButton course_img;
    private ImageButton faq_img;
    private ImageButton mine_img;

    public HttpUtil http=new HttpUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //System.out.println("onCreate is called!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!io.vov.vitamio.LibsChecker.checkVitamioLibs(this))
            return;

        setOverflowShowingAlways();



        List<Fragment> fragments=new ArrayList<Fragment>();
        homePageFragment=new HomePageFragment();
        courseFragment=new CourseFragment();
        faQFragmeng=new FaQFragmeng();
        mineFragment=new MineFragment();
        fragments.add(homePageFragment);
        fragments.add(courseFragment);
        fragments.add(faQFragmeng);
        fragments.add(mineFragment);
        viewPager=(ViewPager)findViewById(viewpager);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),fragments));
        //the next code's efficiency is same to function instantiateItem and destroyItem which in class Myadapter,
        //choose once is ok
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setImagePressed(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        homepage=(LinearLayout)findViewById(R.id.homepage);
        course=(LinearLayout)findViewById(R.id.course);
        faq=(LinearLayout)findViewById(R.id.faq);
        mine=(LinearLayout)findViewById(R.id.mine);
        homepage_img=(ImageButton)findViewById(R.id.homepage_img);
        course_img=(ImageButton)findViewById(R.id.course_img);
        faq_img=(ImageButton)findViewById(R.id.faq_img);
        mine_img=(ImageButton)findViewById(R.id.mine_img);

        homepage.setOnClickListener(this);
        course.setOnClickListener(this);
        faq.setOnClickListener(this);
        mine.setOnClickListener(this);

        //System.out.println("onCreate is end!");

    }

    public class MyPagerAdapter extends FragmentPagerAdapter{

        private FragmentManager fm;
        private List<Fragment> mfragments;

        public MyPagerAdapter(FragmentManager fm,List<Fragment> fragments){
            super(fm);
            this.fm=fm;
            this.mfragments=fragments;
            //System.out.println("MyPagerAdapter is called! fm is: "+fm);
            //System.out.println("MyPagerAdapter is end!");
        }

        @Override
        public int getCount() {
            //System.out.println("getCount is called!");
            //System.out.println("getCount is end!");
            return mfragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            //System.out.println("getItem is called! position is: "+position);
            //System.out.println("getItem is end!");
            return mfragments.get(position);
        }

        //@Override
        //public Object instantiateItem(ViewGroup container, int position) {
        //    Fragment fragment=(Fragment)super.instantiateItem(container,position);
        //    fm.beginTransaction().show(fragment).commit();
        //    return fragment;
        //}

        //@Override
        //public void destroyItem(ViewGroup container, int position, Object object) {
        //    //super.destroyItem(container, position, object);
        //    Fragment fragment=mfragments.get(position);
        //    fm.beginTransaction().hide(fragment).commit();
        //}
    }
    public void setImagePressed(int position){
        //System.out.println("setImagePressed is called! position is: "+position);
        resetImg();
        switch(position){
            case 0:
                homepage_img.setImageResource(R.drawable.tab_homepage_pressed);
                break;
            case 1:
                course_img.setImageResource(R.drawable.tab_course_pressed);
                break;
            case 2:
                faq_img.setImageResource(R.drawable.tab_faq_pressed);
                break;
            case 3:
                mine_img.setImageResource(R.drawable.tab_mine_pressed);
                break;
            default:
                break;
        }
        //System.out.println("setImagePressed is end!");
    }

    @Override
    public void onClick(View v) {
        //System.out.println("onClick is called! v is: "+v+"v.getId() is: "+v.getId());
        switch(v.getId()){
            case R.id.homepage:
            case R.id.homepage_img:
                viewPager.setCurrentItem(0);
                setImagePressed(0);
                //resetImg();
                //homepage_img.setImageResource(R.drawable.tab_homepage_pressed);
                break;
            case R.id.course:
            case R.id.course_img:
                viewPager.setCurrentItem(1);
                setImagePressed(1);
                //resetImg();
                //course_img.setImageResource(R.drawable.tab_course_pressed);
                break;
            case R.id.faq:
            case R.id.faq_img:
                viewPager.setCurrentItem(2);
                setImagePressed(2);
                //resetImg();
                //faq_img.setImageResource(R.drawable.tab_faq_pressed);
                break;
            case R.id.mine:
            case R.id.mine_img:
                viewPager.setCurrentItem(3);
                setImagePressed(3);
                //resetImg();
                //mine_img.setImageResource(R.drawable.tab_mine_pressed);
                break;
            default:
                break;
        }
        //System.out.println("onClick is end!");
    }

    private void resetImg(){
        //System.out.println("resetImg is called!");
        homepage_img.setImageResource(R.drawable.tab_homepage_normal);
        course_img.setImageResource(R.drawable.tab_course_normal);
        faq_img.setImageResource(R.drawable.tab_faq_normal);
        mine_img.setImageResource(R.drawable.tab_mine_normal);
        //System.out.println("resetImg is end!");
    }

    private void setOverflowShowingAlways(){
        //System.out.println("setOverflowShowintAlways is called!");
        try{
            ViewConfiguration config=ViewConfiguration.get(this);
            Field menuKeyField=ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config,false);
        }catch(Exception e){
            e.printStackTrace();
        }
        //System.out.println("setOverflowShowintAlways is end!");
    }

    //is called when the overflow is opened,amied to show the img with items
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        //System.out.println("onMenuOpened is called!");
        if(featureId== Window.FEATURE_ACTION_BAR && menu!=null){
            if(menu.getClass().getSimpleName().equals("MenuBuilder")){
                try{
                    Method m=menu.getClass().getDeclaredMethod("setOptionIconsVisible",Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu,true);
                }catch(Exception e){
                }
            }
        }
        //System.out.println("onMenuOpened is end!");
        return super.onMenuOpened(featureId, menu);
    }

    //is called whem the menu is created
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //System.out.println("onCreateOptionMenu is called!");
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        MenuItem searchItem=menu.findItem(R.id.search);
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Toast.makeText(MainActivity.this,"onMenuItemActionExpand",Toast.LENGTH_LONG).show();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Toast.makeText(MainActivity.this,"onMenuItemActionCollapse",Toast.LENGTH_LONG).show();
                return true;
            }
        });
        //System.out.println("onCreateOptionMenu is end!");
        return super.onCreateOptionsMenu(menu);
    }

    //is called when a item who's in menu is selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //System.out.println("onOptionsItemSelected is called!");
        switch(item.getItemId()){
            case R.id.item1:
                Toast.makeText(this,"item1",Toast.LENGTH_SHORT).show();
                //System.out.println("onOptionsItemSelected is end!");
                return true;
            case R.id.item2:
                Toast.makeText(this,"item2",Toast.LENGTH_SHORT).show();
                //System.out.println("onOptionsItemSelected is end!");
                return true;
            case R.id.item3:
                Toast.makeText(this,"item3",Toast.LENGTH_SHORT).show();
                //System.out.println("onOptionsItemSelected is end!");
                return true;
            default:
                //System.out.println("onOptionsItemSelected is end!");
                return super.onOptionsItemSelected(item);
        }
    }
}
