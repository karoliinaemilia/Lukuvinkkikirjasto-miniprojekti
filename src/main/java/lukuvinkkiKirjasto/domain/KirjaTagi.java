
package lukuvinkkiKirjasto.domain;


public class KirjaTagi {
    private Integer tagiId;
    private String kirjaISBN;

    public KirjaTagi(String kirjaISBN, Integer tagiId) {
        this.tagiId = tagiId;
        this.kirjaISBN = kirjaISBN;
    }

    public Integer getTagiId() {
        return tagiId;
    }

    public void setTagiId(Integer tagiId) {
        this.tagiId = tagiId;
    }

    public String getKirjaId() {
        return kirjaISBN;
    }

    public void setKirjaId(String kirjaId) {
        this.kirjaISBN = kirjaId;
    }
    
    
    
}
