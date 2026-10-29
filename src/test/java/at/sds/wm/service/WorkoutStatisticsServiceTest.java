package at.sds.wm.service;

import at.sds.wm.model.Movement;
import at.sds.wm.model.Workout;
import at.sds.wm.model.builder.WorkoutBuilder;
import at.sds.wm.model.enums.WorkoutType;
import at.sds.wm.util.ExerciseFormatter;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorkoutStatisticsServiceTest {

    @Test
    public void testSortByExerciseCountAscending() {
        WorkoutService workoutService = new WorkoutService(null, null, new ExerciseFormatter());
        WorkoutStatisticsService statisticsService = new WorkoutStatisticsService(workoutService);

        Workout fran = createFranWorkout(); // 2 exercises in RX
        Workout murph = createMurphWorkout(); // 5 exercises in RX

        List<Workout> workouts = Arrays.asList(murph, fran);

        List<Workout> sortedWorkouts = statisticsService.sortByExerciseCountAscending(workouts);

        assertEquals(2, sortedWorkouts.size());
        assertEquals("Fran", sortedWorkouts.get(0).getName());
        assertEquals("Murph", sortedWorkouts.get(1).getName());
    }

    private Workout createFranWorkout() {
        Movement thruster = new Movement(1L, "Thruster", null, "Thrusters", true);
        Movement pullup = new Movement(2L, "Pull-up", null, "Pull-ups", false);

        return new WorkoutBuilder("Fran")
                .type(WorkoutType.GIRL)
                .rounds(1)
                .rx(rx -> rx
                        .reps(thruster, 21, 15, 9)
                        .reps(pullup, 21, 15, 9)
                )
                .build();
    }

    private Workout createMurphWorkout() {
        Movement run = new Movement(1L, "Run", null, "Run", false);
        Movement pull = new Movement(2L, "Pull-ups", null, "Pull-ups", false);
        Movement push = new Movement(3L, "Push-ups", null, "Push-ups", false);
        Movement squat = new Movement(4L, "Air Squats", null, "Air Squats", false);

        return new WorkoutBuilder("Murph")
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
    }
}
