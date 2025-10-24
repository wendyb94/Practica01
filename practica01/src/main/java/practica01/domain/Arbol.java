/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica01.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;        // <-- importa BigDecimal
import java.sql.Timestamp;
import lombok.Data;

@Data
@Entity
@Table(name = "arbol")
public class Arbol implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre_comun", length = 100)
    @Size(max = 100, message = "El nombre común no puede tener más de 100 caracteres.")
    private String nombreComun;

    @Column(name = "tipo_flor", length = 100)
    @Size(max = 100, message = "El tipo de flor no puede tener más de 100 caracteres.")
    private String tipoFlor;

    @Column(name = "dureza_madera")
    private Integer durezaMadera;

    // DECIMAL(5,2) => BigDecimal + precision/scale
    @Column(name = "altura_promedio", precision = 5, scale = 2)
    @DecimalMin(value = "0.00", inclusive = true, message = "La altura promedio debe ser mayor o igual a 0.")
    private BigDecimal alturaPromedio;     // <-- cambiado a BigDecimal

    @Column(name = "imagen_ruta", columnDefinition = "TEXT")
    private String imagenRuta;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private Timestamp updatedAt;
}


