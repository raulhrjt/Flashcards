package com.queens.flashcards.Presentation.Parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import com.queens.flashcards.Model.Category.Category;

public class CategoryParcelable implements Parcelable{
    private Category category;

    public CategoryParcelable(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    //region Parcel

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(category.getId());
        dest.writeString(category.getName());
    }

    protected CategoryParcelable(Parcel in) {
        Long id = in.readLong();
        String name = in.readString();
        this.category = new Category(name, id);
    }

    public static final Creator<CategoryParcelable> CREATOR = new Creator<CategoryParcelable>() {
        @Override
        public CategoryParcelable createFromParcel(Parcel source) {
            return new CategoryParcelable(source);
        }

        @Override
        public CategoryParcelable[] newArray(int size) {
            return new CategoryParcelable[size];
        }
    };

    //endregion

}
