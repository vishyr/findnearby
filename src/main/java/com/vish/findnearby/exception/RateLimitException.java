package com.vish.findnearby.exception;

/**
 * Rate limit exception when number of requests are exceeded
 * 
 * @author Vish
 *
 */
public class RateLimitException extends Exception
{

	private static final long serialVersionUID = 3809515403460335448L;

		public RateLimitException()
		{
		}

		public RateLimitException(String message)
		{
			super(message);
		}

		public RateLimitException(Throwable cause)
		{
			super(cause);
		}

		public RateLimitException(String message, Throwable cause)
		{
			super(message, cause);
		}
}