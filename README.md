# Workoutmanager

Das Workoutmanager Projekt ist ein Schulungsprojekt, um die Grundlagen der Java-Programmierung zu erlernen und von Grund auf Themen wie Kapselung, Kohäsion, Paketstrukturen und die ersten OO-Patterns zu erarbeiten.

## Lernziele

In diesem Projekt werden praxisnah folgende Software-Engineering-Konzepte vermittelt:

*   **Kapselung (Encapsulation):** Schutz des internen Zustands von Objekten durch private Felder und öffentliche Zugriffsmethoden.
*   **Kohäsion (Cohesion):** Erstellung von Klassen und Methoden, die eine klar definierte Aufgabe haben (z.B. Trennung von Datenmodellen und Geschäftslogik).
*   **Paketstrukturen:** Sinnvolle Organisation von Code in Pakete (`model`, `service`, `util`, `enums`), um die Wartbarkeit zu erhöhen.
*   **Objektorientierte Patterns:**
    *   **Builder Pattern:** Zur flexiblen und lesbaren Erzeugung komplexer Objekte (siehe `WorkoutBuilder`).
    *   **Service Layer:** Trennung von Datenhaltung und Logik.

## Projektübersicht

Der Workoutmanager ermöglicht die Verwaltung von Sport-Workouts (z.B. CrossFit-Style). Er unterstützt verschiedene Skalierungsstufen (Scaling Options), Übungen (Exercises) und Bewegungsabläufe (Movements).

### Kernkomponenten

*   **Models (`at.sds.wm.model`):**
    *   `Workout`: Die zentrale Entität, die ein Training repräsentiert.
    *   `Exercise`: Eine konkrete Übung mit Wiederholungen, Gewicht oder Distanz.
    *   `Movement`: Die Definition einer Bewegung (z.B. "Pull-up").
    *   `ScalingOption`: Ermöglicht verschiedene Schwierigkeitsgrade für dasselbe Workout (RX, Scaled, etc.).
*   **Services (`at.sds.wm.service`):**
    *   `WorkoutService`: Logik zum Suchen und Zählen von Übungen.
    *   `WorkoutStatisticsService`: Berechnungen und Sortierungen basierend auf Workout-Daten.
*   **Builder (`at.sds.wm.model.builder`):**
    *   Fluent API zur einfachen Erstellung von Testdaten und Workouts.

## Technische Details

*   **Java Version:** 19
*   **Build-Tool:** Gradle
*   **Testing:** JUnit 5
*   **Dokumentation:** English Javadoc für öffentliche Methoden.

## Installation & Ausführung

### Voraussetzungen
*   JDK 19 oder höher installiert.

### Tests ausführen
Um die Funktionsweise des Codes zu verifizieren, können die Unit-Tests gestartet werden:
```bash
./gradlew test
```

## Beispiel: Workout Erstellung
Dank des Builder-Patterns lassen sich komplexe Workouts sehr lesbar definieren:

```java
Workout murph = new WorkoutBuilder("Murph")
        .hero()
        .rounds(1)
        .rx(rx -> rx
                .distance(run, 1600)
                .reps(pull, 100)
                .reps(push, 200)
                .reps(squat, 300)
                .distance(run, 1600)
        )
        .build();
```
