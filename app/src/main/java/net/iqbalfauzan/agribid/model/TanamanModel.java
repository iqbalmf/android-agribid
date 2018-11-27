package net.iqbalfauzan.agribid.model;

public class TanamanModel {
    String id, nama, namaLatin;
    int hargaPasar;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNamaLatin() {
        return namaLatin;
    }

    public void setNamaLatin(String namaLatin) {
        this.namaLatin = namaLatin;
    }

    public int getHargaPasar() {
        return hargaPasar;
    }

    public void setHargaPasar(int hargaPasar) {
        this.hargaPasar = hargaPasar;
    }
}
