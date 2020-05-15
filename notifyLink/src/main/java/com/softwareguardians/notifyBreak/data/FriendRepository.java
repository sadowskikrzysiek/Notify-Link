package com.softwareguardians.notifyBreak.data;





import org.springframework.data.repository.CrudRepository;
import com.softwareguardians.notifyBreak.entities.Friend;





public interface FriendRepository 
         extends CrudRepository<Friend, Long> {

	Friend findByName(String name);

	Iterable<Friend> findByUserAlfa(String username);

	Friend findById(long parseLong);

	Iterable<Friend> findByUserAlfaAndConsent(String username, boolean b);

	Iterable<Friend> findByNameAndConsent(String username, boolean b);

	

}
