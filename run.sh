#!/bin/sh
JAR_PATH=/usr/src/api/
JAR_FILE="$JAR_PATH"app.jar

buildFn() {
  if [ ! -f "$JAR_FILE" ]; then
    echo "Building JAR"
    cd $JAR_PATH
    mvn clean package
    mv target/*.jar $JAR_FILE
  fi
}

waitFn() {
  while [ ! -f "$JAR_FILE" ]
  do
    sleep 1
  done
}

runFn() {
  java -jar $JAR_FILE
}

case "$1" in
  "build")
    buildFn
  ;;
  *)
    waitFn
    runFn
  ;;
esac