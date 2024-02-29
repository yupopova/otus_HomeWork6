package data.englishLevel;

public enum EnglishLevel implements IEnglishLevel{

    BEGINNER("Начальный уровень (Beginner)"),
    ELEMENTARY("Элементарный уровень (Elementary)"),
    PREINTERMEDIATE("Ниже среднего (Pre-Intermediate)"),
    INTERMEDIATE("Средний (Intermediate)");

    private String nameLevelEnglish;

    EnglishLevel(String nameLevelEnglish) {
        this.nameLevelEnglish = nameLevelEnglish; }

    @Override
    public String getEnglishLevel() {
        return nameLevelEnglish; }

}
