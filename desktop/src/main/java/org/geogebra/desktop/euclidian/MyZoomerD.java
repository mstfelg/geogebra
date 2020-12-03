package org.geogebra.desktop.euclidian;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import org.geogebra.common.euclidian.CoordSystemAnimation;
import org.geogebra.common.euclidian.CoordSystemInfo;
import org.geogebra.common.euclidian.EuclidianView;

public class MyZoomerD extends CoordSystemAnimation implements ActionListener {
	protected Timer timer; // for animation

	public MyZoomerD(EuclidianView view, CoordSystemInfo coordSystemInfo) {
		super(view, coordSystemInfo);
		timer = new Timer(DELAY, this);
	}

	@Override
	protected void stopTimer() {
		timer.stop();
	}

	@Override
	protected boolean hasTimer() {
		return timer != null;
	}

	@Override
	public synchronized void actionPerformed(ActionEvent e) {
		step();
	}

	@Override
	protected void startTimer() {
		timer.start();
	}

}