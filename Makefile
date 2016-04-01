all: build run
build:
	javac -cp .:/usr/share/java/weka.jar Microbes.java
run:
	java -cp .:/usr/share/java/weka.jar Microbes
test:
	java -cp .:/usr/share/java/junit4.jar org.junit.runner.JUnitCore MicrobesTest
