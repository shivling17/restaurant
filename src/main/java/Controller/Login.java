package Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dao.Mydao;
import Dto.Customer;

// Mapping the url
@WebServlet("/login")
public class Login extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //recieve values from frontend
		String email = req.getParameter("email");
		String password = req.getParameter("password");

        //verify if email exists
		Mydao dao = new Mydao();
		if (email.equals("admin@jsp.com") && password.equals("admin")) {
			resp.getWriter().print("<h1 style='color:green' > Login Successfull</h1>");
            // this is logic to send to next page
			req.getRequestDispatcher("adminhome.html").include(req, resp);
		} else {
			Customer customer = dao.fetchByEmail(email);
			if (customer == null) {
				resp.getWriter().print("<h1 style='color:brown'>Invalid Email</h1>");
				req.getRequestDispatcher("login.html").include(req, resp);
			} else {
				if (password.equals(customer.getPassword())) {
					resp.getWriter().print("<h1 style='color:blue'>Login Succesfull</h1>");
					req.getRequestDispatcher("CustomerHomePage.html").include(req, resp);
				} else {
					resp.getWriter().print("<h1 style='color:green'>Invalid Password</h1>");
					req.getRequestDispatcher("login.html").include(req, resp);
				}
			}

		}
	}
}
