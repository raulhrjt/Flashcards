package com.queens.flashcards.Persistence.stubs;

import android.content.Context;
import android.graphics.Bitmap;

import com.queens.flashcards.Persistence.Interfaces.ImagePersistence;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ImagePersistenceStub implements ImagePersistence {

    //region Members

    Map<String, byte[]> images = new HashMap<>();

    //endregion

    //region Constructor

    public ImagePersistenceStub() {
        images.put("not/real/path.png", null); // This has to use a mocking library to work
        images.put("another/fake/path.png", null); // This has to use a mocking library to work
        images.put("ok/thats/enough.png", null); // This has to use a mocking library to work
    }

    //endregion

    //region ImagePersistence Methods

    @Override
    public String saveNewImage(byte[] image) {
        String uuid = UUID.randomUUID().toString() + ".png";

        images.put(uuid, image);

        return uuid;
    }

    @Override
    public boolean saveImage(byte[] image, String fileName) {
        images.put(fileName, image);
        return true;
    }

    @Override
    public byte[] getImage(String fileName) {
        return images.get(fileName);
    }

    //endregion

}
