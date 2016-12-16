package spms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import spms.vo.Member;

public class MemberDao {
	Connection connection;

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	public int MemeberInsert(Member member) throws Exception{
		PreparedStatement stmt = null;
		int memberCount = 0;
		try{
			
			
			stmt = connection.prepareStatement(
					"INSERT INTO MEMBERS(EMAIL,PWD,MNAME,CRE_DATE,MOD_DATE)"
					+ " VALUES (?,?,?,NOW(),NOW())");
			stmt.setString(1, member.getEmail());
			stmt.setString(2, member.getPassword());
			stmt.setString(3, member.getName());
			memberCount = stmt.executeUpdate();
			
			return memberCount;
		} catch(Exception e) {
			throw e;
		} finally{
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
		}
		
		
		
	}
	public List<Member> selectList() throws Exception{
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(
					"SELECT MNO,MNAME,EMAIL,CRE_DATE" + 
					" FROM MEMBERS" +
					" ORDER BY MNO ASC");
			ArrayList<Member> members = new ArrayList<Member>();
			
			
			while(rs.next()) {
				members.add(new Member()
							.setNo(rs.getInt("MNO"))
							.setName(rs.getString("MNAME"))
							.setEmail(rs.getString("EMAIL"))
							.setCreatedDate(rs.getDate("CRE_DATE"))	);
			}
			return members;	
						
		} catch(Exception e){
			throw e;
		} finally {
			try {if (rs != null) rs.close();} catch(Exception e) {}
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
		}
			
		
			
	}
}