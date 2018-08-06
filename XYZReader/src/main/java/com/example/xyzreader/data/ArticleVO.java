package com.example.xyzreader.data;

import android.os.Parcel;
import android.os.Parcelable;

public class ArticleVO implements Parcelable {
    private long id;
    private String title;
    private String author;
    private String body;
    private String thumbUrl;
    private String photoUrl;
    private String aspectRatio;
    private String publishedDate;

    public long getId() {
        return id;
    }

    public ArticleVO setId(long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ArticleVO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public ArticleVO setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getBody() {
        return body;
    }

    public ArticleVO setBody(String body) {
        this.body = body;
        return this;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public ArticleVO setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
        return this;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public ArticleVO setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
        return this;
    }

    public String getAspectRatio() {
        return aspectRatio;
    }

    public ArticleVO setAspectRatio(String aspectRatio) {
        this.aspectRatio = aspectRatio;
        return this;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public ArticleVO setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
        return this;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.author);
        dest.writeString(this.body);
        dest.writeString(this.thumbUrl);
        dest.writeString(this.photoUrl);
        dest.writeString(this.aspectRatio);
        dest.writeString(this.publishedDate);
    }

    public ArticleVO() {
    }

    protected ArticleVO(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.author = in.readString();
        this.body = in.readString();
        this.thumbUrl = in.readString();
        this.photoUrl = in.readString();
        this.aspectRatio = in.readString();
        this.publishedDate = in.readString();
    }

    public static final Parcelable.Creator<ArticleVO> CREATOR = new Parcelable.Creator<ArticleVO>() {
        @Override
        public ArticleVO createFromParcel(Parcel source) {
            return new ArticleVO(source);
        }

        @Override
        public ArticleVO[] newArray(int size) {
            return new ArticleVO[size];
        }
    };
}
