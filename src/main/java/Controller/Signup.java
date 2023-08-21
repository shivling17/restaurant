package Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import Dao.Mydao;
import Dto.Customer;

//this is to map the action url to this class(should be same as action_and case sensitive)
@WebServlet("/signup")
@MultipartConfig
// make class as servlet class
public class Signup extends HttpServlet {
	@Override
	// when there is form we want data should be secure so we write doPost()
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// logic to receive value from frontend
		String fullname = req.getParameter("fullname");

		// password
		String password = req.getParameter("password");

		// mobile number
		long mobile = Long.parseLong(req.getParameter("mobile"));

		// gmail id
		String gmail = req.getParameter("email");

		// gender
		String gender = req.getParameter("gender");

		// country
		String country = req.getParameter("country");

		// dob
		LocalDate date = LocalDate.parse(req.getParameter("dob"));

		// calculating the age
		int age = Period.between(date, LocalDate.now()).getYears();

		// logic for storing the photo
		Part pic = req.getPart("picture");
		byte picture[] = null;
		picture = new byte[pic.getInputStream().available()];
		pic.getInputStream().read(picture);

		Mydao dao = new Mydao();

		// Logic to verify email and mobile number is not repeated
		if (dao.fetchByEmail(gmail) == null && dao.fetchByMobile(mobile) == null) {
			// loading values inside object
			Customer customer = new Customer();
			customer.setAge(age);
			customer.setCountry(country);
			customer.setDob(date);
			customer.setEmail(gmail);
			customer.setFullname(fullname);
			customer.setGender(gender);
			customer.setMobile(mobile);
			customer.setPassword(password);
			customer.setPicture(picture);

			// Persisting

			dao.save(customer);

			resp.getWriter().print("<h1 style='color:orange'>Account created successfully</h1>");
			req.getRequestDispatcher("login.html").include(req, resp);
		}

		else {
			resp.getWriter().print("<h1 style='color:red'> Email and mobile number should be unique </h1>");
			req.getRequestDispatcher("Signup.html").include(req, resp);
		}
	}
}
