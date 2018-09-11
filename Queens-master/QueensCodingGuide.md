# Queens Coding Guide 

### Overview
This file suggests some not-extremely-bad coding standards for our Flashcards project. Try to follow these 
as closely as possbile so that we all know how to navigate through our code.

### General Tips
- Try to use whitespace to separate code into logical sections within methods.
- If the purpose of a block of code isn't extremely obvious, add a *brief* comment 
at the beginning of the block explaining what it does.
- If possible, program to an interface so switching implementations later on is easier.
- If you ever find yourself copying + pasting code, try to put it in a single file and reference
it from there multiple times. (e.g. constants, utility methods, etc.)


### Brackets
- Open bracket is on the same line of code where it is needed.
- Close bracket gets its own line.  

```java
if (helloWorld) {
	//HelloWorld
}
```

### Naming
- Instance variables are named using regular camel-case.
- When referring to instance variables, try to use 'this.myInstanceVariable' just to be extra clear.  
```java
private int myInstanceVariable;
...
private void ten() {
	this.myInstanceVariable = 10;
}
```

- Constants are words in all caps separated by '_'.  

```java
private static final int MY_CONSTANT = 10;
```

### Comments
- Try to add comments to methods/functions and classes.
	- Android Studio can be configured to automatically display comments when hovering over a method/function or class name.
	- Type __/\*\*__ then press __Enter__ just above a method signature, then Android Studio will automatically generate the comment block.  

```java
/**
  * Calculates the sum of all integers in a list.
  * @param values The list of integers to add together.
  * @return The sum of the integers.
  */
public int sum(List<Integer> values) {
	int total = 0;
	
	for (Integer i : values)
		total += i;
		
	return total;
}
```

### Classes
- Try to follow the following format:
	- The //region stuff creates foldable sections of code in the IDE  
  
```java
public class MyClass {
	
	//region Constants
	- Class-level constants go here
	//endregion
	
	
	//region Members
	- Instance variables go here
	//endregion
	
	
	//region Constructors
	- Constructors go here
	//endregion
	
	
	//region Static Factory Methods
	- Static factory methods go here (e.g. newInstance)
	//endregion
	
	
	//region Event Handlers / Listener Methods
	- Event handlers or listener methods go here (e.g. overriding methods like onSomethingHappened())
	//endregion
	
	
	//region Getters & Setters
	- Getters (accessors) & setters (mutators) go here
	//endregion
	
	
	//region Public Methods
	- Public methods go here
	//endregion
	
	
	//region Helper Methods
	- Private methods go here
	//endregion
	
	
	//region InterfaceImplementation Methods
	- Interface implementation code goes here
	- Create a separate region for each interface implemented
	//endregion
	
	
}
```

_Feel free to add anything to this file if it hasn't already been mentioned._