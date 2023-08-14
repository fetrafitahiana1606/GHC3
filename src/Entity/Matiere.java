package Entity;

public abstract class Matiere {
    protected String num_mat;
    protected String designation;
    protected int nbheure;

    public Matiere(String num_mat, String designation, int nbheure) {
        this.num_mat = num_mat;
        this.designation = designation;
        this.nbheure = nbheure;
    }

    public String getNum_mat() {
        return num_mat;
    }

    public void setNum_mat(String num_mat) {
        this.num_mat = num_mat;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getNbheure() {
        return nbheure;
    }

    public void setNbheure(int nbheure) {
        this.nbheure = nbheure;
    }


}
