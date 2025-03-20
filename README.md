# GUI Minigames - FabricMC Minecraft Plugin

> The repository is written and documented in german

## Beschreibung

Das Plugin fügt Minispiele in Minecraft hinzu, die über eine grafische Benutzeroberfläche (GUI) gesteuert werden können.

Darüber hinaus besteht eine Anbindung an eine Datenbank, mit der Statistiken zwischen zwei Spielern gespeichert werden können.

## Lokale Entwicklung

### Voraussetzungen

- Docker

### Schritte zum Starten des Projekts

1. Die `docker-compose.yml` starten.
2. Das `init.sql`-Skript in der Datenbank ausführen, um die Tabellen zu erstellen.
3. Zum Erstellen der `.jar`-Datei den Task `shadow/shadowJar` ausführen.
4. Den `papermc-server` aus dem `docker-compose`-Setup neu starten.
5. Die Plugin-Dateien werden automatisch in das Plugin-Verzeichnis des Docker-Containers kopiert und der Server ist ready.

In der Datei `resource/config.yml` ist bereits eine Standardkonfiguration enthalten, die für die Kommunikation mit der von `docker-compose` bereitgestellten Datenbank eingerichtet ist.