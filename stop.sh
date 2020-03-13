kill $(cat ./pid.file)
sudo kill $(sudo lsof -t -i:4200)