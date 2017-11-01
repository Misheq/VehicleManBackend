package com.vehicleman.backend.security;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

import com.vehicleman.backend.entities.Manager;

public class BaseSecurityContext implements SecurityContext {

	private Manager manager;

	public BaseSecurityContext(Manager manager) {
		this.manager = manager;
	}

	@Override
	public Principal getUserPrincipal() {
		return manager;
	}

	@Override
	public boolean isUserInRole(String role) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSecure() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getAuthenticationScheme() {
		return SecurityContext.BASIC_AUTH;
	}

}
