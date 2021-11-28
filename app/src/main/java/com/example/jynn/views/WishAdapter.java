package com.example.jynn.views;

import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.jynn.R;
import com.example.jynn.model.Wish;
import com.example.jynn.viewmodel.WishesViewModel;

import java.util.List;

public class WishAdapter extends RecyclerView.Adapter<WishAdapter.ViewHolder> {
    private WishesViewModel wishesViewModel;
    private List<Wish> mWishList;
    private OnWishClickListener onWishClickListener;

    public interface OnWishClickListener{
        void onWishClick(Wish wish);
    }

    public WishAdapter(List<Wish> wisheList, OnWishClickListener onWishClickListener){
        this.mWishList = wisheList;
        this.onWishClickListener = onWishClickListener;
    }
    public WishAdapter(OnWishClickListener onWishClickListener){
        this.onWishClickListener = onWishClickListener;

    }

    public void setWishList(List<Wish> wishList){
        if(this.mWishList == null){
            this.mWishList = wishList;
            notifyItemRangeInserted(0, wishList.size());
        } else{
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {

                    return mWishList.size();
                }

                @Override
                public int getNewListSize() {
                    return wishList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    Wish oldWish = mWishList.get(oldItemPosition);
                    Wish newWish = wishList.get(newItemPosition);
                    return TextUtils.equals(oldWish.getId(), newWish.getId());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Wish oldWish = mWishList.get(oldItemPosition);
                    Wish newWish = wishList.get(newItemPosition);
                    return TextUtils.equals(oldWish.getId(), newWish.getId())
                            && TextUtils.equals(oldWish.getTitle(),newWish.getTitle())
                            && TextUtils.equals(oldWish.getDescription(), newWish.getDescription())
                            && TextUtils.equals(oldWish.getCreateUserId(), newWish.getCreateUserId())
                            && TextUtils.equals(oldWish.getCreateUserName(), newWish.getCreateUserName())
                            && TextUtils.equals(oldWish.getCreateUserPhotoUrl(), newWish.getCreateUserPhotoUrl())
                            && TextUtils.equals(oldWish.getFulfillUserId(), newWish.getFulfillUserId())
                            && TextUtils.equals(oldWish.getFulfillUserName(), newWish.getFulfillUserName())
                            && TextUtils.equals(oldWish.getFulfillUserPhotoUrl(), newWish.getFulfillUserPhotoUrl())
                            && TextUtils.equals(oldWish.getComment(), newWish.getComment())
                            && oldWish.isDone() == newWish.isDone();
                }
            });
            mWishList = wishList;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishes_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Wish wish = mWishList.get(viewHolder.getAdapterPosition());
                onWishClickListener.onWishClick(wish);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         Wish wish = mWishList.get(position);
         String wishTitle = wish.getTitle();
         if(wish.getCreateUserPhotoUrl().equals("")){
             holder.getUserImageView().setImageResource(R.drawable.user_image);
         }else{
             Uri userPhotoUrl = Uri.parse(wish.getCreateUserPhotoUrl());
             Glide.with(holder.getUserImageView().getContext()).load(userPhotoUrl).apply(RequestOptions.circleCropTransform()).into(holder.getUserImageView());
         }
        if(wish.getFulfillUserId().equals("")){
            holder.getJinnImageView().setVisibility(View.INVISIBLE);
            holder.getJinnNameTextView().setVisibility(View.INVISIBLE);
            holder.getJinnTextView().setVisibility(View.INVISIBLE);
        }else{
            holder.getJinnImageView().setVisibility(View.VISIBLE);
            holder.getJinnNameTextView().setVisibility(View.VISIBLE);
            holder.getJinnTextView().setVisibility(View.VISIBLE);
        }
        if(wish.getFulfillUserPhotoUrl().equals("")){
            holder.getJinnImageView().setImageResource(R.drawable.user_image);
        }else{
            Uri fulfillPhotoUrl = Uri.parse(wish.getFulfillUserPhotoUrl());
            Glide.with(holder.getUserImageView().getContext()).load(fulfillPhotoUrl).apply(RequestOptions.circleCropTransform()).into(holder.getJinnImageView());

        }

         String wishCreateName = wish.getCreateUserName();
         holder.getUserNameTextView().setText(wishCreateName);
         holder.getWishTitleTextView().setText(wishTitle);
         String jinnName = wish.getFulfillUserName();
         holder.getJinnNameTextView().setText(jinnName);



    }

    @Override
    public int getItemCount() {
        int size;
        if((mWishList !=null)&&!(mWishList.isEmpty())){
            size = mWishList.size();
        }else{
            size=0;
        }
        return size;
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        //private ImageView userImageView;
        private TextView userNameTextView;
        private TextView wishTitleTextView;
        private ImageView userImageView;
        private ImageView jinnImageView;
        private TextView jinnNameTextView;
        private TextView jinnTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userImageView = (ImageView) itemView.findViewById(R.id.user_photo);
            userNameTextView = (TextView) itemView.findViewById(R.id.user_name);
            wishTitleTextView = (TextView) itemView.findViewById(R.id.wish_title);
            jinnImageView = (ImageView) itemView.findViewById(R.id.jinn_photo);
            jinnNameTextView = (TextView) itemView.findViewById(R.id.jinn_name);
            jinnTextView = (TextView) itemView.findViewById(R.id.jinn_text);
        }

        public ImageView getUserImageView() {
            return userImageView;
        }

        public TextView getUserNameTextView() {
            return userNameTextView;
        }

        public TextView getWishTitleTextView() {
            return wishTitleTextView;
        }

        public ImageView getJinnImageView() {
            return jinnImageView;
        }

        public TextView getJinnNameTextView() {
            return jinnNameTextView;
        }

        public TextView getJinnTextView() {
            return jinnTextView;
        }
    }
}
