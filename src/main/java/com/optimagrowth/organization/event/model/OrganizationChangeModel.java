package com.optimagrowth.organization.event.model;

import com.optimagrowth.organization.model.Organization;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class OrganizationChangeModel {
    private String type;
    private String action;
    private Organization organization;
    private String correlationId;
}
