package at.sds.wm.model.builder;

import at.sds.wm.model.*;
import at.sds.wm.model.enums.*;

import java.time.Duration;
import java.util.function.Consumer;

public class WorkoutBuilder {

    private final Workout workout;

    public WorkoutBuilder(String name) {
        this.workout = new Workout();
        this.workout.setName(name);
    }

    public WorkoutBuilder id (Long id) {
        workout.setId(id);
        return this;
    }

    public WorkoutBuilder type(WorkoutType type) {
        workout.setType(type);
        return this;
    }

    public WorkoutBuilder hero() {
        workout.setType(WorkoutType.HERO);
        return this;
    }

    public WorkoutBuilder rounds(int rounds) {
        workout.setRounds(rounds);
        return this;
    }

    public WorkoutBuilder description(String description) {
        workout.setDescription(description);
        return this;
    }

    public WorkoutBuilder timecap(Duration duration) {
        workout.setTimecap(duration);
        return this;
    }

    public WorkoutBuilder restBetweenRounds(Duration duration) {
        workout.setRestBetweenRounds(duration);
        return this;
    }

    public WorkoutBuilder rx(Consumer<ScalingOptionBuilder> consumer) {
        return scaling(ScalingLevel.RX, consumer);
    }

    public WorkoutBuilder intermediate(Consumer<ScalingOptionBuilder> consumer) {
        return scaling(ScalingLevel.INTERMEDIATE, consumer);
    }

    public WorkoutBuilder beginner(Consumer<ScalingOptionBuilder> consumer) {
        return scaling(ScalingLevel.BEGINNER, consumer);
    }

    public WorkoutBuilder scaling(
            ScalingLevel level,
            Consumer<ScalingOptionBuilder> consumer
    ) {
        ScalingOptionBuilder builder = new ScalingOptionBuilder(level);
        consumer.accept(builder);
        workout.addScalingOptions(builder.build());
        return this;
    }

    public WorkoutBuilder scalingBasedOn(
            ScalingLevel level,
            ScalingLevel baseLevel,
            Consumer<ScalingOptionBuilder> consumer
    ) {
        ScalingOption base = workout.getScalingOption(baseLevel);
        ScalingOptionBuilder builder = new ScalingOptionBuilder(level);
        builder.basedOn(base);
        consumer.accept(builder);
        workout.addScalingOptions(builder.build());
        return this;
    }

    public Workout build() {
        return workout;
    }

}