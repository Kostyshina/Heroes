package katsapov.heroes.data.entitiy;

public class Hero {
    private String name;
    private String gender;
    private String culture;
    private String born;
    private String die;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String realName) {
        this.gender = realName;
    }

    public String getCulture() {
        return culture;
    }

    public void setCulture(String createdBy) {
        this.culture = createdBy;
    }

    public String getDie() {
        return die;
    }

    public void setDie(String die) {
        this.die = die;
    }

    public String getBorn() {
        return born;
    }

    public void setBorn(String born) {
        this.born = born;
    }
}