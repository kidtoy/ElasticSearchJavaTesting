package Insert;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.search.SearchResponse;

public class MeuListener<T> implements ActionListener<T> {

    private T response;
    private Exception e;

    @Override
    public void onResponse(T response) {
        if (this.response == null){
            this.response = response;
        }
    }

    @Override
    public void onFailure(Exception e) {
        this.e = e;
    }

    public T waitAndRetrieve(){
        while(e == null){
            if (response == null){
                try{
                    Thread.sleep(500);
                }catch (InterruptedException ie ){
                    System.out.println("Error in thread sleep on lister : "+ ie.getMessage());
                    break;
                }
            }else{
                return response;
            }
        }
        return null;
    }
}
