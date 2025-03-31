package com.g5.fbscripts

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
import com.google.cloud.firestore.Firestore
import java.io.FileInputStream

data class Symbol(
    val name: String,
    val category: String,
    val description: String,
    val history: String,
    val url: String,
    val tags: List<String>
)

fun initFirebase() {
    val serviceAccount = FileInputStream("src/main/resources/firebase-admin.json")
    val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .build()
    FirebaseApp.initializeApp(options)
    println("✅ Firebase initialized.")
}

fun uploadSymbols(symbols: List<Symbol>) {
    val db: Firestore = FirestoreClient.getFirestore()
    symbols.forEach { symbol ->
        try {
            val result = db.collection("symbols").document(symbol.name).set(symbol).get()
            println("✅ Uploaded symbol: ${symbol.name}")
        } catch (e: Exception) {
            println("❌ Failed to upload ${symbol.name}: ${e.message}")
        }
    }
}

fun main() {
    initFirebase()
    uploadSymbols(symbols)
    println("☑\uFE0F Completed Upload Procedure ☑\uFE0F")
}

val symbols = listOf(
    Symbol( // Religious Symbols
        name = "Christian Cross",
        category = "Christianity",
        description = "A cross symbolizing the crucifixion of Jesus Christ.",
        history = "The cross became a symbol of Christianity as early as the 2nd century AD.",
        url = "https://en.wikipedia.org/wiki/Christian_cross",
        tags = listOf("vertical line intersected by horizontal line", "church", "Christianity", "crucifixion", "symbol of faith")
    ),
    Symbol(
        name = "Ichthys",
        category = "Christianity",
        description = "A fish symbol used by early Christians to identify themselves.",
        history = "The Ichthys symbol dates back to the 2nd century AD and was used during times of persecution.",
        url = "https://en.wikipedia.org/wiki/Ichthys",
        tags = listOf("simple outline of a fish", "Christianity", "early Christian symbol", "secret identification")
    ),
    Symbol(
        name = "Star of David",
        category = "Judaism",
        description = "A six-pointed star commonly associated with Judaism.",
        history = "The Star of David became associated with Jewish communities in the Middle Ages.",
        url = "https://en.wikipedia.org/wiki/Star_of_David",
        tags = listOf("two overlapping triangles", "hexagram", "Judaism", "Jewish identity", "synagogue")
    ),
    Symbol(
        name = "Menorah",
        category = "Judaism",
        description = "A seven-branched candelabrum used in the ancient Temple in Jerusalem.",
        history = "The Menorah has been a symbol of Judaism since ancient times, representing the burning bush as seen by Moses.",
        url = "https://en.wikipedia.org/wiki/Menorah_(Temple)",
        tags = listOf("seven branches", "candelabrum", "Judaism", "Temple in Jerusalem", "Hanukkah")
    ),
    Symbol(
        name = "Crescent and Star",
        category = "Islam",
        description = "A symbol featuring a crescent moon and a star, commonly associated with Islam.",
        history = "The crescent and star were used as symbols in the Ottoman Empire and later became associated with Islam.",
        url = "https://en.wikipedia.org/wiki/Star_and_crescent",
        tags = listOf("crescent moon with a star", "Islam", "mosque", "Ottoman Empire", "symbol of faith")
    ),
    Symbol(
        name = "Khanda",
        category = "Sikhism",
        description = "The main symbol of Sikhism, featuring a double-edged sword flanked by two single-edged swords.",
        history = "The Khanda represents the concepts of truth, justice, and spiritual sovereignty in Sikhism.",
        url = "https://en.wikipedia.org/wiki/Khanda_(Sikh_symbol)",
        tags = listOf("double-edged sword", "two single-edged swords", "Sikhism", "symbol of unity", "Gurdwara")
    ),
    Symbol(
        name = "Om",
        category = "Hinduism",
        description = "A sacred sound and spiritual icon in Hinduism, representing the universe.",
        history = "The Om symbol has been used since ancient times in Hindu rituals and scriptures.",
        url = "https://en.wikipedia.org/wiki/Om",
        tags = listOf("stylized character resembling 3 with a tail", "Hinduism", "sacred sound", "meditation", "Vedas")
    ),
    Symbol(
        name = "Dharma Wheel",
        category = "Buddhism",
        description = "A wheel with eight spokes representing the Noble Eightfold Path in Buddhism.",
        history = "The Dharma Wheel has been a symbol of Buddhism since its early days, symbolizing the teachings of Buddha.",
        url = "https://en.wikipedia.org/wiki/Dharmachakra",
        tags = listOf("wheel with eight spokes", "Buddhism", "Noble Eightfold Path", "monastery", "symbol of teaching")
    ),
    Symbol(
        name = "Ankh",
        category = "Ancient Egyptian Religion",
        description = "An ancient Egyptian symbol representing eternal life.",
        history = "The Ankh was widely used in ancient Egypt as a symbol of life and immortality.",
        url = "https://en.wikipedia.org/wiki/Ankh",
        tags = listOf("cross with a loop at the top", "ancient Egypt", "eternal life", "pharaoh", "hieroglyph")
    ),
    Symbol(
        name = "Pentagram",
        category = "Various",
        description = "A five-pointed star used in various religious and spiritual traditions.",
        history = "The pentagram has been used symbolically in various cultures, including ancient Greece and early Christianity.",
        url = "https://en.wikipedia.org/wiki/Pentagram",
        tags = listOf("five-pointed star", "Wicca", "paganism", "protection", "occult")
    ),
    Symbol(
        name = "Christogram (Chi Rho)",
        category = "Christianity",
        description = "An early Christian symbol combining the first two letters of 'Christ' in Greek (Χ and Ρ).",
        history = "Used by early Christians and adopted by Constantine I in the 4th century.",
        url = "https://en.wikipedia.org/wiki/Christogram",
        tags = listOf("XP shape", "Greek letters Chi and Rho", "Christianity", "ancient monogram", "imperial Roman symbol")
    ),
    Symbol(
        name = "Alpha and Omega",
        category = "Christianity",
        description = "Represents the beginning and the end, as referenced in the Book of Revelation.",
        history = "Used by early Christians to represent Jesus Christ as eternal.",
        url = "https://en.wikipedia.org/wiki/Alpha_and_Omega",
        tags = listOf("Greek letters A and Ω", "Christianity", "eternity", "Book of Revelation", "symbol of Christ")
    ),
    Symbol(
        name = "Taijitu (Yin Yang)",
        category = "Taoism",
        description = "Symbol representing the duality of all things in nature.",
        history = "A Taoist symbol describing balance between opposite forces; origin traced to ancient Chinese philosophy.",
        url = "https://en.wikipedia.org/wiki/Yin_and_Yang",
        tags = listOf("black and white circle with two dots", "Taoism", "balance", "harmony", "duality", "opposites")
    ),
    Symbol(
        name = "Jain Prateek Chihna",
        category = "Jainism",
        description = "The official symbol of Jainism representing the universe and path to liberation.",
        history = "Adopted in 1975 during the 2500th Nirvana anniversary of Lord Mahavira.",
        url = "https://en.wikipedia.org/wiki/Jain_symbols",
        tags = listOf("hand with wheel", "swastika", "crescent", "dot", "Jainism", "non-violence", "liberation")
    ),
    Symbol(
        name = "Ayyavazhi Lotus",
        category = "Ayyavazhi",
        description = "A lotus flower symbolizing spiritual enlightenment and detachment.",
        history = "Represents the ideology and faith of Ayyavazhi; connected to Tamil religious tradition.",
        url = "https://en.wikipedia.org/wiki/Ayyavazhi_symbol",
        tags = listOf("lotus flower", "spiritual awakening", "Ayyavazhi", "Tamil tradition", "divine consciousness")
    ),
    Symbol(
        name = "Faravahar",
        category = "Zoroastrianism",
        description = "Symbol of good thoughts, good words, and good deeds in Zoroastrianism.",
        history = "Ancient Persian symbol representing the human soul and divine guidance.",
        url = "https://en.wikipedia.org/wiki/Faravahar",
        tags = listOf("winged figure", "circle with human face", "Zoroastrianism", "Persian", "divine spirit", "eternal soul")
    ),
    Symbol(
        name = "Triple Moon",
        category = "Neopaganism / Wicca",
        description = "Represents the three aspects of the Goddess: Maiden, Mother, and Crone.",
        history = "Modern Wiccan symbol representing the lunar phases and divine feminine.",
        url = "https://en.wikipedia.org/wiki/Triple_Goddess_(Neopaganism)",
        tags = listOf("crescent-full-crescent", "Wicca", "moon phases", "feminine energy", "triple goddess", "divine woman")
    ),
    Symbol(
        name = "Rod of Asclepius",
        category = "Greek Religion / Healing",
        description = "A serpent-entwined rod representing healing and medicine.",
        history = "Linked to Asclepius, the Greek god of healing, and adopted in modern medicine.",
        url = "https://en.wikipedia.org/wiki/Rod_of_Asclepius",
        tags = listOf("rod with a single snake", "Greek god of healing", "medicine", "healthcare symbol", "snake and staff")
    ),
    Symbol(
        name = "Eye of Horus",
        category = "Ancient Egyptian Religion",
        description = "An ancient Egyptian symbol of protection, royal power, and good health.",
        history = "Used as an amulet by Egyptians to protect against evil and illness.",
        url = "https://en.wikipedia.org/wiki/Eye_of_Horus",
        tags = listOf("stylized eye with markings", "Egyptian mythology", "protection", "health", "amulet", "symbolic eye")
    ),
    Symbol(
        name = "Lotus Flower",
        category = "Hinduism / Buddhism",
        description = "A symbol of purity, enlightenment, and rebirth in both Hindu and Buddhist traditions.",
        history = "The lotus is associated with spiritual awakening, often depicted under deities like Vishnu, Brahma, and Lakshmi.",
        url = "https://en.wikipedia.org/wiki/Padma_(attribute)",
        tags = listOf("flower with symmetrical petals", "Hinduism", "Buddhism", "spiritual growth", "enlightenment", "purity")
    ),
    Symbol(
        name = "Swastika",
        category = "Hinduism / Buddhism / Jainism",
        description = "An ancient symbol representing good fortune, eternity, and well-being.",
        history = "Used in Indian religions for thousands of years before being appropriated in the 20th century.",
        url = "https://en.wikipedia.org/wiki/Swastika",
        tags = listOf("cross with bent arms", "Hinduism", "Jainism", "Buddhism", "auspicious symbol", "ancient geometric shape")
    ),
    Symbol(
        name = "Wheel of the Year",
        category = "Neopaganism",
        description = "A circle with eight segments representing the eight seasonal festivals in Neopagan traditions.",
        history = "Used in modern Wicca and Pagan practices to mark solar events and nature-based rituals.",
        url = "https://en.wikipedia.org/wiki/Wheel_of_the_Year",
        tags = listOf("circle divided into eight", "Wicca", "seasonal cycles", "earth-centered", "solar holidays")
    ),
    Symbol(
        name = "Nine-Pointed Star",
        category = "Baháʼí Faith",
        description = "A symbol of unity and the nine major world religions recognized in Baháʼí teachings.",
        history = "Used to represent the Baháʼí Faith’s teachings of spiritual unity and the number 9’s significance.",
        url = "https://en.wikipedia.org/wiki/Bah%C3%A1%CA%BC%C3%AD_symbols",
        tags = listOf("star with nine points", "Baháʼí Faith", "unity", "nine religions", "symbolic geometry")
    ),
    Symbol(
        name = "Triskele (Triskelion)",
        category = "Celtic Paganism",
        description = "A triple spiral symbol representing motion, life, and spiritual growth.",
        history = "Found in Neolithic and Celtic artifacts, later adopted by Neo-Druidism.",
        url = "https://en.wikipedia.org/wiki/Triskelion",
        tags = listOf("three spirals connected at center", "Celtic", "paganism", "motion", "triple goddess", "cycles of life")
    ),
    Symbol(
        name = "Triple Spiral (Spiral of Life)",
        category = "Celtic / Neopaganism",
        description = "Another form of the triskele representing life cycles, the divine feminine, and nature.",
        history = "Originates from Neolithic Europe and was later associated with Celtic traditions.",
        url = "https://en.wikipedia.org/wiki/Triple_spiral",
        tags = listOf("three interconnected spirals", "Celtic", "spiral pattern", "nature cycles", "feminine energy")
    ),
    Symbol(
        name = "Flaming Chalice",
        category = "Unitarian Universalism",
        description = "A symbol of religious freedom and the spirit of service.",
        history = "Created during WWII to represent safe passage for refugees and later adopted by the Unitarian Universalist Association.",
        url = "https://en.wikipedia.org/wiki/Flaming_chalice",
        tags = listOf("bowl or cup with flame", "Unitarian Universalism", "religious freedom", "service", "beacon of hope")
    ),
    Symbol(
        name = "Thor’s Hammer (Mjölnir)",
        category = "Norse Paganism",
        description = "A hammer symbol representing Thor, the Norse god of thunder and protection.",
        history = "Worn as an amulet for protection and strength in Viking-era Scandinavia.",
        url = "https://en.wikipedia.org/wiki/Mj%C3%B6lnir",
        tags = listOf("hammer shape", "Norse mythology", "Viking", "protection", "thunder god", "runic carvings")
    ),
    Symbol(
        name = "Hexagram",
        category = "Occult / Kabbalah / Christianity / Hinduism",
        description = "A six-pointed star symbol used in many religious traditions with different meanings.",
        history = "Found in Kabbalistic, Christian, Hindu, and occult symbolism, often misunderstood or confused with the Star of David.",
        url = "https://en.wikipedia.org/wiki/Hexagram",
        tags = listOf("two interlocked triangles", "six-pointed star", "magic", "Kabbalah", "spiritual balance", "esoteric")
    ),
    Symbol(
        name = "Cross of Lorraine",
        category = "Christianity",
        description = "A double-barred cross used as a symbol of the resurrection and resistance.",
        history = "Used in Christianity and by French resistance during WWII; associated with the patriarchal cross.",
        url = "https://en.wikipedia.org/wiki/Cross_of_Lorraine",
        tags = listOf("vertical line with two horizontal bars", "Christianity", "patriarchal cross", "resurrection", "French resistance")
    ),
    Symbol(
        name = "Coptic Cross",
        category = "Coptic Christianity",
        description = "A cross used by Coptic Christians in Egypt, typically with intricate design.",
        history = "Derived from early Christian and Egyptian motifs, symbolizing eternal life.",
        url = "https://en.wikipedia.org/wiki/Coptic_cross",
        tags = listOf("circle with cross inside", "Christianity", "Egyptian Christian symbol", "Coptic Church", "faith and resurrection")
    ),
    Symbol(
        name = "Eastern Orthodox Cross",
        category = "Eastern Orthodoxy",
        description = "A variation of the Christian cross with three bars, one slanted.",
        history = "Used in Orthodox Christian iconography, particularly in Eastern Europe and Russia.",
        url = "https://en.wikipedia.org/wiki/Eastern_Orthodox_cross",
        tags = listOf("cross with three bars", "Orthodox Christianity", "slanted bottom bar", "Russian Church", "resurrection")
    ),
    Symbol(
        name = "Taoist Bagua",
        category = "Taoism",
        description = "An octagonal symbol showing trigrams from the I Ching, representing cosmic balance.",
        history = "Used in Taoist cosmology and feng shui to describe fundamental principles of reality.",
        url = "https://en.wikipedia.org/wiki/Bagua",
        tags = listOf("eight trigrams around circle", "Taoism", "yin yang", "feng shui", "cosmic harmony", "I Ching")
    ),
    Symbol(
        name = "Hamsa Hand",
        category = "Islam / Judaism / Middle East",
        description = "A hand-shaped symbol believed to protect against the evil eye.",
        history = "Used across Middle Eastern cultures and religions, often featuring an eye in the palm.",
        url = "https://en.wikipedia.org/wiki/Hamsa",
        tags = listOf("open hand with eye", "protection", "evil eye", "Middle Eastern tradition", "amulet", "Judaism", "Islam")
    ),
    Symbol(
        name = "Tree of Life",
        category = "Various Religions",
        description = "A symbol of interconnectedness, growth, and divine order.",
        history = "Appears in Kabbalah, Norse mythology, Christianity, and more; represents creation and life.",
        url = "https://en.wikipedia.org/wiki/Tree_of_life",
        tags = listOf("tree with branches and roots", "interconnectedness", "spiritual growth", "divine order", "creation", "Kabbalah", "Norse")
    ),
    Symbol(
        name = "Islamic Calligraphy (Allah)",
        category = "Islam",
        description = "Arabic script representing the name of God, Allah.",
        history = "Used in Islamic art and iconography to avoid images while expressing devotion.",
        url = "https://en.wikipedia.org/wiki/Islamic_calligraphy",
        tags = listOf("Arabic word for God", "Islam", "calligraphy", "mosque decor", "sacred writing", "monotheism")
    ),
    Symbol(
        name = "Tetragrammaton (YHWH)",
        category = "Judaism",
        description = "The four-letter name of God in Hebrew, considered sacred and unspoken.",
        history = "Found in Jewish scriptures; represents the divine presence in Judaism.",
        url = "https://en.wikipedia.org/wiki/Tetragrammaton",
        tags = listOf("four Hebrew letters", "Judaism", "YHWH", "divine name", "Torah", "sacred writing", "ineffable name")
    ),
    Symbol(
        name = "Seal of Solomon",
        category = "Islam / Judaism / Occult",
        description = "A hexagram or star symbol attributed to King Solomon.",
        history = "Used in Islamic and Jewish mysticism and occult practices as a protective or magical emblem.",
        url = "https://en.wikipedia.org/wiki/Seal_of_Solomon",
        tags = listOf("interlocked triangles", "hexagram", "Jewish mysticism", "Islamic magic", "occult", "King Solomon")
    ),
    Symbol(
        name = "Anointing Horn",
        category = "Christianity",
        description = "A horn used to carry sacred oil for anointing kings or prophets in biblical traditions.",
        history = "Referenced in the Old Testament during the anointing of kings like Saul and David.",
        url = "https://en.wikipedia.org/wiki/Anointing",
        tags = listOf("curved horn shape", "Christianity", "Old Testament", "sacred oil", "prophet", "ceremony")
    ),
    Symbol(
        name = "Zia Sun Symbol",
        category = "Native American (Zia Pueblo)",
        description = "A sacred sun symbol representing the four cardinal directions, seasons, and sacred aspects.",
        history = "Used by the Zia people of New Mexico as a symbol of life’s harmony.",
        url = "https://en.wikipedia.org/wiki/Zia_people#Symbol",
        tags = listOf("circle with four groups of four rays", "Native American", "Zia", "sun", "directions", "balance")
    ),
    Symbol(
        name = "Ahimsa Hand",
        category = "Jainism",
        description = "A hand with a wheel on the palm symbolizing non-violence and truth.",
        history = "Core principle in Jain ethics, with the word ‘Ahimsa’ inscribed in Sanskrit.",
        url = "https://en.wikipedia.org/wiki/Ahimsa_in_Jainism",
        tags = listOf("open palm with wheel", "Jainism", "non-violence", "truth", "five fingers", "stop sign shape")
    ),
    Symbol(
        name = "Shinto Torii Gate",
        category = "Shinto",
        description = "A traditional gate marking the entrance to a sacred Shinto shrine.",
        history = "Symbolizes the transition from mundane to sacred; iconic in Japanese culture.",
        url = "https://en.wikipedia.org/wiki/Torii",
        tags = listOf("two vertical posts with curved top bar", "Shinto", "Japan", "shrine entrance", "sacred space")
    ),
    Symbol(
        name = "Serpent Mound Spiral",
        category = "Ancient North American",
        description = "A spiral-shaped mound representing a snake, symbolizing life and cycles.",
        history = "Constructed by Indigenous people of North America, possibly related to solstice events.",
        url = "https://en.wikipedia.org/wiki/Serpent_Mound",
        tags = listOf("curved snake shape", "Native American", "spiral pattern", "earthwork", "ancient symbol")
    ),
    Symbol(
        name = "Koi Fish Yin Yang",
        category = "Chinese Spirituality / Taoism",
        description = "A version of the Yin Yang symbol stylized with koi fish to represent harmony in motion.",
        history = "Modern adaptation blending Taoist symbolism and Chinese cultural motifs.",
        url = "https://en.wikipedia.org/wiki/Yin_and_Yang",
        tags = listOf("two swimming fish", "black and white", "balance", "motion", "Chinese spirituality", "duality")
    ),
    Symbol(
        name = "Phoenix",
        category = "Chinese / Greek Mythology",
        description = "A mythical bird symbolizing rebirth, immortality, and divine renewal.",
        history = "Appears in both Chinese (Fenghuang) and Greek traditions; rising from ashes.",
        url = "https://en.wikipedia.org/wiki/Phoenix_(mythology)",
        tags = listOf("bird rising in flames", "mythical creature", "rebirth", "immortality", "fire bird", "resurrection")
    ),
    Symbol(
        name = "Uraeus",
        category = "Ancient Egyptian Religion",
        description = "A stylized upright cobra used as a royal symbol of sovereignty and divine authority.",
        history = "Worn on pharaohs' crowns as a protective and authoritative emblem.",
        url = "https://en.wikipedia.org/wiki/Uraeus",
        tags = listOf("upright cobra", "serpent symbol", "ancient Egypt", "royal crown", "protection", "sovereignty")
    ),
    Symbol(
        name = "Antahkarana",
        category = "Tibetan Buddhism / Healing Traditions",
        description = "An ancient symbol used for meditation and spiritual healing.",
        history = "Said to activate healing and purification energies; used in Tibetan and esoteric schools.",
        url = "https://en.wikipedia.org/wiki/Antahkarana",
        tags = listOf("3D cube-like spiral", "healing symbol", "Buddhism", "Tibetan meditation", "geometric energy")
    ),
    Symbol(
        name = "Sun Cross",
        category = "Pre-Christian / Neo-Pagan",
        description = "A circle with a cross inside, symbolizing the solar calendar and nature cycles.",
        history = "One of the oldest religious symbols, used in Bronze Age Europe and revived in neo-paganism.",
        url = "https://en.wikipedia.org/wiki/Sun_cross",
        tags = listOf("circle with cross", "solar wheel", "pagan", "nature cycle", "prehistoric symbol", "seasonal calendar")
    ),
    Symbol(
        name = "Triquetra",
        category = "Celtic / Christian",
        description = "An interlaced three-pointed knot symbolizing the trinity or triple aspects of life.",
        history = "Celtic origins, later adopted in Christianity to represent the Holy Trinity.",
        url = "https://en.wikipedia.org/wiki/Triquetra",
        tags = listOf("three interwoven loops", "Celtic", "Christianity", "trinity", "eternal unity", "symbolic knot")
    ),
    Symbol(
        name = "Djed Pillar",
        category = "Ancient Egyptian Religion",
        description = "A pillar-like symbol representing stability and the spine of Osiris.",
        history = "Used in tombs and amulets to invoke divine support and resurrection.",
        url = "https://en.wikipedia.org/wiki/Djed",
        tags = listOf("column with horizontal lines", "Egyptian pillar", "Osiris", "stability", "resurrection", "spiritual spine")
    ),
    Symbol(
        name = "Chalice Well Symbol",
        category = "Glastonbury / Spiritualism",
        description = "Two interlocking circles forming a vesica piscis at the Glastonbury well.",
        history = "Associated with divine feminine, the Holy Grail, and healing.",
        url = "https://en.wikipedia.org/wiki/Chalice_Well",
        tags = listOf("two interlocking circles", "vesica piscis", "spiritual well", "sacred geometry", "feminine energy")
    ),
    Symbol(
        name = "Wiccan Pentacle",
        category = "Wicca / Neopaganism",
        description = "A pentagram enclosed in a circle symbolizing the five elements and unity.",
        history = "Common in modern witchcraft for protection and ritual purposes.",
        url = "https://en.wikipedia.org/wiki/Pentacle",
        tags = listOf("five-pointed star in circle", "Wicca", "pagan", "five elements", "ritual symbol", "protection")
    ),
    Symbol(
        name = "Crossed Keys",
        category = "Catholicism / Vatican",
        description = "A pair of crossed keys symbolizing the authority of the Pope and Heaven.",
        history = "Appears on the Papal coat of arms, representing the keys to the kingdom of heaven.",
        url = "https://en.wikipedia.org/wiki/Coat_of_arms_of_the_Vatican_City",
        tags = listOf("two keys crossed", "Catholic Church", "papacy", "Vatican", "authority", "heavenly access")
    ),
    Symbol(
        name = "Rainbow Serpent",
        category = "Australian Aboriginal Religion",
        description = "A powerful spirit and creator being often depicted as a rainbow-colored serpent.",
        history = "Central figure in Dreamtime stories representing water, creation, and life cycles.",
        url = "https://en.wikipedia.org/wiki/Rainbow_Serpent",
        tags = listOf("multi-colored snake", "Aboriginal mythology", "creation spirit", "Dreamtime", "water symbol", "serpent")
    ),
    Symbol(
        name = "Hand of Eris (Golden Apple)",
        category = "Discordianism",
        description = "A golden apple inscribed with the word 'Kallisti', symbol of chaos and paradox.",
        history = "Modern parody religion symbolizing disorder, humor, and subjective truth.",
        url = "https://en.wikipedia.org/wiki/Discordianism",
        tags = listOf("golden apple with writing", "chaos symbol", "Discordianism", "parody religion", "symbol of disorder")
    ),
    Symbol(
        name = "Pagan Horned God",
        category = "Neopaganism / Wicca",
        description = "A symbol representing the masculine divine, nature, and fertility.",
        history = "Revived in Wicca and modern paganism as counterpart to the Goddess.",
        url = "https://en.wikipedia.org/wiki/Horned_God",
        tags = listOf("human face with horns", "male divine", "pagan god", "nature symbol", "fertility", "wild spirit")
    ),
    Symbol(
        name = "Star and Crescent (Alternate Form)",
        category = "Islam",
        description = "A reversed or stylized version of the crescent moon with a star.",
        history = "Found on flags and mosques across the Islamic world; variation of the common Islamic emblem.",
        url = "https://en.wikipedia.org/wiki/Star_and_crescent",
        tags = listOf("crescent moon with star", "Islam", "mosque symbol", "flag icon", "spiritual identity", "divine guidance")
    ),
    Symbol( // Religious Symbols
        name = "Feather of Ma'at",
        category = "Ancient Egyptian Religion",
        description = "Represents truth, justice, and cosmic order in ancient Egyptian belief.",
        history = "Used in the weighing of the heart ceremony in Egyptian mythology.",
        url = "https://en.wikipedia.org/wiki/Maat",
        tags = listOf("single feather", "Egyptian goddess", "truth", "justice", "balance", "afterlife judgment")
    ),
    Symbol( // Math/Science Symbols
        name = "Plus",
        category = "Arithmetic",
        description = "Represents the addition operation between two numbers or expressions.",
        history = "Originated from Latin 'et' meaning 'and'; first used in the 15th century.",
        url = "https://en.wikipedia.org/wiki/Plus_and_minus_signs",
        tags = listOf("addition", "sum", "positive", "arithmetic", "operator", "cross symbol")
    ),
    Symbol(
        name = "Minus",
        category = "Arithmetic",
        description = "Denotes the subtraction operation or indicates a negative value.",
        history = "Derived from a tilde written over a letter 'm' in the 15th century.",
        url = "https://en.wikipedia.org/wiki/Plus_and_minus_signs",
        tags = listOf("subtraction", "negative", "difference", "arithmetic", "operator", "dash symbol")
    ),
    Symbol(
        name = "Multiplication",
        category = "Arithmetic",
        description = "Indicates the multiplication operation between two numbers or expressions.",
        history = "The '×' symbol was introduced by William Oughtred in 1631.",
        url = "https://en.wikipedia.org/wiki/Multiplication_sign",
        tags = listOf("multiplication", "product", "times", "arithmetic", "operator", "cross symbol")
    ),
    Symbol(
        name = "Division",
        category = "Arithmetic",
        description = "Represents the division operation, dividing one number by another.",
        history = "The '÷' symbol, known as the obelus, was first used by Johann Rahn in 1659.",
        url = "https://en.wikipedia.org/wiki/Division_sign",
        tags = listOf("division", "quotient", "divide", "arithmetic", "operator", "obelus symbol")
    ),
    Symbol(
        name = "Equal",
        category = "Comparison",
        description = "Indicates that two expressions represent the same value or are equivalent.",
        history = "Introduced by Robert Recorde in 1557 to avoid repetitive writing.",
        url = "https://en.wikipedia.org/wiki/Equals_sign",
        tags = listOf("equality", "equivalence", "balance", "comparison", "operator", "parallel lines")
    ),
    Symbol(
        name = "Not Equal",
        category = "Comparison",
        description = "Denotes that two expressions are not equal or equivalent.",
        history = "The '≠' symbol combines the equal sign with a slash; its origin is unclear.",
        url = "https://en.wikipedia.org/wiki/Inequality_(mathematics)",
        tags = listOf("inequality", "not equal", "difference", "comparison", "operator", "slashed equal")
    ),
    Symbol(
        name = "Less Than",
        category = "Comparison",
        description = "Indicates that the value on the left is smaller than the value on the right.",
        history = "Originated from the practice of writing 'l' for 'less' and 'g' for 'greater'.",
        url = "https://en.wikipedia.org/wiki/Inequality_(mathematics)",
        tags = listOf("inequality", "less than", "comparison", "operator", "angle bracket")
    ),
    Symbol(
        name = "Greater Than",
        category = "Comparison",
        description = "Indicates that the value on the left is larger than the value on the right.",
        history = "Developed alongside the 'less than' symbol for comparative expressions.",
        url = "https://en.wikipedia.org/wiki/Inequality_(mathematics)",
        tags = listOf("inequality", "greater than", "comparison", "operator", "angle bracket")
    ),
    Symbol(
        name = "Infinity",
        category = "Calculus",
        description = "Represents an unbounded quantity that is larger than any real number.",
        history = "The lemniscate symbol '∞' was introduced by John Wallis in 1655.",
        url = "https://en.wikipedia.org/wiki/Infinity_symbol",
        tags = listOf("infinity", "unbounded", "limitless", "calculus", "symbol", "lemniscate")
    ),
    Symbol(
        name = "Pi",
        category = "Geometry",
        description = "Represents the ratio of a circle's circumference to its diameter, approximately 3.14159.",
        history = "The Greek letter 'π' was first used to denote this ratio by William Jones in 1706.",
        url = "https://en.wikipedia.org/wiki/Pi",
        tags = listOf("pi", "circle", "ratio", "geometry", "constant", "Greek letter")
    ),
    Symbol(
        name = "Square Root",
        category = "Arithmetic",
        description = "Denotes a value that, when multiplied by itself, yields the original number.",
        history = "The radical symbol '√' has been in use since the 16th century.",
        url = "https://en.wikipedia.org/wiki/Square_root",
        tags = listOf("square root", "radical", "root", "arithmetic", "operator", "check mark")
    ),
    Symbol(
        name = "Integral",
        category = "Calculus",
        description = "Represents the integral in calculus, signifying the area under a curve.",
        history = "The elongated 'S' symbol was introduced by Gottfried Wilhelm Leibniz in 1675.",
        url = "https://en.wikipedia.org/wiki/Integral_symbol",
        tags = listOf("integral", "calculus", "area", "summation", "operator", "elongated S")
    ),
    Symbol(
        name = "Summation",
        category = "Mathematics",
        description = "Denotes the sum of a sequence of numbers or expressions.",
        history = "The uppercase Greek letter sigma 'Σ' has been used since the 18th century.",
        url = "https://en.wikipedia.org/wiki/Summation",
        tags = listOf("summation", "series", "addition", "mathematics", "operator", "Greek letter sigma")
    ),
    Symbol(
        name = "Delta",
        category = "Mathematics",
        description = "Represents change or difference in a variable.",
        history = "The uppercase Greek letter delta 'Δ' has been used in this context since the 19th century.",
        url = "https://en.wikipedia.org/wiki/Delta_(letter)",
        tags = listOf("delta", "change", "difference", "mathematics", "symbol", "Greek letter delta")
    ),
    Symbol(
        name = "Partial Derivative",
        category = "Calculus",
        description = "Denotes the partial derivative of a function with respect to one variable.",
        history = "The symbol '∂' was introduced by Adrien-Marie Legendre and popularized by Carl Jacobi.",
        url = "https://en.wikipedia.org/wiki/Partial_derivative",
        tags = listOf("curved d", "partial", "derivative", "multivariable calculus", "function slope")
    ),
    Symbol(
        name = "Nabla (Del Operator)",
        category = "Vector Calculus",
        description = "Represents gradient, divergence, or curl in vector calculus.",
        history = "First used by William Rowan Hamilton, resembling an inverted triangle ∇.",
        url = "https://en.wikipedia.org/wiki/Del",
        tags = listOf("inverted triangle", "gradient", "vector calculus", "nabla", "differential operator")
    ),
    Symbol(
        name = "Proportional To",
        category = "Algebra",
        description = "Indicates that one quantity is proportional to another.",
        history = "The symbol ∝ was introduced in the 18th century to describe direct proportionality.",
        url = "https://en.wikipedia.org/wiki/Proportionality_(mathematics)",
        tags = listOf("curved symbol", "proportional", "scaling", "direct relationship", "equation")
    ),
    Symbol(
        name = "Approximately Equal",
        category = "Comparison",
        description = "Used when two values are nearly, but not exactly, equal.",
        history = "Derived from squiggly tilde shapes; used widely in estimation.",
        url = "https://en.wikipedia.org/wiki/Approximation",
        tags = listOf("wavy equal lines", "estimation", "approximate", "close value", "math comparison")
    ),
    Symbol(
        name = "Logical And",
        category = "Logic",
        description = "Represents logical conjunction — true only if both operands are true.",
        history = "Used in formal logic and computer science as the ∧ symbol.",
        url = "https://en.wikipedia.org/wiki/Logical_conjunction",
        tags = listOf("upward angle", "and", "logic", "truth table", "boolean")
    ),
    Symbol(
        name = "Logical Or",
        category = "Logic",
        description = "Represents logical disjunction — true if either operand is true.",
        history = "The ∨ symbol is used in logic and computing.",
        url = "https://en.wikipedia.org/wiki/Logical_disjunction",
        tags = listOf("downward angle", "or", "logic", "truth table", "boolean")
    ),
    Symbol(
        name = "There Exists",
        category = "Logic",
        description = "Denotes the existence of at least one element that satisfies a condition.",
        history = "The ∃ symbol was adopted in formal mathematics in the 20th century.",
        url = "https://en.wikipedia.org/wiki/List_of_logic_symbols",
        tags = listOf("backwards E", "exists", "quantifier", "predicate logic", "mathematical logic")
    ),
    Symbol(
        name = "For All",
        category = "Logic",
        description = "Used to express that a statement applies to all elements in a set.",
        history = "The ∀ symbol was introduced by Gerhard Gentzen in modern logic.",
        url = "https://en.wikipedia.org/wiki/Universal_quantification",
        tags = listOf("upside down A", "forall", "logic", "quantifier", "universal statement")
    ),
    Symbol(
        name = "Element Of",
        category = "Set Theory",
        description = "Denotes membership in a set.",
        history = "The ∈ symbol has been used since the 19th century to denote element inclusion.",
        url = "https://en.wikipedia.org/wiki/Element_(mathematics)",
        tags = listOf("curved E", "set theory", "belongs to", "mathematical sets", "element")
    ),
    Symbol(
        name = "Subset",
        category = "Set Theory",
        description = "Indicates that all elements of one set are contained within another.",
        history = "Subset symbols such as ⊆ and ⊂ are essential in set theory.",
        url = "https://en.wikipedia.org/wiki/Subset",
        tags = listOf("sideways U", "set inclusion", "subset", "set theory", "containment")
    ),
    Symbol(
        name = "Union",
        category = "Set Theory",
        description = "Represents the union of two sets, meaning all elements that are in either set.",
        history = "The ∪ symbol has been used in set theory since the late 19th century.",
        url = "https://en.wikipedia.org/wiki/Union_(set_theory)",
        tags = listOf("U shape", "set theory", "union", "combination", "or logic", "mathematical sets")
    ),
    Symbol(
        name = "Intersection",
        category = "Set Theory",
        description = "Represents the intersection of two sets, meaning elements common to both.",
        history = "The ∩ symbol is used to denote shared elements between sets.",
        url = "https://en.wikipedia.org/wiki/Intersection_(set_theory)",
        tags = listOf("upside-down U", "set theory", "intersection", "common elements", "and logic")
    ),
    Symbol(
        name = "Angle",
        category = "Geometry",
        description = "Used to denote a geometric angle or angular measure.",
        history = "The ∠ symbol appears in Euclidean geometry and trigonometry.",
        url = "https://en.wikipedia.org/wiki/Angle",
        tags = listOf("angle symbol", "geometry", "degrees", "measurement", "corner", "triangle")
    ),
    Symbol(
        name = "Perpendicular",
        category = "Geometry",
        description = "Denotes two lines that intersect at a 90-degree angle.",
        history = "The ⊥ symbol is used in geometry, physics, and logic.",
        url = "https://en.wikipedia.org/wiki/Perpendicular",
        tags = listOf("upside down T", "geometry", "right angle", "orthogonal", "intersection")
    ),
    Symbol(
        name = "Parallel",
        category = "Geometry",
        description = "Indicates that two lines are equidistant and never intersect.",
        history = "The ∥ symbol appears in classical geometry proofs and modern notation.",
        url = "https://en.wikipedia.org/wiki/Parallel_(geometry)",
        tags = listOf("double vertical lines", "parallel lines", "geometry", "distance", "never touching")
    ),
    Symbol(
        name = "Modulus",
        category = "Algebra",
        description = "Represents the absolute value of a number or distance from zero.",
        history = "Vertical bars |a| have been used since the 19th century for absolute value.",
        url = "https://en.wikipedia.org/wiki/Absolute_value",
        tags = listOf("vertical bars", "absolute value", "modulus", "positive distance", "zero reference")
    ),
    Symbol(
        name = "Factorial",
        category = "Arithmetic",
        description = "Represents the product of all positive integers less than or equal to n.",
        history = "The exclamation mark n! was first used in the 19th century.",
        url = "https://en.wikipedia.org/wiki/Factorial",
        tags = listOf("exclamation mark", "factorial", "product", "permutation", "combinatorics", "counting")
    ),
    Symbol(
        name = "Tilde",
        category = "Mathematics",
        description = "Used to indicate similarity, approximation, or proportionality depending on context.",
        history = "The tilde ~ has multiple meanings in logic, math, and programming.",
        url = "https://en.wikipedia.org/wiki/Tilde",
        tags = listOf("squiggly line", "similarity", "approximation", "math notation", "soft equivalence")
    ),
    Symbol(
        name = "Lambda",
        category = "Mathematics / Physics / CS",
        description = "Represents wavelength in physics, eigenvalues in linear algebra, and functions in programming.",
        history = "The Greek letter λ is heavily used across scientific disciplines.",
        url = "https://en.wikipedia.org/wiki/Lambda",
        tags = listOf("lambda", "Greek letter", "wavelength", "eigenvalue", "lambda function", "physics", "computer science")
    ),
    Symbol(
        name = "Planck Constant",
        category = "Physics",
        description = "A fundamental constant used in quantum mechanics, denoted by ℏ (h-bar).",
        history = "Named after Max Planck, the constant relates energy and frequency of particles.",
        url = "https://en.wikipedia.org/wiki/Planck_constant",
        tags = listOf("h-bar", "quantum mechanics", "Planck", "energy", "frequency", "physics constant")
    ),
    Symbol(
        name = "Euler's Number",
        category = "Mathematics",
        description = "The mathematical constant e ≈ 2.718, base of the natural logarithm.",
        history = "Named after Leonhard Euler; fundamental in calculus and exponential growth.",
        url = "https://en.wikipedia.org/wiki/E_(mathematical_constant)",
        tags = listOf("e", "Euler", "natural log base", "exponential growth", "calculus", "constant")
    ),
    Symbol(
        name = "Natural Logarithm",
        category = "Mathematics",
        description = "Logarithm with base e, denoted as ln(x).",
        history = "Common in natural sciences and mathematics, especially in exponential decay/growth.",
        url = "https://en.wikipedia.org/wiki/Natural_logarithm",
        tags = listOf("ln", "log base e", "logarithm", "natural growth", "exponential function")
    ),
    Symbol(
        name = "Probability",
        category = "Statistics",
        description = "Denotes the probability of an event occurring.",
        history = "The capital letter P is standard notation in probability theory.",
        url = "https://en.wikipedia.org/wiki/Probability",
        tags = listOf("P", "chance", "likelihood", "statistics", "event", "outcome")
    ),
    Symbol(
        name = "Sigma (Standard Deviation)",
        category = "Statistics",
        description = "Lowercase Greek sigma (σ) represents standard deviation.",
        history = "Used in statistics to describe variation or dispersion of a dataset.",
        url = "https://en.wikipedia.org/wiki/Standard_deviation",
        tags = listOf("sigma", "σ", "variation", "spread", "statistics", "data dispersion")
    ),
    Symbol(
        name = "Mu (Mean)",
        category = "Statistics",
        description = "Greek letter μ represents the mean (average) of a population.",
        history = "Standard statistical notation used across fields.",
        url = "https://en.wikipedia.org/wiki/Mean",
        tags = listOf("mu", "μ", "mean", "average", "expected value", "statistics")
    ),
    Symbol(
        name = "Phi",
        category = "Mathematics",
        description = "The golden ratio constant φ ≈ 1.618, often found in nature and art.",
        history = "Studied since antiquity and popularized in the Renaissance.",
        url = "https://en.wikipedia.org/wiki/Golden_ratio",
        tags = listOf("phi", "φ", "golden ratio", "divine proportion", "geometry", "nature pattern")
    ),
    Symbol(
        name = "Percent",
        category = "Arithmetic",
        description = "Represents a part per hundred, often used in finance and data.",
        history = "First used in the 15th century from the Latin ‘per centum’.",
        url = "https://en.wikipedia.org/wiki/Percent",
        tags = listOf("percent", "%", "percentage", "ratio", "data", "finance")
    ),
    Symbol(
        name = "Degree Symbol",
        category = "Measurement",
        description = "Indicates degrees in temperature or angle.",
        history = "Originates from Babylonian astronomy and trigonometry.",
        url = "https://en.wikipedia.org/wiki/Degree_symbol",
        tags = listOf("°", "temperature", "angle", "degrees", "measurement")
    ),
    Symbol(
        name = "Ohm",
        category = "Physics / Electrical Engineering",
        description = "Greek letter Ω, representing electrical resistance.",
        history = "Named after German physicist Georg Ohm.",
        url = "https://en.wikipedia.org/wiki/Ohm",
        tags = listOf("omega", "Ω", "resistance", "electrical unit", "circuit", "physics")
    ),
    Symbol(
        name = "Ampersand",
        category = "Logic / Programming",
        description = "Represents logical AND in programming and text.",
        history = "From Latin ‘et’ meaning ‘and’; evolved into its own symbol.",
        url = "https://en.wikipedia.org/wiki/Ampersand",
        tags = listOf("&", "and", "programming", "logic", "syntax", "symbol")
    ),
    Symbol(
        name = "Pipe",
        category = "Logic / Programming / Math",
        description = "Used in programming as a logical OR and in math as a conditional.",
        history = "Derived from Unix pipes and logic notation.",
        url = "https://en.wikipedia.org/wiki/Vertical_bar",
        tags = listOf("|", "pipe", "or", "separator", "condition", "logic", "syntax")
    ),
    Symbol(
        name = "Backslash",
        category = "Programming / Syntax",
        description = "Used in programming for escaping characters or as a directory separator.",
        history = "Popularized by early computing systems like MS-DOS and C.",
        url = "https://en.wikipedia.org/wiki/Backslash",
        tags = listOf("\\", "escape", "programming", "syntax", "directory", "symbol")
    ),
    Symbol(
        name = "Alpha",
        category = "Mathematics / Physics",
        description = "Greek letter α used for angles, significance level, or coefficients.",
        history = "Used since ancient Greece and adapted in scientific formulas.",
        url = "https://en.wikipedia.org/wiki/Alpha",
        tags = listOf("alpha", "α", "angle", "significance", "coefficient", "symbol")
    ),
    Symbol(
        name = "Beta",
        category = "Mathematics / Finance",
        description = "Greek letter β used for variables, beta coefficients, or risk in finance.",
        history = "Appears in beta distributions and CAPM in financial theory.",
        url = "https://en.wikipedia.org/wiki/Beta",
        tags = listOf("beta", "β", "variable", "finance", "risk", "statistics")
    ),
    Symbol(
        name = "Gamma",
        category = "Physics / Math",
        description = "Used for gamma rays, gamma functions, and decay constants.",
        history = "The Greek letter γ has broad uses across physics and engineering.",
        url = "https://en.wikipedia.org/wiki/Gamma",
        tags = listOf("gamma", "γ", "decay", "radiation", "math function", "physics")
    ),
    Symbol(
        name = "Imaginary Unit",
        category = "Complex Numbers",
        description = "The unit i represents the square root of -1 in complex number theory.",
        history = "First discussed by Euler and popularized by Gauss in the 18th century.",
        url = "https://en.wikipedia.org/wiki/Imaginary_number",
        tags = listOf("i", "imaginary", "complex numbers", "square root of negative", "mathematics")
    ),
    Symbol(
        name = "Real Part",
        category = "Complex Numbers",
        description = "Re(x) denotes the real part of a complex number.",
        history = "Used in fields like signal processing, electrical engineering, and math.",
        url = "https://en.wikipedia.org/wiki/Complex_number",
        tags = listOf("Re(x)", "real", "complex numbers", "math", "signal", "number components")
    ),
    Symbol(
        name = "Imaginary Part",
        category = "Complex Numbers",
        description = "Im(x) denotes the imaginary part of a complex number.",
        history = "Paired with Re(x), it's used to extract components of complex values.",
        url = "https://en.wikipedia.org/wiki/Complex_number",
        tags = listOf("Im(x)", "imaginary", "complex component", "math", "engineering")
    ),
    Symbol(
        name = "Logarithm",
        category = "Mathematics",
        description = "Represents a logarithmic function; log base 10 by default, or other specified bases.",
        history = "Introduced by John Napier in the early 17th century.",
        url = "https://en.wikipedia.org/wiki/Logarithm",
        tags = listOf("log", "logarithm", "base 10", "exponents", "inverse", "math function")
    ),
    Symbol(
        name = "Gradient",
        category = "Vector Calculus",
        description = "The vector derivative of a scalar field; shows direction of steepest increase.",
        history = "Notated as ∇f and used in physics, engineering, and multivariable calculus.",
        url = "https://en.wikipedia.org/wiki/Gradient",
        tags = listOf("nabla", "vector field", "slope", "direction", "gradient", "multivariable")
    ),
    Symbol(
        name = "Curl",
        category = "Vector Calculus",
        description = "Measures the rotation of a vector field, written as ∇×F.",
        history = "Key operator in electromagnetism and fluid dynamics.",
        url = "https://en.wikipedia.org/wiki/Curl_(mathematics)",
        tags = listOf("nabla cross", "vector field", "rotation", "fluid", "electromagnetism", "curl")
    ),
    Symbol(
        name = "Divergence",
        category = "Vector Calculus",
        description = "Measures the magnitude of a source or sink at a point in a vector field (∇·F).",
        history = "Found in Gauss's Theorem, fluid dynamics, and Maxwell's equations.",
        url = "https://en.wikipedia.org/wiki/Divergence",
        tags = listOf("nabla dot", "spread", "vector field", "source", "math operator", "divergence")
    ),
    Symbol(
        name = "Tensor",
        category = "Physics / Linear Algebra",
        description = "A generalization of scalars, vectors, and matrices used in physics and relativity.",
        history = "Widely used in Einstein’s theory of general relativity.",
        url = "https://en.wikipedia.org/wiki/Tensor",
        tags = listOf("tensor", "multi-dimensional array", "general relativity", "linear algebra", "mathematical object")
    ),
    Symbol(
        name = "Unit Vector",
        category = "Linear Algebra",
        description = "A vector with magnitude 1, used to denote direction only.",
        history = "Fundamental in coordinate systems and vector calculus.",
        url = "https://en.wikipedia.org/wiki/Unit_vector",
        tags = listOf("hat notation", "vector", "unit length", "direction", "1 magnitude")
    ),
    Symbol(
        name = "Dot Product",
        category = "Linear Algebra",
        description = "An operation that returns a scalar from two vectors, written as A · B.",
        history = "Introduced in vector math to measure projection or angle between vectors.",
        url = "https://en.wikipedia.org/wiki/Dot_product",
        tags = listOf("dot", "scalar product", "projection", "vectors", "angle", "inner product")
    ),
    Symbol(
        name = "Cross Product",
        category = "Linear Algebra",
        description = "Returns a vector perpendicular to two input vectors, written A × B.",
        history = "Common in physics and 3D geometry for torque, rotation, and area.",
        url = "https://en.wikipedia.org/wiki/Cross_product",
        tags = listOf("cross", "vector product", "right hand rule", "3D", "perpendicular", "math")
    ),
    Symbol(
        name = "Identity Matrix",
        category = "Linear Algebra",
        description = "A square matrix with 1s on the diagonal and 0s elsewhere; acts as a multiplicative identity.",
        history = "A foundation of matrix operations and linear transformations.",
        url = "https://en.wikipedia.org/wiki/Identity_matrix",
        tags = listOf("diagonal 1s", "matrix", "linear algebra", "identity", "multiplicative neutral")
    ),
    Symbol(
        name = "Matrix Determinant",
        category = "Linear Algebra",
        description = "A scalar value that can be computed from a square matrix and used to solve systems.",
        history = "Used to determine invertibility, volume, and matrix properties.",
        url = "https://en.wikipedia.org/wiki/Determinant",
        tags = listOf("det(A)", "scalar", "volume", "invertibility", "matrix value")
    ),
    Symbol(
        name = "Transpose",
        category = "Linear Algebra",
        description = "The transpose of a matrix flips it over its diagonal, denoted Aᵀ.",
        history = "Used in systems of equations, inner products, and transformations.",
        url = "https://en.wikipedia.org/wiki/Transpose",
        tags = listOf("matrix flip", "transpose", "T superscript", "row column switch", "matrix operation")
    ),
    Symbol(
        name = "Epsilon",
        category = "Mathematics / Calculus",
        description = "Greek letter ε used to denote small quantities, limits, or errors.",
        history = "Fundamental in ε-δ definitions of limits and calculus proofs.",
        url = "https://en.wikipedia.org/wiki/Epsilon",
        tags = listOf("epsilon", "ε", "small value", "limit", "error bound", "calculus", "Greek letter")
    ),
    Symbol(
        name = "Chi-Square",
        category = "Statistics",
        description = "Used to test the independence of two events or goodness-of-fit in categorical data.",
        history = "Introduced by Karl Pearson in 1900 for statistical hypothesis testing.",
        url = "https://en.wikipedia.org/wiki/Chi-squared_distribution",
        tags = listOf("χ²", "chi square", "hypothesis test", "categorical", "independence", "statistics", "Greek letter")
    ),
    Symbol(
        name = "Z-score",
        category = "Statistics",
        description = "Measures how many standard deviations a value is from the mean.",
        history = "Widely used in normal distribution analysis and standardization.",
        url = "https://en.wikipedia.org/wiki/Standard_score",
        tags = listOf("Z", "z-score", "standardized", "deviation", "statistics", "normal distribution")
    ),
    Symbol(
        name = "Variance",
        category = "Statistics",
        description = "Measures how far a set of numbers is spread out from the mean.",
        history = "A foundational concept in probability and statistical theory.",
        url = "https://en.wikipedia.org/wiki/Variance",
        tags = listOf("σ²", "variance", "spread", "dispersion", "statistics", "mean square")
    ),
    Symbol(
        name = "Covariance",
        category = "Statistics",
        description = "Indicates the directional relationship between two variables.",
        history = "Used in probability and statistics to measure linear relationships.",
        url = "https://en.wikipedia.org/wiki/Covariance",
        tags = listOf("cov", "covariance", "joint variability", "correlation", "statistics")
    ),
    Symbol(
        name = "Correlation Coefficient",
        category = "Statistics",
        description = "Measures strength and direction of a linear relationship between two variables.",
        history = "Commonly represented as r in Pearson correlation.",
        url = "https://en.wikipedia.org/wiki/Pearson_correlation_coefficient",
        tags = listOf("r", "correlation", "statistics", "relationship", "strength", "scatter plot")
    ),
    Symbol(
        name = "Delta (Lowercase)",
        category = "Math / Change",
        description = "Represents a small change in a variable; distinct from uppercase Δ.",
        history = "Common in calculus and physics for infinitesimal values.",
        url = "https://en.wikipedia.org/wiki/Delta_(letter)",
        tags = listOf("δ", "delta", "small change", "variation", "calculus", "Greek letter")
    ),
    Symbol(
        name = "N (Population Size)",
        category = "Statistics",
        description = "Denotes the total number of elements in a population.",
        history = "Used in formulas like variance and mean for population-level calculations.",
        url = "https://en.wikipedia.org/wiki/Population_(statistics)",
        tags = listOf("N", "population", "size", "statistics", "total", "count")
    ),
    Symbol(
        name = "n (Sample Size)",
        category = "Statistics",
        description = "Denotes the number of observations in a sample.",
        history = "Used to distinguish sample-based calculations from population-based.",
        url = "https://en.wikipedia.org/wiki/Sample_(statistics)",
        tags = listOf("n", "sample size", "statistics", "subset", "observations", "data set")
    ),
    Symbol(
        name = "Boolean True",
        category = "Logic / Programming",
        description = "Represents the truth value ‘true’ in logic and Boolean algebra.",
        history = "Named after George Boole; used across all programming languages.",
        url = "https://en.wikipedia.org/wiki/Boolean_data_type",
        tags = listOf("true", "logic", "boolean", "binary", "1", "if condition", "programming")
    ),
    Symbol(
        name = "Boolean False",
        category = "Logic / Programming",
        description = "Represents the truth value ‘false’ in Boolean logic.",
        history = "Logical complement of true; essential in conditionals.",
        url = "https://en.wikipedia.org/wiki/Boolean_data_type",
        tags = listOf("false", "logic", "boolean", "binary", "0", "not true", "programming")
    ),
    Symbol(
        name = "Nucleus Symbol",
        category = "Chemistry / Physics",
        description = "Depicts atomic structure in scientific diagrams.",
        history = "Visual abstraction of protons and neutrons inside an atom’s core.",
        url = "https://en.wikipedia.org/wiki/Nucleus",
        tags = listOf("central circle with dots", "atom core", "protons", "neutrons", "chemistry", "physics")
    ),
    Symbol(
        name = "Electron Shell",
        category = "Chemistry / Physics",
        description = "Circles or rings representing electron orbitals around the nucleus.",
        history = "Used in Bohr model and chemistry diagrams to depict energy levels.",
        url = "https://en.wikipedia.org/wiki/Electron_shell",
        tags = listOf("circles around nucleus", "electron levels", "orbit", "energy shell", "chemistry")
    ),
    Symbol(
        name = "Atomic Number",
        category = "Chemistry",
        description = "Indicates the number of protons in the nucleus of an atom.",
        history = "Defined by Moseley’s law and Mendeleev’s periodic system.",
        url = "https://en.wikipedia.org/wiki/Atomic_number",
        tags = listOf("Z", "atomic number", "protons", "element identity", "periodic table")
    ),
    Symbol(
        name = "Avogadro's Number",
        category = "Chemistry / Physics",
        description = "Represents the number of particles in one mole: 6.022×10²³.",
        history = "Named after Amedeo Avogadro and used in molecular science.",
        url = "https://en.wikipedia.org/wiki/Avogadro_constant",
        tags = listOf("Nₐ", "Avogadro", "mole", "particles", "constant", "chemistry", "molecules")
    ),
    Symbol(
        name = "Molar Mass Symbol",
        category = "Chemistry",
        description = "Denoted as M, represents mass per mole of substance.",
        history = "Used with Avogadro's constant in stoichiometry.",
        url = "https://en.wikipedia.org/wiki/Molar_mass",
        tags = listOf("M", "molar mass", "grams per mole", "chemistry", "stoichiometry", "mass")
    ),
    Symbol(
        name = "pH Symbol",
        category = "Chemistry",
        description = "Represents the acidity or alkalinity of a solution on a scale of 0 to 14.",
        history = "Introduced by Søren Sørensen in 1909 to measure hydrogen ion concentration.",
        url = "https://en.wikipedia.org/wiki/PH",
        tags = listOf("pH", "acidity", "alkalinity", "hydrogen ions", "chemistry", "solution scale")
    ),
    Symbol(
        name = "Arrow (Chemical Reaction)",
        category = "Chemistry",
        description = "Indicates the direction of a chemical reaction.",
        history = "Standard symbol in reaction equations to separate reactants from products.",
        url = "https://en.wikipedia.org/wiki/Chemical_equation",
        tags = listOf("arrow", "reaction", "yield", "chemical process", "reactants", "products")
    ),
    Symbol(
        name = "Equilibrium Arrows",
        category = "Chemistry",
        description = "Two half-arrows indicating reversible chemical reactions.",
        history = "Used to show dynamic equilibrium where forward and reverse reactions occur.",
        url = "https://en.wikipedia.org/wiki/Chemical_equilibrium",
        tags = listOf("double arrow", "equilibrium", "reversible reaction", "chemistry", "dynamic balance")
    ),
    Symbol(
        name = "Degree Celsius",
        category = "Physics / Measurement",
        description = "Measures temperature on the Celsius scale (°C).",
        history = "Named after Anders Celsius, part of the metric system.",
        url = "https://en.wikipedia.org/wiki/Celsius",
        tags = listOf("°C", "temperature", "metric", "heat", "physics", "measurement")
    ),
    Symbol(
        name = "Kelvin",
        category = "Physics / Thermodynamics",
        description = "Absolute thermodynamic temperature scale (K).",
        history = "Introduced by Lord Kelvin to measure absolute temperature starting from 0 K.",
        url = "https://en.wikipedia.org/wiki/Kelvin",
        tags = listOf("K", "temperature", "absolute zero", "physics", "scientific units")
    ),
    Symbol(
        name = "Radian",
        category = "Math / Measurement",
        description = "Angle unit defined as the ratio of arc length to radius.",
        history = "The SI derived unit for angles, key in trigonometry and calculus.",
        url = "https://en.wikipedia.org/wiki/Radian",
        tags = listOf("rad", "angle", "unit circle", "trigonometry", "geometry", "SI unit")
    ),
    Symbol(
        name = "Tau",
        category = "Math / Trigonometry",
        description = "Represents a circle constant equal to 2π.",
        history = "Used as an alternative to pi for full circular rotation (τ = 6.283...).",
        url = "https://en.wikipedia.org/wiki/Tau_(2%CF%80)",
        tags = listOf("tau", "τ", "circle constant", "2π", "rotation", "math")
    ),
    Symbol(
        name = "Planck Length",
        category = "Physics / Cosmology",
        description = "Theoretical smallest measurable length in quantum mechanics.",
        history = "Defined as a natural unit in Planck's system of universal constants.",
        url = "https://en.wikipedia.org/wiki/Planck_units",
        tags = listOf("Planck length", "smallest unit", "quantum scale", "cosmology", "physics")
    ),
    Symbol(
        name = "Light Speed Constant",
        category = "Physics",
        description = "Denoted as c, representing the speed of light in a vacuum (≈ 299,792,458 m/s).",
        history = "Fundamental in Einstein's theory of relativity.",
        url = "https://en.wikipedia.org/wiki/Speed_of_light",
        tags = listOf("c", "speed of light", "constant", "Einstein", "vacuum", "relativity")
    ),
    Symbol(
        name = "Gravitational Constant",
        category = "Physics / Gravity",
        description = "Denoted as G, appears in Newton’s law of universal gravitation.",
        history = "Determines the strength of gravitational force between two masses.",
        url = "https://en.wikipedia.org/wiki/Gravitational_constant",
        tags = listOf("G", "gravity", "universal constant", "physics", "Newton", "force")
    ),
    Symbol(
        name = "Boltzmann Constant",
        category = "Physics / Thermodynamics",
        description = "Links temperature with energy at the molecular level (k).",
        history = "Fundamental constant in statistical mechanics.",
        url = "https://en.wikipedia.org/wiki/Boltzmann_constant",
        tags = listOf("k", "energy per temperature", "thermodynamics", "molecules", "constant")
    ),
    Symbol(
        name = "H-bar (Reduced Planck Constant)",
        category = "Physics / Quantum Mechanics",
        description = "Denoted as ℏ, h/2π, and appears in the Schrödinger equation.",
        history = "Simplifies quantum equations by reducing Planck’s constant.",
        url = "https://en.wikipedia.org/wiki/Reduced_Planck_constant",
        tags = listOf("ℏ", "h-bar", "quantum", "Planck constant", "reduced", "physics")
    ),
    Symbol(
        name = "Permittivity of Free Space",
        category = "Physics / Electromagnetism",
        description = "Denoted ε₀, it measures a material's ability to permit electric field lines.",
        history = "Appears in Coulomb’s law and Maxwell’s equations.",
        url = "https://en.wikipedia.org/wiki/Vacuum_permittivity",
        tags = listOf("ε₀", "permittivity", "vacuum", "electric field", "electromagnetism", "constant")
    ),
    Symbol(
        name = "Permeability of Free Space",
        category = "Physics / Electromagnetism",
        description = "Denoted μ₀, it represents magnetic permeability in vacuum.",
        history = "Used in magnetic field equations and Maxwell's equations.",
        url = "https://en.wikipedia.org/wiki/Vacuum_permeability",
        tags = listOf("μ₀", "permeability", "vacuum", "magnetism", "physics constant")
    ),
    Symbol(
        name = "Atomic Mass Unit",
        category = "Chemistry / Physics",
        description = "A unit of mass used to express atomic and molecular weights (amu or u).",
        history = "Defined as one twelfth of the mass of a carbon-12 atom.",
        url = "https://en.wikipedia.org/wiki/Unified_atomic_mass_unit",
        tags = listOf("amu", "u", "atomic mass", "isotopes", "chemistry", "molecular weight")
    ),
    Symbol(
        name = "Sigma Bond Symbol",
        category = "Chemistry",
        description = "Represents a σ bond, the strongest type of covalent bond.",
        history = "Fundamental in molecular orbital theory.",
        url = "https://en.wikipedia.org/wiki/Sigma_bond",
        tags = listOf("σ bond", "covalent", "overlap", "molecular bonding", "chemistry", "sigma")
    ),
    Symbol(
        name = "Pi Bond Symbol",
        category = "Chemistry",
        description = "Represents a π bond, formed from sideways orbital overlap.",
        history = "Weaker than sigma bonds, found in double/triple bonds.",
        url = "https://en.wikipedia.org/wiki/Pi_bond",
        tags = listOf("π bond", "covalent", "double bond", "triple bond", "chemistry", "pi overlap")
    ),
    Symbol(
        name = "Neutron Symbol",
        category = "Physics / Chemistry",
        description = "A neutral subatomic particle found in the nucleus, symbol n⁰.",
        history = "Discovered by James Chadwick in 1932.",
        url = "https://en.wikipedia.org/wiki/Neutron",
        tags = listOf("n⁰", "neutron", "no charge", "nucleus", "atom", "particle physics")
    ),
    Symbol(
        name = "Proton Symbol",
        category = "Physics / Chemistry",
        description = "A positively charged subatomic particle, symbol p⁺.",
        history = "Discovered by Rutherford, defines atomic number.",
        url = "https://en.wikipedia.org/wiki/Proton",
        tags = listOf("p⁺", "proton", "positive charge", "nucleus", "atomic number", "subatomic")
    ),
    Symbol( // Math/Science Symbols
        name = "Electron Symbol",
        category = "Physics / Chemistry",
        description = "A negatively charged subatomic particle, symbol e⁻.",
        history = "Discovered by J.J. Thomson in 1897, central to electricity and bonding.",
        url = "https://en.wikipedia.org/wiki/Electron",
        tags = listOf("e⁻", "electron", "negative charge", "atom", "electricity", "subatomic")
    ),
    Symbol( // Safety/Warning Symbols
        name = "General Warning",
        category = "Safety / Hazard",
        description = "Indicates a general hazard that requires caution.",
        history = "Defined in ISO 7010 as a triangle with an exclamation point.",
        url = "https://en.wikipedia.org/wiki/Safety_sign",
        tags = listOf("yellow triangle", "exclamation mark", "caution", "hazard", "general danger", "warning sign")
    ),
    Symbol(
        name = "Toxic Material",
        category = "Chemical Hazard",
        description = "Warns of substances that can cause death or serious injury if inhaled, swallowed, or absorbed.",
        history = "Typically shown as a skull and crossbones inside a triangle.",
        url = "https://en.wikipedia.org/wiki/Hazard_symbol",
        tags = listOf("skull and crossbones", "toxicity", "poison", "chemical danger", "ISO 7010", "triangle symbol")
    ),
    Symbol(
        name = "Corrosive Substance",
        category = "Chemical Hazard",
        description = "Indicates materials that can cause skin burns or eye damage and corrode metals.",
        history = "ISO 7010 symbol features test tubes spilling liquid onto hand and surface.",
        url = "https://en.wikipedia.org/wiki/Hazard_symbol",
        tags = listOf("corrosive liquid", "hand burn", "test tube", "chemical hazard", "triangle", "safety")
    ),
    Symbol(
        name = "Flammable Material",
        category = "Fire Hazard",
        description = "Indicates substances that can easily catch fire.",
        history = "Often shown as a flame inside a triangle or diamond.",
        url = "https://en.wikipedia.org/wiki/Flammable",
        tags = listOf("flame", "fire", "combustible", "triangle", "warning", "flammable material")
    ),
    Symbol(
        name = "Explosive Material",
        category = "Fire / Blast Hazard",
        description = "Warns of substances that may explode under certain conditions.",
        history = "ISO symbol shows an explosion blast within a triangle.",
        url = "https://en.wikipedia.org/wiki/Explosive_material",
        tags = listOf("explosion", "blasting", "shockwave", "triangle symbol", "explosive", "hazard")
    ),
    Symbol(
        name = "High Voltage",
        category = "Electrical Hazard",
        description = "Warns of the presence of dangerous high-voltage electrical currents.",
        history = "ISO sign is a lightning bolt inside a yellow triangle.",
        url = "https://en.wikipedia.org/wiki/High_voltage",
        tags = listOf("lightning bolt", "electricity", "shock hazard", "triangle", "electrocution", "voltage warning")
    ),
    Symbol(
        name = "Laser Radiation",
        category = "Radiation Hazard",
        description = "Warns of potentially harmful laser exposure.",
        history = "Depicted as a beam with radiating rays; ISO 7010 standardized.",
        url = "https://en.wikipedia.org/wiki/Laser_safety",
        tags = listOf("laser beam", "radiation", "eyes", "beam", "triangle", "hazard sign")
    ),
    Symbol(
        name = "Radiation",
        category = "Radiation Hazard",
        description = "Warns of ionizing radiation (e.g. nuclear materials).",
        history = "Trefoil radiation symbol has been standardized since the 1940s.",
        url = "https://en.wikipedia.org/wiki/Radioactive_sign",
        tags = listOf("trefoil", "radioactive", "nuclear", "radiation", "triangle", "warning")
    ),
    Symbol(
        name = "Biohazard",
        category = "Biological Hazard",
        description = "Indicates biological materials that may be dangerous to living organisms.",
        history = "Developed in 1966 by Dow Chemical and adopted internationally.",
        url = "https://en.wikipedia.org/wiki/Biohazard",
        tags = listOf("three crescents", "biohazard", "virus", "pathogen", "bio safety", "hazard sign")
    ),
    Symbol(
        name = "No Entry",
        category = "Prohibition",
        description = "Indicates that entry to the area is forbidden.",
        history = "Standard red circle with white horizontal bar (ISO, traffic, safety).",
        url = "https://en.wikipedia.org/wiki/Prohibition_sign",
        tags = listOf("red circle", "horizontal bar", "do not enter", "no access", "forbidden", "entry restriction")
    ),
    Symbol(
        name = "Wear Eye Protection",
        category = "Mandatory PPE",
        description = "Indicates that protective eyewear must be worn in the area.",
        history = "Blue circular ISO symbol featuring safety goggles.",
        url = "https://en.wikipedia.org/wiki/Safety_sign",
        tags = listOf("eye protection", "goggles", "mandatory", "blue circle", "ISO", "personal protective equipment")
    ),
    Symbol(
        name = "Wear Hard Hat",
        category = "Mandatory PPE",
        description = "Indicates a hard hat must be worn to protect against falling objects.",
        history = "ISO standard blue circle with helmet icon.",
        url = "https://en.wikipedia.org/wiki/Hard_hat",
        tags = listOf("hard hat", "helmet", "head protection", "blue circle", "construction", "safety gear")
    ),
    Symbol(
        name = "Emergency Exit",
        category = "Safety / Evacuation",
        description = "Identifies the path to an emergency exit or escape route.",
        history = "Standard green ISO icon with running figure and door.",
        url = "https://en.wikipedia.org/wiki/Emergency_exit",
        tags = listOf("running figure", "exit", "escape route", "green sign", "emergency", "ISO 7010")
    ),
    Symbol(
        name = "Fire Extinguisher",
        category = "Fire Equipment",
        description = "Marks the location of fire extinguishing equipment.",
        history = "Typically a red square with extinguisher icon, standardized by ISO.",
        url = "https://en.wikipedia.org/wiki/Fire_extinguisher",
        tags = listOf("extinguisher", "fire", "red symbol", "safety equipment", "ISO 7010", "emergency")
    ),
    Symbol(
        name = "Slippery Surface",
        category = "Physical Hazard",
        description = "Warns of a floor or surface where slipping is likely.",
        history = "Yellow triangle with a figure slipping backward.",
        url = "https://en.wikipedia.org/wiki/Slip_and_fall",
        tags = listOf("slip hazard", "falling figure", "wet floor", "triangle", "caution", "physical hazard")
    ),
    Symbol(
        name = "Gas Under Pressure",
        category = "GHS Hazard",
        description = "Indicates gas containers under pressure, which may explode if heated.",
        history = "Part of the Globally Harmonized System (GHS); regulated worldwide.",
        url = "https://en.wikipedia.org/wiki/GHS_hazard_pictograms",
        tags = listOf("gas cylinder", "pressure vessel", "GHS", "compressed gas", "explosion risk", "chemical hazard")
    ),
    Symbol(
        name = "Environmental Hazard",
        category = "GHS Hazard",
        description = "Warns that substances may cause damage to aquatic life or the environment.",
        history = "Adopted under GHS regulations, especially for chemical labeling.",
        url = "https://en.wikipedia.org/wiki/GHS_hazard_pictograms",
        tags = listOf("dead tree and fish", "aquatic toxicity", "pollution", "environmental hazard", "GHS symbol")
    ),
    Symbol(
        name = "Health Hazard",
        category = "GHS Hazard",
        description = "Represents substances that may cause serious health effects like cancer, respiratory issues, or organ toxicity.",
        history = "GHS symbol depicting a human silhouette with a starburst in the chest.",
        url = "https://en.wikipedia.org/wiki/GHS_hazard_pictograms",
        tags = listOf("human torso", "chronic health", "carcinogen", "toxic", "organ damage", "GHS hazard")
    ),
    Symbol(
        name = "Irritant",
        category = "GHS Hazard",
        description = "Warns of substances that may irritate skin, eyes, or cause allergic reactions.",
        history = "Exclamation mark icon used for general health hazards (less severe than skull/crossbones).",
        url = "https://en.wikipedia.org/wiki/GHS_hazard_pictograms",
        tags = listOf("exclamation mark", "skin irritant", "eye damage", "mild toxicity", "GHS", "hazard warning")
    ),
    Symbol(
        name = "Oxidizing Agent",
        category = "Chemical Hazard",
        description = "Indicates chemicals that intensify fire or explosion by releasing oxygen.",
        history = "Depicted by a flame over a circle in ISO/GHS systems.",
        url = "https://en.wikipedia.org/wiki/Oxidizing_agent",
        tags = listOf("flame over circle", "oxidizer", "oxygen reaction", "intensifies fire", "GHS", "chemical symbol")
    ),
    Symbol(
        name = "Danger of Death",
        category = "Electrical Hazard",
        description = "Used in the UK and other countries to indicate fatal electric shock risk.",
        history = "Features lightning bolt striking a person; common on high-voltage equipment.",
        url = "https://en.wikipedia.org/wiki/Hazard_symbol",
        tags = listOf("lightning strike", "fatal shock", "electric hazard", "danger of death", "UK signage")
    ),
    Symbol(
        name = "Hot Surface",
        category = "Physical Hazard",
        description = "Warns of surfaces that may cause burns upon contact.",
        history = "ISO 7010 triangle symbol showing hand and heat waves.",
        url = "https://en.wikipedia.org/wiki/Hazard_symbol",
        tags = listOf("hand with heat waves", "burn warning", "hot metal", "thermal hazard", "ISO triangle")
    ),
    Symbol(
        name = "Forklift Traffic",
        category = "Industrial Hazard",
        description = "Warns that industrial vehicles may be in operation in the area.",
        history = "Common in warehouses and manufacturing zones under ISO 7010.",
        url = "https://en.wikipedia.org/wiki/Safety_sign",
        tags = listOf("forklift icon", "vehicle traffic", "material handling", "industrial zone", "safety warning")
    ),
    Symbol(
        name = "Laser Area",
        category = "Radiation Hazard",
        description = "Used to mark laser operation zones where eye or skin damage is possible.",
        history = "Features laser beam emitting from a central point.",
        url = "https://en.wikipedia.org/wiki/Laser_safety",
        tags = listOf("laser icon", "beam hazard", "radiation", "eye safety", "scientific lab", "ISO warning")
    ),
    Symbol(
        name = "Magnetic Field",
        category = "EMF / Medical Equipment Hazard",
        description = "Indicates strong magnetic fields, dangerous to pacemakers and electronics.",
        history = "Used in MRI facilities and electrical substations.",
        url = "https://en.wikipedia.org/wiki/Magnetic_field",
        tags = listOf("horseshoe magnet", "field lines", "EMF", "MRI", "magnet warning", "ISO 7010")
    ),
    Symbol(
        name = "No Open Flames",
        category = "Prohibition",
        description = "Indicates that ignition sources such as matches or lighters are prohibited.",
        history = "Red circle with a crossed-out flame icon, standardized globally.",
        url = "https://en.wikipedia.org/wiki/Prohibition_sign",
        tags = listOf("no flame", "fire ban", "red circle", "crossed out", "ignition source", "prohibition")
    ),
    Symbol(
        name = "Wear Respirator",
        category = "Mandatory PPE",
        description = "Indicates that a respirator mask must be worn due to airborne hazards.",
        history = "Part of ISO 7010 blue-circle mandatory signs.",
        url = "https://en.wikipedia.org/wiki/Respirator",
        tags = listOf("respirator mask", "PPE", "airborne particles", "safety gear", "mandatory symbol")
    ),
    Symbol(
        name = "Chemical Spill",
        category = "Emergency / Cleanup",
        description = "Indicates an area where hazardous chemical cleanup is in progress or required.",
        history = "Used with GHS or workplace-specific signs in labs, plants.",
        url = "https://en.wikipedia.org/wiki/Chemical_spill",
        tags = listOf("spill cleanup", "hazmat", "chemical danger", "emergency", "lab safety", "GHS")
    ),
    Symbol(
        name = "Eye Wash Station",
        category = "Emergency Equipment",
        description = "Marks the location of a station for rinsing eyes after chemical exposure.",
        history = "Standard green sign with icon of eye and water spray.",
        url = "https://en.wikipedia.org/wiki/Emergency_eyewash_and_shower_station",
        tags = listOf("eye symbol", "water spray", "emergency", "eye wash", "chemical exposure", "safety station")
    ),
    Symbol(
        name = "Hearing Protection Required",
        category = "Mandatory PPE",
        description = "Indicates that ear protection must be worn in high-noise environments.",
        history = "Blue ISO circle featuring earmuff icon.",
        url = "https://en.wikipedia.org/wiki/Hearing_protection",
        tags = listOf("earmuffs", "PPE", "noise hazard", "mandatory", "ear protection", "ISO sign")
    ), // Safety/Warning Symbols
    Symbol( // Modern Symbols
        name = "Dollar Sign",
        category = "Currency",
        description = "Represents the US dollar and various other currencies.",
        history = "Originates from the Spanish peso; widely used globally.",
        url = "https://en.wikipedia.org/wiki/Dollar_sign",
        tags = listOf("money", "finance", "USD", "currency", "dollar")
    ),
    Symbol(
        name = "Euro Sign",
        category = "Currency",
        description = "Symbol for the euro, the official currency of the Eurozone.",
        history = "Introduced in 1996; inspired by the Greek letter epsilon.",
        url = "https://en.wikipedia.org/wiki/Euro_sign",
        tags = listOf("money", "finance", "EUR", "currency", "euro")
    ),
    Symbol(
        name = "Pound Sterling Sign",
        category = "Currency",
        description = "Denotes the British pound sterling.",
        history = "Derived from the letter 'L' for libra, meaning scales or balance.",
        url = "https://en.wikipedia.org/wiki/Pound_sign",
        tags = listOf("money", "finance", "GBP", "currency", "pound")
    ),
    Symbol(
        name = "Yen Yuan Sign",
        category = "Currency",
        description = "Represents both the Japanese yen and Chinese yuan.",
        history = "The symbol is a stylized 'Y' with two horizontal strokes.",
        url = "https://en.wikipedia.org/wiki/Yen_sign",
        tags = listOf("money", "finance", "JPY", "CNY", "currency", "yen", "yuan")
    ),
    Symbol(
        name = "Indian Rupee Sign",
        category = "Currency",
        description = "Symbol for the Indian rupee.",
        history = "Adopted in 2010; combines Devanagari 'Ra' and Latin 'R'.",
        url = "https://en.wikipedia.org/wiki/Indian_rupee_sign",
        tags = listOf("money", "finance", "INR", "currency", "rupee", "India")
    ),
    Symbol(
        name = "Bitcoin Sign",
        category = "Digital Currency",
        description = "Represents Bitcoin, a decentralized digital currency.",
        history = "Introduced in 2009; resembles a capital 'B' with two vertical lines.",
        url = "https://en.wikipedia.org/wiki/Bitcoin",
        tags = listOf("cryptocurrency", "digital", "BTC", "currency", "bitcoin")
    ),
    Symbol(
        name = "Cent Sign",
        category = "Currency",
        description = "Denotes one-hundredth of a dollar or other decimal currencies.",
        history = "A 'c' with a vertical line through it; used in various currencies.",
        url = "https://en.wikipedia.org/wiki/Cent_(currency)",
        tags = listOf("money", "finance", "currency", "cent", "fractional")
    ),
    Symbol(
        name = "Won Sign",
        category = "Currency",
        description = "Symbol for the South Korean won.",
        history = "A 'W' with two horizontal lines; represents Korea's currency.",
        url = "https://en.wikipedia.org/wiki/Won_sign",
        tags = listOf("money", "finance", "KRW", "currency", "won", "Korea")
    ),
    Symbol(
        name = "Naira Sign",
        category = "Currency",
        description = "Denotes the Nigerian naira.",
        history = "An 'N' with two horizontal lines; introduced in 1973.",
        url = "https://en.wikipedia.org/wiki/Naira",
        tags = listOf("money", "finance", "NGN", "currency", "naira", "Nigeria")
    ),
    Symbol(
        name = "Peso Sign",
        category = "Currency",
        description = "Represents the peso used in various Latin American countries.",
        history = "Similar to the dollar sign; used in countries like Mexico and Argentina.",
        url = "https://en.wikipedia.org/wiki/Peso",
        tags = listOf("money", "finance", "currency", "peso", "Latin America")
    ),
    Symbol(
        name = "At Symbol",
        category = "Digital Communication",
        description = "Used in email addresses and social media handles.",
        history = "Originated as a commercial symbol meaning 'at the rate of'.",
        url = "https://en.wikipedia.org/wiki/At_sign",
        tags = listOf("email", "internet", "communication", "symbol", "at sign")
    ),
    Symbol(
        name = "Hashtag",
        category = "Digital Communication",
        description = "Used to tag topics on social media platforms.",
        history = "Gained popularity on Twitter to group conversations.",
        url = "https://en.wikipedia.org/wiki/Hashtag",
        tags = listOf("social media", "tag", "internet", "communication", "hashtag")
    ),
    Symbol(
        name = "Wi-Fi Symbol",
        category = "Wireless Technology",
        description = "Indicates the presence of a wireless internet connection.",
        history = "Standardized symbol showing radiating waves.",
        url = "https://en.wikipedia.org/wiki/Wi-Fi",
        tags = listOf("internet", "wireless", "network", "technology", "Wi-Fi")
    ),
    Symbol(
        name = "Bluetooth Symbol",
        category = "Wireless Technology",
        description = "Represents Bluetooth wireless communication standard.",
        history = "Combines Nordic runes for 'H' and 'B' after King Harald Bluetooth.",
        url = "https://en.wikipedia.org/wiki/Bluetooth",
        tags = listOf("wireless", "communication", "technology", "Bluetooth", "devices")
    ),
    Symbol(
        name = "Power Symbol",
        category = "Electronics",
        description = "Indicates the power on/off function on devices.",
        history = "Derived from binary symbols representing 'on' and 'off'.",
        url = "https://en.wikipedia.org/wiki/Power_symbol",
        tags = listOf("electronics", "device", "power", "on/off", "symbol")
    ),
    Symbol(
        name = "USB Symbol",
        category = "Connectivity",
        description = "Represents the Universal Serial Bus standard for connectors.",
        history = "Designed to symbolize versatility and universality.",
        url = "https://en.wikipedia.org/wiki/USB",
        tags = listOf("technology", "connection", "data transfer", "USB", "devices")
    ),
    Symbol(
        name = "Recycle Symbol",
        category = "Environmental",
        description = "Indicates that a product is recyclable.",
        history = "Three chasing arrows forming a triangle; introduced in 1970.",
        url = "https://en.wikipedia.org/wiki/Recycling_symbol",
        tags = listOf("environment", "sustainability", "recycle", "ecology", "symbol")
    ),
    Symbol(
        name = "Trademark Symbol",
        category = "Commercial",
        description = "Indicates that a trademark is claimed by a company.",
        history = "Used with unregistered trademarks to alert others of proprietary branding.",
        url = "https://en.wikipedia.org/wiki/Trademark_symbol",
        tags = listOf("TM", "trademark", "brand", "commercial", "ownership")
    ),
    Symbol(
        name = "Registered Trademark Symbol",
        category = "Commercial",
        description = "Used for trademarks officially registered with a government authority.",
        history = "Circle-R (®) is legally protected under trademark law.",
        url = "https://en.wikipedia.org/wiki/Registered_trademark_symbol",
        tags = listOf("®", "registered", "trademark", "brand", "legal", "ownership")
    ),
    Symbol(
        name = "Copyright Symbol",
        category = "Intellectual Property",
        description = "Denotes the legal right of an author or creator over original work.",
        history = "The © symbol is internationally recognized under copyright law.",
        url = "https://en.wikipedia.org/wiki/Copyright_symbol",
        tags = listOf("©", "copyright", "intellectual property", "creative work", "ownership", "law")
    ),
    Symbol(
        name = "Service Mark Symbol",
        category = "Commercial",
        description = "Used for services rather than physical products.",
        history = "The ℠ symbol differentiates service-based marks from trademarks.",
        url = "https://en.wikipedia.org/wiki/Service_mark",
        tags = listOf("℠", "service mark", "brand", "services", "commercial", "legal")
    ),
    Symbol(
        name = "Barcode",
        category = "Retail / Tracking",
        description = "Used on products for inventory tracking and scanning.",
        history = "Introduced in 1974; standard for retail around the world.",
        url = "https://en.wikipedia.org/wiki/Barcode",
        tags = listOf("black bars", "scanner", "product code", "UPC", "retail", "inventory")
    ),
    Symbol(
        name = "QR Code",
        category = "Digital",
        description = "Matrix barcode used to store data for quick scanning with a phone or device.",
        history = "Invented in 1994 by Denso Wave in Japan for industrial tracking.",
        url = "https://en.wikipedia.org/wiki/QR_code",
        tags = listOf("square code", "QR", "scanner", "mobile link", "barcode", "digital code")
    ),
    Symbol(
        name = "Universal Recycling Symbol",
        category = "Consumer / Environmental",
        description = "Indicates a product is recyclable.",
        history = "Introduced for Earth Day 1970; consists of three chasing arrows.",
        url = "https://en.wikipedia.org/wiki/Recycling_symbol",
        tags = listOf("green arrows", "triangle", "recycle", "environment", "consumer packaging")
    ),
    Symbol(
        name = "Green Dot Symbol",
        category = "Consumer / Packaging",
        description = "Used on packaging in Europe to show financial support of recycling.",
        history = "Established in Germany; often confused with the recycling symbol.",
        url = "https://en.wikipedia.org/wiki/Green_Dot_(symbol)",
        tags = listOf("green circle", "arrows", "eco friendly", "EU packaging", "waste recovery")
    ),
    Symbol(
        name = "Estimated Sign (℮)",
        category = "Packaging",
        description = "Used on European packaging to indicate estimated quantity.",
        history = "Mandated in the EU to represent manufacturing tolerance.",
        url = "https://en.wikipedia.org/wiki/Estimated_sign",
        tags = listOf("℮", "EU packaging", "estimated content", "labeling", "quantity symbol")
    ),
    Symbol(
        name = "Period-After-Opening Symbol",
        category = "Consumer / Cosmetics",
        description = "Indicates how long a product remains usable after opening.",
        history = "Used mainly on cosmetics, shown as an open jar with a number (e.g. 6M, 12M).",
        url = "https://en.wikipedia.org/wiki/Period-after-opening_symbol",
        tags = listOf("open jar", "6M", "12M", "cosmetics", "expiry", "usage timeline")
    ),
    Symbol(
        name = "Bluetooth Symbol",
        category = "Connectivity",
        description = "Represents Bluetooth wireless connection capability.",
        history = "Combines runes Hagall and Bjarkan — initials of Harald Bluetooth.",
        url = "https://en.wikipedia.org/wiki/Bluetooth",
        tags = listOf("bluetooth", "wireless", "devices", "connectivity", "symbol", "technology")
    ),
    Symbol(
        name = "Wi-Fi Icon",
        category = "Connectivity",
        description = "Indicates wireless internet signal strength or presence.",
        history = "Standardized icon with radiating arcs above a dot.",
        url = "https://en.wikipedia.org/wiki/Wi-Fi",
        tags = listOf("wireless", "signal", "network", "internet", "wifi", "technology")
    ),
    Symbol(
        name = "Share Icon",
        category = "Digital UI",
        description = "Represents the option to share content digitally.",
        history = "Common in app design and mobile interfaces.",
        url = "https://en.wikipedia.org/wiki/Share_icon",
        tags = listOf("arrow", "sharing", "mobile app", "UI", "send", "content")
    ),
    Symbol(
        name = "Settings Gear",
        category = "Digital UI",
        description = "Used to represent configuration or settings in software.",
        history = "A gear or cog symbol, almost universal in applications.",
        url = "https://en.wikipedia.org/wiki/User_interface",
        tags = listOf("gear", "settings", "preferences", "configuration", "software", "UI icon")
    ),
    Symbol(
        name = "Menu (Hamburger) Icon",
        category = "Digital UI",
        description = "Represents a collapsible menu in digital apps and websites.",
        history = "Consists of three horizontal lines; widely used in mobile UI.",
        url = "https://en.wikipedia.org/wiki/Hamburger_button",
        tags = listOf("three bars", "menu", "navigation", "mobile UI", "hamburger icon")
    ),
    Symbol(
        name = "Search Icon (Magnifying Glass)",
        category = "Digital UI",
        description = "Represents the search function in apps and websites.",
        history = "Became a standard UI element in early search engines and browsers.",
        url = "https://en.wikipedia.org/wiki/Magnifying_glass",
        tags = listOf("search", "magnifying glass", "lookup", "find", "UI", "icon")
    ),
    Symbol(
        name = "Trash Icon",
        category = "Digital UI",
        description = "Used for deleting or removing content.",
        history = "Visually modeled after a waste bin; now standard in digital interfaces.",
        url = "https://en.wikipedia.org/wiki/Computer_icons",
        tags = listOf("trash", "delete", "remove", "garbage bin", "UI icon", "discard")
    ),
    Symbol(
        name = "Download Icon",
        category = "Digital UI",
        description = "Indicates a file or content download action.",
        history = "Arrow pointing downward into a tray; standard in browsers and apps.",
        url = "https://en.wikipedia.org/wiki/Download",
        tags = listOf("download", "arrow", "save file", "offline", "storage", "device")
    ),
    Symbol(
        name = "Upload Icon",
        category = "Digital UI",
        description = "Used for sending files from a local device to a server or cloud.",
        history = "Arrow pointing upward from a tray or device.",
        url = "https://en.wikipedia.org/wiki/Upload",
        tags = listOf("upload", "arrow", "send file", "cloud", "submit", "transfer")
    ),
    Symbol(
        name = "Link Icon (Chain)",
        category = "Digital UI",
        description = "Represents a hyperlink or connection to another resource.",
        history = "Two chain links or a link symbol; common in editors and browsers.",
        url = "https://en.wikipedia.org/wiki/Hyperlink",
        tags = listOf("link", "chain", "hyperlink", "URL", "anchor", "web navigation")
    ),
    Symbol(
        name = "Edit (Pencil) Icon",
        category = "Digital UI",
        description = "Indicates the ability to edit or modify content.",
        history = "A pencil or pen icon used across productivity tools and CMSs.",
        url = "https://en.wikipedia.org/wiki/Computer_icons",
        tags = listOf("edit", "pencil", "modify", "text", "UI", "content")
    ),
    Symbol(
        name = "Camera Icon",
        category = "Mobile App",
        description = "Used to trigger photo capture or access the camera.",
        history = "Based on the visual shape of traditional cameras.",
        url = "https://en.wikipedia.org/wiki/Camera",
        tags = listOf("camera", "photo", "picture", "capture", "icon", "UI")
    ),
    Symbol(
        name = "Microphone Icon",
        category = "Mobile App",
        description = "Represents audio input, voice commands, or recording.",
        history = "Styled after old studio microphones; used in voice-enabled apps.",
        url = "https://en.wikipedia.org/wiki/Microphone",
        tags = listOf("microphone", "record", "voice", "speech", "UI", "audio input")
    ),
    Symbol(
        name = "Notification Bell",
        category = "Digital UI",
        description = "Used to show alerts or notifications in an app.",
        history = "Styled after physical bells; now universal in app interfaces.",
        url = "https://en.wikipedia.org/wiki/Notification",
        tags = listOf("bell", "alert", "reminder", "notification", "message", "UI icon")
    ),
    Symbol(
        name = "Lock Icon",
        category = "Security / UI",
        description = "Indicates security, privacy, or encryption.",
        history = "A padlock image commonly used on secure sites and password fields.",
        url = "https://en.wikipedia.org/wiki/Padlock",
        tags = listOf("lock", "security", "private", "encryption", "safe", "credentials")
    ),
    Symbol(
        name = "Eye Icon",
        category = "UI / Visibility",
        description = "Toggles visibility (e.g., show/hide password or view mode).",
        history = "Eye-shaped symbol used in visibility or access control features.",
        url = "https://en.wikipedia.org/wiki/Eye",
        tags = listOf("eye", "view", "visibility", "show", "hide", "interface")
    ),
    Symbol(
        name = "Refresh Reload Icon",
        category = "Browser Interface",
        description = "Used to reload or refresh content or data.",
        history = "Typically represented by a circular arrow.",
        url = "https://en.wikipedia.org/wiki/Refresh_rate",
        tags = listOf("refresh", "reload", "update", "loop", "arrow", "browser")
    ),
    Symbol(
        name = "Battery Icon",
        category = "Mobile / Electronics",
        description = "Indicates remaining battery charge on a device.",
        history = "Common UI element showing levels from empty to full.",
        url = "https://en.wikipedia.org/wiki/Battery_indicator",
        tags = listOf("battery", "charge", "power level", "energy", "device", "icon")
    ),
    Symbol(
        name = "Volume Icon",
        category = "Audio / UI",
        description = "Controls audio volume and mute settings.",
        history = "Speaker or wave icons used in multimedia systems.",
        url = "https://en.wikipedia.org/wiki/Volume_icon",
        tags = listOf("volume", "sound", "mute", "audio", "speaker", "icon")
    ),
    Symbol(
        name = "Help (Question Mark)",
        category = "Digital UI",
        description = "Links to help information or tooltips.",
        history = "A question mark icon commonly used for support and guidance.",
        url = "https://en.wikipedia.org/wiki/Help_symbol",
        tags = listOf("help", "question", "support", "tooltip", "info", "guide")
    ),
    Symbol(
        name = "Peace Symbol",
        category = "Social",
        description = "A circular emblem with three lines forming an inverted Y, representing peace and anti-war movements.",
        history = "Designed in 1958 for the British nuclear disarmament movement; widely adopted globally.",
        url = "https://en.wikipedia.org/wiki/Peace_symbols",
        tags = listOf("peace", "anti-war", "harmony", "unity")
    ),
    Symbol(
        name = "Dove with Olive Branch",
        category = "Social",
        description = "A dove carrying an olive branch, symbolizing peace and reconciliation.",
        history = "Originates from Christian iconography and ancient symbolism; widely recognized as a peace emblem.",
        url = "https://en.wikipedia.org/wiki/Peace_symbols",
        tags = listOf("peace", "hope", "reconciliation", "harmony")
    ),
    Symbol(
        name = "Heart Symbol",
        category = "Social",
        description = "A stylized heart shape representing love, affection, and compassion.",
        history = "Used since the Middle Ages to depict the heart; now universal in expressing love.",
        url = "https://en.wikipedia.org/wiki/Heart_(symbol)",
        tags = listOf("love", "affection", "compassion", "emotion")
    ),
    Symbol(
        name = "Smiley Face",
        category = "Social",
        description = "A simple yellow face with a smiling expression, symbolizing happiness and positivity.",
        history = "Created in the 1960s; became a cultural icon representing joy and goodwill.",
        url = "https://en.wikipedia.org/wiki/Smiley",
        tags = listOf("happiness", "joy", "positivity", "emotion")
    ),
    Symbol(
        name = "Handshake",
        category = "Social",
        description = "Two hands clasped together, symbolizing agreement, friendship, and cooperation.",
        history = "Ancient symbol of trust and greeting; used in various cultures to signify accord.",
        url = "https://en.wikipedia.org/wiki/Handshake",
        tags = listOf("friendship", "agreement", "cooperation", "trust")
    ),
    Symbol(
        name = "White Dove",
        category = "Social",
        description = "A white dove in flight, symbolizing peace, purity, and hope.",
        history = "Associated with peace and the Holy Spirit in Christian tradition; universally recognized.",
        url = "https://en.wikipedia.org/wiki/Dove_(symbol)",
        tags = listOf("peace", "hope", "purity", "spirituality")
    ),
    Symbol(
        name = "Female Symbol",
        category = "Gender",
        description = "A circle with a small cross below it, representing the female gender.",
        history = "Derived from the astrological symbol for Venus, associated with femininity.",
        url = "https://en.wikipedia.org/wiki/Gender_symbol",
        tags = listOf("female", "woman", "feminine", "Venus")
    ),
    Symbol(
        name = "Male Symbol",
        category = "Gender",
        description = "A circle with an arrow pointing diagonally upwards to the right, representing the male gender.",
        history = "Originates from the astrological symbol for Mars, associated with masculinity.",
        url = "https://en.wikipedia.org/wiki/Gender_symbol",
        tags = listOf("male", "man", "masculine", "Mars")
    ),
    Symbol(
        name = "Aries",
        category = "Zodiac",
        description = "Represents the ram, symbolizing courage, leadership, and energy.",
        history = "First sign of the zodiac; associated with Mars and fire.",
        url = "https://en.wikipedia.org/wiki/Aries_(astrology)",
        tags = listOf("♈", "ram", "horns", "zodiac", "fire sign", "aries", "march", "april")
    ),
    Symbol(
        name = "Taurus",
        category = "Zodiac",
        description = "Represents the bull, symbolizing strength, stability, and persistence.",
        history = "Ruled by Venus; associated with the Earth element.",
        url = "https://en.wikipedia.org/wiki/Taurus_(astrology)",
        tags = listOf("♉", "bull", "horns", "earth sign", "taurus", "april", "may")
    ),
    Symbol(
        name = "Gemini",
        category = "Zodiac",
        description = "Symbolizes the twins, associated with duality, adaptability, and communication.",
        history = "Ruled by Mercury; represents air element.",
        url = "https://en.wikipedia.org/wiki/Gemini_(astrology)",
        tags = listOf("♊", "twins", "dual", "air sign", "gemini", "may", "june")
    ),
    Symbol(
        name = "Cancer",
        category = "Zodiac",
        description = "Represents the crab, symbolizing emotion, intuition, and protection.",
        history = "Ruled by the Moon; a water sign known for sensitivity.",
        url = "https://en.wikipedia.org/wiki/Cancer_(astrology)",
        tags = listOf("♋", "crab", "shell", "water sign", "cancer", "june", "july")
    ),
    Symbol(
        name = "Leo",
        category = "Zodiac",
        description = "Represents the lion, symbolizing pride, confidence, and vitality.",
        history = "Ruled by the Sun; associated with the fire element.",
        url = "https://en.wikipedia.org/wiki/Leo_(astrology)",
        tags = listOf("♌", "lion", "mane", "fire sign", "leo", "july", "august")
    ),
    Symbol(
        name = "Virgo",
        category = "Zodiac",
        description = "Symbolizes the maiden, associated with purity, precision, and service.",
        history = "Ruled by Mercury; an Earth sign linked to organization.",
        url = "https://en.wikipedia.org/wiki/Virgo_(astrology)",
        tags = listOf("♍", "maiden", "virgin", "earth sign", "virgo", "august", "september")
    ),
    Symbol(
        name = "Libra",
        category = "Zodiac",
        description = "Symbolizes the scales, representing balance, justice, and harmony.",
        history = "Ruled by Venus; the only non-living zodiac sign.",
        url = "https://en.wikipedia.org/wiki/Libra_(astrology)",
        tags = listOf("♎", "scales", "balance", "air sign", "libra", "september", "october")
    ),
    Symbol(
        name = "Scorpio",
        category = "Zodiac",
        description = "Represents the scorpion, symbolizing intensity, transformation, and passion.",
        history = "Ruled by Pluto and Mars; water element.",
        url = "https://en.wikipedia.org/wiki/Scorpio_(astrology)",
        tags = listOf("♏", "scorpion", "sting", "water sign", "scorpio", "october", "november")
    ),
    Symbol(
        name = "Sagittarius",
        category = "Zodiac",
        description = "Symbolizes the archer (centaur), representing adventure, wisdom, and exploration.",
        history = "Ruled by Jupiter; a fire sign.",
        url = "https://en.wikipedia.org/wiki/Sagittarius_(astrology)",
        tags = listOf("♐", "archer", "arrow", "centaur", "fire sign", "sagittarius", "november", "december")
    ),
    Symbol(
        name = "Capricorn",
        category = "Zodiac",
        description = "Represents the goat or sea-goat, symbolizing discipline, ambition, and resilience.",
        history = "Ruled by Saturn; an Earth sign.",
        url = "https://en.wikipedia.org/wiki/Capricorn_(astrology)",
        tags = listOf("♑", "goat", "horns", "earth sign", "capricorn", "december", "january")
    ),
    Symbol(
        name = "Aquarius",
        category = "Zodiac",
        description = "Symbolizes the water bearer, associated with innovation, idealism, and humanitarianism.",
        history = "Ruled by Uranus; often confused as a water sign, but is air.",
        url = "https://en.wikipedia.org/wiki/Aquarius_(astrology)",
        tags = listOf("♒", "water bearer", "jug", "air sign", "aquarius", "january", "february")
    ),
    Symbol(
        name = "Pisces",
        category = "Zodiac",
        description = "Represents two fish swimming in opposite directions, symbolizing empathy, intuition, and dreams.",
        history = "Ruled by Neptune; water sign associated with imagination.",
        url = "https://en.wikipedia.org/wiki/Pisces_(astrology)",
        tags = listOf("♓", "fish", "duality", "water sign", "pisces", "february", "march")
    ), // Modern Symbols
    Symbol( // Cultural/Historical Symbols
        name = "Fleur-de-lis",
        category = "Cultural / Heraldry",
        description = "A stylized lily flower associated with French royalty, purity, and heritage.",
        history = "Used since medieval times on coats of arms and flags, especially in France.",
        url = "https://en.wikipedia.org/wiki/Fleur-de-lis",
        tags = listOf("flower", "lily", "royalty", "France", "purity", "coat of arms", "heritage")
    ),
    Symbol(
        name = "Maple Leaf",
        category = "Cultural / National",
        description = "A national symbol of Canada representing unity, nature, and peace.",
        history = "Used as a Canadian emblem since the 18th century; appears on the national flag.",
        url = "https://en.wikipedia.org/wiki/Maple_leaf",
        tags = listOf("leaf", "Canada", "nature", "national", "unity", "peace", "flag")
    ),
    Symbol(
        name = "Olive Branch",
        category = "Cultural / Peace",
        description = "Symbol of peace and reconciliation dating back to ancient Greece.",
        history = "Used by the Greeks, Romans, and early Christians to signify peace and victory.",
        url = "https://en.wikipedia.org/wiki/Olive_branch",
        tags = listOf("olive", "branch", "peace", "truce", "harmony", "ancient")
    ),
    Symbol(
        name = "Lotus Flower",
        category = "Cultural / Spiritual",
        description = "A sacred flower in Eastern traditions symbolizing purity, rebirth, and enlightenment.",
        history = "Prominent in Hinduism, Buddhism, and Egyptian culture.",
        url = "https://en.wikipedia.org/wiki/Lotus_flower",
        tags = listOf("lotus", "flower", "purity", "rebirth", "Buddhism", "Hinduism", "peace")
    ),
    Symbol(
        name = "Ankh",
        category = "Ancient Egyptian / Cultural",
        description = "An ancient Egyptian hieroglyph symbolizing life and eternal existence.",
        history = "Common in tomb art and temple carvings as a sign of vitality and afterlife.",
        url = "https://en.wikipedia.org/wiki/Ankh",
        tags = listOf("ankh", "loop cross", "eternal life", "Egypt", "ancient", "vitality")
    ),
    Symbol(
        name = "Shamrock",
        category = "Cultural / National",
        description = "A cloverleaf symbol of Ireland and St. Patrick, representing faith, hope, and love.",
        history = "Used by St. Patrick to explain the Holy Trinity; now a national and cultural symbol.",
        url = "https://en.wikipedia.org/wiki/Shamrock",
        tags = listOf("clover", "Ireland", "green", "heritage", "symbol", "faith")
    ),
    Symbol(
        name = "Wheel of Dharma (Dharmachakra)",
        category = "Cultural / Spiritual",
        description = "A wheel symbolizing Buddhist teachings and the path to enlightenment.",
        history = "One of the oldest Buddhist symbols; represents the Eightfold Path.",
        url = "https://en.wikipedia.org/wiki/Dharmachakra",
        tags = listOf("wheel", "Buddhism", "enlightenment", "teaching", "path", "India")
    ),
    Symbol(
        name = "Celtic Knot",
        category = "Cultural / Artistic",
        description = "An intricate loop symbol representing eternity and interconnectedness.",
        history = "Found in Irish and Scottish art; used in medieval manuscripts and jewelry.",
        url = "https://en.wikipedia.org/wiki/Celtic_knot",
        tags = listOf("interlaced", "eternal", "loop", "heritage", "Celtic", "Ireland", "symbolic art")
    ),
    Symbol(
        name = "Triskelion",
        category = "Cultural / Ancient Europe",
        description = "Triple spiral symbol representing progress, cycles, and unity.",
        history = "Found in Celtic, Greek, and Sicilian traditions; often linked to motion and growth.",
        url = "https://en.wikipedia.org/wiki/Triskelion",
        tags = listOf("three spirals", "motion", "unity", "Celtic", "Greek", "progress")
    ),
    Symbol(
        name = "Tree of Life",
        category = "Cultural / Mythology",
        description = "A widespread symbol of connection between earth, heaven, and life.",
        history = "Appears in Norse, Mesopotamian, and Kabbalistic traditions; represents harmony and balance.",
        url = "https://en.wikipedia.org/wiki/Tree_of_life",
        tags = listOf("tree", "life", "growth", "balance", "mythology", "connection", "universal")
    ),
    Symbol(
        name = "Sankofa",
        category = "Cultural / African",
        description = "A symbol from the Akan people of Ghana, depicting a bird with its head turned backward taking an egg from its back, representing the importance of learning from the past.",
        history = "Emphasizes the Akan proverb: 'It is not wrong to go back for that which you have forgotten.'",
        url = "https://en.wikipedia.org/wiki/Sankofa",
        tags = listOf("Ghana", "Akan", "wisdom", "learning", "heritage")
    ),
    Symbol(
        name = "Tree of Peace",
        category = "Cultural / Indigenous",
        description = "A symbol from the Haudenosaunee (Iroquois) representing unity and the Great Law of Peace.",
        history = "Associated with the unification of the Five Nations under a single peace agreement.",
        url = "https://en.wikipedia.org/wiki/Tree_of_Peace",
        tags = listOf("Haudenosaunee", "Iroquois", "unity", "peace", "indigenous")
    ),
    Symbol(
        name = "Agaseke",
        category = "Cultural / African",
        description = "A traditional Rwandan woven basket symbolizing peace, goodwill, and the value of family.",
        history = "Used in ceremonies and as gifts to promote harmony and strong family ties.",
        url = "https://en.wikipedia.org/wiki/Agaseke",
        tags = listOf("Rwanda", "basket", "peace", "family", "craftsmanship")
    ),
    Symbol(
        name = "Adinkra Symbols",
        category = "Cultural / African",
        description = "Visual symbols from the Akan people of Ghana, representing concepts or aphorisms.",
        history = "Used extensively in fabrics, pottery, and architecture to convey traditional wisdom and cultural values.",
        url = "https://en.wikipedia.org/wiki/Adinkra_symbols",
        tags = listOf("Ghana", "Akan", "wisdom", "culture", "symbols")
    ),
    Symbol(
        name = "Cornicello",
        category = "Cultural / Italian",
        description = "A horn-shaped amulet from Naples, Italy, symbolizing good luck, protection, love, and prosperity.",
        history = "Traditionally crafted from red coral, gold, or silver, believed to ward off negative energies and the evil eye.",
        url = "https://apnews.com/article/4d6cc5d8e05b7d515ea72ab6081e504e",
        tags = listOf("Italy", "amulet", "luck", "protection", "prosperity")
    ), // Cultural/Historical Symbols
    Symbol( // Logos
        name = "Apple",
        category = "Logos",
        description = "A stylized apple with a bite taken out of the right side.",
        history = "Designed in 1977 by Rob Janoff; represents knowledge and sleek minimalism.",
        url = "https://en.wikipedia.org/wiki/Apple_Inc.",
        tags = listOf("apple shape", "right bite", "leaf on top", "monochrome", "fruit silhouette", "technology logo")
    ),
    Symbol(
        name = "Nike Swoosh",
        category = "Logos",
        description = "A curved checkmark-like shape sweeping upward, symbolizing movement.",
        history = "Created in 1971 by Carolyn Davidson; inspired by the wing of Nike, the Greek goddess of victory.",
        url = "https://en.wikipedia.org/wiki/Nike,_Inc.",
        tags = listOf("curved line", "checkmark shape", "single swoosh", "dynamic", "italic motion", "sports logo")
    ),
    Symbol(
        name = "McDonald's Golden Arches",
        category = "Logos",
        description = "Two symmetrical golden arches forming the letter M.",
        history = "Introduced in 1968; intended to evoke familiarity and fast food architecture.",
        url = "https://en.wikipedia.org/wiki/McDonald's",
        tags = listOf("golden arches", "symmetrical M", "rounded top", "yellow", "twin curves", "fast food logo")
    ),
    Symbol(
        name = "Starbucks Siren",
        category = "Logos",
        description = "A two-tailed mermaid with long hair in a circular green emblem.",
        history = "Based on a 16th-century Norse woodcut; represents allure, the sea, and Seattle’s maritime culture.",
        url = "https://en.wikipedia.org/wiki/Starbucks",
        tags = listOf("circle logo", "siren", "two tails", "green background", "long hair", "mermaid", "coffee brand")
    ),
    Symbol(
        name = "Amazon Smile",
        category = "Logos",
        description = "A wordmark with a yellow curved arrow from A to Z underneath.",
        history = "The arrow represents a wide range of products and a smile (customer satisfaction).",
        url = "https://en.wikipedia.org/wiki/Amazon_(company)",
        tags = listOf("curved arrow", "smile shape", "A to Z", "wordmark", "yellow underline", "ecommerce logo")
    ),
    Symbol(
        name = "FedEx",
        category = "Logos",
        description = "A wordmark with a hidden right-facing arrow between the E and X.",
        history = "Designed in 1994; the negative space arrow symbolizes speed and accuracy.",
        url = "https://en.wikipedia.org/wiki/FedEx",
        tags = listOf("wordmark", "hidden arrow", "negative space", "purple and orange", "E and X gap", "shipping logo")
    ),
    Symbol(
        name = "Toyota",
        category = "Logos",
        description = "Three overlapping ovals forming a symmetrical abstract T.",
        history = "Introduced in 1989 to represent unification of hearts and advanced technology.",
        url = "https://en.wikipedia.org/wiki/Toyota",
        tags = listOf("three ovals", "interlocking loops", "horizontal and vertical", "abstract T", "chrome emblem", "car brand")
    ),
    Symbol(
        name = "BMW",
        category = "Logos",
        description = "A circle divided into alternating blue and white quadrants, surrounded by a black ring.",
        history = "Represents a spinning propeller and the Bavarian flag.",
        url = "https://en.wikipedia.org/wiki/BMW",
        tags = listOf("circle logo", "blue and white quarters", "propeller", "black outer ring", "luxury car", "German brand")
    ),
    Symbol(
        name = "Mercedes-Benz",
        category = "Logos",
        description = "A three-pointed silver star enclosed in a circle.",
        history = "Symbolizes land, sea, and air dominance in transportation.",
        url = "https://en.wikipedia.org/wiki/Mercedes-Benz",
        tags = listOf("three-point star", "thin circle", "metallic silver", "minimalist", "automotive", "luxury")
    ),
    Symbol(
        name = "Pepsi Globe",
        category = "Logos",
        description = "A circle with red, white, and blue swirling wave patterns.",
        history = "The modern version was introduced in 2008, symbolizing global movement and refreshment.",
        url = "https://en.wikipedia.org/wiki/Pepsi",
        tags = listOf("color swirl", "red top", "white wave", "blue bottom", "circular logo", "soft drink", "beverage brand")
    ),
    Symbol(
        name = "Google",
        category = "Logos",
        description = "A multicolored wordmark with distinctive geometric letter shapes.",
        history = "The current flat logo was launched in 2015 to reflect simplicity and approachability.",
        url = "https://en.wikipedia.org/wiki/Google",
        tags = listOf("multicolor text", "blue red yellow green", "sans-serif", "geometric letters", "flat logo", "tech brand")
    ),
    Symbol(
        name = "Facebook (Meta)",
        category = "Logos",
        description = "An infinity loop rotated vertically, representing connection and the metaverse.",
        history = "Introduced in 2021 as Meta’s rebranding to emphasize a virtual future.",
        url = "https://en.wikipedia.org/wiki/Meta_Platforms",
        tags = listOf("infinity loop", "vertical twist", "minimalist", "blue icon", "metaverse", "social media")
    ),
    Symbol(
        name = "YouTube",
        category = "Logos",
        description = "A red play button triangle inside a rounded rectangle, usually beside black text.",
        history = "Known globally for video content; redesigned in 2017 to emphasize the play icon.",
        url = "https://en.wikipedia.org/wiki/YouTube",
        tags = listOf("red play button", "white triangle", "rounded rectangle", "video platform", "streaming", "media")
    ),
    Symbol(
        name = "Instagram",
        category = "Logos",
        description = "A gradient square camera outline with a small circle inside.",
        history = "Introduced in 2016 to modernize the original camera icon with colorful gradients.",
        url = "https://en.wikipedia.org/wiki/Instagram",
        tags = listOf("rounded square", "camera shape", "gradient pink orange purple", "white lines", "social media", "photography")
    ),
    Symbol(
        name = "LinkedIn",
        category = "Logos",
        description = "A simple blue square with white 'in' text inside.",
        history = "Used to represent professionalism and networking.",
        url = "https://en.wikipedia.org/wiki/LinkedIn",
        tags = listOf("blue square", "white letters", "'in' text", "minimal logo", "professional network")
    ),
    Symbol(
        name = "Twitter (X)",
        category = "Logos",
        description = "A bold, angular X symbol representing the rebrand from Twitter.",
        history = "Elon Musk rebranded Twitter to 'X' in 2023 with a sleek black-and-white logo.",
        url = "https://en.wikipedia.org/wiki/Twitter",
        tags = listOf("angular X", "black and white", "monochrome", "bold strokes", "social media", "rebrand")
    ),
    Symbol(
        name = "Spotify",
        category = "Logos",
        description = "A green circle with three curved black soundwave lines.",
        history = "Represents digital music streaming and audio broadcast.",
        url = "https://en.wikipedia.org/wiki/Spotify",
        tags = listOf("green circle", "black wave lines", "soundwaves", "simple icon", "music", "audio")
    ),
    Symbol(
        name = "Netflix",
        category = "Logos",
        description = "A red stylized 'N' made of ribbon-like diagonal bars on black.",
        history = "Designed to represent cinematic feel with clean, minimal visuals.",
        url = "https://en.wikipedia.org/wiki/Netflix",
        tags = listOf("red N", "black background", "diagonal bars", "ribbon style", "streaming", "cinema logo")
    ),
    Symbol(
        name = "Adobe",
        category = "Logos",
        description = "A stylized white 'A' with sharp triangular forms on a red square.",
        history = "The angular A design reflects creative sharpness and visual precision.",
        url = "https://en.wikipedia.org/wiki/Adobe_Inc.",
        tags = listOf("white A", "red square", "sharp triangle", "geometric", "design software", "creative")
    ),
    Symbol(
        name = "Dropbox",
        category = "Logos",
        description = "An open blue box composed of four rhombus shapes.",
        history = "Symbolizes storage, openness, and simplicity.",
        url = "https://en.wikipedia.org/wiki/Dropbox_(service)",
        tags = listOf("blue box", "rhombus shapes", "open cube", "4 diamonds", "cloud storage", "tech logo")
    ), Symbol(
        name = "Coca-Cola",
        category = "Logos",
        description = "A flowing, Spencerian script wordmark in red, conveying classic elegance.",
        history = "Designed in 1885 by Frank Mason Robinson, the logo's cursive script has become synonymous with the brand's rich history and global presence.",
        url = "https://en.wikipedia.org/wiki/Coca-Cola",
        tags = listOf("red cursive text", "Spencerian script", "flowing letters", "classic design", "beverage brand")
    ),
    Symbol(
        name = "Adidas",
        category = "Logos",
        description = "Three parallel diagonal stripes, often forming a triangle, symbolizing performance.",
        history = "Introduced in 1972, the 'Three Stripes' represent the company's focus on diversity, mountain-like challenges, and the appeal of athleticism.",
        url = "https://en.wikipedia.org/wiki/Adidas",
        tags = listOf("three diagonal stripes", "triangle formation", "black and white", "sportswear", "athletic brand")
    ),
    Symbol(
        name = "Shell",
        category = "Logos",
        description = "A stylized yellow scallop shell with a red outline, representing energy and movement.",
        history = "The pecten shell emblem has been in use since 1904, symbolizing the company's maritime heritage and its commitment to exploration.",
        url = "https://en.wikipedia.org/wiki/Shell_(company)",
        tags = listOf("yellow shell", "red outline", "scallop shape", "energy sector", "oil and gas")
    ),
    Symbol(
        name = "IBM",
        category = "Logos",
        description = "A blue wordmark with horizontal stripes cutting through the letters, symbolizing speed and dynamism.",
        history = "Designed by Paul Rand in 1972, the striped logo represents the company's progressive approach and technological innovation.",
        url = "https://en.wikipedia.org/wiki/IBM",
        tags = listOf("blue text", "horizontal stripes", "bold letters", "technology", "computing")
    ),
    Symbol(
        name = "Volkswagen",
        category = "Logos",
        description = "A circle enclosing a 'V' stacked above a 'W', representing the brand's initials.",
        history = "The interlocking letters within a circle have been the hallmark of Volkswagen since 1937, symbolizing unity and precision.",
        url = "https://en.wikipedia.org/wiki/Volkswagen",
        tags = listOf("circle with VW", "interlocking letters", "blue and silver", "automotive", "German engineering")
    ),
    Symbol(
        name = "FedEx",
        category = "Logos",
        description = "A purple and orange wordmark with a hidden arrow between the 'E' and 'x', symbolizing speed and precision.",
        history = "Designed in 1994 by Lindon Leader, the hidden arrow represents the company's forward-thinking and dynamic services.",
        url = "https://en.wikipedia.org/wiki/FedEx",
        tags = listOf("wordmark", "hidden arrow", "negative space", "purple and orange", "shipping logo")
    ),
    Symbol(
        name = "Toyota",
        category = "Logos",
        description = "Three overlapping ovals forming a symmetrical abstract 'T', representing the unification of the hearts of customers and the company's products.",
        history = "Introduced in 1989, the logo symbolizes the company's commitment to satisfaction and the global expansion of its technology.",
        url = "https://en.wikipedia.org/wiki/Toyota",
        tags = listOf("three ovals", "interlocking loops", "horizontal and vertical", "abstract T", "chrome emblem", "car brand")
    ),
    Symbol(
        name = "BMW",
        category = "Logos",
        description = "A circle divided into alternating blue and white quadrants, surrounded by a black ring, representing a spinning propeller and the colors of the Bavarian flag.",
        history = "Originates from the company's aviation history and its Bavarian roots.",
        url = "https://en.wikipedia.org/wiki/BMW",
        tags = listOf("circle logo", "blue and white quarters", "propeller", "black outer ring", "luxury car", "German brand")
    ),
    Symbol(
        name = "Mercedes-Benz",
        category = "Logos",
        description = "A three-pointed silver star enclosed in a circle, symbolizing the company's ambition to dominate land, sea, and air transportation.",
        history = "Adopted in 1926, the logo represents the brand's engineering prowess across all terrains.",
        url = "https://en.wikipedia.org/wiki/Mercedes-Benz",
        tags = listOf("three-point star", "thin circle", "metallic silver", "minimalist", "automotive", "luxury")
    ),
    Symbol(
        name = "Pepsi",
        category = "Logos",
        description = "A circle with red, white, and blue swirling wave patterns, representing the brand's global appeal and dynamic nature.",
        history = "The modern version was introduced in 2008, symbolizing global movement and refreshment.",
        url = "https://en.wikipedia.org/wiki/Pepsi",
        tags = listOf("color swirl", "red top", "white wave", "blue bottom", "circular logo", "soft drink", "beverage brand")
    ),
    Symbol(
        name = "Chanel",
        category = "Logos",
        description = "Two interlocking, mirrored letter 'C's forming a symmetrical emblem.",
        history = "Designed by Coco Chanel herself in 1925, the logo represents the designer's name and has become an enduring symbol of luxury and elegance.",
        url = "https://en.wikipedia.org/wiki/Chanel",
        tags = listOf("interlocking Cs", "black and white", "symmetrical design", "fashion brand", "luxury")
    ),
    Symbol(
        name = "Lego",
        category = "Logos",
        description = "Bold, white uppercase letters spelling 'LEGO' on a red square background.",
        history = "The logo has evolved since the company's founding in 1932, with the current design introduced in 1998 to reflect the brand's playful and creative spirit.",
        url = "https://en.wikipedia.org/wiki/Lego",
        tags = listOf("white text", "red background", "blocky font", "toy brand", "playfulness")
    ),
    Symbol(
        name = "Hyundai",
        category = "Logos",
        description = "A stylized, slanted letter 'H' enclosed in an oval, symbolizing a handshake.",
        history = "Introduced in 1991, the logo represents trust and satisfaction between the company and its customers.",
        url = "https://en.wikipedia.org/wiki/Hyundai",
        tags = listOf("slanted H", "oval enclosure", "silver color", "automotive", "trust")
    ),
    Symbol(
        name = "Philips",
        category = "Logos",
        description = "A shield containing a wave pattern and four stars, symbolizing innovation and quality.",
        history = "The emblem has been in use since 1938, reflecting the company's commitment to excellence in electronics.",
        url = "https://en.wikipedia.org/wiki/Philips",
        tags = listOf("shield shape", "wave pattern", "four stars", "blue and white", "electronics")
    ),
    Symbol(
        name = "Dell",
        category = "Logos",
        description = "The company name 'DELL' in uppercase letters with a slanted 'E', representing innovation.",
        history = "The slanted 'E' was introduced in 1989 to signify the company's forward-thinking approach.",
        url = "https://en.wikipedia.org/wiki/Dell",
        tags = listOf("uppercase text", "slanted E", "blue color", "technology", "innovation")
    ),
    Symbol(
        name = "Nivea",
        category = "Logos",
        description = "White uppercase letters spelling 'NIVEA' on a deep blue circular background.",
        history = "The logo has maintained its classic design since 1925, symbolizing purity and reliability in skincare.",
        url = "https://en.wikipedia.org/wiki/Nivea",
        tags = listOf("white text", "blue circle", "simple design", "skincare", "purity")
    ),
    Symbol(
        name = "Allianz",
        category = "Logos",
        description = "The company name 'Allianz' accompanied by three vertical bars forming a stylized 'A'.",
        history = "The three bars, introduced in 1977, represent the company's core values of integrity, innovation, and excellence.",
        url = "https://en.wikipedia.org/wiki/Allianz",
        tags = listOf("blue text", "three bars", "stylized A", "insurance", "finance")
    ),
    Symbol(
        name = "Kellogg's",
        category = "Logos",
        description = "A red, cursive wordmark spelling 'Kellogg's', conveying tradition and quality.",
        history = "The signature-style logo has been in use since 1906, reflecting the personal touch of the company's founder, W.K. Kellogg.",
        url = "https://en.wikipedia.org/wiki/Kellogg's",
        tags = listOf("red cursive text", "signature style", "food brand", "breakfast cereals")
    ),
    Symbol(
        name = "John Deere",
        category = "Logos",
        description = "A leaping deer silhouette enclosed in a green and yellow shield.",
        history = "The logo, depicting a deer since 1876, symbolizes agility and the company's agricultural heritage.",
        url = "https://en.wikipedia.org/wiki/John_Deere",
        tags = listOf("leaping deer", "green and yellow", "shield shape", "agriculture", "machinery")
    ),
    Symbol(
        name = "Huawei",
        category = "Logos",
        description = "A red, stylized flower with radiating petals, symbolizing innovation and growth.",
        history = "Introduced in 1987, the logo represents the company's commitment to technological advancement.",
        url = "https://en.wikipedia.org/wiki/Huawei",
        tags = listOf("red flower", "radiating petals", "technology", "innovation", "telecommunications")
    )
)