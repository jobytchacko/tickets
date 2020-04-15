package com.tickets.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
  * For http status codes def: https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html
  * For pagination https://github.com/vijjayy81/spring-boot-jpa-rest-demo-filter-paging-sorting
  */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/rb")
public class AgentController {
	
	@Autowired
	private AgentService agentService;
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
	@RequestMapping("/")
	public String hello() {
		return "Hello RB";
	}
	
	/** 
	  * This method is a dummy one to test application is running successfully  
	  * It doens't accept any parameters 
	  * @return string This returns "Hello World" as response 
	  */
	@RequestMapping("/list/one")
	public String hellorb() {
		return "Hello /rb/list/one";
	}
	
	/** 
	  * This method is used to handle the create agent POST request
	  * @param RequestBody Agents to get the properties related to agents
	  * @param UriComponentsBuilder builder to buld the right url back in the response
	  * @return ResponseEntity with created agent, headers and CREATED status. 
	  */
	@PostMapping("/agents")
	public ResponseEntity<Agent> createAgent(@RequestBody Agent agent) {
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
	@PutMapping("/agents")
	public ResponseEntity<Agent> updateAgent(@RequestBody Agent agent) {
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
	@GetMapping("agents/{id}")
	public ResponseEntity<Agent> fetchAgent(@PathVariable long id) {
		try {
			if(id == 0) {
				throw new IllegalArgumentException("Agent ID Id is not defined");
			}
			LOGGER.info("Fetching the agent with Agent Id: "+id);
			agent = agentService.fetchAgent(id);
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
	@DeleteMapping("agents/{id}")
	public ResponseEntity<String> deleteAgent(@PathVariable long id) {
		try {
			if(id == 0) {
				throw new IllegalArgumentException("Delete Id is not defined");
			}			
			LOGGER.info("Deleting the specified agent");
			agentService.deleteAgent(id);
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
	@GetMapping("/agents")
	public ResponseEntity<List<Agent>> agentsByName(@RequestParam(value = "firstName", required = false, defaultValue = "") String firstName) {
		try {
			System.out.println("First name to search is "+firstName);
			LOGGER.info("Searchin Agents by Name");
			agentList = null;
			if(firstName.equals("")){
				agentList = agentService.agentList();
			} else {
				agentList = agentService.agentListByName(firstName);
			}
			if(null == agentList) {
				throw new InternalServerErrorException("Something went wrong while fetching the agents by name");
			}
	        return new ResponseEntity<List<Agent>>(agentList, HttpStatus.OK);
		} catch(Exception e) {
			LOGGER.info("Something went wrong while searching the agent list");
			LOGGER.debug("Something went wrong while searching the agent list"+e.getMessage());
			throw e;
		}
	}
	
	/** 
	  * This method is used to handle GET request for agents list for the like name search
	  * @param RequestParam String firstName used to accept the searched first name
	  * @return ResponseEntity with agents list, headers and OK status. 
	  */
	@GetMapping("/agentsList")
	public ResponseEntity<Page<Agent>> agentsList(@RequestParam(value = "pageNo", required = false, defaultValue = "0") String pageNo,
													@RequestParam(value = "pageSize", required = false, defaultValue = "5") String pageSize,
													@RequestParam(value = "sortBy", required = false, defaultValue = "firstName") String sortBy) {
		try {
			
			Page<Agent> agentList = agentService.agentList(Integer.parseInt(pageNo), Integer.parseInt(pageSize), sortBy);
		
			if(null == agentList) {
				throw new InternalServerErrorException("Something went wrong while fetching the agents by name");
			}
	        return new ResponseEntity<Page<Agent>>(agentList, HttpStatus.OK);
		} catch(Exception e) {
			LOGGER.info("Something went wrong while searching the agent list");
			LOGGER.debug("Something went wrong while searching the agent list"+e.getMessage());
			throw e;
		}
	}
	
}
