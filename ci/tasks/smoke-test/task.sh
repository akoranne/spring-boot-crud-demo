#!/bin/bash

set -e -u -x # fail fast

echo ""
echo " .. installing needed packages "
echo ""

export NODE_PATH="/usr/lib/node_moudles"
npm install -g markdown-pdf --unsafe-perm=true --allow-root
npm install newman

echo ""
echo " .. Running newman tests"
echo ""

# output target folder should have been creaed
# mkdir -f ../test-output

cd service-repo

# execute newman tests
newman run src/test/resources/postman/PostmanEcho.postman_collection | tee ../test-output/smoke_test_results.out

# generate pdf documents from markdown and asciidocs
markdown-pdf *.md
mv *.pdf ../test-output

echo ""
echo " .. output files "
ls -l ../test-output

echo ""
echo " .. moving output files to artifactory"
echo " .. TODO - need artifactory endpoints "
# TODO: CTS
# move the files from 'test-output' to Artifactory
#
#

echo ""
echo " Smoke Test completed!!!"
echo ""
