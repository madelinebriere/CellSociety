#CellSociety
##Team 04
###Members: Stone Mathers, Maddie Briere and Talha Koç

###Start date: January 24, 2017
###Basic implementation completion date: February 5, 2017
###Estimation of number of hours: 60 (cumulative)

##Roles
The roles for this assignment are divided into front-end, back-end and front-end/back-end interaction.

**Talha** worked on the frontend design. This includes GUIMain and various components it interacts with, such as Grid. His role will be related to user interaction -- his work on the project makes the actual GUI look presentable and clean. Furthermore, his goal is to reduce the amount of control the frontend has over the backend and to make the interaction between the between them as simple as possible. Likewise, it leaves room for extension/change to the overall appearance of the GUI as well as easy implementation of newer backend functionality. His work is held in the GUI package.

**Stone** worked on the handling of files and facilitated the interactions between the three general components of the project: file handling, frontend, and backend. He wrote all classes in the file_handling package which included a class to obtain a file from the user (PopUp), a class to parse the file (XMLParser), and classes to store and interpret the file’s data (SimulationType, LifeSimulation, FireSimulation, PopSimulation, and WaterSimulation). SimulationType was made as an abstract class, with a subclass for each kind of a simulation. It was designed this way so that it can easily be extended for additional simulations. He also created the test classes Reflection, SimulationTypeTester, and XMLParserTester to ensure that all file reading functionality is correct. He also communicated with other members of the group to ensure that each facet of the project communicated appropriately and effectively.

**Maddie** wrote most of the backend code. She wrote the Cell class and designed all of the Cells and their local interactions. This included coding flexibility of the superclass so that new Cells can be added for future simulations. She also wrote the CellSociety code to make sure that all of the code existing in each CellSociety vs. each Cell is properly based depending on locality. She created all of the extensions of CellSociety for the current simulations (the forest fire simulation, the Game of Life simulation, the Wa-Tor World simulation and the segregation simulation). Finally, she created the SocietyTester and NeighborTester to test the functionality of the backend. 



##Resources:
Reflection: http://www.oracle.com/technetwork/articles/java/javareflection-1536171.html

FileChooser: http://docs.oracle.com/javafx/2/ui_controls/file-chooser.html

XML: https://www.w3.org/XML/RDB.html

XML Parsing: CompSci308_2017Spring/lab_browser GIT project

Java File API

Java Element API

Java Document API

Java Node API

Java NodeList API

Java Shape API

Java Rectangle API

Java Label API

Segregation model: http://nifty.stanford.edu/2014/mccown-schelling-model-segregation/

Wa-Tor World model: http://nifty.stanford.edu/2011/scott-wator-world/

Forest fire model: http://nifty.stanford.edu/2007/shiflet-fire/

Game of Life model: https://en.wikipedia.org/wiki/Conway's_Game_of_Life

##Start files
This program is launched through the Main class, in the package cellsociety_team04.

##Tester classes
The files used for testing can be found in the test package. One set of testers is used to test the Cell and CellSociety classes. 
**SocietyTester ** updates a CellSociety at a set pace, checking that the number of cells on the board at each point is consistent with what is expected. It also prints out the number of each type of cell in the society to show change from each generation. 
**NeighborTester** prints out the number of neighbors for each Cell in a CellSociety, and also reports if the Cell is a corner/ border Cell (which can, and should, impact the number of neighbors in certain simulations). This class can be used to test that the neighbor function implemented for the society is functioning as expected.
Another set of testers is used to test the file reading and data storage functionality of the project.
**Reflection** tests the implementation of Java’s Reflection package. This class was equally a practice tool to correctly implement Reflection in the XMLParser when identifying a SimulationType’s instantiated subclass and creating a new instance of it.
**SimulationTypeTester** tests the functionality of SimulationType and all of its subclasses. It instantiates each subclass and prints out the results of all method calls that retrieve each piece of data a SimulationType should hold.
**XMLParserTester** tests the functionality of XMLParser. The end result should be the same as SimulationTypeTester (in fact, it calls SimulationTypeTester’s print methods). The difference comes in the instantiation of each SimulationType. Instead of passing in the data manually, a file containing the data is given to XMLParser, which then returns the SimulationType.

##Required data and resource files
	The FireSimData.xml, LifeSimData.xml, PopSimData.xml, and WaterSimData.xml files, as well as extended copies (add a 2 after each name), all contained within the data folder, are used to test each available kind of simulation.

##Program usage
any information about using the program (i.e., command-line/applet arguments, key inputs, interesting example data files, or easter eggs)
Any time you want to change the file/simulation, you must navigate into the data folder to select the new XML file, via the new file button on the GUI.
	Another note: Certain actions will reset the board -- changing the grid size, choosing a different file, etc. 

##Known bugs/ problems
any known bugs, crashes, or problems with the project's functionality

	In XMLParser, we are yet to implement exception handling. Thus, it is necessary that the user selected a valid XML file that follows all formatting rules. Currently, an error occurs if the user either selects an invalid file, or clicks “Cancel” in the FileChooser, thus “choosing” a null file.
	Likewise, some parts of each Cell do not conform to the expected simultaneity of the CA simulation. For instance, to avoid unnecessary problems, we decided to update cells “in order,” having one choose its actions and giving the following cells knowledge of this action. These cells still act based on data from the old set-up, but if they try to do something that conflicts with the action of any previously updated cells, they are denied. For instance, if a shark cell moves to a certain cell, no future cell is even given the option of moving into this cell. This possibility is removed from the list of available spots. This change does not really affect the overall behavior of the simulation, especially since the cells are shuffled each step in order to avoid biased updates (consistently allowing certain cells to update before other cells).

##Extra features
We have extended user interface functionality which allows the user to choose animation speed, grid size and simulation (via the new file button). 

##Impressions
This project has been a great learning experience. Most of us have never worked in teams to code before, nor have we been forced to be cognizant of the fact that other programmers may need to work with our code. This project has taught us all a lot about communicating, delegating tasks, actively coding for readers (adding comments, making code readable, hiding implementation), etc. The randomization of teams was also a great choice -- this forced us to work with new people and adapt to a new work environment.

