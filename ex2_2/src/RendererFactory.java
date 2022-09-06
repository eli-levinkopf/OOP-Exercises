public class RendererFactory {

    public Renderer buildRenderer(String rendererType){
        return switch (rendererType){
            case "console" -> new ConsoleRenderer();
            case "none" -> new VoidRenderer();
            default -> null;
        };
    }
}
