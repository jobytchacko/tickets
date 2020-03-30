package com.tickets.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tickets.entity.Agent;

public interface AgentRepositoryCustom {
  //  public void agentList(String firstName);
	 public List<Agent> agentListByName(String firstName);
		

}
