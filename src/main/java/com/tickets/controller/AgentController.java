package com.tickets.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.tickets.common.exceptions.InternalServerErrorException;
import com.tickets.common.exceptions.NotAcceptableException;
import com.tickets.common.exceptions.NotFoundException;
import com.tickets.common.validator.CommonValidator;
import com.tickets.entity.Agent;
import com.tickets.service.AgentService;

/** 
  * <h1>Controller to manage Agents</h1> 
  * Rest controller to manage and maintain api related to the agent management
  * "/rb" is the root mappling for this class
  * @author  Joby Chacko 
  * @version 1.0 
  * @since   2020-03-20 
  */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/rb")
public class AgentController {
	
	@Autowired
	private AgentService agentService;
	@Autowired
	private CommonValidator commonValidator;
	private Agent agent;
	private boolean validationStatus;
	private List<Agent> agentList;
	private HttpHeaders headers;
	

	/**
	 * Logback supports ERROR, WARN, INFO, DEBUG, or TRACE as logging level. 
	 * By default, logging level is set to INFO. 
	 * It means that code>DEBUG and TRACE messages are not visible.
	 */
    private static final Logger LOGGER=LoggerFactory.getLogger(AgentController.class);
	
	/** 
	  * This method is a dummy one to test application is running successfully  
	  * It doens't accept any parameters 
	  * @return string This returns "Hello World" as response 
	  */
	@RequestMapping("/hello")
	public String hello() {
		return "Hello Worlds";
	}
	
	/** 
	  * This method is used to handle the create agent POST request
	  * @param RequestBody Agents to get the properties related to agents
	  * @param UriComponentsBuilder builder to buld the right url back in the response
	  * @return ResponseEntity with created agent, headers and CREATED status. 
	  */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<Agent> createAgent(@RequestBody Agent agent, UriComponentsBuilder builder) {
		try {
			LOGGER.info("Inside the /create api");
			validationStatus = false;
			agent.setEnable(true);
			agent.setCreateDate(new Date().toString());
			validationStatus = agentService.validateCreateAgent(agent);
			if(!validationStatus) {
				throw new NotAcceptableException("Validation Failes while creating the Agent");
			}
			agent = agentService.createAgent(agent);
			if(null == agent) {
				throw new InternalServerErrorException("Something Went Wrong in creating the Agent - Agent created is null");
			}
			if(agent.getId() ==0) {
				throw new InternalServerErrorException("Something Went Wrong in creating the Agent - Agent created is null or not created");
			}
			HttpHeaders headers = new HttpHeaders();
	        return new ResponseEntity<Agent>(agent, headers, HttpStatus.CREATED);
		} catch (Exception e) {
			LOGGER.info("Something went wrong while creating an Agent: "+e.fillInStackTrace());
			LOGGER.debug("Exception while creating Agent: ", e.fillInStackTrace());
			throw e;
		}
	}
	
	/** 
	  * This method is used to handle the update agent PUT request
	  * @param RequestBody Agents to get the properties related to agents. Should have the ID to be updated
	  * @param UriComponentsBuilder builder to buld the right url back in the response
	  * @return ResponseEntity with created agent, headers and UPDATE status. 
	  */
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity<Agent> updateAgent(@RequestBody Agent agent, UriComponentsBuilder builder) {
		try {
			if(agent == null) {
				throw new IllegalArgumentException();
			}
			LOGGER.info("Updating the agent :"+agent.getId());
			validationStatus = agentService.validateUpdateAgent(agent);
			if(!validationStatus) {
				throw new NotAcceptableException("Validation Failes while updating the Agent");
			}
			agent = agentService.updateAgent(agent);
			if(null == agent) {
				throw new InternalServerErrorException("Something Went Wrong in updating the Agent - Agent updated is null");
			}
			headers = new HttpHeaders();
	        return new ResponseEntity<Agent>(agent, headers, HttpStatus.ACCEPTED);
		} catch(Exception e) {
				LOGGER.info("Something went wrong while updating Agent");
				LOGGER.debug("Exception while updating Agent", e.fillInStackTrace());
	        	throw e;
	        }
	}
	
