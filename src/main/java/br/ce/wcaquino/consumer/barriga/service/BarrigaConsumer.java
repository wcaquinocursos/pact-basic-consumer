package br.ce.wcaquino.consumer.barriga.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import br.ce.wcaquino.consumer.utils.RequestHelper;

@SuppressWarnings({"unchecked", "rawtypes"})
public class BarrigaConsumer {
	RequestHelper helper = new RequestHelper();
	private String baseUrl;

	public BarrigaConsumer(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String login(String user, String pass) throws IOException {
        Map resp = helper.post(baseUrl + "/signin", String.format("{\"email\": \"%s\", \"senha\": \"%s\"}", user, pass));
		return resp.get("token") != null? resp.get("token").toString(): null;
	}

	public List<Map> getAccounts(String token) throws IOException {
		List<Map> resp = helper.getAll(baseUrl + "/contas", token);
        for(Map account: resp) {
        	System.out.println(String.format("%s - %s", account.get("id"), account.get("nome")));
        }
        return resp;
	}
	

	public Map getAccount(String id, String token) throws IOException {
		Map resp = helper.get(baseUrl + "/contas/" + id, token);
        System.out.println(resp);
        return resp;
	}

	public String insertAccount(String name, String token) throws IOException {
		Map resp = helper.post(baseUrl + "/contas", String.format("{\"nome\": \"%s\"}", name), token);
        System.out.println(resp);
        return resp.get("id").toString();
	}
	
	public Map updateAccount(String id, String name, String token) throws IOException {
		Map resp = helper.put(baseUrl + "/contas/" + id, String.format("{\"nome\": \"%s\"}", name), token);
        System.out.println(resp);
        return resp;
	}
	
	public void deleteAccount(String id, String token) throws IOException {
		helper.delete(baseUrl + "/contas/" + id, token);
	}
	
	public void reset(String token) throws IOException {
		helper.get(baseUrl + "/reset", token);
	}
}

