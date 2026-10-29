package at.sds.wm.model;

import at.sds.wm.model.enums.ScalingLevel;
import at.sds.wm.model.enums.WorkoutType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
@Entity
@Table (name="Workout")
@NamedQuery(name="findAllWokouts", query = "select w from Workout w")
public class Workout {

    @Valid
    @Schema(name = "workout id", required = true)
    @Id
  //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "workout name is mandatory but is empty")
    @NotBlank(message = "workout name is mandatory but is blank")
    @Schema(name = "workout name", required = true)
    @Column(name="name")
    private String name;

    private WorkoutType type;
    private LocalDate date;
    private String description;
    private Duration timecap;

    @Min(1)
    private int rounds;
    private Duration restBetweenRounds;

    //@OneToMany
    /*
    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
    )
     */
    @OneToMany(
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<ScalingOption> scalingOptions;

    /**
     * Default constructor for Workout.
     */
    public Workout() {
    }

    /**
     * Constructs a new Workout with the specified details.
     *
     * @param id the unique identifier of the workout
     * @param name the name of the workout
     * @param type the type of the workout (e.g., AMRAP, EMOM, etc.)
     * @param date the date the workout was performed
     * @param description a description of the workout
     * @param timecap the time limit for the workout
     * @param rounds the number of rounds in the workout
     * @param restBetweenRounds the duration of rest between rounds
     * @param scalingOptions a list of scaling options for the workout
     */
    public Workout(Long id,
                   String name,
                   WorkoutType type,
                   LocalDate date,
                   String description,
                   Duration timecap,
                   int rounds,
                   Duration restBetweenRounds,
                   List<ScalingOption> scalingOptions) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.date = date;
        this.description = description;
        this.timecap = timecap;
        this.rounds = rounds;
        this.restBetweenRounds = restBetweenRounds;
        this.scalingOptions = scalingOptions;
    }

    /**
     * Gets the unique identifier of the workout.
     *
     * @return the workout ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the workout.
     *
     * @param id the workout ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the workout.
     *
     * @return the workout name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the type of the workout.
     *
     * @return the workout type
     */
    public WorkoutType getType() {
        return type;
    }

    /**
     * Gets the date the workout was performed.
     *
     * @return the workout date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Gets the description of the workout.
     *
     * @return the workout description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the time limit (time cap) for the workout.
     *
     * @return the time cap duration
     */
    public Duration getTimecap() {
        return timecap;
    }

    /**
     * Gets the number of rounds in the workout.
     *
     * @return the number of rounds
     */
    public int getRounds() {
        return rounds;
    }

    /**
     * Sets the number of rounds in the workout.
     *
     * @param rounds the number of rounds to set
     */
    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    /**
     * Gets the duration of rest between rounds.
     *
     * @return the rest duration between rounds
     */
    public Duration getRestBetweenRounds() {
        return restBetweenRounds;
    }

    /**
     * Sets the duration of rest between rounds.
     *
     * @param restBetweenRounds the rest duration to set
     */
    public void setRestBetweenRounds(Duration restBetweenRounds) {
        this.restBetweenRounds = restBetweenRounds;
    }

    /**
     * Sets the name of the workout.
     *
     * @param name the workout name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the type of the workout.
     *
     * @param type the workout type to set
     */
    public void setType(WorkoutType type) {
        this.type = type;
    }

    /**
     * Sets the date the workout was performed.
     *
     * @param date the workout date to set
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Sets the description of the workout.
     *
     * @param description the workout description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the time limit (time cap) for the workout.
     *
     * @param timecap the time cap duration to set
     */
    public void setTimecap(Duration timecap) {
        this.timecap = timecap;
    }

    /**
     * Gets the list of scaling options for the workout.
     *
     * @return the list of scaling options
     */
    public List<ScalingOption> getScalingOptions() {
        return scalingOptions;
    }

    /**
     * Adds a scaling option to the workout. If an option with the same level
     * already exists, it is replaced.
     *
     * @param scalingOption the scaling option to add
     */
    public void addScalingOptions(ScalingOption scalingOption) {
        if (this.scalingOptions == null) {
            this.scalingOptions = new java.util.ArrayList<>();
        }
        
        if (scalingOption == null || scalingOption.getLevel() == null) {
            return;
        }

        // Bestehende Option mit gleichem Level entfernen
        this.scalingOptions.removeIf(s -> scalingOption.getLevel().equals(s.getLevel()));
        
        this.scalingOptions.add(scalingOption);
    }

    /**
     * Calculates the total number of repetitions for the RX (prescribed) version of the workout.
     *
     * @return the total number of repetitions for RX
     */
    public int getTotalRepetitions() {
        return getTotalRepetitions(getRxOption());
    }

    /**
     * Calculates the total number of repetitions for a specific scaling option.
     *
     * @param scalingOption the scaling option to calculate repetitions for
     * @return the total number of repetitions, or 0 if the scaling option is null
     */
    public int getTotalRepetitions(ScalingOption scalingOption) {
        if (scalingOption == null) {
            return 0;
        }
        return scalingOption.getTotalRepetitions(this.rounds);
    }

    /**
     * Gets the RX (prescribed) scaling option for the workout.
     *
     * @return the RX scaling option, or null if not found
     */
    public ScalingOption getRxOption() {
        if (scalingOptions == null) return null;
        return scalingOptions.stream()
                .filter(s -> ScalingLevel.RX.equals(s.getLevel()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Gets the Intermediate scaling option for the workout.
     *
     * @return the Intermediate scaling option, or null if not found
     */
    public ScalingOption getIntermediateOption() {
        if (scalingOptions == null) return null;
        return scalingOptions.stream()
                .filter(s -> ScalingLevel.INTERMEDIATE.equals(s.getLevel()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Gets the Beginner scaling option for the workout.
     *
     * @return the Beginner scaling option, or null if not found
     */
    public ScalingOption getBeginnerOption() {
        if (scalingOptions == null) return null;
        return scalingOptions.stream()
                .filter(s -> ScalingLevel.BEGINNER.equals(s.getLevel()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Gets the scaling option for a specific level.
     *
     * @param level the scaling level to look for
     * @return the scaling option for the given level, or null if not found
     */
    public ScalingOption getScalingOption(ScalingLevel level) {
        if (scalingOptions == null) return null;
        return scalingOptions.stream()
                .filter(s -> level.equals(s.getLevel()))
                .findFirst()
                .orElse(null);
    }
}