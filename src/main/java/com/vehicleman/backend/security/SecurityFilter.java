package com.vehicleman.backend.security;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class SecurityFilter implements ContainerRequestFilter {

	private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
	private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";
	//	private static final String SECURED_URL_PREFIX = "persons";

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		//		// if you want to secure specific endpoints
		//		//		if (requestContext.getUriInfo().getPath().contains(SECURED_URL_PREFIX)) {
		//
		//		List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);
		//
		//		if (authHeader != null && !authHeader.isEmpty()) {
		//			String authToken = authHeader.get(0);
		//			authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
		//			String decodeString = Base64.decodeAsString(authToken);
		//			StringTokenizer tokenizer = new StringTokenizer(decodeString, ":");
		//			String username = tokenizer.nextToken();
		//			String password = tokenizer.nextToken();
		//
		//			// get the data from the database
		//
		//			// demo
		//			if ("user".equals(username) && "password".equals(password)) {
		//				return;
		//			}
		//		}
		//
		//		Response unatuhorizedStatus = Response.status(Response.Status.UNAUTHORIZED)
		//				.entity("{\"error\":\"User cannot access the source\"}").build();
		//
		//		requestContext.abortWith(unatuhorizedStatus);
		//		//		}
	}

}
