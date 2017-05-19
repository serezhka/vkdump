package com.github.serezhka.vkdump.config;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;
import java.lang.reflect.Field;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
@Configuration
@PropertySource("classpath:vkdump.properties")
public class VkApiConfig {

    @Value("${proxy.host:#{null}}")
    private String proxyHost;

    @Value("${proxy.port:#{null}}")
    private Integer proxyPort;

    @Value("${vk.api.id}")
    private int id;

    @Value("${vk.api.token}")
    private String token;

    @Value("${vk.api.cooldown:1000}")
    private int cooldown;

    @Bean
    public UserActor tokenOwner() {
        return new UserActor(id, token);
    }

    @Bean
    public VkApiClient vkApiClient(HttpClient httpClient) throws NoSuchFieldException, IllegalAccessException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

        TransportClient transportClient = HttpTransportClient.getInstance();
        Field httpClientField = HttpTransportClient.class.getDeclaredField("httpClient");
        httpClientField.setAccessible(true);
        httpClientField.set(null, httpClient);

        return new VkApiClient(transportClient);
    }

    @Bean
    public HttpClient httpClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        SSLContextBuilder contextBuilder = new SSLContextBuilder();
        contextBuilder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(contextBuilder.build());

        HttpClientBuilder clientBuilder = HttpClients.custom();
        clientBuilder.setSSLSocketFactory(socketFactory);
        RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
        clientBuilder.setDefaultRequestConfig(requestConfig);

        if (proxyHost != null && proxyPort != null) {
            HttpHost proxy = new HttpHost(proxyHost, proxyPort);
            clientBuilder.setRoutePlanner(new DefaultProxyRoutePlanner(proxy));
        }

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(300);
        connectionManager.setDefaultMaxPerRoute(300);
        clientBuilder.setConnectionManager(connectionManager);

        BasicCookieStore cookieStore = new BasicCookieStore();
        clientBuilder.setDefaultCookieStore(cookieStore);
        clientBuilder.setUserAgent("Java VK SDK/0.4.3");

        clientBuilder.addInterceptorFirst(new HttpRequestInterceptor() {

            private long lastApiCallTime;

            @Override
            public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
                long cooldown = System.currentTimeMillis() - lastApiCallTime;
                if (cooldown < VkApiConfig.this.cooldown) {
                    try {
                        Thread.sleep(VkApiConfig.this.cooldown - cooldown);
                    } catch (InterruptedException ignored) {
                    }
                }
                lastApiCallTime = System.currentTimeMillis();
            }
        });

        return clientBuilder.build();
    }
}
