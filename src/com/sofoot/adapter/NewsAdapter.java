package com.sofoot.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
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
import com.sofoot.utils.BitmapInfo;

public class NewsAdapter extends SofootAdapter<News> {

    static private final int ITEM_NEWS = 1;
    static private final int ITEM_LOADER = 0;

    public NewsAdapter(final Activity context) {
        super(context);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        final int type = this.getItemViewType(position);

        if (type == NewsAdapter.ITEM_LOADER) {
            return this.getLoaderView(position, convertView, parent);
        } else if (type == NewsAdapter.ITEM_NEWS) {
            return this.getNewsView(position, convertView, parent);
        }

        return null;
    }


    private View getNewsView(final int position, final View convertView, final ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            final LayoutInflater layoutInflater =
                    (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.news_list_item, parent, false);
            row.setTag(new ViewHolder(row));
        }

        final News news = this.getItem(position);

        final ViewHolder viewHolder = (ViewHolder)row.getTag();


        final SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(news.getTitre());
        builder.setSpan(new TextAppearanceSpan(this.context, R.style.NewsListItemTitle), 0,
                news.getTitre().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        if (news.hasDescriptif()) {
            builder.append("\n\n" + news.getDescriptif());
            builder.setSpan(new TextAppearanceSpan(this.context, R.style.NewsListItemDescription),
                    news.getTitre().length(), builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        viewHolder.textView.setText(builder);
        viewHolder.textView.setTag(R.id.thumbnail, news.getImageHome(News.ImageSize.SMALL));


        final ThumbnailLoader thumbnailLoader = new ThumbnailLoader(viewHolder.textView);
        thumbnailLoader.execute(news.getImageHome(News.ImageSize.SMALL));


        return row;
    }

    private View getLoaderView(final int position, final View convertView, final ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            final LayoutInflater layoutInflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.news_list_item_loading, parent, false);
        }

        return row;
    }


    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return this.list.size() + 1;
    }

    @Override
    public int getItemViewType(final int position) {
        return (position >= this.list.size()) ? NewsAdapter.ITEM_LOADER : NewsAdapter.ITEM_NEWS;
    }

    private class ViewHolder {
        TextView textView;

        public ViewHolder(final View view) {
            this.textView = (TextView)view.findViewById(android.R.id.text1);
        }
    }


    private class ThumbnailLoader extends BitmapLoader {

        private final TextView textView;

        public ThumbnailLoader(final TextView textView) {
            super(textView.getContext());
            this.textView = textView;
        }


        @Override
        protected void onPreExecute() {
            this.textView.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.mock_news_list_thumbnail,
                    0,
                    0,
                    0);
        }

        @Override
        protected void onPostExecute(final BitmapInfo result) {

            if ((result.url == this.textView.getTag(R.id.thumbnail)) && (result.bitmap != null)) {
                final BitmapDrawable bitmapDrawable = new BitmapDrawable(this.textView.getContext().getResources(), result.bitmap);
                bitmapDrawable.setBounds(0, 0, 90, 60);

                this.textView.setCompoundDrawables(
                        bitmapDrawable,
                        null,
                        null,
                        null);
            }
        }
    };
}
