package br.com.ibict.config;

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
            cm.createCache(br.com.ibict.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(br.com.ibict.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(br.com.ibict.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.ibict.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.ibict.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(br.com.ibict.domain.Indicator.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.ibict.domain.Indicator.class.getName() + ".metaDados", jcacheConfiguration);
            cm.createCache(br.com.ibict.domain.GrupIndicator.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.ibict.domain.GrupIndicator.class.getName() + ".metaDados", jcacheConfiguration);
            cm.createCache(br.com.ibict.domain.GrupIndicator.class.getName() + ".categories", jcacheConfiguration);
            cm.createCache(br.com.ibict.domain.TypePresentation.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.ibict.domain.Category.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.ibict.domain.Region.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.ibict.domain.Region.class.getName() + ".relacaos", jcacheConfiguration);
            cm.createCache(br.com.ibict.domain.Year.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.ibict.domain.MetaDado.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.ibict.domain.GeographicFilter.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.ibict.domain.GeographicFilter.class.getName() + ".metaDados", jcacheConfiguration);
            cm.createCache(br.com.ibict.domain.GeographicFilter.class.getName() + ".regions", jcacheConfiguration);
            cm.createCache(br.com.ibict.domain.GeographicFilter.class.getName() + ".categories", jcacheConfiguration);
            cm.createCache(br.com.ibict.domain.Layer.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.ibict.domain.Layer.class.getName() + ".metaDados", jcacheConfiguration);
            cm.createCache(br.com.ibict.domain.MarkerIcon.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.ibict.domain.GroupLayer.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.ibict.domain.GroupLayer.class.getName() + ".metaDados", jcacheConfiguration);
            cm.createCache(br.com.ibict.domain.GroupLayer.class.getName() + ".categories", jcacheConfiguration);
            cm.createCache(br.com.ibict.domain.Chart.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.ibict.domain.Chart.class.getName() + ".shareds", jcacheConfiguration);
            cm.createCache(br.com.ibict.domain.GrupCategory.class.getName(), jcacheConfiguration);
            cm.createCache(br.com.ibict.domain.GrupCategory.class.getName() + ".categories", jcacheConfiguration);
            cm.createCache(br.com.ibict.domain.GrupCategory.class.getName() + ".shareds", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
