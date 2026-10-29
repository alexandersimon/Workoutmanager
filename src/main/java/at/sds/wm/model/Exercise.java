package at.sds.wm.model;

import at.sds.wm.model.enums.WeightUnit;
import jakarta.persistence.*;

import java.time.Duration;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name="Exercise")
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //@ManyToOne
    /*
    @ManyToOne(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "movement_id", nullable = false)
    private Movement movement;
    private List<Integer> reps;
    private Double distance;
    private Duration duration;
    private Double weightMale;
    private Double weightFemale;
    private WeightUnit weightUnit;
    private String notes;

    /**
     * Default constructor for Exercise.
     */
    public Exercise() {}

    /**
     * Constructs a new Exercise with the specified details.
     *
     * @param id the unique identifier of the exercise
     * @param movement the movement associated with the exercise
     * @param reps the list of repetitions for each set
     * @param weightMale the weight prescribed for men
     * @param weightFemale the weight prescribed for women
     * @param weightUnit the unit of weight (e.g., KG, LBS)
     * @param notes additional notes for the exercise
     */
    public Exercise(Long id, Movement movement, List<Integer> reps, Double weightMale, Double weightFemale, WeightUnit weightUnit, String notes) {
        this.id = id;
        this.movement = movement;
        setReps(reps);
        this.weightMale = weightMale;
        this.weightFemale = weightFemale;
        this.weightUnit = weightUnit;
        this.notes = notes;
    }

    /**
     * Gets the unique identifier of the exercise.
     *
     * @return the exercise ID
     */
    public Long getId() { return id; }

    /**
     * Sets the unique identifier of the exercise.
     *
     * @param id the exercise ID to set
     */
    public void setId(Long id) { this.id = id; }

    /**
     * Gets the movement associated with the exercise.
     *
     * @return the movement
     */
    public Movement getMovement() { return movement; }

    /**
     * Sets the movement associated with the exercise.
     *
     * @param movement the movement to set
     */
    public void setMovement(Movement movement) { this.movement = movement; }

    /**
     * Gets the list of repetitions for each set.
     *
     * @return the list of repetitions
     */
    public List<Integer> getReps() { return reps; }

    /**
     * Sets the list of repetitions for each set.
     * Repetitions must be greater than zero.
     *
     * @param reps the list of repetitions to set
     * @throws IllegalArgumentException if any repetition value is 0 or less
     */
    public void setReps(List<Integer> reps) {
        if (reps != null) {
            for (Integer rep : reps) {
                if (rep <= 0) {
                    throw new IllegalArgumentException("Wiederholungen müssen > 0 sein");
                }
            }
        }
        this.reps = reps;
    }

    /**
     * Gets the distance for the exercise (if applicable).
     *
     * @return the distance
     */
    public Double getDistance() { return distance; }

    /**
     * Sets the distance for the exercise.
     * Distance must be greater than zero.
     *
     * @param distance the distance to set
     * @throws IllegalArgumentException if distance is 0 or less
     */
    public void setDistance(Double distance) {
        if (distance != null && distance <= 0) {
            throw new IllegalArgumentException("Distanz muss > 0 sein");
        }
        this.distance = distance;
    }

    /**
     * Gets the duration for the exercise (if applicable).
     *
     * @return the duration
     */
    public Duration getDuration() { return duration; }

    /**
     * Sets the duration for the exercise.
     *
     * @param duration the duration to set
     */
    public void setDuration(Duration duration) { this.duration = duration; }

    /**
     * Gets the weight prescribed for men.
     *
     * @return the male weight
     */
    public Double getWeightMale() { return weightMale; }

    /**
     * Sets the weight prescribed for men.
     *
     * @param weightMale the male weight to set
     */
    public void setWeightMale(Double weightMale) { this.weightMale = weightMale; }

    /**
     * Gets the weight prescribed for women.
     *
     * @return the female weight
     */
    public Double getWeightFemale() { return weightFemale; }

    /**
     * Sets the weight prescribed for women.
     *
     * @param weightFemale the female weight to set
     */
    public void setWeightFemale(Double weightFemale) { this.weightFemale = weightFemale; }

    /**
     * Gets the unit of weight.
     *
     * @return the weight unit
     */
    public WeightUnit getWeightUnit() { return weightUnit; }

    /**
     * Sets the unit of weight.
     *
     * @param weightUnit the weight unit to set
     */
    public void setWeightUnit(WeightUnit weightUnit) { this.weightUnit = weightUnit; }

    /**
     * Gets additional notes for the exercise.
     *
     * @return the notes
     */
    public String getNotes() { return notes; }

    /**
     * Sets additional notes for the exercise.
     *
     * @param notes the notes to set
     */
    public void setNotes(String notes) { this.notes = notes; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Exercise)) return false;
        Exercise e = (Exercise) o;
        return Objects.equals(id, e.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "at.sds.wm.model.Exercise{" +
                "id=" + id +
                ", movement=" + movement +
                ", reps=" + reps +
                ", distance=" + distance +
                ", duration=" + duration +
                ", weightMale=" + weightMale +
                ", weightFemale=" + weightFemale +
                ", weightUnit=" + weightUnit +
                "}";
    }
}
