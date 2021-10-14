package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
    private String title;
    private String desc;
    private String current_date;
    private String cate;
    private String due;
    private int id;
    private int is_completed;
    

    public TodoItem(String title, String desc, String cate, String due){
        this.title=title;
        this.desc=desc;   
        String pattern = "yyyy/MM/dd HH:mm:ss";
        SimpleDateFormat sim = new SimpleDateFormat(pattern);
        this.current_date=sim.format(new Date());;
        this.cate = cate;
        this.due = due;
 
    }
    
    public TodoItem(String title, String cate, String desc, String curr, String due){
        this.title=title;
        this.desc=desc;   
        String pattern = "yyyy/MM/dd HH:mm:ss";
        SimpleDateFormat sim = new SimpleDateFormat(pattern);
        this.current_date=sim.format(new Date());;
        this.cate = cate;
        this.due = due;
 
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }
    public String getcate() {
        return cate;
    }

    public void setcate(String cate) {
        this.cate = cate;
    }
    public String getdue() {
        return due;
    }

    public void setdue(String due) {
        this.due = due;
    }
    
   
    public void setiscom(int n) {
		this.is_completed = n;
	}
	public int getiscom() {
		
		return is_completed;
	}
    

    public String toString(){
    	if(is_completed == 0) {
    		return "[" + cate + "]" + title + "  -  " + desc + "  -  " + due + "  -  " + current_date;
    	}
    	return "[V][" + cate + "]" + title + "  -  " + desc + "  -  " + due + "  -  " + current_date;
    }

    public String toSaveString() {
    	return cate + "##" + title + "##" + desc + "##" + due + "##" + current_date + "\n";
    }

	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	

	
}
