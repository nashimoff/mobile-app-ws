package com.appsdeveloperblog.app.ws.io.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.appsdeveloperblog.app.ws.io.entity.AddressEntity;
import com.appsdeveloperblog.app.ws.io.entity.UserEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserRepositoryTest {
	
	@Autowired
	UserRepository userRepository;

	@BeforeEach
	void setUp() throws Exception {
		
//		Prepare User Entity
		UserEntity userEntity = new UserEntity();
		userEntity.setFirstName("Sergey");
		userEntity.setLastName("Kargopolov");
		userEntity.setUserId("1a2b3c");
		userEntity.setEncryptedPassword("xxx");
		userEntity.setEmail("test@test.com");
		userEntity.setEmailVerificationStatus(true);
		
//		Prepare User Addresses
		AddressEntity addressEntity = new AddressEntity();
		addressEntity.setType("shipping");
		addressEntity.setAddressId("ahgyt74hfy");
		addressEntity.setCity("Vancouver");
		addressEntity.setCountry("Canada");
		addressEntity.setPostalCode("ABCCDA");
		addressEntity.setStreetName("123 Street Address");
		
		List<AddressEntity> addresses = new ArrayList<>();
		addresses.add(addressEntity);
		
		userEntity.setAddresses(addresses);
		
		userRepository.save(userEntity);
		
		
//		Prepare User Entity
		UserEntity userEntity2 = new UserEntity();
		userEntity2.setFirstName("Sergey");
		userEntity2.setLastName("Kargopolov");
		userEntity2.setUserId("1a2b3cddddd");
		userEntity2.setEncryptedPassword("xxx");
		userEntity2.setEmail("test2@test.com");
		userEntity2.setEmailVerificationStatus(true);
		
//		Prepare User Addresses
		AddressEntity addressEntity2 = new AddressEntity();
		addressEntity2.setType("shipping");
		addressEntity2.setAddressId("ahgyt74hfywwww");
		addressEntity2.setCity("Vancouver");
		addressEntity2.setCountry("Canada");
		addressEntity2.setPostalCode("ABCCDA");
		addressEntity2.setStreetName("123 Street Address");
		
		List<AddressEntity> addresses2 = new ArrayList<>();
		addresses2.add(addressEntity2);
		
		userEntity2.setAddresses(addresses2);
		
		userRepository.save(userEntity2);
		
		
	}

	@Test
	final void testGetVerifiedUsers() {
		Pageable pageableRequest = PageRequest.of(1,1);
		Page<UserEntity>page = userRepository.findAllUsersWithConfirmedEmailAddress(pageableRequest);
		assertNotNull(page);
		
		List<UserEntity> userEntities = page.getContent();
		assertNotNull(userEntities);
		assertTrue(userEntities.size() == 1);
		
	}
	
	@Test
	final void testFindUserByFirstName()
	{
		String firstName="Sergey";
		List<UserEntity> users = userRepository.findUserByFirstName(firstName);
	}

}
