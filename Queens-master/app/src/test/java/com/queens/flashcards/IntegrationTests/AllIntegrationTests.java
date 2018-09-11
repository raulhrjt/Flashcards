package com.queens.flashcards.IntegrationTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CategoryManagementServiceTest.class,
        FCManagementServiceTest.class,
        FlashcardManagementServiceTest.class,
        ImageServiceTest.class
})
public class AllIntegrationTests {
}
