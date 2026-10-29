package at.sds.wm.util;

import at.sds.wm.model.Exercise;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ExerciseFormatter {

    public String format(Exercise exercise) {
        if (exercise == null || exercise.getMovement() == null) {
            return "Unknown exercise";
        }

        String movementName = exercise.getMovement().getName();

        if (exercise.getReps() != null && !exercise.getReps().isEmpty()) {
            String reps = exercise.getReps().stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));

            return movementName + " - " + reps + " reps";
        }

        if (exercise.getDistance() != null) {
            return movementName + " - " + exercise.getDistance() + " meters";
        }

        return movementName;
    }
}
