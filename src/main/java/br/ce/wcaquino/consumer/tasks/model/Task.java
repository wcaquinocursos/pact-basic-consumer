package br.ce.wcaquino.consumer.tasks.model;

public class Task {
	private Long id;
	private String task;
	private String dueDate;

	public Task(Long id, String task, String dueDate) {
		this.id = id;
		this.task = task;
		this.dueDate = dueDate;
	}

	public Long getId() {
		return id;
	}

	public String getTask() {
		return task;
	}
	
	public String getDueDate() {
		return dueDate;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", task=" + task + ", dueDate=" + dueDate + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((task == null) ? 0 : task.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (dueDate == null) {
			if (other.dueDate != null)
				return false;
		} else if (!dueDate.equals(other.dueDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (task == null) {
			if (other.task != null)
				return false;
		} else if (!task.equals(other.task))
			return false;
		return true;
	}
	
	@Override
	public Task clone() {
		return new Task(id, task, dueDate);
	}
}
