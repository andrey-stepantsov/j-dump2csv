#
# This is a helper script simply to help with testing of the 'tar' distribution.
#
# I run it as: sh ./misc/dinstall.sh
#
# In my /usr/local/bin I keep 'dj-dump2csv' script which is simply a one-liner:
#
#    /home/<myuser>/dev/j-dump2csv/out/j-dump2csv-1.0-1/bin/j-dump2csv
#
# The <myuser> is my local user name depending on my dev machine
#

install_path="out"

if [ -d $install_path ]
then
    echo "'$install_path' exists - installing"
    last_build=$(cd build/distributions && ls j-dump2csv-*.tar)
    echo "$last_build"
    tar -xf build/distributions/$last_build -C $install_path
else
    echo "'$install_path' does not exist - quiting"
fi
