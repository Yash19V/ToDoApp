package com.Yash.todo.view;

import static com.Yash.todo.utilsfound.MessageReader.getValue;
import static com.Yash.todo.utilsfound.Constants.*;
import com.Yash.todo.dto.ToDoDTO;
import com.Yash.todo.repo.ToDoRepo;
import com.Yash.todo.repo.IToDoRepo;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ToDoView {
	
	private static Scanner sc = new Scanner(System.in);
	
	public static void addTask() {
		sc.nextLine();
		System.out.println(getValue("input.taskname"));
		String name = sc.nextLine();
		System.out.println(getValue("input.taskdesc"));
		String desc = sc.nextLine();
		ToDoDTO todo = new ToDoDTO(name,desc);
		String result = getValue("record.notadded");
		//IToDoRepo repo = new ToDoRepo();
		try {
			IToDoRepo repo = ToDoRepo.getInstance();
			ArrayList<ToDoDTO> tasks = null;
			try {
				tasks = repo.readTasks();
			} catch (EOFException e) {
				System.out.println("File is Empty, a New Record has been Added");
			}
			if (tasks != null && tasks.size() > 0) {
				tasks.add(todo);
			}
			else {
				tasks = new ArrayList<>();
				tasks.add(todo);
			}
			result = repo.writeTasks(tasks)?getValue("record.added"):getValue("record.notadded");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println(result);
	}
	
	public static void deleteTask() {
		String result = getValue("record.notdeleted");
		try {
			IToDoRepo repo = ToDoRepo.getInstance();
			ArrayList<ToDoDTO> tasks = null;
			try {
				tasks = repo.readTasks();
			} catch (EOFException e) {
				System.out.println("File is Empty, Nothing to Delete");
			}
			if (tasks != null && tasks.size() > 0) {
				sc.nextLine();
				System.out.println(getValue("delete.taskname"));
				String name = sc.nextLine();
				for (ToDoDTO task : tasks) {
					if (task.getName().equals(name)) {
						tasks.remove(task);
						result = getValue("record.deleted");
						repo.writeTasks(tasks);
						break;
					}
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println(result);
	}
	
	public static void updateTask() {
		String result = getValue("record.notupdated");
		try {
			IToDoRepo repo = ToDoRepo.getInstance();
			ArrayList<ToDoDTO> tasks = null;
			try {
				tasks = repo.readTasks();
			} catch (EOFException e) {
				System.out.println("File is Empty, Nothing to Update");
			}
			if (tasks != null && tasks.size() > 0) {
				sc.nextLine();
				System.out.println(getValue("input.taskname"));
				String name = sc.nextLine();
				for (ToDoDTO task : tasks) {
					if (task.getName().equals(name)) {
						outer:
							while(true) {
								result = getValue("record.notupdated");
								System.out.println(getValue("update.name"));
								System.out.println(getValue("update.desc"));
								System.out.println(getValue("update.date"));
								System.out.println(getValue("update.status"));
								System.out.println(getValue("update.exit"));
								System.out.println(getValue("choice"));
								int choice = sc.nextInt();
								sc.nextLine();
								switch (choice) {
									case UPDATE_NAME:
										System.out.println(getValue("update.taskname"));
										String taskname = sc.nextLine();
										task.setName(taskname);
										result = getValue("update.apply");
										break;
									case UPDATE_DESC:
										System.out.println(getValue("update.taskdesc"));
										String desc = sc.nextLine();
										task.setDecs(desc);
										result = getValue("update.apply");
										break;
									case UPDATE_DATE:
										task.setDate(new Date());
										System.out.println(getValue("update.newdate"));
										result = getValue("update.apply");
										break;
									case UPDATE_STATUS:
										if (task.getStatus().equals(PENDING)) {
											task.setStatus(COMPLETE);
										}
										else {
											task.setStatus(PENDING);
										}
										result = getValue("update.apply");
										System.out.println(getValue("update.changedstatus") + " " + task.getStatus());
										break;
									case UPDATE_EXIT:
										break outer;
									default:
										System.out.println(getValue("invalidchoice"));
								}
								System.out.println(result);
							}
						result = getValue("record.updated");
						repo.writeTasks(tasks);
						break;
					}
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println(result);
	}
	
	public static void searchTask() {
		String result = getValue("search.notfound");
		try {
			IToDoRepo repo = ToDoRepo.getInstance();
			ArrayList<ToDoDTO> tasks = null;
			try {
				tasks = repo.readTasks();
			} catch (EOFException e) {
				System.out.println("File is Empty, Nothing to Search");
			}
			if (tasks != null && tasks.size() > 0) {
				sc.nextLine();
				System.out.println(getValue("search.taskname"));
				String name = sc.nextLine();
				for (ToDoDTO task : tasks) {
					if (task.getName().equals(name)) {
						result = getValue("search.found");
						System.out.println(task);
						break;
					}
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println(result);
	}
	
	public static void printAllTask() {
		try {
			IToDoRepo repo = ToDoRepo.getInstance();
			ArrayList<ToDoDTO> tasks = repo.readTasks();
			for (ToDoDTO task : tasks) {
				System.out.println(task);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		outer:
			while(true) {
				System.out.println(getValue("addtask"));
				System.out.println(getValue("deletetask"));
				System.out.println(getValue("updatetask"));
				System.out.println(getValue("searchtask"));
				System.out.println(getValue("printtask"));
				System.out.println(getValue("exittask"));
				System.out.println(getValue("choice"));
				int choice = sc.nextInt();
				switch(choice) {
					case ADD_TASK:
						addTask();
						break;
					case DELETE_TASK:
						deleteTask();
						break;
					case UPDATE_TASK:
						updateTask();
						break;
					case SEARCH_TASK:
						searchTask();
						break;
					case PRINT_TASK:
						printAllTask();
						break;
					case EXIT_TASK:
						break outer;
					default:
						System.out.println(getValue("invalidchoice"));
				}
			}
	}
}