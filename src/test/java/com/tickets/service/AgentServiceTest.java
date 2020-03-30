package com.tickets.service;

import static org.junit.Assert.assertEquals;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.Matchers.anyLong;
import static org.hamcrest.Matchers.*;

import com.tickets.common.exceptions.NotFoundException;
import com.tickets.entity.Agent;
import com.tickets.repository.AgentRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AgentServiceTest {
	
    @Autowired
	private AgentService agentService;
    
    	
    @MockBean
	private AgentRepository agentRepository;
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    	
	
	@Before
	public void beforTest() {		
	}
	
	@After
	public void afterTest() {
	}
	
	@Test
	public void agentList() {
	}
	
	@Test
	public void fetchAgent() {
		Agent fetchedAgent;
		Agent agent = new Agent();
		agent.setFirstName("Romina");
		agent.setId(3);
		Mockito.when(agentRepository.findOne(anyLong())).thenReturn(agent);    
		fetchedAgent = agentService.fetchAgent(3);
		assertEquals(agent.getFirstName(), fetchedAgent.getFirstName());
		Mockito.when(agentRepository.findOne(anyLong())).thenReturn(null);    
		thrown.expect(NotFoundException.class);
        thrown.expectMessage(is("Can't find the requested agent"));
		fetchedAgent = agentService.fetchAgent(3);
		thrown.expect(IllegalArgumentException.class);
		agentService.fetchAgent(-1);
		agentService.fetchAgent(0);
	}

}
