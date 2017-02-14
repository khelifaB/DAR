package application;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class WebJouet {
	private List<Point> points;

	public WebJouet() {
		points = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			points.add(new Point(i, i));
		}
	}

	public List<Point> getListPoint() {
		return this.points;
	}

}
