package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		l.importData("todolist.txt");
		boolean isList = false;
		boolean quit = false;
		TodoUtil.loadList(l, "todolist.txt");
		Menu.displaymenu();
		do {
			Menu.prompt();
			isList = false;
			String choice = sc.nextLine();
			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;
			
			case "find":
				System.out.println("Write the word to search > ");
				String keyword = sc.nextLine().trim();
				TodoUtil.findList(l, keyword);
				break;
			
			case "find_cate":
				System.out.println("Write the word to search > ");
				String cate = sc.nextLine().trim();
				TodoUtil.findCateList(l, cate);
				break;
				
			case "ls_cate":
				TodoUtil.listCate(l);
				break;

			case "ls_name_asc":
		        System.out.println("This list is arranged in order of titles.");
				TodoUtil.listAll(l, "title", 1);
				break;

			case "ls_name_desc":
				System.out.println("This list is arranged in reverse order of titles.");
				TodoUtil.listAll(l, "title", 0);
				break;
				
			case "ls_date":
				System.out.println("This list is arranged by date.");
				TodoUtil.listAll(l, "title", 1);
				break;
				
			case "ls_date_desc":
				System.out.println("This list is arranged in reverse order of date.");
				TodoUtil.listAll(l, "title", 0);
				break;
			case "comp":
				System.out.println("Choose the id to change completed > ");
				int id = sc.nextInt();
				TodoUtil.completeItem(l, id);
				sc.nextLine();
				break;
			case "ls_comp":
				TodoUtil.completelist(l);
				
				break;
	
			case "help" :
				Menu.displaymenu();
				break; 

			case "exit":
				quit = true;
				break;

			default:
		        System.out.println("\nEnter the \"help\" if you want to see the menu again!!");
				//System.out.println("please choose one of menu!!");
				break;
			}
			
			if(isList) TodoUtil.listAll(l);
		} while (!quit);
		TodoUtil.saveList(l, "todolist.txt");
	}
}
