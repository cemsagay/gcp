package com.yanchware.gcp;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

	public String zone="";
	public String project= "";
	public String instanceId = "";

	@Test
	void createInstance() throws Exception {
		Object service;
		when(ServiceInstanceController.provision(zone,project,instanceId)).thenReturn(ResponseEntity.ok("Instance provisioned successfully."));
		this.mockMvc.perform(post("/gcp/"+zone+"/"+project+"/"+instanceId)).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Instance provisioned successfully.")));
	}
	@Test
	void startInstance() throws Exception {
		Object service;
		when(ServiceInstanceController.actions(zone,project,instanceId,"start")).thenReturn(ResponseEntity.ok("Instance start action completed successfully."));
		this.mockMvc.perform(post("/gcp/"+zone+"/"+project+"/"+instanceId+"/start")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Instance start action completed successfully.")));
	}
	@Test
	void stopInstance() throws Exception {
		Object service;
		when(ServiceInstanceController.actions(zone,project,instanceId,"stop")).thenReturn(ResponseEntity.ok("Instance stop action completed successfully."));
		this.mockMvc.perform(post("/gcp/"+zone+"/"+project+"/"+instanceId+"/stop")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Instance stop action completed successfully.")));
	}
	@Test
	void deleteInstance() throws Exception {
		Object service;
		when(ServiceInstanceController.deprovision(zone,project,instanceId)).thenReturn(ResponseEntity.ok("Instance deprovisioned successfully."));
		this.mockMvc.perform(delete("/gcp/"+zone+"/"+project+"/"+instanceId)).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Instance deprovisioned successfully.")));
	}

}
