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
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;

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

        CategoriaRepository categoriaRepository = new CategoriaRepository() {
        };
        Categoria categoriaGuardada = categoriaRepository.guardar(categoria);

        System.out.println("Categoría guardada con ID: " + categoriaGuardada.getId());
    }

    private static void modificarCategoria() {
        // Implementar modificación de categoría
    }

    private static void bajaLogicaCategoria() {
        // Implementar baja lógica de categoría
    }

    private static void listarCategoriasActivas() {
        // Implementar listado de categorías activas
    }

    // METODOS SUBMENU PRODUCTOS
    private static void altaProducto() {
        // Implementar alta de producto
    }

    private static void modificarProducto() {
        // Implementar modificación de producto 
    }

    private static void bajaLogicaProducto() {
        // Implementar baja lógica de producto
    }

    private static void listarProductosActivos() {
        // Implementar listado de productos activos
    }

    // METODOS SUBMENU USUARIOS
    private static void altaUsuario() {
        // Implementar alta de usuario
    }
   
    private static void modificarUsuario() {
        // Implementar modificación de usuario
    }
    
    private static void bajaLogicaUsuario() {
        // Implementar baja lógica de usuario
    }
   
    private static void listarUsuariosActivos() {
        // Implementar listado de usuarios activos
    }
    
    private static void buscarUsuarioPorMail() {
        // Implementar búsqueda de usuario por mail
    }
    
    // METODOS SUBMENU PEDIDOS
    private static void altaPedido() {
        // Implementar alta de pedido
    }
    
    private static void cambiarEstadoPedido() {
        // Implementar cambio de estado de pedido
    }
    
    private static void bajaLogicaPedido() {
        // Implementar baja lógica de pedido
    }
    
    private static void listarPedidosActivos() {
        // Implementar listado de pedidos activos
    }
    
    private static void buscarPedidoPorUsuario() {
        // Implementar búsqueda de pedido por usuario
    }
    
    private static void buscarPedidoPorEstado() {
        // Implementar búsqueda de pedido por estado
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

}
