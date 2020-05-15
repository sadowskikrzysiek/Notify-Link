package com.softwareguardians.notifyBreak.data;




import org.springframework.data.repository.CrudRepository;
import com.softwareguardians.notifyBreak.entities.UserToken;




public interface UserTokenRepository 
         extends CrudRepository<UserToken, String> {

	UserToken findByName(String name);

	
	

}
