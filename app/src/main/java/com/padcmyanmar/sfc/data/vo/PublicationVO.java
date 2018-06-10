package com.padcmyanmar.sfc.data.vo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aung on 12/3/17.
 */

@Entity(tableName = "publication")
public class PublicationVO {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("publication-id")
    private String publicationId;

    @SerializedName("title")
    private String title;

    @SerializedName("logo")
    private String logo;

    public String getPublicationId() {
        return publicationId;
    }

    public String getTitle() {
        return title;
    }

    public String getLogo() {
        return logo;
    }

    public void setPublicationId(String publicationId) {
        this.publicationId = publicationId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
