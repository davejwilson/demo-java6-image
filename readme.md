# Java 6 Docker Image Demo
Show how to bundle Java 6 and App using non-root container.

## IDE Settings
* Select Java 6 for the project
* Select Java 8+ for your maven runner

## Package Demo App & Build Image
`mvn install`

## Run the App Image & MariaDB
`docker compose up -d`

## Test MariaDB
`http://localhost:8000/keywords`