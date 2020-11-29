package io.cucumber.manager;

public class AssetsInfo {

    private final String block;
    private final String zone;
    private final String background;
    private final String menuBackground;

    public AssetsInfo(String block, String zone,
                      String background, String menuBackground) {
        this.block = block;
        this.zone = zone;
        this.background = background;
        this.menuBackground = menuBackground;
    }

    public String getBlock() {
        return block;
    }

    public String getZone() {
        return zone;
    }

    public String getBackground() {
        return background;
    }

    public String getMenuBackground() {
        return menuBackground;
    }
}
