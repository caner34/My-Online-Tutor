package com.onlinehocam.ozel.ders.okul;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Random;

public class GlobalVariables
{

    private static final String LOG_TAG = "messageCategory";
    public static DatabaseHelper myDb;
    public static SQLiteDatabase db;
    public static Random r;

    public static LessonsAndClasses lessonsAndClasses;


    //USER INFO
    public static String KEY_USER_STATUS_TUTOR = "tutor";
    public static String KEY_USER_STATUS_STUDENT = "student";
    public static String KEY_USER_STATUS_MARKETER = "marketer";
    public static String[] USER_STATUS_ARRAY = new String[] { KEY_USER_STATUS_TUTOR, KEY_USER_STATUS_STUDENT, KEY_USER_STATUS_MARKETER };

    public static int GetUserTypeIDByUserStatusString(String userStatus)
    {
        int result = -1;
        if(userStatus.equals(KEY_USER_STATUS_TUTOR))
        {
            result = 0;
        }
        else if(userStatus.equals(KEY_USER_STATUS_STUDENT))
        {
            result = 1;
        }
        else if(userStatus.equals(KEY_USER_STATUS_MARKETER))
        {
            result = 2;
        }


        return result;
    }

    public static String USER_STATUS = "";
    public static String USER_NAME = "";
    public static boolean IS_AUTO_SIGN_IN = false;
    public static String USER_PASSWORD_TRIALS = "";
    //TODO INSTANTIATE USER_ID AT START-UP
    public static int USER_ID = -1;

    public static double MIN_APPRECIATED_PRICE = 0.50;
    public static String DATE_SEPERATOR_SLASH = "/";
    public static String DATE_SEPERATOR_DASH = "-";

    public static final String GENDER_MALE = "male";
    public static final String GENDER_FEMALE = "female";
    public static final String[] GENDERS_ARRAY = new String[] { GENDER_MALE, GENDER_FEMALE };

