package com.queens.flashcards.Presentation.Parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Model.Flashcard.FlashcardAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardMCAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardTFAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardTextAnswer;
import com.queens.flashcards.Presentation.Parcelable.Factory.FlashcardAnswerParcelableFactory;
import com.queens.flashcards.Presentation.Parcelable.Factory.FlashcardAnswerParcelableFactoryMaker;

import java.util.ArrayList;
import java.util.List;

public class FlashcardParcelable implements Parcelable {
    //region Members
    private Flashcard flashcard;

    //endregion

    //region Constructors
    public FlashcardParcelable(Flashcard flashcard) {
        this.flashcard = flashcard;
    }
    //endregion

    //region Getters & Setters

    public Flashcard getFlashcard() {
        return flashcard;
    }

    public void setFlashcard(Flashcard flashcard) {
        this.flashcard = flashcard;
    }

    //endregion

    //region Parcelable

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(flashcard.getQuestion());
        dest.writeLong(flashcard.getId());
        dest.writeString(flashcard.getName());
        dest.writeString(flashcard.getImageLocation());

        // Write answer to parcel
        FlashcardAnswer answer = flashcard.getAnswer();
        FlashcardAnswerParcelableFactory parcelableFactory = FlashcardAnswerParcelableFactoryMaker.makeFactory(answer);
        Parcelable parcelable = parcelableFactory.getFlashcardAnswerParcelable();
        dest.writeParcelable(parcelable, flags);
    }

    protected FlashcardParcelable(Parcel in) {
        String question = in.readString();
        Long flashcardId = in.readLong();
        String flashcardName = in.readString();
        List<Long> categories = new ArrayList<>();
        in.readList(categories, Long.class.getClassLoader());

        String imageLocation = in.readString();

        FlashcardAnswer answer;

        FlashcardTextAnswerParcelable textAnswerParcelable = in.readParcelable(FlashcardTextAnswerParcelable.class.getClassLoader());
        answer = textAnswerParcelable.getTextAnswer();

        if(answer == null) {
            FlashcardTFAnswerParcelable trueFalseParcelable = in.readParcelable(FlashcardTFAnswerParcelable.class.getClassLoader());
            answer = trueFalseParcelable.getTrueFalseAnswer();
        }

        if(answer == null) {
            FlashcardMCAnswerParcelable mcAnswerParcelable = in.readParcelable(FlashcardMCAnswerParcelable.class.getClassLoader());
            answer = mcAnswerParcelable.getMcAnswer();
        }

        this.flashcard = new Flashcard(flashcardName, question, answer, flashcardId, imageLocation);
    }

    public static final Creator<FlashcardParcelable> CREATOR = new Creator<FlashcardParcelable>() {
        @Override
        public FlashcardParcelable createFromParcel(Parcel source) {
            return new FlashcardParcelable(source);
        }

        @Override
        public FlashcardParcelable[] newArray(int size) {
            return new FlashcardParcelable[size];
        }
    };

    //endregion


}
