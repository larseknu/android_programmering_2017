package no.hiof.larseknu.playingwithgooglemaps;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by larseknu on 04/04/2017.
 */

public class KittenLocation implements Parcelable {
    private String name;
    private LatLng latLng;

    public KittenLocation(String name, LatLng latLng) {
        this.setName(name);
        this.setLatLng(latLng);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public KittenLocation(Parcel in) {
        name = in.readString();
        latLng = new LatLng(in.readDouble(), in.readDouble());
    }

    public final Parcelable.Creator<KittenLocation> CREATOR = new Parcelable.Creator<KittenLocation>() {
        public KittenLocation createFromParcel(Parcel in) {
            return new KittenLocation(in);
        }

        public KittenLocation[] newArray(int size) {
            return new KittenLocation[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(latLng.latitude);
        dest.writeDouble(latLng.longitude);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return name + " " + latLng.latitude + " " + latLng.longitude;
    }
}
