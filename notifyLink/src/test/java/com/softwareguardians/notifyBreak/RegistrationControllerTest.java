package com.softwareguardians.notifyBreak;



import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.softwareguardians.notifyBreak.data.UserRepository;
import com.softwareguardians.notifyBreak.security.RegistrationController;



@RunWith(SpringRunner.class)
@WebMvcTest(RegistrationController.class)
public class RegistrationControllerTest {

  @Autowired
  private MockMvc mockMvc;
  
  @MockBean
  private   UserRepository userRepo;
  @MockBean
  private PasswordEncoder passwordEncoder;


 @Test
  public void testHomePage() throws Exception {
   mockMvc.perform(get("/register"))
     .andExpect(status().isOk())
     .andExpect(view().name("registration"))
      .andExpect(content().string(
        containsString("Tutaj możesz się zarejestrować.")));  
  }

}
