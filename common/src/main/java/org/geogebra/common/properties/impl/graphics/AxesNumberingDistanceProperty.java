package org.geogebra.common.properties.impl.graphics;

import org.geogebra.common.euclidian.EuclidianView;
import org.geogebra.common.kernel.Kernel;
import org.geogebra.common.main.Localization;
import org.geogebra.common.main.error.ErrorHelper;
import org.geogebra.common.main.settings.EuclidianSettings;
import org.geogebra.common.properties.AbstractProperty;
import org.geogebra.common.properties.BooleanProperty;

/**
 * This property controls the distance of the axes numbering.
 */
public class AxesNumberingDistanceProperty extends AbstractProperty implements BooleanProperty {

    private EuclidianSettings euclidianSettings;
    private Kernel kernel;
    private EuclidianView euclidianView;

    /**
     * Constructs an Axes numbering distance property.
     *
     * @param localization localization for the title
     */
    AxesNumberingDistanceProperty(Localization localization, EuclidianSettings
            euclidianSettings, EuclidianView euclidianView, Kernel kernel) {
        super(localization, "Automatic");
        this.euclidianSettings = euclidianSettings;
        this.kernel = kernel;
        this.euclidianView = euclidianView;
    }

    @Override
    public boolean getValue() {
        boolean[] axesAutomaticDistances = euclidianSettings.getAutomaticAxesNumberingDistances();

        for (boolean auto : axesAutomaticDistances) {
            if (!auto) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void setValue(boolean automatic) {
        if (automatic) {
            setAutoDistance();
        } else {
            setCustomDistance();
        }
    }

    private void setCustomDistance() {
        double[] axesDistances = euclidianView.getAxesNumberingDistances();
        for (int i = 0; i < axesDistances.length; i++) {
            euclidianSettings.setAxisNumberingDistance(i, kernel.getAlgebraProcessor()
                    .evaluateToNumeric("" + axesDistances[i] / 2, ErrorHelper.silent()));
        }
    }

    private void setAutoDistance() {
        int length = euclidianSettings.getAutomaticAxesNumberingDistances().length;
        for (int i = 0; i < length; i++) {
            euclidianSettings.setAutomaticAxesNumberingDistance(true, i, true);
        }
    }
}
