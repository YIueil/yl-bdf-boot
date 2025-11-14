package cc.yiueil.configuration;

import cc.yiueil.controller.SearchController;
import cc.yiueil.data.impl.JpaBaseDao;
import cc.yiueil.query.ConfigResolver;
import cc.yiueil.query.DynamicQueryPool;
import cc.yiueil.query.impl.SimpleConfigResolver;
import cc.yiueil.service.SearchService;
import cc.yiueil.service.impl.SearchServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ConditionalOnClass(DataSource.class)
public class JpaBaseDaoConfiguration {
    @Bean
    @ConditionalOnBean(value = DataSource.class)
    @ConditionalOnMissingBean(value = JpaBaseDao.class)
    public JpaBaseDao jpaBaseDao() {
        return new JpaBaseDao();
    }

    @Bean
    @ConditionalOnBean(value = JpaBaseDao.class)
    @ConditionalOnMissingBean(value = ConfigResolver.class)
    public ConfigResolver configResolver() {
        return new SimpleConfigResolver();
    }

    @Bean
    @ConditionalOnBean(value = JpaBaseDao.class)
    @ConditionalOnMissingBean(value = DynamicQueryPool.class)
    public DynamicQueryPool DynamicQueryPool() {
        return new DynamicQueryPool();
    }

    @Bean
    @ConditionalOnBean(value = JpaBaseDao.class)
    @ConditionalOnMissingBean(value = SearchController.class)
    public SearchController searchController() {
        return new SearchController();
    }

    @Bean
    @ConditionalOnBean(value = JpaBaseDao.class)
    @ConditionalOnMissingBean(value = SearchService.class)
    public SearchService searchService() {
        return new SearchServiceImpl();
    }
}
