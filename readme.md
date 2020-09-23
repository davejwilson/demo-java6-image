# Java 6 Docker Image Demo
Show how to bundle Java 6 and App using non-root container.

## Package the Demo App
`mvn package`  
:information_source: Select a Java 6 JDK for the project and Java 8+ for your maven runner.

## Build the Container Image
`./build`

## Run the Image
`./run`

## Startup MariaDB Database
`docker compose up -d`

## Test MariaDB
`http://localhost:8000/keywords`