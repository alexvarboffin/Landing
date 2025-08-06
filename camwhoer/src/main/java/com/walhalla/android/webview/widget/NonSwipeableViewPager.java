//package com.walhalla.android.webview.widget;
//
//import android.content.Context;
//
//import android.util.AttributeSet;
//
//import androidx.viewpager.widget.ViewPager;
//import androidx.viewpager2.widget.ViewPager2;
//
//public class NonSwipeableViewPager extends ViewPager2 {
//
//    public NonSwipeableViewPager(Context context) {
//        super(context);
//        setMyScroller();
//    }
//
//    public NonSwipeableViewPager(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        setMyScroller();
//    }
//
////    @Override
////    public boolean onInterceptTouchEvent(MotionEvent event) {
////        // Never allow swiping to switch between pages
////        return false;
////    }
////
////    @Override
////    public boolean onTouchEvent(MotionEvent event) {
////        // Never allow swiping to switch between pages
////        return false;
////    }
////
////    //down one is added for smooth scrolling
////
//    private void setMyScroller() {
////        try {
////            Class<?> viewpager = ViewPager.class;
////            Field scroller = viewpager.getDeclaredField("mScroller");
////            scroller.setAccessible(true);
////            scroller.set(this, new MyScroller(getContext()));
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//    }
//
////    public class MyScroller extends Scroller {
////        MyScroller(Context context) {
////            super(context, new DecelerateInterpolator());
////        }
////
////        @Override
////        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
////            super.startScroll(startX, startY, dx, dy, 350 /*1 secs*/);
////        }
////    }
//}
