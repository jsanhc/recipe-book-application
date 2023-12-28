package org.jsanchez.recipe.application.model;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jsanchez.recipe.application.types.converters.FamilyMemberTypeConverter;
import org.jsanchez.recipe.application.types.FamilyMemberType;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "member")
public class Member {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Expose
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Convert(converter = FamilyMemberTypeConverter.class)
    private FamilyMemberType member;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipeToMembers;

    @Override
    public String toString() {
        return "member {" +
                "id=" + id +
                ", member=" + member +
                '}';
    }
}
