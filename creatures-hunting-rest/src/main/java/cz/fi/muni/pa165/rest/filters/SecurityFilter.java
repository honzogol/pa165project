package cz.fi.muni.pa165.rest.filters;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


import cz.fi.muni.pa165.dto.UserDTO;
import cz.fi.muni.pa165.facade.UserFacade;

import cz.fi.muni.pa165.rest.ApiUris;
import cz.fi.muni.pa165.rest.security.SecurityUtils;

import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author Vojtech Sassmann &lt;vojtech.sassmann@gmail.com&gt;
 */
@WebFilter(urlPatterns = {
		ApiUris.ROOT_AUTH + "/monsters/*",
		ApiUris.ROOT_AUTH + "/areas/*",
		ApiUris.ROOT_AUTH + "/weapons/*",
		ApiUris.ROOT_AUTH + "/users/*"})
public class SecurityFilter implements Filter {

	private static final String LOGIN_ERROR_MESSAGE = "{\"errors\":[\"User is not logged in.\"]}";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;


		if (request.getMethod().equals("OPTIONS")) {
			filterChain.doFilter(request, response);
			return;
		}

		Cookie[] cookies = request.getCookies();

		if (cookies == null) {
			filterChain.doFilter(request, response);
			sendUnauthError(response);
			return;
		}

		String token = null;

		for (Cookie cookie : cookies) {
			if (SecurityUtils.AUTH_COOKIE.equals(cookie.getName())) {
				token = SecurityUtils.decrypt(SecurityUtils.KEY, SecurityUtils.INIT_VECTOR, cookie.getValue());
			}
		}

		if (token == null) {
			filterChain.doFilter(request, response);
			sendUnauthError(response);
			return;
		}

		String[] data = token.split(";", 2);
		if (data.length != 2) {
			filterChain.doFilter(request, response);
			sendUnauthError(response);
			return;
		}

		long id;
		String email;

		try {
			id = Long.parseLong(data[0]);
		} catch (NumberFormatException e) {
			filterChain.doFilter(request, response);
			sendUnauthError(response);
			return;
		}
		email = data[1];

		UserFacade userFacade = WebApplicationContextUtils
				.getWebApplicationContext(servletRequest.getServletContext())
				.getBean(UserFacade.class);

		UserDTO user = userFacade.findUserById(id);
		if (!user.getEmail().equals(email)) {
			filterChain.doFilter(request, response);
			sendUnauthError(response);
			return;
		}

		request.setAttribute(SecurityUtils.AUTHENTICATE_USER, user);

		filterChain.doFilter(request, response);
	}

	private void sendUnauthError(HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		PrintWriter out = response.getWriter();
		out.print(LOGIN_ERROR_MESSAGE);
	}

	@Override
	public void destroy() {

	}
}
