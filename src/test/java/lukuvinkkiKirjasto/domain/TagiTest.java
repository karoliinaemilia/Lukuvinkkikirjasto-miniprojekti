
package lukuvinkkiKirjasto.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;


public class TagiTest {
    
    Tagi tagi;
    
    @Before
    public void setUp() {
        tagi = new Tagi(1, "testikappale");
        
    }
    @Test
    public void palauttaaOikeanNimen() {
        assertEquals("testikappale", tagi.getNimi());
    }
    @Test
    public void nimenVoiVaihtaa() {
        tagi.setNimi("testi");
        assertEquals("testi", tagi.getNimi());
    }
    @Test
    public void oikeaId() {
        assertTrue(1 == tagi.getId());
        
    }
    @Test
    public void vaihdaId() {
        tagi.setId(2);
        assertTrue(2 == tagi.getId());
    }
}
