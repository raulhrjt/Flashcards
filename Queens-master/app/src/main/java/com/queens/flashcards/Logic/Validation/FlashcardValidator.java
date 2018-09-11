package com.queens.flashcards.Logic.Validation;

import com.queens.flashcards.Logic.Exception.EmptyAnswerException;
import com.queens.flashcards.Logic.Exception.EmptyNameException;
import com.queens.flashcards.Logic.Exception.EmptyQuestionException;
import com.queens.flashcards.Model.Flashcard.Flashcard;

public class FlashcardValidator {
    //region Public Methods

    public void validateFlashcard(Flashcard f) throws EmptyNameException, EmptyQuestionException, EmptyAnswerException {

        if (f.getName().equals(""))
            throw new EmptyNameException();
        if(f.getQuestion().equals(""))
            throw new EmptyQuestionException();
        if(f.getAnswer() == null || f.getAnswer().isEmpty())
            throw new EmptyAnswerException();
        if (f.getAnswer() != null) {
            FAValidator faValidator = FAValidatorFactory.makeValidator(f.getAnswer());
            faValidator.validateAnswer(f.getAnswer());
        }

    }

    //endregion
}
