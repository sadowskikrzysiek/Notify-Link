package com.softwareguardians.notifyBreak.data;
import org.springframework.data.repository.CrudRepository;
import com.softwareguardians.notifyBreak.entities.User;


public interface UserRepository extends CrudRepository<User, Long> {

  User findByUsername(String username);
  
}
