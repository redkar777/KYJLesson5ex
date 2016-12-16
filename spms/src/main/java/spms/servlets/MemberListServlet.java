package spms.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.dao.MemberDao;
import spms.vo.Member;

// ServletContext�� ������ Connection ��ü ���  
@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			request.setCharacterEncoding("utf-8");

			ServletContext sc = this.getServletContext();
			Connection servletConn = (Connection) sc.getAttribute("conn"); 
			
			MemberDao memberDao = new MemberDao();
			//memberDao.connection ==null;
			memberDao.setConnection(servletConn);
			//memberDao.connection == serlvetConnection
			response.setContentType("text/html; charset=UTF-8");
			
			List<Member> members = memberDao.selectList();
			request.setAttribute("members", members);
			
			
			// request�� ȸ�� ��� ������ �����Ѵ�.
			
			
			// JSP�� ����� �����Ѵ�.
			RequestDispatcher rd = request.getRequestDispatcher(
					"/member/MemberList.jsp");
			rd.include(request, response);
			
			// �����ͺ��̽����� ȸ�� ������ ������ Member�� ��´�.
			// �׸��� Member��ü�� ArrayList�� �߰��Ѵ�.
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
			
		}
			
		}

	}
