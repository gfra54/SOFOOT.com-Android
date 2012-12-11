package com.sofoot.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsMeta implements Parcelable
{

    private final int id;

    private final String titre;

    private final String url;

    public NewsMeta(final Parcel in) {
        final String[] strings = new String[3];
        in.readStringArray(strings);

        this.id = Integer.parseInt(strings[0]);
        this.titre = strings[1];
        this.url = strings[2];
    }

    public NewsMeta(final News news) {
        this.id = news.getId();
        this.titre = news.getTitre();
        this.url = news.getUrl().toString();
    }

    public int getId() {
        return this.id;
    }

    public String getTitre() {
        return this.titre;
    }

    public String getUrl() {
        return this.url;
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel out, final int arg1) {
        out.writeStringArray(new String[] {
                String.valueOf(this.id),
                this.titre,
                this.url
        });
    }

    public static final Parcelable.Creator<NewsMeta> CREATOR = new Parcelable.Creator<NewsMeta>() {
        @Override
        public NewsMeta createFromParcel(final Parcel in) {
            return new NewsMeta(in);
        }

        @Override
        public NewsMeta[] newArray(final int size) {
            return new NewsMeta[size];
        }
    };
}
