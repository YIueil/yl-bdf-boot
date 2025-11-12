package cc.yiueil.configuration;

import cc.yiueil.controller.SearchController;
import cc.yiueil.data.impl.JpaBaseDao;
import cc.yiueil.query.ConfigResolver;
import cc.yiueil.query.DynamicQueryPool;
import cc.yiueil.query.impl.SimpleConfigResolver;
import cc.yiueil.service.SearchService;
import cc.yiueil.service.impl.SearchServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ConditionalOnClass(DataSource.class)
public class JpaBaseDaoConfiguration {
    @Bean
    @ConditionalOnClass(value = JpaBaseDao.class)
    public JpaBaseDao jpaBaseDao() {
        return new JpaBaseDao();
    }

    @Bean
    @ConditionalOnClass(value = JpaBaseDao.class)
    public ConfigResolver configResolver() {
        return new SimpleConfigResolver();
    }

    @Bean
    @ConditionalOnClass(value = JpaBaseDao.class)
    public DynamicQueryPool DynamicQueryPool() {
        return new DynamicQueryPool();
    }

    @Bean
    @ConditionalOnClass(value = JpaBaseDao.class)
    public SearchController searchController() {
        return new SearchController();
    }

    @Bean
    @ConditionalOnClass(value = JpaBaseDao.class)
    public SearchService searchService() {
        return new SearchServiceImpl();
    }
}
