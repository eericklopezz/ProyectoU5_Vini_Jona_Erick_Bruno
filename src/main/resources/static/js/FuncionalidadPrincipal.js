var marcaSeleccionada = "%";
var tallaSeleccionada = "0";
var listaMarcasElementos = [];

function crearTarjetaProducto(prod) {
    const card = document.createElement("a");
    card.href = "/html/PaginaProducto.html?id=" + encodeURIComponent(prod.id);
    card.className = "block max-w-sm p-6 border border-gray-200 rounded-lg shadow hover:bg-gray-100 flex flex-col h-full";

    const img = document.createElement("img");
    img.src = prod.urlImagen;
    img.alt = prod.nombreProd;
    img.className = "h-auto max-w-full rounded-base mb-4";

    const titulo = document.createElement("h5");
    titulo.className = "mb-3 text-2xl font-semibold tracking-tight leading-8";
    titulo.innerText = prod.nombreProd;

    const pMarca = document.createElement("p");
    pMarca.className = "text-black mt-auto";
    pMarca.innerText = prod.marca;

    const pPrecio = document.createElement("p");
    pPrecio.className = "text-gray-600";
    pPrecio.innerText = "Desde " + prod.precioProd + "$";

    card.appendChild(img);
    card.appendChild(titulo);
    card.appendChild(pMarca);
    card.appendChild(pPrecio);

    return card;
}

function manejarRespuesta(listaProductos) {
    const contenedor = document.getElementById("contenidoZapatillas");
    contenedor.innerHTML = "";

    if (listaProductos != null && listaProductos.length > 0) {
        for (var i = 0; i < listaProductos.length; i++) {
            const prod = listaProductos[i];
            const tarjeta = crearTarjetaProducto(prod);
            contenedor.appendChild(tarjeta);
        }
        ;
    } else {
        const mensaje = document.createElement("p");
        mensaje.className = "col-span-full text-center py-10 text-gray-500 font-bold";
        mensaje.innerText = "No se encontraron productos.";
        contenedor.appendChild(mensaje);
    }
}

function buscarPorNombre() {
    var nombreZapatilla = document.getElementById("inputNombre").value;

    fetch("http://localhost:8080/productos/nombre?nombreProd=" + nombreZapatilla)
        .then(response => response.json())
        .then(resObj => manejarRespuesta(resObj))
        .catch(error => console.error('Error en búsqueda por nombre:', error));
}

function buscarConFiltros() {
    var nombreZapatilla = document.getElementById("inputNombre").value;
    var precioMaximo = document.getElementById("sliderPrecio").value;
    var marcaCodificada = encodeURIComponent(marcaSeleccionada);

    fetch("http://localhost:8080/productos/filtro?nombreProd=" + nombreZapatilla +
        "&marca=" + marcaCodificada +
        "&talla=" + tallaSeleccionada +
        "&precioMax=" + precioMaximo)
        .then(response => response.json())
        .then(resObj => {
            manejarRespuesta(resObj);
        })
        .catch(error => console.error('Error en búsqueda por filtros:', error));
}

// Selección de Marcas
function seleccionarMarca() {
    const todasLasMarcas = document.querySelectorAll('.FiltroMarca li');
    todasLasMarcas.forEach(li => {
        li.style.backgroundColor = "transparent";
        li.style.color = "#475569";
    });
    this.style.backgroundColor = "#1E293B";
    this.style.color = "white";
    marcaSeleccionada = this.innerText.trim();
}

// Selección de Tallas
const listaTallas = document.querySelectorAll('.filtroZapatillas div');
listaTallas.forEach(div => {
    div.onclick = function () {
        listaTallas.forEach(t => {
            t.style.backgroundColor = "white";
            t.style.color = "black";
        });
        this.style.backgroundColor = "#1E293B";
        this.style.color = "white";
        tallaSeleccionada = this.innerText;
    };
});

// Slider de Precio
var slider = document.getElementById("sliderPrecio");
var precioLabel = document.getElementById("precioMax");

function actualizarPrecio() {
    precioLabel.innerHTML = this.value;
}

slider.oninput = actualizarPrecio;

// Carga Inicial de Marcas desde el Servidor
function cargarMarcasDinamicas() {
    fetch("http://localhost:8080/productos/marcasDisponibles")
        .then(response => response.json())
        .then(resObj => {
            const contenedorMarcas = document.querySelector('.FiltroMarca ul');

            // Limpiamos el contenedor (borra las marcas estáticas del HTML)
            contenedorMarcas.innerHTML = "";

            resObj.forEach(marca => {
                // 1. Crear el elemento de lista
                const li = document.createElement('li');

                // 2. Asignar clases de estilo
                li.className = "flex items-center cursor-pointer py-1.5 px-2.5 rounded-md bg-white align-middle select-none font-sans transition-all duration-300 ease-in text-slate-600 hover:bg-slate-800 hover:text-white shadow-lg hover:shadow-md";

                // 3. Asignar texto
                li.innerText = marca;

                // 4. Asignar evento de clic (usando tu función seleccionarMarca)
                li.onclick = seleccionarMarca;

                // 5. Inyectar en el DOM
                contenedorMarcas.appendChild(li);
            });
        })
        .catch(error => console.error('Error cargando marcas:', error));
}

function cargarProductosIniciales() {
    fetch("http://localhost:8080/listarProductos")
        .then(response => response.json())
        .then(resObj => manejarProductosIniciales(resObj))
        .catch(error => console.error('Error cargando productos iniciales:', error));
}

function manejarProductosIniciales(listaProductos) {
    const contenedor = document.getElementById("contenidoZapatillas");
    contenedor.innerHTML = "";
    if (listaProductos != null && listaProductos.length > 0) {
        for (var i = 0; i < listaProductos.length; i++) {
            const prod = listaProductos[i];
            const tarjeta = crearTarjetaProducto(prod);
            contenedor.appendChild(tarjeta);
        }
    }
}

document.addEventListener("DOMContentLoaded", cargarProductosIniciales);
document.addEventListener("DOMContentLoaded", cargarMarcasDinamicas);

function resetearFiltros() {
    marcaSeleccionada = "%";
    tallaSeleccionada = "0";
    document.getElementById("inputNombre").value = "";
    document.getElementById("sliderPrecio").value = 500;
    document.getElementById("precioMax").innerHTML = "500";
    document.getElementById("switchReservado").checked = false;
}