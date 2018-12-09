
package lukuvinkkiKirjasto.domain;


public class ArtikkeliTagi {
    private Integer tagiId;
    private Integer artikkeliId;
    
    public ArtikkeliTagi(Integer tagiId, Integer artikkeliId){
        this.tagiId = tagiId;
        this.artikkeliId = artikkeliId;
    }

    public Integer getTagiId() {
        return tagiId;
    }

    public void setTagiId(Integer tagiId) {
        this.tagiId = tagiId;
    }

    public Integer getArtikkeliId() {
        return artikkeliId;
    }

    public void setArtikkeliId(Integer artikkeliId) {
        this.artikkeliId = artikkeliId;
    }
}
