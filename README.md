differ - comparator/reporter of filesystem changes
==================================================
[![Build Status](https://travis-ci.org/plafue/differ.svg)](https://travis-ci.org/plafue/differ)

This little tool will recursivelly compare two directory trees and report changes in their structure. 
It also detects modified files and provides a nice overview of the changes in a side-by-side view.

Its main purpose is to generate HTML reports of modifications in (diff friendly) 
documents that may be relevant when performing a new software release (changes in gherkin/feature files, markdown documentation, etc).

![Screenshot](/misc/screenshot.png?raw=true "Screenshot")


## Nanos gigantum humeris insidentes
This project uses [freemarker](http://www.freemarker.org) as a templating engine, [codemirror](http://codemirror.org) 
(with light modifications waiting to be merged upstream) for diff visualisation and the usual suspects (guava, assertj, java 1.8, maven, commons-lang3, commons-cli, yadda yadda yadda...).  