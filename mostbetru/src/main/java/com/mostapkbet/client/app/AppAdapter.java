//package com.mostapkbet.client.app;
//
//import android.content.SharedPreferences;
//import android.os.Build;
//import android.os.FileObserver;
//import android.os.Handler;
//
//import androidx.annotation.Nullable;
//import androidx.preference.PreferenceManager;
//
//import com.walhalla.ui.DLog;
//
//import java.util.Map;
//import java.util.Set;
//
//public class AppAdapter {
//
//    private final Handler handler;
//    private final MostbetApp m;
//    private final SharedPreferences preferences;
//    private final SharedPreferences.OnSharedPreferenceChangeListener changeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
//        @Override
//        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//            final Map<String, ?> keys = preferences.getAll();
//            final Set<? extends Map.Entry<String, ?>> bbb = keys.entrySet();
////            for (Map.Entry<String, ?> entry : bbb) {
////                if (Const.KEY_ORGANIC_.equals(key)) {
////                    //Log.d(TAG, "ORGANIC_TRIGGER");
////                    //wrapContent0Request();
////                    return;
////                } else if (Const.KEY_DEEP_LINK.equals(key)) {
////                    Log.d(TAG, "=========>>>>" + key + "::" + entry.getValue());
////                    if (mView.handleDeepLink()) {
////                        mView.PEREHOD_S_DEEPLINKOM();
////                    }
////                    return;
////                }
////            }
//        }
//    };
//
//    private FileObserver fileObserver;
//
//    public AppAdapter(Handler handler, MostbetApp mostbetApp) {
//        this.handler = handler;
//        this.m = mostbetApp;
//        preferences = PreferenceManager.getDefaultSharedPreferences(m.getApplicationContext());
//        preferences.registerOnSharedPreferenceChangeListener(changeListener);
//    }
//
//    public void execute() {
//        new Thread(() -> {
//            ///data/user/0/com.mostapkbet.client/files
//            ///data/data/com.mostapkbet.client/files
//
//            //
//            DLog.d("@@@" + m.getFilesDir().getPath());
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                DLog.d("@@@ " + m.getDataDir().getPath());
//            }
//            DLog.d("@@@ " + m.getFilesDir().getParent());
//
//            //String mm = m.getFilesDir().getParent();
//            //String mm = "/data/data/" + m.getPackageName();
//            //String mm = "/data/data/" + m.getPackageName()+"/databases/";
//
//            //String mm = "/data/data/" + m.getPackageName()+"/app_webview/";
//            String mm = "/data/data/" + m.getPackageName() + "/shared_prefs/";
//
//            fileObserver = new FileObserver(mm) {
//                @Override
//                public void onEvent(int event, @Nullable String path) {
//                    // Обработка событий изменения файлов
//                    if (event == FileObserver.CREATE) {
//                        tst("Файл создан", path);
//                    } else if (event == FileObserver.DELETE) {
//                        tst("Файл удален", path);
//                    } else if (event == FileObserver.MODIFY) {
//                        tst("Файл изменен", path);
//                    } else if (event == FileObserver.MOVED_FROM) {
//                        tst("Файл перемещен из директории", path);
//                    } else if (event == FileObserver.MOVED_TO) {
//                        tst("Файл перемещен в директорию", path);
//                    }
//                }
//            };
//            fileObserver.startWatching();
//        }).start();
//    }
//
//    public void terminate() {
//        fileObserver.stopWatching();
//    }
//
//    private void tst(String ba, String path) {
//        handler.post(() -> {
//            m.tst(ba, path);
//        });
//    }
//}
