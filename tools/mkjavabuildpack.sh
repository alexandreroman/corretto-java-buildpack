#!/bin/bash
#
# Use this script to package Amazon Corretto before
# it can be used by the Cloud Foundry Java Buildpack.
#
# This script can be used with Docker:
#  $ docker run --rm -v $PWD/docker/out:/out -v $PWD/docker/mkjavabuildpack.sh:/mkjavabuildpack.sh centos:7 bash /mkjavabuildpack.sh
#
curl https://d3pxv6yz143wms.cloudfront.net/java-1.8.0-amazon-corretto-1.8.0_192.b12-1.amzn2.x86_64.rpm > c.rpm
yum localinstall -y c.rpm
cd /usr/lib/jvm/java-1.8.0-amazon-corretto.x86_64/jre
tar zcf /out/java-1.8.0-amazon-corretto-1.8.0_192.b12-1.amzn2.x86_64.tar.gz .
