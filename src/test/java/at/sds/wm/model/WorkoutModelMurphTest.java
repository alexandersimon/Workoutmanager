package at.sds.wm.model;

import at.sds.wm.model.builder.WorkoutBuilder;
import at.sds.wm.model.enums.ScalingLevel;
import at.sds.wm.model.enums.WorkoutType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorkoutModelMurphTest {

    @Test
    public void testMurphWorkout() {
        Workout murph = new Workout();
        murph.setName("Murph");
        murph.setType(WorkoutType.HERO);
        murph.setRounds(1);

        Movement run = new Movement(1L, "Run", null, "Run", false);
        Movement pull = new Movement(2L, "Pull-ups", null, "Pull-ups", false);
        Movement push = new Movement(3L, "Push-ups", null, "Push-ups", false);
        Movement squat = new Movement(4L, "Air Squats", null, "Air Squats", false);

        Exercise runRx = new Exercise();
        runRx.setMovement(run);
        runRx.setDistance(1600.00);

        Exercise pullRx = new Exercise();
        pullRx.setMovement(pull);
        pullRx.setReps(List.of(100));

        Exercise pushRx = new Exercise();
        pushRx.setMovement(push);
        pushRx.setReps(List.of(200));

        Exercise squatRx = new Exercise();
        squatRx.setMovement(squat);
        squatRx.setReps(List.of(300));

        ScalingOption rx = new ScalingOption();
        rx.setLevel(ScalingLevel.RX);
        rx.addExercise(runRx);
        rx.addExercise(pullRx);
        rx.addExercise(pushRx);
        rx.addExercise(squatRx);
        rx.addExercise(runRx);

        murph.addScalingOptions(rx);

        assertEquals(632, rx.getTotalRepetitions());
    }

    @Test
    public void testMurphWorkoutBuilder() {
        Movement run = new Movement(1L, "Run", null, "Run", false);
        Movement pull = new Movement(2L, "Pull-ups", null, "Pull-ups", false);
        Movement push = new Movement(3L, "Push-ups", null, "Push-ups", false);
        Movement squat = new Movement(4L, "Air Squats", null, "Air Squats", false);

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

        assertEquals(632, murph.getTotalRepetitions());
    }
}
