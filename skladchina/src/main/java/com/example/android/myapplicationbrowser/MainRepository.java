//package com.example.android.myapplicationbrowser;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import com.google.firebase.database.ChildEventListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.walhalla.ui.DLog;
//
//import java.util.ArrayList;
//
//import static org.chromium.net.CoreApplication.FB_ENABLED;
//
//
//public class MainRepository {
//
//    private final Callback var0;
//
//    private final ChildEventListener cccc = new ChildEventListener() {
//        @Override
//        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//        }
//
//
//        /**
//         * Modify in One field
//         */
//        @Override
//        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previous) {
//            /*
//             * Other field null
//             */
//            DLog.d("@1" + dataSnapshot.toString());
//            DLog.d("@2" + previous);
//        }
//
//        @Override
//        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//        }
//
//        @Override
//        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//        }
//
//        @Override
//        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//        }
//    };
//
//    public MainRepository(Callback mCallback) {
//        this.var0 = mCallback;
//    }
//
//    public interface Callback {
//        void OnSuccess(Dataset value);
//    }
//
//    private final ValueEventListener postListener = new ValueEventListener() {
//        @Override
//        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//            // Get Post object and use the values to update the UI
//            //Post post = dataSnapshot.getValue(Post.class);
//            try {
//                Dataset value = dataSnapshot.getValue(Dataset.class);
//                if (var0 != null) {
//                    var0.OnSuccess(value);
//                    DLog.d("-->: " + dataSnapshot.toString());
//                    if (value != null) {
//                        DLog.d("-->: " + value.toString());
//                    }
//                }
//            } catch (Exception e) {
//                DLog.d("@@@: " + dataSnapshot.toString());
//                DLog.handleException(e);
//            }
//        }
//
//        @Override
//        public void onCancelled(DatabaseError databaseError) {
//            DLog.handleException(databaseError.toException());
//
//            // Failed to getConfig value
//            if (var0 != null) {
//                var0.OnSuccess(
//                        new Dataset("", false, new ArrayList<>()));
//            }
//        }
//    };
//
////    public void write() {
////        // Write a message to the database
////        FirebaseDatabase database = FirebaseDatabase.getInstance();
////        database.setPersistenceEnabled(true);
////        DatabaseReference myRef = database.getReference();
////        myRef.setValue(new Dataset(true, "http://ya.ru", true, "ru|ua|de|ch"));
////        myRef.addValueEventListener(postListener);
////    }
//
////    private void writeNewUser(String userId, String name, String email) {
////        Dataset user = new Dataset(
////                //        name, email
////        );
////        FirebaseDatabase database = FirebaseDatabase.getInstance();
////        database.setPersistenceEnabled(true);
////
////        DatabaseReference mDatabase = database.getReference("message");
////        mDatabase.child("users").child(userId).setValue(user);
////        mDatabase.addValueEventListener(postListener);
////    }
//
//    public void getConfig() {
//
//        if(FB_ENABLED){
//            FirebaseDatabase database = FirebaseDatabase.getInstance();
//            DatabaseReference reference = database.getReference();
//            // Read from the database
//            reference.child("/").addValueEventListener(postListener);
//            reference.addChildEventListener(cccc);
//        }else {
//            Dataset value = new Dataset("https://2ip.ru", false, null);
//            var0.OnSuccess(value);
//        }
//
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
//}
