// https://stackoverflow.com/questions/68269286/not-able-to-load-com-github-smarteist-autoimageslider1-4-0
// https://youtube.com/watch?v=J1zCHTXjegI&feature=shares
package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class SlideShow extends Activity {
    ViewPager viewPager;
    ss_ImageAdapter imageAdapter;
    CircleIndicator circleIndicator;
    Intent intentCallMusicService;

    private  List<Photos> dataForSlideShow;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ss_slide_show);

        viewPager = findViewById(R.id.ss_viewpager);
        circleIndicator = findViewById(R.id.circle_indicator);

        dataForSlideShow = getImageList();
        imageAdapter = new ss_ImageAdapter(this, dataForSlideShow);
        viewPager.setAdapter(imageAdapter);

        circleIndicator.setViewPager(viewPager);
        imageAdapter.registerDataSetObserver(
                circleIndicator.getDataSetObserver()
        );

        intentCallMusicService = new Intent(
                this, MusicService.class
        );

        startService(intentCallMusicService);

        autoSlideImages();
    }

    private void autoSlideImages() {
        boolean isInvalidData = dataForSlideShow == null
                || dataForSlideShow.isEmpty()
                || viewPager == null;

        if (!isInvalidData)
        {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            int currentItem = viewPager.getCurrentItem();
                            int totalItem = dataForSlideShow.size();

                            currentItem = (currentItem + 1) % totalItem;
                            viewPager.setCurrentItem(currentItem);
                        }
                    });
                }
            }, 500, 3000);
        }
    }

    private List<Photos> getImageList() {
//        try
//        {
//            SlideShowData.clearList();
//        }
//        catch (Exception e)
//        {
//            SlideShowData.list = new ArrayList<Photos>();
//        }

//        SlideShowData.list.add(new ss_ImageData("new path", R.drawable.avatar01));
//        SlideShowData.list.add(new ss_ImageData("new path", R.drawable.avatar02));
//        SlideShowData.list.add(new ss_ImageData("new path", R.drawable.avatar03));
//        SlideShowData.list.add(new ss_ImageData("new path", R.drawable.avatar04));
//        SlideShowData.list.add(new ss_ImageData("new path", R.drawable.avatar05));
        return SlideShowData.list;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (timer != null)
        {
            timer.cancel();
            timer = null;
        }
    }

}