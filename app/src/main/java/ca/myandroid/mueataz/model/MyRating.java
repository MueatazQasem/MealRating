package ca.myandroid.mueataz.model;
import android.os.Parcel;
import android.os.Parcelable;
public class MyRating implements Parcelable {
    private String item;
    private float rating;
    public MyRating(String item, float rating) {
        this.item = item;
        this.rating = rating;
    }
    @Override
    public String toString() {
        return "Item: " + item + ", Rating: " + rating;
    }
    protected MyRating(Parcel in) {
        item = in.readString();
        rating = in.readFloat();
    }
    public static final Creator<MyRating> CREATOR = new Creator<MyRating>() {
        @Override
        public MyRating createFromParcel(Parcel in) {
            return new MyRating(in);
        }

        @Override
        public MyRating[] newArray(int size) {
            return new MyRating[size];
        }
    };

    public String getItem() {
        return item;
    }

    public float getRating() {
        return rating;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(item);
        dest.writeFloat(rating);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}