    //USER REGISTER
    public static final String CITIES_AND_DISTRICTS_COMMA_AND_PERCENT_SIGN_SEPERATED_STRING = "Adana%Seyhan,Ceyhan,Feke,Karaisalı,Karataş,Kozan,Pozantı,Saimbeyli,Tufanbeyli,Yumurtalık,Yüreğir,Aladağ,İmamoğlu,Sarıçam,Çukurova;Adıyaman%Adıyaman Merkez,Besni,Çelikhan,Gerger,Gölbaşı / Adıyaman,Kahta,Samsat,Sincik,Tut;Afyonkarahisar%Afyonkarahisar Merkez,Bolvadin,Çay,Dazkırı,Dinar,Emirdağ,İhsaniye,Sandıklı,Sinanpaşa,Sultandağı,Şuhut,Başmakçı,Bayat / Afyonkarahisar,İscehisar,Çobanlar,Evciler,Hocalar,Kızılören;Ağrı%Ağrı Merkez,Diyadin,Doğubayazıt,Eleşkirt,Hamur,Patnos,Taşlıçay,Tutak;Amasya%Amasya Merkez,Göynücek,Gümüşhacıköy,Merzifon,Suluova,Taşova,Hamamözü;Ankara%Altındağ,Ayaş,Bala,Beypazarı,Çamlıdere,Çankaya,Çubuk,Elmadağ,Güdül,Haymana,Kalecik,Kızılcahamam,Nallıhan,Polatlı,Şereflikoçhisar,Yenimahalle,Gölbaşı / Ankara,Keçiören,Mamak,Sincan,Kazan,Akyurt,Etimesgut,Evren,Pursaklar;Antalya%Akseki,Alanya,Antalya Merkez,Elmalı,Finike,Gazipaşa,Gündoğmuş,Kaş,Korkuteli,Kumluca,Manavgat,Serik,Demre,İbradı,Kemer / Antalya,Aksu / Antalya,Döşemealtı,Kepez,Konyaaltı,Muratpaşa;Artvin%Ardanuç,Arhavi,Artvin Merkez,Borçka,Hopa,Şavşat,Yusufeli,Murgul,Kemalpaşa / Artvin;Aydın%Aydın Merkez,Bozdoğan,Çine,Germencik,Karacasu,Koçarlı,Kuşadası,Kuyucak,Nazilli,Söke,Sultanhisar,Yenipazar / Aydın,Buharkent,İncirliova,Karpuzlu,Köşk,Didim,Efeler;Balıkesir%Ayvalık,Balıkesir Merkez,Balya,Bandırma,Bigadiç,Burhaniye,Dursunbey,Edremit / Balıkesir,Erdek,Gönen / Balıkesir,Havran,İvrindi,Kepsut,Manyas,Savaştepe,Sındırgı,Susurluk,Marmara,Gömeç,Altıeylül,Karesi;Bilecik%Bilecik Merkez,Bozüyük,Gölpazarı,Osmaneli,Pazaryeri,Söğüt,Yenipazar / Bilecik,İnhisar;Bingöl%Bingöl Merkez,Genç,Karlıova,Kiğı,Solhan,Adaklı,Yayladere,Yedisu;Bitlis%Adilcevaz,Ahlat,Bitlis Merkez,Hizan,Mutki,Tatvan,Güroymak;Bolu%Bolu Merkez,Gerede,Göynük,Kıbrıscık,Mengen,Mudurnu,Seben,Dörtdivan,Yeniçağa;Burdur%Ağlasun,Bucak,Burdur Merkez,Gölhisar,Tefenni,Yeşilova,Karamanlı,Kemer / Burdur,Altınyayla / Burdur,Çavdır,Çeltikçi;Bursa%Gemlik,İnegöl,İznik,Karacabey,Keles,Mudanya,Mustafakemalpaşa,Orhaneli,Orhangazi,Yenişehir / Bursa,Büyükorhan,Harmancık,Nilüfer,Osmangazi,Yıldırım,Gürsu,Kestel;Çanakkale%Ayvacık / Çanakkale,Bayramiç,Biga,Bozcaada,Çan,Çanakkale Merkez,Eceabat,Ezine,Gelibolu,Gökçeada,Lapseki,Yenice / Çanakkale;Çankırı%Çankırı Merkez,Çerkeş,Eldivan,Ilgaz,Kurşunlu,Orta,Şabanözü,Yapraklı,Atkaracalar,Kızılırmak,Bayramören,Korgun;Çorum%Alaca,Bayat / Çorum,Çorum Merkez,İskilip,Kargı,Mecitözü,Ortaköy / Çorum,Osmancık,Sungurlu,Boğazkale,Uğurludağ,Dodurga,Laçin,Oğuzlar;Denizli%Acıpayam,Buldan,Çal,Çameli,Çardak,Çivril,Denizli Merkez,Güney,Kale / Denizli,Sarayköy,Tavas,Babadağ,Bekilli,Honaz,Serinhisar,Pamukkale,Baklan,Beyağaç,Bozkurt / Denizli,Merkezefendi;Diyarbakır%Bismil,Çermik,Çınar,Çüngüş,Dicle,Diyarbakır Merkez,Ergani,Hani,Hazro,Kulp,Lice,Silvan,Eğil,Kocaköy,Bağlar,Kayapınar,Sur,Yenişehir / Diyarbakır;Edirne%Edirne Merkez,Enez,Havsa,İpsala,Keşan,Lalapaşa,Meriç,Uzunköprü,Süloğlu;Elazığ%Ağın,Baskil,Elazığ Merkez,Karakoçan,Keban,Maden,Palu,Sivrice,Arıcak,Kovancılar,Alacakaya;Erzincan%Çayırlı,Erzincan Merkez,İliç,Kemah,Kemaliye,Refahiye,Tercan,Üzümlü,Otlukbeli;Erzurum%Aşkale,Çat,Erzurum Merkez,Hınıs,Horasan,İspir,Karayazı,Narman,Oltu,Olur,Pasinler,Şenkaya,Tekman,Tortum,Karaçoban,Uzundere,Pazaryolu,Aziziye,Köprüköy,Palandöken,Yakutiye;Eskişehir%Çifteler,Eskişehir Merkez,Mahmudiye,Mihalıççık,Sarıcakaya,Seyitgazi,Sivrihisar,Alpu,Beylikova,İnönü,Günyüzü,Han,Mihalgazi,Odunpazarı,Tepebaşı;Gaziantep%Araban,İslahiye,Nizip,Oğuzeli,Yavuzeli,Şahinbey,Şehitkamil,Karkamış,Nurdağı;Giresun%Alucra,Bulancak,Dereli,Espiye,Eynesil,Giresun Merkez,Görele,Keşap,Şebinkarahisar,Tirebolu,Piraziz,Yağlıdere,Çamoluk,Çanakçı,Doğankent,Güce;Gümüşhane%Gümüşhane Merkez,Kelkit,Şiran,Torul,Köse,Kürtün;Hakkari%Çukurca,Hakkari Merkez,Şemdinli,Yüksekova;Hatay%Altınözü,Dörtyol,Hassa,Hatay Merkez,İskenderun,Kırıkhan,Reyhanlı,Samandağ,Yayladağı,Erzin,Belen,Kumlu,Antakya,Arsuz,Defne,Payas;Isparta%Atabey,Eğirdir,Gelendost,Isparta Merkez,Keçiborlu,Senirkent,Sütçüler,Şarkikaraağaç,Uluborlu,Yalvaç,Aksu / Isparta,Gönen / Isparta,Yenişarbademli;Mersin%Anamur,Erdemli,Gülnar,Mersin Merkez,Mut,Silifke,Tarsus,Aydıncık / Mersin,Bozyazı,Çamlıyayla,Akdeniz,Mezitli,Toroslar,Yenişehir / Mersin;İstanbul%Adalar,Bakırköy,Beşiktaş,Beykoz,Beyoğlu,Çatalca,Eminönü,Eyüp,Fatih,Gaziosmanpaşa,Kadıköy,Kartal,Sarıyer,Silivri,Şile,Şişli,Üsküdar,Zeytinburnu,Büyükçekmece,Kağıthane,Küçükçekmece,Pendik,Ümraniye,Bayrampaşa,Avcılar,Bağcılar,Bahçelievler,Güngören,Maltepe,Sultanbeyli,Tuzla,Esenler,Arnavutköy,Ataşehir,Başakşehir,Beylikdüzü,Çekmeköy,Esenyurt,Sancaktepe,Sultangazi;İzmir%Aliağa,Bayındır,Bergama,Bornova,Çeşme,Dikili,Foça,Karaburun,Karşıyaka,Kemalpaşa / İzmir,Kınık,Kiraz,Menemen,Ödemiş,Seferihisar,Selçuk,Tire,Torbalı,Urla,Beydağ,Buca,Konak,Menderes,Balçova,Çiğli,Gaziemir,Narlıdere,Güzelbahçe,Bayraklı,Karabağlar;Kars%Arpaçay,Digor,Kağızman,Kars Merkez,Sarıkamış,Selim,Susuz,Akyaka;Kastamonu%Abana,Araç,Azdavay,Bozkurt / Kastamonu,Cide,Çatalzeytin,Daday,Devrekani,İnebolu,Kastamonu Merkez,Küre,Taşköprü,Tosya,İhsangazi,Pınarbaşı / Kastamonu,Şenpazar,Ağlı,Doğanyurt,Hanönü,Seydiler;Kayseri%Bünyan,Develi,Felahiye,İncesu,Pınarbaşı / Kayseri,Sarıoğlan,Sarız,Tomarza,Yahyalı,Yeşilhisar,Akkışla,Talas,Kocasinan,Melikgazi,Hacılar,Özvatan;Kırklareli%Babaeski,Demirköy,Kırklareli Merkez,Kofçaz,Lüleburgaz,Pehlivanköy,Pınarhisar,Vize;Kırşehir%Çiçekdağı,Kaman,Kırşehir Merkez,Mucur,Akpınar,Akçakent,Boztepe;Kocaeli%Gebze,Gölcük,Kandıra,Karamürsel,Kocaeli Merkez,Körfez,Derince,Başiskele,Çayırova,Darıca,Dilovası,İzmit,Kartepe;Konya%Akşehir,Beyşehir,Bozkır,Cihanbeyli,Çumra,Doğanhisar,Ereğli / Konya,Hadim,Ilgın,Kadınhanı,Karapınar,Kulu,Sarayönü,Seydişehir,Yunak,Akören,Altınekin,Derebucak,Hüyük,Karatay,Meram,Selçuklu,Taşkent,Ahırlı,Çeltik,Derbent,Emirgazi,Güneysınır,Halkapınar,Tuzlukçu,Yalıhüyük;Kütahya%Altıntaş,Domaniç,Emet,Gediz,Kütahya Merkez,Simav,Tavşanlı,Aslanapa,Dumlupınar,Hisarcık,Şaphane,Çavdarhisar,Pazarlar;Malatya%Akçadağ,Arapgir,Arguvan,Darende,Doğanşehir,Hekimhan,Malatya Merkez,Pütürge,Yeşilyurt / Malatya,Battalgazi,Doğanyol,Kale / Malatya,Kuluncak,Yazıhan;Manisa%Akhisar,Alaşehir,Demirci,Gördes,Kırkağaç,Kula,Manisa Merkez,Salihli,Sarıgöl,Saruhanlı,Selendi,Soma,Turgutlu,Ahmetli,Gölmarmara,Köprübaşı / Manisa,Şehzadeler,Yunusemre;Kahramanmaraş%Afşin,Andırın,Elbistan,Göksun,Kahramanmaraş Merkez,Pazarcık,Türkoğlu,Çağlayancerit,Ekinözü,Nurhak,Dulkadiroğlu,Onikişubat;Mardin%Derik,Kızıltepe,Mardin Merkez,Mazıdağı,Midyat,Nusaybin,Ömerli,Savur,Dargeçit,Yeşilli,Artuklu;Muğla%Bodrum,Datça,Fethiye,Köyceğiz,Marmaris,Milas,Muğla Merkez,Ula,Yatağan,Dalaman,Ortaca,Kavaklıdere,Menteşe,Seydikemer;Muş%Bulanık,Malazgirt,Muş Merkez,Varto,Hasköy,Korkut;Nevşehir%Avanos,Derinkuyu,Gülşehir,Hacıbektaş,Kozaklı,Nevşehir Merkez,Ürgüp,Acıgöl;Niğde%Bor,Çamardı,Niğde Merkez,Ulukışla,Altunhisar,Çiftlik;Ordu%Akkuş,Aybastı,Fatsa,Gölköy,Korgan,Kumru,Mesudiye,Ordu Merkez,Perşembe,Ulubey / Ordu,Ünye,Gülyalı,Gürgentepe,Çamaş,Çatalpınar,Çaybaşı,İkizce,Kabadüz,Kabataş,Altınordu;Rize%Ardeşen,Çamlıhemşin,Çayeli,Fındıklı,İkizdere,Kalkandere,Pazar / Rize,Rize Merkez,Güneysu,Derepazarı,Hemşin,İyidere;Sakarya%Akyazı,Geyve,Hendek,Karasu,Kaynarca,Sakarya Merkez,Sapanca,Kocaali,Pamukova,Taraklı,Ferizli,Karapürçek,Söğütlü,Adapazarı,Arifiye,Erenler,Serdivan;Samsun%Alaçam,Bafra,Çarşamba,Havza,Kavak,Ladik,Samsun Merkez,Terme,Vezirköprü,Asarcık,19 Mayıs,Salıpazarı,Tekkeköy,Ayvacık / Samsun,Yakakent,Atakum,Canik,İlkadım;Siirt%Baykan,Eruh,Kurtalan,Pervari,Siirt Merkez,Şirvan,Tillo;Sinop%Ayancık,Boyabat,Durağan,Erfelek,Gerze,Sinop Merkez,Türkeli,Dikmen,Saraydüzü;Sivas%Divriği,Gemerek,Gürün,Hafik,İmranlı,Kangal,Koyulhisar,Sivas Merkez,Suşehri,Şarkışla,Yıldızeli,Zara,Akıncılar,Altınyayla / Sivas,Doğanşar,Gölova,Ulaş;Tekirdağ%Çerkezköy,Çorlu,Hayrabolu,Malkara,Muratlı,Saray / Tekirdağ,Şarköy,Tekirdağ Merkez,Marmaraereğlisi,Ergene,Kapaklı,Süleymanpaşa;Tokat%Almus,Artova,Erbaa,Niksar,Reşadiye,Tokat Merkez,Turhal,Zile,Pazar / Tokat,Yeşilyurt / Tokat,Başçiftlik,Sulusaray;Trabzon%Akçaabat,Araklı,Arsin,Çaykara,Maçka,Of,Sürmene,Tonya,Trabzon Merkez,Vakfıkebir,Yomra,Beşikdüzü,Şalpazarı,Çarşıbaşı,Dernekpazarı,Düzköy,Hayrat,Köprübaşı / Trabzon,Ortahisar;Tunceli%Çemişgezek,Hozat,Mazgirt,Nazımiye,Ovacık / Tunceli,Pertek,Pülümür,Tunceli Merkez;Şanlıurfa%Akçakale,Birecik,Bozova,Ceylanpınar,Halfeti,Hilvan,Siverek,Suruç,Şanlıurfa Merkez,Viranşehir,Harran,Eyyübiye,Haliliye,Karaköprü;Uşak%Banaz,Eşme,Karahallı,Sivaslı,Ulubey / Uşak,Uşak Merkez;Van%Başkale,Çatak,Erciş,Gevaş,Gürpınar,Muradiye,Özalp,Van Merkez,Bahçesaray,Çaldıran,Edremit / Van,Saray / Van,İpekyolu,Tuşba;Yozgat%Akdağmadeni,Boğazlıyan,Çayıralan,Çekerek,Sarıkaya,Sorgun,Şefaatli,Yerköy,Yozgat Merkez,Aydıncık / Yozgat,Çandır,Kadışehri,Saraykent,Yenifakılı;Zonguldak%Çaycuma,Devrek,Ereğli / Zonguldak,Zonguldak Merkez,Alaplı,Gökçebey,Kilimli,Kozlu;Aksaray%Aksaray Merkez,Ortaköy / Aksaray,Ağaçören,Güzelyurt,Sarıyahşi,Eskil,Gülağaç,Sultanhanı;Bayburt%Bayburt Merkez,Aydıntepe,Demirözü;Karaman%Ermenek,Karaman Merkez,Ayrancı,Kazımkarabekir,Başyayla,Sarıveliler;Kırıkkale%Delice,Keskin,Kırıkkale Merkez,Sulakyurt,Bahşili,Balışeyh,Çelebi,Karakeçili,Yahşihan;Batman%Batman Merkez,Beşiri,Gercüş,Kozluk,Sason,Hasankeyf;Şırnak%Beytüşşebap,Cizre,İdil,Silopi,Şırnak Merkez,Uludere,Güçlükonak;Bartın%Bartın Merkez,Kurucaşile,Ulus,Amasra;Ardahan%Ardahan Merkez,Çıldır,Göle,Hanak,Posof,Damal;Iğdır%Aralık,Iğdır Merkez,Tuzluca,Karakoyunlu;Yalova%Yalova Merkez,Altınova,Armutlu,Çınarcık,Çiftlikköy,Termal;Karabük%Eflani,Eskipazar,Karabük Merkez,Ovacık / Karabük,Safranbolu,Yenice / Karabük;Kilis%Kilis Merkez,Elbeyli,Musabeyli,Polateli;Osmaniye%Bahçe,Kadirli,Osmaniye Merkez,Düziçi,Hasanbeyli,Sumbas,Toprakkale;Düzce%Akçakoca,Düzce Merkez,Yığılca,Cumayeri,Gölyaka,Çilimli,Gümüşova,Kaynaşlı";
    public static final String[] educationAttainments = new String[] { "Okuryazar", "İlköğretim Mezunu", "Lise Öğrencisi", "Lise Mezunu", "Lisans Öğrencisi", "Lisans Mezunu", "Yükseklisans Öğrencisi", "Yükseklisans Mezunu", "Doktora Öğrencisi", "Doktor" };
    public static final String STUDENT_USER_AGREEMENT = "";
    public static final String TUTOR_USER_AGREEMENT = "";
    public static final String MARKETER_USER_AGREEMENT = "";


