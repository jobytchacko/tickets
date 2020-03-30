package com.tickets.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.web.context.WebApplicationContext;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tickets.entity.Agent;
import com.tickets.service.AgentService;

//https://dzone.com/articles/unit-and-integration-tests-in-spring-boot-2
//Mokito Matechers
//https://www.javadoc.io/doc/org.mockito/mockito-core/1.10.19/org/mockito/Matchers.html
//https://www.journaldev.com/21876/mockito-argument-matchers-any-eq

@SpringBootTest
@RunWith(SpringRunner.class)
public class AgentControllerTest {
	
    private MockMvc mockMvc;
    private List<Agent> agentList;
    private List<Agent> emptyAgentList;
    private Agent agent;
    private Agent agentTwo;
    private Agent returnAgent;
    private Agent updateAgent;
    private Agent fetchAgent;

    @Autowired
    protected WebApplicationContext wac;

    @SuppressWarnings("unused")
	@Autowired
    private AgentController agentController;

    @MockBean
    private AgentService agentService;
    
    @Before
    public void setup() throws Exception {

        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    	agentList = new ArrayList<Agent>();
    	emptyAgentList = new ArrayList<Agent>();
    	agent = new Agent();
    	agentTwo = new Agent();
    	returnAgent = new Agent();
    	updateAgent = new Agent();
    	fetchAgent = new Agent();
    	agent.setFirstName("Joby");
    	agent.setEmail("jj@gamil.com");
    	agentTwo.setFirstName("Romina");
    	returnAgent.setId(2);
    	returnAgent.setFirstName("Joby");
    	returnAgent.setEmail("jj@gamil.com");
    	agentList.add(agent);
    	agentList.add(agentTwo);
    	updateAgent.setId(3);
    	updateAgent.setFirstName("Joby");
    	updateAgent.setEmail("jobychacko@gmail.com");
    	fetchAgent.setId(6);
    	fetchAgent.setFirstName("Joby");
    	fetchAgent.setEmail("joby@gmail.com");
    }
    
