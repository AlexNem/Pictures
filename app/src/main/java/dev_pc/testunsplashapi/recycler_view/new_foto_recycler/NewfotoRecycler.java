package dev_pc.testunsplashapi.recycler_view.new_foto_recycler;

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
import dev_pc.testunsplashapi.recycler_view.new_foto_recycler.NewfotoFragment.OnListFragmentInteractionListener;
import dev_pc.testunsplashapi.responseModel.Photo;


public class NewfotoRecycler extends RecyclerView.Adapter<NewfotoRecycler.ViewHolder> {

    Context context;
    private final List<Photo> mValues;
    private final OnListFragmentInteractionListener mListener;

    public NewfotoRecycler(List<Photo> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.newfoto_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.photo = mValues.get(position);
        Picasso.with(context)
                .load(holder.photo.getUrls().getRegular())
                .placeholder(R.drawable.ic_photo_size_select_actual_black_24dp)
                .into(holder.imageView);

        holder.new_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.photo);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View new_photo;
        public final ImageView imageView;
        public final TextView author;
        public Photo photo;

        public ViewHolder(View view) {
            super(view);
            new_photo = view;
            imageView = view.findViewById(R.id.new_photo);
            author = view.findViewById(R.id.author);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + author.getText() + "'";
        }
    }
}
