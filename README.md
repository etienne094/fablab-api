# API FabLab

API de prêt de matériel du FabLab associatif : imprimantes 3D, découpeuse laser, perceuses, ponceuses.
Spring Boot, Java 17, Maven, PostgreSQL et Flyway.

## Lancer

    ./mvnw spring-boot:run -Dspring-boot.run.profiles=h2      # sans base installée
    ./mvnw test

Avec PostgreSQL : variables `DB_URL`, `DB_USER`, `DB_PASSWORD` (voir `application.properties`).

## Développer

Une branche par fonctionnalité, partie de develop. Les tests passent avant toute fusion.

## Points d'entrée

- `GET  /api/materiels` : le matériel et sa disponibilité
- `POST /api/prets` : emprunter, corps `{"adherent": "ADH-042", "materiel": "IMP-3D-01", "duree": 7}`
- `PUT  /api/prets/{id}/prolongation` : prolonger de 7 jours
- `PUT  /api/prets/{id}/retour` : rendre
- `GET  /actuator/health`, `GET /actuator/info`

## Règles

- Un prêt dure de 1 à 14 jours.
- Un adhérent a au plus 3 prêts en cours.
- Un matériel déjà sorti ne peut pas être prêté.
- Un retard coûte 0,50 € par jour.
- Un prêt se prolonge une seule fois, de 7 jours au plus : 21 jours au total.
