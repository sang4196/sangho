package com.yorijory.webprj.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.yorijory.webprj.vo.Member;

public class MemberDao {
	
	public List<Member> getMembers() throws SQLException{
		return getMembers(1);
	}
	
	public List<Member> getMembers(int page) throws SQLException{
		
		int start = 1+(page-1)*10;  //1, 11, 21, 31, 41 an = a1+(n-1)d -> 1+(page-1)*10
		int end = page*10;  //10, 20, 30. 4-
		
		String sql = "SELECT * FROM("
				+ "SELECT ROW_NUMBER() OVER (ORDER BY REGDATE DESC) NUM, MEMBERS. * FROM MEMBERS) A "
				+ "WHERE NUM BETWEEN "+start+"AND " +end;
        
		//String url = "jdbc:oracle:thin:@211.238.142.251:1521:orcl";
		String url = "jdbc:sqlserver://211.238.142.251:1433;databaseName=edudb";

        Connection con = DriverManager.getConnection(url, "edu", "class2d");
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        
        List<Member> list = new ArrayList<Member>();
        Member member;
        
        while (rs.next()) {
           member = new Member();
           
           member.setMid(rs.getString("MID"));
           member.setName(rs.getString("NAME"));
           member.setPwd(rs.getString("PWD"));
           
           list.add(member);            
        }

        st.close();
        con.close();
        rs.close();
		
		return list;
	}
}
