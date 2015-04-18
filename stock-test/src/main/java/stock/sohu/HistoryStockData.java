package stock.sohu;

/**
 * Created by Luonanqin on 4/18/15.
 */
public class HistoryStockData {

	private String date; // 日期
	private String kp; // 开盘价
	private String sp; // 收盘价
	private String zde; // 涨跌额
	private String zdf; // 涨跌幅
	private String min; // 最低价
	private String max; // 最高价
	private String vols; // 成交量（手）
	private String vole; // 成交额（万）
	private String hs; // 换手率

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getKp() {
		return kp;
	}

	public void setKp(String kp) {
		this.kp = kp;
	}

	public String getSp() {
		return sp;
	}

	public void setSp(String sp) {
		this.sp = sp;
	}

	public String getZde() {
		return zde;
	}

	public void setZde(String zde) {
		this.zde = zde;
	}

	public String getZdf() {
		return zdf;
	}

	public void setZdf(String zdf) {
		this.zdf = zdf;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getVols() {
		return vols;
	}

	public void setVols(String vols) {
		this.vols = vols;
	}

	public String getVole() {
		return vole;
	}

	public void setVole(String vole) {
		this.vole = vole;
	}

	public String getHs() {
		return hs;
	}

	public void setHs(String hs) {
		this.hs = hs;
	}

	public String toString() {
		return date + ',' + kp + ',' + sp + ',' + zde + ',' + zdf + ',' + min + ',' + max + ',' + vols + ',' + vole + ',' + hs;

	}
}
