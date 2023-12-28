package org.jsanchez.recipe.application.model;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "ingredients")
public class Ingredient implements Serializable {

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Expose
    @Column(nullable = false)
    private String name;
    @Expose
    @Column(nullable = false, precision = 10, scale = 2)
    private Double amount;
    @Expose
    @Column(nullable = false)
    private String unit;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipeToIngredient;

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name=" + name +
                ", amount=" + amount +
                ", unit=" + unit +
                '}';
    }
}
