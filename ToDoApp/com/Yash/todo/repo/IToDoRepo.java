
package com.Yash.todo.repo;

import java.io.IOException;
import java.util.ArrayList;
import com.Yash.todo.dto.ToDoDTO;

public interface IToDoRepo {
	public boolean writeTasks(ArrayList<ToDoDTO> tasks) throws IOException;
	public ArrayList<ToDoDTO> readTasks() throws IOException, ClassNotFoundException;
}