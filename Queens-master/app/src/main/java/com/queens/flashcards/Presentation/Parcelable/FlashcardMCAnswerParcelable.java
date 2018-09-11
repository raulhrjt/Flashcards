package com.queens.flashcards.Presentation.Parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import com.queens.flashcards.Model.Flashcard.FlashcardMCAnswer;

import java.util.ArrayList;
import java.util.List;

public class FlashcardMCAnswerParcelable implements Parcelable {
    //region Members

    private FlashcardMCAnswer mcAnswer;

    //endregion

    //region Constructors

    public FlashcardMCAnswerParcelable(FlashcardMCAnswer mcAnswer) {
        this.mcAnswer = mcAnswer;
    }

    //endregion

    //region Getters & Setters

    public FlashcardMCAnswer getMcAnswer() {
        return mcAnswer;
    }

    public void setMcAnswer(FlashcardMCAnswer mcAnswer) {
        this.mcAnswer = mcAnswer;
    }

    //endregion

    //region Parcelable

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mcAnswer.getId());
        dest.writeString(mcAnswer.getAnswer());
        dest.writeList(mcAnswer.getWrongAnswers());
    }

    protected FlashcardMCAnswerParcelable(Parcel in) {
        Long answerID = in.readLong();
        String answer = in.readString();
        List<String> wrongAnswers = new ArrayList<>();
        in.readList(wrongAnswers, String.class.getClassLoader());

        this.mcAnswer =  new FlashcardMCAnswer(answer, wrongAnswers, answerID);
    }

    public static final Creator<FlashcardMCAnswerParcelable> CREATOR = new Creator<FlashcardMCAnswerParcelable>() {
        @Override
        public FlashcardMCAnswerParcelable createFromParcel(Parcel source) {
            return new FlashcardMCAnswerParcelable(source);
        }

        @Override
        public FlashcardMCAnswerParcelable[] newArray(int size) {
            return new FlashcardMCAnswerParcelable[size];
        }
    };

    //endregion
}
