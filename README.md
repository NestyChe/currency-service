A service that accesses the service of exchange rates and sends a gif in response.
If the exchange rate against the ruble for today has become higher than yesterday,
then we give a random one from here https://giphy.com/search/rich,
if below - from here https://giphy.com/search/broke.
Spring Boot 2 + Gradle + Java 11, using REST conventions.
Java service Requests arrive at the HTTP endpoint, where the currency code is passed.
Feign is used to interact with external services.
Building and running with a Docker container.
---
How to use interface?
There will be a list on the main screen, you need to select the currency to compare
and click on the button with the name "GO".
***
## Host endpoint
```
http://localhost:8080
```
## Start jar
```
java -jar currency-service-0.0.1-SNAPSHOT.jar
```
## Docker
### Create image:
```
make image
```
### Start:
```
make run
```