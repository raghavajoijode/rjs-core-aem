package org.subra.aem.rjs.core.ehcache.services;

import net.sf.ehcache.Ehcache;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.subra.aem.rjs.core.ehcache.helpers.CacheHelper;

import java.util.List;

public interface CacheService {

    <V> CacheHelper<V> getInstanceCache(String className, String countryCode);

    List<Ehcache> getEhcacheInstances();

    void clearByClassName(String className);

    @ObjectClassDefinition(name = "RJS EHCache Service", description = "Service for ehcache configuration")
    @interface Config {
        @AttributeDefinition(name = "cache.config", description = "RJS Cache ehcache.xml path")
        String cacheConfig() default "/etc/rjs/core/cache/ehcache.xml";
    }

}
