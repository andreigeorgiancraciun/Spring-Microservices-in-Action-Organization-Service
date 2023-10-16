package com.optimagrowth.organization.service;

import java.util.Optional;
import java.util.UUID;

import com.optimagrowth.organization.event.service.KafkaProducerService;
import com.optimagrowth.organization.utils.UserContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.optimagrowth.organization.model.Organization;
import com.optimagrowth.organization.repository.OrganizationRepository;

@Service
public class OrganizationService {
    private static final Logger logger = LoggerFactory.getLogger(OrganizationService.class);
    private final OrganizationRepository repository;
    private final KafkaProducerService kafkaProducerService;


    public OrganizationService(OrganizationRepository repository, KafkaProducerService kafkaProducerService) {
        this.repository = repository;
        this.kafkaProducerService = kafkaProducerService;
    }

    public Organization findById(String organizationId) {
        logger.debug("findOrganizationById Correlation id: {}",
                UserContextHolder.getContext().getCorrelationId());

        Optional<Organization> opt = repository.findById(organizationId);
        opt.ifPresent(organization -> kafkaProducerService.publishOrganizationChange("GET", organization));
        return opt.orElse(null);
    }

    public Organization create(Organization organization) {
        organization.setId(UUID.randomUUID().toString());
        organization = repository.save(organization);
        kafkaProducerService.publishOrganizationChange("SAVE", organization);
        return organization;

    }

    public void update(Organization organization) {
        repository.save(organization);
        kafkaProducerService.publishOrganizationChange("UPDATE", organization);
    }

    public void delete(String organizationId) {
        Optional<Organization> opt = repository.findById(organizationId);
        if (opt.isPresent()) {
            repository.deleteById(organizationId);
            kafkaProducerService.publishOrganizationChange("DELETE", opt.get());
        }
    }
}