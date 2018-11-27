package net.iqbalfauzan.agribid.model;

public class LahanModel {
    private String id, luas, alamat, tanaman, pemilik, foto;
    private int hargaPerInvest, hargaAwalLelang, maxInvest;
    private boolean isPanen;

    public LahanModel() {
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLuas() {
        return luas;
    }

    public void setLuas(String luas) {
        this.luas = luas;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTanaman() {
        return tanaman;
    }

    public void setTanaman(String tanaman) {
        this.tanaman = tanaman;
    }

    public String getPemilik() {
        return pemilik;
    }

    public void setPemilik(String pemilik) {
        this.pemilik = pemilik;
    }

    public int getHargaPerInvest() {
        return hargaPerInvest;
    }

    public void setHargaPerInvest(int hargaPerInvest) {
        this.hargaPerInvest = hargaPerInvest;
    }

    public int getHargaAwalLelang() {
        return hargaAwalLelang;
    }

    public void setHargaAwalLelang(int hargaAwalLelang) {
        this.hargaAwalLelang = hargaAwalLelang;
    }

    public int getMaxInvest() {
        return maxInvest;
    }

    public void setMaxInvest(int maxInvest) {
        this.maxInvest = maxInvest;
    }

    public boolean isPanen() {
        return isPanen;
    }

    public void setPanen(boolean panen) {
        isPanen = panen;
    }
}
