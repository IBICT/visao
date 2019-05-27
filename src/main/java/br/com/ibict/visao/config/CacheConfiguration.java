package br.com.ibict.visao.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(br.com.ibict.visao.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(br.com.ibict.visao.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(br.com.ibict.visao.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.ibict.visao.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.ibict.visao.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(br.com.ibict.visao.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.ibict.visao.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(br.com.ibict.visao.domain.Indicator.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.ibict.visao.domain.Name.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.ibict.visao.domain.Category.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.ibict.visao.domain.Region.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.ibict.visao.domain.Region.class.getName() + ".relacaos", jcacheConfiguration);
            cm.createCache(br.com.ibict.visao.domain.Year.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.ibict.visao.domain.MetaDado.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.ibict.visao.domain.MetaDado.class.getName() + ".nomes", jcacheConfiguration);
            cm.createCache(br.com.ibict.visao.domain.Filter.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.ibict.visao.domain.Filter.class.getName() + ".regions", jcacheConfiguration);
            cm.createCache(br.com.ibict.visao.domain.Layer.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.ibict.visao.domain.MarkerIcon.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.ibict.visao.domain.GroupLayer.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.ibict.visao.domain.TypePresentation.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
