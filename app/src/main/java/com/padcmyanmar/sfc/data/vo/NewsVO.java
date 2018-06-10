package com.padcmyanmar.sfc.data.vo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aung on 12/2/17.
 */
@Entity(tableName = "news")
public class NewsVO {

    @PrimaryKey
    @SerializedName("news-id")
    @NonNull
    private String newsId;

    @SerializedName("brief")
    private String brief;

    @SerializedName("details")
    private String details;

    @Ignore
    @SerializedName("images")
    private List<String> images;

    @SerializedName("posted-date")
    private String postedDate;

    @Ignore
    @SerializedName("publication")
    private PublicationVO publication;

    @ColumnInfo(name = "publication-id")
    private String publicationId;

    public String getPublicationId() {
        if(publication != null) {
            return publication.getPublicationId();
        }
        return null;
    }

    /*
    public void setPublicationId(String publicationId) {
        this.publicationId = publicationId;
    }
    */

    @Ignore
    @SerializedName("favorites")
    private List<FavoriteActionVO> favoriteActions;

    @Ignore
    @SerializedName("comments")
    private List<CommentActionVO> commentActions;

    @Ignore
    @SerializedName("sent-tos")
    private List<SentToVO> sentToActions;

    public String getNewsId() {
        return newsId;
    }

    public String getBrief() {
        return brief;
    }

    public String getDetails() {
        return details;
    }

    public List<String> getImages() {
        if(images == null)
            return new ArrayList<>();

        return images;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public PublicationVO getPublication() {
        return publication;
    }

    public List<FavoriteActionVO> getFavoriteActions() {
        return favoriteActions;
    }

    public List<CommentActionVO> getCommentActions() {
        return commentActions;
    }

    public List<SentToVO> getSentToActions() {
        return sentToActions;
    }

    public void setNewsId(@NonNull String newsId) {
        this.newsId = newsId;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public void setPublication(PublicationVO publication) {
        this.publication = publication;
    }

    public void setPublicationId(String publicationId) {
        this.publicationId = publicationId;
    }

    public void setFavoriteActions(List<FavoriteActionVO> favoriteActions) {
        this.favoriteActions = favoriteActions;
    }

    public void setCommentActions(List<CommentActionVO> commentActions) {
        this.commentActions = commentActions;
    }

    public void setSentToActions(List<SentToVO> sentToActions) {
        this.sentToActions = sentToActions;
    }
}
