package org.jsanchez.recipe.application.model;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.jsanchez.recipe.application.types.FamilyMemberType;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class InquiryRequest extends Inquiry {
    @Expose
    private FamilyMemberType who;
}
