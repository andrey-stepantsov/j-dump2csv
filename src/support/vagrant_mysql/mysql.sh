#!/bin/bash
pass="test" # for 'root' and for 'testuser'

#installing mysql
echo "mysql-server mysql-server/root_password password ${pass}" | sudo debconf-set-selections
echo "mysql-server mysql-server/root_password_again password ${pass}" | sudo debconf-set-selections
apt-get install -y mysql-server

start mysql

mysqladmin -ptest create testdb
cat << EOF | mysql -p${pass} testdb
CREATE TABLE info (code varchar(10), name varchar(128), description varchar(128));
INSERT INTO info () values("0001","snake","lives in the desert");
INSERT INTO info () values("0002","rat","lives in the hole");
EOF

cat << EOF | mysql -p${pass} testdb
CREATE USER 'testuser'@'%' IDENTIFIED BY 'test';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY '${pass}' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON *.* TO 'testuser'@'%' IDENTIFIED BY '${pass}' WITH GRANT OPTION;
FLUSH PRIVILEGES;
exit
EOF

