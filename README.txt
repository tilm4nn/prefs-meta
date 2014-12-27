                   README: PrefsMeta 0.6 Release
                   -----------------------------
                            August 2003


Contents:

* Installation notes
* Target audience
* Changes since the 0.6 release
* Contents of the release
* How to get started
* How to build
* How to reach me 
* Limitations and bugs
* Copyright


INSTALLATION NOTES

Just put the jar file and the jars from the lib directory into your classpath.



TARGET AUDIENCE

Java developers that want to provide a GUI to edit Java Preferences.



CHANGES SINCE THE 0.6 RELEASE

0.6   - The first release



CONTENTS OF THE RELEASE

README.txt         this file
LICENSE.txt        license of SwingUtils
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



HOW TO REACH ME

The e.ways to reach me:
http://www.tkuhn.de
prefsmeta@tkuhn.de



LIMITATIONS AND BUGS

- Some features not implemented yet:
  o Deletion of Preferences
  o Additon and deletion of Preferences Nodes

Please report your experiences to prefsmeta@tkuhn.de




THE MIT LICENSE

Copyright (C) 2003 
Tilmann Kuhn           Gildestr. 34
http://www.tkuhn.de    76149 Karlsruhe
prefsmeta@tkuhn.de     Germany

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.