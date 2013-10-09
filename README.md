Requirements
---------------------

 * Java (version = 6)
 * maven (version >= 3)
 * git

Downloads
----------------

I've set up a job on our CI Jenkins instance, the latest executable jar can be downloaded from:

https://code.ceres.auckland.ac.nz/jenkins/job/Phyml-Prototype/

Checking out code
---------------------

    git clone https://github.com/makkus/phyml-prototype.git

Importing into Eclipse
-------------------------------

 * File -> Import... -> Maven -> Existing Maven Projects
 * Next
 * Browse to this directory
 * Finish


Building outside of IDE
---------------------------------

    mvn clean install


Running gui
-----------

    java -jar target/phyml-prototype-binary.jar <ControllerClassName>

for example:

    java -jar target/phyml-prototype-binary.jar ExampleController

