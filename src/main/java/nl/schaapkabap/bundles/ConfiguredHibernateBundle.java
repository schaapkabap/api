package nl.schaapkabap.bundles;

import com.google.common.collect.ImmutableList;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.SessionFactoryFactory;
import nl.schaapkabap.configuration.ApiConfiguration;

public class ConfiguredHibernateBundle extends HibernateBundle<ApiConfiguration> {

    public ConfiguredHibernateBundle(Class<?> entity, Class<?>... entities) {
        super(entity, entities);
    }

    public ConfiguredHibernateBundle(ImmutableList<Class<?>> entities, SessionFactoryFactory sessionFactoryFactory) {
        super(entities, sessionFactoryFactory);
    }

    @Override
    public PooledDataSourceFactory getDataSourceFactory(ApiConfiguration configuration) {
        return configuration.getDataSourceFactory();
    }
}
