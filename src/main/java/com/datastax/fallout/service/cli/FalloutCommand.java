/*
 * Copyright 2020 DataStax, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.datastax.fallout.service.cli;

import io.dropwizard.cli.ConfiguredCommand;
import io.dropwizard.setup.Bootstrap;
import net.sourceforge.argparse4j.inf.Namespace;

import com.datastax.fallout.service.FalloutConfiguration;
import com.datastax.fallout.service.FalloutConfigurationFactoryFactory;

public abstract class FalloutCommand extends ConfiguredCommand<FalloutConfiguration>
{
    protected FalloutCommand(String name, String description)
    {
        super(name, description);
    }

    private void forceConsoleLoggingOnly(Bootstrap<FalloutConfiguration> bootstrap)
    {
        final FalloutConfigurationFactoryFactory falloutConfigurationFactoryFactory =
            new FalloutConfigurationFactoryFactory(bootstrap.getConfigurationFactoryFactory(),
                falloutConfiguration -> {
                    falloutConfiguration.forceLoggingToConsoleOnly();
                    falloutConfiguration.updateLogFormat();
                });

        bootstrap.setConfigurationFactoryFactory(falloutConfigurationFactoryFactory);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void run(Bootstrap<?> wildcardBootstrap, Namespace namespace) throws Exception
    {
        forceConsoleLoggingOnly((Bootstrap<FalloutConfiguration>) wildcardBootstrap);

        super.run(wildcardBootstrap, namespace);
    }
}
