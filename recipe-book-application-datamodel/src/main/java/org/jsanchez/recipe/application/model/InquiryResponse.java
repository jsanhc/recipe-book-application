package org.jsanchez.recipe.application.model;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.jsanchez.recipe.application.types.converters.FamilyMemberTypeConverter;
import org.jsanchez.recipe.application.types.WorthType;
import org.jsanchez.recipe.application.types.converters.WorthTypeConverter;

import javax.persistence.Convert;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class InquiryResponse extends Inquiry {
    @Expose
    @Convert(converter = WorthTypeConverter.class)
    private WorthType worth;
    @Expose
    private Integer portions;
    @Expose
    private Double prepareTime;

}
