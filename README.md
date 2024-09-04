# OOP-Resit

This is my github repository for the CPS2004- Object Oriented Programming Assignment - September Session

All Java related tasks are created as modules to a parent project called oopresit. Each sequential task is dependent on the previous module's pom.xml file allowing for better file sharing. The multi-module Maven project is found in the Java/oopresit directory, within this directory you can find the parent pom.xml file and all module folders: task1, task2, task3 and task4.

To learn how to run the projects correctly [check this out](#how-to-run-tasks).

## Task 1

Task 1 is split into 3 parts:
1. UML Diagram which is found in the report
2. [Java Implementation](#java-implementation) 
3. [C++ Implementation](#c-implementation)

### Java Implementation

The Java implementation of task 1 is found in Java/oopresit/task1/  
This directory of the task has a folder called Saves which stored all preivously saved applications state to be potentionally loaded by the system.  
The task 1 implemtation consists of 5 java files:  
1. Assets.java: Assets class hierarchy
2. Intermediaries.java: Intermediaries class hierarchy
3. Portfolio.java: Snapshot recording and listing
4. CLI.java: User Interface + Program Helper Functions
5. Main.java: Main method

### C++ Implementation

## Task 2

The Java implementation of task 2 is found in Java/oopresit/task2/

## Task 3

The Java implementation of task 3 is found in Java/oopresit/task3/

## Task 4

The Java implementation of task 4 is found in Java/oopresit/task4/, this implemtation consists of Task 4-A and Task 4-B


## How To Run Tasks

Start by navigating to the desired project's path as shown [here](#navigate-paths-accordingly).

Once you are in the correct directory, learn how to compile and run the [Java Projects using Maven](#maven) or the [C++ Projects using Cmake](#cmake).

### Navigate paths accordingly
1. Task 1 (Java): Java/oopresit/task1
2. Task 1 (C++): Task-1/C++ Version
3. Task 2 (Java): Java/oopresit/task2
4. Task 3 (Java): Java/oopresit/task3
5. Task 4 (Java): Java/oopresit/task4


### Maven

To run any of the desired Java projects you can run the following command in the correct path of the project: <br>**mvn exec:java**

To clean and compile a Java project, once in the correct path you can run the following command: <br>**mvn clean compile** 

### Cmake
