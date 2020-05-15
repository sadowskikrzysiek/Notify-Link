package com.softwareguardians.notifyBreak.entities;


import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.Data;


@Data
@Entity
public class Notify {
	  @Id
	  @GeneratedValue(strategy=GenerationType.AUTO)
	  private Long id;
	  @NotBlank(message="Nazwa powiadomienia jest obowizązkowa.")
  private String name;
  private Date createdAt;
  private String description;
  private String creator;
  private boolean active;
  
  @ManyToMany(targetEntity=Friend.class)
 
  private List<Friend> friends;
  @PrePersist
  void createdAt() {
    this.createdAt = new Date();
   
  }
}
