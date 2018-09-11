package com.queens.flashcards.Model.Category;

import com.queens.flashcards.Model.Flashcard.Flashcard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Model for a Category that is associated with multiple Flashcards.
 */
public class Category {

    //region Members

    private long id = -1l;
    private String name;
    private Set<Long> flashcardIds = new HashSet<>();

    //endregion

    //region Constructors

    public Category(String name) {
        this.name = name;
    }

    public Category(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    public Category(String name, Long id, List<Long> flashcardIds) {
        this.name = name;
        this.id = id;
        this.flashcardIds.addAll(flashcardIds);
    }

    //endregion

    //region Getters & Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Long> getFlashcardIds() {
        return flashcardIds;
    }

    //endregion

    //region Public Methods

    /**
     * Adds the Flashcard ID to this Category.
     * @param flashcardId The Flashcard ID to add.
     * @return True if the addition was successful, false otherwise.
     */
    public boolean add(long flashcardId) {
        return flashcardIds.add(flashcardId);
    }

    /**
     * Removes the Flashcard ID from this Category.
     * @param flashcardId The Flashcard ID to remove.
     * @return True if the removal was successful, false otherwise.
     */
    public boolean remove(long flashcardId) {
        return flashcardIds.remove(flashcardId);
    }

    /**
     * Removes all Flashcards from the Category.
     */
    public void clear() {
        this.flashcardIds.clear();
    }

    /**
     * Checks if the Flashcard is in this Category.
     * @param f The Flashcard to search for.
     * @return True if the Flashcard is in this Category, false otherwise.
     */
    public boolean contains(Flashcard f) {
        return flashcardIds.contains(f.getId());
    }

    //endregion


    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.flashcardIds);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Category))
            return false;

        Category other = (Category) obj;

        return this.id == other.id &&
                this.name.equals(other.name) &&
                this.flashcardIds.equals(other.flashcardIds);
    }

}
