CMSC 125 OS Game
- program name: cOSmic invasion
- developers: Norlyn Jane Castillo, Charissa Mae Madriaga, Jannah Esplanada

## 🛠️ How to compile and run the source code

a. navigate to the src folder directory in the cOSmic-invasion/src

b. copy the following lines of instructions to compile the program

For Linux:
```
javac -cp .:lib/* -d . com/model/*.java
javac -cp .:lib/* -d . com/view/component/*.java
javac -cp .:lib/* -d . com/view/*.java
javac -cp .:lib/* -d . Main.java
```

For Windows:
```
javac -cp ".;lib/*" -d . com/model/*.java
javac -cp ".;lib/*" -d . com/view/component/*.java
javac -cp ".;lib/*" -d . com/view/*.java
javac -cp ".;lib/*" -d . Main.java
```

c. lastly, run the program using the compiled Main class

For Linux:
```
java -cp .:lib/*:. Main
```
------


For Windows:
```
java -cp ".;lib/*;." Main
```
