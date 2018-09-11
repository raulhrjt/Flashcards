package com.queens.flashcards.Model.Flashcard;

import java.util.Objects;

public class FlashcardTFAnswer extends FlashcardAnswer {
    //region Members
    private boolean answerIsTrue;

    //endregion

    //region Constructors
    public FlashcardTFAnswer(boolean answerIsTrue) {
        super();
        this.answerIsTrue = answerIsTrue;
    }

    public FlashcardTFAnswer(FlashcardTFAnswer other) {
        super(other.getId());
        this.answerIsTrue = other.answerIsTrue;
    }

    public FlashcardTFAnswer(boolean answerIsTrue, long id) {
        super(id);
        this.answerIsTrue = answerIsTrue;
    }

    public FlashcardTFAnswer() {
        super();
        this.answerIsTrue = true;
    }

    //endregion

    //region Getters & Setters

    public void setAnswerIsTrue(boolean answerIsTrue) {
        this.answerIsTrue = answerIsTrue;
    }

    public boolean getAnswerIsTrue() {
        return answerIsTrue;
    }

    //endregion

    //region Public Methods

    /** Gets the Answer, in String form*/
    public String getAnswer() {
        return answerIsTrue + "";
    }

    /**
     * Can't ever be empty
     * @return False
     */
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.answerIsTrue);
    }

    @Override
    public FlashcardAnswer deepCopy() {
        return new FlashcardTFAnswer(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FlashcardTFAnswer))
            return false;

        FlashcardTFAnswer other = (FlashcardTFAnswer) obj;

        return super.equals(other) &&
                this.answerIsTrue == other.answerIsTrue;
    }

    //endregion
}
