package com.example.demo;

public class Cliente {

	private int id;
	private String nombre;
	private String apellido;
	private String contraseña;
	private double saldo;
	private boolean admin;
	

	public Cliente() {
		super();
	}

	public Cliente(int id, String nombre, String apellido, String contraseña, double saldo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.contraseña = contraseña;
		this.saldo = saldo;
	}

	public Cliente(int id, String nombre, String apellido, String contraseña, double saldo, boolean admin) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.contraseña = contraseña;
		this.saldo = saldo;
		this.admin = admin;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the apellido
	 */
	public String getApellido() {
		return apellido;
	}

	/**
	 * @param apellido the apellido to set
	 */
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	/**
	 * @return the contraseña
	 */
	public String getContraseña() {
		return contraseña;
	}

	/**
	 * @param contraseña the contraseña to set
	 */
	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	/**
	 * @return the saldo
	 */
	public Double getSaldo() {
		return saldo;
	}

	/**
	 * @param saldo the saldo to set
	 */
	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	/**
	 * @return the admin
	 */
	public boolean isAdmin() {
		return admin;
	}

	/**
	 * @param admin the admin to set
	 */
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	/**
	 * @param saldo the saldo to set
	 */
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

}