    public static String USER_INSTALL_REFERRAL_CAMPAIGN_NAME = "";


    //TUTOR DISCOVERY
    public static int TUTORS_AT_A_TIME_TO_DISPLAY = 10;
    public static int COMMENTS_AT_A_TIME_TO_DISPLAY = 5;

    //SHOPPING CART
    public static int SOLUTIONS_ON_AT_A_TIME_TO_DISPLAY = 10;
    public static double DEFAULT_DISPLAY_PRICE = 0.50;

    public enum PURCHASE_RESPONSE
    {
        PURCHASING,
        CONNECTION_FAILED,
        INSUFFICIENT_BALANCE,
        ALREADY_PURCHASED,
        PURCHASING_FREE
    }

    //PURCHASE VARIABLES
    public static double PURCHASE_UNIT_FOR_RECOMMENDED_SOLUTIONS = 0.5;
    public static double PURCHASE_UNIT_FOR_REQUESTED_SOLUTIONS = 1.0;

    public enum PRODUCT
    {
        SOLUTION,
        LECTURE
    }

    //Photo Image Uploading
    public static final int MAXIMUM_USER_PHOTO_SIZE_IN_BYTES = 60000;
    public static final int MAXIMUM_IMAGE_SIZE_IN_BYTES = 60000;

