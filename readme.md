# j-dump2csv #
## Version ##
This document describes version 1.0.1 of j-dump2csv, released on 2018-07-07
## Synopsis ##
~~~
$ j-dump2csv < testdump.conf > testresult.csv 2 > error.log 
~~~
The command above does:
 * load a configuration file 'testdump.conf' from the standard input
 * output to a CSV file 'testresult.csv' from standard output
 * print all the errors to 'error.log'
  
The 'testdump.conf' file in the example above could look like:
~~~
dbtype: H2
uri: "jdbc:h2:mem:"
database: test1
user: sa
password: sa
query: |-
  SELECT * FROM CSVREAD('sample.csv');
output_format: CSV
~~~
## Description ##
The j-dump2csv utility reads data from a database (currently only from Oracle and H2)
or a CSV files and prints delimited data to standard output.

The main intent of the utility is to provide a way to export data from databases.

Because of the utility's ability to read CSV files as relational data, it also can
be used for filtering and transforming CSV.
The SQL engine behind the scene is [H2](http://h2database.com)
 
## Installation ##
The j-dump2csv utility is available as zip and tar distributions, and it requires Java 8 runtime.
To interface Oracle databases an Oracle's JDBC driver is required and it can be downloaded here:
[Oracle JDBC Driver 12c](http://www.oracle.com/technetwork/database/features/jdbc/jdbc-drivers-12c-download-1958347.html)
The ojdbc7.jar file must be placed into the 'lib' folder of distribution.

### Install on Linux ###
```
# assuming that the tar file was downloaded to the user's '~/Downloads' folder
# and the '/opt' folder exists
$ cd /opt
$ sudo tar -xvf ~/Downloads/j-dump2csv-1.0.1.tar
$ suso cat 
```

## Appendix ##

### Misc notes ###

#### H2 - column names with special characters ####

Consider a column name USER.ID.RAW, to refer is in a SQL query use double quotes:
~~~
query: |-
  SELECT * FROM CSVREAD('data.csv') WHERE "USER.ID.RAW" like '%200%';
~~~

#### H2 - reading TAB separated files ####

~~~
dbtype: H2
uri: "jdbc:h2:mem:"
database: test1
user: sa
password: sa
query: |-
  SELECT * FROM CSVREAD('data.tsv', null, STRINGDECODE('fieldSeparator=\t'));
output_format: CSV
~~~

### Complete Example: Filtering CSV files ###

#### Input CSV file (test.csv) ####
~~~
CODE,ALIAS
0001,Snake
0002,Bull
0004,Bear
~~~

#### Command line ####
~~~
$ j-dump2csv < testdump.conf
~~~

#### Sample output ####
~~~
CODE,ALIAS
0001,Snake
~~~

#### Config (testdump.conf) ####
~~~
dbtype: H2
uri: "jdbc:h2:mem:"
database: test1
user: sa
password: sa
query: |-
  SELECT * FROM CSVREAD('sample.csv') WHERE CODE like '%1';
output_format: CSV
~~~