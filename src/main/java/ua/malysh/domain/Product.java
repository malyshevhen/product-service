package ua.malysh.domain;

import static jakarta.persistence.GenerationType.IDENTITY;

import java.util.Objects;

import org.hibernate.Hibernate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products",
        indexes = {
                @Index(name = "idx_products_product_name", columnList = "product_name")},
        uniqueConstraints = {
                @UniqueConstraint(name = "uc_products_product_name", columnNames = "product_name")})
@Getter @Setter
@NoArgsConstructor 
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "product_id",
            nullable = false)
    private Long id;

    @Column(name = "product_name",
            nullable = false,
            unique = true)
    private String name;

    @Column(name = "product_measure",
            nullable = false)
    @Enumerated(EnumType.STRING)
    private Measure measure;

    @Column(name = "product_category",
            nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "product_description")
    private String description;

    public Product(String name,
                   Category category,
                   Measure measure) {
        this.name = name;
        this.category = category;
        this.measure = measure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Product product = (Product) o;
        return getId() != null && Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

