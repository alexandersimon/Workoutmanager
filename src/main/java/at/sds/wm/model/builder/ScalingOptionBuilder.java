package at.sds.wm.model.builder;

import at.sds.wm.model.Exercise;
import at.sds.wm.model.Movement;
import at.sds.wm.model.ScalingOption;
import at.sds.wm.model.enums.ScalingLevel;

import java.time.Duration;
import java.util.List;

public class ScalingOptionBuilder {

    private final ScalingOption scalingOption;

    public ScalingOptionBuilder(ScalingLevel level) {
        this.scalingOption = new ScalingOption();
        this.scalingOption.setLevel(level);
    }

    public ScalingOptionBuilder reps(Movement movement, int reps) {
        Exercise exercise = new Exercise();
        exercise.setMovement(movement);
        exercise.setReps(List.of(reps));

        scalingOption.addExercise(exercise);
        return this;
    }

    public ScalingOptionBuilder reps(Movement movement, Integer... reps) {
        Exercise exercise = new Exercise();
        exercise.setMovement(movement);
        exercise.setReps(List.of(reps));

        scalingOption.addExercise(exercise);
        return this;
    }

    public ScalingOptionBuilder distance(Movement movement, double distance) {
        Exercise exercise = new Exercise();
        exercise.setMovement(movement);
        exercise.setDistance(distance);

        scalingOption.addExercise(exercise);
        return this;
    }

    public ScalingOptionBuilder weightMale(double weight) {
        if (!scalingOption.getExercises().isEmpty()) {
            scalingOption.getExercises().get(scalingOption.getExercises().size() - 1).setWeightMale(weight);
        }
        return this;
    }

    public ScalingOptionBuilder weightFemale(double weight) {
        if (!scalingOption.getExercises().isEmpty()) {
            scalingOption.getExercises().get(scalingOption.getExercises().size() - 1).setWeightFemale(weight);
        }
        return this;
    }

    public ScalingOptionBuilder weightUnit(at.sds.wm.model.enums.WeightUnit unit) {
        if (!scalingOption.getExercises().isEmpty()) {
            scalingOption.getExercises().get(scalingOption.getExercises().size() - 1).setWeightUnit(unit);
        }
        return this;
    }

    public ScalingOptionBuilder notes(String notes) {
        if (!scalingOption.getExercises().isEmpty()) {
            scalingOption.getExercises().get(scalingOption.getExercises().size() - 1).setNotes(notes);
        }
        return this;
    }

    public ScalingOptionBuilder basedOn(ScalingOption base) {
        scalingOption.setBasedOn(base);
        return this;
    }

    public ScalingOptionBuilder rounds(int rounds) {
        scalingOption.setRounds(rounds);
        return this;
    }

    public ScalingOptionBuilder restBetweenRounds(Duration duration) {
        scalingOption.setRestBetweenRounds(duration);
        return this;
    }

    public ScalingOptionBuilder exercise(Exercise exercise) {
        scalingOption.addExercise(exercise);
        return this;
    }

    public ScalingOption build() {
        return scalingOption;
    }
}