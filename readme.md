# Ride Service

Ride Service je zaledna mikrostoritev, ki skrbi za upravljanje voženj (rides).
Implementirana je v Spring Boot in uporablja PostgreSQL (PostGIS) podatkovno bazo.

## Tehnologije
- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL + PostGIS
- Hibernate Spatial
- Maven

## Funkcionalnost
- ustvarjanje voženj
- pridobivanje seznama voženj
- filtriranje voženj po uporabniku (driverId)
- shranjevanje prostorskih podatkov (start / end lokacija)

## Konfiguracija
Ride Service uporablja okoljske spremenljivke za povezavo do baze:

SPRING_DATASOURCE_URL  
SPRING_DATASOURCE_USERNAME  
SPRING_DATASOURCE_PASSWORD

Primer:
jdbc:postgresql://postgres:5432/rides

## Zagon lokalno
```
mvn clean package
java -jar target/ride-service-0.0.1-SNAPSHOT.jar
```

Storitev teče na:
http://localhost:8080

## Swagger dokumentacija
Swagger (OpenAPI) dokumentacija je na voljo na:
http://localhost:8080/swagger-ui/index.html

## Opombe
- Storitev pričakuje, da je PostgreSQL baza že inicializirana
- PostGIS razširitev mora biti omogočena v bazi