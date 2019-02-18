package nl.schaapkabap;


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
import nl.schaapkabap.auth.BasicAuthenticator;
import nl.schaapkabap.auth.BasicAuthorizer;
import nl.schaapkabap.bundles.ConfiguredHibernateBundle;
import nl.schaapkabap.bundles.ConfiguredMigrationBundle;
import nl.schaapkabap.configuration.ApiConfiguration;
import nl.schaapkabap.filters.CORSFilter;
import nl.schaapkabap.models.Product;
import nl.schaapkabap.models.ProductCategory;
import nl.schaapkabap.models.Role;
import nl.schaapkabap.models.User;
import nl.schaapkabap.persistence.ProductCategoryDAO;
import nl.schaapkabap.persistence.ProductDAO;
import nl.schaapkabap.persistence.UserDAO;
import nl.schaapkabap.resource.AuthResource;
import nl.schaapkabap.resource.ProductCategoryResource;
import nl.schaapkabap.resource.ProductResource;
import nl.schaapkabap.resource.UserResource;
import nl.schaapkabap.service.UserService;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import java.util.TimeZone;

public class ApiApplication extends Application<ApiConfiguration> {

    private final ConfiguredHibernateBundle userHibernateBundle = new ConfiguredHibernateBundle(
            User.class,
            Role.class,
            Product.class,
            ProductCategory.class
    );

    private UserDAO userDAO;
    private ProductDAO productDAO;
    private ProductCategoryDAO productCategoryDAO;

    public static void main(String[] args) throws Exception {
        new ApiApplication().run(args);
    }

    private void setupFilters(Environment environment) {
        environment.jersey().register(new CORSFilter());
    }
    @Override
    public void initialize(Bootstrap<ApiConfiguration> bootstrap) {
        super.initialize(bootstrap);

        // Setup migration bundle
        bootstrap.addBundle(new ConfiguredMigrationBundle());

        bootstrap.addBundle(new MultiPartBundle());

        // Setup hibernate bundles for the DAOS
        bootstrap.addBundle(this.userHibernateBundle);

        // Bootstrapping the Angular SPA
        bootstrap.addBundle(new IndexPageBundle("/assets/index.html", ImmutableSet.of("/login", "/app/*")));
        bootstrap.addBundle(new ConfiguredAssetsBundle("/assets", "/", "index.html"));
    }

    @Override
    public void run(ApiConfiguration configuration,
                    Environment environment) throws Exception {

        // Do general setup
        this.setupFilters(environment);
        this.setupDaos(environment);
        this.setupResources(environment);
        this.setupAuthentication(environment);
    }

    private void setupDaos(Environment environment) {
        this.userDAO = new UserDAO(this.userHibernateBundle.getSessionFactory());
        this.productDAO = new ProductDAO(this.userHibernateBundle.getSessionFactory());
        this.productCategoryDAO = new ProductCategoryDAO(this.userHibernateBundle.getSessionFactory());
    }

    private void setupAuthentication(Environment environment) {
        // Create proxy class
        BasicAuthenticator proxyAuth = new UnitOfWorkAwareProxyFactory(this.userHibernateBundle)
                .create(BasicAuthenticator.class, UserDAO.class, this.userDAO);

        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(proxyAuth)
                        .setAuthorizer(new BasicAuthorizer())
                        .setPrefix("Basic")
                        .buildAuthFilter()));

        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
    }

    private void setupResources(Environment environment) {
        environment.getObjectMapper().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        environment.getObjectMapper().setTimeZone(TimeZone.getTimeZone("GMT+1"));

        // Enable more JSON processing details
        environment.jersey().register(new JsonProcessingExceptionMapper(true));

        environment.jersey().register(new AuthResource(this.userDAO));
        environment.jersey().register(new UserResource(new UserService(this.userDAO)));
        environment.jersey().register(new ProductResource(this.productDAO));
        environment.jersey().register(new ProductCategoryResource(this.productCategoryDAO));

    }
    
}
