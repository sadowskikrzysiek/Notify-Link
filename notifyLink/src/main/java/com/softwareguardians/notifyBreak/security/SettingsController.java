package com.softwareguardians.notifyBreak.security;



import java.security.Principal;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.softwareguardians.notifyBreak.data.UserRepository;
import com.softwareguardians.notifyBreak.entities.SettingsChanges;
import com.softwareguardians.notifyBreak.entities.User;

@Controller
@RequestMapping("/settings")
public class SettingsController {
  
  private UserRepository userRepo;
  private PasswordEncoder passwordEncoder;

  public SettingsController(
      UserRepository userRepo, PasswordEncoder passwordEncoder) {
    this.userRepo = userRepo;
    this.passwordEncoder = passwordEncoder;
  }
  
  @GetMapping
  public String registerForm(Model model) {
    return "settings";
  }
  
  @PostMapping("/newPassword")
  public String passwordChange( SettingsChanges patch, Principal principal) {
	  String username = principal.getName();
	  User user = userRepo.findByUsername(username);
	  if(comparer(passwordEncoder, patch.getOldPassword(), user.getPassword()))
		{ user.setPassword(encrypt(passwordEncoder, patch.getNewPassword()));
		System.out.println("Hasla sie zgadzaja");
		userRepo.save(user);
    return "/successAction";}
    else {
    	System.out.println("Hasla sie nie zgadzaja");
    	return "/wrongPassword";
    }
  }
  @PostMapping("/newDescription")
  public String descriptionChange(  SettingsChanges patch, RegistrationForm form, Principal principal) {
	  String username = principal.getName();
	  User userPrevious = userRepo.findByUsername(username);
	  userPrevious.setDescription(patch.getDescription());
	  System.out.println("NEW DESCRIPTION"+ patch.getDescription());
    userRepo.save(userPrevious);
    return "successAction";
  }
  public boolean comparer(PasswordEncoder passwordEncoder, String introducedPassword, String encodedPassword ) {
	    return  passwordEncoder.matches(introducedPassword, encodedPassword);   
	  }
  public String encrypt(PasswordEncoder passwordEncoder, String introducedPassword) {
	    return  passwordEncoder.encode(introducedPassword);   
	  }
}
