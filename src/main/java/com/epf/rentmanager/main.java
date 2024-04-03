package com.epf.rentmanager;

import com.epf.rentmanager.except.ServiceException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.config.AppConfiguration;

public class main {

    public static void main(String[] args) throws ServiceException {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        VehicleService vehicleService = context.getBean(VehicleService.class);
        ClientService clientService = context.getBean(ClientService.class);

        System.out.println("Nombre de véhicules : " + vehicleService.count());
        System.out.println("Liste des clients : " + clientService.findAll());

    }
}
