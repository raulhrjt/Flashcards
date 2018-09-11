-- Create main Flashcard table
CREATE TABLE IF NOT EXISTS
    Flashcard (
        ID BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        Name VARCHAR(1000000) NOT NULL UNIQUE,
        Question VARCHAR(1000000) NOT NULL,
        AnswerType INTEGER NOT NULL,
        ImageFile VARCHAR(1000000)
    );

-- Create FlashcardTextAnswer table
CREATE TABLE IF NOT EXISTS
    FlashcardTextAnswer (
        ID BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        FlashcardID BIGINT NOT NULL UNIQUE,
        Answer VARCHAR(1000000) NOT NULL
    );

-- Create FlashcardTFAnswer table
CREATE TABLE IF NOT EXISTS
    FlashcardTFAnswer (
        ID BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        FlashcardID BIGINT NOT NULL UNIQUE,
        Answer BOOLEAN DEFAULT FALSE NOT NULL
    );

-- Create FlashcardMCAnswer table (correct answer)
CREATE TABLE IF NOT EXISTS
    FlashcardMCAnswer (
        ID BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        FlashcardID BIGINT NOT NULL UNIQUE,
        Answer VARCHAR(1000000) NOT NULL
    );

-- Create FlashcardMCWrongAnswers table (all wrong answers in MC)
CREATE TABLE IF NOT EXISTS
    FlashcardMCWrongAnswers (
        ID BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        FlashcardMCAnswerID BIGINT NOT NULL,
        Answer VARCHAR(1000000) NOT NULL
    );

-- Create Flashcard - Category bridge table
CREATE TABLE IF NOT EXISTS
    Flashcard_Category (
        ID BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        FlashcardID BIGINT NOT NULL,
        CategoryID INTEGER NOT NULL,
    )

-- Create Category table
CREATE TABLE IF NOT EXISTS
    Category (
        ID BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        Name VARCHAR(1000000) NOT NULL UNIQUE
    );