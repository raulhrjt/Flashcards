package com.queens.flashcards.Presentation.Parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import com.queens.flashcards.Model.Flashcard.FlashcardTextAnswer;

public class FlashcardTextAnswerParcelable implements Parcelable {
    //region Members

    private FlashcardTextAnswer textAnswer;

    //endregion

    //region Constructors

    public FlashcardTextAnswerParcelable(FlashcardTextAnswer flashcardTextAnswer) {
        this.textAnswer = flashcardTextAnswer;
    }

    //endregion

    //region Getters & Setters

    public FlashcardTextAnswer getTextAnswer() {
        return textAnswer;
    }

    public void setTextAnswer(FlashcardTextAnswer textAnswer) {
        this.textAnswer = textAnswer;
    }

    //endregion

    //region Parcelable

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(textAnswer.getId());
        dest.writeString(textAnswer.getAnswer());
    }

    protected FlashcardTextAnswerParcelable(Parcel in) {
        Long answerID = in.readLong();
        String answerText = in.readString();

        this.textAnswer = new FlashcardTextAnswer(answerText, answerID);
    }

    public static final Creator<FlashcardTextAnswerParcelable> CREATOR = new Creator<FlashcardTextAnswerParcelable>() {
        @Override
        public FlashcardTextAnswerParcelable createFromParcel(Parcel source) {
            return new FlashcardTextAnswerParcelable(source);
        }

        @Override
        public FlashcardTextAnswerParcelable[] newArray(int size) {
            return new FlashcardTextAnswerParcelable[size];
        }
    };

    //endregion
}
