package com.epf.rentmanager.servlet.Client;
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


@WebServlet("/users")
public class ClientDetailsServlet extends HttpServlet {

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
            Client client = clientService.findById(id);
            List<Reservation> reservations = reservationService.findResaByClientId(id);
            List<Vehicle> vehiclesReserved = new ArrayList<>();

            for (Reservation reservation : reservations) {
                Vehicle vehicle = vehicleService.findById(reservation.getVehicle_id());
                vehiclesReserved.add(vehicle);
            }

            request.setAttribute("vehicles", vehiclesReserved);
            request.setAttribute("user", client);
            request.setAttribute("rents", reservations);


        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        request.getRequestDispatcher("WEB-INF/views/users/list.jsp").forward(request, response);

    }
}