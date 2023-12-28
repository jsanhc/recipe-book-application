package org.jsanchez.recipe.application.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jsanchez.recipe.application.types.converters.MealTypeConverter;
import org.jsanchez.recipe.application.types.MealType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "recipes")
public class Recipe implements Serializable {

    @Expose
    @Id
    @Column(unique = true, nullable = false)
    private String reference;
    @Expose
    @Column(nullable = false)
    private String name;
    @Expose
    private String link;
    @Expose
    private Integer portions;
    @Expose
    @SerializedName(value = "prepare_time", alternate = "prepareTime")
    @Column(name = "prepare_time", nullable = false, precision = 2, scale = 2)
    private Double prepareTime;
    @Expose
    @Column(nullable = false)
    @Convert(converter = MealTypeConverter.class)
    private MealType meal;
    @Expose
    @Column(length = 500)
    private String instructions;
    @Expose
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @JoinColumn(name = "recipe_id")
    private List<Ingredient> ingredients = new ArrayList<>();

    @Expose
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @JoinColumn(name = "recipe_id")
    private List<Member> members = new ArrayList<>();

    public Recipe addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        return this;
    }

    public Recipe addIngredients(List<Ingredient> ingredients) {
        this.ingredients.addAll(ingredients);
        return this;
    }

    public Recipe addMember(Member member) {
        this.members.add(member);
        return this;
    }

    public Recipe addMembers(List<Member> members) {
        this.members.addAll(members);
        return this;
    }

    public void generateReference() {
        this.reference = UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "reference=" + reference +
                ", name='" + name + '\'' +
                ", link=" + link +
                ", portions=" + portions +
                ", prepareTime=" + prepareTime +
                ", meal=" + meal +
                ", instructions=" + instructions +
                ", ingredients=" + ingredients +
                ", members=" + members +
                '}';
    }
}
