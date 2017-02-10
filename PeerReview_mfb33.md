#Peer Code Review
-------
##Code Smell Refactoring
**Decision #1**
The first thing that stood out in our code was the duplication in LiveCell and DeadCell -- these were the parts of the code that I personally wrote. To fix this, I created an abstract superclass, similar to WaterWorldCell, which LiveCell and DeadCell extend. This superclass holds the duplicate code, leaving the unique implementation to the subclasses. 
This superclass, LifeSimulationCell, makes the code more readable and cuts down on unnecessary repetition.

-------
##Checklist Refactoring