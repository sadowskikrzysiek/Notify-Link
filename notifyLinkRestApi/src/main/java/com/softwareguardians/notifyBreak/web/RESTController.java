package com.softwareguardians.notifyBreak.web;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.softwareguardians.notifyBreak.data.FriendRepository;
import com.softwareguardians.notifyBreak.data.NotifyRepository;
import com.softwareguardians.notifyBreak.data.UserRepository;
import com.softwareguardians.notifyBreak.data.UserTokenRepository;
import com.softwareguardians.notifyBreak.entities.Friend;
import com.softwareguardians.notifyBreak.entities.Notify;
import com.softwareguardians.notifyBreak.entities.User;
import com.softwareguardians.notifyBreak.entities.UserToken;

@RestController
@RequestMapping(path="/api", produces="application/json")
@CrossOrigin(origins="*")
public class RESTController {
private NotifyRepository notifyRepo;
private UserRepository userRepo;
private PasswordEncoder passwordEncoder;
private UserTokenRepository userTokenRepo;
private FriendRepository friendRepo;
public RESTController(NotifyRepository notifyRepo, UserRepository userRepo, PasswordEncoder passwordEncoder, UserTokenRepository userTokenRepo,   FriendRepository friendRepo)
{this.notifyRepo=notifyRepo;
this.userRepo=userRepo;
this.passwordEncoder = passwordEncoder;
this.userTokenRepo=userTokenRepo;
this.friendRepo=friendRepo;
}


@PostMapping(path="/restLogin", consumes="application/json")
public UserToken login(@RequestBody User potentialUser)
{User user = userRepo.findByUsername(potentialUser.getUsername());
if(comparer(passwordEncoder,potentialUser.getPassword(),user.getPassword() ))
{
	UserToken userToken= new UserToken();
	String time= LocalDateTime.now().toString();
	String token=time+potentialUser.getUsername();
	userToken.setName(token);
	userToken.setUsername(potentialUser.getUsername());
	userTokenRepo.save(userToken);
	return userToken;
}
else
{UserToken userToken= new UserToken();
	return null;
}
}
@PostMapping(path="/restGetNotifications", consumes="application/json")
public List getNotifications(@RequestBody UserToken potentialToken)
{
	if(userTokenRepo.findByName(potentialToken.getName())!=null)
	{
		List <Notify> notifications= new ArrayList();
		  Friend friend=friendRepo.findByName(potentialToken.getUsername());
		 notifyRepo.findByActiveAndFriends(true,friend).forEach(i -> notifications.add(i));
		 for(Notify notify: notifications)
		 {notify.setActive(false);
		 notifyRepo.save(notify);
		 }
		return notifications;
	}
else
	return null;
}

public boolean comparer(PasswordEncoder passwordEncoder, String introducedPassword, String encodedPassword ) {
    return  passwordEncoder.matches(introducedPassword, encodedPassword);   
  }
}

