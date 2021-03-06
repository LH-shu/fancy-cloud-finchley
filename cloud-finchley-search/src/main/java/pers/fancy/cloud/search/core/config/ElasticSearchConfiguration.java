package pers.fancy.cloud.search.core.config;

import pers.fancy.cloud.search.core.util.Constant;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StringUtils;

/**
 * 自动配置注入restHighLevelClient
 *
 * @author LiLiChai
 */
@Configuration
@ComponentScan("pers.fancy.cloud.search.core")
public class ElasticSearchConfiguration {

    @Value("${elasticsearch.host}")
    private String host;


    private RestHighLevelClient restHighLevelClient;

//    由于@Bean(destroyMethod="close")，所以不需要下面注释掉的释放方式
//    public void close() {
//        if (restHighLevelClient != null) {
//            try {
//                restHighLevelClient.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @PreDestroy
//    public void destroyMethod() throws Exception {
//        close();
//    }

    @Bean(destroyMethod = "close")//这个close是调用RestHighLevelClient中的close
    @Scope("singleton")
    public RestHighLevelClient createInstance() {
        try {
            if (StringUtils.isEmpty(host)) {
                host = Constant.DEFAULT_ES_HOST;
            }
            String[] hosts = host.split(",");
            HttpHost[] httpHosts = new HttpHost[hosts.length];
            for (int i = 0; i < httpHosts.length; i++) {
                String h = hosts[i];
                httpHosts[i] = new HttpHost(h.split(":")[0], Integer.parseInt(h.split(":")[1]), "http");
            }
            restHighLevelClient = new RestHighLevelClient(
                    RestClient.builder(httpHosts));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return restHighLevelClient;
    }
}
