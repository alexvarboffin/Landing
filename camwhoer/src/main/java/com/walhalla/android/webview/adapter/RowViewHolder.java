package com.walhalla.android.webview.adapter;

import android.annotation.SuppressLint;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.walhalla.android.R;
import com.walhalla.android.webview.UResource;

import java.lang.reflect.Field;

public class RowViewHolder extends RecyclerView.ViewHolder {

    private final RowViewHolderCallback callback1;

    public interface RowViewHolderCallback {

        void copyItem(UResource obj);

        void shareText(UResource obj);

        void openHtmlViewer(String data);

        void requestViewIntent(String data);

    }

    final TextView textView;
    final ViewGroup viewGroup;
    private final View overflowMenu;

    public RowViewHolder(RowViewHolderCallback callback, View mView) {
        super(mView);
        textView = mView.findViewById(android.R.id.text1);
        viewGroup = mView.findViewById(R.id.row_container);
        overflowMenu = mView.findViewById(R.id.overflow_menu);
        this.callback1 = callback;
    }

    public void bind(UResource obj) {
        this.textView.setText(obj.data);
        this.viewGroup.setBackgroundResource(obj.type);

        overflowMenu.setOnClickListener(v -> showPopupMenu(v, obj));
        this.viewGroup.setOnClickListener(view ->
                callback1.openHtmlViewer(obj.data));
    }


    @SuppressLint("NonConstantResourceId")
    private void showPopupMenu(View view, UResource obj) {

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

            int itemId = menuItem.getItemId();//                    case R.id.action_open_link:
//                        DLog.d("Open on Google Play" + packageInfo.packageName);
//                        mView.openPackageOnGooglePlay(packageInfo.packageName);
//                        return true;
            if (itemId == R.id.action_copy_item) {
                callback1.copyItem(obj);
            } else if (itemId == R.id.menu_share_text) {
                callback1.shareText(obj);
                return true;
            } else if (itemId == R.id.action_open_with) {
                callback1.requestViewIntent(obj.data);
                return true;
            }
            return false;
        });
        popup.show();
    }
}