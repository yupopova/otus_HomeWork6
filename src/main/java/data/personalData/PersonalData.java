package data.personalData;

public enum PersonalData {

    FNAME("fname"),
    FNAME_LATIN("fname_latin"),
    LNAME("lname"),
    LNAME_LATIN("lname_latin"),
    BLOG_NAME("blog_name"),
    DATE_OF_BIRTH("date_of_birth"),
    CONTACT0VALUE("contact-0-value"),
    CONTACT1VALUE("contact-1-value");


    private String name;

    PersonalData (String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}