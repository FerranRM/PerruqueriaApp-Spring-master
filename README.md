# App D.Tellez Barber

Aplicació per als treballadors de la perruquería David Tellez Barber.
Consta de 5 pantalles principals ques es troben en un Bottom navigation per fer el seu accés més ràpid i cómode.

```diff
Aquestes 5 pantalles són:
 - Principal: Afegir un client fet amb les seves dades (Nom, gènere, servei prestat i/o productes comprats, preu total, data)
 - Calendari: visualitzar i editar els clients que atendrà el perruquer.
 - Estadístiques: veure estadístiques varies dels clients que ha fet el perruquer al llarg de l'any.
 - Ajustos: afegir o eliminar productes o serveis i possibilitat de sortir de la sessió.
```

This is a Spring Boot application that implements an pure API REST for a D.Tellez application. It works with the the [TODOandroid](https://github.com/neich/TODOAndroid) Android application as client.

The main three tiers used are:

* REST tier (```@Controller```)
* Business tier (```@Service```)
* Persistence tier (JPA)

Other Java APIs used:

* Bean validation
* Exception Mappers
* Dependency injection

It uses [Spring Boot 2](https://spring.io/projects/spring-boot) to produce an jar file than can be executed standalone without an application server:

```
gradle bootRun
```

## Image uploading

In order to store files, the app uses a private object storage server: [minio      ](https://www.minio.io/). The minio configuration has to be passed to the application as properties via command line:

```
gradlew bootJar
java -Dswarm.project.minio.ulr=http://your-minio-host.com -Dswarm.project.minio.access-key=your-access-key -Dswarm.project.minio.secret-key=your-secret-key -Dswarm.project.minio.bucket=your-bucket -jar ./build/libs/todo-spring-0.1.0.jar
```



## Heroku

The app is ready to deploy into [Heroku](http://heroku.com) with the ```web``` profile. There is a file ```Procfile``` with the command line arguments to start the jar.
#### Collaborations are welcome!
