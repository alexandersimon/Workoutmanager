package at.sds.wm.model;

import at.sds.wm.model.enums.ScalingLevel;
import jakarta.persistence.*;

import java.time.Duration;
import java.util.List;

@Entity
@Table(name="ScalingOption")
public class ScalingOption {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private ScalingLevel level;

    @ManyToOne
    @JoinColumn(name="basedOnId")
    private ScalingOption basedOn;

    //@OneToMany
    /*
    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )*/
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @OrderColumn(name = "exercise_position")
    private List<Exercise> exercises;
    private Integer rounds;
    private Duration restBetweenRounds;

    /**
     * Gets the scaling level of this option.
     *
     * @return the scaling level
     */
    public ScalingLevel getLevel() {
        return level;
    }

    /**
     * Sets the scaling level of this option.
     *
     * @param level the scaling level to set
     */
    public void setLevel(ScalingLevel level) {
        this.level = level;
    }

    /**
     * Gets the scaling option this one is based on.
     *
     * @return the base scaling option
     */
    public ScalingOption getBasedOn() {
        return basedOn;
    }

    /**
     * Sets the scaling option this one is based on.
     *
     * @param basedOn the base scaling option to set
     */
    public void setBasedOn(ScalingOption basedOn) {
        this.basedOn = basedOn;
    }

    /**
     * Gets the list of exercises for this scaling option.
     *
     * @return the list of exercises
     */
    public List<Exercise> getExercises() {
        return exercises;
    }

    /**
     * Adds an exercise to this scaling option.
     *
     * @param exercise the exercise to add
     */
    public void addExercise(Exercise exercise) {
        if (this.exercises == null) {
            this.exercises = new java.util.ArrayList<>();
        }
        this.exercises.add(exercise);
    }

    /**
     * Gets the number of rounds for this scaling option.
     *
     * @return the number of rounds
     */
    public Integer getRounds() {
        return rounds;
    }

    /**
     * Sets the number of rounds for this scaling option.
     *
     * @param rounds the number of rounds to set
     */
    public void setRounds(Integer rounds) {
        this.rounds = rounds;
    }

    /**
     * Gets the duration of rest between rounds for this scaling option.
     *
     * @return the rest duration
     */
    public Duration getRestBetweenRounds() {
        return restBetweenRounds;
    }

    /**
     * Sets the duration of rest between rounds for this scaling option.
     *
     * @param restBetweenRounds the rest duration to set
     */
    public void setRestBetweenRounds(Duration restBetweenRounds) {
        this.restBetweenRounds = restBetweenRounds;
    }

    /**
     * Calculates the total number of repetitions for this scaling option with 0 rounds.
     *
     * @return the total number of repetitions
     */
    public int getTotalRepetitions() {
        return getTotalRepetitions(0);
    }

    /**
     * Calculates the total number of repetitions for this scaling option.
     * If this option has no exercises, it falls back to the option it is based on.
     *
     * @param defaultRounds the number of rounds to use if not specified in this option
     * @return the total number of repetitions
     */
    public int getTotalRepetitions(int defaultRounds) {

        List<Exercise> targetExercises = this.exercises;

        if (targetExercises == null) {
            // Fallback to RX if this scaling option doesn't have exercises
            ScalingOption rx = basedOn;
            if (rx != null) {
                targetExercises = rx.getExercises();
            }
        }

        int targetReps = (this.rounds != null) ? this.rounds : defaultRounds;

        return calculateRepetitions(targetExercises, targetReps);
    }

    /**
     * Checks if the total number of repetitions is high (greater than 120).
     *
     * @return true if repetitions > 120, false otherwise
     */
    public boolean isHighVolume() {
        return getTotalRepetitions(0) > 120;
    }

    /**
     * Checks if this scaling option contains an exercise with the specified movement name.
     * Case-insensitive.
     *
     * @param name the movement name to look for
     * @return true if found, false otherwise
     */
    public boolean containsMovement(String name) {
        if (name == null) {
            return false;
        }

        List<Exercise> targetExercises = this.exercises;
        if (targetExercises == null && basedOn != null) {
            targetExercises = basedOn.getExercises();
        }

        if (targetExercises == null) {
            return false;
        }

        return targetExercises.stream()
                .filter(e -> e.getMovement() != null && e.getMovement().getName() != null)
                .anyMatch(e -> e.getMovement().getName().equalsIgnoreCase(name));
    }

    private int calculateRepetitions(List<Exercise> exercisesToCount, int roundsToCount) {
        if (exercisesToCount == null || exercisesToCount.isEmpty()) {
            return 0;
        }

        int totalReps = 0;
        for (Exercise exercise : exercisesToCount) {
            int exerciseReps = 0;
            if (exercise.getReps() != null && !exercise.getReps().isEmpty()) {
                for (Integer rep : exercise.getReps()) {
                    exerciseReps += rep;
                }
            } else if (exercise.getDistance() != null && exercise.getMovement() != null && exercise.getMovement().getName() != null) {
                String movementName = exercise.getMovement().getName().toLowerCase();
                if (movementName.contains("run") || movementName.contains("bike") || movementName.contains("row") || movementName.contains("ski")) {
                    exerciseReps += (int) (exercise.getDistance() / 100);
                }
            }
            totalReps += exerciseReps;
        }

        return totalReps * Math.max(1, roundsToCount);
    }
}
