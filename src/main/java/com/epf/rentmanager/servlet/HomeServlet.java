package com.epf.rentmanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;

import com.epf.rentmanager.except.ServiceException;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Autowired
	VehicleService vehicleService;
	@Override
	public void init() throws ServletException {

		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
//		VehicleService vehicleService = context.getBean(VehicleService.class);

		try {
			int vehicleCount = vehicleService.count();
			request.setAttribute("vehicleCount", vehicleCount);
		} catch (ServiceException e) {
			throw new RuntimeException(e);
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
	}
}
