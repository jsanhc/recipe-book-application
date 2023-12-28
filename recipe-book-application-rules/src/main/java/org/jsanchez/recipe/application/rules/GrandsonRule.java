package org.jsanchez.recipe.application.rules;

import org.jsanchez.recipe.application.model.InquiryRequest;
import org.jsanchez.recipe.application.model.InquiryResponse;
import org.jsanchez.recipe.application.model.Recipe;
import org.jsanchez.recipe.application.rules.util.PrepareTimeUtil;
import org.jsanchez.recipe.application.types.FamilyMemberType;
import org.jsanchez.recipe.application.types.WorthType;

import java.util.Objects;

/**
 * Rules for 'GRANDSON'
 */
public class GrandsonRule extends PrepareTimeUtil implements Rule<InquiryRequest, Recipe> {

    @Override
    public InquiryResponse run(InquiryRequest inquiry, Recipe recipe) {
        if (Objects.nonNull(recipe) && Objects.nonNull(recipe.getMembers())) {
            if (recipe.getMembers().stream().anyMatch((member) -> member.getMember().equals(inquiry.getWho()))) {
                return buildResponse(recipe);
            }
        }
        return InquiryResponse.builder()
                .reference(inquiry.getReference())
                .worth(WorthType.INVALID)
                .build();
    }

    @Override
    public InquiryResponse buildResponse(Recipe recipe) {
        InquiryResponse inquiryResponse = InquiryResponse.builder()
                .reference(recipe.getReference())
                .prepareTime(recipe.getPrepareTime())
                .portions(recipe.getPortions())
                .worth(WorthType.YEAH)
                .build();
        int comparatorResult = recipe.getPrepareTime().compareTo(DEFAULT_OPERATOR_VALUE);
        if (comparatorResult < 0) {
            inquiryResponse.setPrepareTime(
                    subtract(recipe.getPrepareTime(), minutesToHours(15.00))
            );
        } else if (comparatorResult > 0) {
            inquiryResponse.setPrepareTime(
                    subtract(recipe.getPrepareTime(), minutesToHours(5.00))
            );
            inquiryResponse.setPortions(null);
        } else {
            inquiryResponse.setPrepareTime(
                    subtract(recipe.getPrepareTime(), minutesToHours(10.00))
            );
        }
        return inquiryResponse;
    }

    @Override
    public FamilyMemberType getFamilyTypeMember() {
        return FamilyMemberType.GRANDSON;
    }
}