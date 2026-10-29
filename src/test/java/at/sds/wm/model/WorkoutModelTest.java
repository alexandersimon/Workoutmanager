package at.sds.wm.model;

import at.sds.wm.model.builder.ScalingOptionBuilder;
import at.sds.wm.model.builder.WorkoutBuilder;
import at.sds.wm.model.enums.MovementCategory;
import at.sds.wm.model.enums.ScalingLevel;
import at.sds.wm.model.enums.WorkoutType;
import at.sds.wm.model.enums.WeightUnit;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WorkoutModelTest {

    @Test
    public void testWOD260609RX() {
        Movement pu = new Movement(1L, "Pull-up", MovementCategory.GYMNASTICS, "Pull-ups", false);
        Movement th = new Movement(1L, "Thruster", MovementCategory.WEIGHTLIFTING, "Thrusters", true);

        Workout wod260609 = new WorkoutBuilder("WOD 260609")
                .type(WorkoutType.FOR_TIME)
                .timecap(Duration.ofMinutes(18))
                .description("Build to a heavy single thruster")
                .rx(rx -> rx
                        .reps(pu, 9, 15, 21)
                        .reps(th, 9, 15, 21)
                        .weightUnit(WeightUnit.LBS)
                        .weightMale(95.00)
                        .weightFemale(65.00)
                        .notes("Take the bar from the floor.")
                        .rounds(1)
                )
                .build();

        ScalingOption rxt = wod260609.getRxOption();
        assertEquals(90, rxt.getTotalRepetitions());
        assertFalse(rxt.isHighVolume());
    }

    @Test
    public void testWOD260609BasedOnRX() {
        Movement pu = new Movement(1L, "Pull-up", MovementCategory.GYMNASTICS, "Pull-ups", false);
        Movement th = new Movement(1L, "Thruster", MovementCategory.WEIGHTLIFTING, "Thrusters", true);

        Workout wod260609 = new WorkoutBuilder("WOD 260609")
                .type(WorkoutType.FOR_TIME)
                .timecap(Duration.ofMinutes(18))
                .description("Build to a heavy single thruster")
                .rx(rx -> rx
                        .reps(pu, 9, 15, 21)
                        .reps(th, 9, 15, 21)
                        .weightUnit(WeightUnit.LBS)
                        .weightMale(95.00)
                        .weightFemale(65.00)
                        .notes("Take the bar from the floor.")
                        .rounds(1)
                )
                .scalingBasedOn(ScalingLevel.INTERMEDIATE, ScalingLevel.RX, intermediate -> {
                    // basedOn is handled by the builder method
                })
                .build();

        ScalingOption intermediateTest = wod260609.getIntermediateOption();
        assertEquals(90, intermediateTest.getTotalRepetitions());
        assertFalse(intermediateTest.isHighVolume());
    }

    @Test
    public void testWOD260609Intermediate() {
        Movement pu = new Movement(1L, "Pull-up", MovementCategory.GYMNASTICS, "Pull-ups", false);
        Movement th = new Movement(1L, "Thruster", MovementCategory.WEIGHTLIFTING, "Thrusters", true);

        Workout wod260609 = new WorkoutBuilder("WOD 260609")
                .type(WorkoutType.FOR_TIME)
                .timecap(Duration.ofMinutes(18))
                .description("Build to a heavy single thruster")
                .intermediate(in -> in
                        .reps(pu, 9, 12, 15)
                        .reps(th, 9, 15, 21)
                        .weightUnit(WeightUnit.LBS)
                        .weightMale(75.00)
                        .weightFemale(55.00)
                        .notes("Take the bar from the floor.")
                        .rounds(1)
                )
                .build();

        ScalingOption rxt = wod260609.getIntermediateOption();
        assertEquals(81, rxt.getTotalRepetitions());
        assertFalse(rxt.isHighVolume());
    }

    @Test
    public void testRunIntervalWorkout() {
        Movement run = new Movement(1L, "Run", null, "Running", false);

        Workout runWorkout = new WorkoutBuilder("6 Rounds of 400m Run")
                .type(WorkoutType.INTERVALS)
                .rounds(6)
                .restBetweenRounds(Duration.ofSeconds(90))
                .rx(rx -> rx
                        .distance(run, 400.0)
                )
                .beginner(beg -> beg
                        .rounds(4)
                        .restBetweenRounds(Duration.ofMinutes(2))
                        .distance(run, 200.0)
                )
                .build();

        assertEquals(6, runWorkout.getRounds());
        assertEquals(400.0, runWorkout.getRxOption().getExercises().get(0).getDistance());
        assertEquals(4, runWorkout.getBeginnerOption().getRounds());
        assertEquals(200.0, runWorkout.getBeginnerOption().getExercises().get(0).getDistance());
    }

    @Test
    public void testFranWorkout() {
        Movement thruster = new Movement(1L, "Thruster", null, "Thrusters", true);
        Movement pullup = new Movement(2L, "Pull-up", null, "Pull-ups", false);
        Movement ringRow = new Movement(3L, "Ring Row", null, "Ring Rows", false);

        Workout fran = new WorkoutBuilder("Fran")
                .type(WorkoutType.GIRL)
                .rounds(1)
                .rx(rx -> rx
                        .reps(thruster, 21, 15, 9)
                        .weightMale(95.0)
                        .weightFemale(65.0)
                        .weightUnit(WeightUnit.LBS)
                        .reps(pullup, 21, 15, 9)
                )
                .beginner(beg -> beg
                        .reps(thruster, 21, 15, 9)
                        .weightMale(45.0)
                        .weightFemale(35.0)
                        .weightUnit(WeightUnit.LBS)
                        .reps(ringRow, 21, 15, 9)
                )
                .build();

        assertEquals(Arrays.asList(21, 15, 9), fran.getRxOption().getExercises().get(0).getReps());
        assertEquals(95.0, fran.getRxOption().getExercises().get(0).getWeightMale());
        assertEquals("Ring Row", fran.getBeginnerOption().getExercises().get(1).getMovement().getName());
    }

    @Test
    public void testMurphWorkout() {
        Movement run = new Movement(1L, "Run", null, "Run", false);
        Movement pull = new Movement(2L, "Pull-ups", null, "Pull-ups", false);
        Movement push = new Movement(3L, "Push-ups", null, "Push-ups", false);
        Movement squat = new Movement(4L, "Air Squats", null, "Air Squats", false);

        Workout murph = new WorkoutBuilder("Murph")
                .hero()
                .rounds(1)
                .rx(rx -> rx
                        .distance(run, 1600.00)
                        .reps(pull, 100)
                        .reps(push, 200)
                        .reps(squat, 300)
                        .distance(run, 1600.00)
                )
                .build();

        assertEquals(632, murph.getRxOption().getTotalRepetitions());
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

    @Test
    public void testTotalRepetitionsWithScaling() {
        Movement thruster = new Movement(1L, "Thruster", null, "Thrusters", true);

        Workout workout = new WorkoutBuilder("Test")
                .rounds(1)
                .rx(rx -> rx
                        .reps(thruster, 21, 15, 9)
                )
                .beginner(beg -> beg
                        .rounds(1)
                        .reps(thruster, 15, 12, 9)
                )
                .build();

        // Rx repetitions
        assertEquals(45, workout.getTotalRepetitions());

        // Beginner repetitions
        assertEquals(36, workout.getTotalRepetitions(workout.getBeginnerOption()));
    }

    @Test
    public void testTotalRepetitionsWithScalingFallback() {
        Movement row = new Movement(1L, "Row", null, "Rowing", false);
        Movement bike = new Movement(2L, "Bike", null, "Biking", false);

        Workout workout = new WorkoutBuilder("Test Fallback")
                .rounds(5)
                .rx(rx -> rx
                        .distance(row, 500.0)
                )
                .scalingBasedOn(ScalingLevel.INTERMEDIATE, ScalingLevel.RX, intermediate -> {
                    intermediate.rounds(3);
                })
                .beginner(beg -> beg
                        .distance(bike, 1000.0)
                )
                .build();

        // Rx: 5 rounds * 5 reps = 25 reps
        assertEquals(25, workout.getTotalRepetitions());

        assertEquals(15, workout.getTotalRepetitions(workout.getIntermediateOption()));

        assertEquals(50, workout.getTotalRepetitions(workout.getBeginnerOption()));
    }

    @Test
    public void testAddScalingOptionReplacement() {
        Workout workout = new WorkoutBuilder("Test Replace")
                .rx(rx -> rx.rounds(5))
                .build();

        assertEquals(5, workout.getRxOption().getRounds());

        // Sollte die bestehende RX Option ersetzen
        workout.addScalingOptions(new WorkoutBuilder("Other").rx(rx -> rx.rounds(10)).build().getRxOption());
        assertEquals(10, workout.getRxOption().getRounds());
    }

    @Test
    public void testIsHighVolume() {
        ScalingOption lowVol = new WorkoutBuilder("low")
                .rx(rx -> rx
                        .rounds(1)
                        .reps(new Movement(1L, "M1", null, "M1", false), 50, 50)
                ).build().getRxOption();

        assertFalse(lowVol.isHighVolume(), "Should not be high volume (100 reps)");

        ScalingOption highVol = new WorkoutBuilder("high")
                .rx(rx -> rx
                        .rounds(1)
                        .reps(new Movement(1L, "M1", null, "M1", false), 60, 61)
                ).build().getRxOption();

        assertTrue(highVol.isHighVolume(), "Should be high volume (121 reps)");

        // Test with Distance (Row 13000m = 130 reps)
        Movement row = new Movement(1L, "Row", null, "Rowing", false);
        ScalingOption highVolDist = new WorkoutBuilder("highDist")
                .rx(rx -> rx
                        .rounds(1)
                        .distance(row, 13000.0)
                ).build().getRxOption();

        assertTrue(highVolDist.isHighVolume(), "Should be high volume (130 reps from row)");
    }

    @Test
    public void testInvalidExerciseReps() {
        assertThrows(IllegalArgumentException.class, () -> {
            new WorkoutBuilder("invalid").rx(rx -> rx.reps(new Movement(), 0));
        }, "Reps must be > 0");

        assertThrows(IllegalArgumentException.class, () -> {
            new WorkoutBuilder("invalid").rx(rx -> rx.reps(new Movement(), 10, -5, 20));
        }, "All reps must be > 0");
    }

    @Test
    public void testInvalidExerciseDistance() {
        assertThrows(IllegalArgumentException.class, () -> {
            new WorkoutBuilder("invalid").rx(rx -> rx.distance(new Movement(), 0.0));
        }, "Distance must be > 0");

        assertThrows(IllegalArgumentException.class, () -> {
            new WorkoutBuilder("invalid").rx(rx -> rx.distance(new Movement(), -100.0));
        }, "Distance must be > 0");
    }

    @Test
    public void testContainsMovement() {
        Movement pullup = new Movement(1L, "Pull-up", null, "Pull-ups", false);
        Movement thruster = new Movement(2L, "Thruster", null, "Thrusters", true);

        Workout workout = new WorkoutBuilder("Test")
                .rx(rx -> rx.reps(pullup, 10))
                .scalingBasedOn(ScalingLevel.INTERMEDIATE, ScalingLevel.RX, intermediate -> {
                })
                .build();

        ScalingOption rx = workout.getRxOption();
        assertTrue(rx.containsMovement("Pull-up"));
        assertTrue(rx.containsMovement("pull-up")); // Case insensitive
        assertFalse(rx.containsMovement("Thruster"));
        assertFalse(rx.containsMovement(null));

        ScalingOption intermediate = workout.getIntermediateOption();
        assertTrue(intermediate.containsMovement("Pull-up"), "Should find movement from basedOn option");

        // Test with overridden exercises
        workout.addScalingOptions(new ScalingOptionBuilder(ScalingLevel.INTERMEDIATE)
                .reps(thruster, 10)
                .build());

        intermediate = workout.getIntermediateOption();
        assertTrue(intermediate.containsMovement("Thruster"));
        assertFalse(intermediate.containsMovement("Pull-up"), "Should NOT find movement from basedOn if exercises are overridden");
    }
}

