package com.queens.flashcards.Logic.Validation;

import com.queens.flashcards.Logic.Exception.EmptyAnswerException;
import com.queens.flashcards.Model.Flashcard.FlashcardAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardMCAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardTFAnswer;
import com.queens.flashcards.Model.Flashcard.FlashcardTextAnswer;

public class FlashcardAnswerValidator {

    //region Public Methods

    /** Checks if the guessed answer is considered "correct", to the FlashcardAnswer passed
     *
     * @param correctAnswer - Holds the correct answer
     * @param guessedAnswer - Answer guessed by a user
     * @return True/false if the guess is correct
     */
    public boolean validateGuessedAnswer(FlashcardAnswer correctAnswer, String guessedAnswer) {
        boolean correct = false;

        if(correctAnswer != null) {
            correct = correctAnswer.getAnswer().equals(guessedAnswer);
        }

        return correct;
    }

    public void validateFlashcardAnswer(FlashcardAnswer a) throws EmptyAnswerException {
        FAValidator validator = FAValidatorFactory.makeValidator(a);
        validator.validateAnswer(a);
    }

    //endregion

}

//region FlashcardAnswer Validator Factory

interface FAValidator {
    void validateAnswer(FlashcardAnswer a) throws EmptyAnswerException;
}

final class FAValidatorFactory {

    static FAValidator makeValidator(FlashcardAnswer a) {
        if (a instanceof FlashcardTextAnswer)
            return new FATextValidator();
        else if (a instanceof FlashcardTFAnswer)
            return new FATrueFalseValidator();
        else if (a instanceof FlashcardMCAnswer)
            return new FAMultipleChoiceValidator();
        else
            throw new IllegalStateException();
    }
}

class FATextValidator implements FAValidator{
    @Override
    public void validateAnswer(FlashcardAnswer a) throws EmptyAnswerException{
        if (!(a instanceof FlashcardTextAnswer))
            throw new IllegalStateException();

        if (a.getAnswer() == null || a.getAnswer().equals(""))
            throw new EmptyAnswerException();
    }
}

class FATrueFalseValidator implements FAValidator {
    @Override
    public void validateAnswer(FlashcardAnswer a) throws EmptyAnswerException {
        if (!(a instanceof FlashcardTFAnswer))
            throw new IllegalStateException();
    }
}

class FAMultipleChoiceValidator implements  FAValidator {
    @Override
    public void validateAnswer(FlashcardAnswer a) throws EmptyAnswerException {
        if (!(a instanceof FlashcardMCAnswer))
            throw new IllegalStateException();

        FlashcardMCAnswer mcAnswer = (FlashcardMCAnswer) a;
        if (mcAnswer.getAnswer() == null || mcAnswer.getAnswer().equals(""))
            throw new EmptyAnswerException();

        for (String wrongAnswer : mcAnswer.getWrongAnswers())
            if (wrongAnswer == null || wrongAnswer.equals(""))
                throw new EmptyAnswerException();
    }
}

//endregion
