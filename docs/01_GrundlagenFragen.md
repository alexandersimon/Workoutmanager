Hier sind Verständnisfragen zum `Workoutmanager`-Code, unterteilt in verschiedene Themenbereiche:

### 1. Grundlagen: Klassen und Objekte
*   **Frage:** In der Klasse `Movement.java` gibt es die Felder `id`, `name` und `category`. Warum sind diese `private` und wie greifen andere Klassen (wie `WorkoutModelMurphTest`) darauf zu?
*   **Antwort:** Die Felder sind private, damit kein unkontrolliertet Zugriff von außerhalb erfolgt. Der Zugriff erfolgt mittels Getter und Setter.
*
*   **Frage:** Was ist der Unterschied zwischen dem "Default Constructor" (ohne Parameter) und dem "Parameterized Constructor" in der Klasse `Exercise.java`? Wann würdest du welchen benutzen?
*   **Antwort:** Der Default-Constructor erzeugt eine leere Klasse, der Parameterized-Contrucor befüllt die Attribute.
*   **           Wenn die Werte für die Attribute bei der Erzeugung der Klasse schon feststehen, würde ich den Parameterized-Contstructor verwenden,
*   **           sonst den Default-Constructor und dann später Setter zum Sezen der Werte verwenden.

### 2. Datenstrukturen und Listen
*   **Frage:** In der Klasse `Workout.java` gibt es das Feld `List<ScalingOption> scalingOptions`. Was bedeutet das `<ScalingOption>` in den spitzen Klammern (Generics) und warum benutzen wir hier eine `List` statt eines einfachen Arrays?
*   **Antwort:** ScalingOption ist der Typ des Listenelements.
*   ** 1. Bei einem Array muss die Größe bei der Erstellung gestgelegt werden, eine Liste kann erweitert werden.
*   ** 2. Arrays können nur primitive Datentypen enthalten, während Listen aus Objekten betsehen.

*   **Frage:** Schau dir die Methode `addScalingOptions` in `Workout.java` an. Was passiert dort, wenn man eine Option hinzufügt, deren Schwierigkeitsgrad (Level) bereits in der Liste existiert?
*   **Antwort:** Wenn schon eine ScalingOption mit diesem Level existiert, wird diese entfernt bevor die neue ScallingOption hinzugefügt wird.

### 3. Aufzählungstypen (Enums)
*   **Frage:** Das Projekt nutzt Enums wie `ScalingLevel` oder `WorkoutType`. Warum ist es besser, Enums zu verwenden, anstatt einfach Strings (z.B. `"RX"`, `"SCALED"`) zu speichern?
*   **Antwort: Durch Enums kann der Wertebereich vorgegeben werden, ungültige Werte können verhindert werden.
*   **         Die Lesbarkeit des Sourcecode ist besser.
*   **         ENUM-Klassen können auch Methoden zur Verfügung stellen.

### 4. Logik und Services
*   **Frage:** Es gibt `Workout.java` (ein Model) und `WorkoutService.java` (einen Service). Warum schreiben wir die Methode `findExerciseByMovementName` in den Service und nicht direkt in die `Workout`-Klasse?
*   **Antwort: Einerseit ändert diese Methode keine Daten der Klasse Workout und sie verarbeitet nicht nur die Daten aus Workout, sondern auch aus ScalingOption und Exercise.
*
*   **Frage:** In `WorkoutService.java` wird `Optional<Exercise>` zurückgegeben. Was ist der Vorteil von `Optional` gegenüber der Rückgabe von `null`, wenn keine Übung gefunden wurde?
* *   **Antwort: Der Aufrufer muss keine NULL-Werte abfragen. Aus der Methodendeklaration ist ersichtlich, dass kein Ergebnis gefunden werden muss.

### 5. Fortgeschrittene Konzepte (Streams & Builder)
*   **Frage:** In `WorkoutStatisticsService.java` wird die Methode `sortByExerciseCountAscending` verwendet. Kannst du grob beschreiben, was `.stream().sorted(...).toList()` macht, ohne jedes Detail der Syntax zu kennen?
* *   **Antwort: Ein 'Stream' aus Workouts wird sortiert und in eine Liste überführt.
*
*   **Frage:** Im Test `WorkoutModelMurphTest.java` wird ein `WorkoutBuilder` verwendet. Welchen Vorteil bietet dieser "Builder" gegenüber dem manuellen Setzen aller Werte mit `set`-Methoden?
*   **Antwort: Der Sourcecode wird übersichtlicher und einfacher erweiterbar. Objekte (hier die Movements) können wiederverwendet werden.

---

### Tipps für dich als Einsteiger:
1.  **Kapselung:** Das Prinzip, Daten `private` zu machen und über `public` Methoden (Getter/Setter) darauf zuzugreifen, nennt man *Encapsulation*.
2.  **Null-Safety:** Achte darauf, wie oft im Code geprüft wird, ob eine Variable `null` ist (z.B. `if (workout == null)`). Das verhindert, dass das Programm abstürzt.
3.  **Vererbung vs. Komposition:** Ein `Workout` *hat* eine Liste von `ScalingOptions`. Das nennt man Komposition.

Möchtest du zu einer der Fragen die Lösung wissen oder soll ich dir ein konkretes Code-Beispiel erklären?
