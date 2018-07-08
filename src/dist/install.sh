version="1.0.1"
install_path="/opt"
run_path="/usr/local/bin"
app="j-dump2csv"
echo "Installing $app, version: $version"

if [ ! -d $install_path ]
then
    echo "$app is expected to install to '""$install_path""' directory;"
    echo "please, create the '""$install_path""' directory"
    exit 1
fi

if [ -w $install_path ]
then
    full_app_path="$install_path/$app-$version"
    #echo "full path: $full_app_path"
    mkdir $full_app_path 2>/dev/null
    cp -r bin/ $full_app_path/
    cp -r lib/ $full_app_path/
    echo "$full_app_path/bin/$app "'"''$@''"' > $run_path/$app
    chmod ugo+x $run_path/$app
else
    echo "need permission to write to '""$install_path""', conisder using: 'sudo sh ./install.sh'"
fi
