package com.door2door.simpleally.routes;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGImageView;
import com.door2door.simpleally.R;
import com.door2door.simpleally.data.pojo.Route;
import com.door2door.simpleally.data.pojo.Segment;
import com.door2door.simpleally.utils.SvgDecoder;
import com.door2door.simpleally.utils.SvgDrawableTranscoder;
import com.door2door.simpleally.utils.SvgSoftwareLayerSetter;

import java.io.InputStream;
import java.util.List;

import static com.door2door.simpleally.routes.RoutesFragment.*;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Route} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class RoutesRecyclerViewAdapter extends RecyclerView.Adapter<RoutesRecyclerViewAdapter.ViewHolder> {

    private final List<Route> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final Context mContext;

    public RoutesRecyclerViewAdapter(List<Route> items,
                                     OnListFragmentInteractionListener listener,
                                     Context context) {
        mValues = items;
        mListener = listener;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.routes_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(toTitleCase(holder.mItem.getType()));
        holder.mProvider.setText(holder.mItem.getProvider());
        if (holder.mItem.getPrice() != null)
            holder.mPrice.setText(holder.mItem.getPrice().getAmount() + holder.mItem.getPrice().getCurrency());
        holder.mRecyclerView.setAdapter(new SegmentsRecyclerViewAdapter(holder.mItem.getSegments(), mContext));

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
        public final TextView mIdView;
        public final TextView mProvider;
        public final TextView mPrice;
        public final RecyclerView mRecyclerView;
        public Route mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mProvider = (TextView) view.findViewById(R.id.provider);
            mPrice = (TextView) view.findViewById(R.id.price);
            mRecyclerView = (RecyclerView) view.findViewById(R.id.segment_list);
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            mRecyclerView.setLayoutManager(layoutManager);
        }

    }

    private String toTitleCase(String text) {
        text = text.replace("_", " ");
        String[] arr = text.split(" ");
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < arr.length; i++) {
            sb.append(Character.toUpperCase(arr[i].charAt(0)))
                    .append(arr[i].substring(1)).append(" ");
        }
        return sb.toString().trim();
    }

    static class SegmentsRecyclerViewAdapter extends RecyclerView.Adapter<SegmentsRecyclerViewAdapter.ViewHolder> {

        private final List<Segment> mValues;
        private Context mContext;
        private GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> mRequestBuilder;

        public SegmentsRecyclerViewAdapter(List<Segment> mValues,
                                           Context mContext) {
            this.mValues = mValues;
            this.mContext = mContext;
            mRequestBuilder = Glide.with(mContext)
                    .using(Glide.buildStreamModelLoader(Uri.class, mContext), InputStream.class)
                    .from(Uri.class)
                    .as(SVG.class)
                    .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                    .sourceEncoder(new StreamEncoder())
                    .cacheDecoder(new FileToStreamDecoder<>(new SvgDecoder()))
                    .decoder(new SvgDecoder())
                    .animate(android.R.anim.fade_in)
                    .listener(new SvgSoftwareLayerSetter<Uri>());
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.segments_item, parent, false);
            return new SegmentsRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            if (holder.mItem.getName() != null)
                holder.mSegmentName.setText(holder.mItem.getName());

            holder.mSegmentContainer.setBackgroundColor(Color.parseColor(holder.mItem.getColor()));

            Uri uri = Uri.parse(holder.mItem.getIcon_url());

            mRequestBuilder
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    // SVG cannot be serialized so it's not worth to cache it
                    .load(uri)
                    .into(holder.mImageView);

            holder.mImageView.clearColorFilter();
            holder.mImageView.setColorFilter(new PorterDuffColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP));
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final ImageView mImageView;
            public final TextView mSegmentName;
            public final LinearLayout mSegmentContainer;
            public Segment mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (ImageView) view.findViewById(R.id.segment_icon);
                mSegmentName = (TextView) view.findViewById(R.id.segment_name);
                mSegmentContainer = (LinearLayout) view.findViewById(R.id.segment_container);
            }

        }
    }

}
