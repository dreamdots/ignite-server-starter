package ru.dreamdots.ignite.server;

import org.apache.ignite.IgniteCheckedException;
import org.apache.ignite.IgniteLogger;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.logger.log4j2.Log4J2Logger;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;

import java.util.Arrays;
import java.util.List;

public class Starter {

    public static void main(String[] args) throws IgniteCheckedException {
        runLocalIgnite();
    }

    public static void runLocalIgnite() throws IgniteCheckedException {
        final IgniteConfiguration config = createConfig();
        Ignition.start(config);
    }

    public static IgniteConfiguration createConfig() throws IgniteCheckedException {
        final String[] addressesConf = new String[]{"127.0.0.1"};
        final IgniteConfiguration configuration = new IgniteConfiguration();
        final TcpDiscoverySpi discoSpi = new TcpDiscoverySpi();
        final TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();
        final List<String> addresses = Arrays.asList(addressesConf);
        final IgniteLogger log = new Log4J2Logger("src/main/resources/log4j2.xml");
        configuration.setGridLogger(log);

        ipFinder
                .setAddresses(addresses)
                .setShared(true);
        discoSpi.setIpFinder(ipFinder);

        configuration.setDiscoverySpi(discoSpi);
        configuration.setConsistentId("analyzer_kekv_server");
        configuration.setClientMode(false);
        return configuration;
    }
}
