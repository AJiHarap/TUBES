import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner      sc      = new Scanner(System.in);
        HotelManager hotel   = new HotelManager();
        boolean      running = true;

        tampilkanWelcome();

        while (running) {
            tampilkanMenu();
            System.out.print("  Pilih menu (0-8): ");

            int pilihan = -1;
            try {
                pilihan = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  [!] Input tidak valid. Masukkan angka 0-8.\n"); continue;
            }

            switch (pilihan) {
                case 1: hotel.tampilkanKamarTersedia();   break;
                case 2: hotel.tambahReservasi(sc);        break;
                case 3: hotel.lihatSemuaReservasi();      break;
                case 4: hotel.cariReservasi(sc);          break;
                case 5: hotel.lihatInvoice(sc);           break;
                case 6: hotel.batalkanReservasi(sc);      break;
                case 7: hotel.prosesPembayaran(sc);       break;
                case 8: hotel.tampilkanStatistik();       break;
                case 0:
                    System.out.println("\n  Terima kasih! Sampai jumpa! 🌟\n");
                    running = false; break;
                default:
                    System.out.println("  [!] Pilihan tidak tersedia. Masukkan angka 0-8.\n");
            }
        }

        sc.close();
    }

    private static void tampilkanWelcome() {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                                                          ║");
        System.out.println("║       ★  HOTEL BINTANG LIMA - SISTEM RESERVASI  ★       ║");
        System.out.println("║                                                          ║");
        System.out.println("║              Kelompok 5 | Pemrograman Java               ║");
        System.out.println("║                                                          ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");
    }

    private static void tampilkanMenu() {
        System.out.println("\n┌─────────────────────────────────────┐");
        System.out.println("│           MENU UTAMA                │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.println("│  1. Lihat Kamar Tersedia            │");
        System.out.println("│  2. Buat Reservasi Baru             │");
        System.out.println("│  3. Lihat Semua Reservasi           │");
        System.out.println("│  4. Cari Reservasi (by Nama)        │");
        System.out.println("│  5. Lihat Invoice Reservasi         │");
        System.out.println("│  6. Batalkan Reservasi              │");
        System.out.println("│  7. Proses Pembayaran / Check-Out   │");
        System.out.println("│  8. Statistik Hotel                 │");
        System.out.println("│  0. Keluar                          │");
        System.out.println("└─────────────────────────────────────┘");
    }
}
