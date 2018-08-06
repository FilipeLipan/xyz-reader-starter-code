package com.example.xyzreader.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleVO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class NewArticleDetailActivity extends AppCompatActivity {

    private static final String TAG = "NewArticleDetail";

    public static final String ARG_ARTICLE_VO = "article-vo";
    private ArticleVO mArticleVo;
    private Toolbar mToolbar;
    private TextView mBylineView;
    private ImageView mPhotoView;
    private TextView mBodyView;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss", Locale.getDefault());
    // Use default locale format
    private SimpleDateFormat outputFormat = new SimpleDateFormat();
    // Most time functions can only handle 1902 - 2037
    private GregorianCalendar START_OF_EPOCH = new GregorianCalendar(2,1,1);

    public static Intent getIntent(Context context, ArticleVO articleVO){
        Intent intent = new Intent(context, NewArticleDetailActivity.class);
        intent.putExtra(ARG_ARTICLE_VO, articleVO);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_article_detail_activity);
        bindViews();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            if (getIntent() != null && getIntent().hasExtra(ARG_ARTICLE_VO)) {
                mArticleVo = getIntent().getParcelableExtra(ARG_ARTICLE_VO);
                setUpViews();
            }
        }
    }

    private void bindViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mBylineView = (TextView) findViewById(R.id.article_byline);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_tollbar);
        mPhotoView = (ImageView) findViewById(R.id.photo);
        mBodyView = (TextView) findViewById(R.id.article_body);
        ViewGroup view = (ViewGroup) findViewById(R.id.root_container);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int viewId = view.getId();
            }
        });

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.main_appbar_layout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    // Fully expanded
                    mBylineView.setVisibility(View.VISIBLE);
                } else {
                    // Not fully expanded or collapsed
                    mBylineView.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void setUpViews() {

        mBodyView.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "Rosario-Regular.ttf"));

        if (mArticleVo != null) {
            mCollapsingToolbarLayout.setTitle(mArticleVo.getTitle());
            Date publishedDate = parsePublishedDate();
            if (!publishedDate.before(START_OF_EPOCH.getTime())) {
                mBylineView.setText(Html.fromHtml(
                        DateUtils.getRelativeTimeSpanString(
                                publishedDate.getTime(),
                                System.currentTimeMillis(), DateUtils.HOUR_IN_MILLIS,
                                DateUtils.FORMAT_ABBREV_ALL).toString()
                                + " by <b>"
                                + mArticleVo.getAuthor()
                                + "</b>"));

            } else {
                // If date is before 1902, just show the string
                mBylineView.setText(Html.fromHtml(
                        outputFormat.format(publishedDate) + " by <b>"
                                + mArticleVo.getAuthor()
                                + "</b>"));

            }
            mBodyView.setText(Html.fromHtml(mArticleVo.getBody().replaceAll("(\r\n|\n)", "<br />")));

            Glide.with(this)
                    .load(mArticleVo.getThumbUrl())
                    .apply(new RequestOptions().centerCrop().placeholder(R.color.photo_placeholder))
                    .into(mPhotoView);

            findViewById(R.id.share_fab).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(NewArticleDetailActivity.this)
                            .setType("text/plain")
                            .setText(mArticleVo.getTitle())
                            .getIntent(), getString(R.string.action_share)));
                }
            });

        } else {
            getSupportActionBar().setTitle("N/A");
            mBylineView.setText("N/A" );
            mBodyView.setText("N/A");
        }
    }

    private Date parsePublishedDate() {
        try {
            String date = mArticleVo.getPublishedDate();
            return dateFormat.parse(date);
        } catch (ParseException ex) {
            Log.e(TAG, ex.getMessage());
            Log.i(TAG, "passing today's date");
            return new Date();
        }
    }
}
