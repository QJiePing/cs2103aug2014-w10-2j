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
- 	Atm, for commands add and edit, it can accept commands with less than max parameters,
	even zero params, like adding a floating task, but it cannot correct duplicate tags
	for example "add -d0222 -d0111 ..." would ignore the first instance of -d


Logic
=====
//for the params, the tags have been taken away in executeCMD already, for example, 
//"edit -t0021 -nhmwk -xivle" --> editTask("0021", {"hmwk", null, null, null, ivle})
addTask(String[] parameters) : void //the parameters are in the order (date, workload, name, description)
									//any fields not entered by the user will be null
deleteTask(String taskID) : void
editTask(String taskID, String[] parameters) : void //the parameters are in the order 
													//(name, doneTag, date, workload, description)
													//any fields not entered by the user will be null
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

