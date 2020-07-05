package com.onlinehocam.ozel.ders.okul;

import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LessonsAndClasses
{
    public Map<CLASSES, List<String>> mapLessons;
    public Map<PUBLISHERS, List<String>> mapBookNames;
    public static final String UNKNOWN = "Bilinmiyor";

    public LessonsAndClasses() {
        mapLessons = new HashMap<>();
        mapBookNames = new HashMap<>();



        for(int i = 0; i < CLASSES.values().length; i++)
        {
            List<String> lessonNames = new ArrayList<>();
            String[] crLessonElements = LESSONS_SCS[i].split(";");
            for(int y = 0; y < crLessonElements.length; y++)
            {
                lessonNames.add(crLessonElements[y]);
            }
            mapLessons.put(CLASSES.values()[i], lessonNames);
        }

        for(int i = 0; i < PUBLISHERS.values().length; i++)
        {
            List<String> bookNames = new ArrayList<>();
            if(i != PUBLISHERS.values().length - 1)
            {
                for(int y = 0; y < CLASS_NAMES.length; y++)
                {
                    bookNames.add( PUBLISHER_NAMES[i] + " " + CLASS_NAMES[y] );
                }
            }
            bookNames.add(UNKNOWN);
            bookNames.add("Diğer");
            mapBookNames.put(PUBLISHERS.values()[i], bookNames);
        }
    }


    public static final String[] LESSONS_SCS = new String[]
    {
            "Çarpanlar ve Katlar;Üslü İfadeler;Kareköklü İfadeler;Olasılık;Cebirsel İfadeler ve Özdeşlik;Doğrusal Denklemler;Denklem Sistemleri;Eşitsizlikler;Veri Analizi",
            "2. Dereceden Denklemler;Basit Eşitsizlikler;Binom;Bölme ve Bölünebilme;Çarpanlara Ayırma;Denklem Çözme;Diziler;Eşitsizlikler;Fonksiyonlar;İntegral;İstatistik;Karmaşık Sayılar;Kartezyen Çarpım;Köklü Sayılar;Kombinasyon;Kümeler;Limit;Logaritma;Mantık;Mutlak Değer;OBEB – OKEK;Olasılık;Oran-Orantı;Parabol;Permütasyon;Polinomlar;Problemler;Rasyonel Sayılar;Sayılar – Sayı Basamakları;Temel Kavramlar;Trigonometri;Türev;Üslü Sayılar",
            "Atışlar;Atom Fiziğine Giriş ve Radyoaktive;Basit Harmonik Hareket;Basit Makineler;Basınç;Dalga Mekaniği;Dalgalar;Düzgün Çembersel Hareket;Elektrik ve Manyetizma;Elektrostatik;Fizik Bilimine Giriş;Hareket ve Kuvvet;İş, Güç ve Enerji I;Isı ve Sıcaklık;Kaldırma Kuvveti;Kütle Merkezi;Kuvvet ve Hareket;Madde ve Özellikleri;Modern Fiziğin Teknolojideki Uygulamaları;Modern Fizik;Optik;Tork ve Denge",
            "Asit, Baz ve Tuz;Atom ve Periyodik Sistem;Endüstride ve Canlılarda Enerji;Hayatımızdaki Kimya;Karbon Kimyasına Giriş;Karışımlar;Kimya Bilimi;Kimya Her Yerde;Kimya ve Elektrik;Kimya ve Enerji;Kimyanın Temel Kanunları;Kimyasal Hesaplamalar;Kimyasal Türler Arası Etkileşimler;Maddenin Halleri;Organik Bileşikler;Sıvı Çözeltiler;Tepkimelerde Hız ve Denge",
            "Bitki Biyolojisi;Bitkisel Dokular;Canlılarda Enerji Dönüşümü;Canlıların Ortak Özellikleri;Canlıların Sınıflandırılması;Canlıların Temel Bileşenleri;Destek ve Hareket Sistemi;Dolaşım Sistemi;Duyu Organları;Ekosistem Ekolojisi;Endokrin Sistemi;Genden Proteine;Güncel Çevre Sorunlar;Hayatın Başlangıcı ve Evrim;Hücre ve Organelleri;Hücre Zarından Madde Geçişi;İnsan Fizyolojisi;Kalıtım;Kominite ve Popülasyon Ekolojisi;Mayoz ve Eşeyli Üreme;Mitoz ve Eşeysiz Üreme;Sindirim Sistemi;Sinir Sistemi;Solunum",
            "Adaptasyon (Çevreye Uyum);Asitler ve Bazlar;Basit Makineler;Basınç;Besin Zinciri ve Enerji Akışı;Biyoteknoloji;DNA ve Genetik Kod;Elektrik Enerjisinin Dönüşümü;Elektrik Yükleri ve Elektriklenme;Elektrik Yüklü Cisimler;Enerji Dönüşümleri;Fiziksel ve Kimyasal Değişimler;İklim ve Hava Hareketleri;Kalıtım;Kimyasal Tepkimeler;Madde Döngüleri ve Çevre Sorunları;Maddenin Isı ile Etkileşimi;Mevsimlerin Oluşumu;Mutasyon ve Modifikasyon;Periyodik Sistem;Sürdürülebilir Kalkınma;Türkiye’de Kimya Endüstrisi",
            "Üçgenler;Dönüşüm Geometrisi;Eşlik ve Benzerlik;Geometrik Cisimler",
            "Açı Kenar Bağıntıları;Açıortay;Çemberin Analitiği;Çemberler;Çokgenler;Dik Üçgen;Dikdörtgen;Doğruda Açılar;Doğrunun Analitiği;Eşkenar Üçgen;İkizkenar Üçgen;Katı Cisimler;Kenarortay;Noktanın Analitiği;Özel Dörtgenler;Özel Üçgenler;Prizmalar;Temel Kavramlar;Üçgende Açılar;Üçgende Alan;Üçgende Benzerlik",
            "1881’den 1919’a Mustafa Kemal;Arayış Yılları (17. Yüzyıl);Atatürk’ün Ölümü;Atatürkçülük ve Atatürk İlkeleri;Avrupa ve Osmanlı Devleti (18. Yüzyıl);Beylikten Devlete (1300-1453);Dünya Gücü: Osmanlı Devleti (1453-1600);En Uzun Yüzyıl (1800-1922);İkinci Dünya Savaşı;İlk Türk Devletleri;İslam Tarihi ve Uygarlığı;Küreselleşen Dünya;Kurtuluş Savaşı’nda Cepheler;Milli Mücadele’nin Hazırlık Dönemi;Soğuk Savaş Dönemi;Tarih Bilimi;Türk Dış Politikası;Türk İnkılabı;Türk-İslam Devletleri;Türkiye Tarihi;Türklerde Devlet Teşkilatı;Türklerde Eğitim;Türklerde Ekonomi;Türklerde Hukuk;Türklerde Sanat;Türklerde Toplum Yapısı;Uygarlığın Doğuşu ve İlk Uygarlıklar;Yumuşama Dönemi ve Sonrası;Yüzyılın Başlarında Dünya",
            "Beşeri Yapı;Bölge Türleri ve Sınırları;Coğrafi Keşifler;Coğrafi Konum;Doğa ile İnsan Arasındaki Etkileşim;Doğa ve İnsan;Doğal Afetler;Doğanın Varlıkları;Dünya’nın Şekli ve Hareketleri;Geçim Tarzları;Göçlerin Nedenleri ve Sonuçları;Harita Bilgisi;İklim Bilgisi;Konum ve Etkileşim;Nüfusun Gelişimi, Dağılışı ve Niteliği;Türkiye İklimi ve Özellikleri;Türkiye’de Yerleşme, Nüfus ve Göç;Türkiye’nin Doğal Varlıkları;Türkiye’nin Yeryüzü Şekilleri ve Özellikleri;Yerin Şekillenmesi",
            "Adventures;Chores;Communıcation;Cooking;Friendship;Natural Forces;Science;Teen Life;The Internet;Tourısm",
            "Anlatım Teknikleri;Cumhuriyet Döneminde Hikaye;Cumhuriyet Döneminde Roman;Cumhuriyet Döneminde Şiir;Cümlede Anlam;Destan;Divan Şiiri;Edebiyat Akımları;Fecriati Şiiri;Geleneksel Türk Tiyatrosu;Masal;Milli Edebiyat Döneminde Hikaye;Paragrafta Anlam;Şiir Türleri;Şiirin Yapısı ve Özellikleri;Sözcükte Anlam;Tanzimat Dönemi Tiyatrosu",
            "Anlatım Bozuklukları;Cümle Çeşitleri;Cümlede Anlam;Cümlenin Ögeleri;Fiil Çatısı;Fiilimsiler(Eylemsi);Noktalama İşaretleri;Paragrafta Anlam;Ses  Bilgisi;Söz Sanatları;Sözcükte Anlam;Yazı (Metin) Türleri;Yazım (imla) Kuralları",
            "Ahlak Felsefesi;Bilgi Felsefesi;Bilim Felsefesi;Din Felsefesi;Felsefe’nin Konusu;Sanat Felsefesi;Siyaset Felsefesi;Varlık Felsefesi",
            "Almanca;Arapça;Fransızca;İngilizce;Rusça"
    };


    public static enum CLASSES
    {
        MATHS_PS,
        MATHS_HS,
        PHYSICS,
        CHEMISTRY,
        BIOLOGY,
        SCIENCES,
        GEOMETRY_PS,
        GEOMETRY_HS,
        HISTORY,
        GEOGRAPHY,
        ENGLISH_PS,
        LITERATURE,
        TURKISH,
        PHILOSOPHY,
        FOREIGN_LANGUAGE
    }





    public static final String[] CLASS_NAMES = new String[] { "Matematik - İlköğretim", "Matematik - Lise", "Fizik", "Kimya", "Biyoloji",
            "Fen Bilimleri", "Geometri - İlköğretim", "Geometri - Lise", "Tarih", "Coğrafya", "İngilizce", "Edebiyat", "Türkçe", "Felsefe", "Yabancı Dil" };



    public static enum PUBLISHERS
    {
        PI,
        OKYANUS,
        SINAV,
        PALME,
        FINAL,
        KAREKOK,
        FENBILIMLERI,
        ESEN,
        FDD,
        UNKNOWN,
        OTHER
    }


    public static final String[] PUBLISHER_NAMES = new String[] {"Pi Yayınları", "Okyanus Yayınları", "Sınav Yayınları", "Palme Yayınları", "Final Yayınları",
            "Karekök Yayınları", "Fen Bilimleri Yayınları", "Esen Yayınları", "FDD Yayınları", UNKNOWN, "Diğer" };


}
