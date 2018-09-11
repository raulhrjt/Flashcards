package com.queens.flashcards.Logic;

import com.queens.flashcards.Persistence.Interfaces.ImagePersistence;

public class ImageService {
    //region Members

    private ImagePersistence imagePersistence;

    //endregion

    //region Constructor

    public ImageService(ImagePersistence imagePersistence) {
        this.imagePersistence = imagePersistence;
    }

    //endregion

    //region Public Methods

    /** Saves the byte array as a new Image file
     *
     * @param image - Image to save
     * @return File name of the Image that was saved
     */
    public String saveNewImage(byte[] image) {
        String imageID = "";

        if(image != null) {
            imageID = imagePersistence.saveNewImage(image);
        }

        return imageID;
    }

    /** Saves the byte array to the previous Image file
     *
     * @param image - Image to save
     * @param fileName - Name of the Image file to overwrite
     * @return True/False if it succeeded in saving the image
     */
    public boolean saveImage(byte[] image, String fileName) {
        boolean saved = false;

        if(image != null && !fileName.equals("")) {
            saved = imagePersistence.saveImage(image, fileName);
        }

        return saved;
    }

    /** Attempts to get the Image with the specified fileName
     *
     * @param fileName - Name of the file to retrieve
     * @return byte array, which is the image found, null if not found
     */
    public byte[] getImage(String fileName) {
        byte[] image = null;

        if(!fileName.equals("")) {
            image = imagePersistence.getImage(fileName);
        }

        return image;
    }

    //endregion
}
