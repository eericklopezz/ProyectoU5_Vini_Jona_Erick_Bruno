package com.example.demo;

public class Producto {

	private int id;
	private String nombreProd;
	private String marca;
	private int talla;
	private double precioProd;
	private boolean reservado;
	private String urlImagen;

	public Producto() {
		super();
	}

	public Producto(int id, String nombreProd, String marca, int talla, double precioProd, boolean reservado,
			String urlImagen) {
		super();
		this.id = id;
		this.nombreProd = nombreProd;
		this.marca = marca;
		this.talla = talla;
		this.precioProd = precioProd;
		this.reservado = reservado;
		this.urlImagen = urlImagen;
	}

	public Producto(int id, String nombreProd, String marca, int talla, double precioProd, String urlImagen) {
		super();
		this.id = id;
		this.nombreProd = nombreProd;
		this.marca = marca;
		this.talla = talla;
		this.precioProd = precioProd;
		this.urlImagen = urlImagen;
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
	 * @return the nombreProd
	 */
	public String getNombreProd() {
		return nombreProd;
	}

	/**
	 * @param nombreProd the nombreProd to set
	 */
	public void setNombreProd(String nombreProd) {
		this.nombreProd = nombreProd;
	}

	/**
	 * @return the marca
	 */
	public String getMarca() {
		return marca;
	}

	/**
	 * @param marca the marca to set
	 */
	public void setMarca(String marca) {
		this.marca = marca;
	}

	/**
	 * @return the talla
	 */
	public int getTalla() {
		return talla;
	}

	/**
	 * @param talla the talla to set
	 */
	public void setTalla(int talla) {
		this.talla = talla;
	}

	/**
	 * @return the precioProd
	 */
	public double getPrecioProd() {
		return precioProd;
	}

	/**
	 * @param precioProd the precioProd to set
	 */
	public void setPrecioProd(double precioProd) {
		this.precioProd = precioProd;
	}

	/**
	 * @return the reservado
	 */
	public boolean isReservado() {
		return reservado;
	}

	/**
	 * @param reservado the reservado to set
	 */
	public void setReservado(boolean reservado) {
		this.reservado = reservado;
	}

	public String getUrlImagen() {
		return urlImagen;
	}

	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}

}