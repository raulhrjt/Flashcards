package com.queens.flashcards.Model.Flashcard;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Model for a base Flashcard.
 */
public class Flashcard {

    // region Members

    private long id = -1;   // Default to -1 to indicate an invalid ID
    private String name = "";
    private String question = "";
    private FlashcardAnswer answer = new FlashcardTextAnswer();
    private String imageLocation = "";

    //endregion

    //region Constructor

    public Flashcard() {

    }

    public Flashcard(Flashcard other) {
        this.id = other.id;
        this.name = other.name;
        this.question = other.question;
        this.imageLocation = other.imageLocation;
        this.answer = other.answer != null ? other.answer.deepCopy() : null;
    }

    public Flashcard(String name, String question, FlashcardAnswer answer) {
        this.name = name;
        this.question = question;
        this.answer = answer;
        this.imageLocation = "";
    }

    public Flashcard(String name, String question, FlashcardAnswer answer, long id) {
        this.name = name;
        this.question = question;
        this.answer = answer;
        this.id = id;
        this.imageLocation = "";
    }

    public Flashcard(String name, String question, FlashcardAnswer answer, long id, String imageLocation) {
        this.name = name;
        this.question = question;
        this.answer = answer;
        this.id = id;
        this.imageLocation = imageLocation;
    }

    //endregion

    //region Getters & Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public FlashcardAnswer getAnswer() {
        return answer;
    }

    public void setAnswer(FlashcardAnswer answer) {
        this.answer = answer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    //endregion

    //region Public Methods

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.question, this.answer.hashCode(), this.imageLocation);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Flashcard))
            return false;

        Flashcard other = (Flashcard) obj;

        return this.id == other.id &&
                this.name.equals(other.name) &&
                this.question.equals(other.question) &&
                this.imageLocation.equals(other.imageLocation) &&
                this.answer != null ? this.answer.equals(other.answer) : other.answer == null;
    }

    //endregion

}
