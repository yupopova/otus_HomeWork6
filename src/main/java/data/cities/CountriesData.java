package data.cities;

public enum CountriesData {

    RUSSIA ("Россия");

    private String name;

    CountriesData(String name) {
        this.name = name;
    }

    public String getNameCountry() {
        return this.name;
    }

}
