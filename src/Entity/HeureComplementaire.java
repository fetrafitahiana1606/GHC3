package Entity;

public class HeureComplementaire {
    protected String matricule;
    protected String nom;
    protected int montant;

    public HeureComplementaire(String matricule, String nom, int montant) {
        this.matricule = matricule;
        this.nom = nom;
        this.montant = montant;
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

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }
}