	/** 
	  * This method is used to handle fetching the agent from the Database
	  * @param RequestParm agnetId will have the id of the agent to be fetched
	  * @param UriComponentsBuilder builder to buld the right url back in the response
	  * @return ResponseEntity with created agent, headers and DELETE status. 
	  */
	@RequestMapping(value = "/fetch", method = RequestMethod.GET)
	public ResponseEntity<Agent> fetchAgent(@RequestParam long agentId, UriComponentsBuilder builder) {
		try {
			if(agentId == 0) {
				throw new IllegalArgumentException("Agent ID Id is not defined");
			}
			LOGGER.info("Fetching the agent with Agent Id: "+agentId);
			agent = agentService.fetchAgent(agentId);
			if(null == agent) {
				throw new NotFoundException("Requested Agent is not available");
			}
			headers = new HttpHeaders();
		    return new ResponseEntity<Agent>(agent, headers, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.info("Something went wrong while fetching the agent");
			LOGGER.debug("Something went wrong while fetching the agent"+e.getMessage());
			throw e;
		}
	}
	
	
	/** 
	  * This method is used to handle the delete agent DELTE request
	  * @param RequestParm ID will have the id of the agent to be deleted
	  * @param UriComponentsBuilder builder to buld the right url back in the response
	  * @return ResponseEntity with created agent, headers and DELETE status. 
	  */
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteAgent(@RequestParam long deleteId, UriComponentsBuilder builder) {
		try {
			if(deleteId == 0) {
				throw new IllegalArgumentException("Delete Id is not defined");
			}			
			LOGGER.info("Deleting the specified agent");
			agentService.deleteAgent(deleteId);
			HttpHeaders headers = new HttpHeaders();
		    return new ResponseEntity<String>("Deleted the Agent", headers, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			LOGGER.info("Something went wrong while deleting the agent");
			LOGGER.debug("Something went wrong in deleting the agent"+e.getMessage());
			throw e;
		}
	}
	
	/** 
	  * This method is used to handle GET request for agents list for the like name search
	  * @param RequestParam String firstName used to accept the searched first name
	  * @return ResponseEntity with agents list, headers and OK status. 
	  */
	@RequestMapping(value = "/agents", method = RequestMethod.GET)
	public ResponseEntity<List<Agent>> agentsByName(@RequestParam String firstName) {
		try {
			LOGGER.info("Searchin Agents by Name");
			if(commonValidator.notEmpty(firstName, "Search Criteria") == null) {
				throw new IllegalArgumentException("Search criteria is not right");
			}
			agentList = agentService.agentListByName(firstName);
			if(null == agentList) {
				throw new InternalServerErrorException("Something went wrong while fetching the agents by name");
			}
			headers = new HttpHeaders();
	        return new ResponseEntity<List<Agent>>(agentList, headers, HttpStatus.OK);
		} catch(Exception e) {
			LOGGER.info("Something went wrong while searching the agent list");
			LOGGER.debug("Something went wrong while searching the agent list"+e.getMessage());
			throw e;
		}
	}
	
	/** 
	  * This method is used to handle GET request for agents list. Doesn't have any parameters
	  * This method will return all data from agent list
	  * @return ResponseEntity with agents list, headers and OK status. 
	  */
	@RequestMapping(value = "/agentsList", method = RequestMethod.GET)
	public ResponseEntity<List<Agent>> agentsList() {
		try {
			LOGGER.info("To List all agents");
			agentList = agentService.agentList();
			if(null == agentList) {
				LOGGER.info("Agent List returned was null");
				throw new InternalServerErrorException("Something went wrong while acceesing the agents");
			}
	        headers = new HttpHeaders();
	        return new ResponseEntity<List<Agent>>(agentList, headers, HttpStatus.OK);
		} catch(Exception e) {
			LOGGER.info("Something went wrong while finding the agent list");
			LOGGER.debug("Something went wrong while finding the agent list: "+e.getMessage());
			throw e;
		}
	}
}
