package com.astropay.demo.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  @Column(name = "amount", nullable = false)
  private Long amount;
  @Column(name = "version", nullable = false)
  @Version
  private Long version;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;
  @Column(name = "created_on")
  private OffsetDateTime createdOn;


  public boolean hasEnoughMoney(Long amount) {
    return  this.amount >= amount;
  }

  public void debit(Long amount) {
    this.amount -= amount;
  }

  public void credit(Long amount) {
    this.amount += amount;
  }
}
