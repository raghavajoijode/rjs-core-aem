package org.subra.aem.rjs.core.ehcache.services;

import net.sf.ehcache.Ehcache;
import org.subra.aem.rjs.core.ehcache.helpers.CacheHelper;

import java.util.List;

public interface CacheService {

    <V> CacheHelper<V> getInstanceCache(String className, String countryCode);

    List<Ehcache> getEhcacheInstances();

    void clearByClassName(String className);

}
