package com.queens.flashcards.Presentation.Interface;

import com.queens.flashcards.Model.Flashcard.Flashcard;

public interface GuessingGameSetup {
    /** Starts the Guessing Game, with the specified options and starts the CountDownTimer if its time is > 0 */
    void startGame();

    /** Sets the time limit on the game
     *
     * @param timeLimit - Time limit in seconds
     */
    void setGameTimeLimit(long timeLimit);

    /** Adds the passed Flashcard to the Guessing Game
     *
     * @param flashcard - Flashcard to add to the game
     */
    void addCardToGame(Flashcard flashcard);

    /** Removes the passed Flashcard from the Guessing Game
     *
     * @param flashcard - Flashcard to remove from the game
     */
    void removeCardFromGame(Flashcard flashcard);

    /** Sets the time limit on the game
     *
     * @param repeatGame - indicates if the user wants to keep repeating the game until
     *                   all answers are correct
     */
    void setRepeatUntilAllCorrect(boolean repeatGame);

    /** Reverses question and answer for Text FlashCard
     *
     * @param reverse - boolean if to reverse the question and answer
     */
    void reverseQuestion(boolean reverse);

    /** Sets a limit on the number of cards used in the game
     *
     * @param maxCards - Max number of cards
     */
    void setCardLimit(int maxCards);
}
