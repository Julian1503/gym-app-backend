package com.julian.gymapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="membership")
@NoArgsConstructor
@AllArgsConstructor
public class Membership {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="membership_id", nullable = false)
  private Long membershipId;
  @Column(name="name", nullable = false, length = 50)
  private String name;
  @Column(name="description", length = 200)
  private String description;
  @Column(name="days", precision = 4)
  private Short days;
  @Column(name="photo")
  private Byte[] photo;
  private Double price;

  @OneToMany(fetch = FetchType.LAZY)
  public List<MembershipElement> membershipElement;

  public Double getPrice() {
    calculatePrice();
    return price;
  }

  public void calculatePrice() {
    double totalCost = 0;
    for (MembershipElement element : membershipElement) {
      totalCost += element.getCost();
    }
    this.price = totalCost;
  }
}
