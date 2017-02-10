#Peer Code Review
-------
##Code Smell Refactoring
**Decision 1:** *Removed duplicated code in combineDataTypes method in WaterSimulation, PopSimulation, and FireSimulation.*
The *combineDataTypes* method was implemented in exactly the same way in all three of these classes. The only reason it had not been implemented in the superclass SimulationType was that one subclass, LifeSimulation, implemented it differently. It also used a final List of Strings that  was different for each subclass. To refactor, the method was first removed from all three subclasses and placed in SimulationType. The method was then overridden in LifeSimulation. Now that *combineDataTypes* was being called in the superclass, nullPointerException arose, because it was trying to access a List that had not yet been instantiated. To fix this, a protected List was added to SimulationType, which was then instantiated in the subclass' constructors.

**Decision 2**: *Removes duplicated update and createCopy methods in DeadCell and LiveCell*
The *update* methods in LiveCell and DeadCell were identical, and the *createCopy* methods in LiveCell and DeadCell were implemented almost exactly the same, but instantiated different subclasses of Cell. To fix this, a new abstract class called LifeSimCell was created, which extends cell. LiveCell and DeadCell were then changed to extend LifeSimCell. The *update* and *createCopy* methods were moved into LifeSimCell. Because *update* calls *changeState*, but *changeState* is implemented differently by LiveCell and DeadCell, it was made a protected, abstract class. 

-------
##Checklist Refactoring
**Decision 1:** *Changed public instance variable myStage in PopUp to private*
This was a relatively quick fix. Looking through the PopUp class and all classes that access it, I found that nothing relied on this instance variable being public. Thus, changing it to private improved the design without disturbing the rest of the program.

**Decision 2** *Remove magic numbers in getCells method in SimulationType subclasses*
In the *getCells* method, each String in the List of data is split, and index 2 of the resulting array contains the name of the Cell to be created. In every implementation, this index is accessed and compared to a String to determine which kind of Cell is being requested. To fix this, I decided to create a protected final variable holding this index. Thus, this formatting design is clearly present in the superclass, and known not to be specific to a certain design, or completely arbitrary.