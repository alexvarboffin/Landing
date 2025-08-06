package com.topzaymnakartu.online24.android.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import androidx.annotation.Nullable;

import com.topzaymnakartu.online24.android.R;
import com.walhalla.landing.databinding.IncChildVwBinding;


public class CustomView extends RelativeLayout {

    private IncChildVwBinding binding;

    public CustomView(Context context) {
        super(context);
        init(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        // Inflate layout with ViewBinding
        binding = IncChildVwBinding.inflate(LayoutInflater.from(context), this, true);

        // Access the views using binding
        binding.mainCloseButton.setOnClickListener(v -> {
            closeChild();
        });

        //binding.mainTitleText.setText("Your Title Here");
        // You can further manipulate the views as needed
        //binding.mainBrowserLayout.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
    }

    public void setTitle(String title) {
        binding.mainTitleText.setText(title);
    }


    public void closeChild() {
        Log.v("@@@", "Closing Child WebView");
        Animation slideDown = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down);
        binding.mainAdChildLayout.startAnimation(slideDown);
        slideDown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.mainTitleText.setText("");
                binding.mainAdChildLayout.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public RelativeLayout mainAdChildLayout() {
        return binding.mainAdChildLayout;
    }

    public RelativeLayout mainBrowserLayout() {
        return binding.mainBrowserLayout;
    }
}
