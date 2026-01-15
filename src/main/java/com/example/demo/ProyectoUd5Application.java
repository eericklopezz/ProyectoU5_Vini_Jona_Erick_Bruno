package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ProyectoUd5Application {

	private final JdbcTemplate jdbcTemplate;

	public ProyectoUd5Application(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	@GetMapping("/crear")
	public String crearTabla() {
		// tabla de clientes
		jdbcTemplate.execute("DROP TABLE IF EXISTS clientes");
		jdbcTemplate.execute("DROP TABLE IF EXISTS productos");
		jdbcTemplate.execute("CREATE TABLE clientes ("
				+ "id SERIAL, nombre VARCHAR(60), apellido VARCHAR(60), contraseña VARCHAR(60), saldo DOUBLE, admin BOOLEAN DEFAULT FALSE");
		// tabla de productos
		jdbcTemplate.execute("CREATE TABLE productos ("
				+ "id SERIAL, nombreProd VARCHAR(60), marca VARCHAR(60), talla INT, precioProd DOUBLE, reservado BOOLEAN DEFAULT FALSE");

		// creacion del primer administrador
		jdbcTemplate.update(
				"insert into clientes (nombre,apellido,contraseña,saldo,admin) values (admin1, -, admin1, 0.0, TRUE)");

		return ("Base de datos creada correctamente!");
	}

	public static void main(String[] args) {
		// Proyecto UD5
		SpringApplication.run(ProyectoUd5Application.class, args);
	}

}
