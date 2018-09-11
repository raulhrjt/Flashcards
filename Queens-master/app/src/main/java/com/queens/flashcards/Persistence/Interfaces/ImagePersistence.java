package com.queens.flashcards.Persistence.Interfaces;

public interface ImagePersistence {

    /** Saves the byte array as a new Image file
     *
     * @param image - Image to save
     * @return File name of the Image that was saved
     */
    String saveNewImage(byte[] image);

    /** Saves the byte array to the previous Image file
     *
     * @param image - Image to save
     * @param fileName - Name of the Image file to overwrite
     * @return True/False if it succeeded in saving the image
     */
    boolean saveImage(byte[] image, String fileName);

    /** Attempts to get the Image with the specified fileName
     *
     * @param fileName - Name of the file to retrieve
     * @return byte array, which is the image found, null if not found
     */
    byte[] getImage(String fileName);

}
