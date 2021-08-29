package jjimenez22.csvjson.config;

import jjimenez22.csvjson.model.PricePaid;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.ExposureConfiguration;
import org.springframework.http.HttpMethod;

@Configuration
public class WebConfiguration {
    private final RepositoryRestConfiguration repositoryRestConfiguration;

    public WebConfiguration(RepositoryRestConfiguration repositoryRestConfiguration) {
        this.repositoryRestConfiguration = repositoryRestConfiguration;

        config();
    }

    private void config() {
        ExposureConfiguration config = repositoryRestConfiguration.getExposureConfiguration();

        config.forDomainType(PricePaid.class).disablePutForCreation();
        config.withItemExposure((metadata, httpMethods) -> httpMethods.disable(HttpMethod.PATCH, HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE));
    }
}
