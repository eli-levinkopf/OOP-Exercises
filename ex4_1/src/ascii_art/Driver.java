package ascii_art;

import ascii_art.img_to_char.CharRenderer;

public class Driver {
    public static void main(String[] args){
        boolean[][] cs = CharRenderer.getImg('c', 16, "Ariel");
        CharRenderer.printBoolArr(cs);
    }
}
