package com.softwareguardians.notifyBreak.entities;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;



@Data

@Entity
public class Friend {
	 @Id
	 @GeneratedValue(strategy=GenerationType.AUTO)
  private  long id;
  private String userAlfa;
  private  String name;
 private  boolean consent;

}
