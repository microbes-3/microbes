CLASSPATH=src:/usr/share/java/weka.jar:/usr/share/java/junit4.jar
TESTCLASS=org.junit.runner.JUnitCore
MAINCLASS=Microbes
TARGETS=src/*.java
TESTTARGETS=VoterTest WeightedDecisionMakerTest

all: build
build:
	javac -cp $(CLASSPATH) -Xlint $(TARGETS)
run:
	java -cp $(CLASSPATH) $(MAINCLASS)
test:
	java -cp $(CLASSPATH) $(TESTCLASS) $(TESTTARGETS)
