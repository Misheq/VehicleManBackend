package com.vehicleman.backend.security;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;

import com.vehicleman.backend.dao.ManagerDAO;
import com.vehicleman.backend.entities.Manager;

@Provider
public class SecurityFilter implements ContainerRequestFilter {

	private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
	private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";
	private static final String SECURED_URL_PREFIX = "auth/register";

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		//		 if you want to secure specific endpoints
		if (!requestContext.getUriInfo().getPath().contains(SECURED_URL_PREFIX)) {

			List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);

			if (authHeader != null && !authHeader.isEmpty()) {
				String authToken = authHeader.get(0);
				authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
				String decodeString = Base64.decodeAsString(authToken);
				StringTokenizer tokenizer = new StringTokenizer(decodeString, ":");
				String email = tokenizer.nextToken();
				String password = tokenizer.nextToken();

				// get the data from the database
				ManagerDAO managerDao = new ManagerDAO();
				Manager manager = managerDao.loginManager(email, password);

				if (manager != null) {
					requestContext.setSecurityContext(new BaseSecurityContext(manager));
					return;
				}
			}

			Response unatuhorizedStatus = Response.status(Response.Status.UNAUTHORIZED)
					.entity("{\"error\":\"User cannot access the source\"}").build();

			requestContext.abortWith(unatuhorizedStatus);
		}
	}

}
