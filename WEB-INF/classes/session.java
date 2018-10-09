

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class session
 */
@WebServlet("/session")
public class session extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public session() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//	    PrintWriter out = response.getWriter();

	    HttpSession session = request.getSession(false);
	    String heading;
	    		

	    if (session == null) {
	    	//response.sendRedirect("/fabflix/index.jsp");
	    	request.getRequestDispatcher("/index.jsp").forward(request, response);
	    //  accessCount = new Integer(0);
	      //heading = "Welcome, Newcomer";
	    } else {
		    String user = (String) session.getAttribute("user");

	    	if (user == null) {
	    		System.out.println("null");
//	    		response.sendRedirect("/fabflix/index.jsp");
	    		request.setAttribute("user", user);
	    		request.getRequestDispatcher("/index.jsp").forward(request, response);
	    	}
	    	else {
	    		System.out.println("logged in");
	    	}
	    	System.out.println("hello world");
	    	System.out.println(request.getRequestedSessionId());
		  //  Integer accessCount = (Integer)session.getAttribute("accessCount");

	      heading = "Welcome Back";
	    //  accessCount = new Integer(accessCount.intValue() + 1);
		 //   session.setAttribute("accessCount", accessCount); 
		    System.out.println(heading);
//			session.invalidate();


	    }
	  //  out.println(heading);


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
