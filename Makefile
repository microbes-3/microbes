CLASSPATH=src:/usr/share/java/weka.jar:/usr/share/java/junit4.jar
MAINCLASS=Microbes
TESTCLASS=org.junit.runner.JUnitCore

all: build run
build:
	javac -cp $(CLASSPATH) -Xlint src/*.java
run:
	java -cp $(CLASSPATH) $(MAINCLASS)
test:
	java -cp $(CLASSPATH) $(TESTCLASS) WeightedDecisionMakerTest
