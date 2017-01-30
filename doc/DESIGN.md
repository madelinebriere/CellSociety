
#Cell Society Design Document
##Team 04
##Team members: Talha Koç, Maddie Briere, Stone Mathers
###Completed: January 29th, 2017
___


###Introduction: Maddie
This project is aimed towards generalizing the Cellular Automata (CA) Simulation. This type of simulation is composed of several cells, with specific “rules” on how to change, move, create new cells, etc. These cells interact with their neighbors in a simultaneous “step,” displaying a type of evolution over several steps.

This design is meant to allow easy switches from alternate simulations, and the possible addition of new types of cells and “cell societies.” The goal is to take advantage of Java inheritance as frequently as possible, in order to meet the goal of “open-closed” programming. That is, certain parts of our project will be closed to change (or at least we will endeavor to make alterations to such classes unnecessary). Meanwhile, other elements will be open to extension, meaning that, were a programmer to decide to add an element, they could simply extend one component of the current design and build off of that foundation.

The elements we mean to leave closed are those that form the foundation of the simulation. For instance, the basic characteristics of a cell, such as its color, position, and neighbors, are necessary “knowledge” for any cell. Furthermore, cells will perform certain common actions, albeit in a slightly different way. They will update themselves with each generation, according to their own rules. Hence, all of these elements should be closed to revision. 

This does not mean that the program cannot be expanded. For instance, if one were to try to describe a living cell in the Game of Life Cellular Automata Simulation, this cell would certainly inherit the characteristics and requirements of the general cell. However, this cell would have specific rules to dictate how it changes. It will have a threshold for under-population and overpopulation, and will use these thresholds in deciding how to change as it moves into the next generation. These characteristics would hence be an extension of the initial definition of a cell.

The same type of branching will occur with cell societies. Cell societies have certain characteristics in common. They have to have knowledge of the cells they are defined by, in a current state. They must know how many rows and columns form their cell society. Likewise, they must be able to make a generational “step” -- to update all of their cells and check for any conflicting updates. These pieces are general “knowledge” and “ability” for a cell society. These elements are closed to change, as any alterations would alter the foundation of the program and the simulation.

However, as with the cell, we can create extensions of the general cell society. A fire-simulation society, for instance, would not have to do much more than correctly initialize the grid with fire-simulation specific cells and continue to call update on its cells. Meanwhile, the population-simulation society may have to do a bit more work. If, for instance, two cells update to the same position, this society must be able to resolve the conflict by relocating one of the cells (likely at random). These details, however, come down to implementation and do not need to be defined right now -- that is the beauty of creating a foundation common to each simulation. We can add as many details as we desire later, and they will all be local to that specific society.

The user interface will likely be a point of little variation over the course of the project. The intention is to have a popup at the beginning of the game to request the file to read in the initial setup. A separate component of the project will take in this file name and read the text file, interpreting the settings and desired simulation.

After determining the settings, initial simulation state, and simulation type, the popup will disappear, leading the user to the GUI main. The two basic components of the GUI are the grid where the simulation is displayed and the controls that the user can use to manipulate the simulation in various ways (start, stop, reset, step -- the actions most commonly desired in CA Simulation). 

The GUI is, for the most part, closed to extension. However, some of the open components are the controls and the appearance of the grid. These should be changeable -- we do not want to leave the appearance and functionality static. It should be possible for new users to extend these elements.

###Overview: Maddie
The first general division comes in terms of the Cells. We plan to have an abstract Cell class that defines the basic, unchanging characteristics of a Cell, and requires that each Cell have an update method that returns an ArrayList of Cells (could be just the original cell, or could include a spawned cell, dead cell, etc.). From this Cell we will extend as many subclasses as are necessary to act in a single simulation. For instance, the population simulation only requires a single Cell type, which we have named HouseCell, that knows its color and is able to decide if it is satisfied or not. Meanwhile, the WaterWorld simulation requires a SharkCell and FishCell for the two types of creatures in the simulation. Each has a different type of update for each generation -- while the sharks can die of starvation, the fish cannot. Likewise, while the sharks can eat the fish, the fish cannot harm the sharks. 

