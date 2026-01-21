package com.example.demo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@SpringBootApplication
@RestController
public class ProyectoUd5Application {

	private final JdbcTemplate jdbcTemplate;

	public ProyectoUd5Application(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	@GetMapping("/conexion")
	public String conexion() {
		return "Conexión establecida";
	}

	// Creacion de tablas e insert del admin
	@GetMapping("/crear")
	public String crearTabla() {
		// tabla de clientes
		jdbcTemplate.execute("DROP TABLE IF EXISTS clientes");
		jdbcTemplate.execute("DROP TABLE IF EXISTS productos");
		jdbcTemplate.execute("CREATE TABLE clientes (" + "id SERIAL PRIMARY KEY, " + "nombre VARCHAR(60), "
				+ "apellido VARCHAR(60), " + "contra VARCHAR(60), " + "saldo DOUBLE, "
				+ "admin BOOLEAN DEFAULT FALSE)");
		// tabla de productos
		jdbcTemplate.execute("CREATE TABLE productos (" + "id SERIAL PRIMARY KEY, " + "nombreProd VARCHAR(60), "
				+ "marca VARCHAR(60), " + "talla INT, " + "precioProd DOUBLE, " + "reservado BOOLEAN DEFAULT FALSE, "
				+ "urlImagen VARCHAR(255))");

		// creacion del primer administrador
		jdbcTemplate.update("INSERT INTO clientes (nombre, apellido, contra, saldo, admin) VALUES (?,?,?,?,?)",
				"admin1", "-", "admin1", 0.0, true);

		return ("Base de datos creada correctamente!");
	}

	// Inserts de las zapatillas que hay en stock
	@GetMapping("/productosIniciales")
	public String insertarProductosIniciales() {

		jdbcTemplate.update(
				"INSERT INTO productos (nombreProd, marca, talla, precioProd, reservado, urlImagen) VALUES (?,?,?,?,?,?)",
				"Nike Air Force 1", "Nike", 42, 110.0, false, "/img/airforce1.jpg");

		jdbcTemplate.update(
				"INSERT INTO productos (nombreProd, marca, talla, precioProd, reservado, urlImagen) VALUES (?,?,?,?,?,?)",
				"Nike Dunk Low", "Nike", 41, 115.0, false,
				"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSSNu_DJi72FXcMzVcGi7m8X1TFEmEnR8oeuA&s");

		jdbcTemplate.update(
				"INSERT INTO productos (nombreProd, marca, talla, precioProd, reservado, urlImagen) VALUES (?,?,?,?,?,?)",
				"Adidas Stan Smith", "Adidas", 42, 95.0, false,
				"https://cdn.grupoelcorteingles.es/SGFM/dctm/MEDIA03/202406/06/00117731204669____11__1200x1200.jpg");

		jdbcTemplate.update(
				"INSERT INTO productos (nombreProd, marca, talla, precioProd, reservado, urlImagen) VALUES (?,?,?,?,?,?)",
				"Adidas Superstar", "Adidas", 43, 105.0, false,
				"https://assets.adidas.com/images/w_600,f_auto,q_auto/9e7f2f7e61c6403bb4f4ae7c00c6d92a_9366/Superstar_Shoes_White_EG4958_01_standard.jpg");

		jdbcTemplate.update(
				"INSERT INTO productos (nombreProd, marca, talla, precioProd, reservado, urlImagen) VALUES (?,?,?,?,?,?)",
				"Puma RS-X", "Puma", 44, 120.0, false,
				"https://images.puma.com/image/upload/f_auto,q_auto,b_rgb:fafafa,w_2000,h_2000/global/395550/13/sv01/fnd/EEA/fmt/png/RS-X-Efekt-Youth-Sneakers");

		jdbcTemplate.update(
				"INSERT INTO productos (nombreProd, marca, talla, precioProd, reservado, urlImagen) VALUES (?,?,?,?,?,?)",
				"Puma Suede Classic", "Puma", 41, 90.0, false,
				"https://images.puma.com/image/upload/f_auto,q_auto,b_rgb:fafafa,w_2000,h_2000/global/352634/75/sv01/fnd/EEA/fmt/png/Suede-Classic-XXI-Sneakers");

		jdbcTemplate.update(
				"INSERT INTO productos (nombreProd, marca, talla, precioProd, reservado, urlImagen) VALUES (?,?,?,?,?,?)",
				"New Balance 550", "New Balance", 42, 130.0, false,
				"https://nb.scene7.com/is/image/NB/bb550pb1_nb_03_i?$dw_detail_main_lg$&bgc=f1f1f1&layer=1&bgcolor=f1f1f1&blendMode=mult&scale=10&wid=1600&hei=1600");

		jdbcTemplate.update(
				"INSERT INTO productos (nombreProd, marca, talla, precioProd, reservado, urlImagen) VALUES (?,?,?,?,?,?)",
				"New Balance 574", "New Balance", 43, 100.0, false,
				"https://nb.scene7.com/is/image/NB/ml574evw_nb_02_i?$dw_detail_main_lg$");

		jdbcTemplate.update(
				"INSERT INTO productos (nombreProd, marca, talla, precioProd, reservado, urlImagen) VALUES (?,?,?,?,?,?)",
				"Reebok Classic Leather", "Reebok", 41, 85.0, false,
				"https://assets.reebok.com/images/w_600,f_auto,q_auto/6e94f9d75b4f4dff9a2ead4f01010a68_9366/Classic_Leather_Shoes_White_49799_01_standard.jpg");

		jdbcTemplate.update(
				"INSERT INTO productos (nombreProd, marca, talla, precioProd, reservado, urlImagen) VALUES (?,?,?,?,?,?)",
				"Vans Old Skool", "Vans", 42, 75.0, false,
				"https://images.vans.com/is/image/Vans/VN000D3HY28-HERO?$583x583$");

		return "Productos iniciales insertados correctamente (10 zapatillas)";
	}

	// endpoint del registro de un cliente (he puesto el admin como false
	@GetMapping("/registro")
	public int registrarCliente(@RequestParam(value = "nombre", defaultValue = "-") String nombre,
			@RequestParam(value = "apellido", defaultValue = "-") String apellido,
			@RequestParam(value = "contra", defaultValue = "-") String contrasenya,
			@RequestParam(value = "saldo", defaultValue = "0") double saldo) {
		return jdbcTemplate.update("INSERT INTO clientes (nombre, apellido, contra, saldo, admin) VALUES (?,?,?,?,?)",
				nombre, apellido, contrasenya, saldo, false);
	}

	// registro para la consola (ADMIN)
	@GetMapping("/registroConsola")
	public int registrarClienteConsola(@RequestParam(value = "nombre", defaultValue = "-") String nombre,
			@RequestParam(value = "apellido", defaultValue = "-") String apellido,
			@RequestParam(value = "contra", defaultValue = "-") String contrasenya,
			@RequestParam(value = "saldo", defaultValue = "0") double saldo) {
		return jdbcTemplate.update("INSERT INTO clientes (nombre, apellido, contra, saldo, admin) VALUES (?,?,?,?,?)",
				nombre, apellido, contrasenya, saldo, true);
	}

	// El mapper para devolver las zapatillas
	public class ProductoMapper implements RowMapper<Producto> {
		@Override
		public Producto mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Producto(rs.getInt("id"), rs.getString("nombreProd"), rs.getString("marca"), rs.getInt("talla"),
					rs.getDouble("precioProd"), rs.getBoolean("reservado"), rs.getString("urlImagen"));
		}
	}

