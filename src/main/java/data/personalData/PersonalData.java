package data.personalData;

public enum PersonalData {

    FNAME("fname"),
    FNAME_LATIN("fname_latin"),
    LNAME("lname"),
    LNAME_LATIN("lname_latin"),
    BLOG_NAME("blog_name"),
    DATE_OF_BIRTH("date_of_birth"),
    TELEGRAM("Тelegram"),
    WHATSAPP("WhatsApp");

    private String name;

    PersonalData (String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}