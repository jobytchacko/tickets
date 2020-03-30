package com.tickets.service;

import java.util.List;

import com.tickets.entity.Agent;
import com.tickets.repository.AgentRepository;

public interface AgentService {
		
	public boolean validateCreateAgent(Agent agent);
	
	public boolean validateUpdateAgent(Agent agent);
	
	public Agent createAgent(Agent agent);
	
	public List<Agent> agentListByName(String firstName);
	
	public List<Agent> agentList();
	
	public Agent updateAgent(Agent agent);
	
	public void deleteAgent(long deleteId);
	
	public Agent fetchAgent(long agentId);

	public void setAgentRepository(AgentRepository agentRepository);
	
	public String agentName(String testName);
	
}
