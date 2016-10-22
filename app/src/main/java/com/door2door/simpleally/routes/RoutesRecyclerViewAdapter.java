package com.door2door.simpleally.routes;

import android.content.Context;
import android.graphics.Color;

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
import com.door2door.simpleally.R;
import com.door2door.simpleally.data.RoutesDataHelper;
import com.door2door.simpleally.data.pojo.Route;
import com.door2door.simpleally.data.pojo.Segment;
import com.door2door.simpleally.utils.SvgDecoder;
import com.door2door.simpleally.utils.SvgDrawableTranscoder;
import com.door2door.simpleally.utils.SvgSoftwareLayerSetter;

import java.io.InputStream;
import java.text.ParseException;

import java.util.List;


public class RoutesRecyclerViewAdapter extends RecyclerView.Adapter<RoutesRecyclerViewAdapter.ViewHolder> {

    private final List<Route> mValues;
    private final Context mContext;
    private final RoutesDataHelper mRoutesHelper;

    public RoutesRecyclerViewAdapter(List<Route> items, Context context) {
        mValues = items;
        mContext = context;
        mRoutesHelper = new RoutesDataHelper();
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
        holder.mIdView.setText(mRoutesHelper.toTitleCase(holder.mItem.getType()));
        if (holder.mItem.getPrice() != null)
            holder.mPrice.setText(holder.mItem.getPrice().getAmount() + " " + holder.mItem.getPrice().getCurrency());

        holder.mRecyclerView.setAdapter(
                new SegmentsRecyclerViewAdapter(mRoutesHelper.filterSegments(holder.mItem.getSegments()), mContext));

        try {
            holder.mProvider.setText(mRoutesHelper.calculateDuration(holder.mItem.getSegments()));
            holder.mFromToTime.setText(mRoutesHelper.makeFromToTime(holder.mItem.getSegments()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
        public final TextView mFromToTime;
        public Route mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mProvider = (TextView) view.findViewById(R.id.provider);
            mPrice = (TextView) view.findViewById(R.id.price);
            mFromToTime = (TextView) view.findViewById(R.id.from_to_time);
            mRecyclerView = (RecyclerView) view.findViewById(R.id.segment_list);
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            mRecyclerView.setLayoutManager(layoutManager);
        }

    }

    static class SegmentsRecyclerViewAdapter extends RecyclerView.Adapter<SegmentsRecyclerViewAdapter.ViewHolder> {

        private final List<Segment> mValues;
        private GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> mRequestBuilder;

        public SegmentsRecyclerViewAdapter(List<Segment> mValues,
                                           Context mContext) {
            this.mValues = mValues;
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