Everything encompassed in each Cell subclass is specifically related to LOCAL operations. This is an important element of our segregation of tasks. Each Cell will make an update based on its neighbors only, it will not be able to influence any other cells. That is, if, for instance, a HouseCell decides that it is not happy, it will remove itself from the board, but it will not relocate. This task is left to the CellSociety objects (which will be discussed in a second). The purpose and functionality of each subclass of Cell is laid out in the image below.

![Image 1](CellDesign.jpg “Cell Design”)

The intended methods and variables for each type of cell are included in the image below.

![Image #2](CellFunction.jpg “Cell Function”)

Note that, although we made our best efforts to predict necessary variables and methods, there will likely be more in these subclasses. This is okay, because these classes are much more flexible than the superclass Cell. Changes to one subclass can be made without major influence on other types of Cells. This is the case so that new types of Cells can be created without much extra work, but also without altering the foundation of the program.

The class responsible for non-local changes will be CellSociety, which conceptually represents a grouping of Cells with a set of rules for interacting. CellSociety itself will be an abstract class that holds all of the information and functionality common to any society of cells. It will have knowledge of all cells that compose it (an ArrayList of Cells). Likewise, it will be able to update them by calling their respective update methods and deal with any conflicts. These conflicts can arise because each Cell will be making changes with complete ignorance of the changes its neighboring Cell is making. This is the case so that changes will seem to happen instantaneously (both visually and in the logic). This means that some Cells may carry out conflicting updates. 

Consider, for instance, the population simulation. In this simulation, if a cell "relocates," it is only capable of removing itself from the board, not adding itself back. Hence, PopSociety, the extension of CellSociety specific to the population simulation, will have to go through the requested updates and see if the new CellSociety is valid. For PopSociety, this means adding back the Cells that were removed to new empty spots to ensure that the population numbers do not change. The exact implementation of this action has not yet been decided. However, for a lot of the specific types of cells, that means that the Cell must carry with it a certain type of "memory" of past events. A HouseCell must know where it is being moved from, a FishCell must know if it has been eaten, and so on. 

As of the current design, there are only four extensions of CellSociety: FireSociety, PopSociety, WaterSociety and LifeSociety -- one for each simulation and set of rules. These can be seen in their relation to CellSociety in the image below:

![Image #3](CellSocietyDesign.jpg “Cell Society Design”). 

These are included to avoid giving each Cell power over an entire CellSociety. The CellSociety step function (which updates one generation) allows for everything to happen simultaneously and without conflict. Despite all of this complexity, in the end, the active CellSociety will only interact with the rest of the program when an updated version of the current CellSociety is requested. The true implementation of this process is not known to the rest of the program, nor does it need to be. In addition, the way in which this step is taken will be defined by the type of CellSociety being used. This means that a new simulation can easily be made by creating an extension of CellSociety, with the required Cells. Still, despite extensions in these subclasses, controlling classes will be able to call the step method on ANY CellSociety without having to know its type. This commonality allows for backend implementation to stay private.

This is all the work for the backend, but we are still missing a huge chunk of the program -- the user interface. The backend keeps the logic churning, but we also need something to display these changes. 

This is performed by the front-end of the program. At the forefront, we have the UIMain. This is the class that updates the graphics and records user input. It will make calls to GridView, which transforms the information from the updated CellSociety into Nodes (specifically, Rectangles) in the right location, of the right size and of the right color. These Nodes are defined by the CellNode class, which is used in GridView for simplicity and conciseness of code. The UIMain class also makes a call to the ButtonsView/ControlView class (name/ structure still in the works) in order to generate the buttons displayed in the simulation. 

Hence, from this end, we get a set of Nodes to display. What happens next? How is everything tied together?

The front-end and back-end come together in the Simulation class, which acts as a bridge between the two types of input. The Simulation class is responsible for holding the Stage used in the GUI. It constantly receives input from UIMain in the form of updated Nodes and adds these to the screen at the set number of frames per second. Many of the controls happen here (e.g., response to a button being pressed, pausing the program, resetting the information in the grid). Because the Simulation class holds much of the information about the animation itself, most of the input will be funneled back into this class for processing. This control flow is pictured in the image below.

![Image #4](OverallDesign.jpg “Overall Design”) 


###User Interface

The GUI will first create a popup asking the user to input an XML file to determine the simulation type, starting state, and parameters. After successfully parsing the file, the popup will disappear and lead the user to the main page of the GUI. This GUI and its current CellSociety will be initialized according to the user input.

Once the main page is opened, the user can start the simulation by pressing the start button. Some of the other controls the user has over the simulation are pause, reset, step, and speed control. Furthermore, the user has the ability to switch between different simulations using a drop down menu located on top of the grid.

In the case that the XML file input is formatted incorrectly, the user will be notified of the problem and told to re-enter a file. Until the file is formatted correctly, the user will not be able to start the simulation.

Here are two sketches of the basic GUI design:

![UI Main Sketch](UI_Sketch.jpg “UI Main Sketch”)
![File Input Sketch](File_Input_Sketch.jpg “File Input Sketch”)

###Design Details
**Main**
This class will simply create a new Simulation and call its run() method. The reason for such a basic Main is to minimize the steps needed to run the simulation.

**Simulation**
This class will first set up the background of the animation, including a Stage, Timeline, Group, and EventHandler. It will then instantiate a new PopUp, which it will use to obtain a file from the user. This file will then be passed to a FileReader to be interpreted. This information will then be used to instantiate a new CellSociety (backend) and UIMain (frontend). At this point, it will proceed to continuously run its *step* method. This method will first check for any user input and handle it accordingly. It will then call CellSociety's *update* method, which will return the new 2D array. It will then give this array to UIMain's *update* method, which will return an ArrayList of Nodes. It will then add all of these Nodes to the Group to be displayed. Simulation acts as a facilitator and bridge between frontend and backend calculations. Thus, it handles all big-picture tasks while delegating more focused tasks to other classes.

**PopUp**
PopUp will display a screen prompting the user to input a data file. It will then wait until the file is entered. Once the file is received, a method will be called to check the file's validity, returning true if it is, false if not. If it is not valid, then an error message will be displayed. The user will then be prompted for another file.

**FileReader**
FileReader will take in a file and, according to formatting rules determined by us, will extract and return all of the data needed to begin the simulation (name of the simulation, dimensions of the grid, initial configuration, etc.). In the future, this class could be extended to read in different pieces of information, giving the user further customization. It could also be extended to read in different data for additional simulations. This will also be the only class that needs to know the formatting rules for files, thus a format change will only impact operations in this class.

**CellSociety**
This abstract class will define the properties of any grid of cells running a Cellular Automaton. The instance variables will include a Color to represent the color of empty cells, two ints holding the number of rows and columns, and two ArrayLists of Cells, one holding the current Cell layout, one to hold the next Cell layout. It will have a method that takes in a Cell and returns an ArrayList of its neighbors in this CellSociety, one that returns all positions in the grid without reference to Cells, and one that returns all occupied cells. Finally, it will have a method (or constructor) to interpret file information and create a corresponding CellSociety.

It will also have a void method *step* that goes through every Cell in the current layout ArrayList, calls its *update* method, and places the returned Cell in the new layout ArrayList. It will then set the current layout ArrayList equal to the new one. This way, all Cells are updated simultaneously, and Cells don't update their state based on data still developing between steps. The goal of CellSociety is to handle decisions on a non-local level, more specifically resolving conflicts between cells and packaging Cells in a format that Simulation can then pass to UIMain. The abstraction of CellSociety keeps the program open to the extension of new simulations, but closed to modification.

*FireSociety*
This CellSociety is specific to the forest fire simulation. It dictates the actions of a cell society acting under these specific rules. This particular extension of CellSociety doesn’t have to do much because most of the updates made in this particular simulation are local updates (and are managed in the BurnCells and TreeCells). However, this class implements the step function and updates all of the cells it holds in an ArrayList.

*PopulationSociety*
This CellSociety is specific to the segregation simulation. It dictates the actions of a cell society acting under these specific rules. This extension must monitor relocations of individuals. When reading in updates in the step function, if any Cells return a NULL object, it must be relocated in an empty space. As a catch, it will likely be prudent to check that the set population levels (the percentage of each race) are still valid. 

*WaterSociety*
This CellSociety is specific to the Water World simulation. This extension has the largest role out of all of the current simulations. It must regulate breeding, starvation and movement. This means that, for every fish that is updated to eaten, there must be a matching shark in proximity to that fish. If two sharks try to eat the same fish, then one must be denied. Other interactions must also be monitored to make sure that no cells erroneously appear, disappear or move. In this class’ implementation of the step function, the previous checks must be accomplished following the update of each shark and fish cell.

*LifeSociety*
This extension of Cell addresses the Game of Life simulation. This subclass of CellSociety will, like PopSociety, have little to do other than implement the step method and update each Cell, and create a corresponding LifeSociety given file information. This is because the updates made in the Game of Life simulation are all local.

**Cell**
This abstract class will define the properties of any cell being represented in a Cellular Automaton. The instance variables will include two ints to represent the Cell's location in the 2D grid and a Color to represent the Cell's current state. It will have a method *update*, which takes in an ArrayList of neighboring Cells, uses the states of those Cells and itself to determine its new state, and create and return a new Cell that has this state. There will also be a method that takes in an ArrayList of neighboring Cells and returns an int representing the number of Cells that share the same state. The purpose of Cell is to make decisions about state on a local level. The abstraction of Cell keeps the program open to the extension of new simulations.

*BurnCell*
This cell represents a tree that is in the process of burning down, in the Flame Simulation. This subclass of the Cell class will have a static constant *stepsToBurn* that defines how many steps (how many generational updates) it takes for a tree to switch from burning to burned. It also has a local variable *mySteps* to keep track of its “lifetime” and compare to *stepsToBurn*. It will also implement the update class, which decides whether or not the cell should keep burning (return itself) or return an empty Cell (dead).

*TreeCell*
This cell represents a healthy tree in the Flame Simulation. This subclass of the Cell class will have a static constant *probCatch*, a double from 0 to 1 that describes how a likely a tree is to catch on fire. It implements the update method by either returning the same Cell or returning a BurnCell in the same location (meaning the tree has caught fire).

*HouseCell*
This is currently the sole cell type in the Segregation Simulation. It is a subclass of Cell representing an individual/household of a certain ethnicity, race, etc. These Cells are defined by their colors. This class will have a static constant *satisfiedThresh* that dictates the percentage (0->1) of same-color neighbors required for a HouseCell to be satisfied. This class also has the method, *isSatisfied()* which returns a boolean based on whether or not the Cell is content in its current location. If the Cell is not content, it will return NULL in its update method and allow PopSociety to relocate it elsewhere. If it is content, then it will return the same Cell. 

*SharkCell*
This class describes the sharks in the Water World simulation. A shark cell both knows its *stepsToStarve* and *stepsToBreed* (the number of steps between each occurrence of these actions). It also knows its own individual *stepsSinceBreed* and *stepsSinceEat* (its steps from the last occurrence of these actions). Finally, each shark holds a boolean *hasEaten* which indicates if the shark has eaten in this step. This is used in CellSociety for population checks. These variables are used to decide whether the Cell will return in its update method a NULL Cell (a starved shark), a moved Cell (the same shark, but in a new neighboring location), the same Cell and a new Cell (the original shark and its child), or just the same Cell (the original shark with no changes made).

*FishCell*
This class describes the sharks in the Water World simulation. A fish cell knows its *stepsToBreed* (the number of steps between each breeding event). It also knows its own individual *stepsSinceBreed* (its steps from the last breeding event). Finally, it knows if it has been eaten in this step -- it holds a boolean *isEaten*. This boolean is again used in CellSociety. These variables are used to decide whether the Cell will return in its update method a NULL Cell (a starved shark), a moved Cell (the same shark, but in a new neighboring location), the same Cell and a new Cell (the original shark and its child), or just the same Cell (the original shark with no changes made).

*LiveCell*
 This type of Cell represents a living cell in the Game of Life simulation. It stores a static constant underPop and overPop to keep track of the limits for under and over-population. In its *update* method, it either returns the same cell (alive) or a dead cell (if the cell has died this round). 

*DeadCell*
This type of Cell represents a dead cell in the Game of Life simulation. It stores a static constant *lifeThresh* that defines the number of neighboring living cells required for life. In its *update* method, it either returns the same cell (dead) or an alive cell (if this cell has regenerated).

**UIMain**
This class is instantiated from Simulation with handlers for controls. It will contain all of the UI objects. It will also have a GridView object representing the running simulation, which will be obtained by Simulation to be displayed. It also allows the user to interact with the simulation through various controls. This class serves to coordinate all frontend functionality and provide Simulation with everything it needs to display. Thus, Simulation does not need to know how each cell in the grid is determined and created.

**GridView**
The grid is a subclass of GridPane that displays cells and updates their values. The constructor takes in a 2d-array of colors and makes a new 2d-array of CellNodes of the same dimensions. Each node is added to the grid in the same order as it is in the 2d-array. In order to update the states of the cells in the grid, the *updateGrid* method is called. Its parameter is a 2d-array of colors with the same dimensions that were used to initialize the GridView object. If dimensions are not the same, the method raises an invalid size error. 

**CellNode**
This will be a Node with variables color and size. It must be initialized with a specific type, such as a circle or rectangle. 

**ControlView**
A view at the bottom of the screen that contains buttons that the user can click to control some aspects of the simulation. The buttons that we are currently planning on using are as follows:
 - Start : Starts running the simulation
 - Reset: Stops and resets the simulation
 - Pause: Pauses the simulation without affecting the data
 - Step: Moves simulation one-step ahead
 - Speed Slider: used to change the speed of the simulation.
 - Simulation Picker: a drop down menu located on top of the grid that allows the user to change the simulation type

Note that the UI does not determine the function of the buttons. We will implement an interface so that the parent class, Simulation, can handle what happens when the user interacts with the controls.

**UIControls**
Interface that Simulation implements to specify the function of UI controls like start and pause.
___
####**Use Cases**
Scenario 1: Set a middle cell to dead using Game of Life rules

1. LifeSociety runs through its current Cell ArrayList and then reaches this particular LiveCell.
2. LifeSociety passes this LiveCell to its *getNeighbors* method, which returns an ArrayList of neighboring Cells.
3. LifeSociety passes this ArrayList into the LiveCell's *update* method.
4. The method determines how many LiveCells are in the given ArrayList.
5. **A)** The Cell is initially a LiveCell. This number is not equal to 2 or 3 (the thresholds for under and over-population), so a new DeadCell is created, with the same location parameters as the LiveCell passed in, and returned to LifeSociety. **B)** The Cell is initially a DeadCell. This number is not equal to 3, so the same DeadCell is returned.
6. LifeSociety stores this cell in the new layout ArrayList of Cells.

Scenario 2: Set an edge cell to live using Game of Life rules

1. LifeSociety runs through its current Cell ArrayList and then reaches this particular Cell.
2. LifeSociety passes this Cell to its *getNeighbors* method, which returns an ArrayList of neighboring Cells.
3. LifeSociety passes this ArrayList into the Cell's *update* method
4. The method determines how many LiveCells are in the given ArrayList.
5. **A)** The Cell is initially a LiveCell. This number is equal to 2 or 3, so the same LiveCell is returned. **B)** The Cell is initially a DeadCell. This number is equal to 3 (the number of living neighbors required for life), so a new LiveCell is created, with the same location parameters as the DeadCell, and returned to LifeSociety.
6. LifeSociety stores this cell in the new layout ArrayList of Cells.

