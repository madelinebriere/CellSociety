#Peer Code Review
-------
#Code Smell Refactoring
Maddie Briere and Kris Elbert

**Addition of the LifeSimulation Class**
The feedback given by the Similar Code Analysis Report indicated several areas of repetition, but I honed in on the LiveCell and DeadCell duplications because these were the  classes for which I was specifically responsible. I had been thinking in the back of my head that I would have to at some point consolidate the code from LiveCell and DeadCell in a superclass, because a lot of the actions for a LiveCell are the same as for a DeadCell. This report reminded me to make this important change. I created the LifeSimulation class in order to consolidate these similarities -- both Cells have the same skeleton for the update method, with the unique parts left to the abstract changeState method. If I choose to change this Cell type functionality, I can now avoid potential divergence of these two classes.

**Duplication in Simulation classes**
There was also quite a bit of duplication between the simulation classes. Some of it I had added for my own convenience, but I hadn't noted the duplication until I saw the classes side by side. To fix this, I utilized the CellGenerator class, which goes from enums of CellNames to instances of Cells and back. This allowed me to reduce the duplication between the getCells() methods -- no specific instances had to be checked, as these relationships were exported to the CellGenerator class. 

-------
#Checklist Refactoring
**Use of static variables**
In order to remove the MASSIVE number of static variables in all of the Cell structures, I separated the fields into static final constants for the desired default values, and variables for the the related values. For instance, instead of having "static double satisfiedThreshold = .3," we now have "static final double SATISFIED_THRESH=.3" and "private double satisfiedThresh." This removed the static variable problems almost completely. 

**Use of static methods**
While going through all of the Cell classes (there are about 12) and shifting to constants and variables, we also had to change the getters and setters from static to non-static. This is preferable, as it keeps the information of each Cell relatively "hidden" from other Cells.