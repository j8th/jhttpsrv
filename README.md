jhttpsrv
========
A small http server written in Java.


## Required Software
Gradle can be installed via Homebrew.
`brew install gradle`


## Running the Tests
`gradle test --info`


## Creating the Jar
`gradle jar`


## Running the Server
`java -jar build/libs/jhttpsrv-$VERSION.jar`

By default, the server runs on port 8080 and tries to use $CURRENT-WORKING-DIR/www/ as a root directory from which to serve files.

These options can be changed with --port and --directory.  Short options (-p and -d) are also supported.

Example:
Serve files from /var/www on port 9090.

`java -jar build/libs/jhttpsrv-$VERSION.jar -p 9090 -d /var/www`


