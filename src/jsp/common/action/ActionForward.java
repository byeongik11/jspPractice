package jsp.common.action;

public class ActionForward {
	private boolean isRedirect = false;
	private String nextPath = null; // 이동할 다음화면
	
	// Redirect 사용여부, false면 forward 사용
	
	public boolean isRedirect() {
		return isRedirect;
	}
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	public String getNextPath() {
		return nextPath;
	}
	public void setNextPath(String nextPath) {
		this.nextPath = nextPath;
	}
	
	
}
