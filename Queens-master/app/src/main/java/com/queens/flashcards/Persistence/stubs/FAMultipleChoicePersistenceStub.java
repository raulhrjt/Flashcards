package com.queens.flashcards.Persistence.stubs;

import com.queens.flashcards.Model.Flashcard.Flashcard;
import com.queens.flashcards.Model.Flashcard.FlashcardMCAnswer;
import com.queens.flashcards.Persistence.Interfaces.FAMultipleChoicePersistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FAMultipleChoicePersistenceStub implements FAMultipleChoicePersistence {

    //region Members

    private Map<Long, FlashcardMCAnswer> answers = new HashMap<>();
    private long nextAnswerId = 1;

    //endregion

    //region Constructors

    public FAMultipleChoicePersistenceStub() {
        answers.put(1L, new FlashcardMCAnswer("Answer7", new ArrayList<String>() {{add("Wrong1"); add("Wrong2"); add("Wrong3");}}, nextAnswerId++));
        answers.put(8L, new FlashcardMCAnswer("Answer8", new ArrayList<String>() {{add("Wrong4"); add("Wrong5"); add("Wrong6");}}, nextAnswerId++));
    }

    //endregion

    //region FAMultipleChoicePersistence Methods

    @Override
    public FlashcardMCAnswer getMCAnswerFor(Flashcard flashcard) {
        for (Long flashcardId : answers.keySet())
            if (flashcardId == flashcard.getId())
                return answers.get(flashcardId);

        return null;
    }

    @Override
    public FlashcardMCAnswer createMCAnswerFor(FlashcardMCAnswer answer, Flashcard flashcard) {
        if (!answers.keySet().contains(flashcard.getId())) {
            answer.setId(nextAnswerId++);
            answers.put(flashcard.getId(), answer);
        }

        return answer;
    }

    @Override
    public boolean deleteMCAnswerFor(Flashcard flashcard) {
        if (answers.containsKey(flashcard.getId())) {
            answers.remove(flashcard.getId());
            return true;
        }

        return false;
    }

    @Override
    public boolean updateMCAnswerFor(FlashcardMCAnswer answer, Flashcard flashcard) {
        if (answers.containsKey(flashcard.getId())) {
            FlashcardMCAnswer exist = answers.get(flashcard.getId());
            exist.setAnswer(answer.getAnswer());
            exist.setWrongAnswers(answer.getWrongAnswers());
            return true;
        }

        return false;
    }

    //endregion

}
