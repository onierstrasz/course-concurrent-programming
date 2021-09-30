
This projects demonstrates a Linda-style computation performed with multiple threads coordinating through a JavaSpaces tuple space.

Works with Java 1.5 only.

Unzip fly.zip and run the startFly.* script for your platform to start the tuple space server.

Then run FibDemo for an example of a multithreaded application performing a computation coordinating via the tuple space.

NB: To stop the server, you will have to run, e.g., "ps -A | grep fly" to get the process id.  Then you can kill the process manually (with "kill").

More information about this implementation of JavaSpaces as well as the complete download (minus the demo program) can be found here:

www.flyobjectspace.com

2009-02-10
