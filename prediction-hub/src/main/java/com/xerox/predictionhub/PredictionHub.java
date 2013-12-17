package com.xerox.predictionhub;

import com.xerox.services.HubEngine;
import com.xerox.services.LUPEngine;

import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Iterator;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.apache.clerezza.rdf.core.Graph;

import org.apache.clerezza.rdf.core.MGraph;
import org.apache.clerezza.rdf.core.Triple;
import org.apache.clerezza.rdf.core.UriRef;
import org.apache.clerezza.rdf.core.access.TcManager;
import org.apache.clerezza.rdf.ontologies.DC;
import org.apache.clerezza.rdf.ontologies.RDF;
import org.apache.clerezza.rdf.utils.GraphNode;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of the predictor engine service
 * @author aczerny
 */
@Component(metatype = true, immediate = true)
@Service
@Property(name = "javax.ws.rs", boolValue = true)
@Path("predictionhub")
public class PredictionHub implements HubEngine {

    /**
     * Using slf4j for logging
     */
    private static final Logger log = LoggerFactory.getLogger(PredictionHub.class);
    
    /**
     * Accessing the TripleCollection Manager via the OSGi framework
     */
    @Reference
    private TcManager tcManager;
    /**
     * Uri Reference to access the AnnoStore (Listener)
     */
    private UriRef ANNOTATION_GRAPH_NAME = new UriRef("urn:x-localinstance:/fusepool/annotation.graph");
    private MGraph annostore;
    
    /**
     * List of probes. Don't forget to insert any probes you need into this list
     * at activation !
     */
    private HashMap<String, LUPEngine> probesList;
    
    public String getAnnostore() {
        return ANNOTATION_GRAPH_NAME.toString();
    }
    
    public void register(LUPEngine p) {
        /**
         * 1) Check if the probe is not already registered
         * 2) Add it to the List
         * 3) Plug the Listener with the TripleFilter to the AS
         */
        String name = p.getName();
        if (probesList.containsKey(name)) {
            return;
        }
        probesList.put(name, p);
        annostore.addGraphListener(p.getListener(), p.getFilter(), p.getDelay());
        System.out.println("[PREDICTION HUB] LUP Module " + p.getName() + " registered !");
        System.out.println("[PREDICTION HUB] LUP registered :");
        for (String key : probesList.keySet()) {
            System.out.println("[PREDICTION HUB]   - " + key);
        }
        System.out.println("[PREDICTION HUB] Registering done");
    }
    
    public void unregister(LUPEngine p) {
        /**
         * We have to remove the listener from the Annostore,
         * then remove the LUPEngine from the HashMap
         */
        System.out.println("[PREDICTION HUB] Unregister()");
        annostore.removeGraphListener(p.getListener());
        probesList.remove(p.getName());
        System.out.println("[PREDICTION HUB] Probe " + p.getName() + " removed.");
        System.out.println("[PREDICTION HUB] LUP registered :");
        for (String key : probesList.keySet()) {
            System.out.println("[PREDICTION HUB]   - " + key);
        }
    }
    
    @Activate
    private void activator() throws IOException {
        try {
            /**
             * Refactoring the project, bundle initialization :
             *  1) accessing AnnoStore
             *  2) creating new ProbesList
             *  3) that's it (everything is more OSGied now...)
             */
            
            // 1.) Accessing Annostore
            tcManager.getMGraph(ANNOTATION_GRAPH_NAME);
            annostore = tcManager.getMGraph(ANNOTATION_GRAPH_NAME);
            
            // 2.) Creating ProbesList
            probesList = new HashMap<String,LUPEngine>();
            
            // 3.) That's it
            System.out.println("[PREDICTION HUB] Started !");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    @Deactivate
    private void deactivator() {
        // Removing every probes' listener from the annostore
        if (annostore != null) {
            for (LUPEngine p : probesList.values()) {
                annostore.removeGraphListener(p.getListener());
            }
        }
        System.out.println("[PREDICTION HUB] Stopped !");
        /**
         * Note that we don't empty the probesList collection since a new ArrayList
         * instance will be created at (re-)activation. Garbage collector will
         * handle the memory leak we induce !
         */
    }

    public String predict(String task, String param) {
        /**
         * We simply routes the predict method through the probesList hashList
         */
        if (!probesList.containsKey(task)) {
            return "NO SUCH PREDICTOR";
        }
        return probesList.get(task).predict(param);
    }
    
    /**
     * Returns the context of a resource. This includes any Annotation on that
     * resource.
     */
    
    @GET
    public String testService(@Context final UriInfo uriInfo,
            @QueryParam("query") final String query) throws Exception {
        System.out.println("[PREDICTIONHUB] TestService(): " + query);
        
        String uriInfoStr = uriInfo.getRequestUri().toString();
        
        String response = "no query. Add a query param for a response message /test?query=<your query>";
        
        if(!(query == null)) {
        	response = "parrot's response: " + query;
        }
        
        return "Test PredictionHub service Ok. Request URI: " + uriInfoStr + ". Response: " + response;
    }
    
    @POST
    public String postAnnotation(final String query) {
        return "This should work right ?";
    }
    
}
