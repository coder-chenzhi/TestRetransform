# Introduction
The code is borrowed from this site
https://gist.github.com/nickman/6494990#file-transformerservice-java

To run these code, you can see the corresponding stack overflow post 
http://stackoverflow.com/questions/18567552/how-to-retransform-a-class-at-runtime



# Issue

The code depends on tools.jar and javassist.jar. tools.jar is under JDK's /lib path, javassist.jar need to be downloaded from Internet.

If `com.sun.tools.attach.AttachNotSupportedException: no providers installed` occurred, you can fix it by change the build path from JRE to JDK, because the attach.dll is located in JDK's path.

For more details, you can see this stack overflow post 
http://stackoverflow.com/questions/14291556/error-while-using-attach-api
