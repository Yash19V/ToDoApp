package com.Yash.todo.repo;


import java.io.File;
import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import com.Yash.todo.dto.ToDoDTO;
import static com.Yash.todo.utilsfound.Constants.PATH;

//Singleton class - Design pattern to creates only one object
public class ToDoRepo implements IToDoRepo{

	private File file;
	private static ToDoRepo todorepo;
	
	private ToDoRepo() throws IOException {
		file = new File(PATH);
		file.createNewFile();
	}
	
	public static ToDoRepo getInstance() throws IOException {
		if (todorepo == null)
			todorepo = new ToDoRepo();
		return todorepo;
	}
	
	@Override
	public boolean writeTasks(ArrayList<ToDoDTO> tasks) throws IOException {
		FileOutputStream fo = null;
		ObjectOutputStream os = null;
		try {
			fo = new FileOutputStream(file);
			os = new ObjectOutputStream(fo);
			os.writeObject(tasks);
		}
		finally {
			if (os != null) os.close();
			if (fo != null)	fo.close();
		}	
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<ToDoDTO> readTasks() throws IOException, ClassNotFoundException {
		ArrayList<ToDoDTO> list = new ArrayList<>();
		try(FileInputStream fs = new FileInputStream(file)) {
			try(ObjectInputStream os = new ObjectInputStream(fs)) {
				list = (ArrayList<ToDoDTO>)os.readObject();
			}
		}
		return list;
	}

}