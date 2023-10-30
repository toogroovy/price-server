package com.briandidthat.priceserver.service;

import com.briandidthat.priceserver.domain.exception.BadRequestException;
import com.briandidthat.priceserver.domain.fred.FredResponse;
import com.briandidthat.priceserver.domain.fred.Observation;
import com.briandidthat.priceserver.util.RequestUtilities;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class FredService {
    private static final Logger logger = LoggerFactory.getLogger(FredService.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    @Value("${apis.fred.baseUrl}")
    private String fredBaseUrl;
    @Value("${apis.fred.apiKey}")
    private String fredApiKey;
    @Autowired
    private RestTemplate restTemplate;

    public List<Observation> getObservations(String seriesId, Map<String, Object> params) {
        params.putAll(Map.of("series_id", seriesId, "file_type", "json", "api_key", fredApiKey));
        final String url =  RequestUtilities.formatQueryString(fredBaseUrl + "/series/observations", params);
        try {
            final ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            final FredResponse fredResponse = mapper.readValue(response.getBody(), new TypeReference<>() {});
            return fredResponse.getObservations();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }


}
