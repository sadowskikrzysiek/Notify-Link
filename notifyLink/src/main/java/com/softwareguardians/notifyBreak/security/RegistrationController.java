package com.softwareguardians.notifyBreak.security;




import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.softwareguardians.notifyBreak.data.UserRepository;


@Controller
@RequestMapping("/register")
public class RegistrationController {
  
  private UserRepository userRepo;
  private PasswordEncoder passwordEncoder;

  public RegistrationController(
      UserRepository userRepo, PasswordEncoder passwordEncoder) {
    this.userRepo = userRepo;
    this.passwordEncoder = passwordEncoder;
  }
  
  @GetMapping
  public String registerForm() {
    return "registration";
  }
  
  @PostMapping
  public String processRegistration(RegistrationForm form) {
	
	if(  userRepo.findByUsername(form.getUsername())==null)
	{ userRepo.save(form.toUser(passwordEncoder));
    return "/login";}
	else
		return"registrationNameError";
  }

}
