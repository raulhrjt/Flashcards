package com.queens.flashcards.UnitTests;

import com.queens.flashcards.UnitTests.Logic.CategoryManagementServiceTest;
import com.queens.flashcards.UnitTests.Logic.FCManagementServiceTest;
import com.queens.flashcards.UnitTests.Logic.FlashcardManagementTest;
import com.queens.flashcards.UnitTests.Logic.ImageServiceTest;
import com.queens.flashcards.UnitTests.Model.CategoryTest;
import com.queens.flashcards.UnitTests.Model.FlashcardAnswerTest;
import com.queens.flashcards.UnitTests.Model.FlashcardTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        FlashcardTest.class,
        FlashcardAnswerTest.class,
        CategoryTest.class,
        FlashcardManagementTest.class,
        CategoryManagementServiceTest.class,
        FCManagementServiceTest.class,
        ImageServiceTest.class
})

public class AllUnitTests {
}
