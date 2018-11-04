package io.bankbridge.model;
import java.util.List;

public class BankModelList {
	//Changed from public to private. Variables should NEVER be public
	private List<BankModel> banks;

	public List<BankModel> getBanks() {
		return banks;
	}

	public void setBanks(List<BankModel> banks) {
		this.banks = banks;
	}

}
