DAS-SYSTEM-APP [(DAS-SYSTEM-SERVER)](https://github.com/marcelh89/DAS-SYSTEM-SERVER)
==========


DAS-SYSTEM ist eine Client(Android App) / Server (Tomcat/Java) Anwendung die im Rahmen eines Studienprojektes entstanden ist. Sie beinhaltet vier Grundfunktionen:

  - Kommunikation mit Nutzern
  - Lokalisation/Navigation zu einem Nutzer
  - Vorlesungsinformationen zum Raum XY erfahren
  - Anwesenheit von Nutzern (durch Dozent) nachvollziehen


Version
----

0.1

Tech
----
* verwendet Tomcat Server v7
* Java 1.7
* Websockets
* Jersey Web Service
* Maven Build Tool

Installation
--------------
import als Eclipse Projekt --> RUN as Android Application

bekannte Fehler
---------------
Android Abhängigkeiten verursachen Problem beim Ausführen des dex Codes
* Rechtsklick auf Projekt --> Settings --> Abhängigkeiten und alles bis auf Android 4 rauslöschen --> Run
