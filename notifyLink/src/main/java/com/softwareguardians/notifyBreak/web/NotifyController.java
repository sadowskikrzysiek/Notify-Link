package com.softwareguardians.notifyBreak.web;

import java.security.Principal;
import java.util.ArrayList;

import java.util.List;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import com.softwareguardians.notifyBreak.data.FriendRepository;

import com.softwareguardians.notifyBreak.data.NotifyRepository;


import com.softwareguardians.notifyBreak.entities.Friend;


import com.softwareguardians.notifyBreak.entities.Notify;

import com.softwareguardians.notifyBreak.entities.User;



@Controller
@RequestMapping("/")
public class NotifyController {
	
	  private NotifyRepository notifyRepo;

	  private FriendRepository friendRepo;

	 @Autowired
	  public NotifyController(NotifyRepository notifyRepo,  FriendRepository friendRepo) 
	 {
	    this.notifyRepo=notifyRepo;
	 
	    this.friendRepo=friendRepo;
	  }

	  @ModelAttribute(name = "Notification")
	  public Notify notification() {
	    return new Notify();
	  }
	 
  @GetMapping("/newNotification")
  public String showNewNotifyForm(Model model, Principal principal) {
	  String username = principal.getName();
	  List<Friend> friends = new ArrayList<>();
	  friendRepo.findByUserAlfaAndConsent(username, true).forEach(i -> friends.add(i));
	 model.addAttribute("friendsList",friends);
    return "newNotification";
  }

  @PostMapping("/newNotification")
  public String processNewNotification(@Valid @ModelAttribute("Notification") Notify notify, Errors errors, Model model,  SessionStatus sessionStatus, Principal principal, @AuthenticationPrincipal User user) {
    if (errors.hasErrors()) {
  List<ObjectError> errorsList= errors.getAllErrors();
  for(ObjectError el: errorsList)
  {
	  System.out.println(el.getDefaultMessage());
  }
      return "newNotification";
    }
    notify.setActive(true);
	String username = principal.getName();
	notify.setCreator(username);
    notifyRepo.save(notify);
    sessionStatus.setComplete();
    return "successAction";
  }

  @GetMapping("/waitingNotifications")
  public String showWaitingNotifications(Model model, Principal principal) {
	  String username = principal.getName();
	  List<Notify> notifications = new ArrayList<>();
	 notifyRepo.findByCreatorAndActive(username,true).forEach(i -> notifications.add(i));
	 model.addAttribute("notificationsList",notifications);	
    return "notifications";
  }
  
  @GetMapping("/notificationArchive")
  public String showOldNotifications(Model model, Principal principal) {
	  String username = principal.getName();
	  List<Notify> notifications = new ArrayList<>();
	  notifyRepo.findByCreatorAndActive(username,false).forEach(i -> notifications.add(i));
	  model.addAttribute("notificationsList",notifications);	
	 return "notifications";
  }
  
  @GetMapping("/notification/{id}")
  public String showNotification(@PathVariable("id") String id, Model model, Principal principal) {
	 Notify notify =notifyRepo.findById(Long.parseLong(id));
	 model.addAttribute("notification",notify);
    return "notification";
  }
  
  @GetMapping("/deleteNotification/{id}")
  public String deleteNotification(@PathVariable("id") String id, Model model, Principal principal) {
	notifyRepo.deleteById(Long.parseLong(id));
   return "successAction";
  }

  
}

