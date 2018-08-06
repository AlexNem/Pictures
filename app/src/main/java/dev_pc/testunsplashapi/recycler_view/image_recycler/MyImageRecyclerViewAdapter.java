package dev_pc.testunsplashapi.recycler_view.image_recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import dev_pc.testunsplashapi.R;
import dev_pc.testunsplashapi.model.Photo;

public class MyImageRecyclerViewAdapter extends RecyclerView.Adapter<MyImageRecyclerViewAdapter.ViewHolder> {

    private final List<Photo> mValues;
    private final ImageFragment.OnListFragmentInteractionListener mListener;
    Context context;

    public MyImageRecyclerViewAdapter(List<Photo> items, ImageFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        context = holder.mImageView.getContext();
        Picasso.with(context)
                .load(holder.mItem.getUrls().getRegular())
                .placeholder(R.drawable.ic_photo_size_select_actual_black_24dp)
                .into(holder.mImageView);
        holder.mLikeCount.setText(holder.mItem.getLikes());
        if (holder.mItem.isLikedByUser()){
            Picasso.with(context)
                    .load(R.drawable.ic_favorite_red_24dp)
                    .placeholder(R.drawable.ic_favorite_red_24dp)
                    .into(holder.mLike);
        }else Picasso.with(context)
                .load(R.drawable.ic_favorite_black_24dp)
                .placeholder(R.drawable.ic_faworite_empty)
                .into(holder.mLike);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mImageView, mLike, mDownload, mCollections;
        public final TextView mLikeCount;
        public Photo mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = view.findViewById(R.id.image);
            mLike = view.findViewById(R.id.like_image);
            mDownload = view.findViewById(R.id.download_image);
            mCollections = view.findViewById(R.id.collections);
            mLikeCount = view.findViewById(R.id.like_count);

        }
    }
}
