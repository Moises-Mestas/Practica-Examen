package com.upeu.ms_cliente.util;

import com.upeu.ms_cliente.entity.Cliente;
import com.upeu.ms_cliente.repository.ClienteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ClienteSeeder implements CommandLineRunner {

    private final ClienteRepository clienteRepository;

    public ClienteSeeder(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void run(String... args) {
        if (clienteRepository.count() == 0) {
            Cliente c1 = new Cliente(null, "Carlos Pérez", "12345678", "Av. Los Olivos 123", "987654321");
            Cliente c2 = new Cliente(null, "Lucía Fernández", "87654321", "Jr. Las Flores 456", "912345678");
            Cliente c3 = new Cliente(null, "José Ramírez", "11223344", "Calle Primavera 789", "998877665");
            Cliente c4 = new Cliente(null, "María López", "44332211", "Av. El Sol 321", "911222333");
            Cliente c5 = new Cliente(null, "Pedro Castillo", "55667788", "Jr. Libertad 654", "944332211");

            clienteRepository.save(c1);
            clienteRepository.save(c2);
            clienteRepository.save(c3);
            clienteRepository.save(c4);
            clienteRepository.save(c5);

            System.out.println("Datos de clientes insertados correctamente.");
        } else {
            System.out.println("Los clientes ya existen, no se insertaron datos.");
        }
    }
}
