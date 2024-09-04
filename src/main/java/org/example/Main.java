package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("example-unit");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        System.out.println("Estamos en Marcha");

        try {
            entityManager.getTransaction().begin();

            Cliente cliente1 = Cliente.builder()
                    .nombre("Romina")
                    .apellido("Suarez")
                    .dni(3898274)
                    .build();
            Domicilio domicilio1= Domicilio.builder()
                    .nombreCalle("Jorge A Calle")
                    .numero(2467)
                    .build();
            cliente1.setDomicilio(domicilio1);
            domicilio1.setCliente(cliente1);

            Categoria perecederos= Categoria.builder()
                    .denominacion("Perecederos")
                    .build();
            Categoria lacteos= Categoria.builder()
                    .denominacion("Lacteos")
                    .build();
            Categoria limpieza= Categoria.builder()
                    .denominacion("Limpieza")
                    .build();

            Articulo articulo1= Articulo.builder()
                    .denominacion("Leche Entera La serenisima")
                    .cantidad(100)
                    .precio(700)
                    .build();

            Articulo articulo2= Articulo.builder()
                    .denominacion("Lavandina Ayudin")
                    .cantidad(200)
                    .precio(1000)
                    .build();

            Factura factura1= Factura.builder()
                    .numero(100)
                    .fecha("03/03/2024")
                    .cliente(cliente1)
                    .build();

            articulo1.getCategorias().add(perecederos);
            articulo1.getCategorias().add(lacteos);

            lacteos.getArticulos().add(articulo1);
            perecederos.getArticulos().add(articulo1);

            articulo2.getCategorias().add(limpieza);
            limpieza.getArticulos().add(articulo2);

            DetalleFactura detalle1= DetalleFactura.builder()
                    .cantidad(3)
                    .articulo(articulo1)
                    .subtotal(2100)
                    .build();
            articulo1.getDetalle().add(detalle1);
            factura1.getDetalle().add(detalle1);
            detalle1.setFactura(factura1);

            DetalleFactura detalle2= DetalleFactura.builder()
                    .cantidad(1)
                    .articulo(articulo2)
                    .subtotal(1000)
                    .build();
            articulo2.getDetalle().add(detalle2);
            factura1.getDetalle().add(detalle2);
            detalle2.setFactura(factura1);

            factura1.setTotal(3100);
            entityManager.persist(factura1);

            Cliente cliente2 = Cliente.builder()
                    .nombre("Hsin Yu")
                    .apellido("Lin")
                    .dni(4802772)
                    .build();
            Domicilio domicilio2= Domicilio.builder()
                    .nombreCalle("San Martin")
                    .numero(3893)
                    .build();
            cliente2.setDomicilio(domicilio2);
            domicilio2.setCliente(cliente2);

            Articulo articulo3= Articulo.builder()
                    .denominacion("Queso cremoso")
                    .cantidad(300)
                    .precio(2000)
                    .build();

            Factura factura2= Factura.builder()
                    .numero(101)
                    .fecha("05/03/2024")
                    .cliente(cliente2)
                    .build();

            articulo3.getCategorias().add(perecederos);
            articulo3.getCategorias().add(lacteos);

            lacteos.getArticulos().add(articulo3);
            perecederos.getArticulos().add(articulo3);



            DetalleFactura detalle3= DetalleFactura.builder()
                    .cantidad(2)
                    .articulo(articulo3)
                    .subtotal(4000)
                    .build();
            articulo3.getDetalle().add(detalle3);
            factura2.getDetalle().add(detalle3);
            detalle3.setFactura(factura2);

            DetalleFactura detalle4= DetalleFactura.builder()
                    .cantidad(1)
                    .articulo(articulo1)
                    .subtotal(700)
                    .build();
            articulo1.getDetalle().add(detalle4);
            factura2.getDetalle().add(detalle4);
            detalle4.setFactura(factura2);

            factura2.setTotal(4700);
            entityManager.persist(factura2);

            entityManager.flush();
            entityManager.getTransaction().commit();


        }catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
            System.out.println("No se pudo grabar la clase factura");
        }

        // Cerrar el EntityManager y el EntityManagerFactory
        entityManager.close();
        entityManagerFactory.close();


    }

}
