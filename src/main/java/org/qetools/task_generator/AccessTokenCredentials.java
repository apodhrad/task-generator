package org.qetools.task_generator;

import org.apache.http.HttpRequest;

import net.rcarz.jiraclient.ICredentials;
import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.RestClient;

public class AccessTokenCredentials implements ICredentials {

	private String accessToken;

	public AccessTokenCredentials(String accessToken) {
		this.accessToken = accessToken;
	}

	@Override
	public void initialize(RestClient client) throws JiraException {
	}

	@Override
	public void authenticate(HttpRequest req) {
		req.addHeader("Authentication", "Bearer " + accessToken);
	}

	@Override
	public String getLogonName() {
		return accessToken;
	}

	@Override
	public void logout(RestClient client) throws JiraException {
	}
}
