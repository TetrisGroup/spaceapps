#!/bin/bash

mkdir /tmp/stream

raspistill --nopreview -w 320 -h 240 -q 5 -o /tmp/stream/pic.jpg -tl 50 -t 9999999 -th 0:0:0 &

LD_LIBRARY_PATH=/usr/local/lib mjpg_streamer -i "input_file.so -f /tmp/stream -n pic.jpg" -o "output_http.so -w /usr/local/www" &


