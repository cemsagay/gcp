package com.yanchware.gcp;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.yanchware.gcp.controller.ServiceInstanceController;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ServiceInstanceController.class)
class GcpApplicationTests {


	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ServiceInstanceController ServiceInstanceController;

	@Test
	void greetingShouldReturnDefaultMessage() throws Exception {
		Object service;
		when(ServiceInstanceController.actions("us-central1-c","bilsag","instance-20240711-135322","stop")).thenReturn(ResponseEntity.ok("Instance stop action completed successfully."));
		this.mockMvc.perform(post("/gcp/us-central1-c/bilsag/instance-20240711-135322/stop")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Instance stop action completed successfully.")));
	}

}
