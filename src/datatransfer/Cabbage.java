/* File: Cabbage.java
 * Author: Stanley Pieda
 * Modified: Stanley Pieda
 * Date: Sept, 2017
 * Description: Sample solution to Assignment 2
 * Modifications:
 *     String field for uuid with getters and setters
 *     implements Serializable with serialVersionUID
 *     overriden toString()
 */
package datatransfer;

import java.io.Serializable;

/**
 * Class to hold data about cabbages
 * Class provided by Stan Pieda
 */
public class Cabbage implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private int lineNumber;
	private String alpha;
	private String beta;
	private String charlie;
	private String delta;
	private String uuid;

	/**
	 * Default constructor
	 */
	public Cabbage() {
		this(0,0,"","","","");
	}

	/**
	 * sets all data of object from given values
	 * @param id
	 * @param lineNumber
	 * @param alpha
	 * @param beta
	 * @param charlie
	 * @param delta
	 */
	public Cabbage(Integer id, int lineNumber, String alpha, String beta, String charlie, String delta) {
		this.id = id;
		this.lineNumber = lineNumber;
		this.alpha = alpha;
		this.beta = beta;
		this.charlie = charlie;
		this.delta = delta;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	public String getAlpha() {
		return alpha;
	}
	public void setAlpha(String alpha) {
		this.alpha = alpha;
	}
	public String getBeta() {
		return beta;
	}
	public void setBeta(String beta) {
		this.beta = beta;
	}
	public String getCharlie() {
		return charlie;
	}
	public void setCharlie(String charlie) {
		this.charlie = charlie;
	}
	public String getDelta() {
		return delta;
	}
	public void setDelta(String delta) {
		this.delta = delta;
	}
	public String getUUID() {
		return uuid;
	}
	public void setUUID(String uuid) {
		this.uuid = uuid;
	}
	@Override
	public String toString() {
		return String.format("%d,%d,%s,%s,%s,%s,%s",id, lineNumber, alpha, beta, charlie, delta,uuid);
	}
}
