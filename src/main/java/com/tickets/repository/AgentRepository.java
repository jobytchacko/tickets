package com.tickets.repository;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tickets.entity.Agent;

@Repository
public interface AgentRepository extends CrudRepository<Agent, Long>, AgentRepositoryCustom{
	

}