Scenario 3: Move to the next generation

1. Simulation calls CellSociety's *step* method.
2. CellSociety runs through its current Cell ArrayList.
3. For each Cell, CellSociety calls its *getNeighbors* method to get the Cell's neighbors, which is passed to the Cell's *update* method.
4. Depending on the type of Cell, calculations are done with the Cell's state and that of its neighbors to determine the new Cell to be returned to CellSociety.
5. Once CellSociety goes through its entire current layout ArrayList, it checks for conflicts between Cells in the new layout ArrayList and resolves them. The current layout ArrayList is then set to equal the new layout ArrayList. The current layout ArrayList is then passed back to Simulation.
6. Simulation passes this ArrayList to UIMain.
7. UIMain passes this ArrayList to GridView.
8. GridView creates an empty 2d-array of CellNodes.
9. GridView runs through the ArrayList of Cells, creating a new CellNode with each Cell's X-position, Y-position, and Color.
10. The new CellNode is placed in the 2d-array at its X-position and Y-position.
11. UIMain retrieves this 2d-array from GridView, then passes it to Simulation to be added to the Group and displayed.

Scenario 4: Set a simulation parameter

1. FileReader reads through the file and obtains the value of *probCatch* as a String.
2. It then parses this value to an int and returns it to Simulation.
3. Simulation constructs a new FireSociety object with, amongst other things, the value of *probCatch*.
4. FireSociety now hold the value of *probCatch*, passing it to the constructor of any new TreeCell it creates.

