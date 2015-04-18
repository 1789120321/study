package stock.sohu;

import com.google.gson.Gson;

/**
 * Created by Luonanqin on 4/18/15.
 */
public class HistoryData {


	private String status;
	private String[][] hq;
	private String code;
	private String[] stat;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String[][] getHq() {
		return hq;
	}

	public void setHq(String[][] hq) {
		this.hq = hq;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String[] getStat() {
		return stat;
	}

	public void setStat(String[] stat) {
		this.stat = stat;
	}

	public static void main(String[] args) {
		Gson gson = new Gson();
		String str = "{\"status\":0,\"hq\":[[\"2015-04-17\",\"8.50\",\"8.22\",\"-0.08\",\"-0.96%\",\"8.20\",\"8.59\",\"160889\",\"13410.81\",\"8.27%\"],[\"2015-04-16\",\"8.00\",\"8.30\",\"0.22\",\"2.72%\",\"7.80\",\"8.45\",\"138088\",\"11335.55\",\"7.10%\"]],\"code\":\"cn_603333\",\"stat\":[\"累计:\",\"2015-04-16至2015-04-17\",\"0.14\",\"1.73%\",7.8,8.59,298977,24746.36,\"15.37%\"]}";
		HistoryStockData hsd = gson.fromJson(str, HistoryStockData.class);
		System.out.println(hsd);

	}

}
