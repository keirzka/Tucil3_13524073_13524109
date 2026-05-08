package icesolver;

public final class AppConfig {
    private AppConfig() {}
    public static final String ALGO_UCS = "UCS";
    public static final String ALGO_GBFS = "GBFS";
    public static final String ALGO_BINTANG = "A*";
    public static final String HEURISTIK_H1 = "H1";
    public static final String HEURISTIK_H2 = "H2";
    public static final String HEURISTIK_H3 = "H3";
    public static final String JALUR_KELUARAN_BAWAAN = "output/solusi.txt";
    public static final String DIR_UJI = "test/";
    public static final int MAKS_ITERASI = Integer.MAX_VALUE;
    public static final boolean TAMPILKAN_PAPAN_TIAP_LANGKAH = true;
    public static final String VERSI = "1.0.0";
}