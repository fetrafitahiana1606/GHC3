package Entity;

public abstract class VolumeHoraire {
    protected String matricule;
    protected String num_mat;
    protected int annee;

    public VolumeHoraire(String matricule, String num_mat, int annee) {
        this.matricule = matricule;
        this.num_mat = num_mat;
        this.annee = annee;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNum_mat() {
        return num_mat;
    }

    public void setNum_mat(String num_mat) {
        this.num_mat = num_mat;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

}
