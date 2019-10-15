import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Task {
    private int id;
    private String title;
    private Date createdDate;
    private Date dueDate;
    private String project;
    private Status status;

    /**
     * toList method casting all the values of the fields (title, createdDate, dueDate, project, status) as a list of
     * Strings.
     * note that the (id) field is excluded.
     * @return List of Strings
     */
    public List<String> toList(){ //without id
        List<String> listOfFields = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        listOfFields.add(getTitle());
        listOfFields.add(dateFormat.format(getCreatedDate()));
        listOfFields.add(dateFormat.format(getDueDate()));
        listOfFields.add(getProject());
        listOfFields.add(getStatus().toString());

        return listOfFields;
    }






    public void setId(int id){
        this.id = id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setCreatedDate(Date createdDate){
        this.createdDate = createdDate;
    }

    public void setDueDate(Date dueDate){
        this.dueDate = dueDate;
    }

    public void setProject(String project){
        this.project = project;
    }

    public void setStatus(Status status){
        this.status = status;
    }






    public int getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public Date getCreatedDate(){
        return createdDate;
    }

    public Date getDueDate(){
        return dueDate;
    }

    public String getProject(){
        return project;
    }

    public Status getStatus(){
        return status;
    }

}
