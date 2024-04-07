package com.epf.rentmanager.servlet.Client;

import com.epf.rentmanager.except.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
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

@WebServlet("/users/edit")
public class ClientEditServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Autowired
    ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/edit.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            int id = Integer.parseInt(request.getParameter("id"));

            String nom = request.getParameter("last_name");
            String prenom = request.getParameter("first_name");
            String email = request.getParameter("email");
            String datenaissance = request.getParameter("birth_date");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDate naissance = LocalDate.parse(datenaissance, formatter);

            boolean hasError = false;
            boolean isEmailAlreadyUsed = clientService.isEmailAlreadyUsed(email);

            if (nom == null || nom.length() < 3) {
                request.setAttribute("nomError", "Le nom du client doit comporter au moins 3 caractères.");
                hasError = true;
            } else {
                request.setAttribute("last_name", nom);
            }

            if (prenom == null || prenom.length() < 3) {
                request.setAttribute("prenomError", "Le prénom du client doit comporter au moins 3 caractères.");
                hasError = true;
            } else {
                request.setAttribute("first_name", prenom);
            }

            if (!email.matches("[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+")) {
                request.setAttribute("emailError", "Adresse email invalide.");
                hasError = true;
            } else {

                if (isEmailAlreadyUsed) {
                    request.setAttribute("emailError", "L'adresse e-mail est déjà utilisée par un autre client.");
                    hasError = true;
                } else {

                    request.setAttribute("email", email);
                }
            }

            LocalDate birthdate = LocalDate.parse(datenaissance, formatter);
            LocalDate today = LocalDate.now();
            if (today.minusYears(18).isBefore(birthdate)) {
                request.setAttribute("birthdateError", "Le client doit avoir au moins 18 ans.");
                hasError = true;
            } else {
                request.setAttribute("birth_date", datenaissance);
            }

            if (hasError) {
                request.getRequestDispatcher("/WEB-INF/views/users/edit.jsp").forward(request, response);
            } else {
                Client clientEdited= new Client(0, nom, prenom, email, naissance);

                clientService.edit(clientEdited, id);
                response.sendRedirect("/rentmanager/users");
            }

        } catch (ServiceException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
