package com.queens.flashcards.Logic.Validation;

import com.queens.flashcards.Logic.Exception.EmptyNameException;
import com.queens.flashcards.Model.Category.Category;

public class CategoryValidator {

    //region Public Methods

    public void validateCategory(Category c) throws EmptyNameException {

        if (c.getName() == null || c.getName().equals(""))
            throw new EmptyNameException();
    }

    //endregion
}
