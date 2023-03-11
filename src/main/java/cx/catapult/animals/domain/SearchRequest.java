package cx.catapult.animals.domain;

public class SearchRequest {
    private String name;

    private String description;

    public SearchRequest(String name, String description){
        this.name = name;
        this.description = description;
    }
    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }

}
