package com.optimagrowth.organization.event.service;

import com.optimagrowth.organization.event.model.OrganizationChangeModel;
import com.optimagrowth.organization.utils.UserContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);
    private static final String TOPIC_NAME = "orgChangeTopic";

    private final KafkaTemplate<String, OrganizationChangeModel> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, OrganizationChangeModel> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishOrganizationChange(String action, String organizationId) {
        logger.debug("Sending Kafka message {} for Organization Id: {}", action, organizationId);
        OrganizationChangeModel change = new OrganizationChangeModel(
                OrganizationChangeModel.class.getTypeName(),
                action,
                organizationId,
                UserContextHolder.getContext().getCorrelationId());

        kafkaTemplate.send(TOPIC_NAME, change);
    }
}
