package orodja.export;

import sifranti.Letnik;
import sifranti.Predmet;
import sifranti.StudijskiProgram;
import sifranti.StudijskoLeto;

public class Metadata {

    private Predmet subject;
    private Letnik yearOfStudy;
    private StudijskoLeto studyYear;
    private StudijskiProgram studyProgramme;

    public Predmet getSubject() {
        return subject;
    }

    public void setSubject(Predmet subject) {
        this.subject = subject;
    }

    public Letnik getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(Letnik yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public StudijskoLeto getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(StudijskoLeto studyYear) {
        this.studyYear = studyYear;
    }

    public StudijskiProgram getStudyProgramme() {
        return studyProgramme;
    }

    public void setStudyProgramme(StudijskiProgram studyProgramme) {
        this.studyProgramme = studyProgramme;
    }
}
