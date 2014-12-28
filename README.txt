PrefsMeta Java API
------------------
Copyright (C) 2014 Tilmann Kuhn
See LICENSE.txt for details of the MIT license.

Contents of this readme:

* Purpose
* Target audience
* Installation notes
* Change history
* Contents of the release
* How to get started
* How to build
* How to contact me 
* Limitations and bugs


PURPOSE

PrefsMeta is a Java API that enriches the standard Java Preferences API to
store meta data about the preferences. This meta data can be used to
dynamically generate Swing GUIs to edit these preferences for example.



TARGET AUDIENCE

Java developers that want to provide a GUI to edit Java Preferences.



INSTALLATION NOTES

Just put the jar file and the jars from the lib directory into your classpath.



CHANGE HISTORY

0.6.2 - Updated README, LICENSE
0.6.1 - Fixed build script 
      - Fixed README
      - Fixed JavaDoc
0.6   - The first release



CONTENTS OF THE RELEASE

README.txt         this file
LICENSE.txt        license of PrefsMeta
build.xml          ant build script

docs/              contains documentation about PrefsMeta
src/               contains the PrefsMeta source files
lib/               contains libraries PrefsMeta depends on, currently only SwingUtils
test_src/          contains demo files for the use of PrefsMeta



HOW TO GET STARTED

I recommend taking a look at the source demos in test_src/ and at the api-docs
in docs/api/



HOW TO BUILD

I recommend usage of the ant building environment (http://ant.apache.org/)
together with the provided build script (build.xml). An eclipse project file 
is also included.



HOW TO CONTACT ME

http://www.tkuhn.de
prefsmeta@tkuhn.de



LIMITATIONS AND BUGS

- Some features not implemented yet:
  o Deletion of Preferences
  o Additon and deletion of Preferences Nodes

Please report your experiences to me.