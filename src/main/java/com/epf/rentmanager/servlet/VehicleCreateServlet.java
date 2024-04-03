package com.epf.rentmanager.servlet;

import com.epf.rentmanager.config.AppConfiguration;
import com.epf.rentmanager.except.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cars/create")
public class VehicleCreateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Autowired
    VehicleService vehicleService;
    @Override
    public void init() throws ServletException {

        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
//        VehicleService vehicleService = context.getBean(VehicleService.class);

        try {
            String constructeur = request.getParameter("manufacturer");
            String modele = request.getParameter("modele");
            String nbPlacesString = request.getParameter("seats");
            int nbPlaces = Integer.parseInt(nbPlacesString);
            Vehicle vehicleCreated = new Vehicle(0, constructeur, modele, nbPlaces);
            vehicleService.create(vehicleCreated);

        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect("/rentmanager/cars");
    }

}