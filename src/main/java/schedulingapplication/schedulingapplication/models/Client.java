package schedulingapplication.schedulingapplication.models;

import java.sql.Timestamp;

/**
 * Creates an object representation of a Client
 */
public class Client extends Person
{
    private String address;
    private String zip;
    private String phone;
    private int divId;
    private String division;
    private int countryId;
    private String country;
    private String updatedBy;
    private Timestamp updatedDate;
    private String createdBy;
    private Timestamp createdDate;

    /**
     * @param id The unique identifier of the Client
     * @param name A String representation of the Client name
     * @param address A String representation of the Client address
     * @param zip A String representation of the Client postal code
     * @param phone A String representation of the Client phone number
     * @param divId The identifier for the Client lower-level division - State/Province ID
     * @param division A String representation of the division with provided divId
     * @param countryId The identifier for the country
     * @param country A String representation of the country with the given id
     * @param updatedBy A String representation of who or what last updated the Client
     * @param updatedDate A Timestamp representation of the date and time the Client was last updated
     */
    public Client(int id, String name, String address, String zip, String phone, int divId, String division, int countryId, String country, String updatedBy, Timestamp updatedDate) {
        super(id, name);
        this.address = address;
        this.zip = zip;
        this.phone = phone;
        this.divId = divId;
        this.division = division;
        this.countryId = countryId;
        this.country = country;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
    }

    /**
     * @param name A String representation of the Client name
     * @param address A String representation of the Client address
     * @param zip A String representation of the Client postal code
     * @param phone A String representation of the Client phone number
     * @param divId The identifier for the Client lower-level division - State/Province ID
     * @param division A String representation of the division with provided divId
     * @param countryId The identifier for the country
     * @param country A String representation of the country with the given id
     * @param updatedBy A String representation of who or what last updated the Client
     * @param updatedDate A Timestamp representation of the date and time the Client was last updated
     * @param createdBy A String representation of who or what created the Client
     * @param createdDate A Timestamp representation of the date and time the Client was created
     */
    public Client(String name, String address, String zip, String phone, int divId, String division, int countryId, String country, String updatedBy, Timestamp updatedDate, String createdBy, Timestamp createdDate) {
        super(name);
        this.address = address;
        this.zip = zip;
        this.phone = phone;
        this.divId = divId;
        this.division = division;
        this.countryId = countryId;
        this.country = country;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
    }


    /**
     * @return  A String representation of the Client address
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * @param address  A String representation of the Client address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return  A String representation of the Client postal code
     */
    public String getZip() {
        return this.zip;
    }

    /**
     * @param zip  A String representation of the Client postal code
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * @return  A String representation of the Client phone number
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * @param phone A String representation of the Client phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return The id of the lower-level division the Client is associated with
     */
    public int getDivId() {
        return this.divId;
    }

    /**
     * @param divId The id of the lower-level division the Client is associated with
     */
    public void setDivId(int divId) {
        this.divId = divId;
    }

    /**
     * @return A String representation of the lower-level division the Client is associated with
     */
    public String getDivision() {
        return this.division;
    }

    /**
     * @param division A String representation of the lower-level division the Client is associated with
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * @return A String representation of the country the Client is associated with
     */
    public String getCountry() {
        return this.country;
    }

    /**
     * @param country  A String representation of the country the Client is associated with
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return A String representation of who or what last updated the Client
     */
    public String getUpdatedBy() {
        return this.updatedBy;
    }

    /**
     * @param updatedBy A String representation of who or what last updated the Client
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return  A Timestamp representation of the date and time the Client was last updated
     */
    public Timestamp getUpdatedDate() {
        return this.updatedDate;
    }

    /**
     * @param updatedDate  A Timestamp representation of the date and time the Client was last updated
     */
    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return  The id of the country the Client is associated with
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * @param countryId The id of the country the Client is associated with
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * @return A String representation of who or what created the Client
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy A String representation of who or what created the Client
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return  A Timestamp representation of the date and time the Client was created
     */
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate A Timestamp representation of the date and time the Client was created
     */
    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }
}
