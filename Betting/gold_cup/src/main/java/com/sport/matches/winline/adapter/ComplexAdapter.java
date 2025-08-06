package com.sport.matches.winline.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.footballdeluxe.goldсup.R;
import com.sport.matches.winline.fragment.SimpleClubViewModel;
import com.sport.matches.winline.model.ButtonViewModel;
import com.sport.matches.winline.model.ClubViewModel;
import com.sport.matches.winline.model.GooglePlayViewModel;
import com.sport.matches.winline.model.HeaderObject;
import com.sport.matches.winline.model.SeasonViewModel;
import com.sport.matches.winline.model.SimpleCardViewModel;
import com.sport.matches.winline.model.SimpleModel;
import com.sport.matches.winline.model.SimpleText;
import com.sport.matches.winline.model.ViewModel;
import com.walhalla.ui.DLog;

import java.util.List;

public class ComplexAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_SIMPLE = R.layout.menu_select_item;
    private static final int TYPE_GOOGLE_PLAY = 111;
    public static final int TYPE_HEADER = R.layout.row_header;
    public static final int TYPE_CLUB = R.layout.item_club_ex;
    public static final int TYPE_SIMPLE_CARD = R.layout.item_simple_card;

    public static final int TYPE_SIMPLE_TEXT = R.layout.simple_list_item_1;


    public static final int TYPE_SEASON = R.layout.item_button;
    public static final int TYPE_SIMPLE_CLUBVIEWMODEL = 676;
    public static final int T_SIMPLE_BUTTON = 769;

    public static final int TYPE_SIMPLE_TEXT_2_H = R.layout.simple_list_item_2_horizont;
    public static final int TYPE_SIMPLE_TEXT_2_V = R.layout.simple_list_item_2_vertical;


    private final ChildItemClickListener mCallback;

    public interface ChildItemClickListener {

        void menuNavigation(ViewModel navItem);

        void clubSelected(ClubViewModel obj);
    }

    private final List<ViewModel> data;
    private final Context context;

    public ComplexAdapter(Context context, List<ViewModel> data,
                          ChildItemClickListener callback) {
        this.mCallback = callback;
        this.context = context;
        this.data = data;
//        TypedArray typedArray = context.getResources().obtainTypedArray(R.array.rainbow);
//        int[] color = new int[typedArray.length()];
//
//        for (int i = 0; i < typedArray.length(); i++) {
//            //color[i] = typedArray.getResourceId(i, -1);
//            color[i] = typedArray.getColor(i, 0);
//        }
//        typedArray.recycle();
    }

    @Override
    public int getItemViewType(int position) {
        ViewModel viewModel = data.get(position);
//        if (viewModel instanceof MyIpObj) {
//            return TYPE_MY_IP;
//        } else if (viewModel instanceof ExObj) {
//            return TYPE_EXTENDED;
//        } else
        if (viewModel instanceof SimpleModel) {
            return TYPE_SIMPLE;
        } else if (viewModel instanceof HeaderObject) {
            return TYPE_HEADER;
        }
//        else if (viewModel instanceof GatewayObj2) {
//            return TYPE_GATEWAY;
//        }
        else if (viewModel instanceof GooglePlayViewModel) {
            return TYPE_GOOGLE_PLAY;
        } else if (viewModel instanceof SimpleClubViewModel) {
            return TYPE_SIMPLE_CLUBVIEWMODEL;
        } else if (viewModel instanceof ClubViewModel) {
            return TYPE_CLUB;

        } else if (viewModel instanceof SimpleCardViewModel) {
            return TYPE_SIMPLE_CARD;
        } else if (viewModel instanceof SimpleText) {
            return TYPE_SIMPLE_TEXT;
        } else if (viewModel instanceof SimpleText2Horizontal) {
            return TYPE_SIMPLE_TEXT_2_H;
        } else if (viewModel instanceof SimpleText2Verical) {
            return TYPE_SIMPLE_TEXT_2_V;
        } else if (viewModel instanceof SeasonViewModel) {
            return TYPE_SEASON;
        } else if (viewModel instanceof ButtonViewModel) {
            return T_SIMPLE_BUTTON;
        }
//        else if (viewModel instanceof CountryObj) {
//            return TYPE_COUNTRY;
//        } else {
//            return TYPE_NONE;
//        }
        return TYPE_HEADER;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {


            case TYPE_SIMPLE:
                View view = inflater.inflate(R.layout.menu_select_item, parent, false);
                viewHolder = new SimpleViewHolder(view);
                break;

            case TYPE_GOOGLE_PLAY:
                view = inflater.inflate(R.layout.note_list_google_play, parent, false);
                viewHolder = new GooglePlayViewHolder(view);
                break;

            default:

            case TYPE_HEADER:
                view = inflater.inflate(R.layout.row_header, parent, false);
                viewHolder = new HeaderViewHolder(view);
                break;

            case TYPE_CLUB:
                view = inflater.inflate(R.layout.item_club_ex, parent, false);
                viewHolder = new ClubViewHolder(view);
                break;

            case TYPE_SIMPLE_CARD:
                view = inflater.inflate(R.layout.item_simple_card, parent, false);
                viewHolder = new SimpleCardViewHolder(view);
                break;

            case TYPE_SIMPLE_CLUBVIEWMODEL:
                view = inflater.inflate(R.layout.item_simple_card, parent, false);
                viewHolder = new SimpleClubViewHolder(view);
                break;

            case TYPE_SEASON:
                view = inflater.inflate(R.layout.item_season, parent, false);
                viewHolder = new SeasonViewHolder(view);
                break;

            case T_SIMPLE_BUTTON:
                view = inflater.inflate(R.layout.item_button, parent, false);
                viewHolder = new ButtonViewHolder(view);
                break;

            case TYPE_SIMPLE_TEXT:
                view = inflater.inflate(R.layout.simple_list_item_1, parent, false);
                viewHolder = new RecyclerViewSimpleTextViewHolder(view);
                break;

            case TYPE_SIMPLE_TEXT_2_H:
                view = inflater.inflate(R.layout.simple_list_item_2_horizont, parent, false);
                viewHolder = new RecyclerViewSimpleText2ViewHolder(view);
                break;

            case TYPE_SIMPLE_TEXT_2_V:
                view = inflater.inflate(R.layout.simple_list_item_2_vertical, parent, false);
                viewHolder = new RecyclerViewSimpleText2ViewHolder(view);
                break;

//            default:
//                view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
//                viewHolder = new RecyclerViewSimpleTextViewHolder(view);
//                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewModel obj = data.get(position);

        switch (holder.getItemViewType()) {

            case TYPE_SIMPLE:
                SimpleViewHolder simpleViewHolder = (SimpleViewHolder) holder;
                simpleViewHolder.bind((SimpleModel) obj, context, mCallback);
                break;
            case TYPE_SEASON:
                SeasonViewHolder buttonViewHolder = (SeasonViewHolder) holder;
                buttonViewHolder.bind((SeasonViewModel) obj, mCallback);
                break;
            case T_SIMPLE_BUTTON:
                ButtonViewHolder buttonViewHolder9 = (ButtonViewHolder) holder;
                buttonViewHolder9.bind((ButtonViewModel) obj, mCallback);
                break;
            case TYPE_HEADER:
                HeaderViewHolder holder5 = (HeaderViewHolder) holder;
                holder5.bind((HeaderObject) obj, mCallback);
                break;
            case TYPE_CLUB:
                ClubViewHolder holder5s = (ClubViewHolder) holder;
                holder5s.bind((ClubViewModel) obj, mCallback);
                break;

            case TYPE_SIMPLE_CARD:
                SimpleCardViewHolder ss = (SimpleCardViewHolder) holder;
                ss.bind((SimpleCardViewModel) obj, mCallback);
                break;

            case TYPE_SIMPLE_CLUBVIEWMODEL:
                SimpleClubViewHolder viewHolder = (SimpleClubViewHolder) holder;
                viewHolder.bind((SimpleClubViewModel) obj, mCallback);
                break;

            case TYPE_GOOGLE_PLAY:
                GooglePlayViewHolder holder2 = (GooglePlayViewHolder) holder;
                //holder2.bind((GooglePlayViewModel) obj, context);
                break;

            case TYPE_SIMPLE_TEXT:
                RecyclerViewSimpleTextViewHolder vh0 = (RecyclerViewSimpleTextViewHolder) holder;
                vh0.bind((SimpleText) obj);
                break;

            case TYPE_SIMPLE_TEXT_2_H:
                RecyclerViewSimpleText2ViewHolder vh09 = (RecyclerViewSimpleText2ViewHolder) holder;
                vh09.bind((SimpleText2Horizontal) obj);
                break;

            case TYPE_SIMPLE_TEXT_2_V:
                RecyclerViewSimpleText2ViewHolder text2ViewHolder = (RecyclerViewSimpleText2ViewHolder) holder;
                text2ViewHolder.bind((SimpleText2Verical) obj);
                break;

//            default:
//                RecyclerViewSimpleTextViewHolder vh = (RecyclerViewSimpleTextViewHolder) holder;
//                configureDefaultViewHolder(vh, position);
//                break;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    static class SimpleClubViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;
        private final ImageView image;

        public SimpleClubViewHolder(@NonNull View view) {
            super(view);
            textView = view.findViewById(R.id.title);
            image = view.findViewById(R.id.thumbnail);
        }

        public void bind(SimpleClubViewModel obj, ChildItemClickListener mCallback) {
            textView.setText("" + obj.total_win);
            Glide.with(image.getContext())
                    .load(Uri.parse(obj.logoClub))
                    .into(image);
            itemView.setOnClickListener(v -> mCallback.menuNavigation(obj));
        }
    }

    static class SimpleCardViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;
        private final ImageView image;

        public SimpleCardViewHolder(@NonNull View view) {
            super(view);
            textView = view.findViewById(R.id.title);
            image = view.findViewById(R.id.thumbnail);
        }

        public void bind(SimpleCardViewModel obj, ChildItemClickListener mCallback) {
            textView.setText("" + obj.total_win);
            Glide.with(image.getContext())
                    .load(Uri.parse(obj.img))
                    .into(image);
            itemView.setOnClickListener(v -> mCallback.menuNavigation(obj));
        }
    }


    static class SimpleViewHolder extends RecyclerView.ViewHolder {

        //        private final SquareImageButton button;
        //        //private final RecyclerView recyclerView;
        private final TextView textView;
//        private final ImageView number;

        SimpleViewHolder(@NonNull View view) {
            super(view);
//            button = view.findViewById(R.id.key);
//            number = null;//view.findViewById(R.id.number_icon);
            //recyclerView = view.findViewById(R.id.recycler_view);
            textView = view.findViewById(R.id.textView);
        }

        void bind(final SimpleModel level, Context context, ChildItemClickListener mCallback) {
            //GridLayoutManager lm = new GridLayoutManager(context, level.field_size);
            //recyclerView.setLayoutManager(lm);
            //CAdapter adapter = new CAdapter((level.field_size * level.field_size) - 1);
            //recyclerView.addItemDecoration(new ItemDecorationAlbumColumns(2, level.field_size));

            //recyclerView.setAdapter(adapter);
            final int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                //button.setBackgroundDrawable(ContextCompat.getDrawable(context, level.icon));
                //button.setImageDrawable(ContextCompat.getDrawable(context, level.icon));
            } else {
                //button.setBackground(ContextCompat.getDrawable(context, level.icon));
                try {
                    //button.setImageDrawable(ContextCompat.getDrawable(context, level.icon));
                } catch (Exception e) {
                    DLog.handleException(e);
                }
            }
//            if(level.isSolved()){
//                number.setImageDrawable(ContextCompat.getDrawable(context, level.getIc2()));
//            }
//            button.setOnClickListener(view -> {
//                mCallback.menuNavigation(level);
//            });
//            recyclerView.addOnItemTouchListener(
//                    new RecyclerItemClickListener(context, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(View view, int position) {
//                            button.callOnClick();
//                        }
//
//                        @Override
//                        public void onLongItemClick(View view, int position) {
//                            // do whatever
//                        }
//                    })
//            );
            textView.setText(level.title);
        }
    }


    private static class SeasonViewHolder extends RecyclerView.ViewHolder {

        public TextView label;
        public TextView content;

        SeasonViewHolder(View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.tv_label);
            content = itemView.findViewById(R.id.tv_description);
        }

        public void bind(SeasonViewModel obj, ChildItemClickListener mCallback) {
            label.setText(obj.title);
            content.setText(obj.url);
            itemView.setOnClickListener(v -> mCallback.menuNavigation(obj));
        }
    }

    private static class ButtonViewHolder extends RecyclerView.ViewHolder {

        public TextView label;
        public TextView content;

        ButtonViewHolder(View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.tv_label);
            content = itemView.findViewById(R.id.tv_description);
        }

        public void bind(ButtonViewModel obj, ChildItemClickListener mCallback) {
            label.setText(obj.title);
            content.setText(obj.url);
            itemView.setOnClickListener(v -> mCallback.menuNavigation(obj));
        }
    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {

        public TextView label;
        public TextView content;

        HeaderViewHolder(View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.tv_label);
            content = itemView.findViewById(R.id.tv_description);
        }

        public void bind(HeaderObject obj, ChildItemClickListener mCallback) {
            label.setText(obj.title);
            content.setText(obj.packageName);
            itemView.setOnClickListener(v -> mCallback.menuNavigation(obj));
        }
    }

