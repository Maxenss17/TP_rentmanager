package com.epf.rentmanager.servlet.Vehicle;
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


@WebServlet("/vehicles/details")
public class VehicleDetailsServlet extends HttpServlet {

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
            Vehicle vehicle = vehicleService.findById(id);
            List<Reservation> reservations = reservationService.findResaByVehicleId(id);
            List<Client> clients_tenants = new ArrayList<>();

            for (Reservation reservation : reservations) {
                Client client = clientService.findById(reservation.getVehicle_id());
                clients_tenants.add(client);
            }

            request.setAttribute("users", clients_tenants);
            request.setAttribute("vehicle", vehicle);
            request.setAttribute("rents", reservations);

        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/details.jsp").forward(request, response);

    }
}