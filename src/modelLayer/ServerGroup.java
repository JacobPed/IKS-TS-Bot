/**
 * 
 */
package modelLayer;

/**
 * @author Jacob
 *
 */
public enum ServerGroup {
	owner(6),
	admin(9),
	moderator(12),
	member(7),
	guest(8),
	jailed(11);
	
	private final int i;
	
	ServerGroup(int i) {
		this.i = i;
	}
	
	public int getIndex() {
		return i;
	}
}
