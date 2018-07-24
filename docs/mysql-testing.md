
# For Vagrant 2 #

run:
~~~
$ vagrant init hashicorp/precise64
~~~
edit 'Vagrantfile' file, so it contains:
~~~ruby
# Vagrant
Vagrant.configure("2") do |config|
  #...
  config.vm.network "forwarded_port", guest: 3306, host: 13306
  config.vm.provision :shell, path: "bootstrap.sh"
  #...
end
~~~

Place the 'bootstrap.sh' and 'mysql.sh' files into your testing machine directory.
Sample copies of the files are installed with the utility (by default into: $HOME/opt/j-dump2csv-x.x.x/vagrant_mysql).

~~~
#!/bin/bash
# 'bootstrap.sh'
apt-get update -y 
sudo apt-get install vim -y
apt-get -y install zsh htop
sh /vagrant/mysql.sh
~~~

~~~
#!/bin/bash
#'mysql.sh'
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
~~~

run the following, it will comment out all the 'bind-address' options in the MySQL config:
~~~
#
vagrant up
vagrant ssh
#after being promted with the shell 
cp /etc/mysql/my.cnf /home/vagrant/my.cnf.bak
sed -e 's/\(^bind-address\)/#\1/g' ~/my.cnf.bak >~/my.cnf
sudo cp /home/vagrant/my.cnf /etc/mysql/my.cnf
rm ~/my.cnf
sudo restart mysql
~~~
