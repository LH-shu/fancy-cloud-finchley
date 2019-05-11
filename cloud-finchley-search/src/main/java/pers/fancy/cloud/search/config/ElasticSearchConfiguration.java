package pers.fancy.cloud.search.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Slf4j
@Configuration
@Component
public class ElasticSearchConfiguration {
    @Value("${dcp.elasticsearch.cluster-nodes}")
    private String clusterNodes;

    @Value("${dcp.elasticsearch.username}")
    private String username;

    @Value("${dcp.elasticsearch.password}")
    private String password;

    /**
     * 配置RestHighLevelClient
     * @return
     */
    @Bean
    public RestHighLevelClient buildClient() {

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,  new UsernamePasswordCredentials(username, password));
        String[] inetSocket = clusterNodes.split(":");
        String address = inetSocket[0];
        Integer port = Integer.valueOf(inetSocket[1]);

        RestClientBuilder.HttpClientConfigCallback httpClientConfigCallback =  new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                httpClientBuilder.disableAuthCaching();
                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            }
        };

        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost(address, port)).setHttpClientConfigCallback(httpClientConfigCallback));
        return client;
    }



    /**
     * 配置LowLevelRestClient
     * @return
     */
    @Bean
    public RestClient restClient() {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        String[] inetSocket = clusterNodes.split(":");
        String address = inetSocket[0];
        Integer port = Integer.valueOf(inetSocket[1]);

        credentialsProvider.setCredentials(AuthScope.ANY,new UsernamePasswordCredentials(username, password));
        RestClientBuilder builder = RestClient.builder(new HttpHost(address, port))
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                });
        RestClient restClient =   builder.build();
        return restClient;

        /*
        RestClient restClient =  RestClient.builder(new HttpHost(address, port, "http")).build();
        return restClient;
        */
    }
}