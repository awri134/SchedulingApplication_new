package schedulingapplication.schedulingapplication.models;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import schedulingapplication.schedulingapplication.utilities.Query;

import java.sql.ResultSet;
import java.sql.SQLException;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Creates an object representation of a Volunteer
 */
public class Volunteer extends Person{
    private String email;
    private String skill;
    private String quote;

    /**
     * @param id The unique identifier of the Volunteer
     * @param name A String representation of the Volunteer name
     * @param email A String representation of the Volunteer email address
     */
    public Volunteer(int id, String name, String email, String skill, String quote) {
        super(id, name);
        this.email = email;
        this.skill = skill;
        this.quote = quote;
        //System.out.println(this.toString());
    }
    public Volunteer(String name, String email, String skill, String quote) {
        super(name);
        this.email = email;
        this.skill = skill;
        this.quote = quote;
    }

    /**
     * @return A String representation of the Volunteer email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email A String representation of the Volunteer email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return A String representation of the Volunteer skill area
     */
    public String getSkill() {
        return skill;
    }

    /**
     * @param skill A String representation of the Volunteer skill area
     */
    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public static ObservableList<Volunteer> getVolunteers() throws SQLException {
        String sql = "SELECT * FROM volunteers";
        Query.queryDb(sql);
        ResultSet vols = Query.getResults();
        ObservableList<Volunteer> vlist = observableArrayList();
        while (vols.next())
        {
            Integer cid = vols.getInt("Volunteer_ID");
            String name = vols.getString("Volunteer_Name");
            String email = vols.getString("Email");
            String skill = vols.getString("SkillArea");
            String quote = vols.getString("Quote");
            Volunteer v = new Volunteer(cid, name, email, skill, quote);
            vlist.add(v);
        }
        return vlist;
    }

    public void giveIntro()
    {
        Alert intro = new Alert(Alert.AlertType.INFORMATION);
        StringBuilder sb = new StringBuilder();
        sb.append("Hi! My name is ");
        sb.append(this.getName());
        sb.append(".\n");
        sb.append(this.getQuote());
        intro.setContentText(String.valueOf(sb));
        intro.setTitle("Introduction");
        intro.setHeaderText(this.getName());
        intro.show();
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ");
        sb.append(this.getId());
        sb.append("\nName: ");
        sb.append(this.getName());
        sb.append("\nEmail: ");
        sb.append(this.getEmail());
        sb.append("\nSkill Area: ");
        sb.append(this.getSkill());
        return String.valueOf(sb);
    }
}