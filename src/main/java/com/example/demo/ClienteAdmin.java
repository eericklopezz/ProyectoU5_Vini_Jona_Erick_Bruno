package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class ClienteAdmin {
	static String servidor = "http://10.1.192.24:8080"; // cambiar
	static HttpClient cliente = HttpClient.newHttpClient();
	static HttpRequest peticion;
	static HttpResponse<String> respuesta = null;

	private static final AtomicInteger contadorId = new AtomicInteger(1);

	

	private static void mostrarMenu() {
		System.out.println("-- MENU TIENDA ZAPATILLAS --");
		System.out.println("1. Insertar zapatillas");
		System.out.println("2. Mostrar zapatillas");
		System.out.println("3. Actualizar zapatillas");
		System.out.println("4. Borrar zapatillas");
		System.out.println("5. Salir");
	}

	private static void insertarZapatillas(Scanner sc) {
		System.out.println("=== INSERTAR ZAPATILLAS ===");

		int id = calcularId();
		String marca = leerTextoNoVacio(sc, "Introduzca la marca: ");
		String nombre = leerTextoNoVacio(sc, "Introduzca el modelo (nombre): ");
		int talla = leerEnteroConRango(sc, "Introduzca la talla (36-49): ", 36, 49);
		double precioProd = leerDoubleConRango(sc, "Introduzca el precio de producción (0.1 - 300): ", 0.1, 300);
		boolean reservado = leerBoolean(sc, "¿Están reservadas? (true/false): ");

		Producto producto = new Producto(id, marca, nombre, talla, precioProd, reservado);
		
		
		
		
		System.out.println("Zapatilla creada correctamente: " + producto);
		
		
		
	}

	private static int calcularId() {
		return contadorId.getAndIncrement();
	}

	private static String leerTextoNoVacio(Scanner sc, String mensaje) {
		System.out.print(mensaje);
		String texto = sc.nextLine().trim();

		while (texto.isEmpty()) {
			System.out.println("El valor no puede estar vacío.");
			System.out.print(mensaje);
			texto = sc.nextLine().trim();
		}
		return texto;
	}

	private static int leerEntero(Scanner sc, String mensaje) {
		System.out.print(mensaje);
		while (!sc.hasNextInt()) {
			System.out.println("Introduzca un número válido.");
			sc.next();
			System.out.print(mensaje);
		}
		int valor = sc.nextInt();
		sc.nextLine();
		return valor;
	}

	private static int leerEnteroConRango(Scanner sc, String mensaje, int min, int max) {
		int valor = leerEntero(sc, mensaje);
		while (valor < min || valor > max) {
			System.out.printf("El valor debe estar entre %d y %d.%n", min, max);
			valor = leerEntero(sc, mensaje);
		}
		return valor;
	}

	private static double leerDoubleConRango(Scanner sc, String mensaje, double min, double max) {
		double valor = leerDouble(sc, mensaje);
		while (valor < min || valor > max) {
			System.out.printf("El valor debe estar entre %.2f y %.2f.%n", min, max);
			valor = leerDouble(sc, mensaje);
		}
		return valor;
	}

	private static double leerDouble(Scanner sc, String mensaje) {
		System.out.print(mensaje);
		while (!sc.hasNextDouble()) {
			System.out.println("Introduzca un número válido.");
			sc.next();
			System.out.print(mensaje);
		}
		double valor = sc.nextDouble();
		sc.nextLine();
		return valor;
	}

	private static boolean leerBoolean(Scanner sc, String mensaje) {
		System.out.print(mensaje);
		while (!sc.hasNextBoolean()) {
			System.out.println("Introduzca true o false.");
			sc.next();
			System.out.print(mensaje);
		}
		boolean valor = sc.nextBoolean();
		sc.nextLine();
		return valor;
	}

	private static String endPoint(String url) {
		String contenido = null;
		try {
			peticion = HttpRequest.newBuilder().uri(URI.create(url)).build();
			respuesta = cliente.send(peticion, BodyHandlers.ofString());
			contenido = respuesta.body();
//			System.out.println(contenido);
		} catch (ConnectException e) { // si hay error de conexión
			System.out.println("ERROR DE CONEXIÓN. Intenta entrar más tarde");
			System.exit(-1);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return contenido;
	}

	// Método que hace una primera llamada para comprobar la conexión
	private static void conexion() {
		// Llama al método conexión del servidor
		String url = String.format(servidor + "/conexion");
		System.out.println(endPoint(url));
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int opcion;
		
		
		
		endPoint(servidor + "/crear");
		

		do {
			mostrarMenu();
			opcion = leerEntero(sc, "Seleccione una opción: ");

			switch (opcion) {
			case 1 -> insertarZapatillas(sc);
			case 2 -> System.out.println("Mostrando zapatillas...");
			case 3 -> System.out.println("Actualizando zapatillas...");
			case 4 -> System.out.println("Borrando zapatillas...");
			case 5 -> System.out.println("Saliendo del programa...");
			default -> System.out.println("Opción no válida.");
			}

			System.out.println();

		} while (opcion != 5);

		sc.close();
	}
}