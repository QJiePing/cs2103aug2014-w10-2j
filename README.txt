cs2103aug2014-w10-2j
====================
API

Global
======
- Create a Taskaler class which calls the constructors of UI, Controller, Logic classes
- store a global integer variable for taskID
- store an arraylist of Task objects
- controller calls storage functions as well
- add now has 2 params
- edit key switching will be changed to separate functions

UI
====
display(Task toShow): void          //??? is this function supposed to be here ???

Controller
===========
executeCMD(String cmd) : void       //calls functions in the logic class with the correct params
handleError(String error) : void    //handles all errors of the taskaler


Logic
=====
addTask(String name, String description) : void     //any fields not entered by the user will be null
deleteTask(String taskID) : void
editTask(String taskID, String name, String description) : void //any fields not entered will be null
viewList() : void
viewCal() : void
undo() : void
simpleSearch(String key) : Task

Storage
=======
//using external jar: json-simple.jar to output format in json format
//using external jar: json-simple.jar to read in information in json format
writeToFile(): boolean               //output task information in json format in storage file
readFromFile(): String               //read storage file for task information and initialise all task object and put into the global task arrayList

Task
====

