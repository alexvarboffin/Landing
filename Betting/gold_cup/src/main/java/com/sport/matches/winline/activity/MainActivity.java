package com.sport.matches.winline.activity;

import android.annotation.SuppressLint;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import android.content.pm.ActivityInfo;
import android.os.Build;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.footballdeluxe.goldсup.R;
import com.sport.matches.winline.app.Config;


import com.sport.matches.winline.fragment.ClubDescriptionFragment;
import com.sport.matches.winline.fragment.ClubsListFragment;
import com.sport.matches.winline.fragment.SeasonDescriptionFragment;
import com.sport.matches.winline.fragment.SimpleClubViewModel;
import com.sport.matches.winline.fragment.base.CoreFragment;
import com.sport.matches.winline.fragment.SeasonsListFragment;
import com.sport.matches.winline.model.ButtonViewModel;
import com.sport.matches.winline.model.HeaderObject;
import com.sport.matches.winline.model.SeasonViewModel;
import com.sport.matches.winline.model.ViewModel;
import com.walhalla.plugins.Launcher;
import com.walhalla.ui.DLog;
import com.walhalla.plugins.Module_U;

import org.apache.cordova.CompatActivity;

import org.apache.cordova.Gfg;
import org.apache.cordova.domen.BodyClass;
import org.apache.cordova.domen.Dataset;
import org.apache.cordova.domen.ScreenType;
import org.apache.cordova.domen.UIVisibleDataset;
import org.apache.cordova.enumer.UrlSaver;
import org.apache.cordova.repository.AbstractDatasetRepository;
import org.apache.cordova.repository.impl.FirebaseRepository;
import org.apache.mvp.presenter.DeviceCheck;

import java.util.Calendar;


public class MainActivity extends CompatActivity
        implements CoreFragment.Callback {


    private Menu menu;
    private Button seasons;

    @Override
    public AbstractDatasetRepository makeTracker() {
        //return new KwkRemoteRepository(new Handler(), "https://galyaunasotmena.xyz/", this, this);
        //return new KwkRemoteRepository(new Handler(), "https://maxbetdeluxejack-6532e.web.app/demo.txt", this, this);
        //return new ExFirebaseRepository(new Handler(), this, this);
        FirebaseRepository aa = new FirebaseRepository(
                this);
//        aa.setCallback(new DatasetRepository.RepoCallback() {
//            @Override
//            public void successResponse(Dataset value) {
//
//            }
//
//            @Override
//            public void handleError(String message) {
//
//            }
//        });
        return aa;
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        this.menu = menu;
//        MenuItem item1 = menu.add(
//                Menu.NONE, // groupId
//                899, // itemId
//                2, // order
//                "" // title ТЕСТ_WEB_VIEW
//        );
//        //item1.setIcon(R.drawable.ic_archive);
//        item1.setShowAsActionFlags(
//                MenuItem.SHOW_AS_ACTION_WITH_TEXT |
//                        MenuItem.SHOW_AS_ACTION_ALWAYS
//        );
//        item1.setOnMenuItemClickListener(item -> {
//            //mWebView.loadUrl(Config.success_url);
//            return false;
//        });


        super.onPrepareOptionsMenu(menu);
        return true;
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        if (this.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
//            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        }
        //setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        //this.getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        //setContentView(R.layout.activity_main);


        final ActionBar ab = getSupportActionBar();
//ab.setHomeAsUpIndicator(R.drawable.ic_menu);
//        ab.setDisplayHomeAsUpEnabled(true);
//        ab.setDisplayShowHomeEnabled(true);
//        ab.setDisplayShowTitleEnabled(false);


//        ConnectivityManager connectivityManager = (ConnectivityManager)
//                /*c.*/getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (connectivityManager != null)
//
//        {
//            NetworkInfo ni = connectivityManager.getActiveNetworkInfo();
//            if (ni.getState() != NetworkInfo.State.CONNECTED) {
//                // record the fact that there is not connection
//                isConnected = false;
//            }
//        }


//        StringBuffer sb = new StringBuffer();
//        sb.append("&app=").append(getPackageName());
//        sb.toString();//sig

        //Google AOSP on IA Emulator


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                        channelName, NotificationManager.IMPORTANCE_LOW));
            }
        }

        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                //Log.d("@@@", "Key: " + key + " Value: " + value);
            }
        }
// [END handle_data_extras]

//        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        //Sub
//        FirebaseMessaging.getInstance().subscribeToTopic("weather")
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        String msg = getString(R.string.msg_subscribed);
//                        if (!task.isSuccessful()) {
//                            msg = getString(R.string.msg_subscribe_failed);
//                        }
//                        Log.d(TAG, msg);
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
//                    }
//                });


        //Get token
