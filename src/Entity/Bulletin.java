package Entity;

public class Bulletin {
    protected String designation;
    protected int nbheure;
    protected int montant;

    public Bulletin(String designation, int nbheure, int montant) {
        this.designation = designation;
        this.nbheure = nbheure;
        this.montant = montant;
    }

    public String getDesignation () { return designation;}
    public void setDesignation (String designation) {this.designation = designation;}
    public int getNbheure () {return nbheure;}
    public void setNbheure (int nbheure) {this.nbheure = nbheure;}
    public int getMontant () {return this.montant = montant;}
    public void setMontant(int montant) { this.montant = montant;}
}


