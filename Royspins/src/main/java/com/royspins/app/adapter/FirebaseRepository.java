package com.royspins.app.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.royspins.app.pojo.Dataset;
import com.royspins.app.presenter.AbstractPresenter;
import com.royspins.app.presenter.MainContract;
import com.walhalla.landing.pagination.CatItem;
import com.walhalla.ui.DLog;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Get Url from Firebase
 */
public class FirebaseRepository extends AbstractPresenter {

    private ArrayList<CatItem> categories;

    private static final String KEY_DATASET = "dataset";
    public static final String HKEY_USERS = "users";

    private static String __e(String aaa) {
        return aaa;
    }

    private final ChildEventListener _cel = new ChildEventListener() {

        //Child event listener
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }


        /**
         * Modify in One field
         */
        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previous) {
            /*
             * Other field null
             */
//            DLog.d("@1" + dataSnapshot.toString());
//            DLog.d("@2" + previous);
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull @NotNull DatabaseError error) {
            DLog.handleException(error.toException());
            // Failed to getConfig value
            if (mView != null) {
                mView_empty();
            }
        }

    };

    private final ValueEventListener _vel = new ValueEventListener() {

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            DLog.d("! @@-->: [" + dataSnapshot.getKey() + "] " + dataSnapshot.toString());

            // Get Post object and use the values to update the UI
            //Post post = dataSnapshot.getValue(Post.class);
            if (KEY_DATASET.equals(dataSnapshot.getKey())) {
                try {
                    Dataset dataset = dataSnapshot.getValue(Dataset.class);
                    DLog.d("! @@-->: " + (dataset==null)+""+dataset.items);
                    if (mView != null) {
                        //dataset.setEnabled(true);
                        //dataset.url = "https://twitter.com/";
                        categories = new ArrayList<>();
                        if (dataset != null && dataset.items != null && dataset.items.size() > 0) {
                            categories.addAll(dataset.items);
                            mView.setupNavigationMenus(categories, null);
                        } else {
                            mView_empty();
                        }
                    }
                } catch (Exception e) {
                    //DLog.d("@@@: " + dataSnapshot.toString());
                    DLog.handleException(e);
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            DLog.handleException(databaseError.toException());
            // Failed to getConfig value
            if (mView != null) {
                mView_empty();
            }
        }
    };

    private void mView_empty() {
        DLog.d("@@@");
        //new Dataset(false, "", false, "")
        //new Dataset(false, "", false, "")
    }


//    public FirebaseRepository(RepoCallback mCallback) {
//        super(null, mCallback);
//    }
//
//    public FirebaseRepository(Context context, RepoCallback mCallback) {
//        super(context, mCallback);
//    }

    public FirebaseRepository(Context context, MainContract.View view) {
        super(view);
    }

//    public void write() {
//        // Write a message to the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        database.setPersistenceEnabled(true);
//        DatabaseReference myRef = database.getReference();
//        myRef.setValue(new Dataset(true, "http://ya.ru", true, "ru|ua|de|ch"));
//        myRef.addValueEventListener(postListener);
//    }

//    private void writeNewUser(String userId, String name, String email) {
//        Dataset user = new Dataset(
//                //        name, email
//        );
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        database.setPersistenceEnabled(true);
//
//        DatabaseReference mDatabase = database.getReference("message");
//        mDatabase.child(Const.KEY_USERS).child(userId).setValue(user);
//        mDatabase.addValueEventListener(postListener);
//    }

    @Override
    public void onNavigationItemSelected(String itemId) {
        String url = getUrlForNavigationItem(itemId);
        DLog.d("" + itemId + " " + url);
        mView.loadUrlInWebView(url);
    }

    public String getUrlForNavigationItem(String title) {
        if (categories != null) {
            for (CatItem item : categories) {
                if (item.title.equals(title)) {
                    return item.url;
                }
            }
        }
        return null;
    }

    @Override
    public void onBottomNavigationItemSelected(String itemId) {
        //none
    }

    @Override
    public void fetchRemoteConfig(Context context) {
        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference(KEY_DATASET);

            reference.addValueEventListener(_vel);
            reference.addChildEventListener(_cel);
        } catch (Exception e) {
            DLog.handleException(e);
        }

// Write a message to the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference reference = database.getReference("message");
//        // Read from the database
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d(TAG, "Value is: " + value);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // Failed to getConfig value
//                Log.w(TAG, "Failed to getConfig value.", error.toException());
//            }
//        });
    }

}