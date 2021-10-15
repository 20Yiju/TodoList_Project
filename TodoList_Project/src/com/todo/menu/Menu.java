package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("+++++Menu of ToDoList Program+++++");
        System.out.println("1. Add a new item ( add )                       2. Delete an existing item ( del )");
        System.out.println("3. Update an item  ( edit )                     4. Show all List items ( ls )");
        System.out.println("5. Show all Category ( ls_cate )                6. Find the item ( find )");
        System.out.println("7. Find the item by category ( find_cate )      8. Find the item by due_date ( find_due )");
        System.out.println("9. Sort the list by name ( ls_name_asc )        10. Sort the list by name ( ls_name_desc  )");
        System.out.println("11. Sort the list by date ( ls_date )           12. Sort the list by date ( ls_date_desc )");
        System.out.println("13. Change to complete ( comp )                 14. Sort the list by due_date ( ls_due_asc )");
        System.out.println("15. Sort the list by due_date ( ls_due_desc )   16. Show all completed list ( ls_comp )");
        System.out.println("17. Show all uncompleted list ( ls_uncomp )     18. Exit (Or press escape key to exit)");

        //System.out.println("Enter your choice >");
    }
    
    public static void prompt() {
    	System.out.print("\nEnter the Command :D > ");
    }
}
 