//        FirebaseInstanceId.getInstance().getInstanceId()
//                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                        if (!task.isSuccessful()) {
//                            Log.w(TAG, "getInstanceId failed", task.getException());
//                            return;
//                        }
//
//                        // Get new Instance ID token
//                        String token = task.getResult().getToken();
//
//                        // Log and toast
//                        Log.d(TAG, "token: " + token);
//                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
//                    }
//                });
        //Log.d(TAG, "Connected : " + Utils.isNetworkConnected(this));

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            requestSelectFileLauncher = this.registerForActivityResult(
//                    new ActivityResultContracts.StartActivityForResult(),
//                    result -> {
//                        Intent data = result.getData();
//                        if (data != null) {
//                            Uri uri = data.getData();
//                        }
//                        if (uploadMessage == null) return;
//                        uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(result.getResultCode(), data));
//                        uploadMessage = null;
//                    });
//        } else {
//            requestSelectFileLauncher = this.registerForActivityResult(
//                    new ActivityResultContracts.StartActivityForResult(),
//                    result -> {
//                        Intent data = result.getData();
//                        if (data != null) {
//                            Uri uri = data.getData();
//                        }
//                        if (null == mUploadMessage) return;
//                        Uri result2 = data == null || result.getResultCode() != RESULT_OK ? null : data.getData();
//                        mUploadMessage.onReceiveValue(result2);
//                        mUploadMessage = null;
//                    });
//        }


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new SeasonsListFragment())
                    .commit();
        }

        Button clubs = findViewById(R.id.clubs);
        seasons = findViewById(R.id.seasons);
        clubs.setOnClickListener(v -> replaceFragment(
                ClubsListFragment.newInstance(0)));
        seasons.setOnClickListener(v ->
                replaceFragment(new SeasonsListFragment()));
    }

    public void loadErrorPage(WebView webview) {
        if (webview != null) {

            String htmlData = "<html><body><div align=\"center\" >This is the description for the load fail : "
                    //+ description + "\nThe failed url is : " + failingUrl
                    + "\n </div ></body > ";

            webview.loadUrl("about:blank");
            webview.loadDataWithBaseURL(null, htmlData, "text/html", "UTF-8", null);
            webview.invalidate();

        }
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify activity parent activity in AndroidManifest.xml.
//        noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
//            case R.id.action_about:
//                AboutBox.Show(MainActivity.this);
//                return true;

            case R.id.action_refresh:
                actionRefresh();
                return true;


            case R.id.action_about:
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                //&#169; - html
                String title = "\u00a9 " + year + " " + getString(R.string.play_google_pub);

                View mView = LayoutInflater.from(this).inflate(R.layout.about, null);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle(null)
                        .setCancelable(true)
                        .setIcon(null)
                        //.setPositiveButton(button, null)
                        .setView(mView)
                        .create();
                mView.setOnClickListener(v -> dialog.dismiss());
                TextView textView = mView.findViewById(R.id.about_version);
                textView.setText(DLog.getAppVersion(this));
                ((TextView) mView.findViewById(R.id.about_copyright)).setText(title);
                dialog.show();
                return true;

            case R.id.action_privacy_policy:
                Launcher.openBrowser(this, Config.PRIVACY_POLICY);
                return true;

            case R.id.action_exit:
                this.finish();
                return true;

//            case R.id.action_testers:
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
//                if (prev != null) {
//                    ft.remove(prev);
//                }
//                ft.addToBackStack(null);
//
//                // Create and show the dialog.
//                DialogFragment newFragment = new TestersDialogFragment();
//                newFragment.show(ft, "dialog");
//                return true;


//            case R.id.action_settings:
//                startActivityForResult(ScoopSettingsActivity.createIntent(this), RC_CHANGE_THEME);
//                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void makeScreen(UIVisibleDataset screen) {
        super.makeScreen(screen);

        MenuItem item = null;
        if (menu != null) {
            item = menu.findItem(R.id.action_refresh);
        }
        if (screen.getScreenType() != ScreenType.WEB_VIEW) {
            //aaa.setVisibility(View.VISIBLE);//privacy.setVisibility(View.VISIBLE);
            if (item != null) {
                item.setVisible(false);
            }
        } else {
            //surfaceView1.setVisibility(View.GONE);
            if (item != null) {
                item.setVisible(true);
            }//privacy.setVisibility(View.GONE);
        }
    }


    @Override
    protected Gfg GCfc() {
        return new Gfg.Builder().build()
//                true,
//                false,
//                UrlSaver.FIRST, true, true)
                ;
    }


    @Override
    public UIVisibleDataset data() {
        return null; //firebase dataset
    }

    @Override
    public DeviceCheck checkDevice() {
        return null;
    }

    @Override
    public Integer orientation404() {
        return ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
    }

    @Override
    public Integer orientationWeb() {
        return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }


    @Override
    public boolean webTitle() {
        return false;
    }

    @Override
    public boolean handleDeepLink() {
        return false;
    }

    @Override
    public void PEREHOD_S_DEEPLINKOM() {

    }


    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    //SimpleModel
    @Override
    public void onClick(ViewModel navItem) {
        DLog.d("@@@@" + navItem.getClass().getSimpleName());


        if (navItem instanceof HeaderObject) {
            Toast.makeText(this, "####", Toast.LENGTH_SHORT).show();
            //replaceFragment(ClubsListFragment.newInstance(((HeaderObject) navItem).title));
            //replaceFragment(SeasonDescriptionFragment.newInstance(getString(((HeaderObject) navItem).title)));
        } else if (navItem instanceof SeasonViewModel) {
            //replaceFragment(ClubsListFragment.newInstance(((HeaderObject) navItem).title));
            replaceFragment(SeasonDescriptionFragment.newInstance(((SeasonViewModel) navItem)));
        } else if (navItem instanceof SimpleClubViewModel) {
            //replaceFragment(ClubsListFragment.newInstance(((HeaderObject) navItem).title));
            replaceFragment(ClubDescriptionFragment.newInstance(((SimpleClubViewModel) navItem)));

        } else if (navItem instanceof ButtonViewModel) {
            ButtonViewModel aa = (ButtonViewModel) navItem;
            DLog.d("[URL]" + aa.url);
            Launcher.openBrowser(this, aa.url);
        } else {
            DLog.d("@@@@" + navItem.getClass().getSimpleName());
        }
    }

    @Override
    public void showToolbar(boolean visible) {

    }


    @Override
    public void onPageStarted(String url) {

    }

    

    @Override
    public void mAcceptPressed(String url) {

    }

    @Override
    public void eventRequest(BodyClass bodyClass) {

    }

    @Override
    public void setErrorPage(ReceivedError receivedError) {
        switchViews(true);
    }

    @Override
    public void removeErrorPage() {
        switchViews(false);
    }
}