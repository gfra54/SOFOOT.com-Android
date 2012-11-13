package com.sofoot.gateway;

public class GatewayException extends Exception
{

	private static final long serialVersionUID = -2957116585010683640L;

	public GatewayException(final String message) {
		super(message);
	}

	public GatewayException(final Throwable throwable) {
		super(throwable);
	}
}
