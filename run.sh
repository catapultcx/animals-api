#!/bin/sh
dev=false
JAR_PATH=/usr/src/api/
JAR_FILE="$JAR_PATH"app.jar

buildFn() {
  if [ ! -f "$JAR_FILE" ]; then
    echo "Building JAR"
    cd $JAR_PATH
    if [ "$dev" = true ]; then
      mvn spring-boot:run
    else
      mvn clean package
      mv target/*.jar $JAR_FILE
    fi
  fi
}

startFn() {
  docker-compose up -d db
  MYSQL="mysql -h localhost -u root -ppassword animals"
  echo "Waiting for MySQL service to start..."
  while ! docker exec animalsdb bash -c "$MYSQL"' -e "select 1"' >/dev/null 2>&1; do
    sleep 1
  done
  ## Populate
  docker exec -it animalsdb bash -c "$MYSQL < /usr/src/db/animals.sql"
  docker exec -it animalsdb bash -c "$MYSQL"' -e "select * from animaltype"'
  docker exec -it animalsdb bash -c "$MYSQL"' -e "select * from animal"'
  ## Start all
  docker-compose up
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
  "shell")
    docker exec -it animalsdb bash
  ;;
  "start")
    startFn
  ;;
  "build")
    buildFn
  ;;
  "populate")
    populateFn
  ;;
  *)
    waitFn
    runFn
  ;;
esac