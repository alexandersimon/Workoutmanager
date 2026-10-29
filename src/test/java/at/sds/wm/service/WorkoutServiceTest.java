package at.sds.wm.service;

import at.sds.wm.model.Exercise;
import at.sds.wm.model.Movement;
import at.sds.wm.model.Workout;
import at.sds.wm.model.builder.WorkoutBuilder;
import at.sds.wm.util.ExerciseFormatter;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class WorkoutServiceTest {

    @Test
    public void testFindExerercisebyMovementName() {

        WorkoutService workoutService = new WorkoutService(null, null, new ExerciseFormatter());

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


        Optional<Exercise> exercise = workoutService.findExerciseByMovementName(murph, "Air Squats");

        assertTrue(exercise.isPresent());
        assertEquals(300, exercise.get().getReps().get(0));
    }
    @Test
    public void testCountExercises () {

        WorkoutService workoutService = new WorkoutService(null, null, new ExerciseFormatter());

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

        assertEquals(5, workoutService.countExercises(murph));
    }

    @Test
    public void testFindAndFormatExercise() {

        WorkoutService workoutService = new WorkoutService(null, null, new ExerciseFormatter());

        Movement pull = new Movement(2L, "Pull-ups", null, "Pull-ups", false);
        Movement push = new Movement(3L, "Push-ups", null, "Push-ups", false);

        Workout test = new WorkoutBuilder("Test")
                .hero()
                .rounds(3)
                .rx(rx -> rx
                        .reps(pull, 100)
                        .reps(push, 200)
                )
                .build();

        Optional<String> formatedExercise = workoutService.findAndFormatExercise(test, "Push-ups");

        assertTrue(formatedExercise.isPresent());
        assertEquals(new String("Push-ups - 200 reps"), formatedExercise.get());
    }

    @Test
    public void testFindAndFormatExerciseNotFound() {

        WorkoutService workoutService = new WorkoutService(null, null, new ExerciseFormatter());

        Movement run = new Movement(1L, "Run", null, "Run", false);

        Workout test = new WorkoutBuilder("Test")
                .hero()
                .rounds(5)
                .rx(rx -> rx
                        .reps(run, 1000)
                )
                .build();

        Optional<String> formatedExercise = workoutService.findAndFormatExercise(test, "xxxxx");

        assertFalse(formatedExercise.isPresent());
        //
        //assertEquals(new String("Unknown exercise"), formatedExercise.get());
    }

}
