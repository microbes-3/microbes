# microbes

Requires Weka 3.6 (incompatible with 3.7).

* Weka docs: http://weka.sourceforge.net/doc.stable/
* Competition: https://competitions-test.codalab.org/competitions/1104

## Usage

```bash
make # Build app

make select-features # Select most important attributes
make build-classifiers # Build classifiers with best params
make classify # Classify test & valid data

make zip # Create a zip file for submission
make rank-voters # Create graphs for voters
```
