package base.graphics;

public interface Drawable {
	public GamePanel.PanelType getPanelToDrawOn();
	
	public void Draw();
}
