CLASSPATH=src:/usr/share/java/weka.jar:/usr/share/java/junit4.jar
TESTCLASS=org.junit.runner.JUnitCore
MAINCLASS=Microbes
TARGETS=src/*.java
TESTTARGETS=VoterTest WeightedDecisionMakerTest

RUN=java -cp $(CLASSPATH) $(MAINCLASS)

all: build
build:
	javac -cp $(CLASSPATH) -Xlint $(TARGETS)
run:
	$(RUN)
select-features:
	$(RUN) select-features
build-models:
	$(RUN) build-models
classify:
	$(RUN) classify
test:
	java -cp $(CLASSPATH) $(TESTCLASS) $(TESTTARGETS)
zip:
	zip -r submission.zip src *.predict Makefile README.md .classpath .project
