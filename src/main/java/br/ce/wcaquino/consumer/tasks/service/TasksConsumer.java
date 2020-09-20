package br.ce.wcaquino.consumer.tasks.service;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import br.ce.wcaquino.consumer.tasks.model.Task;
import br.ce.wcaquino.consumer.utils.RequestHelper;

@SuppressWarnings("unchecked")
public class TasksConsumer {
	private RequestHelper helper = new RequestHelper();
	private String tasksURL;

	public TasksConsumer(String tasksURL) {
		this.tasksURL = tasksURL;
	}
	
	public Task getTask(Long id) throws ClientProtocolException, IOException {
		Map<String, Object> response = helper.get(tasksURL + "/todo/" + id);
		if (response.keySet().size() == 0) return null;
		
		return new Task(Long.parseLong(response.get("id").toString()),
				response.get("task").toString(), response.get("dueDate").toString());
	}
	
	public Task saveTask(String task, String dueDate) throws ClientProtocolException, IOException {
		Map<String, Object> response = helper.post(tasksURL + "/todo", 
				String.format("{\"task\": \"%s\", \"dueDate\": \"%s\"}", task, dueDate));
		if (response.keySet().size() == 0) return null;
		
		return new Task(Long.parseLong(response.get("id").toString()),
				response.get("task").toString(), response.get("dueDate").toString());
	}
}

