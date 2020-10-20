#!/bin/zsh

rm *.log*
rm *.png
rm *.out

./test.sh 14.0.0.hs-adpt openjdk

sleep 60

./test.sh 14.0.0.hs-adpt corretto

/Users/sanchezmariscala/Library/Python/3.9/bin/hdr-plot --output latencies.png --title "Latencies" ./openjdk.out ./corretto.out

open latencies.png
