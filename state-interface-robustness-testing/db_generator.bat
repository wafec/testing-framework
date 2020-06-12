@echo off

cd src\main\resources\hbm\mysql\
python script_create.py | clip
echo "Copied to Clipboard!"