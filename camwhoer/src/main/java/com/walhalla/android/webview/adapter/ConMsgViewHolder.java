package com.walhalla.android.webview.adapter;

import android.annotation.SuppressLint;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.walhalla.android.R;
import com.walhalla.android.webview.UResource;
import com.walhalla.android.webview.viewmodel.ConMsg;

import java.lang.reflect.Field;

public class ConMsgViewHolder extends RecyclerView.ViewHolder {

    private final ConMsgViewHolderCallback callback1;
    private final ImageView msgLevel;

    public interface ConMsgViewHolderCallback {

        void copyItem(ConMsg obj);

        void shareText(ConMsg obj);

        void openHtmlViewer(String data);

        void requestViewIntent(String data);

    }

    final TextView textView;
    final ViewGroup viewGroup;
    private final View overflowMenu;

    public ConMsgViewHolder(ConMsgViewHolderCallback callback, View mView) {
        super(mView);
        textView = mView.findViewById(android.R.id.text1);
        viewGroup = mView.findViewById(R.id.row_container);
        overflowMenu = mView.findViewById(R.id.overflow_menu);
        msgLevel = mView.findViewById(R.id.msgLevel);

        this.callback1 = callback;
    }

    public void bind(ConMsg obj) {
        this.textView.setText(obj.mMessage);
        this.viewGroup.setBackgroundResource(aa(obj.icon));
        msgLevel.setImageResource(obj.icon);

        overflowMenu.setOnClickListener(v -> showPopupMenu(v, obj));
        this.viewGroup.setOnClickListener(view ->
                callback1.openHtmlViewer(obj.mMessage));
    }

    private int aa(int messageLevel) {
        if (messageLevel == R.drawable.ic_msg_tip) {
            return R.color.ic_msg_bg_tip;
        } else if (messageLevel == R.drawable.ic_msg_log) {
            return R.color.ic_msg_bg_log;
        } else if (messageLevel == R.drawable.ic_msg_warning) {
            return R.color.ic_msg_bg_warning;
        } else if (messageLevel == R.drawable.ic_msg_error) {
            return R.color.ic_msg_bg_error;
        } else if (messageLevel == R.drawable.ic_msg_debug) {
            return R.color.ic_msg_bg_debug;
        }
        return R.color.ic_msg_bg_tip;
    }


    @SuppressLint("NonConstantResourceId")
    private void showPopupMenu(View view, ConMsg obj) {

        //PackageMeta packageInfo = items.get(obj);
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();

        Menu menu = popup.getMenu();

//        MenuPopupHelper menuHelper = new MenuPopupHelper(
//                mView.getActivity(), (MenuBuilder) menu, view);
//        menuHelper.setForceShowIcon(true);
//        menuHelper.show();

        //reaper.wrapper(menu, new File(packageInfo.applicationInfo.sourceDir));

        inflater.inflate(R.menu.abc_popup_apps, menu);
        Object menuHelper;
        Class<?>[] argTypes;
        try {
            @SuppressLint("DiscouragedPrivateApi") Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
            fMenuHelper.setAccessible(true);
            menuHelper = fMenuHelper.get(popup);
            argTypes = new Class[]{boolean.class};
            if (menuHelper != null) {
                menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper, true);
            }
        } catch (Exception ignored) {
        }
        popup.setOnMenuItemClickListener(menuItem -> {

            int itemId = menuItem.getItemId();
            //                    case R.id.action_open_link:
//                        DLog.d("Open on Google Play" + packageInfo.packageName);
//                        mView.openPackageOnGooglePlay(packageInfo.packageName);
//                        return true;
            if (itemId == R.id.action_copy_item) {
                callback1.copyItem(obj);
            } else if (itemId == R.id.menu_share_text) {
                callback1.shareText(obj);
                return true;
            } else if (itemId == R.id.action_open_with) {
                callback1.requestViewIntent(obj.mMessage);
                return true;
            }
            return false;
        });
        popup.show();
    }
}