Scenario 5: Switch simulations

1. The user clicks on the "Simulations" drop-down menu, and clicks on the "Wator" option.
2. Menu object will determine that Wator option was pressed and call the handler. Note that the Simulation class instantiated the interface and passed it down to the Menu object.
3. Simulation instantiates a new WaterSociety using the user-given grid size.
4. WaterSociety's constructor assigns default values to all other parameters.
5. WaterSociety uses these default values to fill the current layout ArrayList with SharkCells and FishCells.
6. Simulation passes this ArrayList of Cells to UIMain.
7. UIMain passes this ArrayList to GridView, which creates a 2d-array of CellNodes.
8. Simulation retrieves this 2d-array from UIMain and displays it.

___

###Design Considerations


The biggest open end in our design is the approach to the user’s input file. Currently, we have one class, FileReader, that takes in a String representing the entire file. From here, it will obtain all necessary data and return it to Simulation. However, the number of methods used to do so, the rules dictating the file’s format, and the return types are still undecided. Furthermore, we are considering abstracting FileReader, as different data will be read in for different simulations.

We are also still deciding on the command flow of user input, more specifically, how button commands will be carried out and which object will do it. We have two different designs that we are considering. The first is to have Simulation control the user input by checking the state of the controls. For example, if the boolean isPaused is true, the simulation will not step forward with the simulation. Our second design is to implement an interface whereby the control objects let the parent class handle the functionality. For example, when a button is clicked, the button will simply call myHandler (initialized in the constructor) without actually knowing what the handler will do.

