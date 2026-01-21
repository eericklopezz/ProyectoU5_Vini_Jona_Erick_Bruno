document.addEventListener("DOMContentLoaded", () => {
    const params = new URLSearchParams(window.location.search);//Busca parámetros en la URL
    const idProducto = params.get("id");

    if (idProducto) {
        cargarDatosProducto(idProducto);
        cargarTallas(idProducto);
    }
});

function cargarDatosProducto(id) {
    fetch("http://localhost:8080/productos/id?id=" + encodeURIComponent(id))
        .then(response => response.json())
        .then(producto => {
            document.getElementById("producto-titulo").innerText = producto.nombreProd;
            document.getElementById("breadcrumb-nombre").innerText = producto.nombreProd;

            // 2. Precio (Asegúrate de que el nombre coincida con tu clase Java)
            document.getElementById("producto-precio").innerText = producto.precioProd + "$";

            // 3. Imagen
            const imgPrincipal = document.getElementById("producto-imagen");
            imgPrincipal.src = producto.urlImagen;
            imgPrincipal.alt = producto.nombreProd;

            // 4. Actualizar miniaturas automáticamente
            const miniaturas = document.querySelectorAll(".grid-cols-4 img");
            miniaturas.forEach(m => m.src = producto.urlImagen);
        })
        .catch(error => console.error("Error cargando producto:", error));
}


function cargarTallas(id) {
    const contenedorTallas = document.getElementById("contenedor-tallas");
    contenedorTallas.innerHTML = "";
    fetch("http://localhost:8080/productos/tallasDisponibles?id=" + encodeURIComponent(id))
        .then(response => response.json())
        .then(tallas => {
            for (var i = 0; i < tallas.length; i++) {
                const botonTalla = document.createElement("button");
                botonTalla.innerText = tallas[i];
                botonTalla.className = "border border-gray-300 rounded-md px-3 py-1 hover:bg-gray-200";
                contenedorTallas.appendChild(botonTalla);
            }
        })
        .catch(error => console.error("Error cargando tallas:", error));
}