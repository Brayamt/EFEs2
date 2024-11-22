import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

class DatosEmpresa {
    public static final String NOMBRE = "TIENDAS EFE";
    public static final String RUC = "20100123456";
    public static final String DIRECCION = "Av.principal - 20 - JILIACA";
    public static final String TELEFONO = "(01) 555-5555";
    public static final String WEBSITE = "www.tiendasefe.com.pe";
    public static final String TELEFONO_RECLAMOS = "0800-12345";
    public static final String SERIE_BOLETA = "B001";
    public static final String CLIENTE_NOMBRE = "Alex-Fernando";
    public static final String CLIENTE_DNI = "12345678";
    public static final String CLIENTE_DIRECCION = "Av. Labayeque 123";
}

class Usuario {
    private String correo;
    private String contrasenia;

    public Usuario(String correo, String contrasenia) {
        this.correo = correo;
        this.contrasenia = contrasenia;
    }

    public String getCorreo() { return correo; }
    public String getContrasenia() { return contrasenia; }

    public boolean validarCredenciales(String correo, String contrasenia) {
        return this.correo.equals(correo) && this.contrasenia.equals(contrasenia);
    }
}

class DatosTarjeta {
    private String numero;
    private String fechaVencimiento;
    private String cvv;
    private String titular;

    public DatosTarjeta(String numero, String fechaVencimiento, String cvv, String titular) {
        this.numero = numero;
        this.fechaVencimiento = fechaVencimiento;
        this.cvv = cvv;
        this.titular = titular;
    }

    public String getTitular() { return titular; }
    public String getNumeroOculto() {
        return "** ** ** " + numero.substring(numero.length() - 4);
    }
}

class Producto {
    private String nombre;
    private double precio;
    private String categoria;

    public Producto(String nombre, double precio, String categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
    }

    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public String getCategoria() { return categoria; }

