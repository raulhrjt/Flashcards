package com.queens.flashcards;

import android.content.Context;

import com.queens.flashcards.IntegrationTests.AllIntegrationTests;
import com.queens.flashcards.Presentation.Messages;
import com.queens.flashcards.UnitTests.AllUnitTests;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AllIntegrationTests.class,
        AllUnitTests.class,
})

public class AllTests {
}
