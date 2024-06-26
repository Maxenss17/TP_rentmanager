package com.epf.rentmanager.servlet.Vehicle;
import com.epf.rentmanager.except.ServiceException;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/vehicles/delete")
public class VehicleDeleteServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    @Autowired
    VehicleService vehicleService;
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)

            throws ServletException, IOException {
        try {
            String int_id = request.getParameter("id");
            int id = Integer.parseInt(int_id);

            vehicleService.delete(vehicleService.findById(id));
            request.setAttribute("vehicles", vehicleService.findAll());
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/list.jsp").forward(request, response);

        } catch (ServiceException e) {
            throw new ServletException();
        }
    }
}