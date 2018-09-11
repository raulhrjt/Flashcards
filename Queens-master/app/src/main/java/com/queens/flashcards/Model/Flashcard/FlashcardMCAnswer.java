package com.queens.flashcards.Model.Flashcard;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FlashcardMCAnswer extends FlashcardAnswer {

    //region Members
    private String answer;
    private List<String> wrongAnswers;

    //endregion

    //region Constructors
    public FlashcardMCAnswer(String answer, List<String> wrongAnswers) {
        super();
        this.answer = answer;
        this.wrongAnswers = wrongAnswers;
    }

    public FlashcardMCAnswer(FlashcardMCAnswer other) {
        super(other.getId());
        this.answer = other.answer;
        this.wrongAnswers = new ArrayList<>(other.wrongAnswers);
    }

    public FlashcardMCAnswer(String answer, List<String> wrongAnswers, long id) {
        super(id);
        this.answer = answer;
        this.wrongAnswers = wrongAnswers;
    }

    public FlashcardMCAnswer() {
        super();
        this.answer = "";
        this.wrongAnswers = new ArrayList<>();
    }

    //endregion

    //region Getters & Setters

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<String> getWrongAnswers() {
        return wrongAnswers;
    }

    public void setWrongAnswers(List<String> wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    //endregion

    //region Public Methods

    /** Gets the Answer, in String form*/
    public String getAnswer() {
        return answer;
    }

    /**
     * Checks whether this answer is empty or not.
     * @return True if this answer is empty, false otherwise
     */
    public boolean isEmpty() {
        return answer == null || answer.equals("");
    }

    @Override
    public FlashcardAnswer deepCopy() {
        return new FlashcardMCAnswer(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.answer, this.wrongAnswers);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FlashcardMCAnswer))
            return false;

        FlashcardMCAnswer other = (FlashcardMCAnswer) obj;

        return super.equals(other) &&
                this.answer.equals(other.answer) &&
                this.wrongAnswers.equals(other.wrongAnswers);
    }

    //endregion
}
