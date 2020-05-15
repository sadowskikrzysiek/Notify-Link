package com.softwareguardians.notifyBreak.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.softwareguardians.notifyBreak.data.FriendRepository;
import com.softwareguardians.notifyBreak.entities.Friend;





@Controller
@RequestMapping("/")
public class FriendsController {
	 
	  private FriendRepository friendRepo;
	 @Autowired
	  public FriendsController(FriendRepository friendRepo) 
	 {
	    this.friendRepo=friendRepo;
	  }


  @GetMapping("/friends")
  public String showFriends(Model model, Principal principal) {
	  String username = principal.getName();
	  List<Friend> friends = new ArrayList<>();
	 friendRepo.findByUserAlfaAndConsent(username, true).forEach(i -> friends.add(i));
	 model.addAttribute("friendsList",friends);
    return "friends";
  }

@GetMapping("/friend/{id}")
public String showFriend(@PathVariable("id") String id, Model model, Principal principal) {
	
	Friend friend =friendRepo.findById(Long.parseLong(id));
	 model.addAttribute("friend",friend);
	   
  return "friend";
}

  @GetMapping("/deleteFriend/{id}")
  public String deleteFriend(@PathVariable("id") String id, Model model, Principal principal) {
	  Friend friend = friendRepo.findById(Long.parseLong(id));
	  friendRepo.delete(friend);
    return "successAction";
  }
  
  @GetMapping("/newFriend")
  public String newFriend(Friend friend, Model model, Principal principal) {
    return "newFriend";
  }
  
  @PostMapping("/newFriend")
  public String saveNewFriend(Friend friend, Principal principal) {
	  String username = principal.getName();
	  friend.setUserAlfa(username);
	 friendRepo.save(friend);
    return "successAction";
  }
  
  @GetMapping("/getInvitations")
  public String invitations(Model model, Principal principal) {
	  List<Friend> invitations = new ArrayList<>();
	  String username = principal.getName();
		friendRepo.findByNameAndConsent(username,false).forEach(i -> invitations.add(i));
		 model.addAttribute("invitationsList",invitations);
    return "invitations";
  }
  
  @GetMapping("/acceptInvitation/{id}")
  public String acceptInvitation(@PathVariable("id") String id, Model model, Principal principal) {
	  Friend friend = friendRepo.findById(Long.parseLong(id));
	friend.setConsent(true);
	friendRepo.save(friend);
    return "successAction";
  }
  
  @GetMapping("/deleteInvitation/{id}")
  public String deleteInvitation(@PathVariable("id") String id, Model model, Principal principal) {
	  Friend friend = friendRepo.findById(Long.parseLong(id));
	  friendRepo.delete(friend);
    return "successAction";
  }
}

