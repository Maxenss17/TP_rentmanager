package com.epf.rentmanager.servlet;

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

@WebServlet("/users/create")
public class ClientCreateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Autowired
    ClientService clientService;
    @Override
    public void init() throws ServletException {

        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
//        VehicleService vehicleService = context.getBean(VehicleService.class);

        try {
            String nom = request.getParameter("last_name");
            String prenom = request.getParameter("first_name");
            String email = request.getParameter("email");
            LocalDate naissance = LocalDate.parse(request.getParameter("birthday"));
            Client clientCreated = new Client(0, nom, prenom, email, naissance);
            clientService.create(clientCreated);

        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect("/rentmanager/users");
    }

}