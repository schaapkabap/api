package nl.hsleiden;


import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.ImmutableSet;
import com.palantir.indexpage.IndexPageBundle;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.bundles.assets.ConfiguredAssetsBundle;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import io.dropwizard.jersey.jackson.JsonProcessingExceptionMapper;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import nl.hsleiden.configuration.ApiConfiguration;
import nl.hsleiden.persistence.UserDAO;
import org.eclipse.jetty.server.Authentication;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import java.util.TimeZone;

public class ApiApplication extends Application<ApiConfiguration> {

    
}
