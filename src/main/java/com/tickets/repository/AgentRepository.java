package com.tickets.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tickets.entity.Agent;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long>, AgentRepositoryCustom{
	

}
