public class RendererFactory {

    // =========== constants ===========
    public static final String NONE = "none";
    public static final String CONSOLE = "console";

    public Renderer buildRenderer(String rendererType, int size){
        switch (rendererType){
            case CONSOLE:
                return new ConsoleRenderer(size);
            case NONE:
                return new VoidRenderer();
        }
        return null;
    }
}
