# Readm me

## Introduction

In this repository you may find two applications:
- The first simulates the behavior of a client, you may find it in the `client` folder.
- the second simulates the behavior of a server, you may find it in the `server` folder.

The two are supposed to work together, running at the same time in different process/devices.

## Client
The client open a `socket` on `127.0.0.1:8000`, and send an integer between 0 and 1000 every 3 seconds.
## Server
The server open a `ServerSocket` listening on `8000` and can manage multiple client connections. It retrieves integers from the clients and stores it. Every 3 seconds it displays per client the last 4 received values, the number of received values and a global average of the received values.
## Setup
To set up the applications :
You will need a JDK for Java 8, maven 3 and a git client. Personally I used `JDK-15.0.2` and `Maven 3.8.4`.
1. Open a terminal and a folder where you want to clone the project.
2. Clone the project:
`git clone https://github.com/guyotn/eaton-client-server.git`
(you can pass this step by downloading the application from the github repository)
3. Go into the project and compile the server application:
`cd eaton-client-server/server`
`mvn install`
4. In an other terminal do the same in the client folder.
5. In the first terminal launch the server:
`java -cp target/server-0.0.1-SNAPSHOT.jar server.Server`
6. Once the server is running you can launch several clients in the 2nd terminal:
`java -cp target/client-0.0.1-SNAPSHOT.jar client.Client`
add `&` at the end of the command tu run the client/server in background.

## TO DO
This prototype has been made in 2 days, several things still need to be done to make this project cleaner.
Here is the list of things to add to the application to finish it. Most of the items are sort by importance order.
 - Add options to the client program like `--ip String --port int` to target a particular server, for now it is hard code.  the ip adresse is `127.0.0.1` and the port is `8000`.
 - Add an input parser to properly close the client and server. For now the cleaning functions are created but never called. To stop the client or the server you have to kill the process.
 - Add a proper Junit test suit. For now the behavior has been tested by hand.
 - Comment the code, add headings to the functions to generate a javadoc.
 - Several options for the client and the server:
  - Server: number of ressource to display.
  - Server: which port to listen.
  - Client: transmited data range.
 - git rebase for a clean working tree. 

## Ressources
Here are a list of ressources used to create the project:
 - [Create maven project](http:https://openclassrooms.com/en/courses/4503526-organisez-et-packagez-une-application-java-avec-apache-maven/4608897-creez-votre-premier-projet-maven// "Create maven project").
 - [Oracle documentation on sockets](https://docs.oracle.com/javase/7/docs/api/java/net/Socket.html) and [an example](https://www.codeflow.site/fr/article/a-guide-to-java-sockets).
 - [Synchronized collections](https://www.codejava.net/java-core/collections/understanding-collections-and-thread-safety-in-java).
