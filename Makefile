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
build-classifiers:
	$(RUN) build-classifiers
classify:
	$(RUN) classify
test:
	java -cp $(CLASSPATH) $(TESTCLASS) $(TESTTARGETS)
zip:
	rm submission.zip
	zip -r submission.zip src *.predict Makefile README.md .classpath .project -x src/*.class
