package com.example.danielgoncalves.listafilmes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by NeoHome on 11/01/2017.
 */

public class MovieEntity implements Parcelable {

    private String coverPath;
    private String title;
    private String synopsis;
    private Double rating;
    private String releaseDate;
    private String detailURL;

    public String getDetailURL() {
        return detailURL;
    }

    public void setDetailURL(String detailURL) {
        this.detailURL = detailURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public String getSynopsis() { return synopsis;  }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public MovieEntity() {
    }

    public MovieEntity(String coverPath, String title, String synopsis, Double rating, String releaseDate, String detailURL) {
        this.coverPath = coverPath;
        this.title = title;
        this.synopsis = synopsis;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.detailURL = detailURL;
    }

    private MovieEntity(Parcel in) {
        this.coverPath = in.readString();
        this.title = in.readString();
        this.synopsis = in.readString();
        this.rating = in.readDouble();
        this.releaseDate = in.readString();
        this.detailURL = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(coverPath);
        parcel.writeString(title);
        parcel.writeString(synopsis);
        parcel.writeDouble(rating);
        parcel.writeString(releaseDate);
        parcel.writeString(detailURL);
    }

    public static final Parcelable.Creator<MovieEntity> CREATOR = new Parcelable.Creator<MovieEntity>() {
        @Override
        public MovieEntity createFromParcel(Parcel parcel) {
            return new MovieEntity(parcel);
        }

        @Override
        public MovieEntity[] newArray(int i) {
            return new MovieEntity[i];
        }

    };

}
