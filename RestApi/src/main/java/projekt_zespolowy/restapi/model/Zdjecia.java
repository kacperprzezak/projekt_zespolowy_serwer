package projekt_zespolowy.restapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name="zdjecia")
@XmlRootElement(name="zdjecia")
@XmlType(propOrder={"id_zdjecia", "id_zgloszenia", "zdjecie"})
public class Zdjecia {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_zdjecia")
    private int id_zdjecia;
    @Column(name="id_zgloszenia")
    private int id_zgloszenia;
    @Column(name="zdjecie")
    private byte[] zdjecie;

    @XmlElement
    public int getId_zdjecia() {
        return id_zdjecia;
    }

    public void setId_zdjecia(int id_zdjecia) {
        this.id_zdjecia = id_zdjecia;
    }

    @XmlElement
    public int getId_zgloszenia() {
        return id_zgloszenia;
    }

    public void setId_zgloszenia(int id_zgloszenia) {
        this.id_zgloszenia = id_zgloszenia;
    }

    @XmlElement
    public byte[] getZdjecie() {
        return zdjecie;
    }

    public void setZdjecie(byte[] zdjecie) {
        this.zdjecie = zdjecie;
    }
    
}
