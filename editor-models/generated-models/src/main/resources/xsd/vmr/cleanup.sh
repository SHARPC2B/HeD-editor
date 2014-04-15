#!/bin/bash

rm ./vmr.xsd
awk "/<xs:annotation>/{h=1};!h;/<\/xs:annotation>/{h=0}" ./original/vmr.xsd > ./vmr.xsd

rm ./datatypes.xsd
awk "/<xs:annotation>/{h=1};!h;/<\/xs:annotation>/{h=0}" ./original/datatypes.xsd > ./datatypes.xsd
