package at.sds.wm.controller;

import at.sds.wm.exceptions.*;
import at.sds.wm.model.Exercise;
import at.sds.wm.model.ScalingOption;
import at.sds.wm.model.Workout;

import at.sds.wm.model.enums.ScalingLevel;
import at.sds.wm.repository.WorkoutRepository;
import at.sds.wm.service.WorkoutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

@RestController
@Tag(name = "Document Workouts-API")
public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {

        this.workoutService = workoutService;
        }

    @PostMapping("/workouts")
    @Operation(summary = "create a workout", description = "create a workout using data from RequestBody 'workout'")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                         description = "workout successfully created",
                         content = {@Content(mediaType = "application/json",
                                             schema = @Schema(implementation = Workout.class))}),
            @ApiResponse(responseCode = "409",
                         description = "workout already exists",
                         content = {@Content(mediaType = "application/json",
                                             schema = @Schema(implementation = ProblemDetail.class))})
    })
    public ResponseEntity<Workout> createWorkout (@Valid @RequestBody Workout workout) {
        Workout newWorkout = workoutService.createWorkout(workout);
        //return new ResponseEntity(newWorkout, HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).body(newWorkout);
    }

    @PutMapping("/workouts")
    @Operation(summary = "put a workout",description = "update an existing workout using data from RequestBody 'workout'")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "workout successfully changed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Workout.class))}),
            @ApiResponse(responseCode = "404",
                    description = "workout not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))})
    })
    public ResponseEntity<Workout> putWorkout (@Valid @RequestBody Workout workout) {
        Workout newWorkout = workoutService.setWorkoutById(workout.getId(), workout);
        //return new ResponseEntity(newWorkout, HttpStatus.OK);
        return ResponseEntity.ok(newWorkout);
    }

    @PutMapping("/workouts/{id}")
    @Operation(summary = "put a workout", description = "update the workout with 'id' using data from RequestBody 'workout'")
    @ResponseStatus(HttpStatus.OK)
    @Parameter(name = "id", description = "workout id", required = true)
    @Parameter(name = "workout", description = "workout", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "workout successfully changed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Workout.class))}),
            @ApiResponse(responseCode = "404",
                    description = "workout not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))})
    })
    public ResponseEntity<Workout> putWorkoutId (@PathVariable Long id, @Valid @RequestBody Workout workout) {
        if (workout.getId() != null && !workout.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Workout-ID in RequestBody and Path-Parameter must be equal");
        }

        return ResponseEntity.ok(workoutService.setWorkoutById(id, workout));
    }

    @DeleteMapping("/workouts/{id}")
    @Operation(summary = "delete a workout", description = "delete the workout with 'id'")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Parameter(name = "id", description = "workout id", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "workout successfully deleted"),
            @ApiResponse(responseCode = "404",
                    description = "workout not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))})
    })
    public ResponseEntity deleteWorkoutId (@PathVariable Long id) {
        workoutService.deleteWorkoutById(id);
        //return new ResponseEntity("workout deleted " + id, HttpStatus.NO_CONTENT);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/workouts")
    @Operation(summary = "list all workouts", description = "list all existing workouts")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Workout>> getWorkouts() {
        return new ResponseEntity<List<Workout>>(workoutService.getAllWorkouts(), HttpStatus.OK);
    }

    @GetMapping("/workouts/{id}")
    @Operation(summary = "get a workout", description = "get the workout with 'id'")
    @ResponseStatus(HttpStatus.OK)
    @Parameter(name = "id", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "workout found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Workout.class))}),
            @ApiResponse(responseCode = "404",
                    description = "workout not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))})
    })
    public ResponseEntity<Workout> getWorkoutById(@PathVariable String id) {
        long idLong;
        try {
            idLong = Long.parseLong(id);
        }
        catch (NumberFormatException e) {
            throw new WorkoutIdFormatException("Workout-Id has to be numeric " + id);
        }

        Workout workout = workoutService.getWorkoutById(idLong);
        //return new ResponseEntity(workout, HttpStatus.OK);
        return ResponseEntity.ok(workout);
    }

    @GetMapping("/workouts/{id}/exercises")
    @Operation(summary = "list exercises of a workout", description = "list all exercises of the workout with 'id'")
    @ResponseStatus(HttpStatus.OK)
    @Parameter(name = "id", required = true)
    @Parameter(name = "level", required = true)
    public ResponseEntity <List<Exercise>> getExercises(@PathVariable String id, @RequestParam(value = "level") String level) {

        // check Workout-Id
        Long idLong;
        try {
            idLong = Long.parseLong(id);
        }
        catch (NumberFormatException e) {
            throw new WorkoutIdFormatException("Workout-Id has to be numeric " + id);
        }
        Workout workout = workoutService.getWorkoutById(idLong);

        // check ScalingLevel
        ScalingLevel scalingLevel;
        try {
            scalingLevel = ScalingLevel.valueOf(level.toUpperCase());
        } catch (Exception e) {
            throw new ScalingLevelNotValidException("ScalingLevel invalid " + level);
        }

        // get list of excercises
        List<Exercise> exercises = null;
        try{
            exercises = workout.getScalingOption(scalingLevel).getExercises();
        } catch (RuntimeException e) {
            throw  new ScalingOptionNotFoundException("no exerciss for ScalingLevel" + scalingLevel);
        }
        return new ResponseEntity<List<Exercise>>(exercises, HttpStatus.OK);
    }
}
