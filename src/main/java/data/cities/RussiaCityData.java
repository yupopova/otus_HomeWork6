package data.cities;

public enum RussiaCityData implements ICityData {

    MOSCOW("Москва", CountriesData.RUSSIA),
    PITER("Санкт-Петербург", CountriesData.RUSSIA),
    TAGANROG("Таганрог", CountriesData.RUSSIA);

    private String name;

    private CountriesData countriesData;

    RussiaCityData(String name, CountriesData countriesData) {
        this.name = name;
        this.countriesData = countriesData;
    }

    public String getName() {
        return name;
    }

    public CountriesData getCountriesData() {
        return countriesData;
    }

}
