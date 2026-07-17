package com.tp.jpa;

import com.tp.jpa.model.enums.EstadoPedido;
import com.tp.jpa.model.*;
import com.tp.jpa.model.enums.FormaPago;
import com.tp.jpa.model.enums.Rol;
import com.tp.jpa.repository.CategoriaRepository;
import com.tp.jpa.repository.PedidoRepository;
import com.tp.jpa.repository.ProductoRepository;
import com.tp.jpa.repository.UsuarioRepository;
import com.tp.jpa.util.JPAUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;

/**
 * Clase principal: menú de consola del sistema Food Store.
 * Orden de uso natural: Categorías -> Productos -> Usuarios -> Pedidos.
 */
public class Main {

    private static final Scanner sc = new Scanner(System.in);

    private static final CategoriaRepository categoriaRepo = new CategoriaRepository();
    private static final ProductoRepository productoRepo = new ProductoRepository();
    private static final UsuarioRepository usuarioRepo = new UsuarioRepository();
    private static final PedidoRepository pedidoRepo = new PedidoRepository();

    public static void main(String[] args) {

        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE); //remueve la informacion de Hibernate de la primera conexion

        boolean salir = false;
        while (!salir) {
            System.out.println();
            System.out.println("===== FOOD STORE - MENÚ PRINCIPAL =====");
            System.out.println("1. Gestionar Categorías");
            System.out.println("2. Gestionar Productos");
            System.out.println("3. Gestionar Usuarios");
            System.out.println("4. Gestionar Pedidos");
            System.out.println("5. Reportes");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            String op = sc.nextLine().trim();
            switch (op) {
                case "1": menuCategorias(); break;
                case "2": menuProductos(); break;
                case "3": menuUsuarios(); break;
                case "4": menuPedidos(); break;
                case "5": menuReportes(); break;
                case "0": salir = true; break;
                default: System.out.println("Opción inválida.");
            }
        }
        JPAUtil.close();
        System.out.println("Aplicación finalizada.");
    }

    // ── Submenús ─────────────────────────────────────────────────

    private static void menuCategorias() {

        // Opciones: 1-Alta  2-Modificar  3-Baja lógica  4-Listado  0-Volver
        boolean salir = false;
        while (!salir) {
            System.out.println();
            System.out.println("===== SUBMENÚ CATEGORÍAS =====");
            System.out.println("1. Alta Categoría");
            System.out.println("2. Modificar Categoría");
            System.out.println("3. Baja Lógica de Categoría");
            System.out.println("4. Listado de Categorías");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            String op = sc.nextLine().trim();
            switch (op) {
                case "1": System.out.println(">> Alta de categoria"); altaCategoria(); break;
                case "2": System.out.println(">> Modificar categoria"); modificarCategoria(); break;
                case "3": System.out.println(">> Baja logica de categoria"); bajaLogicaCategoria(); break;
                case "4": System.out.println(">> Listar categorias activas"); listarCategoriasActivas(); break;
                case "0": salir = true; break;
                default: System.out.println("Opción inválida.");
            }
        }
        
    }

    private static void menuProductos() {

        // Opciones: 1-Alta  2-Modificar  3-Baja lógica  4-Listado  0-Volver
        boolean salir = false;
        while (!salir) {
            System.out.println();
            System.out.println("===== SUBMENÚ PRODUCTOS =====");
            System.out.println("1. Alta Producto");
            System.out.println("2. Modificar Producto");
            System.out.println("3. Baja Lógica de Producto");
            System.out.println("4. Listado de Productos");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            String op = sc.nextLine().trim();
            switch (op) {
                case "1": System.out.println(">> Alta de producto"); altaProducto(); break;
                case "2": System.out.println(">> Modificar producto"); modificarProducto(); break;
                case "3": System.out.println(">> Baja logica de producto"); bajaLogicaProducto(); break;
                case "4": System.out.println(">> Listar productos activos"); listarProductosActivos(); break;
                case "0": salir = true; break;
                default: System.out.println("Opción inválida.");
            }
        }

    }

    private static void menuUsuarios() {

        // Opciones: 1-Alta  2-Modificar  3-Baja lógica  4-Listado  5-Buscar por mail  0-Volver
        boolean salir = false;
        while (!salir) {
            System.out.println();
            System.out.println("===== SUBMENÚ USUARIOS =====");
            System.out.println("1. Alta Usuario");
            System.out.println("2. Modificar Usuario");
            System.out.println("3. Baja Lógica de Usuario");
            System.out.println("4. Listado de Usuarios");
            System.out.println("5. Buscar por Mail");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            String op = sc.nextLine().trim();
            switch (op) {
                case "1": System.out.println(">> Alta de usuario"); altaUsuario(); break;
                case "2": System.out.println(">> Modificar usuario"); modificarUsuario(); break;
                case "3": System.out.println(">> Baja logica de usuario"); bajaLogicaUsuario(); break;
                case "4": System.out.println(">> Listar usuarios activos"); listarUsuariosActivos(); break;
                case "5": System.out.println(">> Buscar usuario por mail"); buscarUsuarioPorMail(); break;
                case "0": salir = true; break;
                default: System.out.println("Opción inválida.");
            }
        }

    }

    private static void menuPedidos() {

        // Opciones: 1-Alta  2-Cambiar estado  3-Baja lógica  4-Listado
        //           5-Por usuario  6-Por estado  0-Volver
        boolean salir = false;
        while (!salir) {
            System.out.println();
            System.out.println("===== SUBMENÚ PEDIDOS =====");
            System.out.println("1. Alta Pedido");
            System.out.println("2. Cambiar Estado");
            System.out.println("3. Baja Lógica de Pedido");
            System.out.println("4. Listado de Pedidos");
            System.out.println("5. Buscar por Usuario");
            System.out.println("6. Buscar por Estado");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            String op = sc.nextLine().trim();
            switch (op) {
                case "1": System.out.println(">> Alta de pedido"); altaPedido(); break;
                case "2": System.out.println(">> Cambiar estado de pedido"); cambiarEstadoPedido(); break;
                case "3": System.out.println(">> Baja lógica de pedido"); bajaLogicaPedido(); break;
                case "4": System.out.println(">> Listar pedidos activos"); listarPedidosActivos(); break;
                case "5": System.out.println(">> Buscar pedido por usuario"); buscarPedidoPorUsuario(); break;
                case "6": System.out.println(">> Buscar pedido por estado"); buscarPedidoPorEstado(); break;
                case "0": salir = true; break;
                default: System.out.println("Opción inválida.");
            }
        }
    }

    private static void menuReportes() {

        // Opciones: 1-Productos por categoría  2-Pedidos por usuario
        //           3-Pedidos por estado  4-Total facturado  0-Volver
        boolean salir = false;
        while (!salir) {
            System.out.println();
            System.out.println("===== SUBMENÚ REPORTES =====");
            System.out.println("1. Productos por categoría");
            System.out.println("2. Pedidos por usuario");
            System.out.println("3. Pedidos por estado");
            System.out.println("4. Total facturado");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            String op = sc.nextLine().trim();
            switch (op) {
                case "1": System.out.println(">> Productos por categoría"); productosPorCategoria(); break;
                case "2": System.out.println(">> Pedidos por usuario"); pedidosPorUsuario(); break;
                case "3": System.out.println(">> Pedidos por estado"); pedidosPorEstado(); break;
                case "4": System.out.println(">> Total facturado"); totalFacturado(); break;
                case "0": salir = true; break;
                default: System.out.println("Opción inválida.");
            }
    }
    }

    private static class ItemPedidoTemporal {
        private final Long productoId;
        private final String nombre;
        private final int cantidad;
        private final double precioUnitario;
        private final double subtotal;

        private ItemPedidoTemporal(Long productoId, String nombre, int cantidad, double precioUnitario, double subtotal) {
            this.productoId = productoId;
            this.nombre = nombre;
            this.cantidad = cantidad;
            this.precioUnitario = precioUnitario;
            this.subtotal = subtotal;
        }
    }

    // METODOS SUBMENU CATEGORIAS
    private static void altaCategoria() {
        System.out.print("Ingrese nombre de la categoría: ");
        String nombre = sc.nextLine().trim();
        if (nombre.isEmpty()) {
            System.out.println("Error: El nombre no puede estar vacío.");
            return;
        }

        System.out.print("Ingrese descripción de la categoría: ");
        String descripcion = sc.nextLine().trim();

        Categoria categoria = Categoria.builder()
                .nombre(nombre)
                .descripcion(descripcion)
                .build();


        Categoria categoriaGuardada = categoriaRepo.guardar(categoria);

        System.out.println("Categoría guardada con ID: " + categoriaGuardada.getId());
    }

    private static void modificarCategoria() {
        listarCategoriasActivas();

        System.out.print("Ingrese el ID de la categoría a modificar: ");
        Long id = sc.nextLong();
        sc.nextLine();

        Categoria categoriaExistente = categoriaRepo.buscarPorId(id)
                .filter(c -> !c.isEliminado())
                .orElse(null);

        if (categoriaExistente == null) {
            System.out.println("Error: No existe una categoría activa con ese ID.");
            return;
        }

        System.out.println("Nombre actual: " + categoriaExistente.getNombre());
        System.out.print("Nuevo nombre (enter para mantener): ");
        String nombreNuevo = sc.nextLine().trim();
        if (nombreNuevo.isEmpty()) {
            nombreNuevo = categoriaExistente.getNombre();
        }

        System.out.println("Descripción actual: " + categoriaExistente.getDescripcion());
        System.out.print("Nueva descripción (enter para mantener): ");
        String descripcionNueva = sc.nextLine().trim();
        if (descripcionNueva.isEmpty()) {
            descripcionNueva = categoriaExistente.getDescripcion();
        }

        Categoria categoriaActualizada = Categoria.builder()
                .nombre(nombreNuevo)
                .descripcion(descripcionNueva)
                .build();

        categoriaActualizada.setId(categoriaExistente.getId());
        categoriaActualizada.setEliminado(categoriaExistente.isEliminado());
        categoriaActualizada.setCreatedAt(categoriaExistente.getCreatedAt());

        categoriaRepo.guardar(categoriaActualizada);

        System.out.println("Categoría actualizada correctamente.");
    }

    private static void bajaLogicaCategoria() {

        
        List<Categoria> categoriasActivas = categoriaRepo.listarActivos();

        if (categoriasActivas.isEmpty()) {
            System.out.println("No hay categorías activas para modificar.");
            return;
        }

        System.out.print("Ingrese el ID de la categoría a dar de baja: ");
        Long id = sc.nextLong();
        sc.nextLine();

        Categoria categoriaExistente = categoriaRepo.buscarPorId(id)
                .filter(c -> !c.isEliminado())
                .orElse(null);

        if (categoriaExistente == null) {
            System.out.println("Error: No existe una categoría activa con ese ID.");
            return;
        }

        boolean resultado = categoriaRepo.eliminarLogico(id);
        if (resultado) {
            System.out.println("Categoría dada de baja: " + categoriaExistente.getNombre());
        } else {
            System.out.println("Error: No se pudo dar de baja la categoría.");
        }
    }

    private static void listarCategoriasActivas() {

        List<Categoria> categorias = categoriaRepo.listarActivos();
        imprimirListaCategorias(categorias);
    }

    // METODOS SUBMENU PRODUCTOS

    private static void altaProducto() {
        
        listarCategoriasActivas();

        System.out.print("Ingrese el ID de la categoría para el producto: ");
        Long categoriaId = sc.nextLong();
        sc.nextLine();

        Categoria categoriaSeleccionada = categoriaRepo.buscarPorId(categoriaId)
                .filter(c -> !c.isEliminado())
                .orElse(null);

        if (categoriaSeleccionada == null) {
            System.out.println("Error: No existe una categoría activa con ese ID.");
            return;
        }

        System.out.print("Ingrese nombre del producto: ");
        String nombre = sc.nextLine().trim();
        if (nombre.isEmpty()) {
            System.out.println("Error: El nombre no puede estar vacío.");
            return;
        }

        System.out.print("Ingrese descripción del producto: ");
        String descripcion = sc.nextLine().trim();

        System.out.print("Ingrese precio del producto: ");
        double precio = sc.nextDouble();
        sc.nextLine();
        if (precio <= 0) {
            System.out.println("Error: El precio debe ser mayor a 0.");
            return;
        }

        System.out.print("Ingrese stock del producto: ");
        int stock = sc.nextInt();
        sc.nextLine();
        if (stock < 0) {
            System.out.println("Error: El stock no puede ser negativo.");
            return;
        }

        Producto producto = Producto.builder()
                .nombre(nombre)
                .descripcion(descripcion)
                .precio(precio)
                .stock(stock)
                .build();

        Categoria categoriaReferencia = new Categoria();
        categoriaReferencia.setId(categoriaId);
        producto.setCategoria(categoriaReferencia);

        Producto productoGuardado = productoRepo.guardar(producto);

        categoriaSeleccionada.addProducto(productoGuardado);
        categoriaRepo.guardar(categoriaSeleccionada);

        System.out.println("Producto guardado con ID: " + productoGuardado.getId() + " y asignado a categoría: " + categoriaId);
    }

    private static void modificarProducto() {
        listarProductosActivos();

        System.out.print("Ingrese el ID del producto a modificar: ");
        Long id = sc.nextLong();
        sc.nextLine();

        Producto productoExistente = productoRepo.buscarPorId(id)
                .filter(p -> !p.isEliminado())
                .orElse(null);

        if (productoExistente == null) {
            System.out.println("Error: No existe un producto activo con ese ID.");
            return;
        }

        System.out.println("Nombre actual: " + productoExistente.getNombre());
        System.out.print("Nuevo nombre (enter para mantener): ");
        String nombreNuevo = sc.nextLine().trim();
        if (nombreNuevo.isEmpty()) {
            nombreNuevo = productoExistente.getNombre();
        }

        System.out.println("Precio actual: " + productoExistente.getPrecio());
        System.out.print("Nuevo precio (enter para mantener): ");
        String precioNuevo = sc.nextLine().trim();
        if (precioNuevo.isEmpty()) {
            precioNuevo = String.valueOf(productoExistente.getPrecio());
        }

        System.out.println("Stock actual: " + productoExistente.getStock());
        System.out.print("Nuevo stock (enter para mantener): ");
        String stockNuevo = sc.nextLine().trim();
        if (stockNuevo.isEmpty()) {
            stockNuevo = String.valueOf(productoExistente.getStock());
        }

        Producto productoActualizado = Producto.builder()
                .nombre(nombreNuevo)
                .precio(Double.parseDouble(precioNuevo))
                .stock(Integer.parseInt(stockNuevo))
                .build();

        productoActualizado.setId(productoExistente.getId());
        productoActualizado.setEliminado(productoExistente.isEliminado());
        productoActualizado.setCreatedAt(productoExistente.getCreatedAt());

        productoRepo.guardar(productoActualizado);

        System.out.println("Producto actualizado correctamente.");
    }

    private static void bajaLogicaProducto() {
        
        List<Producto> productosActivos = productoRepo.listarActivos();

        if (productosActivos.isEmpty()) {
            System.out.println("No hay productos activos para modificar.");
            return;
        }

        System.out.print("Ingrese el ID del producto a dar de baja: ");
        Long id = sc.nextLong();
        sc.nextLine();

        Producto productoExistente = productoRepo.buscarPorId(id)
                .filter(p -> !p.isEliminado())
                .orElse(null);

        if (productoExistente == null) {
            System.out.println("Error: No existe un producto activo con ese ID.");
            return;
        }

        boolean resultado = productoRepo.eliminarLogico(id);
        if (resultado) {
            System.out.println("Producto dada de baja: " + productoExistente.getNombre());
        } else {
            System.out.println("Error: No se pudo dar de baja el producto.");
        }
    }

    private static void listarProductosActivos() {

        List<Producto> productos = productoRepo.listarActivos();
        imprimirListaProductos(productos);
    }

    // METODOS SUBMENU USUARIOS

    private static void altaUsuario() {
        System.out.print("Ingrese nombre del usuario: ");
        String nombre = sc.nextLine().trim();
        if (nombre.isEmpty()) {
            System.out.println("Error: El nombre no puede estar vacío.");
            return;
        }

        System.out.print("Ingrese apellido del usuario: ");
        String apellido = sc.nextLine().trim();

        System.out.print("Ingrese mail del usuario: "); 
        String mail = sc.nextLine().trim();
        if (usuarioRepo.buscarPorMail(mail).isPresent()) {
            System.out.println("Error: El mail ya está en uso.");
            return;
        }

        System.out.print("Ingrese celular del usuario: ");
        String celular = sc.nextLine().trim();

        System.out.print("Ingrese contraseña del usuario: ");
        String contraseña = sc.nextLine().trim();

        System.out.print("Ingrese rol del usuario (ADMIN/USUARIO): "); 
        String rolInput = sc.nextLine().trim().toUpperCase(Locale.ROOT);
        Rol rol = Rol.valueOf(rolInput);

        Usuario usuario = Usuario.builder()
                .nombre(nombre)
                .apellido(apellido)
                .mail(mail)
                .celular(celular)
                .contraseña(contraseña)
                .rol(rol)
                .build();


        Usuario usuarioGuardado = usuarioRepo.guardar(usuario);

        System.out.println("Usuario guardado con ID: " + usuarioGuardado.getId());
    }
   
    private static void modificarUsuario() {
        listarUsuariosActivos();

        System.out.print("Ingrese el ID del usuario a modificar: ");
        Long id = sc.nextLong();
        sc.nextLine();

        Usuario usuarioExistente = usuarioRepo.buscarPorId(id)
                .filter(u -> !u.isEliminado())
                .orElse(null);

        if (usuarioExistente == null) {
            System.out.println("Error: No existe un usuario activo con ese ID.");
            return;
        }

        System.out.println("Nombre actual: " + usuarioExistente.getNombre());
        System.out.print("Nuevo nombre (enter para mantener): ");
        String nombreNuevo = sc.nextLine().trim();
        if (nombreNuevo.isEmpty()) {
            nombreNuevo = usuarioExistente.getNombre();
        }

        System.out.println("Apellido actual: " + usuarioExistente.getApellido());
        System.out.print("Nuevo apellido (enter para mantener): ");
        String apellidoNuevo = sc.nextLine().trim();
        if (apellidoNuevo.isEmpty()) {
            apellidoNuevo = usuarioExistente.getApellido();
        }

        System.out.println("Mail actual: " + usuarioExistente.getMail());
        System.out.print("Nuevo mail (enter para mantener): ");
        String mailNuevo = sc.nextLine().trim();
        if (mailNuevo.isEmpty()) {
            mailNuevo = usuarioExistente.getMail();
        } else if (usuarioRepo.buscarPorMail(mailNuevo).isPresent()) {
            System.out.println("Error: El mail ya está en uso.");
            return;
        }

        System.out.print("Nueva contraseña (enter para mantener): ");
        String contraseñaNueva = sc.nextLine().trim();
        if (contraseñaNueva.isEmpty()) {
            contraseñaNueva = usuarioExistente.getContraseña();
        }

        System.out.println("Celular actual: " + usuarioExistente.getCelular());
        System.out.print("Nuevo celular (enter para mantener): ");
        String celularNuevo = sc.nextLine().trim();
        if (celularNuevo.isEmpty()) {
            celularNuevo = usuarioExistente.getCelular();
        }

        Usuario usuarioActualizado = Usuario.builder()
                .nombre(nombreNuevo)
                .apellido(apellidoNuevo)
                .mail(mailNuevo)
                .contraseña(contraseñaNueva)
                .celular(celularNuevo)
                .build();

        usuarioActualizado.setId(usuarioExistente.getId());
        usuarioActualizado.setEliminado(usuarioExistente.isEliminado());
        usuarioActualizado.setCreatedAt(usuarioExistente.getCreatedAt());

        usuarioRepo.guardar(usuarioActualizado);

        System.out.println("Usuario actualizado correctamente.");
    }
    
    private static void bajaLogicaUsuario() {
        
        List<Usuario> usuariosActivos = usuarioRepo.listarActivos();

        if (usuariosActivos.isEmpty()) {
            System.out.println("No hay usuarios activos para modificar.");
            return;
        }

        System.out.print("Ingrese el ID del usuario a dar de baja: ");
        Long id = sc.nextLong();
        sc.nextLine();

        Usuario usuarioExistente = usuarioRepo.buscarPorId(id)
                .filter(u -> !u.isEliminado())
                .orElse(null);

        if (usuarioExistente == null) {
            System.out.println("Error: No existe un usuario activo con ese ID.");
            return;
        }

        boolean resultado = usuarioRepo.eliminarLogico(id);
        if (resultado) {
            System.out.println("Usuario dado de baja: " + usuarioExistente.getNombre() + " " + usuarioExistente.getApellido());
        } else {
            System.out.println("Error: No se pudo dar de baja el usuario.");
        }
    }
   
    private static void listarUsuariosActivos() {

        List<Usuario> usuarios = usuarioRepo.listarActivos();
        imprimirListaUsuarios(usuarios);
    }
    
    private static void buscarUsuarioPorMail() {
        System.out.print("Ingrese el mail del usuario a buscar: ");
        String mail = sc.nextLine().trim();

        Optional<Usuario> usuarioOpt = usuarioRepo.buscarPorMail(mail);
        imprimirUsuario(usuarioOpt);
    }
    
    // METODOS SUBMENU PEDIDOS

    private static void altaPedido() {
        List<Usuario> usuariosActivos = usuarioRepo.listarActivos();
        imprimirListaUsuarios(usuariosActivos);

        System.out.print("Seleccione el ID del usuario: ");
        Long usuarioId;
        try {
            usuarioId = Long.parseLong(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }

        Usuario usuarioSeleccionado = usuariosActivos.stream()
                .filter(u -> u.getId().equals(usuarioId))
                .findFirst()
                .orElse(null);

        if (usuarioSeleccionado == null) {
            System.out.println("No existe un usuario activo con ese ID.");
            return;
        }

        System.out.println("Formas de pago disponibles:");
        for (FormaPago formaPago : FormaPago.values()) {
            System.out.println("- " + formaPago.name());
        }

        System.out.print("Seleccione la forma de pago: ");
        String formaPagoInput = sc.nextLine().trim().toUpperCase(Locale.ROOT);
        FormaPago formaPago;
        try {
            formaPago = FormaPago.valueOf(formaPagoInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Forma de pago inválida.");
            return;
        }

        List<ItemPedidoTemporal> itemsTemporales = new ArrayList<>();
        boolean seguirAgregando = true;

        while (seguirAgregando) {
            List<Producto> productosActivos = productoRepo.listarActivos();
            imprimirListaProductos(productosActivos);

            System.out.print("Ingrese el ID del producto a agregar (o 0 para cancelar): ");
            Long productoId;
            try {
                productoId = Long.parseLong(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("ID inválido.");
                continue;
            }

            if (productoId == 0) {
                break;
            }

            Producto productoSeleccionado = productosActivos.stream()
                    .filter(p -> p.getId().equals(productoId))
                    .findFirst()
                    .orElse(null);

            if (productoSeleccionado == null) {
                System.out.println("No existe un producto activo con ese ID.");
                continue;
            }

            if (!Boolean.TRUE.equals(productoSeleccionado.getDisponible())) {
                System.out.println("El producto no está disponible.");
                continue;
            }

            System.out.print("Ingrese la cantidad: ");
            int cantidad;
            try {
                cantidad = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Cantidad inválida.");
                continue;
            }

            if (cantidad <= 0) {
                System.out.println("La cantidad debe ser mayor a 0.");
                continue;
            }

            if (productoSeleccionado.getStock() < cantidad) {
                System.out.printf("Stock insuficiente. Stock disponible: %d%n", productoSeleccionado.getStock());
                continue;
            }

            double subtotal = productoSeleccionado.getPrecio() * cantidad;
            itemsTemporales.add(new ItemPedidoTemporal(
                    productoSeleccionado.getId(),
                    productoSeleccionado.getNombre(),
                    cantidad,
                    productoSeleccionado.getPrecio(),
                    subtotal));

            System.out.print("¿Desea agregar otro producto? (s/n): ");
            String respuesta = sc.nextLine().trim().toLowerCase(Locale.ROOT);
            seguirAgregando = "s".equals(respuesta) || "si".equals(respuesta) || "y".equals(respuesta);
        }

        if (itemsTemporales.isEmpty()) {
            System.out.println("El pedido debe tener al menos un detalle. Operación cancelada.");
            return;
        }

        try {
            Usuario usuarioPersistente = usuarioRepo.buscarPorId(usuarioSeleccionado.getId())
                    .filter(u -> !u.isEliminado())
                    .orElse(null);

            if (usuarioPersistente == null) {
                throw new IllegalArgumentException("No existe un usuario activo con ese ID.");
            }

            Pedido pedido = Pedido.builder()
                    .fecha(LocalDate.now())
                    .estado(EstadoPedido.PENDIENTE)
                    .formaPago(formaPago)
                    .total(0.0)
                    .build();

            for (ItemPedidoTemporal item : itemsTemporales) {
                Producto productoGestionado = productoRepo.buscarPorId(item.productoId)
                        .filter(p -> !p.isEliminado())
                        .orElse(null);

                if (productoGestionado == null || !Boolean.TRUE.equals(productoGestionado.getDisponible())) {
                    throw new IllegalArgumentException("El producto seleccionado ya no está disponible.");
                }

                if (productoGestionado.getStock() < item.cantidad) {
                    throw new IllegalArgumentException("Stock insuficiente para el producto: " + item.nombre);
                }

                productoGestionado.setStock(productoGestionado.getStock() - item.cantidad);
                productoRepo.guardar(productoGestionado);

                pedido.addDetallePedido(item.cantidad, productoGestionado);
            }

            pedido.calcularTotal();

            Pedido pedidoGuardado = pedidoRepo.guardar(pedido);

            usuarioPersistente.addPedido(pedido);

            Usuario usuarioGuardado = usuarioRepo.guardar(usuarioPersistente);

            
            System.out.println("Pedido creado correctamente.");
            System.out.println("ID: " + pedidoGuardado.getId());
            System.out.println("Fecha: " + pedidoGuardado.getFecha());
            System.out.println("Usuario: " + usuarioGuardado.getNombre() + " " + usuarioGuardado.getApellido());
            System.out.println("Forma de pago: " + pedidoGuardado.getFormaPago());
            System.out.println("Detalle:");
            for (ItemPedidoTemporal item : itemsTemporales) {
                System.out.printf("- %s x%d - Subtotal: %.2f%n", item.nombre, item.cantidad, item.subtotal);
            }
            System.out.printf("Total: %.2f%n", pedidoGuardado.getTotal());
        } catch (RuntimeException e) {
            System.out.println("Error al crear el pedido: " + e.getMessage());
        }
    }
    
    private static void cambiarEstadoPedido() {
        System.out.println("Ingrese el ID del pedido a modificar: ");
        Long pedidoId = sc.nextLong();
        
        Pedido pedidoExistente = pedidoRepo.buscarPorId(pedidoId)
                .filter(p -> !p.isEliminado())
                .orElse(null);

        if (pedidoExistente == null) {
            System.out.println("Error: No existe un pedido activo con ese ID.");
            return;
        }
        System.out.println("Estado actual: " + pedidoExistente.getEstado().toString());
        
        System.out.println("Estado de pedido disponibles:");
        for (EstadoPedido estado : EstadoPedido.values()) {
            System.out.println("- " + estado.name());
        }
        
        System.out.print("Nuevo estado (enter para mantener): ");
        String estadoNuevo = sc.nextLine().trim().toUpperCase(Locale.ROOT);
        
        if (estadoNuevo.isEmpty()) {
            estadoNuevo = pedidoExistente.getEstado().toString();
        }

        pedidoExistente.setEstado(EstadoPedido.valueOf(estadoNuevo));
        pedidoRepo.guardar(pedidoExistente);
        System.out.println("Estado del pedido actualizado correctamente."); 
               
    }
  
    private static void bajaLogicaPedido() {
        
        List<Pedido> pedidosActivos = pedidoRepo.listarActivos();

        if (pedidosActivos.isEmpty()) {
            System.out.println("No hay pedidos activos para modificar.");
            return;
        }

        System.out.print("Ingrese el ID del pedido a dar de baja: ");
        Long id = sc.nextLong();
        sc.nextLine();

        Pedido pedidoExistente = pedidoRepo.buscarPorId(id)
                .filter(p -> !p.isEliminado())
                .orElse(null);

        if (pedidoExistente == null) {
            System.out.println("Error: No existe un pedido activo con ese ID.");
            return;
        }

        boolean resultado = pedidoRepo.eliminarLogico(id);
        if (resultado) {
            System.out.println("Pedido dado de baja: " + pedidoExistente.getId());
        } else {
            System.out.println("Error: No se pudo dar de baja el pedido.");
        }
    }
    
    private static void listarPedidosActivos() {

       List<Pedido> pedidos = pedidoRepo.listarActivos();
        imprimirListaPedidos(pedidos);
    }
    
    private static void buscarPedidoPorUsuario() {
        listarUsuariosActivos();
        System.out.print("Ingrese el ID del usuario para buscar sus pedidos: ");
        Long usuarioId = sc.nextLong();

        Usuario usuarioExistente = usuarioRepo.buscarPorId(usuarioId)
                .filter(u -> !u.isEliminado())
                .orElse(null);

        if (usuarioExistente == null) {
            System.out.println("Error: No existe un usuario activo con ese ID.");
            return;
        }
        List<Pedido> pedidos = usuarioRepo.buscarPedidosPorUsuario(usuarioExistente.getId());
        imprimirListaPedidos(pedidos);
    }
    
    private static void buscarPedidoPorEstado() {
        System.out.println("Estado de pedido disponibles:");
        for (EstadoPedido estado : EstadoPedido.values()) {
            System.out.println("- " + estado.name());
        }
        System.out.print("Ingrese el estado de los pedidos a buscar: ");
        String estadoSeleccionado = sc.nextLine().trim().toUpperCase(Locale.ROOT);

        EstadoPedido estadoExistente = EstadoPedido.valueOf(estadoSeleccionado);

        List<Pedido> pedidos = pedidoRepo.buscarPorEstado(estadoExistente);
        imprimirListaPedidos(pedidos);

    }

    // METODOS SUBMENU REPORTES
    private static void productosPorCategoria() {
        // Implementar reporte de productos por categoría
    }
    
    private static void pedidosPorUsuario() {
        // Implementar reporte de pedidos por usuario
    }
    
    private static void pedidosPorEstado() {
        // Implementar reporte de pedidos por estado
    }
    
    private static void totalFacturado() {
        // Implementar reporte de total facturado
    }

    // Métodos auxiliares

    // metodos auxiliares para imprimir listas de entidades formateadas en consola

    // metodo que imprime una lista de productos formateada 
    static void imprimirListaProductos(List<Producto> productos) {
    if (productos.isEmpty()) {
            System.out.println("No hay productos activos en el listado.");
            return;
        }
        System.out.println("Productos activos encontrados:");
        for (Producto producto : productos) {
            System.out.printf("ID: %d - Categoría: %s - Nombre: %s - Precio: %.2f - Stock: %d%n",
                    producto.getId(), producto.getCategoria().getNombre(), producto.getNombre(), producto.getPrecio(), producto.getStock());
        }
    
    }
    
    // metodo que imprime una lista de categorias formateada
    static void imprimirListaCategorias(List<Categoria> categorias) {
        if (categorias.isEmpty()) {
            System.out.println("No hay categorías activas en el listado.");
            return;
        }
        System.out.println("Categorías activas encontradas:");
        for (Categoria categoria : categorias) {
            System.out.printf("ID: %d - Nombre: %s - Descripción: %s%n",
                    categoria.getId(), categoria.getNombre(), categoria.getDescripcion());
        }
    }

    // metodo que imprime una lista de usuarios formateada
    static void imprimirListaUsuarios(List<Usuario> usuarios) {
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios activos en el listado.");
            return;
        }
        System.out.println("Usuarios activos encontrados:");
        for (Usuario usuario : usuarios) {
            System.out.printf("ID: %d - Nombre: %s Apellido: %s - Mail: %s%n - Rol: %s%n",
                    usuario.getId(), usuario.getNombre(), usuario.getApellido(), usuario.getMail(), usuario.getRol().name());
        }
    }

    // metodo que imprime una lista de pedidos formateada
    static void imprimirListaPedidos(List<Pedido> pedidos) {
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos activos en el listado.");
            return;
        }
        System.out.println("Pedidos activos encontrados:");
        for (Pedido pedido : pedidos) {
            System.out.printf("ID: %d - Fecha: %s - Forma de pago: %s - Total: %.2f%n",
                    pedido.getId(), pedido.getFecha(), pedido.getFormaPago(), pedido.getTotal());
        }
    }

    // metodo que imprime un optional usuario formateado
    static void imprimirUsuario(Optional<Usuario> usuarioOpt) {
        if (usuarioOpt.isEmpty()) {
            System.out.println("No se encontró un usuario activo con ese mail.");
            return;
        }   
        System.out.println("Usuario activo encontrado:");
        Usuario usuario = usuarioOpt.get();
        System.out.printf("ID: %d - Nombre: %s Apellido: %s - Mail: %s%n - Rol: %s%n",
                usuario.getId(), usuario.getNombre(), usuario.getApellido(), usuario.getMail(), usuario.getRol().name());
    }

}

    
