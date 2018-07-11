#
# This is a helper script simply
# do not run it manually, instead
#    $ sh ./gradlew testTar
#
install_path="$1/out"

if [ -d $install_path ]
then
    echo "'$install_path' exists - installing"
    last_build="$2/distributions/j-dump2csv-$3.tar"
    #echo "$last_build"
    tar -xf $last_build -C $install_path
else
    echo "'$install_path' does not exist - quiting"
fi