    @Override
    public String toString() {
        return nombre + " - S/." + precio;
    }
}

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static List<Usuario> usuarios = new ArrayList<>(Arrays.asList(
            new Usuario("admin@gmail.com", "admin123"),
            new Usuario("user@gmail.com", "user123")
    ));
    private static Usuario usuarioActual = null;
    private static List<Producto> productos = new ArrayList<>();
    private static Map<Producto, Integer> carrito = new HashMap<>();
    private static double totalCompra = 0.0;

    static {
        // Electrónicos
        productos.add(new Producto("Lavadora Hisense WM TL 20KG WT3T2023UT", 1399.00, "Electrónicos"));
        productos.add(new Producto("Secadora Samsung Bespoke Luxury 17KG Black", 3900.00, "Electrónicos"));
        productos.add(new Producto("Lavaseca LG AI DD 12/7KG WD12PVC3S6C Platino", 1899.00, "Electrónicos"));
        productos.add(new Producto("Refrigeradora Bord 207LT Frost RE207FS-M Silver", 799.00, "Electrónicos"));
        productos.add(new Producto("Congelador Bord CO360B-M 362LT Blanco", 1399.00, "Electrónicos"));

        // Hogar
        productos.add(new Producto("Parrilla Mr Grill Cilíndrica Con Tapa", 119.00, "Hogar"));
        productos.add(new Producto("Mini Raclette con Parrilla Severin 1800W RG 2370", 159.00, "Hogar"));
        productos.add(new Producto("Parrilla Maletín Meat Master Mixta", 269.00, "Hogar"));
        productos.add(new Producto("Parrila Portátil Plegable 45x30cm", 49.00, "Hogar"));
        productos.add(new Producto("Parrilla Junior con Sistema de Levante Mixta", 169.00, "Hogar"));

        // Muebles
        productos.add(new Producto("Seccional Forli Spring Tela Marrón Derecho", 1139.00, "Muebles"));
        productos.add(new Producto("Seccional derecho Decohome Noruega", 1799.00, "Muebles"));
        productos.add(new Producto("Seccional Decohome Almendra Gris", 1939.00, "Muebles"));
        productos.add(new Producto("Seccional Casabella Curitiva Turquesa", 1749.00, "Muebles"));
        productos.add(new Producto("Mueble seccional Falotih Cristina", 4309.00, "Muebles"));

        // Deportes
        productos.add(new Producto("Combo Fitness K6 Trotadora Crono 3", 1839.00, "Deportes"));
        productos.add(new Producto("Trotadora Curva C200 Ultimate", 3419.00, "Deportes"));
        productos.add(new Producto("Trotadora Muvo Endurance 06 Serie 6", 2699.00, "Deportes"));
        productos.add(new Producto("Silicona lubricante Soportex de 250ML", 89.00, "Deportes"));
        productos.add(new Producto("Trotadora Muvo Endurance 56", 7159.00, "Deportes"));

        // Moda
        productos.add(new Producto("Bolso Kaeroshy impermeable Bibi Rosado", 39.00, "Moda"));
        productos.add(new Producto("Billetera Baellerry hombre Negro", 39.90, "Moda"));
        productos.add(new Producto("Billetera Luigi MBI-019 LAG Neg", 173.00, "Moda"));
        productos.add(new Producto("Perfume unisex 4711 Edition Lavender 100ml", 99.00, "Moda"));
        productos.add(new Producto("Eau de toilette Women Berry Temptation 40ML", 49.00, "Moda"));
    }
    public static void iniciarSistema() {
        int opcion;
        do {
            System.out.println("=== SISTEMA DE TIENDA EFE ===");
            System.out.println("1. Iniciar Sesión");
            System.out.println("2. Registrarse");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1 -> iniciarSesion();
                case 2 -> registrarUsuario();
                case 3 -> System.out.println("¡Gracias por visitar EFE!");
                default -> System.out.println("Opción no válida");
            }
        } while (opcion != 3 && usuarioActual == null);
    }

    public static void mostrarMenu() {
        int opcion;
        do {
            System.out.println("=== TIENDA EFE ===");
            System.out.println("1. Ver Categorías");
            System.out.println("2. Ver Carrito");
            System.out.println("3. Realizar Pago");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1 -> mostrarCategorias();
                case 2 -> mostrarCarrito();
                case 3 -> realizarPago();
                case 4 -> System.out.println("¡Gracias por su visita!");
                default -> System.out.println("Opción no válida");
            }
        } while (opcion != 4);
    }

    private static void mostrarCategorias() {
        Set<String> categorias = productos.stream()
                .map(Producto::getCategoria)
                .collect(Collectors.toSet());

        System.out.println("\nCategorías disponibles:");
        int i = 1;
        for (String categoria : categorias) {
            System.out.println(i + ". " + categoria);
            i++;
        }

        System.out.print("Seleccione una categoría (0 para volver): ");
        int opcion = Integer.parseInt(sc.nextLine());

        if (opcion != 0 && opcion <= categorias.size()) {
            String categoriaSeleccionada = (String) categorias.toArray()[opcion - 1];
            mostrarProductos(categoriaSeleccionada);
        }
    }

    private static void mostrarProductos(String categoria) {
        List<Producto> productosCat = productos.stream()
                .filter(p -> p.getCategoria().equals(categoria))
                .toList();

        System.out.println("Productos en " + categoria + ":");
        for (int i = 0; i < productosCat.size(); i++) {
            System.out.println((i + 1) + ". " + productosCat.get(i));
        }

        System.out.print("Seleccione un producto (0 para volver): ");
        int opcion = Integer.parseInt(sc.nextLine());

        if (opcion != 0 && opcion <= productosCat.size()) {
            agregarAlCarrito(productosCat.get(opcion - 1));
        }
    }

    private static void agregarAlCarrito(Producto producto) {
        System.out.print("Cantidad: ");
        int cantidad = Integer.parseInt(sc.nextLine());

        if (cantidad > 0) {
            carrito.put(producto, carrito.getOrDefault(producto, 0) + cantidad);
            totalCompra += producto.getPrecio() * cantidad;
            System.out.println("Producto agregado al carrito");
        }
    }

    private static void mostrarCarrito() {
        if (carrito.isEmpty()) {
            System.out.println("Carrito vacío");
            return;
        }

        System.out.println("=== Su Carrito ===");
        carrito.forEach((producto, cantidad) ->
                System.out.println(producto + " x " + cantidad +
                        " = S/." + (producto.getPrecio() * cantidad))
        );
        System.out.println("Total: S/." + totalCompra);
    }

    private static void iniciarSesion() {
        System.out.print("Correo (@gmail.com): ");
        String correo = sc.nextLine();
        System.out.print("Contraseña: ");
        String contrasenia = sc.nextLine();

        for (Usuario u : usuarios) {
            if (u.validarCredenciales(correo, contrasenia)) {
                usuarioActual = u;
                System.out.println("¡Bienvenido " + u.getCorreo() + "!");
                return;
            }
        }

        System.out.println("Credenciales incorrectas");
        System.out.println("1. Intentar de nuevo");
        System.out.println("2. Registrarse");
        if (sc.nextLine().equals("2")) {
            registrarUsuario();
        }
    }
    private static void registrarUsuario() {
        System.out.print("Nuevo correo (@gmail.com): ");
        String correo = sc.nextLine();

        if (!correo.contains("@gmail.com")) {
            System.out.println("El correo debe ser de Gmail");
            return;
        }

        if (usuarios.stream().anyMatch(u -> u.getCorreo().equals(correo))) {
            System.out.println("Este correo ya está registrado");
            return;
        }

        System.out.print("Nueva contraseña: ");
        String contrasenia = sc.nextLine();

        if (contrasenia.length() < 6) {
            System.out.println("La contraseña debe tener al menos 6 caracteres");
            return;
        }

        Usuario nuevoUsuario = new Usuario(correo, contrasenia);
        usuarios.add(nuevoUsuario);
        usuarioActual = nuevoUsuario;
        System.out.println("¡Registro exitoso!");
    }

    private static DatosTarjeta procesarPagoTarjeta() {
        System.out.println("Ingrese los datos de la tarjeta:");
        String numero;
        do {
            System.out.print("Número de tarjeta (16 dígitos): ");
            numero = sc.nextLine().replaceAll("\\s+", "");
        } while (!numero.matches("\\d{16}"));

        String fecha;
        do {
            System.out.print("Fecha de vencimiento (MM/YY): ");
            fecha = sc.nextLine();
        } while (!fecha.matches("(0[1-9]|1[0-2])/[0-9]{2}"));

        String cvv;
        do {
            System.out.print("CVV: ");
            cvv = sc.nextLine();
        } while (!cvv.matches("\\d{3}"));

        System.out.print("Nombre del titular: ");
        String titular = sc.nextLine();

        return new DatosTarjeta(numero, fecha, cvv, titular);
    }

    private static void generarFactura(String metodoPago, double montoRecibido) {
        try {
            String rutaFacturas = "C:\\Facturas_EFE\\";
            File directorioFacturas = new File(rutaFacturas);
            if (!directorioFacturas.exists()) {
                directorioFacturas.mkdirs();
            }
            String numeroFactura = String.format("%08d", new Random().nextInt(100000000));
            String nombreArchivo = rutaFacturas + "factura_" + numeroFactura + ".txt";
            PrintWriter print = new PrintWriter(new FileWriter(nombreArchivo));

            print.println("===============================================");
            print.printf("                %s%n", DatosEmpresa.NOMBRE);
            print.printf("          R.U.C.: %s%n", DatosEmpresa.RUC);
            print.printf("       %s%n", DatosEmpresa.DIRECCION);
            print.printf("        Teléfono: %s%n", DatosEmpresa.TELEFONO);
            print.println("-----------------------------------------------");
            print.println("            BOLETA DE VENTA");
            print.printf("              %s-%s%n", DatosEmpresa.SERIE_BOLETA, numeroFactura);
            print.println("-----------------------------------------------");

            LocalDateTime ahora = LocalDateTime.now();
            print.printf("Fecha: %s             Hora: %s%n",
                    ahora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    ahora.format(DateTimeFormatter.ofPattern("HH:mm")));
            print.printf("Cliente: %s%n", DatosEmpresa.CLIENTE_NOMBRE);
            print.printf("DNI: %s%n", DatosEmpresa.CLIENTE_DNI);
            print.printf("Dirección: %s%n", DatosEmpresa.CLIENTE_DIRECCION);
            print.println("-----------------------------------------------");
            print.println("DESCRIPCION                 CANT   P.U.   TOTAL");
            print.println("-----------------------------------------------");

            carrito.forEach((producto, cantidad) -> {
                print.printf("%-25s %3d  %6.2f  %7.2f%n",
                        producto.getNombre().substring(0, Math.min(25, producto.getNombre().length())),
                        cantidad,
                        producto.getPrecio(),
                        producto.getPrecio() * cantidad);
            });

            print.println("-----------------------------------------------");
            double subtotal = totalCompra / 1.18;
            double igv = totalCompra - subtotal;
            print.printf("                     SUBTOTAL: S/. %.2f%n", subtotal);
            print.printf("                          IGV: S/. %.2f%n", igv);
            print.printf("                    DESCUENTO: S/. %.2f%n", 0.00);
            print.printf("                       TOTAL: S/. %.2f%n", totalCompra);
            print.println("-----------------------------------------------");
            print.printf("FORMA DE PAGO: %s%n", metodoPago);
            print.printf("MONTO RECIBIDO: S/. %.2f%n", montoRecibido);
            print.printf("VUELTO: S/. %.2f%n", montoRecibido - totalCompra);
            print.println("-----------------------------------------------");
            print.println("        GRACIAS POR SU PREFERENCIA");
            print.println("    Conserve su boleta para cualquier");
            print.println("          reclamo o devolución");
            print.println("    Para consultas y reclamos:");
            print.printf("    %s%n", DatosEmpresa.WEBSITE);
            print.println("    Libro de Reclamaciones Virtual");
            print.printf("    Tel: %s%n", DatosEmpresa.TELEFONO_RECLAMOS);
            print.println("===============================================");

            print.close();
            System.out.println("Factura generada: " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al generar la factura: " + e.getMessage());
        }
    }
    private static void realizarPago() {
        if (carrito.isEmpty()) {
            System.out.println("\nNo hay productos en el carrito");
            return;
        }

        System.out.println("=== Resumen de Compra ===");
        mostrarCarrito();

        System.out.println("Métodos de pago:");
        System.out.println("1. Tarjeta de crédito");
        System.out.println("2. Efectivo");

        System.out.print("Seleccione método de pago: ");
        int metodo = Integer.parseInt(sc.nextLine());

        double montoRecibido = 0;
        String metodoPago = "";

        if (metodo == 1) {
            DatosTarjeta tarjeta = procesarPagoTarjeta();
            montoRecibido = totalCompra;
            metodoPago = "Tarjeta " + tarjeta.getNumeroOculto();
        } else if (metodo == 2) {
            do {
                System.out.print("Ingrese el monto en efectivo: S/. ");
                montoRecibido = Double.parseDouble(sc.nextLine());
                if (montoRecibido < totalCompra) {
                    System.out.println("Monto insuficiente. Total a pagar: S/. " + totalCompra);
                }
            } while (montoRecibido < totalCompra);
            metodoPago = "Efectivo";
        }

        generarFactura(metodoPago, montoRecibido);
        System.out.println("Pago procesado correctamente");
        System.out.println("¡Gracias por su compra!");
        carrito.clear();
        totalCompra = 0;
    }

    public static void main(String[] args) {
        try {
            iniciarSistema();
            if (usuarioActual != null) {
                mostrarMenu();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
