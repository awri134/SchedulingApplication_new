package schedulingapplication.schedulingapplication.models;

import java.sql.Timestamp;

/**
 * Represents an Appointment
 */
public class Appointment
{
    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp start;
    private Timestamp end;
    private String updatedBy;
    private Timestamp updatedDate;
    private String createdBy;
    private Timestamp createdDate;
    private int clientId;
    private int userId;
    private int volunteerId;
    private String clientName;
    private String volName;
    private String userName;

    /**
     * Creates an appointment with the specified appointment information retrieved from the database
     * @param id The id of the appointment
     * @param title The title of the appointment
     * @param description The description of the appointment
     * @param location The location of the appointment
     * @param type The type of the appointment
     * @param start The start date and time of the appointment
     * @param end The end date and time of the appointment
     * @param updatedBy Who or what last updated the appointment - Any String value is valid for this
     * @param updatedDate The date and time the appointment was last updated
     * @param createdBy Who or what created the appointment - Any String value is valid for this
     * @param createdDate The date and time the appointment was last created
     * @param clientId The client id  of the client associated with the Appointment
     * @param userId The user id of the user associated with the Appointment
     * @param volunteerId The volunteer id of the volunteer associated with the Appointment
     * @param clientName The name of the client associated with the Appointment
     * @param userName The name of the user associated with the Appointment
     * @param volName The name of the volunteer associated with the Appointment
     */
    public Appointment(int id, String title, String description, String location, String type, Timestamp start, Timestamp end, String updatedBy, Timestamp updatedDate, String createdBy, Timestamp createdDate, int clientId, int userId, int volunteerId, String clientName, String userName, String volName)
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.clientId = clientId;
        this.userId = userId;
        this.volunteerId = volunteerId;
        this.clientName = clientName;
        this.volName = volName;
        this.userName = userName;

    }

    /**
     * Creates an appointment with the specified appointment information
     * @param title The title of the appointment
     * @param description The description of the appointment
     * @param location The location of the appointment
     * @param type The type of the appointment
     * @param start The start date and time of the appointment
     * @param end The end date and time of the appointment
     * @param updatedBy Who or what last updated the appointment - Any String value is valid for this
     * @param updatedDate The date and time the appointment was last updated
     * @param createdBy Who or what created the appointment - Any String value is valid for this
     * @param createdDate The date and time the appointment was last created
     * @param clientId The client id  of the client associated with the Appointment
     * @param userId The user id of the user associated with the Appointment
     * @param volunteerId The volunteer id of the volunteer associated with the Appointment
     */
    public Appointment(String title, String description, String location, String type, Timestamp start, Timestamp end, String updatedBy, Timestamp updatedDate, String createdBy, Timestamp createdDate, int clientId, int userId, int volunteerId)
    {
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.clientId = clientId;
        this.userId = userId;
        this.volunteerId = volunteerId;
    }


    /**
     * @return The unique identifier for the Appointment
     */
    public int getId() {
        return id;
    }

    /**
     * @param id The id of the Appointment - Must be Unique
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return A String representation of the Appointment title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title A String representation of the Appointment title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return A String representation of the Appointment description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description A String representation of the Appointment description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return A String representation of the Appointment location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location A String representation of the Appointment location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return A String representation of the Appointment type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type A String representation of the Appointment type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return A Timestamp representation of the Appointment start time in Local Time
     */
    public Timestamp getStart()
    {
        return this.start;
    }

    /**
     * @param start A Timestamp representation of the Appointment start time in Local Time
     */
    public void setStart(Timestamp start) {
        this.start = start;
    }

    /**
     * @return A Timestamp representation of the Appointment end time
     */
    public Timestamp getEnd() {
        return end;
    }

    /**
     * @param end A Timestamp representation of the Appointment end time
     */
    public void setEnd(Timestamp end) {
        this.end = end;
    }

    /**
     * @return The id of the Client associated to this appointment
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * @param clientId The id of the Client associated to this appointment
     */
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    /**
     * @return The id of the User associated to this appointment
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId The id of the User associated to this appointment
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return The id of the Volunteer associated to this appointment
     */
    public int getVolunteerId() {
        return volunteerId;
    }

    /**
     * @param volunteerId The id of the Volunteer associated to this appointment
     */
    public void setVolunteerId(int volunteerId) {
        this.volunteerId = volunteerId;
    }

    /**
     * @return A String representation of the name of the Client associated to this appointment
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * @param clientName A String representation of the name of the Client associated to this appointment
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * @return A String representation of the name of the Volunteer associated to this appointment
     */
    public String getVolName() {
        return volName;
    }

    /**
     * @param volName A String representation of the name of the Volunteer associated to this appointment
     */
    public void setVolName(String volName) {
        this.volName = volName;
    }

    /**
     * @return A String representation of the name of the User associated to this appointment
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName A String representation of the name of the User associated to this appointment
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return A String representing who or what last updated the appointment
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy A String representing who or what last updated the appointment - Any String value is valid for this
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return  The Timestamp for the date the appointment was last updated
     */
    public Timestamp getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate  The Timestamp for the date the appointment was last updated
     */
    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return A String representing who or what created the appointment
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy A String representing who or what created the appointment - Any String value is valid for this
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return  The Timestamp for the date the appointment was created
     */
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate The Timestamp for the date the appointment was created
     */
    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }
}
