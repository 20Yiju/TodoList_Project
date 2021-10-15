package com.todo.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.todo.service.DbConnect;
import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;

public class TodoList {
	private Connection conn;
	
	public TodoList() {
		this.conn = DbConnect.getConnection();
	}
		
	public void importData(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String sql = "insert into list (title, memo, category, current_date, due_date, is_completed, imp, etc)"
					+ " values (?,?,?,?,?,?,?,?);";
			
			int record = 0;
			while((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, "##");
				String category = st.nextToken();
				String imp = st.nextToken();
				String title = st.nextToken();
				String description = st.nextToken();
				String due_date = st.nextToken();
				String etc = st.nextToken();
				String current_date = st.nextToken();
				String is_comp = st.nextToken();
				int is_completed = Integer.parseInt(is_comp);
				
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, title);
				pstmt.setString(2, description);
				pstmt.setString(3, category);
				pstmt.setString(4, current_date);
				pstmt.setString(5, due_date);
				pstmt.setInt(6, is_completed);
				pstmt.setString(7, imp);
				pstmt.setString(8, etc);
				int count = pstmt.executeUpdate();
				if(count > 0) {
					record++;
				}
				pstmt.close();
			}
			System.out.println(record + " records read!!");
			br.close();
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int addItem(TodoItem t) {
		String sql = "insert into list (title, memo, category, current_date, due_date, is_completed, imp, etc)"
				+ " values (?,?,?,?,?,?,?,?);";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getcate());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getdue());
			pstmt.setInt(6, t.getiscom());
			pstmt.setString(7, t.getImp());
			pstmt.setString(8, t.getEtc());
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				String imp = rs.getString("imp");
				String etc = rs.getString("etc");

				TodoItem t = new TodoItem(title, description, category, due_date, imp, etc);
				t.setId(id);
				if(rs.getInt("is_completed") == 1) {
					t.setiscom(1);
				}
				else {
					t.setiscom(0);
				}
				t.setCurrent_date(current_date);
				list.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int getCount() {
		Statement stmt;
		int count = 0;
		
		try {
			stmt = conn.createStatement();
			String sql = "SELECT count(id) FROM list;";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(id)");
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
		
	}
	public int updateItem(TodoItem t) {
		String sql = "update list set title=?, memo=?, category=?, current_date=?, due_date=?, imp=?, etc=?"
				+ " where id = ?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getcate());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getdue());
			pstmt.setString(6, t.getImp());
			pstmt.setString(7, t.getEtc());
			pstmt.setInt(8, t.getId());
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public int deleteItem(String index) {
		
		String sql = "delete from list where id=?;";
		PreparedStatement pstmt;
		int count = 0;
		int n = 0;
		int idx =0;
		for(int j = 0; j < index.length(); j++) {
			if(index.charAt(j) == ',') {
				n += 1;
			}
		}
		try {
			if(index.contains(",")) {
				String[] ind = index.split(",");
				
				for(int i = 0; i < (n+1); i++) {
					idx = Integer.parseInt(ind[i]);
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, idx);
					count = pstmt.executeUpdate();
					pstmt.close();
				}
			}
			else {
				idx = Integer.parseInt(index);
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, idx);
				count = pstmt.executeUpdate();
				pstmt.close();
			}

			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	public ArrayList<TodoItem> getList(String keyword) {
		
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		keyword = "%" + keyword + "%";
		try {
			String sql = "SELECT * FROM list WHERE title like ? or memo like ? or category like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			pstmt.setString(3, keyword);
			ResultSet rs = pstmt.executeQuery();
			changetolist_Itemtype(list, rs);			
			pstmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<String> getCategories() {
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT DISTINCT category FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			String cate = rs.getString("category");
			list.add(cate);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;

	}
	
	public ArrayList<TodoItem> getListCategories(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql = "SELECT * FROM list WHERE category = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			ResultSet rs = pstmt.executeQuery();
			changetolist_Itemtype(list, rs);
			//String cate = rs.getString("category");
			//list.add(cate);
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;

	}
	
	public ArrayList<TodoItem> getListDuedates(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql = "SELECT * FROM list WHERE due_date = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			ResultSet rs = pstmt.executeQuery();
			changetolist_Itemtype(list, rs);

			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;

	}
	
	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list ORDER BY " + orderby;
			if(ordering == 0) {
				sql += " desc";
			}
			ResultSet rs = stmt.executeQuery(sql);
			changetolist_Itemtype(list, rs);			
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;

	}
	
	@SuppressWarnings("unused")
	private static void changetolist_Itemtype(ArrayList<TodoItem> list, ResultSet rs) throws SQLException {
		while(rs.next()) {
			TodoItem item = new TodoItem(
					rs.getString("title"),
					rs.getString("category"), 
					rs.getString("memo"), 
					rs.getString("current_date"),
					rs.getString("due_date"),
					rs.getString("imp"),
					rs.getString("etc")
			);
			int ind = rs.getInt("id");
			item.setId(ind);
			if(rs.getInt("is_completed") == 1) {
				item.setiscom(1);
			}
			else {
				item.setiscom(0);
			}
			
			list.add(item);
			
		}
	}
	
	public Boolean isDuplicate(String title) {
		String sql = "select count(id) from list where title = '" + title +"'";
		Statement stmt;
		int count = 0;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(id)");
			stmt.close();
			if(count > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean completeItem(String id) {
		String sql = "";
		Statement stmt;
		int count = 0;
		int idx = 0;
		int n = 0;
		for(int j = 0; j<id.length(); j++) {
			if(id.charAt(j) == ',') {
				n += 1;
			}
		}
		try {
			if(id.contains(",")) {
				String[] ind = id.split(",");
				for(int i = 0; i < (n+1); i++) {
					idx = Integer.parseInt(ind[i]);
					sql = "update list set is_completed = 1 where id = " + idx;
					stmt = conn.createStatement();
					count = stmt.executeUpdate(sql);
					stmt.close();
				}
			}
			else {
				idx = Integer.parseInt(id);
				sql = "update list set is_completed = 1 where id = " + idx;
				stmt = conn.createStatement();
				count = stmt.executeUpdate(sql);
				stmt.close();
			}
			

			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(count > 0) {
			return true;
		}
		return false;
		
	}
	
	public ArrayList<TodoItem> completelist() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list WHERE is_completed = 1";
			ResultSet rs = stmt.executeQuery(sql);
			changetolist_Itemtype(list, rs);			
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
		
	}
	
	public ArrayList<TodoItem> uncompletelist() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list WHERE is_completed = 0";
			ResultSet rs = stmt.executeQuery(sql);
			changetolist_Itemtype(list, rs);			
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
		
	}
	
}


