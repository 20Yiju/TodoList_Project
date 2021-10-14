package com.todo.service;

import java.util.*;
import java.io.*;
import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc, date, cate, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("ADD item You want!!\n"
				+ "Write the title of item to ADD > ");
		title = sc.next();
		
		if(list.isDuplicate(title)) {
			System.out.println("Title can not be duplicate!!");
			return;
		}
		
		sc.nextLine();
		System.out.println("Write the category of item to ADD > ");
		
		cate = sc.next();
		sc.nextLine();
		System.out.println("Write the due date of item to ADD > ");
		due_date = sc.next().trim();
		
		sc.nextLine();
		System.out.println("Write the description of title you wirte > ");
		desc = sc.nextLine().trim();
		
		
		TodoItem t = new TodoItem(title, desc, cate, due_date);
		if(list.addItem(t) > 0) {
			System.out.println("ADD successfully:D");
		}
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("DELETE item You Want!!\n"
				+ "Write the Number of item to DELETE > ");
		int ind = sc.nextInt();
		sc.nextLine();
		System.out.println("Are you sure to erase the above items? (y/n)");
		String yn = sc.next().trim();
		//if(l.deleteItem(ind) > 0) {
		//	System.out.println("DELETE successfully:D");
		//}
		if(yn.equals("y")) {
			if(l.deleteItem(ind) > 0) {
				System.out.println("DELETE successfully:D");
			}
			
		}
	}


	public static void updateItem(TodoList l) {
		String new_title, new_desc, new_date, new_cate, new_due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("EDIT item You Want!!\n"
				+ "Write the number of the item you want to EDIT > " );
		int ind = sc.nextInt();
		
		sc.nextLine();
		System.out.println("Write the NEW title of the item > ");
		new_title = sc.next().trim();
		
		sc.nextLine();
		System.out.println("Write the NEW category of the item > ");
		new_cate = sc.next().trim();
		sc.nextLine();
		
		System.out.println("Write the new description > ");
		String new_description = sc.nextLine().trim();
		
		sc.nextLine();
		System.out.println("Write the NEW due date of the item > ");
		String new_due = sc.next().trim();
		
		
		TodoItem t = new TodoItem(new_title, new_description, new_cate, new_due);
		t.setId(ind);
		if(l.updateItem(t) > 0) {
			System.out.println("item updated successfuly:D");
		}

	}

	public static void listAll(TodoList l) {

		System.out.println("Full list of the list:D , " + l.getCount() + " items are readed!!");
		for (TodoItem item : l.getList()) {
			System.out.println(item.getId() + item.toString());
		}
	}
	
	
	
	public static void saveList(TodoList l, String filename) {
		String path = "/Users/jeong-yiju/git/TodoListApp/" + filename;
		try {
			FileWriter fw = new FileWriter(path);
			for (TodoItem item : l.getList()) {
				fw.write(item.getId() + item.toSaveString());
			}
			System.out.println("All data has been successfully saved!:D");
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void loadList(TodoList l, String filename) {
		String path = "/Users/jeong-yiju/git/TodoListApp/" + filename;
		
		try {
			File f = new File(path);
			if(f.exists() == false) {
				System.out.println("The file doesn't exist...");
			}
			else {
				FileReader fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr);
				String line = "";
				int count = 0;
				while((line = br.readLine()) != null) {
					System.out.println(line);
					count++;
				}
				System.out.println(count + "items are readed!!");
				System.out.println("All data has been successfully loaded!:D");
				br.close();
				fr.close();
				
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public static void findList(TodoList l, String keyword) {
		
		int c = 0;
		
		for (TodoItem item : l.getList(keyword)) {
			System.out.println(item.getId() + item.toString());
			c++;
		}
		System.out.printf("%d items are founded:D\n", c);
		
	}
	
	
	public static void listCate(TodoList l) {
		
		Set<String> list =  new HashSet<>();
		
		for (TodoItem item : l.getList()) {
			String ca = item.getcate();
			list.add(ca);
			
		}
		
		int c = 0;
		
		for (String item : list) {
			if(c == 0) {
				System.out.print(item);
				c++;
			}
			else {
				System.out.print(" / " + item);
				c++;
			}
			
		}
		System.out.printf("\n %d items are exist!!\n", c);
	}

	public static void listCateAll(TodoList l) {
		int c = 0;
		for (String item : l.getCategories()) {
			System.out.println(item + " ");
			c++;
		}
		System.out.printf("\n%d items are founded:D\n", c);	
	}
	
	public static void findCateList(TodoList l, String cate) {
		int c = 0;
		for (TodoItem item : l.getListCategories(cate)) {
			System.out.println(item.getId() + item.toString());
			c++;
		}
		System.out.printf("\n%d items are founded:D\n", c);	
	}
	
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("\n%d items are exist:D\n", l.getCount());
		for (TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.println(item.getId() + item.toString());
		}
	}
	
	public static void completeItem(TodoList l, int id) {
		if(l.completeItem(id)) {
			System.out.println("Change to \"complete\" successfully:D\n");
		}
		else {
			System.out.println("ID doesn't exist...");
		}
	}
	public static void completelist(TodoList l) {
		int c = 0;
		for (TodoItem item : l.completelist()) {
			System.out.println(item.getId() + item.toString());
			c++;
		}
		System.out.printf("\n%d items are founded:D\n", c);	
	}
	
	
	

}
