import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseClickListener implements MouseListener {

    Point mouseEvent;
	
	public Point getMouseOutput() {
		return mouseEvent;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		mouseEvent = e.getPoint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

}
