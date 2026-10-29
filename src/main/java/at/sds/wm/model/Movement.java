package at.sds.wm.model;

import at.sds.wm.model.enums.MovementCategory;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="Movement")
public class Movement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private MovementCategory category;
    private String description;
    private boolean weighted;

    /**
     * Default constructor for Movement.
     */
    public Movement() {}

    /**
     * Constructs a new Movement with the specified details.
     *
     * @param id the unique identifier of the movement
     * @param name the name of the movement
     * @param category the category of the movement
     * @param description a description of the movement
     * @param weighted whether the movement is typically performed with weight
     */
    public Movement(Long id, String name, MovementCategory category, String description, boolean weighted) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.weighted = weighted;
    }

    /**
     * Gets the unique identifier of the movement.
     *
     * @return the movement ID
     */
    public Long getId() { return id; }

    /**
     * Sets the unique identifier of the movement.
     *
     * @param id the movement ID to set
     */
    public void setId(Long id) { this.id = id; }

    /**
     * Gets the name of the movement.
     *
     * @return the movement name
     */
    public String getName() { return name; }

    /**
     * Sets the name of the movement.
     *
     * @param name the movement name to set
     */
    public void setName(String name) { this.name = name; }

    /**
     * Gets the category of the movement.
     *
     * @return the movement category
     */
    public MovementCategory getCategory() { return category; }

    /**
     * Sets the category of the movement.
     *
     * @param category the movement category to set
     */
    public void setCategory(MovementCategory category) { this.category = category; }

    /**
     * Gets the description of the movement.
     *
     * @return the movement description
     */
    public String getDescription() { return description; }

    /**
     * Sets the description of the movement.
     *
     * @param description the movement description to set
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * Returns whether the movement is weighted.
     *
     * @return true if the movement is weighted, false otherwise
     */
    public boolean isWeighted() { return weighted; }

    /**
     * Sets whether the movement is weighted.
     *
     * @param weighted true if the movement is weighted, false otherwise
     */
    public void setWeighted(boolean weighted) { this.weighted = weighted; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movement)) return false;
        Movement m = (Movement) o;
        return Objects.equals(id, m.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "at.sds.wm.model.Movement{id=" + id + ", name='" + name + "', category=" + category + "}";
    }
}
