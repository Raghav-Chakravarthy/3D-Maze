public class BackendEngine {
    private ViewEngine viewEngine;
    public BackendEngine(){
        viewEngine = new ViewEngine();
    }
    public static void main(String[] args){
        BackendEngine backend = new BackendEngine();
    }
}
