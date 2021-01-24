package org.subra.aem.rjs.core.solr.services;

import com.day.cq.search.result.SearchResult;
import org.apache.sling.api.resource.Resource;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.jcr.RepositoryException;
import java.io.IOException;

/**
 * @author Raghava Joijode
 */
public interface SolrSearchService {

    JSONArray crawlContent(String resourcePath, String resourceType);

    JSONArray createPageMetadataArray(SearchResult results) throws RepositoryException;

    JSONObject createPageMetadataObject(Resource pageContent);

    boolean indexPageToSolr(JSONObject indexPageData, HttpSolrClient server) throws JSONException, SolrServerException, IOException;

    boolean indexPagesToSolr(JSONArray indexPageData, HttpSolrClient server) throws JSONException, SolrServerException, IOException;

}