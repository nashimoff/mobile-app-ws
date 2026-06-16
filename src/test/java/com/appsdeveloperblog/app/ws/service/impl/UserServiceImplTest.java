package com.appsdeveloperblog.app.ws.service.impl;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.appsdeveloperblog.app.ws.io.repository.UserRepository;

class UserServiceImplTest {
	
	@Mock
	UserRepository userRepository;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	final void testGetUser() {
		when(userRepository.findByEmail(anyString())).thenReturn(null);
	}

}
