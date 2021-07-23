### SRE Interview Instructions

Please follow the following steps in order to run the backend application using docker compose.

#### Clone Repositories

```
mkdir /tmp
git clone https://github.com/catapultcx/animals-api
```

#### Start the backend application with docker-compose

```
cd /tmp/animals-api
docker-compose up
```

#### Run the Jmeter Performance Test for Backend App

```
cd /tmp/animals-api/jmeter-test
jmeter -n -t backend-test-plan.jmx -j backend-test-plan.log
```
