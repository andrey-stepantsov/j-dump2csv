# j-dump2csv #
## Description ##
The j-dump2csv utility reads data from a database (currently only from Oracle and H2)
or CSV files and prints delimited data to standard output.

The main intent of the utility is to provide a way to export data from databases.

Because of the utility's ability to read CSV files as relational data, it also can
be used for filtering and transforming CSV. Check out this advanced [example with JOIN](https://github.com/andrey-stepantsov/j-dump2csv/wiki/Using-%27JOIN%27-with-CSV).

The SQL engine behind the scene is [H2](http://h2database.com).
## Synopsis ##
~~~
$ j-dump2csv < testdump.conf
~~~
The command above does:
 * load a configuration file 'testdump.conf' from the standard input:
~~~
dbtype: H2
uri: "jdbc:h2:mem:"
database: test
query: |-
  SELECT * FROM CSVREAD('sample.csv') WHERE CODE like '%1';
output_format: CSV
~~~
 * reads the 'sample.csv':
~~~
CODE,ALIAS
0001,Snake
0002,Bull
0004,Bear
~~~
 * prints to standard output:
~~~
CODE,ALIAS
0001,Snake
~~~
  
## Version ##
This document describes version 1.0.1 of j-dump2csv, released on 2018-07-08
## Downloads ##
[tar](https://github.com/andrey-stepantsov/j-dump2csv/releases/download/1.0.1/j-dump2csv-1.0.1.tar)
[zip](https://github.com/andrey-stepantsov/j-dump2csv/releases/download/1.0.1/j-dump2csv-1.0.1.zip)
 
## Installation ##
The j-dump2csv utility is available as zip and tar distributions, and it requires Java 8 runtime.
To interface Oracle databases an Oracle's JDBC driver is required and it can be downloaded here:
[Oracle JDBC Driver 12c](http://www.oracle.com/technetwork/database/features/jdbc/jdbc-drivers-12c-download-1958347.html)
The ojdbc7.jar file must be placed into the 'lib' folder of distribution. (For example: /opt/j-dump2csv-x.x.x/lib)

### Install on Linux ###
```
# assuming that the tar file was downloaded to the user's '~/Downloads' folder
# and the '/opt' folder exists
$ cd ~/Downloads
$ tar -xvf j-dump2csv-1.0.1.tar
$ cd j-dump2csv-1.0.1
$ sudo ./install
```

### Install on Windows ###
An install script for Windows is not provided. Unzip the distribution files and move the entire
folder to a desired destination, then add the 'bin' folder to your path.

## Appendix ##

### Misc notes ###

#### H2 - column names with special characters ####

Consider a column name USER.ID.RAW, to refer to it in a SQL query use double quotes:
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

#### Input CSV file (sample.csv) ####
~~~
CODE,ALIAS
0001,Snake
0002,Bull
0004,Bear
~~~

#### Command line ####
~~~
$ j-dump2csv < testdump.conf > testresult.csv 2 > error.log 
~~~

#### Sample output (testresult.csv) ####
~~~
CODE,ALIAS
0001,Snake
~~~

#### Config (testdump.conf) ####
~~~
dbtype: H2
uri: "jdbc:h2:mem:"
database: test
query: |-
  SELECT * FROM CSVREAD('sample.csv') WHERE CODE like '%1';
output_format: CSV
~~~

### Configuring MySQL for remote access  ###

~~~sh
#
# bootstrap.sh vor vagrant
apt-get update -y #&& apt-get upgrade -y

#echo "America/Los_Angeles" > /etc/timezone
echo "UTC" > /etc/timezone
dpkg-reconfigure -f noninteractive tzdata
sudo apt-get install vim -y

apt-get -y install zsh htop


echo "mysql-server mysql-server/root_password password test" | sudo debconf-set-selections
echo "mysql-server mysql-server/root_password_again password test" | sudo debconf-set-selections
apt-get install -y mysql-server

sudo mysqladmin -ptest create testdb
~~~

`mysql-install.sh`:
~~~sh
#!/bin/sh
cat << EOF | sudo mysql -ptest testdb
CREATE TABLE info (code varchar(10), name varchar(128), description varchar(128));
INSERT INTO info () values("0001","snake","lives in the desert");
INSERT INTO info () values("0002","rat","lives in the hole");
EOF

#any of:
#netstat -tln
#cat /etc/mysql/my.cnf |grep  port
#mysql> show global variables like 'port%';

#sudo /etc/init.d/mysql start
#  or
#sudo start mysql  

#ufw enable
#ufw allow 22
#ufw allow 3306
~~~

in `/etc/mysql/my.cnf`
find and comment 
~~~
...
bind-address           = 127.0.0.1
...
~~~

~~~
$ mysql -u root -ptest
mysql> GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY 'test' WITH GRANT OPTION;
mysql> GRANT ALL PRIVILEGES ON *.* TO 'vagrant'@'%' IDENTIFIED BY 'test' WITH GRANT OPTION;
mysql> FLUSH PRIVILEGES;
mysql> exit
$ sudo restart mysql
~~~

Timezone:
~~~
&serverTimezone=UTC
useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
~~~

~~~
$ mysql_tzinfo_to_sql /usr/share/zoneinfo | mysql -u root -ptest mysql
~~~

