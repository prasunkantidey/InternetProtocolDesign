#! /bin/bash

rm -rf ~/Documents/NETWORKING

mkdir ~/Documents/NETWORKING
mkdir ~/Documents/NETWORKING/Node1
mkdir ~/Documents/NETWORKING/Node2
mkdir ~/Documents/NETWORKING/Node3
mkdir ~/Documents/NETWORKING/Node4

cp -R ~/git/CourseWork/CPE701/CPE701 ~/Documents/NETWORKING/Node1
cp -R ~/git/CourseWork/CPE701/CPE701 ~/Documents/NETWORKING/Node2
cp -R ~/git/CourseWork/CPE701/CPE701 ~/Documents/NETWORKING/Node3
cp -R ~/git/CourseWork/CPE701/CPE701 ~/Documents/NETWORKING/Node4

rm -rf ~/Documents/NETWORKING/Node1/CPE701/bin/*
rm -rf ~/Documents/NETWORKING/Node2/CPE701/bin/*
rm -rf ~/Documents/NETWORKING/Node3/CPE701/bin/*
rm -rf ~/Documents/NETWORKING/Node4/CPE701/bin/*

mv ~/Documents/NETWORKING/Node1/CPE701/ITC ~/Documents/NETWORKING/Node1/CPE701/bin/ITC
mv ~/Documents/NETWORKING/Node2/CPE701/ITC ~/Documents/NETWORKING/Node2/CPE701/bin/ITC
mv ~/Documents/NETWORKING/Node3/CPE701/ITC ~/Documents/NETWORKING/Node3/CPE701/bin/ITC
mv ~/Documents/NETWORKING/Node4/CPE701/ITC ~/Documents/NETWORKING/Node4/CPE701/bin/ITC


javac -d ~/Documents/NETWORKING/Node1/CPE701/bin ~/Documents/NETWORKING/Node1/CPE701/src/com/cpe701/helper/*.java ~/Documents/NETWORKING/Node1/CPE701/src/com/cpe701/layers/*.java ~/Documents/NETWORKING/Node1/CPE701/src/com/cpe701/packets/*.java

javac -d ~/Documents/NETWORKING/Node2/CPE701/bin ~/Documents/NETWORKING/Node1/CPE701/src/com/cpe701/helper/*.java ~/Documents/NETWORKING/Node1/CPE701/src/com/cpe701/layers/*.java ~/Documents/NETWORKING/Node1/CPE701/src/com/cpe701/packets/*.java

javac -d ~/Documents/NETWORKING/Node3/CPE701/bin ~/Documents/NETWORKING/Node1/CPE701/src/com/cpe701/helper/*.java ~/Documents/NETWORKING/Node1/CPE701/src/com/cpe701/layers/*.java ~/Documents/NETWORKING/Node1/CPE701/src/com/cpe701/packets/*.java

javac -d ~/Documents/NETWORKING/Node4/CPE701/bin ~/Documents/NETWORKING/Node1/CPE701/src/com/cpe701/helper/*.java ~/Documents/NETWORKING/Node1/CPE701/src/com/cpe701/layers/*.java ~/Documents/NETWORKING/Node1/CPE701/src/com/cpe701/packets/*.java



mv ~/Documents/NETWORKING/Node1/CPE701/RunNode1.command ~/Documents/NETWORKING/Node1/CPE701/bin/RunNode1.command
mv ~/Documents/NETWORKING/Node2/CPE701/RunNode2.command ~/Documents/NETWORKING/Node2/CPE701/bin/RunNode2.command
mv ~/Documents/NETWORKING/Node3/CPE701/RunNode3.command ~/Documents/NETWORKING/Node3/CPE701/bin/RunNode3.command
mv ~/Documents/NETWORKING/Node4/CPE701/RunNode4.command ~/Documents/NETWORKING/Node4/CPE701/bin/RunNode4.command

chmod u+x ~/Documents/NETWORKING/Node1/CPE701/bin/RunNode1.command
chmod u+x ~/Documents/NETWORKING/Node2/CPE701/bin/RunNode2.command
chmod u+x ~/Documents/NETWORKING/Node3/CPE701/bin/RunNode3.command
chmod u+x ~/Documents/NETWORKING/Node4/CPE701/bin/RunNode4.command

mv ~/Documents/NETWORKING/Node1/CPE701/space.jpg ~/Documents/NETWORKING/Node1/CPE701/bin/space.jpg
