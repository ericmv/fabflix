

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fabflix.Pair;

/**
 * Servlet implementation class Checkout
 */
@WebServlet("/Checkout")
public class Checkout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Checkout() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String id = request.getParameter("id");
		HttpSession session = request.getSession(false);
		@SuppressWarnings("unchecked")
		ArrayList<Pair<String, Integer>> cart = (ArrayList<Pair<String, Integer>>) session.getAttribute("cart");
		
		if (request.getParameter("function").equals("remove")) {
			int index = 0;
			for (int i = 0; i < cart.size(); i++) {
				if (cart.get(i).getFirst().equals(id)) {
					index = i;
				}
			}
			cart.remove(index);
		}
		
		
		if (request.getParameter("function").equals("update")) {
			int count = Integer.parseInt(request.getParameter("count"));
			
			
			for (int i = 0; i < cart.size(); i++) {
				if (cart.get(i).getFirst().equals(id)) {
					cart.get(i).setSecond(count);
				}
			}
		}

		response.sendRedirect("/fabflix/cart.jsp");
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
