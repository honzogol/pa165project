package cz.fi.muni.pa165.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import cz.fi.muni.pa165.dto.UserDTO;
import cz.fi.muni.pa165.enums.UserRole;
import cz.fi.muni.pa165.facade.UserFacade;
import cz.fi.muni.pa165.rest.ApiUris;
import cz.fi.muni.pa165.rest.exceptions.PrivilegeException;
import cz.fi.muni.pa165.rest.exceptions.ResourceAlreadyExistingException;
import cz.fi.muni.pa165.rest.exceptions.ResourceNotFoundException;
import cz.fi.muni.pa165.rest.security.RoleResolver;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Users REST Controller
 *
 * @author Balcirak Peter <peter.balcirak@gmail.com>
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_USERS)
public class UsersController {

	private final static Logger log = LoggerFactory.getLogger(MonstersController.class);

	private final UserFacade userFacade;
	private final RoleResolver roleResolver;

	@Inject
	public UsersController(UserFacade monsterFacade, RoleResolver roleResolver) {
		this.userFacade = monsterFacade;
		this.roleResolver = roleResolver;
	}

	/**
	 * Get list of Users curl -i -X GET
	 * http://localhost:8080/pa165/rest/users
	 *
	 * @return list of UserDTOs
	 * @throws JsonProcessingException exception
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final List<UserDTO> getAllUsers(HttpServletRequest request) throws JsonProcessingException {

		log.debug("rest getAllUsers()");

		if(!roleResolver.hasRole(request, UserRole.ADMIN)) {
			throw new PrivilegeException("Not permitted.");
		}

		return userFacade.getAllUsers();
	}

	/**
	 * Get User by identifier id curl -i -X GET
	 * http://localhost:8080/pa165/rest/users/1
	 *
	 * @param id user identifier
	 * @return UserDTO
	 * @throws ResourceNotFoundException exception
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final UserDTO findUserById(@PathVariable("id") long id, HttpServletRequest request) throws Exception {

		log.debug("rest findUserById({})", id);

		if(!roleResolver.hasRole(request, UserRole.ADMIN) &&
				!roleResolver.isSelf(request, userFacade.findUserById(id)) ) {
			throw new PrivilegeException("Not permitted.");
		}

		UserDTO userDTO = userFacade.findUserById(id);
		if (userDTO == null){
			throw new ResourceNotFoundException("User not found");
		}
		return userDTO;
	}

	/**
	 * Get User by email curl -i -X GET
	 * http://localhost:8080/pa165/rest/users/filter?email=jozoraz@azet.sk
	 *
	 * @param email user email
	 * @return UserDTO
	 * @throws ResourceNotFoundException exception
	 */
	@RequestMapping(value = "/filter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final UserDTO findUserByEmail(@RequestParam("email") String email, HttpServletRequest request) throws Exception {

		log.debug("rest findUserByEamil({})", email);

		if(!roleResolver.hasRole(request, UserRole.ADMIN) &&
				!roleResolver.isSelf(request, userFacade.findUserByEmail(email)) ) {
			throw new PrivilegeException("Not permitted.");
		}

		UserDTO userDTO = userFacade.findUserByEmail(email);
		if (userDTO == null){
			throw new ResourceNotFoundException("User not found");
		}
		return userDTO;
	}

	/**
	 * Delete one user by id curl -i -X DELETE
	 * http://localhost:8080/pa165/rest/users/1
	 *
	 * @param id identifier of user
	 * @throws ResourceNotFoundException when user with given ID wasn't found
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public final void deleteUser(@PathVariable("id") long id, HttpServletRequest request) {

		log.debug("rest deleteUser({})", id);

		if(!roleResolver.hasRole(request, UserRole.ADMIN) &&
				!roleResolver.isSelf(request, userFacade.findUserById(id)) ) {
			throw new PrivilegeException("Not permitted.");
		}

		try {
			userFacade.deleteUser(id);
		} catch (Exception e) {
			throw new ResourceNotFoundException("User not found.");
		}
	}

	/**
	 * Register a new user by POST method
	 * curl -X POST -i -H "Content-Type: application/json" --data
	 * '{"firstName":"Lukas","lastName":"Novak","email":"lukas@novak.com"}'
	 * http://localhost:8080/pa165/rest/users/register?unencryptedPassword=0000
	 *
	 * @param userDTO with required fields for creation
	 * @param unencryptedPassword password
	 * @throws ResourceAlreadyExistingException when user with given email already exists
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public final void registerUser(@RequestBody UserDTO userDTO,
	                               @RequestParam("unencryptedPassword") String unencryptedPassword,
	                               HttpServletRequest request){

		log.debug("Rest registerUser ({}, {})" , userDTO, unencryptedPassword);

		if(!roleResolver.hasRole(request, UserRole.ADMIN)) {
			throw new PrivilegeException("Not permitted.");
		}

		UserDTO foundUser = userFacade.findUserByEmail(userDTO.getEmail());
		if (foundUser != null){
			throw new ResourceAlreadyExistingException("User already exists!");
		} else {
			userFacade.registerUser(userDTO, unencryptedPassword);
		}
	}

	/**
	 * Check if is user admin by his id curl -i -X GET
	 * http://localhost:8080/pa165/rest/users/isAdmin?id=1
	 * @param id user identifier
	 * @return true if is user admin, false otherwise
	 * @throws ResourceNotFoundException exception
	 */
	@RequestMapping(value = "/isAdmin", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final boolean isAdmin(@RequestParam("id") long id, HttpServletRequest request) throws Exception {

		log.debug("rest isAdmin({})", id);

		if(!roleResolver.hasRole(request, UserRole.ADMIN) &&
				!roleResolver.isSelf(request, userFacade.findUserById(id)) ) {
			throw new PrivilegeException("Not permitted.");
		}

		try {
			return userFacade.isAdmin(id);
		} catch(Exception e) {
			throw new ResourceNotFoundException("User not found");
		}
	}

	/**
	 * set Admin role to the user
	 * curl -i -X PUT -H
	 * "Content-Type: application/json"
	 * http://localhost:8080/pa165/rest/users/setAdmin?id=1
	 *
	 * @param id identified of the user
	 */
	@RequestMapping(value = "/setAdmin", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public final void setAdmin(@RequestParam("id") long id, HttpServletRequest request){
		log.debug("Rest setAdmin ({})", id);

		if(!roleResolver.hasRole(request, UserRole.ADMIN)) {
			throw new PrivilegeException("Not permitted.");
		}

		try {
			userFacade.setAdmin(id);
		} catch(Exception e) {
			throw new ResourceNotFoundException("User not found");
		}
	}

	/**
	 * set Regular role to the user
	 * curl -i -X PUT -H
	 * "Content-Type: application/json"
	 * http://localhost:8080/pa165/rest/removeAdmin?id=1
	 *
	 * @param id identified of the user
	 */
	@RequestMapping(value = "/removeAdmin", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public final void removeAdmin(@RequestParam("id") long id, HttpServletRequest request){
		log.debug("Rest removeAdmin ({})", id);

		if(!roleResolver.hasRole(request, UserRole.ADMIN)) {
			throw new PrivilegeException("Not permitted.");
		}

		try {
			userFacade.removeAdmin(id);
		} catch(Exception e) {
			throw new ResourceNotFoundException("User not found");
		}
	}
}
