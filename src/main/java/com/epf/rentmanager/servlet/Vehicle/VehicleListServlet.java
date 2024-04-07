package com.epf.rentmanager.servlet.Vehicle;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.config.AppConfiguration;
import com.epf.rentmanager.except.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


@WebServlet("/cars")
public class VehicleListServlet extends HttpServlet {

    @Autowired
    VehicleService vehicleService;
    @Override
    public void init() throws ServletException {

        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)

            throws ServletException, IOException {

//        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
//        VehicleService vehicleService = context.getBean(VehicleService.class);

        List<Vehicle> vehicles = null;

        try {
            vehicles = vehicleService.findAll();

        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }

        // Transmettre la liste des véhicules à la page JSP
        request.setAttribute("vehicles", vehicles);
        request.getRequestDispatcher("WEB-INF/views/vehicles/list.jsp").forward(request, response);
    }
}