package cz.fi.muni.pa165.rest.security;

import cz.fi.muni.pa165.dto.UserDTO;
import cz.fi.muni.pa165.enums.UserRole;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Vojtech Sassmann &lt;vojtech.sassmann@gmail.com&gt;
 */
@Component
public class RoleResolverImpl implements RoleResolver {

	@Override
	public boolean hasRole(HttpServletRequest request, UserRole role) {

		UserDTO user = (UserDTO) request.getAttribute(SecurityUtils.AUTHENTICATE_USER);

		if (user == null || user.getRole() == null) {
			return false;
		}

		return user.getRole().equals(role);
	}

	@Override
	public boolean isSelf(HttpServletRequest request, UserDTO user) {
		UserDTO userFromRequest = (UserDTO) request.getAttribute(SecurityUtils.AUTHENTICATE_USER);

		return user.equals(userFromRequest);
	}
}
