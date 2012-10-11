/*
 * 
 */
package ImageViewTouch;

// TODO: Auto-generated Javadoc
/**
 * The Interface Easing.
 */
public interface Easing {

	/**
	 * Ease out.
	 *
	 * @param time the time
	 * @param start the start
	 * @param end the end
	 * @param duration the duration
	 * @return the double
	 */
	double easeOut( double time, double start, double end, double duration );

	/**
	 * Ease in.
	 *
	 * @param time the time
	 * @param start the start
	 * @param end the end
	 * @param duration the duration
	 * @return the double
	 */
	double easeIn( double time, double start, double end, double duration );

	/**
	 * Ease in out.
	 *
	 * @param time the time
	 * @param start the start
	 * @param end the end
	 * @param duration the duration
	 * @return the double
	 */
	double easeInOut( double time, double start, double end, double duration );
}
