package com.epf.rentmanager.servlet.Reservation;

import com.epf.rentmanager.except.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@WebServlet("/rents/edit")
public class ReservationEditServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            List<Client> clients = clientService.findAll();
            List<Vehicle> vehicles = vehicleService.findAll();

            request.setAttribute("clients", clients);
            request.setAttribute("vehicles", vehicles);

            request.getRequestDispatcher("/WEB-INF/views/rents/edit.jsp").forward(request, response);

        } catch (ServiceException e) {
            throw new ServletException("Error processing request", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            int id = Integer.parseInt(request.getParameter("id"));

            int clientId = Integer.parseInt(request.getParameter("client"));
            int vehicleId = Integer.parseInt(request.getParameter("car"));
            LocalDate startDate = LocalDate.parse(request.getParameter("begin"), DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            LocalDate endDate = LocalDate.parse(request.getParameter("end"), DateTimeFormatter.ofPattern("yyyy/MM/dd"));

            boolean hasError = false;
            boolean already_reserved = reservationService.isVehicleAlreadyReserved(vehicleId, startDate, endDate);
            boolean exceeds_seven =  ChronoUnit.DAYS.between(startDate, endDate) > 7;
            boolean exceeds_thirty = reservationService.validateReservationTotalExceedsThirtyDays(vehicleId, startDate, endDate);

            if (already_reserved) {

                request.setAttribute("vehicle_error", "Ce vehicule est deja reserve pour ces dates.");
                request.setAttribute("begin_error", "Ce vehicule est deja reserve pour ces dates.");
                request.setAttribute("end_error", "Ce vehicule est deja reserve pour ces dates.");
                hasError = true;

            }

            if (exceeds_seven) {

                request.setAttribute("begin_error_2", "La duree de la réservation ne peut pas dépasser 7 jours.");
                request.setAttribute("end_error_2", "La duree de la réservation ne peut pas dépasser 7 jours.");
                hasError = true;

            }

            if (exceeds_thirty) {

                request.setAttribute("vehicle_error_3", "Cette voiture ne peut pas prendre de réservation pendant plus de 30 jours consécutifs sans pause.");
                request.setAttribute("begin_error_3", "Cette voiture ne peut pas prendre de réservation pendant plus de 30 jours consécutifs sans pause.");
                request.setAttribute("end_error_3", "Cette voiture ne peut pas prendre de réservation pendant plus de 30 jours consécutifs sans pause.");
                hasError = true;

            }

            if (hasError) {
                request.setAttribute("clients", clientService.findAll());
                request.setAttribute("vehicles", vehicleService.findAll());
                request.getRequestDispatcher("/WEB-INF/views/rents/edit.jsp").forward(request, response);

            } else {

                Reservation reservationEdited = new Reservation(0, clientId, vehicleId, startDate, endDate);
                reservationService.edit(reservationEdited, id);
                response.sendRedirect(request.getContextPath() + "/rents");
            }

        } catch (ServiceException e) {
            throw new ServletException("Error processing request", e);
        }

    }
}
