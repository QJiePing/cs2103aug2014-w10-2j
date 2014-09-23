cs2103aug2014-w10-2j
====================

Symbols Explaination
====================
Please follow these symbols where possible to reflect whether your methods/variables are 
accessible by others

"-"	private variables or methods; Not accessible from other classes
"+"	public variables or methods; Accessible by anyone
" "	unknown accessiblity; Try not to access these methods/variables

API

========
Taskaler
========
Main Class object to execute and
store global variables

+Controller controller
+Logic logic
+OPLogic opLogic
+Storage storage
+UI ui
+ViewLogic viewLogic
+ArrayList<Task> taskList

+main(args[]): void		  // Main method to start process
+start(primaryStage): void	  // Method to launch application

Additional Notes:
- store a global integer variable for taskID
- controller calls storage functions as well
- add now has 2 params
- edit key switching will be changed to separate functions

====
 UI
====
Main Class to create the required UI
for the application This class also
serves as the Action Event Controller
for the UI. i.e. OnClick Events

+displayCalendar(): void          // Can be called to display the calendar
				  // TODO: Sync days with Tasks

+displayList(): void		  // Can be called to display the list
				  // TODO: NOT YET IMPLEMENTED!!!!

-start(Stage): void		  // Method to start rendering the UI elements of Taskaler

-txtCmdInputKeyPressed(KeyEvent e): void
				  // Method which is binded to the KeyPressed event of txtCmdInput
				  // element This method passes user input to the controller
				  // TODO: Not yet implemented error handling

Additional Notes:
- Many other classes reside in the same class file as UI.java.
  Please do not modify these.

===========
Controller
===========
executeCMD(String cmd) : void       //calls functions in the logic class with the correct params
- 	Atm, for commands add and edit, it can accept commands with less than max parameters,
	even zero params, like adding a floating task, but it cannot correct duplicate tags
	for example "add -d0222 -d0111 ..." would ignore the first instance of -d

=====
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

=======
Storage
=======
//using external jar: json-simple.jar to output format in json format
//using external jar: json-simple.jar to read in information in json format
writeToFile(): boolean               //output task information in json format in storage file
readFromFile(): String               //read storage file for task information and initialise all task object and put into the global task arrayList

====
Task
====

