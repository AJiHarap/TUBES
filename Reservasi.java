import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Reservasi {

    private static int counterID = 1;

    public static final String STATUS_AKTIF   = "AKTIF";
    public static final String STATUS_BATAL   = "BATAL";
    public static final String STATUS_SELESAI = "SELESAI";

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private int       idReservasi;
    private String    namaTamu;
    private String    noTelepon;
    private Kamar     kamar;
    private int       jumlahMalam;
    private LocalDate tanggalCheckIn;
    private LocalDate tanggalCheckOut;
    private long      totalBiaya;
    private String    status;
    private boolean   sudahBayar;

    public Reservasi(String namaTamu, String noTelepon, Kamar kamar,
                     LocalDate tanggalCheckIn, int jumlahMalam) {
        this.idReservasi     = counterID++;
        this.namaTamu        = namaTamu;
        this.noTelepon       = noTelepon;
        this.kamar           = kamar;
        this.tanggalCheckIn  = tanggalCheckIn;
        this.jumlahMalam     = jumlahMalam;
        this.tanggalCheckOut = tanggalCheckIn.plusDays(jumlahMalam);
        this.totalBiaya      = kamar.hitungTotalBiaya(jumlahMalam);
        this.status          = STATUS_AKTIF;
        this.sudahBayar      = false;
    }

    public int       getIdReservasi()     { return idReservasi; }
    public String    getNamaTamu()        { return namaTamu; }
    public String    getNoTelepon()       { return noTelepon; }
    public Kamar     getKamar()           { return kamar; }
    public int       getJumlahMalam()     { return jumlahMalam; }
    public LocalDate getTanggalCheckIn()  { return tanggalCheckIn; }
    public LocalDate getTanggalCheckOut() { return tanggalCheckOut; }
    public long      getTotalBiaya()      { return totalBiaya; }
    public String    getStatus()          { return status; }
    public boolean   isSudahBayar()      { return sudahBayar; }

    public void setStatus(String status)     { this.status = status; }
    public void setSudahBayar(boolean bayar) { this.sudahBayar = bayar; }

    public void tampilkanInvoice() {
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║           INVOICE RESERVASI HOTEL            ║");
        System.out.println("╠══════════════════════════════════════════════╣");
        System.out.printf( "║  ID Reservasi  : %-27d║%n", idReservasi);
        System.out.printf( "║  Nama Tamu     : %-27s║%n", namaTamu);
        System.out.printf( "║  No. Telepon   : %-27s║%n", noTelepon);
        System.out.println("╠══════════════════════════════════════════════╣");
        System.out.printf( "║  Kamar         : %-27d║%n", kamar.getNomorKamar());
        System.out.printf( "║  Tipe          : %-27s║%n", kamar.getTipeKamar());
        System.out.printf( "║  Check-In      : %-27s║%n", tanggalCheckIn.format(FMT));
        System.out.printf( "║  Check-Out     : %-27s║%n", tanggalCheckOut.format(FMT));
        System.out.printf( "║  Lama Menginap : %-23s hari║%n", jumlahMalam);
        System.out.println("╠══════════════════════════════════════════════╣");
        System.out.printf( "║  Harga/Malam   : Rp %-,24.0f║%n", (double) kamar.getHargaPerMalam());
        System.out.printf( "║  TOTAL BIAYA   : Rp %-,24.0f║%n", (double) totalBiaya);
        System.out.printf( "║  Status Bayar  : %-27s║%n", sudahBayar ? "LUNAS ✓" : "BELUM BAYAR");
        System.out.printf( "║  Status Res.   : %-27s║%n", status);
        System.out.println("╚══════════════════════════════════════════════╝");
    }

    @Override
    public String toString() {
        return String.format("[%03d] %-20s | Kamar %03d %-20s | %s s.d %s | Rp %,.0f | %s",
                idReservasi, namaTamu, kamar.getNomorKamar(), kamar.getTipeKamar(),
                tanggalCheckIn.format(FMT), tanggalCheckOut.format(FMT),
                (double) totalBiaya, status);
    }
}
