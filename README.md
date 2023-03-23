## LAGALT.NO API

### Hva er dette?

Dette er API/Backend for Lagalt.no. Frontend ligger på et seperat repository her: https://github.com/yomirobera/lagalt_frontend.
Lagalt API innkluderer en rekke API endpoints som utnyttes av frontenden.
Lagalt utnytter seg av Java med Spring Boot, med en Postgresql database. Hibernate
blir brukt til å forenkle operasjoner med databasen. Entiteter i databasen genereres
ved start av applikasjonen fra definerte modeller. Backend benytter seg av også
controller-service-repository mønstret.

### Hvem arbeidet med dette?

Lagalt gruppen består av:
- Kenny Lu
- Mebrahtu Zerizghi Gebremedhin
- Simen Skaarseth
- Yomiy Robera
- Juni Stenberg

Kenny Lu og Simen Skaarseth var involvert i utvikling av dette API (backend-team).

### Deployment

Dette API'et er deployed til azure.
https://superproapiavkennylu.azurewebsites.net/api

Eksempel på et endepunkt: https://superproapiavkennylu.azurewebsites.net/api/v1/users/

Andre endepunkter inklduere CRUD operasjoner for alle entiter:
Users, Applications, Projects and Comments
