package com.queens.flashcards.Persistence.stubs;

import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Model.Flashcard.FlashcardTFAnswer;
import com.queens.flashcards.Persistence.Interfaces.FATrueFalsePersistence;

import java.util.HashMap;
import java.util.Map;

public class FATrueFalsePersistenceStub implements FATrueFalsePersistence {

    //region Members

    private Map<Long, FlashcardTFAnswer> answers = new HashMap<>();
    private long nextAnswerId = 1;

    //endregion

    //region Constructors

    public FATrueFalsePersistenceStub() {
        answers.put(5L, new FlashcardTFAnswer(false, nextAnswerId++));
        answers.put(6L, new FlashcardTFAnswer(true, nextAnswerId++));
    }

    //endregion

    //region FATrueFalsePersistence Methods

    @Override
    public FlashcardTFAnswer getTFAnswerFor(Flashcard flashcard) {
        for (Long flashcardId : answers.keySet())
            if (flashcardId == flashcard.getId())
                return answers.get(flashcardId);

        return null;
    }

    @Override
    public FlashcardTFAnswer createTFAnswerFor(FlashcardTFAnswer answer, Flashcard flashcard) {
        if (!answers.keySet().contains(flashcard.getId())) {
            answer.setId(nextAnswerId++);
            answers.put(flashcard.getId(), answer);
        }

        return answer;
    }

    @Override
    public boolean deleteTFAnswerFor(Flashcard flashcard) {
        if (answers.containsKey(flashcard.getId())) {
            answers.remove(flashcard.getId());
            return true;
        }

        return false;
    }

    @Override
    public boolean updateTFAnswerFor(FlashcardTFAnswer answer, Flashcard flashcard) {
        if (answers.containsKey(flashcard.getId())) {
            answers.get(flashcard.getId()).setAnswerIsTrue(answer.getAnswerIsTrue());
            return true;
        }

        return false;
    }


    //endregion

}
