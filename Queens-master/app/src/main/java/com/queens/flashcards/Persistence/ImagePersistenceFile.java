package com.queens.flashcards.Persistence;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.queens.flashcards.FlashcardApplication;
import com.queens.flashcards.Persistence.Interfaces.ImagePersistence;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class ImagePersistenceFile implements ImagePersistence{

    /** Saves the byte array as a new Image file
     *
     * @param image - Image to save
     * @return File name of the Image that was saved
     */
    @Override
    public String saveNewImage(byte[] image) {
        String uniqueID = "";
        String uuid = UUID.randomUUID().toString() + ".png";

        ContextWrapper cw = new ContextWrapper(FlashcardApplication.getContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        boolean success = false;
        FileOutputStream ostream;
        File file = new File(directory, uuid);

        if (!file.exists() ) {
            try {
                success = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(success) {
            try {
                ostream = new FileOutputStream(file);
                ostream.write(image);
                ostream.flush();
                ostream.close();

                uniqueID = uuid;
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        return uniqueID;
    }

    /** Saves the byte array to the previous Image file
     *
     * @param image - Image to save
     * @param fileName - Name of the Image file to overwrite
     * @return True/False if it succeeded in saving the image
     */
    @Override
    public boolean saveImage(byte[] image, String fileName) {
        boolean saved = true;
        ContextWrapper cw = new ContextWrapper(FlashcardApplication.getContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        FileOutputStream ostream;
        File file = new File(directory, fileName);

        if(file.exists()) {
            try {
                ostream = new FileOutputStream(file, false);
                ostream.write(image);
                ostream.flush();
                ostream.close();

                saved = true;
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        return saved;
    }

    /** Attempts to get the Image with the specified fileName
     *
     * @param fileName - Name of the file to retrieve
     * @return byte array, which is the image found, null if not found
     */
    @Override
    public byte[] getImage(String fileName) {
        byte[] image = null;
        ContextWrapper cw = new ContextWrapper(FlashcardApplication.getContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

        try {
            File file = new File(directory, fileName);
            Bitmap bmImage = BitmapFactory.decodeStream(new FileInputStream(file));

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmImage.compress(Bitmap.CompressFormat.PNG, 70, stream);

            image = stream.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return image;
    }
}
