package com.softwareguardians.notifyBreak.data;




import org.springframework.data.repository.CrudRepository;
import com.softwareguardians.notifyBreak.entities.Friend;
import com.softwareguardians.notifyBreak.entities.Notify;





public interface NotifyRepository 
         extends CrudRepository<Notify, Long> {

	

	Iterable<Notify> findByCreatorAndActive(String username, boolean b);

	Iterable<Notify> findByActive(boolean b);

	Iterable<Notify> findByFriends(Friend friend);
	
	Notify findById(long id);

	Iterable<Notify> findByActiveAndFriends(boolean b, Friend friend);

}
