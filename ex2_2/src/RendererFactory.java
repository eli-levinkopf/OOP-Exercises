public class RendererFactory {

    public Renderer buildRenderer(String rendererType, int size){
        return switch (rendererType){
            case "console" -> new ConsoleRenderer(size);
            case "none" -> new VoidRenderer();
            default -> null;
        };
    }
}
