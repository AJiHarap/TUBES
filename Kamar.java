public class Kamar {

    public static final String TIPE_STANDARD  = "Standard";
    public static final String TIPE_DELUXE    = "Deluxe";
    public static final String TIPE_SUITE     = "Suite";
    public static final String TIPE_PRESIDENT = "Presidential Suite";

    public static final long HARGA_STANDARD  = 350_000;
    public static final long HARGA_DELUXE    = 600_000;
    public static final long HARGA_SUITE     = 1_200_000;
    public static final long HARGA_PRESIDENT = 3_500_000;

    private int     nomorKamar;
    private String  tipeKamar;
    private long    hargaPerMalam;
    private boolean tersedia;

    public Kamar(int nomorKamar, String tipeKamar) {
        this.nomorKamar    = nomorKamar;
        this.tipeKamar     = tipeKamar;
        this.hargaPerMalam = tentukanHarga(tipeKamar);
        this.tersedia      = true;
    }

    private long tentukanHarga(String tipe) {
        switch (tipe) {
            case TIPE_STANDARD:  return HARGA_STANDARD;
            case TIPE_DELUXE:    return HARGA_DELUXE;
            case TIPE_SUITE:     return HARGA_SUITE;
            case TIPE_PRESIDENT: return HARGA_PRESIDENT;
            default:             return 0;
        }
    }

    public int     getNomorKamar()    { return nomorKamar; }
    public String  getTipeKamar()     { return tipeKamar; }
    public long    getHargaPerMalam() { return hargaPerMalam; }
    public boolean isTersedia()       { return tersedia; }
    public void    setTersedia(boolean tersedia) { this.tersedia = tersedia; }

    public long hitungTotalBiaya(int jumlahMalam) {
        return hargaPerMalam * jumlahMalam;
    }

    @Override
    public String toString() {
        return String.format("Kamar %03d | %-20s | Rp %,d/malam | %s",
                nomorKamar, tipeKamar, hargaPerMalam,
                tersedia ? "TERSEDIA" : "TERISI");
    }
}
