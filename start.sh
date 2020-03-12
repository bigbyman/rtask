gradle build
java -jar build/libs/rtask-0.0.1-SNAPSHOT.jar & echo $! > ./pid.file & (cd ../rtask-angular ; ng serve --open & echo $! > ./pid.file)