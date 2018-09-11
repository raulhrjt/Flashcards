package com.queens.flashcards.Model.Flashcard;

import java.util.Objects;

public class FlashcardTextAnswer extends FlashcardAnswer {
    //region Members

    private String textAnswer;

    //endregion

    //region Constructors

    public FlashcardTextAnswer(FlashcardTextAnswer other) {
        super(other.getId());
        this.textAnswer = other.textAnswer;
    }

    public FlashcardTextAnswer(String textAnswer, long id) {
        super(id);
        this.textAnswer = textAnswer;
    }

    public FlashcardTextAnswer(String textAnswer) {
        super();
        this.textAnswer = textAnswer;
    }

    public FlashcardTextAnswer() {
        super();
        this.textAnswer = "";
    }

    //endregion

    //region Getters & Setters

    public void setTextAnswer(String textAnswer) {
        this.textAnswer = textAnswer;
    }

    //endregion

    //region Public Methods

    /** Gets the Answer, in String form*/
    public String getAnswer() {
        return textAnswer;
    }

    /**
     * Checks whether this answer is empty or not.
     * @return True if this answer is empty, false otherwise
     */
    public boolean isEmpty() {
        return this.textAnswer == null || this.textAnswer.equals("");
    }

    @Override
    public FlashcardAnswer deepCopy() {
        return new FlashcardTextAnswer(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.textAnswer);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FlashcardTextAnswer))
            return false;

        FlashcardTextAnswer other = (FlashcardTextAnswer) obj;

        return super.equals(other) &&
                this.textAnswer.equals(other.textAnswer);
    }

    //endregion
}