//    private class CAdapter extends RecyclerView.Adapter<CAdapter.VH2> {
//
//        private List<Object> data = new ArrayList<>();
//        private int index = 0;
//
//        CAdapter(int count) {
//            for (int i = 0; i < count; i++) {
//                data.add(new Object());
//            }
//        }
//
//        @NonNull
//        @Override
//        public VH2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//            View view = inflater.inflate(R.layout.bbbb, parent, false);
//            return new VH2(view);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull VH2 holder, int position) {
//            holder.bind(data.get(position));
//        }
//
//        @Override
//        public int getItemCount() {
//            return data.size();
//        }
//
//        class VH2 extends RecyclerView.ViewHolder {
//
//            private final View bbb;
//            private final TextView text;
//
//            VH2(@NonNull View view) {
//                super(view);
//                bbb = view.findViewById(R.id.bb);
//                text = view.findViewById(R.id.text1);
//            }
//
//            void bind(Object o) {
//                Random r = new Random();
//                //int randomNumber = r.nextInt(color.length - 1);
//                //bbb.setBackgroundColor(color[randomNumber]);
//                text.setText("" + (++index));
//            }
//        }
//    }

    static class GooglePlayViewHolder extends RecyclerView.ViewHolder {
        public GooglePlayViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    //    static class GooglePlayViewHolder extends RecyclerView.ViewHolder {
//
//        private final SquareImageButton button;
//        //private final RecyclerView recyclerView;
//        private final TextView textView;
////        private final ImageView number;
//
//        public GooglePlayViewHolder(@NonNull View view) {
//            super(view);
//            button = view.findViewById(R.id.key);
////            number = null;//view.findViewById(R.id.number_icon);
//            //recyclerView = view.findViewById(R.id.recycler_view);
//            textView = view.findViewById(R.id.textView);
//        }
//
//        public void bind(GooglePlayViewModel level, Context context) {
//            //GridLayoutManager lm = new GridLayoutManager(context, level.field_size);
//            //recyclerView.setLayoutManager(lm);
//            //CAdapter adapter = new CAdapter((level.field_size * level.field_size) - 1);
//            //recyclerView.addItemDecoration(new ItemDecorationAlbumColumns(2, level.field_size));
//
//            //recyclerView.setAdapter(adapter);
//            final int sdk = android.os.Build.VERSION.SDK_INT;
//            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//                //button.setBackgroundDrawable(ContextCompat.getDrawable(context, level.icon));
//                button.setImageDrawable(ContextCompat.getDrawable(context, level.icon));
//            } else {
//                //button.setBackground(ContextCompat.getDrawable(context, level.icon));
//                try {
//                    button.setImageDrawable(ContextCompat.getDrawable(context, level.icon));
//                } catch (Exception e) {
//                    DLog.handleException(e);
//                }
//            }
////            if(level.isSolved()){
////                number.setImageDrawable(ContextCompat.getDrawable(context, level.getIc2()));
////            }
//            button.setOnClickListener(view -> {
//                if (UUtils.isPackageInstalledForLaunch(context, level.packageName)) {
//                    //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://vm.tiktok.com/"));
//                    //intent.setPackage(level.packageName);
//                    Intent intent = context.getPackageManager().getLaunchIntentForPackage(level.packageName);
//                    context.startActivity(intent);
//                } else {
//                    try {
//                        Uri uri = Uri.parse(String.format("market://details?id=%1$s", level.packageName));
//                        context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
//                    } catch (android.content.ActivityNotFoundException anfe) {
//                        openBrowser(context, UConst.GOOGLE_PLAY_CONSTANT + level.packageName);
//                    }
//                }
//            });
////            recyclerView.addOnItemTouchListener(
////                    new RecyclerItemClickListener(context, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
////                        @Override
////                        public void onItemClick(View view, int position) {
////                            button.callOnClick();
////                        }
////
////                        @Override
////                        public void onLongItemClick(View view, int position) {
////                            // do whatever
////                        }
////                    })
////            );
//            textView.setText(level.title);
//        }
//    }

    static class RecyclerViewSimpleTextViewHolder extends RecyclerView.ViewHolder {

        TextView text1;

        RecyclerViewSimpleTextViewHolder(View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.text1);
        }

        void bind(SimpleText label) {
            text1.setText(label.text1);
        }
    }

    static class RecyclerViewSimpleText2ViewHolder extends RecyclerView.ViewHolder {

        TextView text1;
        TextView text2;

        RecyclerViewSimpleText2ViewHolder(View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.text1);
            text2 = itemView.findViewById(R.id.text2);
        }

        void bind(SimpleText2Horizontal label) {
            text1.setText(label.text1);
            text2.setText(label.text2);
        }

        void bind(SimpleText2Verical label) {
            text1.setText(label.text1);
            text2.setText(label.text2);
        }
    }
}
