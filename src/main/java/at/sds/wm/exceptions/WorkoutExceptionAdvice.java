package at.sds.wm.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@RestControllerAdvice
public class WorkoutExceptionAdvice {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException (MethodArgumentNotValidException e, HttpServletRequest request){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("workout validation failed");
        problemDetail.setProperty("error code","WORKOUT_VALIDATION_FAILED");
        if (e.hasFieldErrors()) {
            List<FieldError> fieldErrorList = e.getFieldErrors();
            AtomicReference<String> message = new AtomicReference<>("");

            fieldErrorList.stream()
                    .forEach(fieldError -> message.set("Field name: '" + fieldError.getField() + "'"
                            + " Rejected Value: (" + fieldError.getRejectedValue() + ")"
                            + " Message: " + fieldError.getDefaultMessage()));
            problemDetail.setDetail(message.get());
        }
        return problemDetail;
    }

    @ExceptionHandler(WorkoutIdNotFoundException.class)
    public ProblemDetail handleIdNotFoundException (WorkoutIdNotFoundException e, HttpServletRequest request){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle("workout not found");
        problemDetail.setProperty("error code","WORKOUT_NOT_FOUND");
        return problemDetail;
    }
    @ExceptionHandler(WorkoutIdDuplicateException.class)
    public ProblemDetail handleIdDuplicateException (WorkoutIdDuplicateException e, HttpServletRequest request){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
        problemDetail.setTitle("workout already exists");
        problemDetail.setProperty("error code","WORKOUT_ALREADY_EXISTS");
        return problemDetail;
    }
    @ExceptionHandler(WorkoutIdFormatException.class)
    public ProblemDetail handleIdFormatException (WorkoutIdFormatException e, HttpServletRequest request){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("workout id not numeric");
        problemDetail.setProperty("error code","WORKOUT_ID_NOT_VALID");
        return problemDetail;
    }
    @ExceptionHandler(ScalingLevelNotValidException.class)
    public ProblemDetail handleScalingLevelNotValidException (ScalingLevelNotValidException e, HttpServletRequest request){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("scaling level not valid");
        problemDetail.setProperty("error code","SCALING_LEVEL_NOT_VALID");
        return problemDetail;
    }
    @ExceptionHandler(ScalingOptionNotFoundException.class)
    public ProblemDetail handleScalingOptionNotFoundException (ScalingOptionNotFoundException e, HttpServletRequest request){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle("scaling option not found" );
                problemDetail.setProperty("error code","SCALING_OPTION_NOT_FOUND");
        return problemDetail;
    }
}

