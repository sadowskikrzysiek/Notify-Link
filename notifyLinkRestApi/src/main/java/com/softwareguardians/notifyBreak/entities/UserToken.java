package com.softwareguardians.notifyBreak.entities;



import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import lombok.Data;



@Data
@Entity
public class UserToken {
	 @Id
  private String name;
  private Date createdAt;
  private String username;
  @PrePersist
  void createdAt() {
    this.createdAt = new Date();
   
  }
}
