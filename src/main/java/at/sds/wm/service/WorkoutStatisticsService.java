package at.sds.wm.service;

import at.sds.wm.model.Workout;

import java.util.Comparator;
import java.util.List;

public class WorkoutStatisticsService {

    private final WorkoutService workoutService;

    /**
     * Constructs a new WorkoutStatisticsService with the specified WorkoutService.
     *
     * @param workoutService the workout service used for calculations
     */
    public WorkoutStatisticsService(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    /**
     * Sorts a list of workouts by the number of exercises they contain in ascending order.
     *
     * @param workouts the list of workouts to sort
     * @return a new sorted list of workouts
     */
    public List<Workout> sortByExerciseCountAscending(List<Workout> workouts) {

        return workouts.stream()
                .sorted(Comparator.comparingInt(workoutService::countExercises))
                .toList();
    }

    /**
     * Prints the name and exercise count of each workout in the list, sorted by exercise count.
     * Output is printed to standard output.
     *
     * @param workouts the list of workouts to display
     */
    public void printExerciseCount(List<Workout> workouts) {

        sortByExerciseCountAscending(workouts)
                .forEach(workout ->
                        System.out.printf("%s - %d exercises%n",
                                workout.getName(),
                                workoutService.countExercises(workout))
                );
    }
}
