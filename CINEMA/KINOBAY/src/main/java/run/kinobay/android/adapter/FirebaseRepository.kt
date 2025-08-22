//package com.royspins.app.adapter
//
//import android.content.Context
//import com.google.firebase.database.ChildEventListener
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.DatabaseError
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.database.ValueEventListener
//import com.royspins.app.pojo.Dataset
//import com.royspins.app.presenter.AbstractPresenter
//import com.royspins.app.presenter.MainContract
//import com.walhalla.landing.pagination.CatItem
//import com.walhalla.ui.DLog.d
//import com.walhalla.ui.DLog.handleException
//
///**
// * Get Url from Firebase
// */
//class FirebaseRepository  //    public FirebaseRepository(RepoCallback mCallback) {
////        super(null, mCallback);
////    }
////
////    public FirebaseRepository(Context context, RepoCallback mCallback) {
////        super(context, mCallback);
////    }
//    (context: Context?, view: MainContract.View) :
//    AbstractPresenter(view) {
//    private var categories: ArrayList<CatItem>? = null
//
//    private val _cel: ChildEventListener = object : ChildEventListener {
//        //Child event listener
//        override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
//            println("@@@ $dataSnapshot")
//        }
//
//
//        /**
//         * Modify in One field
//         */
//        override fun onChildChanged(dataSnapshot: DataSnapshot, previous: String?) {
//            println("@@@ $dataSnapshot")
//            /*
//             * Other field null
//             */
////            DLog.d("@1" + dataSnapshot.toString());
////            DLog.d("@2" + previous);
//        }
//
//        override fun onChildRemoved(dataSnapshot: DataSnapshot) {
//            println("@@@ $dataSnapshot")
//        }
//
//        override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {
//            println("@@@ $dataSnapshot")
//        }
//
//        override fun onCancelled(error: DatabaseError) {
//            println("@@@ $error")
//
//            handleException(error.toException())
//            // Failed to getConfig value
//            if (mView != null) {
//                mView_empty()
//            }
//        }
//    }
//
//    private val _vel: ValueEventListener = object : ValueEventListener {
//        override fun onDataChange(dataSnapshot: DataSnapshot) {
//            d("! @@-->: [" + dataSnapshot.key + "] " + dataSnapshot.toString())
//
//            // Get Post object and use the values to update the UI
//            //Post post = dataSnapshot.getValue(Post.class);
//            if (KEY_DATASET == dataSnapshot.key) {
//                try {
//                    val dataset = dataSnapshot.getValue(
//                        Dataset::class.java
//                    )
//                    d("! @@-->: " + (dataset == null) + "" + dataset!!.items)
//                    if (mView != null) {
//                        //dataset.setEnabled(true);
//                        //dataset.url = "https://twitter.com/";
//                        categories = ArrayList()
//                        if (dataset?.items != null && dataset.items.size > 0) {
//                            categories!!.addAll(dataset.items)
//                            mView.setupNavigationMenus(categories!!, emptyList())
//                        } else {
//                            mView_empty()
//                        }
//                    }
//                } catch (e: Exception) {
//                    //DLog.d("@@@: " + dataSnapshot.toString());
//                    handleException(e)
//                }
//            }
//        }
//
//        override fun onCancelled(databaseError: DatabaseError) {
//            handleException(databaseError.toException())
//            println("@@@ $databaseError")
//            // Failed to getConfig value
//            if (mView != null) {
//                mView_empty()
//            }
//        }
//    }
//
//    private fun mView_empty() {
//        d("@@@")
//        //new Dataset(false, "", false, "")
//        //new Dataset(false, "", false, "")
//    }
//
//
//    //    public void write() {
//    //        // Write a message to the database
//    //        FirebaseDatabase database = FirebaseDatabase.getInstance();
//    //        database.setPersistenceEnabled(true);
//    //        DatabaseReference myRef = database.getReference();
//    //        myRef.setValue(new Dataset(true, "http://ya.ru", true, "ru|ua|de|ch"));
//    //        myRef.addValueEventListener(postListener);
//    //    }
//    //    private void writeNewUser(String userId, String name, String email) {
//    //        Dataset user = new Dataset(
//    //                //        name, email
//    //        );
//    //        FirebaseDatabase database = FirebaseDatabase.getInstance();
//    //        database.setPersistenceEnabled(true);
//    //
//    //        DatabaseReference mDatabase = database.getReference("message");
//    //        mDatabase.child(Const.KEY_USERS).child(userId).setValue(user);
//    //        mDatabase.addValueEventListener(postListener);
//    //    }
//    override fun onNavigationItemSelected(itemId: String?) {
//        val url = getUrlForNavigationItem(itemId)
//        d("$itemId $url")
//        mView.loadUrlInWebView(url!!)
//    }
//
//    fun getUrlForNavigationItem(title: String?): String? {
//        if (categories != null) {
//            for (item in categories!!) {
//                if (item.title == title) {
//                    return item.url
//                }
//            }
//        }
//        return null
//    }
//
//    override fun onBottomNavigationItemSelected(itemId: String?) {
//        //none
//    }
//
//    override fun fetchRemoteConfig(context: Context) {
//        try {
//            println("@@@@@")
//            val database = FirebaseDatabase.getInstance()
//            val reference = database.getReference(KEY_DATASET)
//
//            reference.addValueEventListener(_vel)
//            reference.addChildEventListener(_cel)
//        } catch (e: Exception) {
//            handleException(e)
//        }
//
//        // Write a message to the database
////        FirebaseDatabase database = FirebaseDatabase.getInstance();
////        DatabaseReference reference = database.getReference("message");
////        // Read from the database
////        reference.addValueEventListener(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                // This method is called once with the initial value and again
////                // whenever data at this location is updated.
////                String value = dataSnapshot.getValue(String.class);
////                Log.d(TAG, "Value is: " + value);
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError error) {
////                // Failed to getConfig value
////                Log.w(TAG, "Failed to getConfig value.", error.toException());
////            }
////        });
//    }
//
//    companion object {
//        private const val KEY_DATASET = "dataset"
//        const val HKEY_USERS: String = "users"
//
//        private fun __e(aaa: String): String {
//            return aaa
//        }
//    }
//}