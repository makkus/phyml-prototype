Requirements
---------------------

 * Java (version = 6)
 * maven (version >= 3)
 * git

Downloads
----------------

I've set up a job on our CI Jenkins instance, the latest executable jar can be downloaded from:

https://code.ceres.auckland.ac.nz/jenkins/job/Phyml-Prototype/


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

    java -cp target/phyml-prototype-binary.jar

