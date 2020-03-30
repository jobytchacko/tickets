package com.tickets.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tickets.common.exceptions.InvalidFieldsException;
import com.tickets.common.exceptions.NotFoundException;
import com.tickets.common.validator.CommonValidator;
import com.tickets.entity.Agent;
import com.tickets.repository.AgentRepository;

/** 
 * <h1>Service for agent related handling</h1> 
 * Service that handles the specific needs of the Agent Related activities
 * "/rb" is the root mappling for this class
 * @author  Joby Chacko 
 * @version 1.0 
 * @since   2020-03-20 
 */
@Service
public class AgentServiceImpl implements AgentService {
	
	
	
	@Autowired
	private AgentRepository agentRepository;
	
	@Autowired
	private CommonValidator commonValidator;
	
	private Agent agent;
	
	/** 
	  * This method is to validate essential agent properties 
	  * It accept the Agent Objects
	  * It won't return anything. Throws exception if there is anything wrong
	  */
	@Override
	public boolean validateCreateAgent(Agent agent) {
		System.out.println("Validate Agents");
		ArrayList<String> invalidFields = new ArrayList<String>();
		boolean validationStatus = false;
		
		   if(agent == null) {
			   throw new NullPointerException();
		   }
		  try {
			  commonValidator.notEmpty(agent.getFirstName(), "First Name");
	        } catch (IllegalArgumentException | NullPointerException e) {
	        	invalidFields.add("First Name");
	        }
		  
		  try {
			  commonValidator.isValidEmail(agent.getEmail(), "Email");
			  
	        } catch (IllegalArgumentException | NullPointerException e) {
	        	invalidFields.add("Email");
	        }
		  
			if (!invalidFields.isEmpty()) {
				throw new InvalidFieldsException(invalidFields);
			} else{
				validationStatus = true;
			}
			return validationStatus;
	}
	
	/** 
	  * This method is to validate essential agent properties 
	  * It accept the Agent Objects
	  * It won't return anything. Throws exception if there is anything wrong
	  */
	@Override
	public boolean validateUpdateAgent(Agent agent) {
		System.out.println("Validate Update Agents");
		ArrayList<String> invalidFields = new ArrayList<String>();
		boolean validationStatus = false;
		validateCreateAgent(agent);
	  try {
		  commonValidator.isValidID(agent.getId(), "ID");
		  
        } catch (IllegalArgumentException | NullPointerException e) {
        	invalidFields.add("Id");
        }
	  
		if (!invalidFields.isEmpty()) {
			throw new InvalidFieldsException(invalidFields);
		} else{
			validationStatus = true;
		}
		return validationStatus;
	}
	
	@Override
	public Agent createAgent(Agent agent) {
		try {
			agent = agentRepository.save(agent);
		} catch (Exception e) {
			System.out.println("Erro in creating agent" +e.fillInStackTrace());
			throw e;
		}
		return agent;

	}
	
	public Agent updateAgent(Agent agent) {
		try {
			agent = agentRepository.save(agent);
			
		} catch (Exception e) {
			throw e;
		}
		return agent;
	}
	
	public void deleteAgent(long deleteId) {
		try {
			agentRepository.delete(deleteId);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public String agentName(String testName) {
		return "RST";
	}

	@Override
	public List<Agent> agentListByName(String firstName) {
		return agentRepository.agentListByName(firstName);
	}

	@Override
	public List<Agent> agentList() {
		return (List<Agent>) agentRepository.findAll();
	}

	@Override
	public Agent fetchAgent(long agentId) {
		try {
			commonValidator.isValidID(agentId, "Agent ID");
			agent = agentRepository.findOne(agentId);
			if(null == agent) {
				throw new NotFoundException("Can't find the requested agent");
	
			}
		} catch(Exception e) {
			throw e;
		}
    	return agent;
	}
	
	/**
     * This setter method should be used only by unit tests.
     * @param agentRepository
     */
    public void setAgentRepository(AgentRepository agentRepository) { 
        this.agentRepository = agentRepository;
    }

}
