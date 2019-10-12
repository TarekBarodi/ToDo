import java.util.Date;

public class Task {
    private int id;
    private String title;
    private Date createdDate;
    private Date dueDate;
    private String project;
    private Status status;











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