    public static final int MAXIMUM_MINI_IMAGE_SIZE_IN_BYTES = 5000;
    public static final int MAXIMUM_MINI_USER_PHOTO_IN_BYTES = 15000;


    //QUESTION REQUESTS
    public enum QUESTION_REQUEST_STATE
    {
        PENDING_FOR_TUTOR_ACCEPTANCE,
        REQUEST_CANCELED_BY_STUDENT,
        REQUEST_REJECTED_BY_TUTOR,
        TIMED_OUT_FOR_TUTOR_ACCEPTANCE,
        REQUEST_ACCEPTED_BY_TUTOR_AND_STUDENT_PENDING_FOR_TUTORS_SOLUTION,
        PENDING_FOR_STUDENT_ACCEPTANCE,
        ACCEPTED_BY_STUDENT,
        SOLUTION_OBJECTED_BY_STUDENT,
        REPORTED_BY_STUDENT,
        SOLUTION_JOB_CANCELLED_BY_TUTOR,
        TIMED_OUT_FOR_TUTOR_SOLUTION,
        DIRECTLY_POSTED_BY_TUTOR,
        PAYMENT_ACCEPTED_BY_SYSTEM
    }

    //TODO You may specialize the Strings below for both in the eyes of students and tutors >>> QUESTION_REQUEST_STATE_NAME_FOR_STUDENT_RESOURCE_IDS, QUESTION_REQUEST_STATE_NAME_FOR_TUTOR_RESOURCE_IDS
    public static int[] QUESTION_REQUEST_STATE_NAME_FOR_STUDENT_RESOURCE_IDS = new int[] {
            R.string.PENDING_FOR_TUTOR_ACCEPTANCE,
            R.string.REQUEST_CANCELED_BY_STUDENT,
            R.string.REQUEST_REJECTED_BY_TUTOR,
            R.string.TIMED_OUT_FOR_TUTOR_ACCEPTANCE,
            R.string.REQUEST_ACCEPTED_BY_TUTOR_AND_STUDENT_PENDING_FOR_TUTORS_SOLUTION,
            R.string.PENDING_FOR_STUDENT_ACCEPTANCE,
            R.string.ACCEPTED_BY_STUDENT,
            R.string.SOLUTION_OBJECTED_BY_STUDENT,
            R.string.REPORTED_BY_STUDENT,
            R.string.SOLUTION_JOB_CANCELLED_BY_TUTOR,
            R.string.TIMED_OUT_FOR_TUTOR_SOLUTION,
            R.string.DIRECTLY_POSTED_BY_TUTOR,
            R.string.PAYMENT_ACCEPTED_BY_SYSTEM_For_Student

    };


    public static int[] QUESTION_REQUEST_STATE_NAME_FOR_TUTOR_RESOURCE_IDS = new int[] {
            R.string.PENDING_FOR_TUTOR_ACCEPTANCE,
            R.string.REQUEST_CANCELED_BY_STUDENT,
            R.string.REQUEST_REJECTED_BY_TUTOR,
            R.string.TIMED_OUT_FOR_TUTOR_ACCEPTANCE,
            R.string.REQUEST_ACCEPTED_BY_TUTOR_AND_STUDENT_PENDING_FOR_TUTORS_SOLUTION,
            R.string.PENDING_FOR_STUDENT_ACCEPTANCE,
            R.string.ACCEPTED_BY_STUDENT,
            R.string.SOLUTION_OBJECTED_BY_STUDENT,
            R.string.REPORTED_BY_STUDENT,
            R.string.SOLUTION_JOB_CANCELLED_BY_TUTOR,
            R.string.TIMED_OUT_FOR_TUTOR_SOLUTION,
            R.string.DIRECTLY_POSTED_BY_TUTOR,
            R.string.PAYMENT_ACCEPTED_BY_SYSTEM_For_Tutor
    };

