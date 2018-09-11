package com.queens.flashcards.Persistence.stubs;

import com.queens.flashcards.Model.FlashcardCategory;
import com.queens.flashcards.Persistence.Interfaces.FCPersistence;

import java.util.ArrayList;
import java.util.List;

public class FCPersistenceStub implements FCPersistence {

    //region Members

    private List<FlashcardCategory> flashcardCategories = new ArrayList<>();

    //endregion

    //region Constructors

    public FCPersistenceStub() {
        flashcardCategories.add(new FlashcardCategory(1, 1));
        flashcardCategories.add(new FlashcardCategory(2, 2));
        flashcardCategories.add(new FlashcardCategory(3, 3));
        flashcardCategories.add(new FlashcardCategory(4, 4));
        flashcardCategories.add(new FlashcardCategory(5, 5));
        flashcardCategories.add(new FlashcardCategory(6, 6));
        flashcardCategories.add(new FlashcardCategory(7, 7));
        flashcardCategories.add(new FlashcardCategory(8, 8));
    }

    //endregion

    //region FCPersistence Methods


    @Override
    public List<FlashcardCategory> getAllFlashcardCategories() {
        return new ArrayList<>(flashcardCategories);
    }

    @Override
    public List<Long> getFlashcardsInCategory(long categoryId) {
        List<Long> flashcardIds = new ArrayList<>();

        for (FlashcardCategory fc : flashcardCategories)
            if (fc.getCategoryId() == categoryId)
                flashcardIds.add(fc.getFlashcardId());

        return flashcardIds;
    }

    @Override
    public List<Long> getCategoriesWithFlashcard(long flashcardId) {
        List<Long> categoryIds = new ArrayList<>();

        for (FlashcardCategory fc : flashcardCategories)
            if (fc.getFlashcardId() == flashcardId)
                categoryIds.add(fc.getCategoryId());

        return categoryIds;
    }

    @Override
    public boolean createFlashcardCategory(long flashcardId, long categoryId) {
        for (FlashcardCategory fc : flashcardCategories)
            if (fc.getCategoryId() == categoryId && fc.getFlashcardId() == flashcardId)
                return false;

        flashcardCategories.add(new FlashcardCategory(flashcardId, categoryId));
        return true;
    }

    @Override
    public boolean deleteFlashcardCategory(long flashcardId, long categoryId) {
        for (FlashcardCategory fc : flashcardCategories) {
            if (fc.getFlashcardId() == flashcardId && fc.getCategoryId() == categoryId) {
                flashcardCategories.remove(fc);
                return true;
            }
        }

        return false;
    }

    @Override
    public int removeFlashcard(long flashcardId) {
        List<FlashcardCategory> toDelete = new ArrayList<>(flashcardCategories.size());

        for (FlashcardCategory fc : flashcardCategories)
            if (fc.getFlashcardId() == flashcardId)
                toDelete.add(fc);

        flashcardCategories.removeAll(toDelete);
        return toDelete.size();
    }

    @Override
    public int removeCategory(long categoryId) {
        List<FlashcardCategory> toDelete = new ArrayList<>(flashcardCategories.size());

        for (FlashcardCategory fc : flashcardCategories)
            if (fc.getCategoryId() == categoryId)
                toDelete.add(fc);

        flashcardCategories.removeAll(toDelete);
        return toDelete.size();
    }

    //endregion

}
