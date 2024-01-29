

Objective:

The main objective of this project is to build a chat program. I have developed this program using two threads, which are main ones called as reading thread and writing thread. Using this application, we can send messages between connected users and also we can transfer files. 


---------------------------------------------Technical Details---------------------------------------------------

Programming Language Ð Java
Operating System(Used for development and Testing) Ð MAC OS

----------------------------------------------Files---------------------------------
ChatProgram.java -> This files handles the implementation of sending and receiving messages and also transferring files between connected users. In this program port numbers are assigned randomly for each user and then exchanged among users to establish connection

ChatProgram.class Ð> This is the class file generated upon compilation of ChatProgram.java using the commands below:
javac ChatProgram.java

Execution Steps :

1) Install or use any IDE which support Java program execution (I used Visual Studio as IDE)
2) Import project folder and open two terminals to start execution
3) All the files which needs to be uploaded to server needs to be kept in folder like downloadTestFile.pptx
4) In one terminal, first execute javac ChatProgram.java to compile in which generates  ChatProgram.class
5) Next, execute java ChatProgram to run the program, then it will ask for it's name, generate some random port number and other user's name
6) In other terminal, repeat step 5
7) Next give port numbers generated in two terminals to each other. Now both terminals are connected to each other. We can send messages and transfer files between them




