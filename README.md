# GUI Minigames - FabricMC Minecraft Plugin

> The repository is written and documented in german

## Beschreibung

Das Plugin fügt Minispiele in Minecraft hinzu, die über eine grafische Benutzeroberfläche (GUI) gesteuert werden können.

Darüber hinaus besteht eine Anbindung an eine Datenbank, mit der Statistiken zwischen zwei Spielern gespeichert werden können.

## Lokale Entwicklung

### Voraussetzungen

- Docker

### Schritte zum Starten des Projekts

**Führe den Task `dev/buildAndRunServer` aus**.

Mit dem Befehl `dev/buildAndRunServer` wird das gesamte System mithilfe Docker automatisch gebaut, konfiguriert und gestartet. Er vereinfacht die Entwicklung enorm, da alle nötigen Schritte – vom Erstellen des Builds über das Starten des Minecraft-Servers mit dem neuen Plugin – in einem einzigen Task kombiniert werden.

In der Datei `resource/config.yml` ist bereits eine Standardkonfiguration enthalten, die für die Kommunikation mit der von `docker-compose` bereitgestellten Datenbank eingerichtet ist.