Lastly, our goal of separating local and non-local actions between Cell and CellSociety could be trick for some Cell subclasses. Due to the Cell’s limited view, conflicts between the desired locations could arise, such as where dissatisfied HouseCells move. Currently, CellSociety is in charge of detecting and handling any conflicts between cells. However, we anticipate complications arising when attempting to implement this design.


###Team Responsibilities
This project will largely be split up by frontend and backend designation.

**Talha** will be working on the frontend design. This includes UIMain and the classes it interacts with, such as CellNode, ButtonsView and GridView. His role will be related to user interaction -- his work on the project will make the actual GUI look presentable and clean. Likewise, it will leave room for extension/change to the overall appearance of the GUI. Talha will also be in charge of the file-reading operation. This includes getting file input from the user via a popup, processing this information and converting it to a usable format. 

**Stone** will be working on much of the “in-between” code. He will be writing the Main and Simulation, which will respectively launch and control the program. He will have to take information from the backend (the Cells in a generation) and get them to the front-end in a meaningful format. Stone will also be contributing to the CellSociety code (and the subclasses), coding the general/ non-local rules for each CellSociety. 

**Maddie** will be working on the foundation of the backend. She will be writing the Cell class and designing all of the aforementioned Cells and their local interactions. This includes coding flexibility of the superclass so that new Cells can be added for future simulations. She will also be contributing to the CellSociety code to make sure that all of the code existing in each CellSociety vs. each Cell is properly based depending on locality.
