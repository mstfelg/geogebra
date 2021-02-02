package org.geogebra.common.kernel.interval;

import static java.lang.Math.PI;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.geogebra.common.BaseUnitTest;
import org.geogebra.common.euclidian.plot.interval.PlotterUtils;
import org.geogebra.common.kernel.geos.GeoFunction;
import org.junit.Test;

public class IntervalAsymtotesTest extends BaseUnitTest {

	@Test
	public void tanX() {
		IntervalTupleList result = functionValues("tan(x)", -PI / 4, 3 * PI / 4, -10, 10);
		assertTrue(result.get(74).isAsymptote());
	}

	@Test
	public void tanXZoomedOut() {
		IntervalTupleList result = functionValues("tan(x)", -PI / 4, 6 * PI / 4, -35, 20);
		assertTrue(result.get(74).isAsymptote());
	}

	@Test
	public void cotX() {
		IntervalTupleList result = functionValues("cot(x)", 0, PI, -9, 9);
		assertTrue(result.get(0).y().isUndefined()
				&& result.get(100).y().isUndefined());
	}

	@Test
	public void secCscXInverseCutOff() {
		IntervalTupleList result = functionValues("1/sec(csc(x))", -2.9, 2.9, -8, 8);
		List<Integer> cutOffIndexes = Arrays.asList(7, 38, 61, 92);
		for (int index: cutOffIndexes) {
			assertFalse(result.get(index).y().isWhole());
		}
	}

	@Test
	public void sqrtXInverse() {
		GeoFunction function = add("sqrt(1/x)");
		IntervalTuple range = PlotterUtils.newRange(0, 10, -8, 8);
		IntervalFunctionSampler sampler =
				new IntervalFunctionSampler(function, range, 100);
		IntervalTupleList result = sampler.result();
		assertEquals(new Interval(3.1622776016, Double.POSITIVE_INFINITY), result.get(0).y());
	}

	@Test
	public void minusSqrtXInverse() {
		GeoFunction function = add("-sqrt(1/x)");
		IntervalTuple range = PlotterUtils.newRange(0, 10, -8, 8);
		IntervalFunctionSampler sampler =
				new IntervalFunctionSampler(function, range, 100);
		IntervalTupleList result = sampler.result();
		assertEquals(new Interval(Double.NEGATIVE_INFINITY, -3.1622776016), result.get(0).y());
	}

	@Test
	public void squareRootOfTanInverse() {
		GeoFunction function = add("1/sqrt(tan(x))");
		IntervalTuple range = PlotterUtils.newRange(0, 5, -8, 8);
		IntervalFunctionSampler sampler =
				new IntervalFunctionSampler(function, range, 100);
		IntervalTupleList result = sampler.result();
		for (IntervalTuple tuple: result) {
			assertFalse(tuple.y().isHalfNegativeInfinity());
		}

	}

	@Test
	public void tanSqrtCosX() {
		IntervalTupleList tuples = functionValues("tan(sqrt(cos(x)))", -2, 2, 10, 10);
		assertTrue(false);
	}

	private IntervalTupleList functionValues(String functionDescription,
			double xmin, double xmax, double ymin, double ymax) {
		GeoFunction function = add(functionDescription);
		IntervalTuple range = PlotterUtils.newRange(xmin, xmax, ymin, ymax);
		IntervalFunctionSampler sampler = PlotterUtils.newSampler(function, range, 100);
		return sampler.result();
	}

}