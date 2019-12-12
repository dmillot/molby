package entity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class YearGroup {

    public YearGroup() {

    }

    //region ID
    public int getId() {return id; }
    public void setId(int id) { this.id = id; }
    //endregion

    //region Label
    public String getLabel() {
        return this.label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    //endregion

    //region DateStart
    public Date getDateStart() {return dateStart; }
    public void setDateStart(Date dateStart) { this.dateStart = dateStart; }
    //endregion

    //region DateEnd
    public Date getDateEnd() {return dateEnd; }
    public void setDateEnd(Date dateEnd) { this.dateEnd = dateEnd; }
    //endregion

    //region Users
    public ArrayList<User> getUsers() {return users; }
    public void setUsers( ArrayList<User> users) { this.users = users; }
    //endregion

    private int id;
    private String label;
    private ArrayList<User> users;
    private Date dateStart;
    private Date dateEnd;

    public YearGroup(String label, ArrayList<User> users, Date dateStart, Date dateEnd)
    {
        this.label = label;
        this.users = users;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public YearGroup(int id, String label, ArrayList<User> users, Date dateStart, Date dateEnd)
    {
        this.id = id;
        this.label = label;
        this.users = users;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }
}
