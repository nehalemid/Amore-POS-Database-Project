package projectBAD;

public class th {
	
	String transactionID;
	int modal;
	int gain;
	int earn;
	
	public String getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	public int getModal() {
		return modal;
	}

	public void setModal(int modal) {
		this.modal = modal;
	}

	public int getGain() {
		return gain;
	}

	public void setGain(int gain) {
		this.gain = gain;
	}

	public int getEarn() {
		return earn;
	}

	public void setEarn(int earn) {
		this.earn = earn;
	}
	
	public th (String transactionID, int modal, int earn, int gain) {
		super();
		this.transactionID = transactionID;
		this.modal = modal;
		this.gain = gain;
		this.earn = earn;
	}
}
