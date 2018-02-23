/*
 * oxTrust is available under the MIT License (2008). See http://opensource.org/licenses/MIT for full text.
 *
 * Copyright (c) 2018, Gluu
 */
package org.gluu.oxtrust.api.client;


import java.text.MessageFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import org.glassfish.jersey.filter.LoggingFilter;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.filter.EncodingFeature;
import org.glassfish.jersey.message.GZipEncoder;

/**
 * REST webservice CRUD template.
 * 
 * @author Dmitry Ognyannikov
 */
public class AbstractClient<T> {

    private static final Logger logger = LogManager.getLogger(AbstractClient.class);
    
    public static final int HTTP_OK = 200;
    
    protected WebTarget webTarget;
    protected Client client;
    protected Class<T> entityClass;
    
    public AbstractClient(Class<T> entityClass, Client client, String baseURI, String path) {
        this.entityClass = entityClass;
        this.client = client;
        
        webTarget = client.target(baseURI).path(path);
    }

    /**
     * Read entity by ID.
     * 
     * @param id ID of the entity (inum bu default)
     * @return entity instance
     * @throws ClientErrorException 
     */
    public T read(String id) throws ClientErrorException {
        WebTarget resource = webTarget.path(MessageFormat.format("read/{0}", new Object[]{id}));
        return resource.request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .get(entityClass);
    }

    /**
     * Create (save) entity.
     * 
     * @param requestEntity
     * @return ID of created entity (inum bu default)
     * @throws ClientErrorException
     * @throws OxTrustAPIException
     */
    public String create(T requestEntity) throws ClientErrorException, OxTrustAPIException {
        Response response = webTarget.path("create").request().post(Entity.entity(requestEntity, MediaType.APPLICATION_JSON));
        if (response.getStatus() != HTTP_OK) {
            throw new OxTrustAPIException("Response error. HTTP code: " + response.getStatus() + ", reason phrase: " + response.getStatusInfo().getReasonPhrase(), response.getStatus());
        }
        String id = response.readEntity(String.class);
        response.close();
        
        return id;
    }

    public boolean update(T requestEntity, String id) throws ClientErrorException {
        WebTarget resource = webTarget.path(MessageFormat.format("update/{0}", new Object[]{id}));
        
        Response response = resource.request().put(Entity.entity(requestEntity, MediaType.APPLICATION_JSON));
        
        int code = response.getStatus();
        response.close();
        return code == HTTP_OK;
    }

    public boolean delete(String id) throws ClientErrorException {
        WebTarget resource = webTarget.path(MessageFormat.format("delete/{0}", new Object[]{id}));
        Response response = resource.request(MediaType.TEXT_PLAIN).delete();
        
        int code = response.getStatus();
        response.close();
        return code == HTTP_OK;
    }

    public void close() {
        client.close();
    }
    
}

