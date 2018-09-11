package com.queens.flashcards.Presentation.Parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import com.queens.flashcards.Model.Flashcard.FlashcardTFAnswer;

public class FlashcardTFAnswerParcelable implements Parcelable {
    //region Members

    private FlashcardTFAnswer trueFalseAnswer;

    //endregion

    //region Constructors

    public FlashcardTFAnswerParcelable(FlashcardTFAnswer trueFalseAnswer) {
        this.trueFalseAnswer = trueFalseAnswer;
    }

    //endregion

    //region Getters & Setters

    public FlashcardTFAnswer getTrueFalseAnswer() {
        return trueFalseAnswer;
    }

    public void setTrueFalseAnswer(FlashcardTFAnswer trueFalseAnswer) {
        this.trueFalseAnswer = trueFalseAnswer;
    }

    //endregion

    //region Parcelable

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(trueFalseAnswer.getId());
        dest.writeByte((byte) (trueFalseAnswer.getAnswerIsTrue() ? 1 : 0));
    }

    protected FlashcardTFAnswerParcelable(Parcel in) {
        Long answerID = in.readLong();
        boolean answer = in.readByte() != 0;

        this.trueFalseAnswer = new FlashcardTFAnswer(answer, answerID);
    }

    public static final Creator<FlashcardTFAnswerParcelable> CREATOR = new Creator<FlashcardTFAnswerParcelable>() {
        @Override
        public FlashcardTFAnswerParcelable createFromParcel(Parcel source) {
            return new FlashcardTFAnswerParcelable(source);
        }

        @Override
        public FlashcardTFAnswerParcelable[] newArray(int size) {
            return new FlashcardTFAnswerParcelable[size];
        }
    };

    //endregion
}
