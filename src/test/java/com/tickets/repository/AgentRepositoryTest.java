package com.tickets.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.tickets.entity.Agent;

//https://www.baeldung.com/spring-boot-testing
//https://github.com/pkainulainen/spring-data-jpa-examples/tree/master/integration-testing
//https://github.com/pkainulainen/spring-data-jpa-examples/tree/master/tutorial-part-seven


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class AgentRepositoryTest {

	@Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private AgentRepository agentRepository;
    
    
    @Test
    public void findByName() {
    	
    	System.out.println("To test the findbyname");
        // given
        Agent alex = new Agent();
        alex.setFirstName("Alex");
        alex.setEmail("alex@gmail.com");
        long id = (long) entityManager.persistAndGetId(alex);
        entityManager.flush();
        
        System.out.println("The id is -------"+id);
     
        // when
        Agent foundAgent = agentRepository.findOne(id);
     
        // then
        assertEquals(foundAgent.getFirstName(), alex.getFirstName());
        
    }
 
}
