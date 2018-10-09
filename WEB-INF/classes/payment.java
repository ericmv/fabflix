

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;
import java.util.Calendar;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import fabflix.Pair;


/**
 * Servlet implementation class payment
 */
@WebServlet("/payment")
public class payment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public payment() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		Context initCtx;
		Connection connection = null;
		try {
			initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource) envCtx.lookup("jdbc/write");
			try {
				connection = ds.getConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("hsdjfbsdskjfhsdj");
				e.printStackTrace();
			}
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String cardnum = request.getParameter("cardnum");
		String first_name = request.getParameter("first_name");
		String last_name = request.getParameter("last_name");
		String exp = request.getParameter("exp");
		
		String query = "SELECT * FROM creditcards c WHERE c.id = ? AND c.first_name = ? AND c.last_name = ? AND c.expiration = ?;";
		try {
			//Statement select = connection.createStatement();
			PreparedStatement payment = connection.prepareStatement(query);
			payment.setString(1, cardnum);
			payment.setString(2, first_name);
			payment.setString(3, last_name);
			payment.setString(4, exp);
//			ResultSet result = select.executeQuery("SELECT * FROM creditcards c WHERE c.id='" + cardnum + "' AND c.first_name = '" + first_name + "' AND "
//					+ "c.last_name = '" + last_name + "' AND c.expiration = '" + exp + "';");
			ResultSet result = payment.executeQuery();
			
			if (!result.next()) {
				request.setAttribute("errorMessage", "One or more fields does not match records");
				request.getRequestDispatcher("/info.jsp").forward(request, response);
			}
			else {
				Statement update = connection.createStatement();
				HttpSession session = request.getSession(false);
				String customer_id = (String) session.getAttribute("user");
//			    Date date = new Date();
				String cid = request.getParameter("cardnum");
				
				
				Calendar cal = Calendar.getInstance();
				int month = cal.get(cal.MONTH);
				month++;
				String month_str = Integer.toString(month);
				String day = Integer.toString(cal.get(cal.DATE));
				String year = Integer.toString(cal.get(cal.YEAR));
				
				String fullDate = year + "-" + month + "-" + day;
				
				
				
				@SuppressWarnings("unchecked")
				ArrayList<Pair<String, Integer>> cart = (ArrayList<Pair<String,Integer>>)session.getAttribute("cart");
				for (int i = 0; i < cart.size(); i++) {
					System.out.println("INSERT INTO sales (customer_id, movie_id, sale_date)"
							+ " VALUES(" + cid + ", " + cart.get(i).getFirst() + ", '2004/08/23');");
					update.execute("INSERT INTO sales (customer_id, movie_id, sale_date)"
							+ " VALUES(" + customer_id + ", " + cart.get(i).getFirst() + ", '" + fullDate + "');");
				}

				
				
				

				double price = (double) session.getAttribute("totalPrice");
				request.setAttribute("price", price);
				request.setAttribute("first_name", first_name);
				request.setAttribute("last_name", last_name);
				connection.close();

				request.getRequestDispatcher("/confirmation.jsp").forward(request, response);
				request.setAttribute("date", fullDate);
			}
		} catch (SQLException e1) {
			System.out.println("Error connecting to server");
			return;
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
