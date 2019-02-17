package nl.schaapkabap.bundles;


import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.migrations.MigrationsBundle;
import nl.schaapkabap.configuration.ApiConfiguration;


public class ConfiguredMigrationBundle extends MigrationsBundle<ApiConfiguration> {

    @Override
    public PooledDataSourceFactory getDataSourceFactory(ApiConfiguration configuration) {
        return configuration.getDataSourceFactory();
    }

}
