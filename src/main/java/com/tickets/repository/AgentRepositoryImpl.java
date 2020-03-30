package com.tickets.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.tickets.entity.Agent;

/** 
 * <h1>Repository for the custom repository method implementations</h1> 
 * Methods that are not available in the Crud or JPA rep classes are created here
 * @author  Joby Chacko 
 * @version 1.0 
 * @since   2020-03-20 
 */
@Repository
public class AgentRepositoryImpl implements AgentRepositoryCustom {

   @PersistenceContext
   EntityManager entityManager;


    @Override
    public List<Agent> agentListByName(String firstName) {
        Query query = entityManager.createNativeQuery("SELECT em.* FROM agents as em " +

                "WHERE em.first_name LIKE ?", Agent.class);
        query.setParameter(1, firstName + "%");
        return query.getResultList();        
    }
    

}
