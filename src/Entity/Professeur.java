package Entity;

public abstract class Professeur {
    protected String matricule;
    protected String nom;
    protected int tauxhoraire;

    public Professeur(String matricule, String nom, int tauxhoraire) {
        this.matricule = matricule;
        this.nom = nom;
        this.tauxhoraire = tauxhoraire;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getTauxhoraire() {
        return tauxhoraire;
    }

    public void setTauxhoraire(int tauxhoraire) {
        this.tauxhoraire = tauxhoraire;
    }


}
