all: build run
build:
	javac -cp src:/usr/share/java/weka.jar:/usr/share/java/junit4.jar src/*.java
run:
	java -cp src:/usr/share/java/weka.jar Microbes
test:
	java -cp src:/usr/share/java/weka.jar:/usr/share/java/junit4.jar org.junit.runner.JUnitCore WeightedDecisionMakerTest
