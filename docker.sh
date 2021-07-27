mvn clean package
docker build -t animals-api .
docker tag animals-api:latest catapultcx/animals-api:latest
docker push catapultcx/animals-api:latest
