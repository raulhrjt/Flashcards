package com.queens.flashcards.IntegrationTests;

import com.queens.flashcards.Logic.ImageService;
import com.queens.flashcards.Services;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class ImageServiceTest {
    private ImageService imageService;

    @Before
    public void setUp() {
        imageService = new ImageService(Services.getImagePersistence());
    }

    @Test
    public void testSaveNewImage() {
        byte[] image = null;
        String filepath = imageService.saveNewImage(image);

        assertTrue(filepath == null || filepath.equals(""));
    }

    @Test
    public void testSaveImage() {
        byte[] image = null;
        boolean saved = imageService.saveImage(image, "test/path.png");

        assertTrue(!saved);
    }
}