	// mapper de cliente

	public class ClienteMapper implements RowMapper<Cliente> {
		@Override
		public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Cliente(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido"),
					rs.getString("contra"), rs.getDouble("saldo"), rs.getBoolean("admin"));
		}
	}

	// endpoint que lista los productos para la web usando el mapper
	@GetMapping("/listarProductos")
	public List<Producto> listarProductos() {
		return jdbcTemplate.query("SELECT * FROM productos", new ProductoMapper());
	}

	// endpoint para el login
	@GetMapping("/login")
	public Cliente login(@RequestParam(value = "nombre", defaultValue = "-") String nombre,
			@RequestParam(value = "contra", defaultValue = "-") String contraseña) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM clientes WHERE nombre = ? AND contra = ?",
					new ClienteMapper(), nombre, contraseña);

		} catch (Exception e) {
			System.out.println("Error en el proceso del Login");
			return null;
		}

	}

	// busqueda por talla
	@GetMapping("/productos/talla")
	public List<Producto> busquedaTalla(@RequestParam(value = "talla", defaultValue = "-") int talla) {
		try {
			return jdbcTemplate.query("SELECT * FROM productos WHERE talla = ? AND reservado = false",
					new ProductoMapper(), talla);
		} catch (Exception e) {
			System.out.println("Error al buscar por talla");
			return null;
		}
	}

	// busqueda por marca
	@GetMapping("/productos/marca")
	public List<Producto> busquedaMarca(@RequestParam(value = "marca", defaultValue = "-") String marca) {
		try {
			return jdbcTemplate.query("SELECT * FROM productos WHERE marca = ? AND reservado = false",
					new ProductoMapper(), marca);

		} catch (Exception e) {
			System.out.println("Error al buscar por marca");
			return null;
		}

	}

	// busqueda por precio max NO SE SI ESTE ESTA BIEN DEL TODO
	@GetMapping("/productos/precio")
	public List<Producto> busquedaPrecio(@RequestParam(value = "precioMax", defaultValue = "130") double precioMax) {
		try {
			return jdbcTemplate.query("SELECT * FROM productos WHERE precioProd <= ? AND reservado = false",
					new ProductoMapper(), precioMax);
		} catch (Exception e) {
			System.out.println("Error al buscar por precio");
			return null;
		}
	}

	// busqueda por nombre

	@GetMapping("/productos/nombre")
	public List<Producto> busquedaNombre(@RequestParam(value = "nombreProd", defaultValue = "-") String nombre) {
		try {
			return jdbcTemplate.query("SELECT * FROM productos WHERE nombreProd LIKE ? AND reservado = false",
					new ProductoMapper(), "%" + nombre + "%");
		} catch (Exception e) {
			System.out.println("Error al buscar por nombre");
			return null;
		}
	}

	// busqueda de producto por id
	// endpoint para obtener un producto por ID
	@GetMapping("/productos/{id}")
	public Producto obtenerProductoPorId(@RequestParam(value = "id") int id) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM productos WHERE id = ?", new ProductoMapper(), id);
		} catch (Exception e) {
			System.out.println("Producto no encontrado con id: " + id);
			return null;
		}
	}

	// ahora el crud de clientes que solo se va a usar en consola
	// endpoint que lista a los clientes para la consola
	@GetMapping("/clientes/listar")
	public List<Cliente> listarClientes() {
		return jdbcTemplate.query("SELECT * FROM clientes", new ClienteMapper());
	}

	// endpoint para obtener el cliente desde la consola por ID
	@GetMapping("/clientes/{id}")
	public Cliente obtenerClientePorId(@RequestParam(value = "id") int id) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM clientes WHERE id = ?", new ClienteMapper(), id);
		} catch (Exception e) {
			System.out.println("Cliente no encontrado");
			return null;
		}
	}

	// endpoint para actualizar un cliente que solo se podra hacer por consola
	@GetMapping("/clientes/actualizar")
	public int actualizarCliente(@RequestParam(value = "id") int id, @RequestParam(value = "nombre") String nombre,
			@RequestParam(value = "apellido") String apellido, @RequestParam(value = "contra") String contrasenya,
			@RequestParam(value = "saldo") double saldo) {
		return jdbcTemplate.update("UPDATE clientes SET nombre = ?, apellido = ?, contra = ?, saldo = ? WHERE id = ?",
				nombre, apellido, contrasenya, saldo, id);
	}

	// endpoint de eliminar un cliente que solo lo podra hacer el admin desde la
	// consola
	@GetMapping("/clientes/borrar/{id}")
	public int borrarCliente(@RequestParam(value = "id") int id) {
		return jdbcTemplate.update("DELETE FROM clientes WHERE id = ?", id);
	}

	public static void main(String[] args) {
		// Proyecto UD5
		SpringApplication.run(ProyectoUd5Application.class, args);
	}

	// endpoints para el admin
	@GetMapping("/productos/añadir")
	public String añadirProducto(@RequestParam(value = "nombreProd") String nombre,
			@RequestParam(value = "marca") String marca, @RequestParam(value = "talla") int talla,
			@RequestParam(value = "precioProd") double precioProd,
			@RequestParam(value = "urlImagen") String urlImagen) {
		jdbcTemplate.update(
				"INSERT INTO productos (nombreProd, marca, talla, precioProd, reservado, urlImagen) VALUES (?,?,?,?,?,?)",
				nombre, marca, talla, precioProd, false, urlImagen);

		return "Producto '" + nombre + "' añadido correctamente";
	}

	@GetMapping("/productos/actualizar")
	public String editarProducto(@RequestParam(value = "idProd") int idProd,
			@RequestParam(value = "nombreProd") String nombre, @RequestParam(value = "marca") String marca,
			@RequestParam(value = "talla") int talla, @RequestParam(value = "precioProd") double precioProd,
			@RequestParam(value = "reservado") boolean reservado, @RequestParam(value = "urlImagen") String urlImagen) {
		jdbcTemplate.update(
				"UPDATE productos SET nombreProd = ?, marca = ?, talla = ?, precioProd = ?, reservado = ?, urlImagen = ? WHERE id = ?",
				nombre, marca, talla, precioProd, reservado, urlImagen, idProd);

		return "Producto '" + nombre + "' actualizado correctamente";
	}

	@GetMapping("/productos/eliminar/{id}")
	public String eliminarProducto(@RequestParam(value = "idProd") String idProd) {

		jdbcTemplate.update("DELETE FROM productos WHERE id = ?", idProd);

		return "Producto eliminado correctamente";
	}

	// endpoints para el filtro
	@GetMapping("/productos/disponibles")
	public List<Producto> productoDispo(@RequestParam(value = "reservado", defaultValue = "false") boolean reservado) {
		try {
			return jdbcTemplate.query("SELECT * FROM productos WHERE reservado = ?", new ProductoMapper(), reservado);
		} catch (Exception e) {
			System.out.println("Error en la busqueda de productos disponibles");
			return null;
		}

	}

	// endpoint para el filtro
	@GetMapping("/productos/filtro")
	public List<Producto> filtrarProductos(@RequestParam(value = "marca", defaultValue = "%") String marca,
			@RequestParam(value = "nombreProd", defaultValue = "%") String nombre,
			@RequestParam(value = "talla", defaultValue = "0") int talla,
			@RequestParam(value = "precioMax", defaultValue = "9999") double precioMax) {

		String sql = """
				SELECT * FROM productos
				WHERE reservado = false
				AND marca LIKE ?
				AND nombreProd LIKE ?
				AND (? = 0 OR talla = ?)
				AND precioProd <= ?
				""";

		return jdbcTemplate.query(sql, new ProductoMapper(), marca, "%" + nombre + "%", talla, talla, precioMax);
	}

}