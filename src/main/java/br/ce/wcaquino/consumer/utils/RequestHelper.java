package br.ce.wcaquino.consumer.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("rawtypes")
public class RequestHelper {

	public Map get(String url) throws ClientProtocolException, IOException {
		String reponse = Request.Get(url).execute().returnContent().asString();
		return getAsMap(reponse);
	}
	
	public Map post(String url, String body) throws ClientProtocolException, IOException {
		String reponse = Request.Post(url)
				.bodyString(body, ContentType.APPLICATION_JSON)
				.execute().returnContent().asString();
		return getAsMap(reponse);
	}
	
	public List getAll(String url, String token) throws ClientProtocolException, IOException {
		String reponse = Request.Get(url)
				.addHeader("Authorization", token)
				.execute().returnContent().asString();
		return getAsList(reponse);
	}
	
	public Map get(String url, String token) throws ClientProtocolException, IOException {
		String reponse = Request.Get(url)
				.addHeader("Authorization", token)
				.execute().returnContent().asString();
		return getAsMap(reponse);
	}
	
	public Map post(String url, String body, String token) throws ClientProtocolException, IOException {
		String reponse = Request.Post(url)
				.addHeader("Authorization", token)
				.bodyString(body, ContentType.APPLICATION_JSON)
				.execute().returnContent().asString();
		return getAsMap(reponse);
	}

	public Map put(String url, String body, String token) throws ClientProtocolException, IOException {
		String reponse = Request.Put(url)
				.addHeader("Authorization", token)
				.bodyString(body, ContentType.APPLICATION_JSON)
				.execute().returnContent().asString();
		return getAsMap(reponse);
	}

	public void delete(String url, String token) throws ClientProtocolException, IOException {
		Request.Delete(url)
			.addHeader("Authorization", token)
			.execute();
	}
	
	private Map getAsMap(String body) throws JsonMappingException, JsonProcessingException {
		if (body.isEmpty()) return new HashMap();
        return new ObjectMapper().readValue(body, HashMap.class);
	}
	
	private List getAsList(String body) throws IOException {
		if (body.isEmpty()) return new ArrayList();
		return new ObjectMapper().readValue(body, ArrayList.class);
	}
}
