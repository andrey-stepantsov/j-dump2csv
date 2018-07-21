#!/bin/bash
# 'bootstrap.sh'
apt-get update -y 
sudo apt-get install vim -y
apt-get -y install zsh htop
sh /vagrant/mysql.sh
