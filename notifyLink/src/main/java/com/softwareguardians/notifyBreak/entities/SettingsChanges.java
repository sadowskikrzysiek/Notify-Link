package com.softwareguardians.notifyBreak.entities;




import lombok.Data;



@Data
public class SettingsChanges {
	
  private String oldPassword;
  private String newPassword;
  private String description;
  
}
