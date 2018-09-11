_Group: Queens  
COMP 3350 A01  
Iteration 1_

# Flashcards Architecture

## Overview
The Flashcards application follows the 3-tier architecture, utilizing 3 layers: Presentation, Logic, and Persistence. Each layer is entirely responsible for achieving a particular task, such as the Presentation layer displaying data on a screen, the Logic layer applying business logic to data, and the Persistence layer storing data in memory to some form of external storage. The general flow of communication between the layers is as follows: Presentation <-> Logic <-> Persistence. In addition to the 3 layers, a collection of Domain Models exists which represent the application data as classes. The Domain Models are the medium through which the layers communicate data with one another.

## Packages
    - Logic
    	- Exception
    	- Validation
    - Model
    	- Flashcard
    	- Category
    - Persistence
        - Exception
        - hsqldb
		- Interfaces
    	- stubs
    	- Wrappers
    	- Factory
    - Presentation
		- Category
    	- Flashcard
    	    - Factory 
    	- GuessingGame
    	    - Factory 
    	- Interface
    	- Parcelable
    	    - Factory 
	
### Logic
The _Logic_ package contains all code that handles any application logic required in the form of Service classes.

**Exception**  
The _Exception_ package that contains all our custom Exceptions, which can be thrown from any location in our logic code.

**Validation**  
The _Validation_ package that contains all rules (in the form of methods) for validating the state of our domain model objects.

### Model
The _Model_ package contains our domain models, which are classes that represent our data as objects in memory. 

**Flashcard**  
The _Flashcard_ package contains our domain models needed to represent and work with a Flashcard. The classes in this package are the core focus of the application.

**Category**  
The _Category_ package contains our domain models needed to represent and work with a Category.

### Persistence
The _Persistence_ package contains all code that handles the storage and retrieval of the application data. The classes in this package act as an interface to the storage mechanism being used, which is a local database.

**Exception**  
The _Exception_ package contains all exception classes that are used in the persistence layer. Most importantly, it has the PersistenceException, which is an unchecked wrapper for SQLExceptions.

**hsqldb**  
The _hsqldb_ package contains the HSQLDB implementations of the persistence interfaces. The code in these classes interact directly with the database via JDBC.

**Interfaces**  
The _Interfaces_ package contains interfaces for our persistence class implementations. These classes provide a contract to outside code, stating what can be done with the storage mechanism.

**stubs**  
The _stubs_ package contains a fake implementation that mimics the storage and retrieval of application data. It simply stores the objects in memory as a list. These classes are only used in the unit tests.

**Wrappers**      
The _Wrappers_ package contains wrappers for Persistence objects of various FlashcardAnswer types. They use interfaces for deletion, updating, creating, and retrieval of Answers.

**Factory**        
The _Factory_ package contains Factory's that create specific Answer Persistence objects based on a FlashcardAnswer.

### Presentation
The _Presentation_ package contains all Android-specific code that displays our application data and receives user input.

**Category**  
The _Category_ package contains all Android code for GUI elements that deal with Categories.

**Flashcard**  
The _Flashcard_ package contains all Android code for GUI elements that deal with Flashcards.

**Factory**         
The _Factory_ package within the Flashcard package contains Factory's for creating instances of EditCardAnswerFragment and EditCardOptionsFragment.

**GuessingGame**  
The _GuessingGame_ package contains all Android code for GUI elements that deal with the guessing game.

**Factory**        
The _Factory_ package within the GuessingGame package contains Factory's for creating instances of GuessingGameAnswerFragment and GuessingGameQuestionFragment.

**Interface**  
The _Interface_ package contains interfaces for GUI-related needs.

**Parcelable**          
The __Parcelable_ package contains Android specific code for wrapping Models to pass between Fragments.

**Factory**       
The _Factory_ package within the Parcelable package contains Factory's for creating instances a Parcelable version of a subclass of FlashcardAnswer.

## Diagram
The diagram in architecture\_diagram.png is a visual representation of the structure and flow of our application. (Please refer to the architecture_diagram.png file)