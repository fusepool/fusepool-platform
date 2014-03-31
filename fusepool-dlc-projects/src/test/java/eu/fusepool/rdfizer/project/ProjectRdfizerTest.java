package eu.fusepool.rdfizer.project;

import java.io.InputStream;
import org.apache.clerezza.rdf.core.MGraph;
import org.apache.clerezza.rdf.core.serializedform.Parser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import eu.fusepool.datalifecycle.Rdfizer;

public class ProjectRdfizerTest {
    
    Rdfizer rdfizer;

    @Before
    public void setUp() throws Exception {
        ProjectRdfizer projectRdfizer = new ProjectRdfizer();
        projectRdfizer.parser = Parser.getInstance();
        rdfizer = projectRdfizer; 
    }

    @Test
    public void testTransform() {
        InputStream in = this.getClass().getResourceAsStream("calls.csv");        
        MGraph mGraph = rdfizer.transform(in);
        Assert.assertTrue(mGraph.size() > 0);
    }

}
