package com.github.serezhka.vkdump.vkapi;

import org.apache.http.HttpHost;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
@Component
public class VkApiHttpsClient {

    private static final Logger LOGGER = Logger.getLogger(VkApiHttpsClient.class);

    private RestTemplate restTemplate;

    @Value("${proxy.host:#{null}}")
    private String proxyHost;

    @Value("${proxy.port:0}")
    private int proxyPort;

    @Value("${vk.api.url}")
    private String apiUrl;

    @Value("${vk.api.accessToken}")
    private String accessToken;

    @Value("${vk.api.version}")
    private String apiVersion;

    @Value("${vk.api.delay}")
    private long apiDelay;

    private long lastExecTime;

    @PostConstruct
    public void init() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        SSLContextBuilder contextBuilder = new SSLContextBuilder();
        contextBuilder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(contextBuilder.build());
        HttpClientBuilder clientBuilder = HttpClients.custom();
        clientBuilder.setSSLSocketFactory(socketFactory);
        RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
        clientBuilder.setDefaultRequestConfig(requestConfig);

        if (proxyHost != null) {
            HttpHost proxy = new HttpHost(proxyHost, proxyPort);
            clientBuilder.setRoutePlanner(new DefaultProxyRoutePlanner(proxy));
        }

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(clientBuilder.build());
        restTemplate = new RestTemplate(requestFactory);
    }

    public String execVkApiMethod(String method, Map<String, String> args) {

        LOGGER.debug("Call method " + method + " with args: " + args);

        long delta = System.currentTimeMillis() - lastExecTime;
        if (delta < apiDelay) {
            try {
                Thread.sleep(apiDelay - delta);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(apiUrl + method);
        for (Map.Entry<String, String> arg : args.entrySet()) {
            uriBuilder.queryParam(arg.getKey(), arg.getValue());
        }
        uriBuilder.queryParam("access_token", accessToken);
        uriBuilder.queryParam("v", apiVersion);

        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);

        String result = restTemplate.exchange(
                uriBuilder.build().encode().toUri(),
                HttpMethod.GET,
                httpEntity,
                String.class).getBody();

        lastExecTime = System.currentTimeMillis();

        LOGGER.debug("Received response: " + result);

        return result;
    }
}
