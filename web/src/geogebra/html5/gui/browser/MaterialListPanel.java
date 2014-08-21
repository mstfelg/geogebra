package geogebra.html5.gui.browser;

import geogebra.common.move.ggtapi.models.Material;
import geogebra.html5.gui.ResizeListener;
import geogebra.html5.gui.laf.GLookAndFeel;
import geogebra.html5.main.AppWeb;
import geogebra.web.main.AppW;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * 
 * The preview panel for the {@link MaterialListElement materials}
 *
 */
public class MaterialListPanel extends FlowPanel implements ResizeListener {
	
	protected List<MaterialListElement> materials = new ArrayList<MaterialListElement>();
	protected AppWeb app;
	
	/**
	 * @param app AppWeb
	 */
	public MaterialListPanel(final AppWeb app) {
		this.app = app;
		this.setStyleName("materialListPanel");
		this.setHeight(Window.getClientHeight() - GLookAndFeel.BROWSE_HEADER_HEIGHT +"px");
	}
	
	/**
	 * adds the new materials (matList)
	 * @param matList List<Material>
	 */
	public void setMaterials(final List<Material> matList) {
		for (final Material mat : matList) {
			addMaterial(mat);
		}
	}

	/**
	 * clears the list of existing {@link MaterialListElement materials} and the {@link MaterialListPanel preview-panel}
	 */
	public void clearMaterials() {
		this.materials.clear();
		this.clear();
	}
	
	/**
	 * adds the given material to the list of {@link MaterialListElement materials} and the preview-panel
	 * 
	 * @param mat {@link Material}
	 */
	public void addMaterial(final Material mat) {
		final MaterialListElement preview = ((AppW)app).getLAF().getMaterialElement(mat, this.app);
		this.materials.add(preview);
		this.add(preview);
	}

	/**
	 * removes the given material from the list of {@link MaterialListElement materials} and the preview-panel
	 * @param mat {@link Material}
	 */
	public void removeMaterial(final Material mat) {
		for(final MaterialListElement matElem : this.materials) {
			if (matElem.getMaterial().equals(mat)) {
				this.materials.remove(matElem);
				this.remove(matElem);
			}
		}
	}
	
	/**
	 * 
	 */
	public void setLabels() {
		for (final MaterialListElement e : this.materials) {
			e.setLabels();
		}
	}

	@Override
	public void onResize() {
		this.setHeight(Window.getClientHeight() - GLookAndFeel.BROWSE_HEADER_HEIGHT +"px");
		for (final MaterialListElement elem : this.materials) {
			elem.onResize();
		}
	}
}
