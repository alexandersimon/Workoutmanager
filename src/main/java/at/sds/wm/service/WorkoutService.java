package at.sds.wm.service;

import at.sds.wm.exceptions.*;
import at.sds.wm.model.Exercise;
import at.sds.wm.model.Movement;
import at.sds.wm.model.ScalingOption;
import at.sds.wm.model.Workout;
import at.sds.wm.repository.MovementRepository;
import at.sds.wm.repository.WorkoutRepository;
import at.sds.wm.util.ExerciseFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class WorkoutService {

    private static Logger logger = LoggerFactory.getLogger(WorkoutService.class);

    private final ExerciseFormatter exerciseFormatter;
    private final WorkoutRepository workoutRepository;
    private final MovementRepository movementRepository;

    public WorkoutService(
            WorkoutRepository workoutRepository,
            MovementRepository movementRepository,
            ExerciseFormatter exerciseFormatter    ) {
        this.workoutRepository = workoutRepository;
        this.movementRepository = movementRepository;
        this.exerciseFormatter = exerciseFormatter;
    }

    /**
     * Finds an exercise in a workout by the name of its movement.
     * Searches across all scaling options.
     *
     * @param workout the workout to search in
     * @param movementName the name of the movement to find
     * @return an Optional containing the exercise if found, or empty otherwise
     */
    @Transactional(readOnly = true)
    public Optional<Exercise> findExerciseByMovementName(
            Workout workout,
            String movementName
    ) {
        if (workout == null || movementName == null || workout.getScalingOptions() == null) {
            return Optional.empty();
        }

        return workout.getScalingOptions().stream()
                .filter(scalingOption -> scalingOption.getExercises() != null)
                .flatMap(scalingOption -> scalingOption.getExercises().stream())
                .filter(exercise -> exercise.getMovement() != null)
                .filter(exercise ->
                        movementName.equalsIgnoreCase(exercise.getMovement().getName())
                )
                .findFirst();
    }

    /**
     * Finds an exercise in a workout and formats it as a string.
     *
     * @param workout the workout to search in
     * @param movementName the name of the movement to find
     * @return an Optional containing the formatted exercise string if found, or empty otherwise
     */
    @Transactional(readOnly = true)
    public Optional<String> findAndFormatExercise(
            Workout workout,
            String movementName
    ) {
        return findExerciseByMovementName(workout, movementName)
                .map(exerciseFormatter::format);
    }

    /**
     * Counts the total number of exercises in a workout across all scaling options.
     *
     * @param workout the workout to count exercises for
     * @return the total number of exercises
     */
    @Transactional(readOnly = true)
    public int countExercises(Workout workout) {
        if (workout == null || workout.getScalingOptions() == null) {
            return 0;
        }

        return workout.getScalingOptions().stream()
                .filter(scalingOption -> scalingOption.getExercises() != null)
                .mapToInt(scalingOption -> scalingOption.getExercises().size())
                .sum();
    }
    public List<Workout> getAllWorkouts() {
        return workoutRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Workout getWorkoutById(Long id) {
        logger.info("getWorkoutById - START WorkoutId = {}", id );
/*
        if (workoutRepository.findById(id).isEmpty()) {
            logger.warn("getWorkoutById - WorkoutId not found = {}", id );
            throw new WorkoutIdNotFoundException("workout not found" + id);
        }
        return workoutRepository.findById(id).get();
*/
        return workoutRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("WorkoutId not found: {}", id);

                    return new WorkoutIdNotFoundException(
                            "WorkoutId not found: " + id
                    );
                });
    }
/*
    public Workout createWorkout (Workout workout) {
        logger.info("createWorkout - START WorkoutId = {}", workout.getId());

        if (!workoutRepository.findById(workout.getId()).isEmpty()) {
            logger.warn("createWorkout - WorkoutId already exists = {}", workout.getId() );
            throw new WorkoutIdDuplicateException("workout already exists " + workout.getId());
        }
        return workoutRepository.save(workout);
    }
*/
    @Transactional
    public Workout createWorkout(Workout workout) {
        if (workout.getId() == null) {
            throw new IllegalArgumentException("Workout-ID must not be null");
        }

        if (workoutRepository.existsById(workout.getId())) {
            throw new WorkoutIdDuplicateException(
                    "workout already exists " + workout.getId()
            );
        }
        resolveMovements(workout);

        return workoutRepository.save(workout);
    }

    @Transactional
    public Workout setWorkoutById(Long id, Workout workout) {
        logger.info("setWorkoutById - START WorkoutId = {}", id );

        if (workoutRepository.findById(id).isEmpty()) {
            logger.warn("setWorkoutById - WorkoutId not found = {}", id);
            throw new WorkoutIdNotFoundException("workout to set not found " + id);
        }
        resolveMovements(workout);
       return workoutRepository.save(workout);
    }

    @Transactional
    public void deleteWorkoutById(Long id) {
        logger.info("deleteWorkoutById - START WorkoutId = {}", id );

        if (workoutRepository.findById(id).isEmpty()) {
            logger.debug("deleteWorkoutById - WorkoutId not found = {}", id);
            throw new WorkoutIdNotFoundException("workout to delete not found " + id);
        }
        workoutRepository.deleteById(id);

        logger.info("deleteWorkoutById - END");
    }

    private void resolveMovements(Workout workout) {
        if (workout.getScalingOptions() == null) {
            return;
        }

        for (ScalingOption option : workout.getScalingOptions()) {
            if (option.getExercises() == null) {
                continue;
            }

            for (Exercise exercise : option.getExercises()) {
                exercise.setMovement(
                        resolveMovement(exercise.getMovement())
                );
            }
        }
    }

    private Movement resolveMovement(Movement supplied) {
        if (supplied == null) {
            throw new IllegalArgumentException("Movement missing");
        }

        // Eine ID referenziert ausdrücklich ein bestehendes Movement.
        if (supplied.getId() != null) {
            return movementRepository.findById(supplied.getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Movement not found: " + supplied.getId()
                    ));
        }

        if (supplied.getName() == null
                || supplied.getName().isBlank()) {
            throw new IllegalArgumentException("Movement-Name missing");
        }

        String name = supplied.getName().strip();

        return movementRepository.findByNameIgnoreCase(name)
                .orElseGet(() -> {
                    Movement movement = new Movement();
                    movement.setName(name);
                    movement.setCategory(supplied.getCategory());
                    movement.setDescription(supplied.getDescription());
                    movement.setWeighted(supplied.isWeighted());

                    return movementRepository.saveAndFlush(movement);
                });
    }

}
