package com.julian.gymapp.domain;

    import jakarta.persistence.Column;
    import jakarta.persistence.Entity;
    import jakarta.persistence.FetchType;
    import jakarta.persistence.GeneratedValue;
    import jakarta.persistence.GenerationType;
    import jakarta.persistence.Id;
    import jakarta.persistence.Inheritance;
    import jakarta.persistence.InheritanceType;
    import jakarta.persistence.JoinColumn;
    import jakarta.persistence.JoinTable;
    import jakarta.persistence.ManyToMany;
    import jakarta.persistence.OneToMany;
    import jakarta.persistence.OneToOne;
    import jakarta.persistence.PrimaryKeyJoinColumn;
    import jakarta.persistence.Table;
    import java.util.Date;
    import java.util.List;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="trainer")
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "person_id")
public class Trainer extends Person {
  @Column(name="trainer_number", nullable = false, length = 20)
  private String trainerNumber;
  @Column(name="date_hired", nullable = false)
  private Date hireDate;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "trainer_specialty",
      joinColumns = @JoinColumn(name = "trainer_id"),
      inverseJoinColumns = @JoinColumn(name = "trainer_specialty_id"))
  private List<TrainerSpecialty> specialties;
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "trainer_plan",
      joinColumns = @JoinColumn(name = "trainer_id"),
      inverseJoinColumns = @JoinColumn(name = "plan_id"))
  private List<Plan> plans;
}
