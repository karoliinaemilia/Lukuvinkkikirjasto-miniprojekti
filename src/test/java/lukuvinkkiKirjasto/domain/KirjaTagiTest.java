/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lukuvinkkiKirjasto.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author ihqsanna
 */
public class KirjaTagiTest {

    KirjaTagi kt;

    @Before
    public void setUp() {
        kt = new KirjaTagi("testausta", 2);
    }

    @Test
    public void oikeaTagiId() {
        assertTrue(2 == kt.getTagiId());
    }

    @Test
    public void oikeaArtikkeiId() {
        assertEquals("testausta", kt.getKirjaId());
    }

    @Test
    public void artikkeliIdnVaihtoOnnistuu() {
        kt.setKirjaId("test");
        assertEquals("test", kt.getKirjaId());
    }

    @Test
    public void tagiIdnVaihtoOnnistuu() {
        kt.setTagiId(20);
        assertTrue(20 == kt.getTagiId());
    }

}
