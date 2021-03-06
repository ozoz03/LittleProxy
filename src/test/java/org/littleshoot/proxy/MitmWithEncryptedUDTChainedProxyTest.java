package org.littleshoot.proxy;

import static org.littleshoot.proxy.TransportProtocol.*;

import javax.net.ssl.SSLEngine;

import org.littleshoot.proxy.extras.SelfSignedSslEngineSource;

public class MitmWithEncryptedUDTChainedProxyTest extends MitmWithChainedProxyTest {
    private final SslEngineSource sslEngineSource = new SelfSignedSslEngineSource(
            "chain_proxy_keystore_1.jks");

    @Override
    protected HttpProxyServerBootstrap upstreamProxy() {
        return super.upstreamProxy()
                .withTransportProtocol(UDT)
                .withSslEngineSource(sslEngineSource);
    }

    @Override
    protected ChainedProxy newChainedProxy() {
        return new BaseChainedProxy() {
            @Override
            public TransportProtocol getTransportProtocol() {
                return TransportProtocol.UDT;
            }

            @Override
            public boolean requiresEncryption() {
                return true;
            }

            @Override
            public SSLEngine newSslEngine() {
                return sslEngineSource.newSslEngine();
            }

            @Override
            public SSLEngine newSslEngine(String peerHost, int peerPort) {
                return sslEngineSource.newSslEngine(peerHost, peerPort);
            }
        };
    }
}