    @Test
    public void agentsList() throws Exception {
    	
    	Mockito.when(agentService.agentList()).thenReturn(agentList);  
        mockMvc.perform(get("/rb/agentsList").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$[0].firstName", is("Joby")))
        	.andExpect(jsonPath("$[1].firstName", is("Romina")));
    	
    	Mockito.when(agentService.agentList()).thenReturn(null);  
    	mockMvc.perform(get("/rb/agentsList").contentType(MediaType.APPLICATION_JSON))
          	.andExpect(status().isInternalServerError());
    	 
    	Mockito.when(agentService.agentList()).thenReturn(emptyAgentList);  
       	mockMvc.perform(get("/rb/agentsList").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }
    
    @Test
    public void agentListByName() throws Exception {
    	Mockito.when(agentService.agentListByName(anyString())).thenReturn(agentList);  
    	mockMvc.perform(get("/rb/agents").param("firstName", "Joby").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$[0].firstName", is("Joby")))
    	.andExpect(jsonPath("$[1].firstName", is("Romina")));

    	Mockito.when(agentService.agentListByName(anyString())).thenReturn(emptyAgentList);  
    	mockMvc.perform(get("/rb/agents").param("firstName", "Joby").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(0)))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    	
    	Mockito.when(agentService.agentListByName(anyString())).thenReturn(null);  
    	mockMvc.perform(get("/rb/agents").param("firstName", "Joby").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isInternalServerError());
        	
    }
    
    @Test
    public void creatAgent() throws Exception {  
    	Mockito.when(agentService.validateCreateAgent(anyObject())).thenReturn(true);
    	Mockito.when(agentService.createAgent(anyObject())).thenReturn(returnAgent);
    	mockMvc.perform(post("/rb/create")
    		.contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonString(agent)))
    		.andExpect(status().isCreated())
    		.andExpect(jsonPath("$.firstName", is("Joby")));
    	
    	Mockito.when(agentService.validateCreateAgent(anyObject())).thenReturn(false);
    	Mockito.when(agentService.createAgent(anyObject())).thenReturn(returnAgent);
    	mockMvc.perform(post("/rb/create")
    		.contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonString(agent)))
    		.andExpect(status().isNotAcceptable());
    	
    	Mockito.when(agentService.createAgent(anyObject())).thenReturn(null);
    	Mockito.when(agentService.validateCreateAgent(anyObject())).thenReturn(true);
    	mockMvc.perform(post("/rb/create")
    		.contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonString(agent)))
			.andExpect(status().isInternalServerError());
    }
    
    @Test
    public void updateAgent() throws JsonProcessingException, Exception {
    	Mockito.when(agentService.validateUpdateAgent(anyObject())).thenReturn(true);
    	Mockito.when(agentService.updateAgent(anyObject())).thenReturn(updateAgent);
    	mockMvc.perform(put("/rb/update")
    		.contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonString(updateAgent)))
    		.andExpect(status().isAccepted())
    		.andExpect(jsonPath("$.firstName", is("Joby")));
    	
    	Mockito.when(agentService.validateUpdateAgent(anyObject())).thenReturn(true);
    	Mockito.when(agentService.updateAgent(anyObject())).thenReturn(null);
    	mockMvc.perform(put("/rb/update")
    		.contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonString(updateAgent)))
    		.andExpect(status().isInternalServerError());
    	
    	Mockito.when(agentService.validateUpdateAgent(anyObject())).thenReturn(false);
    	Mockito.when(agentService.updateAgent(anyObject())).thenReturn(updateAgent);
    	mockMvc.perform(put("/rb/update")
    		.contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonString(updateAgent)))
    		.andExpect(status().isNotAcceptable());
    	
    	Mockito.when(agentService.validateUpdateAgent(anyObject())).thenReturn(false);
    	Mockito.when(agentService.updateAgent(anyObject())).thenReturn(null);
    	mockMvc.perform(put("/rb/update")
    		.contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonString(updateAgent)))
    		.andExpect(status().isNotAcceptable()); 	
    }
    
    @Test
    public void fetchAgent() throws Exception {
    	Mockito.when(agentService.fetchAgent(anyLong())).thenReturn(fetchAgent);
    	mockMvc.perform(get("/rb/fetch")
    			.param("agentId", "6")
    			.contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.firstName", is("Joby")));
    	
    	Mockito.when(agentService.fetchAgent(anyLong())).thenReturn(null);
    	mockMvc.perform(get("/rb/fetch")
    			.param("agentId", "6")
    			.contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isNotFound());
    	
    	Mockito.when(agentService.fetchAgent(anyLong())).thenReturn(fetchAgent);
    	mockMvc.perform(get("/rb/fetch")
    			.param("agentId", "0")
    			.contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isNotAcceptable());	
    	
    	mockMvc.perform(get("/rb/fetch")
    			.param("agentId", "")
    			.contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isNotAcceptable());
    }
    
    @Test
    public void deleteAgent() throws JsonProcessingException, Exception {
    	
    	mockMvc.perform(delete("/rb/delete").param("deleteId", "5")
    	.contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isAccepted());
    	
    	mockMvc.perform(delete("/rb/delete").param("deleteId", "0")
    	.contentType(MediaType.APPLICATION_JSON))
    	.andExpect(status().isNotAcceptable());
    	
    	mockMvc.perform(delete("/rb/delete").param("deleteId", "")
    	    	.contentType(MediaType.APPLICATION_JSON))
    	    	.andExpect(status().isNotAcceptable());
    }
    
  //Converts Object to Json String
    private String convertObjectToJsonString(Agent agent) throws JsonProcessingException{
    	 ObjectMapper Obj = new ObjectMapper(); 
    	 String jsonStr = null;
         try { 
             // get Oraganisation object as a json string 
             jsonStr = Obj.writeValueAsString(agent); 
             // Displaying JSON String 
             System.out.println(jsonStr); 
         } 
         catch (IOException e) { 
             e.printStackTrace(); 
         }
		return jsonStr;
    }

}