    public static int[] questionRequestStatusAccordingToTutors = new int[] {
            R.string.question_requests_not_answered_yet_question_requests,
            R.string.question_requests_pending_for_answer_questions,
            R.string.question_requests_pending_approval_answers,
            R.string.question_requests_approved_answers,
            R.string.question_requests_rejected_answers,
            R.string.question_requests_questions_you_rejected,
            R.string.question_requests_canceled_or_timed_out
    };



    public static int[] questionRequestStatusAccordingToStudents = new int[] {
            R.string.question_requests_for_students_not_answered_yet_question_requests,
            R.string.question_requests_for_students_pending_for_answer_questions,
            R.string.question_requests_for_students_pending_approval_answers,
            R.string.question_requests_for_students_approved_answers,
            R.string.question_requests_for_students_rejected_answers,
            R.string.question_requests_for_students_questions_you_rejected,
            R.string.question_requests_you_canceled_or_timed_out
    };

    public static int[] questionRequestObjectionReasons = new int[] {
            R.string.question_requests_objection_reason_explaination_not_comprehensible,
            R.string.question_requests_objection_reason_not_looking_so_competent,
            R.string.question_requests_objection_reason_wrong_solution,
            R.string.question_requests_objection_reason_video_doesnt_include_solution,
            R.string.question_requests_objection_reason_other
    };


    public static int[] questionRequestWithdrawalExcuses = new int[] {
            R.string.question_requests_withdrawal_excuse_not_using_the_time_well,
            R.string.question_requests_withdrawal_unexpected_interruption,
            R.string.question_requests_withdrawal_excuse_other
    };


    public static int[] questionRequestReportAbuseReasons = new int[] {
            R.string.question_requests_abuse_reason_sexual_content,
            R.string.question_requests_abuse_reason_violent_or_repulsive_content,
            R.string.question_requests_abuse_reason_hateful_or_abusive_content,
            R.string.question_requests_abuse_reason_harmful_dangerous_acts,
            R.string.question_requests_abuse_reason_child_abuse,
            R.string.question_requests_abuse_reason_promates_terrorism,
            R.string.question_requests_abuse_reason_spam_or_misleading,
            R.string.question_requests_abuse_reason_infringes_my_rights
    };


    public static int[] questionRequestObjectionResults = new int[] {
            R.string.question_requests_objection_result_objection_result_cannot_be_displayed_right_now,
            R.string.question_requests_objection_result_objection_being_reviewed,
            R.string.question_requests_objection_result_content_removed,
            R.string.question_requests_objection_result_content_removed_fee_refunded,
            R.string.question_requests_objection_result_content_removed_fee_refunded_tutor_warned,
            R.string.question_requests_objection_result_content_removed_fee_refunded_tutor_suspended,
            R.string.question_requests_objection_result_objection_reason_cannot_be_detected
    };


    public static int[] questionRequestAbuseReportResults = new int[] {
            R.string.question_requests_objection_result_objection_result_cannot_be_displayed_right_now,
            R.string.question_requests_objection_result_objection_being_reviewed,
            R.string.question_requests_objection_result_content_removed,
            R.string.question_requests_objection_result_content_removed_fee_refunded_tutor_warned,
            R.string.question_requests_objection_result_content_removed_fee_refunded_tutor_suspended,
            R.string.question_requests_abuse_result_abuse_reason_cannot_be_detected
    };


    //PAYMENT REQUESTS
    enum PAYMENT_REQUEST_STATE
    {
        ACTIVE_BEING_REVIEWED,
        PAID_COMPLETELY,
        PAID_PARTIALLY,
        TEMPORARILY_SUSPENDED_DUE_TO_SUSPICIOUS_ACTIVITY
    }

    public static int[] PAYMENT_REQUEST_STATE_NAME_IDS = new int[] {
            R.string.ACTIVE_BEING_REVIEWED,
            R.string.PAID_COMPLETELY,
            R.string.PAID_PARTIALLY,
            R.string.TEMPORARILY_SUSPENDED_DUE_TO_SUSPICIOUS_ACTIVITY
    };







    public static void InstantiateSQLiteDatabase(Context context)
    {
        myDb = new DatabaseHelper(context);
        db = myDb.getWritableDatabase();
    }

    public static void InstantiateLessonsAndClasses()
    {
        lessonsAndClasses = new LessonsAndClasses();
    }

    public static void Toast(Context c, String message)
    {
        Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
    }


    public static void Log(Context getApplicationContext, String message)
    {
        Log.d(LOG_TAG, message);
    }

