package com.queens.flashcards.Persistence.stubs;

import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Model.Flashcard.FlashcardTextAnswer;
import com.queens.flashcards.Persistence.Interfaces.FATextPersistence;

import java.util.HashMap;
import java.util.Map;

public class FATextPersistenceStub implements FATextPersistence {

    //region Members

    private Map<Long, FlashcardTextAnswer> answers = new HashMap<>();
    private long nextAnswerId = 1;

    //endregion

    //region Constructors

    public FATextPersistenceStub() {
        answers.put(2L, new FlashcardTextAnswer("Answer2", nextAnswerId++));
        answers.put(3L, new FlashcardTextAnswer("Answer3", nextAnswerId++));
        answers.put(4L, new FlashcardTextAnswer("Answer4", nextAnswerId++));
        answers.put(5L, new FlashcardTextAnswer("Answer5", nextAnswerId++));
        answers.put(6L, new FlashcardTextAnswer("Answer6", nextAnswerId++));
        answers.put(7L, new FlashcardTextAnswer("Answer7", nextAnswerId++));
        answers.put(8L, new FlashcardTextAnswer("Answer8", nextAnswerId++));
    }

    //endregion

    //region FATextPersistence Methods

    @Override
    public FlashcardTextAnswer getTextAnswerFor(Flashcard flashcard) {
        for (Long flashcardId : answers.keySet())
            if (flashcardId == flashcard.getId())
                return answers.get(flashcardId);

        return null;
    }

    @Override
    public FlashcardTextAnswer createTextAnswerFor(FlashcardTextAnswer answer, Flashcard flashcard) {
        if (!answers.keySet().contains(flashcard.getId())) {
            answer.setId(nextAnswerId++);
            answers.put(flashcard.getId(), answer);
        }

        return answer;
    }

    @Override
    public boolean deleteTextAnswerFor(Flashcard flashcard) {
        if (answers.containsKey(flashcard.getId())) {
            answers.remove(flashcard.getId());
            return true;
        }

        return false;
    }

    @Override
    public boolean updateTextAnswerFor(FlashcardTextAnswer answer, Flashcard flashcard) {
        if (answers.containsKey(flashcard.getId())) {
            answers.get(flashcard.getId()).setTextAnswer(answer.getAnswer());
            return true;
        }

        return false;
    }

    //endregion

}
