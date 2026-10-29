package at.sds.wm;

import at.sds.wm.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WorkoutManagerApplication {

    @Autowired
    private WorkoutRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(WorkoutManagerApplication.class, args);
    }
}