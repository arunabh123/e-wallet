package com.flip.demo.wallet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.flip.demo.wallet.models.User;
import com.flip.demo.wallet.repositories.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = WalletApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
  locations = "classpath:application-IT.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WalletApplicationTests {

	@Autowired
    private MockMvc mvc;
	
	@Autowired
	private UserRepository userRepo;
	
	@Test
	public void test01RegisterUser_thenStatus200()
	  throws Exception {

	    mvc.perform(post("/create_user").content("{\n" + 
	    		"    \"username\":\"arunabh 1\"\n" + 
	    		"}")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(content()
	      .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	
	@Test
	public void test01RegisterUser2_thenStatus200()
	  throws Exception {

	    mvc.perform(post("/create_user").content("{\n" + 
	    		"    \"username\":\"arunabh 2\"\n" + 
	    		"}")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(content()
	      .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	
	@Test
	public void test02RegisterSameUser_thenStatus409()
	  throws Exception {
	    
	    mvc.perform(post("/create_user").content("{\n" + 
	    		"    \"username\":\"arunabh 1\"\n" + 
	    		"}")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isConflict());
	}
	
	@Test
	public void test03Updatebalance_thenStatus200() throws Exception{
		
		User user = userRepo.findUserByUsername("arunabh 1");
		
		mvc.perform(post("/balance_topup").content("{\n" + 
				"    \"amount\":10000\n" + 
				"}")
				.header("Authorization", user.getId().toString())
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isNoContent());
	}
	
	@Test
	public void test04Updatebalance_thenStatus401() throws Exception{
		
		User user = userRepo.findUserByUsername("arunabh 1");
		String token = user.getId().toString().replace("e", "f");
		mvc.perform(post("/balance_topup").content("{\n" + 
				"    \"amount\":10000\n" + 
				"}")
				.header("Authorization", token)
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isUnauthorized());
	}
	
	@Test
	public void test05Updatebalance_thenStatus400() throws Exception{
		
		User user = userRepo.findUserByUsername("arunabh 1");
		
		mvc.perform(post("/balance_topup").content("{\n" + 
				"    \"amount\":-1\n" + 
				"}")
				.header("Authorization", user.getId().toString())
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isBadRequest());
	}
	
	@Test
	public void test06GetBalance_thenStatus200() throws Exception{
		
		User user = userRepo.findUserByUsername("arunabh 1");
		
		mvc.perform(get("/balance_read")
				.header("Authorization", user.getId().toString())
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk());
	}
	
	@Test
	public void test07GetBalance_thenStatus401() throws Exception{
		
		User user = userRepo.findUserByUsername("arunabh 1");
		String token = user.getId().toString().replace("e", "f");
		mvc.perform(get("/balance_read")
				.header("Authorization", token)
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isUnauthorized());
	}
	
	
	@Test
	public void test08Transfer_thenStatus200() throws Exception{
		
		User user = userRepo.findUserByUsername("arunabh 1");
		
		mvc.perform(post("/transfer").content("{\n" + 
				"    \"amount\":100,\n" + 
				"    \"to_username\" : \"arunabh 2\"\n" + 
				"}")
				.header("Authorization", user.getId().toString())
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isNoContent());
	}
	
	@Test
	public void test09Transfer_thenStatus404() throws Exception{
		
		User user = userRepo.findUserByUsername("arunabh 1");
		
		mvc.perform(post("/transfer").content("{\n" + 
				"    \"amount\":100,\n" + 
				"    \"to_username\" : \"arunabh 3\"\n" + 
				"}")
				.header("Authorization", user.getId().toString())
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isNotFound());
	}
	
	@Test
	public void test10Transfer_thenStatus400() throws Exception{
		
		User user = userRepo.findUserByUsername("arunabh 1");
		
		mvc.perform(post("/transfer").content("{\n" + 
				"    \"amount\":-1,\n" + 
				"    \"to_username\" : \"arunabh 2\"\n" + 
				"}")
				.header("Authorization", user.getId().toString())
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isBadRequest());
	}
	
	@Test
	public void test10Transfer2_thenStatus400() throws Exception{
		
		User user = userRepo.findUserByUsername("arunabh 1");
		
		mvc.perform(post("/transfer").content("{\n" + 
				"    \"amount\":1000000,\n" + 
				"    \"to_username\" : \"arunabh 2\"\n" + 
				"}")
				.header("Authorization", user.getId().toString())
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isBadRequest());
	}
	
	@Test
	public void test11Transfer_thenStatus401() throws Exception{
		
		User user = userRepo.findUserByUsername("arunabh 1");
		String token = user.getId().toString().replace("e", "f");
		mvc.perform(post("/transfer").content("{\n" + 
				"    \"amount\":100,\n" + 
				"    \"to_username\" : \"arunabh 2\"\n" + 
				"}")
				.header("Authorization", token)
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isUnauthorized());
	}
	
	@Test
	public void test12UserToptransaction_thenStatus200() throws Exception{
		
		User user = userRepo.findUserByUsername("arunabh 1");
		
		mvc.perform(get("/top_transactions_per_user")
				.header("Authorization", user.getId().toString())
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk());
	}
	
	@Test
	public void test13UserToptransaction_thenStatus401() throws Exception{
		
		User user = userRepo.findUserByUsername("arunabh 1");
		String token = user.getId().toString().replace("e", "f");
		mvc.perform(get("/top_transactions_per_user")
				.header("Authorization", token)
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isUnauthorized());
	}
	
	@Test
	public void test14OverallTopUsers_thenStatus200() throws Exception{
		
		User user = userRepo.findUserByUsername("arunabh 1");
		
		mvc.perform(get("/top_users")
				.header("Authorization", user.getId().toString())
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk());
	}
	
	@Test
	public void test15OverallTopUsers_thenStatus401() throws Exception{
		
		User user = userRepo.findUserByUsername("arunabh 1");
		String token = user.getId().toString().replace("e", "f");
		mvc.perform(get("/top_users")
				.header("Authorization", token)
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isUnauthorized());
	}
	

}
