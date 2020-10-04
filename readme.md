# Java 6 Docker Image Demo
Show how to bundle Java 6 and App using non-root container.

## IDE Settings
* Select Java 6 for the project
* Select Java 8+ for your maven runner

## Package Demo App & Build Image
`mvn install`

## Run the App Image & MariaDB
* Set the `ORACLE_HOSTNAME` to the IP address of your host machine.
* `docker compose up -d`  

## Test MariaDB
`http://localhost:8000/keywords`

## Test Oracle XE on Host
`http://localhost:8000/tables`
