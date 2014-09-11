cs2103aug2014-w10-2j
====================
API

UI
====
display(Task toShow): void          //??? is this function supposed to be here ???

Controller
===========
executeCMD(String cmd) : void       //calls functions in the logic class with the correct params

Logic
=====
addTask(Task toDo) : void
deleteTask(Task toDo) : void
viewList() : void
viewCal() : void
undo(int numOfTimes) : void         //numOfTimes is the number of commands in the stack to undo
simpleSearch(String key) : Task

Storage
=======
saveToFile(): boolean               //rewrites the storage file and saves the global task list into it
readFile(): String                  //reads the storage file and returns the task list(not sure what format to return as)

Task
====

