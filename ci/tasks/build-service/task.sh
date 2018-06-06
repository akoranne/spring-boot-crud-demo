#!/bin/bash

set -e # fail fast
#	set -x # print commands

echo ""
echo " .. Running build"
echo ""

cd service-repo

# gradle build
export GRADLE_OPTS="-Dorg.gradle.native=false"
./gradlew clean test assemble

# create target folder
# mkdir -f ../build-output

# move all manifests file to target
cp manifest.yml  ../build-output/

cp build/libs/*.jar ../build-output/


# change dir to the libs folder
cd build/libs

# get the file name from the archive
export vers=vers=$(ls -1 *.jar);
export vers=${vers%-*};
# export vers=${vers##*-};
echo " .. current version - ${vers} ";

# go back
cd ../../

# copy the reports of the tests to the output folder
cp -rfv build/reports  ../build-output/${vers}-reports


echo ""
echo " .. output files "
$(ls -l ../build-output)

echo ""
echo " .. moving output files to Artifactory"
echo " .. TODO - need artifactory endpoints "
# TODO: CTS
# move the files from 'test-output' to Artifactory
#
#



echo ""
echo " Build completed!!!"
echo ""
