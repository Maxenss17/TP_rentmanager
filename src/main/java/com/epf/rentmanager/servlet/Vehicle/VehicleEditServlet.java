package com.epf.rentmanager.servlet.Vehicle;
import com.epf.rentmanager.except.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/vehicles/edit")
public class VehicleEditServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Autowired
    VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/edit.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            int id = Integer.parseInt(request.getParameter("id"));
            String constructeur = request.getParameter("manufacturer");
            String modele = request.getParameter("modele");
            String nbPlacesString = request.getParameter("seats");
            int nb_places = Integer.parseInt(nbPlacesString);

            boolean hasError = false;

            if (constructeur == null || constructeur.isEmpty()) {
                request.setAttribute("manufacturerError", "Le constructeur est requis.");
                hasError = true;

            }  else {
                request.setAttribute("manufacturer", constructeur);
            }

            if (modele == null || modele.isEmpty()) {
                request.setAttribute("modeleError", "Le modèle est requis.");
                hasError = true;

            }  else {
                request.setAttribute("modele", modele);
            }

            if (nb_places < 2 || nb_places > 9) {
                request.setAttribute("seatsError", "Le nombre de places doit être compris entre 2 et 9.");
                hasError = true;

            }  else {
                request.setAttribute("seats", nb_places);
            }

            if (hasError) {
                request.getRequestDispatcher("/WEB-INF/views/vehicles/edit.jsp").forward(request, response);
            } else {
                Vehicle vehicleEdited = new Vehicle(0, constructeur, modele, nb_places);
                vehicleService.edit(vehicleEdited, id);
                response.sendRedirect("/rentmanager/cars");
            }

        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
}
