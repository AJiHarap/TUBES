import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class HotelManager {

    private static final int MAKS_KAMAR     = 20;
    private static final int MAKS_RESERVASI = 50;

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Kamar[]     daftarKamar;
    private Reservasi[] daftarReservasi;
    private int         jumlahReservasi;

    public HotelManager() {
        daftarKamar     = new Kamar[MAKS_KAMAR];
        daftarReservasi = new Reservasi[MAKS_RESERVASI];
        jumlahReservasi = 0;
        inisialisasiKamar();
    }

    private void inisialisasiKamar() {
        int idx = 0;
        for (int i = 101; i <= 105; i++) daftarKamar[idx++] = new Kamar(i, Kamar.TIPE_STANDARD);
        for (int i = 201; i <= 205; i++) daftarKamar[idx++] = new Kamar(i, Kamar.TIPE_DELUXE);
        for (int i = 301; i <= 305; i++) daftarKamar[idx++] = new Kamar(i, Kamar.TIPE_SUITE);
        for (int i = 401; i <= 405; i++) daftarKamar[idx++] = new Kamar(i, Kamar.TIPE_PRESIDENT);
    }

    // Cari reservasi berdasarkan ID, return null jika tidak ditemukan
    private Reservasi cariById(int id) {
        for (int i = 0; i < jumlahReservasi; i++)
            if (daftarReservasi[i].getIdReservasi() == id) return daftarReservasi[i];
        return null;
    }

    // Baca integer dari Scanner dengan prompt
    private int bacaInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try { return Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("  [!] Input tidak valid."); }
        }
    }

    public void tampilkanKamarTersedia() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                  DAFTAR KAMAR TERSEDIA                      ║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");

        boolean ada = false;
        for (Kamar k : daftarKamar) {
            if (k.isTersedia()) { System.out.println("║  " + k + "  ║"); ada = true; }
        }
        if (!ada) System.out.println("║       Semua kamar sedang penuh.                              ║");

        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    }

    public void tambahReservasi(Scanner sc) {
        System.out.println("\n══════════ FORM RESERVASI BARU ══════════");

        if (jumlahReservasi >= MAKS_RESERVASI) {
            System.out.println("  [!] Kapasitas reservasi penuh!"); return;
        }

        System.out.print("  Nama Tamu     : ");
        String nama = sc.nextLine().trim();
        while (nama.isEmpty()) {
            System.out.print("  [!] Nama tidak boleh kosong. Nama Tamu: ");
            nama = sc.nextLine().trim();
        }

        System.out.print("  No. Telepon   : ");
        String noTelp = sc.nextLine().trim();

        System.out.println("\n  Pilih Tipe Kamar:");
        System.out.println("  1. Standard           - Rp 350.000/malam");
        System.out.println("  2. Deluxe             - Rp 600.000/malam");
        System.out.println("  3. Suite              - Rp 1.200.000/malam");
        System.out.println("  4. Presidential Suite - Rp 3.500.000/malam");

        int pilihanTipe = 0;
        while (pilihanTipe < 1 || pilihanTipe > 4) {
            pilihanTipe = bacaInt(sc, "  Pilihan (1-4): ");
            if (pilihanTipe < 1 || pilihanTipe > 4) System.out.println("  [!] Masukkan angka 1-4.");
        }

        String[] tipes = { Kamar.TIPE_STANDARD, Kamar.TIPE_DELUXE, Kamar.TIPE_SUITE, Kamar.TIPE_PRESIDENT };
        String tipeKamar = tipes[pilihanTipe - 1];

        Kamar kamarDipilih = null;
        for (Kamar k : daftarKamar) {
            if (k.getTipeKamar().equals(tipeKamar) && k.isTersedia()) { kamarDipilih = k; break; }
        }
        if (kamarDipilih == null) {
            System.out.println("  [!] Tidak ada kamar " + tipeKamar + " yang tersedia."); return;
        }

        LocalDate checkIn = null;
        while (checkIn == null) {
            System.out.print("  Tanggal Check-In (dd/MM/yyyy): ");
            try {
                checkIn = LocalDate.parse(sc.nextLine().trim(), FMT);
                if (checkIn.isBefore(LocalDate.now())) {
                    System.out.println("  [!] Tanggal tidak boleh di masa lalu."); checkIn = null;
                }
            } catch (DateTimeParseException e) {
                System.out.println("  [!] Format salah. Gunakan dd/MM/yyyy.");
            }
        }

        int jumlahMalam = 0;
        while (jumlahMalam < 1) {
            jumlahMalam = bacaInt(sc, "  Jumlah Malam: ");
            if (jumlahMalam < 1) System.out.println("  [!] Minimal 1 malam.");
        }

        Reservasi resv = new Reservasi(nama, noTelp, kamarDipilih, checkIn, jumlahMalam);
        kamarDipilih.setTersedia(false);
        daftarReservasi[jumlahReservasi++] = resv;

        System.out.println("\n  ✓ Reservasi berhasil dibuat!");
        resv.tampilkanInvoice();
    }

    public void lihatSemuaReservasi() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                              DAFTAR SEMUA RESERVASI                                 ║");
        System.out.println("╠══════════════════════════════════════════════════════════════════════════════════════╣");

        if (jumlahReservasi == 0)
            System.out.println("║  Belum ada reservasi yang tercatat.                                                  ║");
        else
            for (int i = 0; i < jumlahReservasi; i++)
                System.out.println("║  " + daftarReservasi[i]);

        System.out.println("╚══════════════════════════════════════════════════════════════════════════════════════╝");
        System.out.printf("  Total reservasi: %d%n", jumlahReservasi);
    }

    public void cariReservasi(Scanner sc) {
        System.out.println("\n══════════ CARI RESERVASI ══════════");
        System.out.print("  Nama tamu: ");
        String keyword = sc.nextLine().trim().toLowerCase();

        int jumlahDitemukan = 0;
        for (int i = 0; i < jumlahReservasi; i++) {
            if (daftarReservasi[i].getNamaTamu().toLowerCase().contains(keyword)) {
                if (jumlahDitemukan == 0) System.out.println("\n  Hasil pencarian untuk \"" + keyword + "\":");
                daftarReservasi[i].tampilkanInvoice();
                jumlahDitemukan++;
            }
        }

        if (jumlahDitemukan == 0) System.out.println("  [!] Tidak ditemukan reservasi atas nama \"" + keyword + "\".");
        else System.out.printf("\n  Ditemukan %d reservasi.%n", jumlahDitemukan);
    }

    public void lihatInvoice(Scanner sc) {
        System.out.println("\n══════════ LIHAT INVOICE ══════════");
        int id = bacaInt(sc, "  ID Reservasi: ");
        Reservasi r = cariById(id);
        if (r != null) r.tampilkanInvoice();
        else System.out.println("  [!] Reservasi ID " + id + " tidak ditemukan.");
    }

    public void batalkanReservasi(Scanner sc) {
        System.out.println("\n══════════ BATALKAN RESERVASI ══════════");
        int id = bacaInt(sc, "  ID Reservasi yang ingin dibatalkan: ");
        Reservasi r = cariById(id);

        if (r == null) { System.out.println("  [!] Reservasi ID " + id + " tidak ditemukan."); return; }
        if (r.getStatus().equals(Reservasi.STATUS_BATAL)) { System.out.println("  [!] Reservasi ini sudah dibatalkan."); return; }

        System.out.println("\n  Detail reservasi yang akan dibatalkan:");
        r.tampilkanInvoice();
        System.out.print("  Yakin ingin membatalkan? (y/n): ");
        String konfirmasi = sc.nextLine().trim().toLowerCase();

        if (konfirmasi.equals("y") || konfirmasi.equals("yes")) {
            r.setStatus(Reservasi.STATUS_BATAL);
            r.getKamar().setTersedia(true);
            System.out.println("  ✓ Reservasi ID " + id + " berhasil dibatalkan.");
            System.out.println("  ✓ Kamar " + r.getKamar().getNomorKamar() + " tersedia kembali.");
        } else {
            System.out.println("  Pembatalan dibatalkan.");
        }
    }

    public void prosesPembayaran(Scanner sc) {
        System.out.println("\n══════════ PROSES PEMBAYARAN ══════════");
        int id = bacaInt(sc, "  ID Reservasi: ");
        Reservasi r = cariById(id);

        if (r == null)  { System.out.println("  [!] Reservasi ID " + id + " tidak ditemukan."); return; }
        if (!r.getStatus().equals(Reservasi.STATUS_AKTIF)) { System.out.println("  [!] Reservasi tidak aktif (status: " + r.getStatus() + ")."); return; }
        if (r.isSudahBayar()) { System.out.println("  [!] Reservasi ini sudah dibayar."); return; }

        System.out.printf("\n  Total yang harus dibayar: Rp %,.0f%n", (double) r.getTotalBiaya());
        System.out.println("\n  Metode Pembayaran:");
        System.out.println("  1. Tunai");
        System.out.println("  2. Transfer Bank");
        System.out.println("  3. Kartu Kredit/Debit");

        int metode = 0;
        while (metode < 1 || metode > 3) metode = bacaInt(sc, "  Pilih (1-3): ");

        if (metode == 1) {
            long bayar = 0;
            while (bayar < r.getTotalBiaya()) {
                System.out.print("  Jumlah uang yang dibayarkan: Rp ");
                try {
                    bayar = Long.parseLong(sc.nextLine().trim());
                    if (bayar < r.getTotalBiaya()) {
                        System.out.printf("  [!] Kurang Rp %,.0f.%n", (double)(r.getTotalBiaya() - bayar));
                        bayar = 0;
                    }
                } catch (NumberFormatException e) { System.out.println("  [!] Input tidak valid."); }
            }
            System.out.printf("  Kembalian: Rp %,.0f%n", (double)(bayar - r.getTotalBiaya()));
        } else if (metode == 2) {
            System.out.println("  Transfer ke rekening: 1234-5678-9012 (Bank Bintang Hotel)");
            System.out.printf("  Nominal: Rp %,.0f%n", (double) r.getTotalBiaya());
        } else {
            System.out.println("  Silakan tempelkan kartu pada mesin EDC...");
        }

        r.setSudahBayar(true);
        r.setStatus(Reservasi.STATUS_SELESAI);
        r.getKamar().setTersedia(true);
        System.out.println("\n  ✓ Pembayaran berhasil! Terima kasih telah menginap.");
        r.tampilkanInvoice();
    }

    public void tampilkanStatistik() {
        int  totalAktif = 0, totalBatal = 0, totalSelesai = 0;
        int  kamarTersedia = 0, kamarTerisi = 0;
        long totalPendapatan = 0;

        for (int i = 0; i < jumlahReservasi; i++) {
            switch (daftarReservasi[i].getStatus()) {
                case Reservasi.STATUS_AKTIF:   totalAktif++;   break;
                case Reservasi.STATUS_BATAL:   totalBatal++;   break;
                case Reservasi.STATUS_SELESAI: totalSelesai++; break;
            }
            if (daftarReservasi[i].isSudahBayar()) totalPendapatan += daftarReservasi[i].getTotalBiaya();
        }

        for (Kamar k : daftarKamar) {
            if (k.isTersedia()) kamarTersedia++; else kamarTerisi++;
        }

        double persen = (double) kamarTerisi / MAKS_KAMAR * 100;

        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║         STATISTIK HOTEL              ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.printf( "║  Total Kamar       : %-16d║%n", MAKS_KAMAR);
        System.out.printf( "║  Kamar Tersedia    : %-16d║%n", kamarTersedia);
        System.out.printf( "║  Kamar Terisi      : %-16d║%n", kamarTerisi);
        System.out.printf( "║  Tingkat Hunian    : %-13.1f %%  ║%n", persen);
        System.out.println("╠══════════════════════════════════════╣");
        System.out.printf( "║  Total Reservasi   : %-16d║%n", jumlahReservasi);
        System.out.printf( "║  Reservasi Aktif   : %-16d║%n", totalAktif);
        System.out.printf( "║  Reservasi Selesai : %-16d║%n", totalSelesai);
        System.out.printf( "║  Reservasi Dibatal : %-16d║%n", totalBatal);
        System.out.println("╠══════════════════════════════════════╣");
        System.out.printf( "║  Total Pendapatan  : Rp %-,13.0f║%n", (double) totalPendapatan);
        System.out.println("╚══════════════════════════════════════╝");
    }
}
