package schedulingapplication.schedulingapplication.utilities;

import javafx.collections.ObservableList;
import schedulingapplication.schedulingapplication.models.Volunteer;

import java.sql.ResultSet;
import java.sql.SQLException;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Utility class for handling Volunteer related functions
 */
public abstract class VolunteersHelper
{
    /**
     * Gets all Volunteers from the volunteers table
     * @return An Observable list of Volunteers
     * @throws SQLException
     */
    public static ObservableList<Volunteer> getVolunteers() throws SQLException {
        String sql = "SELECT * FROM volunteers ORDER BY Volunteer_ID ASC";
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

    public static void updateVolunteer(Volunteer v) throws SQLException {
        StringBuilder sqlUpdate = new StringBuilder();
        sqlUpdate.append("UPDATE volunteers SET Volunteer_Name = '");
        sqlUpdate.append(v.getName());
        sqlUpdate.append("', Email = '");
        sqlUpdate.append(v.getEmail());
        sqlUpdate.append("', SkillArea = '");
        sqlUpdate.append(v.getSkill());
        sqlUpdate.append("', Quote = '");
        sqlUpdate.append(v.getQuote());
        sqlUpdate.append("' WHERE Volunteer_ID = ");
        sqlUpdate.append(v.getId());
        Query.queryDb(sqlUpdate.toString());

    }

    /**
     * Insert a client into the clients table
     * @param v The Client to add to the clients table
     * @throws SQLException
     */
    public static void addVolunteer(Volunteer v) throws SQLException {
        StringBuilder sqlInsert = new StringBuilder();
        sqlInsert.append("INSERT INTO volunteers (Volunteer_Name, Email, SkillArea, Quote) VALUES('");
        sqlInsert.append(v.getName());
        sqlInsert.append("', '");
        sqlInsert.append(v.getEmail());
        sqlInsert.append("', '");
        sqlInsert.append(v.getSkill());
        sqlInsert.append("', '");
        sqlInsert.append(v.getQuote());
        sqlInsert.append("')");

        Query.queryDb(sqlInsert.toString());
    }

    /**
     * Delete a client from the clients table
     * @param v The client to delete
     * @throws SQLException
     */
    public static void deleteVolunteer(Volunteer v) throws SQLException {

        //AppointmentsHelper.deleteAppointments(v);
        StringBuilder volDelete = new StringBuilder();
        volDelete.append("DELETE FROM volunteers WHERE Volunteer_ID = ");
        volDelete.append(v.getId());
        try
        {
            Query.queryDb(volDelete.toString());
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }


    public static ObservableList<Volunteer> searchVolunteers(String q) throws SQLException {
        ObservableList<Volunteer> volunteers = getVolunteers();
        ObservableList<Volunteer> results = observableArrayList();
        for (Volunteer v: volunteers) {
            if(hasQuery(v, q))
            {
                results.add(v);
            }
        }

        System.out.println("SEARCH VOLUNTEERS!");
        return results;
    }

    public static boolean hasQuery(Volunteer v, String query)
    {
        boolean found = false;
        String q = query.toUpperCase();
        if(String.valueOf(v.getId()).toUpperCase().indexOf(q) >= 0){found = true;}
        else if(v.getName().toUpperCase().indexOf(q) >= 0){found = true;}
        else if(v.getEmail().toUpperCase().indexOf(q) >= 0){found = true;}
        else if(v.getSkill().toUpperCase().indexOf(q) >= 0){found = true;}
        else if(v.getQuote().toUpperCase().indexOf(q) >= 0){found = true;}
        return found;
    }
}
