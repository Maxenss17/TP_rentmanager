package com.epf.rentmanager.servlet.Reservation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.except.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


@WebServlet("/rents/details")
public class ReservationDetailsServlet extends HttpServlet {

    @Autowired
    ClientService clientService;
    @Autowired
    VehicleService vehicleService;
    @Autowired
    ReservationService reservationService;
    @Override
    public void init() throws ServletException {

        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)

            throws ServletException, IOException {

//        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
//        VehicleService vehicleService = context.getBean(VehicleService.class);

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            int clientId = Integer.parseInt(request.getParameter("client_id"));
            int vehicleId = Integer.parseInt(request.getParameter("vehicle_id"));

            Reservation reservation = reservationService.findById(id);
            Client client = clientService.findById(clientId);
            Vehicle vehicle = vehicleService.findById(vehicleId);

            request.setAttribute("user", client);
            request.setAttribute("rent", reservation);
            request.setAttribute("vehicle", vehicle);


        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/details.jsp").forward(request, response);

    }
}
