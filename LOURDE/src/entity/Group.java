package entity;

import java.util.ArrayList;
import java.util.Date;

public class Group {

    public Group() {

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

    //region AvailableXp
    public int getAvailableXp() {return availableXp; }
    public void setAvailableXp(int availableXp) { this.availableXp = availableXp; }
    //endregion

    //region Points to give
    public int getPointsToGive() {return pointsToGive; }
    public void setPointsToGive(int pointsToGive) { this.pointsToGive = pointsToGive; }
    //endregion

    //region TotalXp
    public int getTotalXp() {return totalXp; }
    public void setTotalXp(int totalXp) { this.totalXp = totalXp; }
    //endregion

    //region DateStart
    public java.sql.Date getDateStart() {return dateStart; }
    public void setDateStart(java.sql.Date dateStart) { this.dateStart = dateStart; }
    //endregion

    //region DateEnd
    public java.sql.Date getDateEnd() {return dateEnd; }
    public void setDateEnd(java.sql.Date dateEnd) { this.dateEnd = dateEnd; }
    //endregion

    //region Users
    public ArrayList<User> getUsers() {return users; }
    public void setUsers( ArrayList<User> users) { this.users = users; }
    //endregion

    private int id;
    private String label;
    private ArrayList<User> users;
    private int availableXp;
    private int pointsToGive;
    private int totalXp;
    private java.sql.Date dateStart;
    private java.sql.Date dateEnd;

    public Group(String label, ArrayList<User> users, int availableXp, int pointsToGive,  int totalXp, java.sql.Date dateStart, java.sql.Date dateEnd)
    {
        this.label = label;
        this.users = users;
        this.availableXp = availableXp;
        this.pointsToGive = pointsToGive;
        this.totalXp = totalXp;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public Group(int id, String label, ArrayList<User> users, int availableXp, int pointsToGive,  int totalXp, java.sql.Date dateStart, java.sql.Date dateEnd)
    {
        this.id = id;
        this.label = label;
        this.users = users;
        this.availableXp = availableXp;
        this.pointsToGive = pointsToGive;
        this.totalXp = totalXp;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }
}
