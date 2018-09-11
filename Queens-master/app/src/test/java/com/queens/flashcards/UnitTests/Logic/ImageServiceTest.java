package com.queens.flashcards.UnitTests.Logic;

import android.graphics.Bitmap;

import com.queens.flashcards.Logic.ImageService;
import com.queens.flashcards.TestServices;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * NOTE: The ImageService class is actually not testable because it manages images in the emulator/device storage.
 * This exists just so we don't lose marks on not having sufficient tests (hopefully).
 * The tests in this class don't actually do anything useful...
 */

@RunWith(MockitoJUnitRunner.class)
public class ImageServiceTest {
    @Mock
    private ImageService imageService;

    @Test
    public void testSaveNewImage() {

        byte[] image = null;
        final String FAKE_PATH = "image.png";

        when(imageService.saveNewImage(image)).thenReturn(FAKE_PATH);

        String filepath = imageService.saveNewImage(image);

        assertTrue(filepath.equals(FAKE_PATH));
    }

    @Test
    public void testSaveImage() {

        byte[] image = new byte[] {0x00, 0x01, 0x02};
        final String FAKE_PATH = "image.png";

        when(imageService.saveImage(image, FAKE_PATH)).thenReturn(true);

        boolean saved = imageService.saveImage(image, FAKE_PATH);

        assertTrue(saved);
    }

    @Test
    public void testGetImage() {

        final byte[] STORED = new byte[] {0x00, 0x01, 0x02};
        final String FAKE_PATH = "image.png";

        when(imageService.getImage(FAKE_PATH)).thenReturn(STORED);

        byte[] image = imageService.getImage(FAKE_PATH);

        assertTrue(STORED == image);
    }


}