    public static void AlertDialogDisplay(Context Activity_this, String message)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(Activity_this).create();
        //alertDialog.setMessage(message);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View crView = LayoutInflater.from(Activity_this).inflate(R.layout.custom_alert_dialog_view, null);
        ((TextView)crView.findViewById(R.id.texViewMessage)).setText(message);
        TextView buttonPositive = crView.findViewById(R.id.texViewButtonPositive);
        final AlertDialog fDialog = alertDialog;
        buttonPositive.setText(Activity_this.getString(R.string.constant_ok));
        buttonPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fDialog.dismiss();
            }
        });
        alertDialog.setView(crView);
        alertDialog.show();
    }

    public static void AlertDialogQuestionDisplay(final Context Activity_this, boolean isZoomable, boolean isAnimated, String animatedGifAssetsName, String titleText, String textPositive, String textNegative, Bitmap bitmap)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(Activity_this).create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View crView = LayoutInflater.from(Activity_this).inflate(R.layout.custom_question_photo_display, null);
        ((TextView)crView.findViewById(R.id.textViewQuestionDisplayTitle)).setText(titleText);
        TextView buttonPositive = crView.findViewById(R.id.texViewButtonPositive);
        TextView buttonNegative = crView.findViewById(R.id.texViewButtonNegative);
        final WebView webView = crView.findViewById(R.id.webViewQuestionDisplayTitle);

        if(isAnimated)
        {
            DisplayAnimatedGifOnWebView(animatedGifAssetsName, webView);;
        }
        else
        {
            if(bitmap != null)
            {
                String html = "<html><body><img src='{IMAGE_PLACEHOLDER}' /></body></html>";
                // Convert bitmap to Base64 encoded image for web
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                String imageBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                String image = "data:image/png;base64," + imageBase64;
                // Use image for the img src parameter in your html and load to webview
                html = html.replace("{IMAGE_PLACEHOLDER}", image);
                webView.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "utf-8", "");
            }
        }


        if(isZoomable)
        {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setSupportZoom(true);
            webView.getSettings().setBuiltInZoomControls(true);

            LinearLayout linearLayoutZoomTutorial = crView.findViewById(R.id.linearLayoutZoomTutorial);
            linearLayoutZoomTutorial.setVisibility(View.VISIBLE);
            TextView textViewZoomTutorial = crView.findViewById(R.id.texViewZoomTutorial);
            textViewZoomTutorial.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GlobalVariables.AlertDialogQuestionDisplay(Activity_this, false, true, "pinch_zoom_tutorial.gif", Activity_this.getString(R.string.tutorial_how_to_zoom), Activity_this.getString(R.string.constant_ok), "", BitmapFactory.decodeResource(Activity_this.getResources(),R.drawable.pinch_zoom_tutorial));
                }
            });
        }

        final AlertDialog fDialog = alertDialog;
        buttonPositive.setText(textPositive);
        buttonPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fDialog.dismiss();
            }
        });
        alertDialog.setView(crView);
        alertDialog.show();
    }



    public static void AlertDialogQuestionDisplay(final Context Activity_this, boolean isZoomable, boolean isAnimated, String animatedGifAssetsName, String titleText, String textPositive, String textNegative, SolutionOnSale solutionOnSale, CustomPurchasedSolutionsLinearLayout customPurchasedSolutionsLinearLayout, Bitmap bitmap)
    {
        final SolutionOnSale crSolutionOnSale = solutionOnSale;
        final CustomPurchasedSolutionsLinearLayout crCustomPurchasedSolutionsLinearLayout = customPurchasedSolutionsLinearLayout;
        AlertDialog alertDialog = new AlertDialog.Builder(Activity_this).create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View crView = LayoutInflater.from(Activity_this).inflate(R.layout.custom_question_photo_display, null);
        ((TextView)crView.findViewById(R.id.textViewQuestionDisplayTitle)).setText(titleText);
        TextView buttonPositive = crView.findViewById(R.id.texViewButtonPositive);
        TextView buttonNegative = crView.findViewById(R.id.texViewButtonNegative);
        LinearLayout linearLayoutPositive = crView.findViewById(R.id.linearLayoutPositive);
        LinearLayout linearLayoutNegative = crView.findViewById(R.id.linearLayoutButtonNegative);
        ImageView imageViewMiniYoutubeIcon = crView.findViewById(R.id.imageViewMiniYoutubeIcon);
        final WebView webView = crView.findViewById(R.id.webViewQuestionDisplayTitle);

        if(isAnimated)
        {
            DisplayAnimatedGifOnWebView(animatedGifAssetsName, webView);;
        }
        else
        {
            if(bitmap != null)
            {
                String html = "<html><body><img src='{IMAGE_PLACEHOLDER}' /></body></html>";
                // Convert bitmap to Base64 encoded image for web
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                String imageBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                String image = "data:image/png;base64," + imageBase64;
                // Use image for the img src parameter in your html and load to webview
                html = html.replace("{IMAGE_PLACEHOLDER}", image);
                webView.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "utf-8", "");
            }
        }


        if(isZoomable)
        {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setSupportZoom(true);
            webView.getSettings().setBuiltInZoomControls(true);

            LinearLayout linearLayoutZoomTutorial = crView.findViewById(R.id.linearLayoutZoomTutorial);
            linearLayoutZoomTutorial.setVisibility(View.VISIBLE);
            TextView textViewZoomTutorial = crView.findViewById(R.id.texViewZoomTutorial);
            textViewZoomTutorial.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GlobalVariables.AlertDialogQuestionDisplay(Activity_this, false, true, "pinch_zoom_tutorial.gif", Activity_this.getString(R.string.tutorial_how_to_zoom), Activity_this.getString(R.string.constant_ok), "", crSolutionOnSale, crCustomPurchasedSolutionsLinearLayout, BitmapFactory.decodeResource(Activity_this.getResources(),R.drawable.pinch_zoom_tutorial));
                }
            });
        }

        final AlertDialog fDialog = alertDialog;
        if(textNegative.isEmpty())
        {
            buttonPositive.setText(textPositive);
            buttonPositive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fDialog.dismiss();
                }
            });
        }
        else
        {
            buttonPositive.setText(textPositive);
            buttonNegative.setText(textNegative.toUpperCase());
            imageViewMiniYoutubeIcon.setVisibility(View.VISIBLE);
            linearLayoutNegative.setVisibility(View.VISIBLE);
            buttonNegative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fDialog.dismiss();
                }
            });
            linearLayoutPositive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    crCustomPurchasedSolutionsLinearLayout.DisplayYoutubeSolutionVideo(crSolutionOnSale.questionAnswerVideoURL);
                }
            });
        }
        alertDialog.setView(crView);
        alertDialog.show();
    }





    public static void AlertDialogQuestionDisplay(final Context Activity_this, boolean isZoomable, boolean isAnimated, String animatedGifAssetsName, String titleText, String textPositive, String textNegative, SolutionOnSale solutionOnSale, CustomSolutionOnSaleForPopularRecommendedLinearLayout customSolutionOnSaleForPopularRecommendedLinearLayout, Bitmap bitmap)
    {
        final SolutionOnSale crSolutionOnSale = solutionOnSale;
        final CustomSolutionOnSaleForPopularRecommendedLinearLayout crCustomSolutionOnSaleForPopularRecommendedLinearLayout = customSolutionOnSaleForPopularRecommendedLinearLayout;
        AlertDialog alertDialog = new AlertDialog.Builder(Activity_this).create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View crView = LayoutInflater.from(Activity_this).inflate(R.layout.custom_question_photo_display, null);
        ((TextView)crView.findViewById(R.id.textViewQuestionDisplayTitle)).setText(titleText);
        TextView buttonPositive = crView.findViewById(R.id.texViewButtonPositive);
        TextView buttonNegative = crView.findViewById(R.id.texViewButtonNegative);
        LinearLayout linearLayoutPositive = crView.findViewById(R.id.linearLayoutPositive);
        LinearLayout linearLayoutNegative = crView.findViewById(R.id.linearLayoutButtonNegative);
        ImageView imageViewMiniYoutubeIcon = crView.findViewById(R.id.imageViewMiniYoutubeIcon);
        final WebView webView = crView.findViewById(R.id.webViewQuestionDisplayTitle);

        if(isAnimated)
        {
            DisplayAnimatedGifOnWebView(animatedGifAssetsName, webView);;
        }
        else
        {
            if(bitmap != null)
            {
                String html = "<html><body><img src='{IMAGE_PLACEHOLDER}' /></body></html>";
                // Convert bitmap to Base64 encoded image for web
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                String imageBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                String image = "data:image/png;base64," + imageBase64;
                // Use image for the img src parameter in your html and load to webview
                html = html.replace("{IMAGE_PLACEHOLDER}", image);
                webView.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "utf-8", "");
            }
        }


        if(isZoomable)
        {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setSupportZoom(true);
            webView.getSettings().setBuiltInZoomControls(true);

            LinearLayout linearLayoutZoomTutorial = crView.findViewById(R.id.linearLayoutZoomTutorial);
            linearLayoutZoomTutorial.setVisibility(View.VISIBLE);
            TextView textViewZoomTutorial = crView.findViewById(R.id.texViewZoomTutorial);
            textViewZoomTutorial.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GlobalVariables.AlertDialogQuestionDisplay(Activity_this, false, true, "pinch_zoom_tutorial.gif", Activity_this.getString(R.string.tutorial_how_to_zoom), Activity_this.getString(R.string.constant_ok), "", crSolutionOnSale, crCustomSolutionOnSaleForPopularRecommendedLinearLayout, BitmapFactory.decodeResource(Activity_this.getResources(),R.drawable.pinch_zoom_tutorial));
                }
            });
        }

        final AlertDialog fDialog = alertDialog;
        if(textNegative.isEmpty())
        {
            buttonPositive.setText(textPositive);
            buttonPositive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fDialog.dismiss();
                }
            });
        }
        else
        {
            buttonPositive.setText(textPositive);
            buttonNegative.setText(textNegative.toUpperCase());
            imageViewMiniYoutubeIcon.setVisibility(View.VISIBLE);
            linearLayoutNegative.setVisibility(View.VISIBLE);
            buttonNegative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fDialog.dismiss();
                }
            });
            linearLayoutPositive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO BUY
                    GlobalVariables.PURCHASE_RESPONSE response = ServerHelper.TryPurchasingTheSolution(Activity_this, GlobalVariables.USER_ID, crSolutionOnSale.displayPrice, crSolutionOnSale.questionRequestID);
                    GlobalVariables.Log(Activity_this, "PURCHASE_RESPONSE response: "+ response);
                    if(response == GlobalVariables.PURCHASE_RESPONSE.PURCHASING)
                    {
                        crCustomSolutionOnSaleForPopularRecommendedLinearLayout.PurchaseSolution(GlobalVariables.USER_ID, false, crSolutionOnSale.questionRequestID, crSolutionOnSale.questionAnswerVideoURL);
                    }
                    else if(response == GlobalVariables.PURCHASE_RESPONSE.PURCHASING_FREE)
                    {
                        crCustomSolutionOnSaleForPopularRecommendedLinearLayout.PurchaseSolution(GlobalVariables.USER_ID, true, crSolutionOnSale.questionRequestID, crSolutionOnSale.questionAnswerVideoURL);
                    }
                    else if(response == GlobalVariables.PURCHASE_RESPONSE.CONNECTION_FAILED)
                    {
                        GlobalVariables.AlertDialogDisplay(crCustomSolutionOnSaleForPopularRecommendedLinearLayout.activity, crCustomSolutionOnSaleForPopularRecommendedLinearLayout.context.getString(R.string.shopping_cart_failed_to_purchase_please_try_again));
                    }
                    else if(response == GlobalVariables.PURCHASE_RESPONSE.ALREADY_PURCHASED)
                    {
                        ServerHelper.AddToPurchasedSolutions(GlobalVariables.USER_ID, crSolutionOnSale.questionRequestID, crSolutionOnSale.displayPrice);
                        crCustomSolutionOnSaleForPopularRecommendedLinearLayout.DisplayYoutubeSolutionVideo(crSolutionOnSale.questionAnswerVideoURL);
                    }
                    else if(response == GlobalVariables.PURCHASE_RESPONSE.INSUFFICIENT_BALANCE)
                    {
                        GlobalVariables.AlertDialogDisplay(crCustomSolutionOnSaleForPopularRecommendedLinearLayout.activity, crCustomSolutionOnSaleForPopularRecommendedLinearLayout.context.getString(R.string.shopping_cart_insufficient_balance) + " (₺"+crSolutionOnSale.displayPrice+")");
                    }
                }
            });
        }
        alertDialog.setView(crView);
        alertDialog.show();
    }



    public static void AlertDialogQuestionDisplay(final Context Activity_this, boolean isZoomable, boolean isAnimated, String animatedGifAssetsName, String titleText, String textPositive, String textNegative, QuestionRequest questionRequest, CustomQuestionAnswerLinearLayout customQuestionAnswerLinearLayout, Bitmap bitmap)
    {
        final QuestionRequest crQuestionRequest = questionRequest;
        final CustomQuestionAnswerLinearLayout crCustomQuestionAnswerLinearLayout = customQuestionAnswerLinearLayout;
        AlertDialog alertDialog = new AlertDialog.Builder(Activity_this).create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View crView = LayoutInflater.from(Activity_this).inflate(R.layout.custom_question_photo_display, null);
        ((TextView)crView.findViewById(R.id.textViewQuestionDisplayTitle)).setText(titleText);
        TextView buttonPositive = crView.findViewById(R.id.texViewButtonPositive);
        TextView buttonNegative = crView.findViewById(R.id.texViewButtonNegative);
        LinearLayout linearLayoutPositive = crView.findViewById(R.id.linearLayoutPositive);
        LinearLayout linearLayoutNegative = crView.findViewById(R.id.linearLayoutButtonNegative);
        ImageView imageViewMiniYoutubeIcon = crView.findViewById(R.id.imageViewMiniYoutubeIcon);
        final WebView webView = crView.findViewById(R.id.webViewQuestionDisplayTitle);

        if(isAnimated)
        {
            DisplayAnimatedGifOnWebView(animatedGifAssetsName, webView);;
        }
        else
        {
            if(bitmap != null)
            {
                String html = "<html><body><img src='{IMAGE_PLACEHOLDER}' /></body></html>";
                // Convert bitmap to Base64 encoded image for web
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                String imageBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                String image = "data:image/png;base64," + imageBase64;
                // Use image for the img src parameter in your html and load to webview
                html = html.replace("{IMAGE_PLACEHOLDER}", image);
                webView.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "utf-8", "");
            }
        }


        if(isZoomable)
        {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setSupportZoom(true);
            webView.getSettings().setBuiltInZoomControls(true);

            LinearLayout linearLayoutZoomTutorial = crView.findViewById(R.id.linearLayoutZoomTutorial);
            linearLayoutZoomTutorial.setVisibility(View.VISIBLE);
            TextView textViewZoomTutorial = crView.findViewById(R.id.texViewZoomTutorial);
            textViewZoomTutorial.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GlobalVariables.AlertDialogQuestionDisplay(Activity_this, false, true, "pinch_zoom_tutorial.gif", Activity_this.getString(R.string.tutorial_how_to_zoom), Activity_this.getString(R.string.constant_ok), "", BitmapFactory.decodeResource(Activity_this.getResources(),R.drawable.pinch_zoom_tutorial));
                }
            });
        }

        final AlertDialog fDialog = alertDialog;
        if(textNegative.isEmpty())
        {
            buttonPositive.setText(textPositive);
            buttonPositive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fDialog.dismiss();
                }
            });
        }
        else
        {
            buttonPositive.setText(textPositive);
            buttonNegative.setText(textNegative.toUpperCase());
            imageViewMiniYoutubeIcon.setVisibility(View.VISIBLE);
            linearLayoutNegative.setVisibility(View.VISIBLE);
            buttonNegative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fDialog.dismiss();
                }
            });


            if(crQuestionRequest.solutionVideoUrlID.isEmpty() || crQuestionRequest.solutionVideoUrlID.equals(DB.CONS_EMPTY))
            {
                linearLayoutNegative.setVisibility(View.INVISIBLE);
                imageViewMiniYoutubeIcon.setVisibility(View.GONE);
                buttonPositive.setText(textNegative);
                buttonPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fDialog.dismiss();
                    }
                });
            }
            else
            {
                if(GlobalVariables.USER_STATUS == GlobalVariables.KEY_USER_STATUS_TUTOR)
                {
                    linearLayoutPositive.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //TODO DISPLAY
                            if (!crQuestionRequest.solutionVideoUrlID.isEmpty() && !crQuestionRequest.solutionVideoUrlID.equals(DB.CONS_EMPTY)) {
                                crCustomQuestionAnswerLinearLayout.DisplayYoutubeSolutionVideo(crQuestionRequest.solutionVideoUrlID);
                            }
                        }
                    });
                }
                else if(GlobalVariables.USER_STATUS == GlobalVariables.KEY_USER_STATUS_STUDENT)
                {
                    linearLayoutPositive.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //TODO BUY
                            crCustomQuestionAnswerLinearLayout.DisplayYoutubeSolutionVideo(crQuestionRequest.solutionVideoUrlID);
                            /*
                            GlobalVariables.PURCHASE_RESPONSE response = ServerHelper.TryPurchasingTheSolution(Activity_this, GlobalVariables.USER_ID, crQuestionRequest.appreciatedPrice, crQuestionRequest.questionRequestID);
                            GlobalVariables.Log(Activity_this, "PURCHASE_RESPONSE response: "+ response);
                            if(response == GlobalVariables.PURCHASE_RESPONSE.PURCHASING)
                            {
                                crCustomQuestionAnswerLinearLayout.PurchaseSolution(GlobalVariables.USER_ID, false, crQuestionRequest.questionRequestID, crQuestionRequest.solutionVideoUrlID);
                            }
                            else if(response == GlobalVariables.PURCHASE_RESPONSE.PURCHASING_FREE)
                            {
                                crCustomQuestionAnswerLinearLayout.PurchaseSolution(GlobalVariables.USER_ID, true, crQuestionRequest.questionRequestID, crQuestionRequest.solutionVideoUrlID);
                            }
                            else if(response == GlobalVariables.PURCHASE_RESPONSE.CONNECTION_FAILED)
                            {
                                GlobalVariables.AlertDialogDisplay(crCustomQuestionAnswerLinearLayout.activity, crCustomQuestionAnswerLinearLayout.context.getString(R.string.shopping_cart_failed_to_purchase_please_try_again));
                            }
                            else if(response == GlobalVariables.PURCHASE_RESPONSE.ALREADY_PURCHASED)
                            {
                                ServerHelper.AddToPurchasedSolutions(GlobalVariables.USER_ID, crQuestionRequest.questionRequestID, crQuestionRequest.appreciatedPrice);
                                crCustomQuestionAnswerLinearLayout.DisplayYoutubeSolutionVideo(crQuestionRequest.solutionVideoUrlID);
                            }
                            else if(response == GlobalVariables.PURCHASE_RESPONSE.INSUFFICIENT_BALANCE)
                            {
                                GlobalVariables.AlertDialogDisplay(crCustomQuestionAnswerLinearLayout.activity, crCustomQuestionAnswerLinearLayout.context.getString(R.string.shopping_cart_insufficient_balance) + " (₺"+crQuestionRequest.appreciatedPrice+")");
                            }*/
                        }
                    });
                }
            }
        }
        alertDialog.setView(crView);
        alertDialog.show();
    }

    public static void DisplayAnimatedGifOnWebView(String aniGifFileNameWithExtension, WebView webView)
    {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        //webView.loadUrl("file:///android_asset/" + aniGifFileNameWithExtension);
        webView.loadDataWithBaseURL("file:///android_asset/","<html><center><img src=\"" + aniGifFileNameWithExtension + "\"></html>","text/html","utf-8","");
    }

    public static void AlertDialogDisplay(Context Activity_this, String message, String title)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(Activity_this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, Activity_this.getString(R.string.constant_ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public static void FillUserVariables(Activity tempActivity, Context tempContext, String USER_NAME)
    {
        GlobalVariables.USER_NAME = USER_NAME;
        USER_ID = ServerHelper.GetUserIDByUserName(USER_NAME);
    }

    public static Bitmap GetBitmapFromImageView(ImageView iv)
    {
        BitmapDrawable drawable = (BitmapDrawable) iv.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        return bitmap;
    }

}
