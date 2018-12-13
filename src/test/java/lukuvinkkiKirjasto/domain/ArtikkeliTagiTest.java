
package lukuvinkkiKirjasto.domain;

import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;


public class ArtikkeliTagiTest {
    ArtikkeliTagi at;
    
    @Before
    public void setUp() {
        at = new ArtikkeliTagi(1, 2);
    }
    @Test
    public void oikeaTagiId() {
        assertTrue(2 == at.getTagiId());
    }
    @Test
    public void oikeaArtikkeiId() {
        assertTrue(1 == at.getArtikkeliId());
    }
    
    @Test
    public void artikkeliIdnVaihtoOnnistuu() {
        at.setArtikkeliId(10);
        assertTrue(10 == at.getArtikkeliId());
    }
    
    @Test
    public void tagiIdnVaihtoOnnistuu() {
        at.setTagiId(20);
        assertTrue(20 == at.getTagiId());
    }
    
}
