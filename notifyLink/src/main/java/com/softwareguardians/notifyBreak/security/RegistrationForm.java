package com.softwareguardians.notifyBreak.security;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.softwareguardians.notifyBreak.entities.User;
import lombok.Data;


@Data
public class RegistrationForm {

  private String username;
  private String password;
  private String description;

  
  public User toUser(PasswordEncoder passwordEncoder) {
   User user= new User();
   user.setUsername(username);
   user.setPassword(passwordEncoder.encode(password));
   user.setDescription(description);
       
       return user;
  }
  
}
