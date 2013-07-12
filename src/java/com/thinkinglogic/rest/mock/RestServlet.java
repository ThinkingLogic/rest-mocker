package com.thinkinglogic.rest.mock;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Servlet implementation class RestServlet
 */
public class RestServlet extends HttpServlet {

	private static final Logger logger = Logger.getLogger(RestServlet.class);

	private static final long serialVersionUID = 1L;

	public static final String GET = "GET";
	public static final String PUT = "PUT";
	public static final String POST = "POST";
	public static final String DELETE = "DELETE";

	/**
	 * Default constructor.
	 */
	public RestServlet() {
		// nothing to do
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handleRequest(request, response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		handleRequest(request, response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPut(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handleRequest(request, response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doDelete(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		handleRequest(request, response);
	}

	/**
	 * Handles all request types in the same fashion.
	 * 
	 * @param request the current request.
	 * @param response the current response.
	 * @throws ServletException if a problem occurs.
	 * @throws IOException if a problem occurs.
	 */
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		logger.debug("Received request: " + request);
		String path = request.getPathInfo().substring(1);
		Map<String, String> queryParams = getQueryParams(request);
		Map<String, String> headers = getHeaders(request);
		String requestMethod = request.getMethod().toUpperCase();
		String body = getBody(request, requestMethod);

		logger.info("Received " + requestMethod + " request: path=/" + path);
		logger.info("queryString: " + request.getQueryString());
		logger.info("headers: " + headers);
		logger.info("body: " + body);

		ResponseBuilder builder = new ResponseBuilder(queryParams, headers, body, path, requestMethod,
				request.getContextPath());
		builder.handleResponse(response);
	}

	/**
	 * 
	 * @param request the current request.
	 * @param requestMethod the request method.
	 * @return the body of the request, as a string. Returns an empty json object for GET and DELETE requests.
	 */
	protected String getBody(final HttpServletRequest request, final String requestMethod) {
		StringBuffer buffer = new StringBuffer();
		String line = null;
		if (PUT.equalsIgnoreCase(requestMethod) || POST.equalsIgnoreCase(requestMethod)) {
			try {
				BufferedReader reader = request.getReader();
				while ((line = reader.readLine()) != null)
					buffer.append(line).append("\n");
			} catch (Exception e) {
				logger.error("Unable to read body of request: " + request, e);
				buffer.setLength(0);
			}
		}
		return buffer.toString();
	}

	/**
	 * @param request the current request.
	 * @return the query parameters, as a map.
	 */
	protected Map<String, String> getQueryParams(final HttpServletRequest request) {
		String queryString = notNullString(request.getQueryString());
		Map<String, String> queryParams = new TreeMap<>();
		String[] split = queryString.split("&");
		for (String string : split) {
			String[] strings = string.split("=");
			if (strings.length == 2) {
				queryParams.put(strings[0], strings[1]);
			}
		}
		return queryParams;
	}

	/**
	 * @param request the current request.
	 * @return the request headers, as a map.
	 */
	protected Map<String, String> getHeaders(HttpServletRequest request) {
		Map<String, String> headers = new TreeMap<>();
		@SuppressWarnings("rawtypes")
		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			final String header = notNullString(headerNames.nextElement());
			headers.put(header, request.getHeader(header));
		}
		return headers;
	}

	/**
	 * @param string the string to ensure is not null.
	 * @return returns the string, or empty string if null.
	 */
	protected String notNullString(final Object string) {
		if (string == null) {
			return "";
		}
		return string.toString();
	}
}
