public class RendererFactory {

    public Renderer buildRenderer(String rendererType, int size){
        switch (rendererType){
            case "console":
                return new ConsoleRenderer(size);
            case "none":
                return new VoidRenderer();
        }
        return null;
    }
}
