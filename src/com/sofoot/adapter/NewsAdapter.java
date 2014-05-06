package com.sofoot.adapter;

import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sofoot.R;
import com.sofoot.domain.model.News;
import com.sofoot.loader.BitmapLoader;
import com.sofoot.loader.BitmapLoaderCallbacks;

public class NewsAdapter extends SofootAdapter<News> {

    static private final int ITEM_NEWS = 1;
    static private final int ITEM_LOADER = 0;

    private boolean showLoader = false;

    public NewsAdapter(final Activity context) {
        super(context);
    }

    public void showLoader(final boolean show) {
        this.showLoader = show;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        final int type = this.getItemViewType(position);

        // Log.d("NEWSADAPTER", "view type : " + type);

        if (type == NewsAdapter.ITEM_LOADER) {
            return this.getLoaderView(position, convertView, parent);
        }
        else if (type == NewsAdapter.ITEM_NEWS) {
            return this.getNewsView(position, convertView, parent);
        }

        return null;
    }

    private View getNewsView(final int position, final View convertView, final ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            final LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.news_list_item, parent, false);
            row.setTag(new ViewHolder(row));
        }

        final News news = this.getItem(position);

        final ViewHolder viewHolder = (ViewHolder) row.getTag();

        final SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.clear();
        builder.append(Html.fromHtml(news.getTitre()));

        final int index = builder.length();

        try {
            builder.setSpan(new TextAppearanceSpan(this.context, R.style.NewsListItemTitle), 0, index,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } catch (final Exception e) {
        }

        if (news.hasDescriptif()) {
            builder.append("\n\n" + Html.fromHtml(news.getDescriptif()));
            try {
                builder.setSpan(new TextAppearanceSpan(this.context, R.style.NewsListItemDescription), index,
                        builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } catch (final Exception e) {
            }
        }

        viewHolder.textView.setText(builder);
        viewHolder.textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mock_news_list_thumbnail, 0, 0, 0);

        if (news.hasThumbnail()) {
            final URL thumbnail = news.getThumbnail(this.context.getWindowManager().getDefaultDisplay());
            viewHolder.textView.setTag(thumbnail);
            BitmapLoader.getInstance(this.context).load(thumbnail,
                    new ThumbnailBitmapLoaderCallbacks(viewHolder.textView));
        }

        return row;
    }

    private View getLoaderView(final int position, final View convertView, final ViewGroup parent) {
        final LayoutInflater layoutInflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return layoutInflater.inflate(R.layout.news_list_item_loading, parent, false);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {

        if (this.showLoader) {
            return this.list.size() + 1;
        }

        return this.list.size();
    }

    @Override
    public int getItemViewType(final int position) {

        if (position >= this.list.size()) {
            return NewsAdapter.ITEM_LOADER;
        }

        return NewsAdapter.ITEM_NEWS;
    }

    private class ViewHolder {
        TextView textView;

        public ViewHolder(final View view) {
            this.textView = (TextView) view.findViewById(android.R.id.text1);
        }
    }

    private class ThumbnailBitmapLoaderCallbacks implements BitmapLoaderCallbacks {

        private final TextView textView;

        public ThumbnailBitmapLoaderCallbacks(final TextView textView) {
            this.textView = textView;
        }

        @Override
        public void onBitmapLoaded(final URL url, final Bitmap bitmap) {
            if (this.textView == null) {
                return;
            }

            if (this.textView.getTag() != url) {
                return;
            }

            final BitmapDrawable bitmapDrawable = new BitmapDrawable(this.textView.getContext().getResources(), bitmap);
            bitmapDrawable.setBounds(0, 0,
                    (int) this.textView.getContext().getResources().getDimension(R.dimen.thumbnailWidth),
                    (int) this.textView.getContext().getResources().getDimension(R.dimen.thumbnailHeight));

            this.textView.setCompoundDrawables(bitmapDrawable, null, null, null);
        }
    }
}
