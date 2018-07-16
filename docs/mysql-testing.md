

~~~ruby
#
# Vagrantfile
Vagrant.configure("2") do |config|
  config.vm.box = "hashicorp/precise64"
  config.vm.network "forwarded_port", guest: 3306, host: 13306
  config.vm.provision :shell, path: "bootstrap.sh"
end
~~~

~~~sh
#
# bootstrap.sh

apt-get update -y #&& apt-get upgrade -y

#echo "America/Los_Angeles" > /etc/timezone
#echo "UTC" > /etc/timezone
#dpkg-reconfigure -f noninteractive tzdata
apt-get install vim -y
apt-get -y install zsh htop


echo "mysql-server mysql-server/root_password password test" | sudo debconf-set-selections
echo "mysql-server mysql-server/root_password_again password test" | sudo debconf-set-selections
apt-get install -y mysql-server

sudo mysqladmin -ptest create testdb

~~~

Questions:
The default timezone is 'UTC'
 is 'vagrant' == 'root' for mysql?
 try: 
 `$ msysql -u 'vagrant'
 
~~~
CREATE USER 'newuser'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON * . * TO 'newuser'@'localhost';
~~~
