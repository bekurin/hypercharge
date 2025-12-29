package com.brawl.stars.hypercharge.domain.support

enum class BrawlerType(val displayName: String) {
    // Starting Brawlers
    SHELLY("Shelly"),

    // Trophy Road
    NITA("Nita"),
    COLT("Colt"),
    BULL("Bull"),
    JESSIE("Jessie"),
    BROCK("Brock"),
    DYNAMIKE("Dynamike"),
    BO("Bo"),
    TICK("Tick"),
    _8BIT("8-Bit"),
    EMZ("Emz"),
    STU("Stu"),

    // Rare
    EL_PRIMO("El Primo"),
    BARLEY("Barley"),
    POCO("Poco"),
    ROSA("Rosa"),

    // Super Rare
    RICO("Rico"),
    DARRYL("Darryl"),
    PENNY("Penny"),
    CARL("Carl"),
    JACKY("Jacky"),

    // Epic
    PIPER("Piper"),
    PAM("Pam"),
    FRANK("Frank"),
    BIBI("Bibi"),
    BEA("Bea"),
    NANI("Nani"),
    EDGAR("Edgar"),
    GRIFF("Griff"),
    GROM("Grom"),
    BONNIE("Bonnie"),
    GALE("Gale"),
    COLETTE("Colette"),
    BELLE("Belle"),
    ASH("Ash"),
    LOLA("Lola"),
    SAM("Sam"),
    MANDY("Mandy"),
    MAISIE("Maisie"),
    HANK("Hank"),
    PEARL("Pearl"),
    LARRY_AND_LAWRIE("Larry & Lawrie"),
    ANGELO("Angelo"),
    BERRY("Berry"),
    SHADE("Shade"),

    // Mythic
    MORTIS("Mortis"),
    TARA("Tara"),
    GENE("Gene"),
    MAX("Max"),
    MR_P("Mr. P"),
    SPROUT("Sprout"),
    BYRON("Byron"),
    SQUEAK("Squeak"),
    LOU("Lou"),
    RUFFS("Colonel Ruffs"),
    BUZZ("Buzz"),
    FANG("Fang"),
    EVE("Eve"),
    JANET("Janet"),
    OTIS("Otis"),
    BUSTER("Buster"),
    GRAY("Gray"),
    R_T("R-T"),
    WILLOW("Willow"),
    DOUG("Doug"),
    CHUCK("Chuck"),
    CHARLIE("Charlie"),
    MICO("Mico"),
    MELODIE("Melodie"),
    LILY("Lily"),
    CLANCY("Clancy"),
    MOE("Moe"),
    JUJU("Juju"),

    // Legendary
    SPIKE("Spike"),
    CROW("Crow"),
    LEON("Leon"),
    SANDY("Sandy"),
    AMBER("Amber"),
    MEG("Meg"),
    SURGE("Surge"),
    CHESTER("Chester"),
    CORDELIUS("Cordelius"),
    KIT("Kit"),
    DRACO("Draco"),
    KENJI("Kenji"),
    ;

    companion object {
        fun fromId(id: String): BrawlerType? = entries.find { it.name == id }

        fun getDisplayName(id: String): String = fromId(id)?.displayName ?: id
    }
}
