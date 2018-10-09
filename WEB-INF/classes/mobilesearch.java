

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class mobilesearch
 */
@WebServlet("/mobilesearch")
public class mobilesearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public mobilesearch() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		response.setContentType("text/html");
		//String title = "Logging In";
		
		Connection connection;
		try {
			connection = DriverManager.getConnection("jdbc:mysql:///moviedb?autoReconnect=true&useSSL=false", "root", "database");
		} catch (SQLException e1) {
			System.out.println("Error connecting to server");
			return;
		}
		
		try {
			JSONObject json = new JSONObject();
			Statement select = connection.createStatement();
			Statement select2 = connection.createStatement();
			String query_string = request.getParameter("title");
			int show = 10;
			int currentPage = Integer.parseInt(request.getParameter("page"));
			
			int offset = (currentPage-1) * show;
			JSONArray array = new JSONArray();

//			String pagenum = request.getParameter("page")
			String totalQuery = "SELECT m.title FROM movies m WHERE MATCH (m.title) AGAINST ('" + query_string + "');";
			ResultSet sizeResult = select2.executeQuery(totalQuery);
			sizeResult.last();
			int size = sizeResult.getRow();
			array.put(Integer.toString(size));
			
			String query = "SELECT m.title FROM movies m WHERE MATCH (m.title) AGAINST ('" + query_string + "') LIMIT 10 OFFSET " +  offset + ";";
			ResultSet result = select.executeQuery(query);
			
//			ArrayList<String> titles = new ArrayList<String>)();
			
			while (result.next()) {
				//JSONObject item = new JSONObject();
				String mtitle = result.getString(1);
//				item.put("title", mtitle);

				array.put(mtitle);
//				titles.add(result.getString(1));
			}
//			
			try {
				json.put("movies", array);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String message = json.toString();
//			
			out.print(message);
//			String json = new Gson().toJson(titles);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
