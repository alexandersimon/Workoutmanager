package at.sds.wm.exceptions;

public class WorkoutIdDuplicateException extends RuntimeException {
    public WorkoutIdDuplicateException(String message) {
        super(message);
    }
}
