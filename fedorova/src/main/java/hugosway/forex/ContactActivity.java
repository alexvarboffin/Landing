package hugosway.forex;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.walhalla.ui.DLog;
import com.walhalla.ui.plugins.Launcher;

public class ContactActivity extends AppCompatActivity
        implements View.OnClickListener {


    private static final String TAG = "@@@";
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 3400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.nav_contact));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        for (int i = 0; i < Config.contacts.size(); i++) {

            final int key = Config.contacts.keyAt(i);
            final int value = Config.contacts.get(key);

            ViewGroup viewGroup = findViewById(key);
            if ("@0".equals(getString(value))) {
                viewGroup.setVisibility(View.GONE);
            } else {
                ViewGroup viewGroup1 = (ViewGroup) viewGroup.getChildAt(1);
                View child = viewGroup1.getChildAt(1);
                if (child instanceof TextView) {
                    TextView textView = (TextView) child;
                    SpannableString content = new SpannableString(getString(value));
                    content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                    textView.setText(content);
                }
            }
        }

    }

    public void openEmail(View view) {
        TextView textView = (TextView) view;

        Intent emailIntent;
        emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + textView.getText().toString()));

        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        } else {

        }
    }

    public void callNumber(View view) {
        TextView textView = (TextView) view;
        String number = textView.getText().toString();
        number = number.trim()
                .replace(" ", "")
                .replace("-", "")
                .replace(")", "")
                .replace("(", "");

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + number));//change the number


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);

            // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        } else {
            //You already have permission
            try {
                startActivity(callIntent);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this,
                        "Permission was granted, yay! Do the phone call", Toast.LENGTH_SHORT).show();

            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
            return;

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onClick(View v) {
        if (v instanceof TextView) {
            TextView textView = (TextView) v;
            Launcher.openBrowser(this, textView.getText().toString());
        } else if (v.getId() == R.id.contact_address) {

            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("google.navigation:q=" + getString(R.string.app_address)));
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Log.i(TAG, "onClick: " + e.getLocalizedMessage());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.about_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_about_share) {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < Config.contacts.size(); i++) {

                final int key = Config.contacts.keyAt(i);
                final int value = Config.contacts.get(key);

                ViewGroup viewGroup = findViewById(key);

                if ("@0".equals(getString(value))) {
                    //viewGroup.setVisibility(View.GONE);
                } else {
                    ViewGroup viewGroup1 = (ViewGroup) viewGroup.getChildAt(1);
                    int count = viewGroup1.getChildCount();
                    sb.append((char) 10);
                    for (int i1 = 0; i1 < count; i1++) {
                        View t2 = viewGroup1.getChildAt(i1);
                        if (t2 instanceof TextView) {
                            TextView textView = (TextView) t2;
                            sb.append(textView.getText())
                                    .append((char) 10);
                        }

                    }

                }
            }
            //Toast.makeText(this, sb.toString(), Toast.LENGTH_LONG).show();
            DLog.d("@@" + sb.toString());
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            sharingIntent.putExtra(Intent.EXTRA_TEXT, sb.toString());
            startActivity(Intent.createChooser(sharingIntent, "Share text via"));
            return true;
        } else if (itemId == android.R.id.home) {//NavUtils.navigateUpFromSameTask(this);
            this.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}