package dev_pc.testunsplashapi.image_recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import dev_pc.testunsplashapi.R;
import dev_pc.testunsplashapi.api.UnsplashModel;

public class MyImageRecyclerViewAdapter extends RecyclerView.Adapter<MyImageRecyclerViewAdapter.ViewHolder> {

    private final List<UnsplashModel> mValues;
    private final ImageFragment.OnListFragmentInteractionListener mListener;
    Context context;

    public MyImageRecyclerViewAdapter(List<UnsplashModel> items, ImageFragment.OnListFragmentInteractionListener listener) {
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
                .load(holder.mItem.getUrls().getSmall())
                .into(holder.mImageView);

//        holder.mIdView.setText(mValues.get(position).getId());
//        holder.mContentView.setText(mValues.get(position).getColor());
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
        public final ImageView mImageView;
        public UnsplashModel mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = view.findViewById(R.id.image);


        }
    }
}
